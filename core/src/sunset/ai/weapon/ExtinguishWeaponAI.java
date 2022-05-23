package sunset.ai.weapon;

import arc.math.geom.Vec2;
import arc.util.Tmp;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Posc;
import mindustry.gen.Unit;
import sunset.utils.Utils;

import static sunset.utils.Utils.mountX;
import static sunset.utils.Utils.mountY;

public class ExtinguishWeaponAI extends BaseWeaponAI {
    private static final Vec2 tmpVec = new Vec2();
    private static int ticks = 15;
    public final int tickTimer=timers++;
    Posc target;

    @Override
    public boolean update(Unit unit, WeaponMount mount) {
        if (timer(unit).get(tickTimer,ticks)) {
            target = findTarget(unit, mount);
        }
        if (target != null) {
            aim(Tmp.v1.set(target), unit, mount);
            return true;
        }
        aim(null, unit, mount);
        return false;
    }

    private Posc findTarget(Unit unit, WeaponMount mount) {
        float range = mount.weapon.bullet.range;
        float range2 = range * range;
        Vec2 point = tmpVec.set(mountX(unit,mount), mountY(unit,mount));
        return target = Utils.findFireTarget(unit.x, unit.y, unit.team, range, u -> point.dst2(u) < range2 && u != unit, b -> true);
    }
}
