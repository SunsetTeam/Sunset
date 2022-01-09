package sunset.type.weapons;

import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.type.Weapon;
import mma.ModVars;

/** needs for remove modPrefix when you create a weapon*/
public class SnWeapon extends Weapon {
    public SnWeapon(String name) {
        super(ModVars.fullName(name));
    }

    public SnWeapon() {
        super();
    }
}
