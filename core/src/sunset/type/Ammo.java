package sunset.type;

import mindustry.graphics.Pal;
import mindustry.type.Item;

public class Ammo extends Item {

    public Ammo(String name) {
        super(name);
        color = Pal.gray;
    }
    @Override
    public boolean isHidden() {
        return true;
    }
}
