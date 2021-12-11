package sunset.ai.weapon;

import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;

/** AI, которое ничего не делает с орудием. Может быть полезно в специфичных случаях. */
public class EmptyWeaponAI extends WeaponAI {
    @Override
    public boolean update(Unit unit, WeaponMount mount) { return false; }
}
