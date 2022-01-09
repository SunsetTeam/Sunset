package sunset.type.weapons;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import sunset.ai.weapon.*;
import sunset.content.*;
import sunset.utils.*;

import static arc.graphics.Color.*;

public class ChainWeapon extends WeaponExt implements StatValue{
    public static final UnitData.DataKey<ObjectMap<WeaponMount, Seq<Unit>>> chainWeaponDataKey = UnitData.dataKey(ObjectMap::new);
    public int maxChainLength = 1;
    public float range = 120f;
    public float damageTick = 1f;
    public float healTick = 4f;
    public float rangeFactor = 0.9f;
    public float damageFactor = 0.9f;
    public float laserLayer = Layer.bullet;
    public Color chainColor = coral.cpy();
    public boolean draw = false;
    TextureRegion laser, laserEnd;

    public ChainWeapon(String name){
        super(name);
        // Should never shoot using the standard method.
        ai = new EmptyWeaponAI();
        bullet = SnBullets.emptyBullet;
        firstShotDelay = Float.MAX_VALUE;
        reload = Float.MAX_VALUE;
        recoil = 0;
    }

    public static Unit getFirstUnit(WeaponMount mount, Unit unit){
        Vec2 wpos = Tmp.v1.set(Utils.mountX(unit, mount), Utils.mountY(unit, mount));
        ChainWeapon weapon = (ChainWeapon)mount.weapon;
        return Units.closest(null, wpos.x, wpos.y, weapon.range, u -> {
            if(unit == u) return false;
            if(u.team != unit.team && weapon.damageTick == 0) return false;
            if(u.team == unit.team && (weapon.healTick == 0 || !u.damaged())) return false;
            return mount.weapon.rotate || Angles.within(wpos.angleTo(u), unit.rotation + mount.rotation, mount.weapon.shootCone);
        });
    }

    @Override
    public void load(){
        super.load();
        laser = Core.atlas.find("parallax-laser");
        laserEnd = Core.atlas.find("parallax-laser-end");
    }

    private Seq<Unit> getUnits(WeaponMount mount, Unit unit){
        ObjectMap<WeaponMount, Seq<Unit>> data = chainWeaponDataKey.get(unit);
        if(data == null) return null;
        return data.get(mount, Seq::new);
    }

    @Override
    public void update(Unit unit, WeaponMount mount){
        Seq<Unit> units = getUnits(mount, unit);
        if(units == null) return;
        updateUnits(units, mount, unit);
        float[] damages = new float[]{damageTick, healTick};
        units.each(u -> {
            if(unit.team.isEnemy(u.team)){
                u.damageContinuousPierce(damages[0]);
            }else{
                u.heal(damages[1] * Time.delta);
            }
            damages[0] *= damageFactor;
            damages[1] *= damageFactor;
        });
    }

    private void updateUnits(Seq<Unit> units, WeaponMount mount, Unit unit){
        units.clear();
        final float[] currentRange = {range};
        Unit currentUnit = getFirstUnit(mount, unit);
        for(int unitsLeft = maxChainLength; unitsLeft > 0 && currentUnit != null; unitsLeft--){
            currentRange[0] *= rangeFactor;
            units.add(currentUnit);
            Tmp.v1.set(currentUnit);

            currentUnit = Units.closest(null, currentUnit.x, currentUnit.y, currentRange[0], u -> {
                if(u == unit || units.contains(u)) return false;
                if((u.health >= u.maxHealth) && !unit.team.isEnemy(u.team)) return false;
                return Tmp.v1.within(u, currentRange[0]);
            });
            unitsLeft--;
        }
    }

    @Override
    public boolean customDraw(){
        return true;
    }

    private void drawWeapon(WeaponMount mount, Unit unit, float rotation){
        Weapon weapon = mount.weapon;
        Vec2 wpos = Tmp.v1.set(Utils.mountX(unit, mount), Utils.mountY(unit, mount));

        if(weapon.shadow > 0) Drawf.shadow(wpos.x, wpos.y, weapon.shadow);

        if(weapon.outlineRegion.found()){
            float z = Draw.z();
            if(!weapon.top) Draw.z(z - unit.type.outlineRadius);
            Draw.rect(weapon.outlineRegion, wpos,
            weapon.outlineRegion.width * Draw.scl * -Mathf.sign(weapon.flipSprite),
            weapon.region.height * Draw.scl, rotation);
            Draw.z(z);
        }

        Draw.rect(weapon.region, wpos,
        weapon.region.width * Draw.scl * -Mathf.sign(weapon.flipSprite),
        weapon.region.height * Draw.scl, rotation);

        if(weapon.heatRegion.found() && mount.heat > 0){
            Draw.color(weapon.heatColor, mount.heat);
            Draw.blend(Blending.additive);
            Draw.rect(weapon.heatRegion, wpos,
            weapon.heatRegion.width * Draw.scl * -Mathf.sign(weapon.flipSprite),
            weapon.heatRegion.height * Draw.scl,
            rotation);
            Draw.blend();
            Draw.color();
        }
    }

    @Override
    public void preDraw(WeaponMount mount, Unit unit){
        Seq<Unit> units = getUnits(mount, unit);
        Vec2 weaponPos = Tmp.v1.set(Utils.mountX(unit, mount), Utils.mountY(unit, mount));
        float angle = (units == null || units.isEmpty()) ? unit.rotation : weaponPos.angleTo(units.get(0));
        if(units != null && !units.isEmpty()){
            float z = Draw.z();
            Draw.z(laserLayer); //TODO как-то пофиксить эффекты луча
            Draw.mixcol(chainColor, 0.4f);
            Drawf.laser(unit.team, laser, laserEnd, weaponPos.x, weaponPos.y, units.get(0).x, units.get(0).y);
            for(int i = 0; i < units.size - 1; i++){
                Drawf.laser(unit.team, laser, laserEnd,
                units.get(i).x, units.get(i).y,
                units.get(i + 1).x, units.get(i + 1).y);
            }
            Draw.mixcol();
            Draw.z(z);
        }
        if(draw) drawWeapon(mount, unit, angle - 90);
    }

    @Override
    public void postDraw(WeaponMount mount, Unit unit){
    }

    @Override
    public void display(Table table){
        table.left().defaults().padRight(3).left();
        if(damageTick > 0){
            table.row();
            table.add(Core.bundle.format("bullet.damagesec", Strings.autoFixed(damageTick * 60, 1)));
        }
        if(healTick > 0){
            table.row();
            table.add(Core.bundle.format("bullet.healsec", Strings.autoFixed(healTick * 60, 1)));
        }
        if(maxChainLength != 1){
            table.row();
            table.add(Core.bundle.format("bullet.maxchain", maxChainLength));
        }
    }
}
