package sunset.type.weapons;

import mindustry.type.*;

/** Needs for remove modPrefix when you create a weapon. */
public class SnWeapon extends Weapon{
    public SnWeapon(String name){
        super(("sunset-" + name));
    }

    public SnWeapon(){
        super();
    }
}
