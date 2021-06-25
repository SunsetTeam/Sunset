package sunset.ai;

import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import mindustry.Vars;
import mindustry.ai.types.FlyingAI;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentType;
import mindustry.entities.Fires;
import mindustry.entities.Predict;
import mindustry.entities.Units;
import mindustry.entities.bullet.LiquidBulletType;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Building;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mindustry.type.Weapon;
import sunset.type.LiquidWeapon;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

/** AI, которое пытается тушить горящие союзные пострйки или боевые единицы, если таковые есть. */
public class ExtinguishAI extends FlyingAI {
    protected static boolean isUnitBurning(Unit u) {
        // Считаем, что эффект - горение, если он наносит урон и
        // среди его противоположностей есть вода. Такой подход позволит
        // тушить "пожары" из других модов.
        return Vars.content.getBy(ContentType.status).find(content -> {
            StatusEffect s = (StatusEffect)content;
            return u.hasEffect(s) && s.damage > 0 && s.opposites.contains(StatusEffects.wet);
        }) != null;
    }

    protected Teamc findTargetExt(float x, float y, float range, boolean air, boolean ground) {
        final Teamc[] ret = new Teamc[] { null };
        // Ищем горящих юнитов
        Units.nearby(unit.team, x, y, range, u -> {
            if(u != unit && ret[0] == null && isUnitBurning(u)) {
                ret[0] = u;
            }
        });
        // Ищем горящие постройки
        int tr = (int)(range / tilesize);
        int bx = (int)(x / tilesize), by = (int)(y / tilesize);
        for(int fx = -tr; fx < tr; fx++)
            for(int fy = -tr; fy < tr; fy++)
                if(Mathf.dst(fx, fy) <= tr && ret[0] == null)
                {
                    Building b = world.build(bx + fx, by + fy);
                    if(b != null && Fires.has(bx + fx, by + fy) && b.team == unit.team) {
                        ret[0] = b;
                    }
                }
        if(ret[0] != null) {
            return ret[0];
        }
        // Ищем врагов
        return super.findTarget(x, y, range, air, ground);
    }

    @Override
    protected void updateWeapons() {
        if(targets.length != unit.mounts.length) targets = new Teamc[unit.mounts.length];

        float rotation = unit.rotation - 90;
        boolean ret = retarget();

        unit.isShooting = false;

        for(int i = 0; i < targets.length; i++){
            WeaponMount mount = unit.mounts[i];
            Weapon weapon = mount.weapon;

            float mountX = unit.x + Angles.trnsx(rotation, weapon.x, weapon.y),
                    mountY = unit.y + Angles.trnsy(rotation, weapon.x, weapon.y);

            if(ret){
                if(weapon instanceof LiquidWeapon
                        && weapon.bullet instanceof LiquidBulletType
                        && ((LiquidBulletType)weapon.bullet).liquid.canExtinguish())
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