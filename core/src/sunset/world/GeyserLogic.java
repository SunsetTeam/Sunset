package sunset.world;

import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.Interval;
import mindustry.Vars;
import mindustry.core.GameState;
import mindustry.game.EventType;
import mindustry.graphics.Layer;
import mindustry.world.Tile;
import mma.ModListener;
import mma.graphics.ADrawf;
import sunset.type.GeyserGroup;
import sunset.world.blocks.environment.Geyser;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class GeyserLogic {
    private static final int effectTime = 20;
    private final Seq<GeyserGroup> geysers = new Seq<>();
    private final ObjectSet<Tile> tmpSet = new ObjectSet<>();
    private final Interval effects = new Interval();
    private float sumfactors;
    private float baseUpChance = 0.00015f;
    private float baseDownChance = 0.0001f;

    public GeyserLogic() {
        Events.run(EventType.WorldLoadEvent.class, this::reloadData);
//        Events.run(EventType.Trigger.draw, this::debugDraw);
        ModListener.updaters.add(this::update);
    }

    private GeyserGroup randomGeyser() {
        float f = Mathf.random(sumfactors);
        for (GeyserGroup geyser : geysers) {
            if (f <= geyser.factor()) {
                return geyser;
            }
            f -= geyser.factor();
        }
        return geysers.random();
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
            if (tmpSet.size>0){
                geysers.add(new GeyserGroup(tmpSet.toSeq()));
            }
        });
        for (GeyserGroup geyser : geysers) {
            geyser.tiles.sort(Tile::pos);
        }
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
            if (nearby != null && nearby.floor() instanceof Geyser && tiles.add(nearby)) {
                grabGeysers(nearby, tiles);
            }
        }
    }

    private void debugDraw() {
        Draw.reset();
        Draw.z(Layer.overlayUI);
        for (int i = 0; i < geysers.size; i++) {
//            renderer.effectBuffer.resize(graphics.getWidth(), graphics.getHeight());
//            renderer.effectBuffer.begin(Color.clear);
            GeyserGroup group = geysers.get(i);
            Draw.color(Color.grays(i / (float) geysers.size), 1f);
            for (Tile tile : group.tiles) {
                Fill.rect(tile.worldx(), tile.worldy(), tilesize, tilesize);
            }
//            renderer.effectBuffer.end();
//            renderer.effectBuffer.blit(Shaders.shield);
            Tile tile = group.tiles.first();
//            Log.info("draw gay ser#@(@)",i,group.tiles.size);
            ADrawf.drawText(tile.worldx(), tile.worldy(), (i + 1) + ": " + group.state());
        }
        Draw.reset();
    }

    private void update() {
        if (geysers.isEmpty() || !Vars.state.is(GameState.State.playing)) return;
        //update states
        if (Mathf.chance(baseUpChance)) {
            randomGeyser().upState();
        } else if (Mathf.chance(baseDownChance)) {
            randomGeyser().downState();
        }
        //apply effects
        if (effects.get(effectTime)) {
            geysers.each(GeyserGroup::effect);
        }
    }


}
