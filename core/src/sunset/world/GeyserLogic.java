package sunset.world;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.async.*;
import mindustry.core.*;
import mindustry.graphics.*;
import mindustry.io.*;
import mindustry.io.SaveFileReader.*;
import mindustry.world.*;
import mma.*;
import mma.graphics.*;
import sunset.type.*;
import sunset.utils.*;
import sunset.world.blocks.environment.*;

import java.io.*;

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

    public GeyserLogic(){
        Vars.asyncCore.processes.add(this);
//        Events.run(EventType.WorldLoadEvent.class, this::reloadData);
//        Events.run(EventType.Trigger.draw, this::debugDraw);
        ModListener.updaters.add(this::update);
        SaveVersion.addCustomChunk("sunset-geyser-logic",this);
    }

    @Override
    public void init(){
        shouldUpdate = !loadedFromMapSave;
        loadedFromMapSave=false;
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
            if(!(tile.floor() instanceof Geyser) || !visited.add(tile)) return;
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

        updateFactors();
    }

    private void updateFactors(){
        sumfactors = geysers.sumf(GeyserGroup::calculateFactor);
    }


    /**
     * collects a group of geyser tiles using recursion
     */
    private void grabGeysers(Tile tile, ObjectSet<Tile> tiles){
        for(int i = 0; i < 4; i++){
            Tile nearby = tile.nearby(i);
            if(nearby != null && nearby.floor() instanceof Geyser && tiles.add(nearby)){
                grabGeysers(nearby, tiles);
            }
        }
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
        stream.writeInt(geysers.size);
        for(GeyserGroup geyser : geysers){
            geyser.write(Writes.get(stream));
        }
//        UtilsKt.TODO();//Added write block
    }

    @Override
    public void read(DataInput stream) throws IOException{

        int geysersAmount = stream.readInt();
        geysers.setSize(geysersAmount);
        geysers.clear();
        for(int i = 0; i < geysersAmount; i++){
            geysers.add(new GeyserGroup(Reads.get(stream)));
        }
        updateFactors();
        loadedFromMapSave=true;
//        UtilsKt.TODO();//Added read block
    }

    @Override
    public boolean shouldWrite(){
        return geysers.any();
    }
}
