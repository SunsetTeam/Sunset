package sunset.ai;

import arc.math.Angles;
import arc.math.geom.Vec2;
import mindustry.entities.Predict;
import mindustry.entities.units.AIController;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Teamc;
import mindustry.type.Weapon;
import sunset.ai.weapon.WeaponAI;
import sunset.type.weapons.WeaponExt;

/** AI, которое использует AI орудия, если таковое существует. */
public class UnitWeaponAI extends AIController {
    @Override
    protected void updateWeapons() {
        if(targets.length != unit.mounts.length) targets = new Teamc[unit.mounts.length];

        float rotation = unit.rotation - 90;
        boolean ret = retarget();

        if(ret) {
            target = findTarget(unit.x, unit.y, unit.range(), unit.type.targetAir, unit.type.targetGround);
        }
        if(invalid(target)) {
            target = null;
        }

        unit.isShooting = false;

        for(int i = 0; i < targets.length; i++){
            WeaponMount mount = unit.mounts[i];
            Weapon weapon = mount.weapon;

            float mountX = unit.x + Angles.trnsx(rotation, weapon.x, weapon.y),
                    mountY = unit.y + Angles.trnsy(rotation, weapon.x, weapon.y);

            if (weapon instanceof WeaponExt) {
                WeaponAI ai = ((WeaponExt)weapon).ai;
                if(ai.isUnset()) ai.set(unit, mount);
                ai.update();
                unit.isShooting |= ai.isShooting();
                if(ai.isShooting()) {
                    unit.aimX = ai.aimX();
                    unit.aimY = ai.aimY();
                }
            } else {
                if (ret) {
                    targets[i] = findTarget(mountX, mountY, weapon.bullet.range(), weapon.bullet.collidesAir, weapon.bullet.collidesGround);
                }

                boolean shoot = false;

                if (targets[i] != null) {
                    shoot = targets[i].within(mountX, mountY, weapon.bullet.range()) && shouldShoot();

                    Vec2 to = Predict.intercept(unit, targets[i], weapon.bullet.speed);
                    mount.aimX = to.x;
                    mount.aimY = to.y;
                }

                mount.shoot = shoot;
                mount.rotate = shoot;

                unit.isShooting |= shoot;
                if (shoot) {
                    unit.aimX = mount.aimX;
                    unit.aimY = mount.aimY;
                }
            }
        }
    }
}
