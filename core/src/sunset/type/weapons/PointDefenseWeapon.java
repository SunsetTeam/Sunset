package sunset.type.weapons;

import arc.Core;
import arc.graphics.Color;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.*;
import arc.scene.ui.layout.Table;
import arc.util.Strings;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.world.meta.StatValue;
import sunset.ai.weapon.EmptyWeaponAI;
import sunset.ai.weapon.WeaponAI;
import sunset.content.SnBullets;
import sunset.type.UpdateDrawWeapon;
import sunset.utils.Utils;

public class PointDefenseWeapon extends WeaponExt implements UpdateDrawWeapon, StatValue {
    public float range;
    public Color color = Color.white;
    Bullet target, _target;
    public float damage;
    public Effect beamEffect = Fx.pointBeam;
    public PointDefenseWeapon(String name){
        super(name);
        ai = new EmptyWeaponAI();
        rotateSpeed = 0;
        bullet = SnBullets.emptyBullet;
    }

    protected Bullet findTarget(Unit unit, float x, float y, float range){
        return Groups.bullet.intersect(x - range, y - range, range*2, range*2)
                .min(b -> b.team != unit.team && b.type().hittable, b -> b.dst2(x, y));
    }

    protected boolean checkTarget(Unit unit, Bullet target, float x, float y, float range){
        return target.within(unit, range) && target.team() != unit.team && target.type != null && target.type.hittable;
    }
    @Override
    public void update(WeaponMount mount, Unit unit) {
        Vec2 wpos = Tmp.v1.set(Utils.mountX(unit,mount), Utils.mountY(unit,mount));
        _target = findTarget(unit, wpos.x, wpos.y, range);
        if(_target != null && checkTarget(unit, _target, wpos.x, wpos.y, range)) {
            target = _target;
        } else if(target == null || !checkTarget(unit, target, wpos.x, wpos.y, range)) {
            return;
        }
        mount.rotation = Angles.angle(wpos.x, wpos.y, target.x, target.y) - unit.rotation;
        if(readyToShoot(unit)) {
            shoot(wpos, target);
            setShoot();
        }
    }

    void shoot(Vec2 from, Bullet target) {
        if(target.damage() > damage) {
            target.damage(target.damage() - damage);
        } else {
            target.remove();
        }
        float rotation = Angles.angle(from.x, from.y, target.x, target.y);
        beamEffect.at(from.x, from.y, rotation, color, new Vec2().set(target));
        bullet.shootEffect.at(from.x, from.y, rotation, color);
        bullet.hitEffect.at(target.x, target.y, color);
        shootSound.at(from.x, from.y, Mathf.random(0.9f, 1.1f));
    }
    @Override
    public boolean useDefaultDraw() { return true; }
    @Override
    public void preDraw(WeaponMount mount, Unit unit) { }
    @Override
    public void postDraw(WeaponMount mount, Unit unit) { }
    @Override
    public void display(Table table) {
        table.left().defaults().padRight(3).left();
        table.row();
        table.add(Core.bundle.get("bullet.destroybullets"));
        table.row();
        table.add(Core.bundle.format("stat.weaponreload" + (mirror ? "mirror" : ""),
                Strings.autoFixed(60f / reload, 2)));
        table.row();
        table.add(Core.bundle.format("bullet.damage", damage));
    }
}