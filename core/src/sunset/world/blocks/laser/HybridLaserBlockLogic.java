package sunset.world.blocks.laser;

import arc.*;
import arc.struct.*;
import arc.struct.ObjectMap.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.async.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.io.*;
import mindustry.io.SaveFileReader.*;
import mindustry.world.consumers.*;
import mma.*;
import org.jetbrains.annotations.*;

import java.io.*;

/** Механика в разработке. Суть состоит в том, чтобы каждому блоку, который имеет этот ConsumeLaser, в HybridLaserBlockLogic назначить LaserModule, и, таким образом, сделать их де-факто лазерными блоками без LaserBuild, а далее обращаться к ним по мере надобности. */
@SuppressWarnings("ALL")
public class HybridLaserBlockLogic implements  AsyncProcess{
    public ObjectMap<Building, LaserModule> hybridBuildings = new ObjectMap<>();
    public boolean wasSaved = false;

    public HybridLaserBlockLogic(){
        ModListener.updaters.add(this::update);
        Vars.asyncCore.processes.add(this);
//        SaveVersion.addCustomChunk("sunset-hybrid-laser-blocks", this);
        Events.on(EventType.BlockBuildEndEvent.class, e -> {
            addBlock(e.tile.build);
        });
        Events.on(EventType.BlockDestroyEvent.class, e -> {
            removeBlock(e.tile.build);
        });
    }

    private void addBlock(@Nullable Building b){
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

    @Override
    public void init(){
        hybridBuildings.clear();
        Groups.build.each(b -> addBlock(b));
    }

    @Override
    public void reset(){
        hybridBuildings.clear();
    }

    public void update(){
        for(Entry<Building, LaserModule> e : hybridBuildings){
            e.value.hybridBuildUpdate();
        }
    }

    @Override
    public void end(){
        for(ObjectMap.Entry<Building, LaserModule> e : hybridBuildings){
            LaserModule lm = e.value;
            lm.leftInput = false;
            lm.rightInput = false;
            lm.topInput = false;
            lm.downInput = false;
        }
    }

    /*@Override
    public void write(DataOutput stream) throws IOException{
        Writes w = Writes.get(stream);
        w.i(hybridBuildings.size);
        for(ObjectMap.Entry<Building,LaserModule> e : hybridBuildings){
            w.i(e.key.pos());
        }
    }


    @Override
    public void read(DataInput stream){
        Reads r = Reads.get(stream);
        int buildingAmount = r.i();
        for(int i = 0; i < buildingAmount; i++){
            addBlock(Vars.world.build(r.i()));
        }
        wasSaved = true;
    }*/
}
