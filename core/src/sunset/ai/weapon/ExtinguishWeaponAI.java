package sunset.ai.weapon;

import arc.math.Mathf;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import mindustry.Vars;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.*;
import sunset.type.UnitData;

import static sunset.utils.Utils.*;

public class ExtinguishWeaponAI extends BaseWeaponAI {
    static int ticks = 15;
    Posc target;
    @Override
    public boolean update(Unit unit, WeaponMount mount) {
        int tick = UnitData.getData(unit, UnitData.extinguishTick, () -> 0);
        if (++tick >= ticks) {
            tick = 0;
            target = findTarget(unit, mount);
        }
        UnitData.setData(unit, UnitData.extinguishTick, tick);
        if (target != null) {
            aim(new Pos(target), unit, mount);
            return true;
        }
        aim(null, unit, mount);
        return false;
    }
    private static class Pos implements Position {
        public Pos(Posc c) {
            this.x = c.getX();
            this.y = c.getY();
        }
        final float x, y;
        @Override
        public float getX() { return x; }
        @Override
        public float getY() { return y; }
    }
    private Posc findTarget(Unit unit, WeaponMount mount) {
        final Posc[] ret = {null};
        float range = mount.weapon.bullet.range();
        final float[] minCost = {Float.MAX_VALUE};
        Vec2 point = new Vec2(mount.weapon.x + unit.x, mount.weapon.y + unit.y);
        unit.team.data().units.each(un -> {
            if (un == unit || point.dst(un) > range || !isUnitBurning(un)) return;
            float cost = Pow2(un.healthf()) * Mathf.len(unit.x - un.x, un.y - unit.y);
            if(cost < minCost[0]) {
                minCost[0] = cost;
                ret[0] = un;
            }
        });
        // если не нашли юнитов, то ищем постройки
        if(ret[0] == null) {
            minCost[0] = Float.MAX_VALUE;
            Vars.indexer.eachBlock(unit.team, unit.x, unit.y, range, bld -> true, building -> {
                Fire fire = getBuildingFire(building);
                if (fire == null) return;
                float cost = Pow2(building.healthf()) * Mathf.dst(unit.x, unit.y, building.x, building.y);
                if (cost < minCost[0]) {
                    minCost[0] = cost;
                    ret[0] = fire;
                }
            });
        }
        return ret[0];
    }
}
