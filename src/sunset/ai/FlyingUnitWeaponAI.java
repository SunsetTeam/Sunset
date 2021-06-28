package sunset.ai;

import arc.math.Angles;
import mindustry.entities.units.UnitCommand;
import mindustry.world.meta.BlockFlag;
import static mindustry.Vars.state;

public class FlyingUnitWeaponAI extends UnitWeaponAI {
    @Override
    public void updateMovement(){
        if(target != null && unit.hasWeapons() && command() == UnitCommand.attack){
            if(!unit.type.circleTarget){
                moveTo(target, unit.type.range * 0.8f);
                unit.lookAt(target);
            }else{
                attack(120f);
            }
        }

        if(target == null && command() == UnitCommand.attack && state.rules.waves && unit.team == state.rules.defaultTeam){
            moveTo(getClosestSpawner(), state.rules.dropZoneRadius + 120f);
        }

        if(command() == UnitCommand.rally){
            moveTo(targetFlag(unit.x, unit.y, BlockFlag.rally, false), 60f);
        }
    }

    protected void attack(float circleLength){
        vec.set(target).sub(unit);

        float ang = unit.angleTo(target);
        float diff = Angles.angleDist(ang, unit.rotation());

        if(diff > 70f && vec.len() < circleLength){
            vec.setAngle(unit.vel().angle());
        }else{
            vec.setAngle(Angles.moveToward(unit.vel().angle(), vec.angle(), 6f));
        }

        vec.setLength(unit.speed());

        unit.moveAt(vec);
    }
}
