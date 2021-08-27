package sunset.ai;

import arc.math.Mathf;
import mindustry.Vars;
import mindustry.entities.Units;
import mindustry.gen.*;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;
import static sunset.Utils.*;

/** AI, которое преследует горящие союзные пострйки или боевые единицы, если таковые есть. */
public class ExtinguishAI extends FlyingUnitWeaponAI {
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
        unit.rotation(vec.angle());
        float scl = vec.len() < length ? 0 : 1f;
        vec.setLength(unit.realSpeed() * scl);
        unit.moveAt(vec);
    }
}