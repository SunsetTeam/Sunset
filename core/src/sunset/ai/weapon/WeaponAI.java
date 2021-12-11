package sunset.ai.weapon;

import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;

/**
 * AI, которое управляет одним орудием, а не всей боевой единицой.
 * @implNote реализация данного AI должна не только находить цель, но и
 * самостоятельно наводить орудие и проводить проверку на корректность цели!
 * */
public abstract class WeaponAI {
    public abstract boolean update(Unit unit, WeaponMount mount);
}
