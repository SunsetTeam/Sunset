package sunset.type;

import mindustry.gen.Sounds;
import mindustry.type.Weapon;

/** Орудие, которое может тушить постройки. Ничего не переопределяет,
 * просто позволяет ExtinguishAI понять, что это орудие может
 * использоваться для тушения пожаров. */
public class ExtinguishWeapon extends Weapon {
    public ExtinguishWeapon(String name) {
        super(name);
        shootSound = Sounds.wave;
    }
}
