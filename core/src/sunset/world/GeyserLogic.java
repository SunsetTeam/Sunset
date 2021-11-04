package sunset.world;

import arc.Events;
import arc.math.Mathf;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.Interval;
import mindustry.Vars;
import mindustry.core.GameState;
import mindustry.game.EventType;
import mindustry.world.Tile;
import mma.ModListener;
import sunset.type.GeyserGroup;
import sunset.world.blocks.environment.Geyser;

import static mindustry.Vars.world;

public class GeyserLogic {
    private static final int effectTime = 30;
    private final Seq<GeyserGroup> geysers = new Seq<>();
    private final ObjectSet<Tile> tmpSet = new ObjectSet<>();
    private final Interval effects = new Interval();
    private float sumfactors;
    private float baseUpChance = 0.00015f;
    private float baseDownChance = 0.0001f;

    public GeyserLogic() {
        Events.run(EventType.WorldLoadEvent.class, this::reloadData);
        ModListener.updaters.add(this::update);
    }

    private int randomGeyser() {
        float f = Mathf.random(sumfactors);
        int r = 0;
        while (r < geysers.size && f > geysers.get(r).factor()) {
            f -= geysers.get(r).factor();
            r++;
        }
        return r;
    }

    private void reloadData() {
        //TODO оптимизировать???
        geysers.clear();
        tmpSet.clear();
        ObjectSet<Tile> visited = new ObjectSet<>();
        world.tiles.eachTile(tile -> {
            if (!(tile.floor() instanceof Geyser) || !visited.add(tile)) return;
            tmpSet.clear();
            grabGeysers(tile, tmpSet);
            visited.addAll(tmpSet);
            geysers.add(new GeyserGroup(tmpSet.asArray()));
        });
        //update factors

        updateFactors();
    }

    private void updateFactors() {
        sumfactors = geysers.sumf(GeyserGroup::calculateFactor);
    }


    /**
     * collects a group of geyser tiles using recursion
     */
    private void grabGeysers(Tile tile, ObjectSet<Tile> tiles) {
        for (int i = 0; i < 4; i++) {
            Tile nearby = tile.nearby(i);
            if (nearby.floor() instanceof Geyser && tiles.add(nearby)) {
                grabGeysers(nearby, tiles);
            }
        }
    }

    private void update() {
        if (geysers.isEmpty() || !Vars.state.is(GameState.State.playing)) return;
        //update states
        if (Mathf.chance(baseUpChance)) {
            geysers.get(randomGeyser()).upState();
        } else if (Mathf.chance(baseDownChance)) {
            geysers.get(randomGeyser()).downState();
        }
        //apply effects
        if (effects.get(effectTime)) {
            geysers.each(GeyserGroup::effect);
        }
    }


}
