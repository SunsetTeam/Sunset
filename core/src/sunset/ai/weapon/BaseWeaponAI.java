package sunset.ai.weapon;

import arc.math.geom.Position;
import arc.math.geom.Vec2;
import mindustry.entities.Predict;
import mindustry.entities.Units;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;

import static sunset.utils.Utils.mountX;
import static sunset.utils.Utils.mountY;

public class BaseWeaponAI extends WeaponAI {
    @Override
    public boolean update(Unit unit, WeaponMount mount) {
        float mountX = mountX(unit, mount);
        float mountY = mountY(unit, mount);
        Teamc target = Units.closestTarget(unit.team, mountX, mountY, mount.weapon.bullet.range,
                u -> u.checkTarget(mount.weapon.bullet.collidesAir, mount.weapon.bullet.collidesGround),
                t -> mount.weapon.bullet.collidesGround);
        aim(target, unit, mount);
        return target != null;
    }

    protected void aim(Position target, Unit unit, WeaponMount mount) {
        if (target == null) {
            mount.shoot = false;
            mount.rotate = false;
        } else {
            float mountX = mountX(unit, mount);
            float mountY = mountY(unit, mount);
            mount.shoot = target.within(mountX, mountY,
                    mount.weapon.bullet.range);
            mount.rotate = mount.shoot;
            Vec2 to = Predict.intercept(unit, target, mount.weapon.bullet.speed);
            mount.aimX = to.x;
            mount.aimY = to.y;
        }
    }
}
