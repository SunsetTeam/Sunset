package sunset.ai.weapon;

import arc.math.geom.Vec2;
import arc.util.Tmp;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Posc;
import mindustry.gen.Unit;
import sunset.utils.Utils;

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
        float range = mount.weapon.bullet.range();
        float range2 = range * range;
        Vec2 point = tmpVec.set(mount.weapon.x + unit.x, mount.weapon.y + unit.y);
        return target = Utils.findFireTarget(unit.x, unit.y, unit.team, range, u -> point.dst2(u) < range2 && u != unit, b -> true);
    }
}
