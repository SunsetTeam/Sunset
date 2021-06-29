package sunset.ai.weapon;

import arc.math.geom.Position;
import arc.math.geom.Vec2;
import mindustry.entities.Predict;
import mindustry.entities.Units;
import mindustry.gen.Teamc;

public class BaseWeaponAI extends WeaponAI {
    @Override
    public void update() {
        Teamc target = Units.closestTarget(unit.team, weaponX(), weaponY(), range(),
                u -> u.checkTarget(mount.weapon.bullet.collidesAir, mount.weapon.bullet.collidesGround),
                t -> mount.weapon.bullet.collidesGround);
        aim(target);
    }

    protected void aim(Position target) {
        if(target == null) {
            mount.shoot = false;
            mount.rotate = false;
        } else {
            mount.shoot = target.within(weaponX(), weaponY(), mount.weapon.bullet.range());
            mount.rotate = mount.shoot;
            Vec2 to = Predict.intercept(unit, target, mount.weapon.bullet.speed);
            mount.aimX = to.x;
            mount.aimY = to.y;
        }
    }
}
