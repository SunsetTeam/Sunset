package sunset.world;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.struct.Queue;
import arc.util.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.async.*;
import mindustry.core.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.io.SaveFileReader.*;
import mindustry.io.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mma.*;
import mma.graphics.*;
import sunset.type.*;
import sunset.world.blocks.environment.*;

import java.io.*;
import java.util.*;

import static mindustry.Vars.*;

@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
public class GeyserLogic implements AsyncProcess, CustomChunk{
    private static final int effectTime = 20;
    private final Seq<GeyserGroup> geysers = new Seq<>();
    private final ObjectSet<Tile> tmpSet = new ObjectSet<>();
    private final Interval effects = new Interval();
    private final float baseUpChance = 0.00015f;
    private final float baseDownChance = 0.0001f;
    private float sumfactors;
    private boolean shouldUpdate = false;
    private boolean loadedFromMapSave = false;
    private GeyserGroup[] tileToGroup;
    private boolean[] shouldDrawTile;

    public GeyserLogic(){
        Vars.asyncCore.processes.add(this);
//        Events.run(EventType.WorldLoadEvent.class, this::reloadData);
//        Events.run(EventType.Trigger.draw, this::debugDraw);
        ModListener.updaters.add(this::update);
        SaveVersion.addCustomChunk("sunset-geyser-logic", this);
    }

    @Override
    public void init(){
        shouldUpdate = !loadedFromMapSave;
        loadedFromMapSave = false;
    }

    @Override
    public void reset(){
        geysers.clear();
    }

    @Override
    public void process(){
        if(shouldUpdate){
            reloadData();
            shouldUpdate = false;
        }
    }

    private GeyserGroup randomGeyser(){
        float f = Mathf.random(sumfactors);
        for(GeyserGroup geyser : geysers){
            if(f <= geyser.factor()){
                return geyser;
            }
            f -= geyser.factor();
        }
        return geysers.random();
    }

    private void reloadData(){
        //TODO оптимизировать???
        geysers.clear();
        tmpSet.clear();
        ObjectSet<Tile> visited = new ObjectSet<>();
        world.tiles.eachTile(tile -> {
            if(!(getBlockRaw(tile) instanceof Geyser) || !visited.add(tile)) return;
            tmpSet.clear();
            grabGeysers(tile, tmpSet);
            visited.addAll(tmpSet);
            if(tmpSet.size > 0){
                geysers.add(new GeyserGroup(tmpSet.toSeq()));
            }
        });
        for(GeyserGroup geyser : geysers){
            geyser.tiles.sort(Tile::pos);
        }
        //update factors

        postReload();
    }

    private void postReload(){

        tileToGroup = null;
        shouldDrawTile=null;

        sumfactors = geysers.sumf(GeyserGroup::calculateFactor);
        tileToGroup = new GeyserGroup[world.width() * world.height()];
        shouldDrawTile = new boolean[world.width() * world.height()];
        Arrays.fill(shouldDrawTile,false);
        for(GeyserGroup geyser : geysers){
            for(Tile tile : geyser.tiles){
                for(int size = getBlock(tile).drawSize; size > 1; size--){
                    for(Point2 edge : Edges.getInsideEdges(size)){
                        tileToGroup[tile.nearby(edge).array()] = geyser;
                    }

                }
                shouldDrawTile[tile.array()]=true;
            }
        }
    }
public boolean shouldDrawTile(Tile tile){
    if(shouldUpdate || shouldDrawTile==null) return false;
        return shouldDrawTile[tile.array()];
}
    @Nullable
    @org.jetbrains.annotations.Nullable
    public GeyserGroup geyserGroupFor(Tile tile){
        if(shouldUpdate || tileToGroup==null) return null;
        return tileToGroup[tile.array()];
    }

    /**
     * collects a group of geyser tiles using recursion
     */
    private void grabGeysers(Tile tile, ObjectSet<Tile> tiles){
//        chained = new Seq<>();
        Queue<Tile> doorQueue = new Queue<>();
        doorQueue.clear();
        doorQueue.add(tile);

        Geyser overlay = getBlock(tile);

        while(!doorQueue.isEmpty()){
            var next = doorQueue.removeLast();

            for(Point2 edge : Edges.getEdges(overlay.drawSize)){
                Tile nearby = next.nearby(edge);
                if(nearby != null && getBlockRaw(nearby) == overlay && tiles.add(nearby)){
                    doorQueue.addFirst(nearby);
                }
            }
        }
    }

    private Geyser getBlock(Tile tile){
        return (Geyser)getBlockRaw(tile);
    }
    private Block getBlockRaw(Tile tile){
        return tile.overlay();
    }

    private void debugDraw(){
        Draw.reset();
        Draw.z(Layer.overlayUI);
        for(int i = 0; i < geysers.size; i++){
//            renderer.effectBuffer.resize(graphics.getWidth(), graphics.getHeight());
//            renderer.effectBuffer.begin(Color.clear);
            GeyserGroup group = geysers.get(i);
            Draw.color(Color.grays(i / (float)geysers.size), 1f);
            for(Tile tile : group.tiles){
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

    private void update(){
        if(shouldUpdate) return;
        if(geysers.isEmpty() || !Vars.state.is(GameState.State.playing)) return;
        //update states
        if(Mathf.chance(baseUpChance)){
            randomGeyser().upState();
        }else if(Mathf.chance(baseDownChance)){
            randomGeyser().downState();
        }
        //apply effects
        if(effects.get(effectTime)){
            geysers.each(GeyserGroup::effect);
        }
    }


    @Override
    public void write(DataOutput stream) throws IOException{
        Log.info("GeyserLogic.write(DataOutput)");
        stream.writeInt(geysers.size);
        Log.info("|-size: @" + geysers.size);
        for(GeyserGroup geyser : geysers){
            geyser.write(Writes.get(stream));

            Log.info("|-geyser", "");
            Log.info("| |-geyser.size: @", geyser.tiles.size);
            Log.info("| |-geyser.factor: @", geyser.factor());
            Log.info("| \\-geyser.state: @", geyser.state());
        }
//        UtilsKt.TODO();//Added write block
    }

    @Override
    public void read(DataInput stream) throws IOException{
        Log.info("GeyserLogic.read(DataOutput)");
        int geysersAmount = stream.readInt();
        geysers.setSize(geysersAmount);
        geysers.clear();
        Log.info("|-size: @" + geysersAmount);
        for(int i = 0; i < geysersAmount; i++){
            GeyserGroup group = new GeyserGroup(Reads.get(stream));
            geysers.add(group);
            Log.info("|-geyser", "");
            Log.info("| |-geyser.size: @", group.tiles.size);
            Log.info("| |-geyser.factor: @", group.factor());
            Log.info("| \\-geyser.state: @", group.state());
        }
        postReload();
        loadedFromMapSave = true;
//        UtilsKt.TODO();//Added read block
    }

    @Override
    public boolean shouldWrite(){
        return geysers.any();
    }
}
