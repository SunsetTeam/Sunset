package sunset.core;

import arc.ApplicationListener;
import sunset.world.GeyserLogic;

public class SnLogic implements ApplicationListener {
    public final GeyserLogic geyserLogic;

    public SnLogic() {
        geyserLogic = new GeyserLogic();
    }
}
