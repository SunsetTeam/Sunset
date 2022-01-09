package sunset.type.weapons;

import mindustry.entities.units.*;
import mindustry.gen.*;

public abstract class CustomWeapon extends SnWeapon{
    public CustomWeapon(String name){
        super(name);
    }

    public CustomWeapon(){
    }


    public void preDraw(WeaponMount mount, Unit unit) { }

    public void postDraw(WeaponMount mount, Unit unit) { }

    public boolean customDraw() {
        return false;
    }
}
