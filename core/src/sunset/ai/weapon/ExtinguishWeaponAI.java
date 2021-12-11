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
import sunset.utils.Utils;

import static sunset.utils.Utils.getBuildingFire;
import static sunset.utils.Utils.isUnitBurning;

public class ExtinguishWeaponAI extends BaseWeaponAI {
    public final static UnitData.DataKey extinguishTick = UnitData.dataKey();
    private static final Vec2 tmpVec = new Vec2();
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
        float range2 = range * range;
        Vec2 point = tmpVec.set(mount.weapon.x + unit.x, mount.weapon.y + unit.y);
        return target = Utils.findFireTarget(unit.x, unit.y, unit.team, range, u -> point.dst2(u) < range2 && u != unit, b -> true);
    }
}
