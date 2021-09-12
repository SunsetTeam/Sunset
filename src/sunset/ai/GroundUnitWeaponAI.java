package sunset.ai;

import arc.math.Mathf;
import mindustry.ai.Pathfinder;
import mindustry.entities.Predict;
import mindustry.entities.Units;
import mindustry.entities.units.UnitCommand;
import mindustry.gen.Building;
import mindustry.gen.Teamc;
import mindustry.world.Tile;
import mindustry.world.meta.BlockFlag;

import static mindustry.Vars.state;
import static mindustry.Vars.tilesize;

public class GroundUnitWeaponAI extends UnitWeaponAI {
    @Override
    public void updateMovement(){
        Building core = unit.closestEnemyCore();

        if(core != null && unit.within(core, unit.range() / 1.1f + core.block.size * tilesize / 2f)){
            target = core;
            for(int i = 0; i < targets.length; i++){
                if(unit.mounts[i].weapon.bullet.collidesGround) targets[i] = core;
            }
        }

        if((core == null || !unit.within(core, unit.range() * 0.5f)) && command() == UnitCommand.attack){
            boolean move = true;
            if(state.rules.waves && unit.team == state.rules.defaultTeam){
                Tile spawner = getClosestSpawner();
                if(spawner != null && unit.within(spawner, state.rules.dropZoneRadius + 120f)) move = false;
            }
            if(move) pathfind(Pathfinder.fieldCore);
        }

        if(command() == UnitCommand.rally){
            Teamc target = targetFlag(unit.x, unit.y, BlockFlag.rally, false);
            if(target != null && !unit.within(target, 70f)) pathfind(Pathfinder.fieldRally);
        }

        if(unit.type.canBoost && !unit.onSolid()){
            unit.elevation = Mathf.approachDelta(unit.elevation, 0f, unit.type.riseSpeed);
        }

        if(!Units.invalidateTarget(target, unit, unit.range()) && unit.type.rotateShooting){
            if(unit.type.hasWeapons()){
                unit.lookAt(Predict.intercept(unit, target, unit.type.weapons.first().bullet.speed));
            }
        }else if(unit.moving()) unit.lookAt(unit.vel().angle());
    }
}
