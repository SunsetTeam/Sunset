package sunset.ai;

import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.util.Log;
import mindustry.Vars;
import mindustry.ai.types.FlyingAI;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentType;
import mindustry.entities.Fires;
import mindustry.entities.Predict;
import mindustry.entities.Units;
import mindustry.entities.bullet.LiquidBulletType;
import mindustry.entities.units.UnitCommand;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.*;
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

    protected static Fire getBuildingFire(Building b) {
        Log.info(b.tileX() + " " + b.tileY());
        for(int dx = 0; dx < b.block.size; dx++)
            for(int dy = 0; dy < b.block.size; dy++)
                if(Fires.has(b.tileX() + dx, b.tileY() + dy))
                    return Fires.get(b.tileX() + dx, b.tileY() + dy);
        return null;
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
        Vars.indexer.eachBlock(unit.team, unit.x, unit.y, range, bld -> true, building -> {
            Fire fire = getBuildingFire(building);
            if(ret[0] == null && fire != null) ret[0] = building;
        });
        if(ret[0] != null) {
            return ret[0];
        }
        // Ищем врагов
        return super.findTarget(x, y, range, air, ground);
    }

    @Override
    public void updateMovement() {
        Unit u = Units.closest(unit.team, unit.x, unit.y,
                Float.MAX_VALUE, un -> un != unit && isUnitBurning(un),
                (unit1, x, y) -> Mathf.pow(unit1.health / unit1.maxHealth, 2f));
        if(u != null) {
            moveTo(u, unit.range());
            return;
        }
        Fire[] b = new Fire[] { null };
        float[] cost = new float[] { Float.MAX_VALUE };
        float range = Math.max(world.width(), world.height()) * tilesize;
        Vars.indexer.eachBlock(unit.team, unit.x, unit.y, range, bld -> true, building -> {
            Fire fire = getBuildingFire(building);
            if(fire == null) return;
            float cs = Mathf.pow(building.health / building.maxHealth, 2f)
                    * Mathf.dst(unit.x, unit.y, building.x, building.y);
            if(b[0] == null || cs < cost[0]) {
                cost[0] = cs;
                b[0] = fire;
            }
        });
        if(b[0] != null) {
            moveTo(b[0], unit.range());
            return;
        }
        super.updateMovement();
    }

    protected void moveTo(Posc target, float length){
        if(target == null) return;

        vec.set(target).sub(unit);

        float scl = 1f;

        if(vec.len() < length) {
            scl = 0;
        }

        vec.setLength(unit.realSpeed() * scl);

        unit.moveAt(vec);
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