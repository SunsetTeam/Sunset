package sunset.ai.weapon;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Fire;
import mindustry.gen.Posc;
import mindustry.gen.Unit;
import sunset.type.UnitData;

import static sunset.utils.Utils.getBuildingFire;
import static sunset.utils.Utils.isUnitBurning;

public class ExtinguishWeaponAI extends BaseWeaponAI {
    public final static UnitData.DataKey extinguishTick = UnitData.dataKey();
    private static final Vec2 tmpVec = new Vec2();
    private static float minCost;
    static int ticks = 15;
    Posc target;

    @Override
    public boolean update(Unit unit, WeaponMount mount) {
        int tick = UnitData.getData(unit, extinguishTick, () -> 0);
        if (++tick >= ticks) {
            tick = 0;
            target = findTarget(unit, mount);
        }
        UnitData.setData(unit, extinguishTick, tick);
        if (target != null) {
            aim(Tmp.v1.set(target), unit, mount);
            return true;
        }
        aim(null, unit, mount);
        return false;
    }

    private Posc findTarget(Unit unit, WeaponMount mount) {
        float range = mount.weapon.bullet.range();
        Vec2 point = tmpVec.set(mount.weapon.x + unit.x, mount.weapon.y + unit.y);
        target = unit.team.data().units.min(un -> un != unit && !(point.dst(un) > range) && isUnitBurning(un), un -> {
            return un.healthf() * un.healthf() * Mathf.len(unit.x - un.x, un.y - unit.y);
        });
        // если не нашли юнитов, то ищем постройки
        if (target == null) {
            minCost= Float.MAX_VALUE;
            Vars.indexer.eachBlock(unit.team, unit.x, unit.y, range, bld -> true, building -> {
                Fire fire = getBuildingFire(building);
                if (fire == null) return;
                float cost = Mathf.sqr(building.healthf()) * Mathf.dst(unit.x, unit.y, building.x, building.y);
                if (cost < minCost) {
                    minCost = cost;
                    target = fire;
                }
            });
        }
        return target;
    }
}
