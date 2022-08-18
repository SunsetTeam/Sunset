package sunset.core;

import arc.ApplicationListener;
import arc.Events;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.BlockUnitc;
import sunset.world.GeyserLogic;
import sunset.world.blocks.defense.turrets.MissileSileBuild;
import sunset.world.blocks.laser.*;

public class SnLogic implements ApplicationListener {
    public final GeyserLogic geyserLogic;
    public final HybridLaserBlockLogic hybridLaserBlockLogic;

    public SnLogic() {
        geyserLogic = new GeyserLogic();
        hybridLaserBlockLogic = new HybridLaserBlockLogic();

        Events.run(EventType.Trigger.draw, () -> {
            if (Vars.player.unit() instanceof BlockUnitc b && b.tile() instanceof MissileSileBuild build) {
                build.drawControlled();
            }
        });
        Events.on(EventType.TapEvent.class, e -> {
            if (e.player != null && e.player.unit() instanceof BlockUnitc b && b.tile() instanceof MissileSileBuild build) {
                build.tapTile(e.player, e.tile);
            }
        });
    }
}
