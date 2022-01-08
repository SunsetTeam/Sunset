package sunset.ai.weapon;

import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;

/** AI that does nothing with the weapon. It can be useful in specific cases. */
public class EmptyWeaponAI extends WeaponAI {
    @Override
    public boolean update(Unit unit, WeaponMount mount) { return false; }
}
