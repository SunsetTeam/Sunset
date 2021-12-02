package sunset.type.weapons;

import arc.graphics.g2d.Draw;
import mindustry.graphics.Layer;
import mindustry.type.Weapon;

public class CopterWeapon extends WeaponExt {
    public CopterWeapon(String name) {
        super(name);
        
    }
    
    @Override
    public void draw(Weapon weapon){
        Draw.z(Layer.turret);
        super.draw(weapon);
    }
}
