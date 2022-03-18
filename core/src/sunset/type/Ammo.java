package sunset.type;

import mindustry.type.Item;

public class Ammo extends Item {

    public Ammo(String name) {
        super(name);
    }
    @Override
    public boolean isHidden() {
        return true;
    }
}
