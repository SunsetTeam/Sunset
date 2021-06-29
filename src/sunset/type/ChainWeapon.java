package sunset.type;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;

import static arc.graphics.Color.coral;
import static sunset.Utils.*;

public class ChainWeapon extends AutoWeapon implements UpdateDrawWeapon {
    public int maxChainLength = 1;
    public float range = 120f;
    public float damageTick = 1f;
    public float healTick = 4f;
    public float rangeFactor = 0.9f;
    public float damageFactor = 0.9f;
    public Color chainColor = coral.cpy();
    TextureRegion laser, laserEnd;
    public ChainWeapon(String name) {
        super(name);
        laser = Core.atlas.find("parallax-laser");
        laserEnd = Core.atlas.find("parallax-laser-end");
        /** Никогда не должно стрелять стандартным методом. */
        bullet = new BasicBulletType();
        firstShotDelay = Float.MAX_VALUE;
        reload = Float.MAX_VALUE;
    }

    Seq<Unit> units = new Seq<>();
    @Override
    public void update(WeaponMount mount, Unit unit) {
        updateUnits(mount, unit);
        float[] p = new float[] { damageTick, healTick };
        units.each(u -> {
            if(unit.team.isEnemy(u.team)) {
                u.damageContinuousPierce(p[0]);
            } else {
                u.heal(p[1] * Time.delta);
            }
            p[0] *= damageFactor;
            p[1] *= damageFactor;
        });
    }

    private void updateUnits(WeaponMount mount, Unit unit) {
        units.clear();
        final float[] r = new float[] { range };
        final int[] d = new int[] { maxChainLength };
        Vec2 wpos = new Vec2(mount.weapon.x, mount.weapon.y).rotate(mount.rotation).add(unit.x, unit.y);
        final Unit[] t = new Unit[] { unitClosest(wpos.x, wpos.y, u -> {
            if(Mathf.dst(wpos.x, wpos.y, u.x, u.y) > r[0]) return false;
            if((u.maxHealth <= u.health) && !unit.team.isEnemy(u.team)) return false;
            float a1 = unit.rotation + mount.rotation;
            float a2 = wpos.angleTo(u.x, u.y);
            return Math.abs(a1 - a2) <= mount.weapon.shootCone;
        })};
        while(t[0] != null) {
            d[0]--;
            r[0] *= rangeFactor;
            units.add(t[0]);
            t[0] = unitClosest(t[0].x, t[0].y, u -> {
                if(units.contains(u)) return false;
                return (Mathf.dst(t[0].x, t[0].y, u.x, u.y) <= r[0]) && (d[0] > 0);
            });
        }
    }

    @Override
    public void preDraw(WeaponMount mount, Unit unit) {
        if(units.isEmpty()) return;
        Draw.z(Layer.bullet);
        Draw.mixcol(coral.cpy(), 0.4f);
        Vec2 wpos = new Vec2(mount.weapon.x, mount.weapon.y).rotate(mount.rotation).add(unit.x, unit.y);
        Drawf.laser(unit.team, laser, laserEnd, wpos.x, wpos.y, units.get(0).x, units.get(0).y);
        for(int i = 0; i < units.size - 1; i++) {
            Drawf.laser(unit.team, laser, laserEnd,
                    units.get(i).x, units.get(i).y,
                    units.get(i+1).x, units.get(i+1).y);
        }
        Draw.mixcol();
    }
    @Override
    public void postDraw(WeaponMount mount, Unit unit) { }
}
