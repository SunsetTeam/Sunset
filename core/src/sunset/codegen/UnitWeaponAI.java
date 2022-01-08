package sunset.codegen;

import arc.math.geom.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.type.*;
import sunset.ai.weapon.*;
import sunset.annotations.SnAnnotations.*;
import sunset.type.weapons.*;
import sunset.utils.*;

/**
 * AI, которое использует AI орудия, если таковое существует.
 */
@BothExtends(
classes = {"mindustry.ai.types.FlyingAI","mindustry.ai.types.GroundAI"},
classNames = {"FlyingWeaponAI", "GroundWeaponAI"},
packageName = "sunset.ai"
)
class UnitWeaponAI extends AIController{

    @Override
    public void updateWeapons(){
        float rotation = unit.rotation - 90;
        boolean ret = retarget();

        if(ret){
            target = findTarget(unit.x, unit.y, unit.range(), unit.type.targetAir, unit.type.targetGround);
        }
        if(invalid(target)){
            target = null;
        }

        unit.isShooting = false;
        for(WeaponMount mount : unit.mounts){
            Weapon weapon = mount.weapon;

            float mountX = Utils.mountX(unit, mount),
            mountY = Utils.mountY(unit, mount);

            if(weapon instanceof WeaponExt){
                WeaponAI ai = ((WeaponExt)weapon).ai;
                unit.isShooting |= ai.update(unit, mount);
            }else{
                if(ret){
                    mount.target = findTarget(mountX, mountY, weapon.bullet.range(), weapon.bullet.collidesAir, weapon.bullet.collidesGround);
                }

                boolean shoot = false;

                if(mount.target != null){
                    shoot = mount.target.within(mountX, mountY, weapon.bullet.range()) && shouldShoot();

                    Vec2 to = Predict.intercept(unit, mount.target, weapon.bullet.speed);
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
    }
}
