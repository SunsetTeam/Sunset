package sunset.ai;

import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Log;
import mindustry.Vars;
import mindustry.ai.types.FlyingAI;
import mindustry.content.StatusEffects;
import mindustry.entities.Fires;
import mindustry.entities.Predict;
import mindustry.entities.Units;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Building;
import mindustry.gen.Teamc;
import mindustry.type.Weapon;
import mindustry.world.Tile;
import sunset.type.ExtinguishWeapon;

import java.util.concurrent.atomic.AtomicReference;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

/** AI, которое пытается тушить горящие союзные пострйки или боевые единицы, если таковые есть. */
public class ExtinguishAI extends FlyingAI {
    protected static boolean hasFires(Building b) {
        boolean ret = false;
        for (int x = 0; x < b.block.size && !ret; x++)
            for (int y = 0; y < b.block.size && !ret; y++)
                ret = Fires.has(b.tileX() + x, b.tileY() + y);
        return ret;
    }
    protected Teamc findTargetExt(float x, float y, float range, boolean air, boolean ground) {
        final AtomicReference<Teamc> ret = new AtomicReference<>(null);
        // Ищем горящих юнитов
        Log.info(unit.team.name + " " + x + "/" + y);
        Units.nearby(unit.team, x, y, range, u -> {
            //Log.info(u.toString() + " " + u.hasEffect(StatusEffects.burning) + " " + u.x + "/" + u.y);
            if(u != unit && u.hasEffect(StatusEffects.burning) && ret.get() == null) {
                ret.set(u);
            }
        });
        // Ищем горящие постройки
        int tr = (int)(range / tilesize);
        int bx = (int)(x / tilesize), by = (int)(y / tilesize);
        for(int fx = -tr; fx < tr; fx++)
            for(int fy = -tr; fy < tr; fy++)
                if(Mathf.dst(fx, fy) <= tr && ret.get() == null)
                {
                    Building b = world.build(bx + fx, by + fy);
                    Log.info(b + "->" + fx + ":" + fy);
                    if(b != null && Fires.has(bx + fx, by + fy) && b.team == unit.team) {
                        ret.set(b);
                    }
                }
        Log.info(ret.get());
        if(ret.get() != null) {
            return ret.get();
        }
        // Ищем врагов
        return super.findTarget(x, y, range, air, ground);
    }

    @Override
    protected void updateWeapons() {
        if(targets.length != unit.mounts.length) targets = new Teamc[unit.mounts.length];

        float rotation = unit.rotation - 90;
        boolean ret = retarget();

        if(ret) {
            Log.info("Update weapons");
        }

        unit.isShooting = false;

        for(int i = 0; i < targets.length; i++){
            WeaponMount mount = unit.mounts[i];
            Weapon weapon = mount.weapon;

            float mountX = unit.x + Angles.trnsx(rotation, weapon.x, weapon.y),
                    mountY = unit.y + Angles.trnsy(rotation, weapon.x, weapon.y);

            if(ret){
                if(weapon instanceof ExtinguishWeapon)
                    targets[i] = findTargetExt(mountX, mountY, weapon.bullet.range(), weapon.bullet.collidesAir, weapon.bullet.collidesGround);
                else
                    targets[i] = findTarget(mountX, mountY, weapon.bullet.range(), weapon.bullet.collidesAir, weapon.bullet.collidesGround);
            }

            if(invalid(targets[i], weapon)){
                targets[i] = null;
            }

            boolean shoot = false;

            if(targets[i] != null){
                shoot = targets[i].within(mountX, mountY, weapon.bullet.range()) && shouldShoot();

                Vec2 to = Predict.intercept(unit, targets[i], weapon.bullet.speed);
                mount.aimX = to.x;
                mount.aimY = to.y;
            }

            mount.shoot = shoot;
            mount.rotate = shoot;

            unit.isShooting |= shoot;
            if(shoot){
                unit.aimX = mount.aimX;
                unit.aimY = mount.aimY;
            }
        }
    }

    @Override
    protected boolean invalid(Teamc target) {
        return invalid(target, null);
    }

    protected boolean invalid(Teamc t, Weapon w) {
        return false;
        //TODO: починить валидатор
        /*float r = (w == null) ? unit.type.range : w.bullet.range();
        float x = unit.x + ((w == null) ? 0 : w.x);
        float y = unit.x + ((w == null) ? 0 : w.y);
        if(t != null) {
            Log.info("Is invalid");
            Log.info((!t.within(x, y, r + (t instanceof Sized ? ((Sized) t).hitSize() / 2f : 0f))));
            Log.info((t instanceof Healthc && !((Healthc)t).isValid()));
        }
        return t == null ||
                (!t.within(x, y, r + (t instanceof Sized ? ((Sized) t).hitSize() / 2f : 0f))) ||
                (t instanceof Healthc && !((Healthc)t).isValid());*/
    }
}