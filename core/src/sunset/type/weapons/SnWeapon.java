package sunset.type.weapons;

import mindustry.type.Weapon;
import mma.ModVars;

/** Needs for remove modPrefix when you create a weapon. */
public class SnWeapon extends Weapon {
    public SnWeapon(String name) {
        super(ModVars.fullName(name));
    }
    public SnWeapon() {
        super();
    }
}
