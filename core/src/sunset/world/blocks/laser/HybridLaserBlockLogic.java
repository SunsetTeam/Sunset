package sunset.world.blocks.laser;

import arc.*;
import arc.struct.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.async.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.io.*;
import mindustry.io.SaveFileReader.*;
import mindustry.world.*;
import mma.*;

import java.io.*;

/** Механика в разработке. Суть состоит в том, чтобы каждому блоку, который имеет этот ConsumeLaser, в HybridLaserBlockLogic назначить LaserModule, и, таким образом, сделать их де-факто лазерными блоками без LaserBuild, а далее обращаться к ним по мере надобности. */
public class HybridLaserBlockLogic implements CustomChunk, AsyncProcess{
    public ObjectMap<Building, LaserModule> hybridBuildings = new ObjectMap<>();
    public boolean saved = false;
    public HybridLaserBlockLogic(){
        ModListener.updaters.add(this::update);
        Vars.asyncCore.processes.add(this);
        SaveVersion.addCustomChunk("sunset-hybrid-laser-blocks", this);
        Events.on(EventType.BlockBuildEndEvent.class, e -> {
            addBlock(e.tile.build);
        });
        Events.on(EventType.BlockDestroyEvent.class, e-> {
            removeBlock(e.tile.build);
        });
    }

    private void addBlock(Building b){
        if(b == null)
            return;
        for(int i = 0; i < b.block().consumers.length; i++){
            if(b.block().consumers[i] instanceof ConsumeLaser c){
                hybridBuildings.put(b, new LaserModule(b, c));
                break;
            }
        }
    }

    private void removeBlock(Building b){
        hybridBuildings.remove(b);
    }

    private void loadBlocks(){
        hybridBuildings.clear();
        Groups.build.each(b -> addBlock(b));
    }

    @Override
    public void init(){
        if(!saved){
            loadBlocks();
        }
    }

    public void update(){
        for(ObjectMap.Entry e : hybridBuildings.entries()){
            ((LaserModule) e.value).hybridBuildUpdate();
        }
    }

    @Override
    public void end(){
        for(ObjectMap.Entry e : hybridBuildings.entries()){
            LaserModule lm = (LaserModule) e.value;
            lm.leftInput = false;
            lm.rightInput = false;
            lm.topInput = false;
            lm.downInput = false;
        }
    }
    @Override
    public void write(DataOutput stream) throws IOException{
        Writes w = Writes.get(stream);
        w.i(hybridBuildings.size);
        for(ObjectMap.Entry e : hybridBuildings.entries()){
            Building b = (Building) e.key;
            w.i(b.tile.pos());
        }
    }
    @Override
    public void read(DataInput stream){
        Reads r = Reads.get(stream);
        int n = r.i();
        for(int i = 0; i < n; i++){
            Tile t = Vars.world.tile(r.i());
            Building b = t.build;
            for(int k = 0; k < b.block().consumers.length; k++){
                if(b.block().consumers[k] instanceof ConsumeLaser c){
                    hybridBuildings.put(b, new LaserModule(b, c));
                    break;
                }
            }
        }
        saved = true;
    }
}
