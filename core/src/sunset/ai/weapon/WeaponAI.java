package sunset.ai.weapon;

import arc.func.Floatc2;
import arc.math.Angles;
import arc.math.geom.Vec2;
import arc.util.Interval;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.type.Weapon;
import sunset.type.UnitData;

/**
 * AI, which controls one weapon, not the entire combat unit.
 *
 * @implNote implementation of a given AI should not only find the target, but also
 * independently aim the gun and check for the correctness of the target!
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
