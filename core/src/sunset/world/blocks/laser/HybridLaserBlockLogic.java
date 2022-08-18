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
public class HybridLaserBlockLogic implements AsyncProcess{
    public LaserModule[] hybridBuildings = new LaserModule[0];
//    public ObjectMap<Building, LaserModule> hybridBuildings = new ObjectMap<>();

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
                hybridBuildings[b.tile.array()] = new LaserModule(b, c);
                break;
            }
        }
    }

    private void removeBlock(Building b){
        hybridBuildings[b.tile.array()] = null;
    }

    @Override
    public void init(){
        hybridBuildings = new LaserModule[Vars.world.width() * Vars.world.height()];
        Groups.build.each(b -> addBlock(b));
    }

    @Override
    public void reset(){
        hybridBuildings = new LaserModule[0];
    }

    public void update(){
        for(int i = 0; i < hybridBuildings.length; i++){
            if(hybridBuildings[i] != null){
                hybridBuildings[i].hybridBuildUpdate();
            }
        }
    }

    @Override
    public void end(){
        for(int i = 0; i < hybridBuildings.length; i++){
            if(hybridBuildings[i] != null){
                LaserModule lm = hybridBuildings[i];
                lm.leftInput = false;
                lm.rightInput = false;
                lm.topInput = false;
                lm.downInput = false;
            }
        }
    }
}
