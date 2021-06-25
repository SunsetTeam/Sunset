package sunset.type;

import mindustry.gen.Sounds;
import mindustry.type.Weapon;

/** Орудие, которое стреляет жидкостью. Ничего не переопределяет,
 * просто позволяет ExtinguishAI понять, что это орудие может
 * использоваться для тушения пожаров. */
public class LiquidWeapon extends Weapon {
    public LiquidWeapon(String name) {
        super(name);
        shootSound = Sounds.wave;
    }
}
