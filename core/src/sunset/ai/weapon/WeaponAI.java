package sunset.ai.weapon;

import arc.util.Interval;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import sunset.type.UnitData;

/**
 * AI, которое управляет одним орудием, а не всей боевой единицой.
 *
 * @implNote реализация данного AI должна не только находить цель, но и
 * самостоятельно наводить орудие и проводить проверку на корректность цели!
 */
public abstract class WeaponAI {
    public final static UnitData.DataKey<Interval> timerKey = UnitData.dataKey(null);
    protected int timers = 0;

    protected Interval timer(Unit unit) {
        Interval interval = timerKey.get(unit);
        if (interval == null) {
            interval = new Interval(timers);
            timerKey.set(unit, interval);
        }
        return interval;

    }

    public abstract boolean update(Unit unit, WeaponMount mount);
}
