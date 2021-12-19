package sunset.type.weapons;

import arc.graphics.g2d.Draw;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;

public class CopterWeapon extends WeaponExt {
    public CopterWeapon(String name) {
        super(name);
        
    }
    @Override
    public void draw(Unit unit, WeaponMount mount){
        Draw.z(Layer.groundUnit);
        super.draw(unit, mount);
    }

}


