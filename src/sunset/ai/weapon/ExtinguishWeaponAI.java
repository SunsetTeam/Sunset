package sunset.ai.weapon;

import arc.math.Mathf;
import arc.math.geom.Position;
import mindustry.Vars;
import mindustry.entities.Units;
import mindustry.gen.*;

import static sunset.Utils.*;

public class ExtinguishWeaponAI extends BaseWeaponAI {
    @Override
    public void update() {
        Posc t = findTarget();
        if(t == null) {
            super.update();
            return;
        }
        Position target = new Position() {
            @Override
            public float getX() { return t.getX(); }
            @Override
            public float getY() { return t.getY(); }
        };
        aim(target);
    }

    private Posc findTarget() {
        final Posc[] ret = new Posc[] { null };
        // Ищем горящих юнитов
        ret[0] = Units.closest(unit.team, weaponX(), weaponY(),
                range(), un -> un != unit && isUnitBurning(un),
                (unit1, xx, yy) -> Mathf.pow(unit1.health / unit1.maxHealth, 2f));
        if(ret[0] != null) return ret[0];
        // Ищем горящие постройки
        float[] cost = new float[] { Float.MAX_VALUE };
        Vars.indexer.eachBlock(unit.team, unit.x, unit.y, range(), bld -> ret[0] == null, building -> {
            Fire fire = getBuildingFire(building);
            if(fire == null) return;
            float cs = Mathf.pow(building.health / building.maxHealth, 2f)
                    * Mathf.dst(unit.x, unit.y, building.x, building.y);
            if(cs < cost[0]) {
                cost[0] = cs;
                ret[0] = fire;
            }
        });
        if(ret[0] != null) return ret[0];
        return null;
    }
}
