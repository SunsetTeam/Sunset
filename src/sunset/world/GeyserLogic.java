package sunset.world;

import arc.Events;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.game.EventType;
import mindustry.world.Tile;
import sunset.world.blocks.Geyser;

import static mindustry.Vars.world;

public class GeyserLogic {
    public static void init(){
        Events.on(EventType.WorldLoadEvent.class, (e) -> reloadData());
    }
    private static Seq<Seq<Tile>> geysers = new Seq<>(), parts = new Seq<>();
    private static void reloadData() {
        Log.info("Reload!");
        //TODO оптимизировать???
        geysers.clear();
        world.tiles.eachTile(tile -> {
            if (!(tile.floor() instanceof Geyser)) return;
            geysers.each(tiles -> {
                final boolean[] any = {false};
                tiles.each(pred -> {
                    if(!any[0] &&
                        (pred.nearby(0) == tile ||
                        pred.nearby(1) == tile ||
                        pred.nearby(2) == tile ||
                        pred.nearby(3) == tile)) any[0] = true;
                });
                if(any[0]) parts.add(tiles);
            });
            if(parts.isEmpty()) {
                geysers.add(new Seq<>(new Tile[]{tile}));
            } else {
                parts.get(0).add(tile);
                if(parts.size > 1) {
                    for(int i = parts.size-1; i > 0; i--) {
                        parts.get(0).addAll(parts.get(i));
                        geysers.remove(parts.get(i));
                    }
                }
            }
            parts.clear();
        });
        geysers.each(tiles -> {
            Log.info(" -> " + tiles.size);
        });
    }
}
