package sunset.type;

import mindustry.type.Weapon;
import sunset.ai.weapon.BaseWeaponAI;
import sunset.ai.weapon.WeaponAI;

/** Орудие с собственным AI. */
public class AutoWeapon extends Weapon {
    public WeaponAI ai = new BaseWeaponAI();

    public AutoWeapon(String name) {
        super(name);
    }
}
