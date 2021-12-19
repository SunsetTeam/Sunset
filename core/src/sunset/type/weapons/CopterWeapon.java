package sunset.type.weapons;

import arc.graphics.g2d.Draw;
import mindustry.entities.units.WeaponMount;
import mindustry.graphics.Layer;
import mindustry.type.Weapon;

public class CopterWeapon extends Weapon {
    public CopterWeapon(String name) {
        super(name);
        
    }
    public void draw(WeaponMount weapon){
        Draw.z(Layer.debris);
        super.draw(null, weapon);
    }

}


