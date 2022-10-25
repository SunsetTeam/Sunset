package sunset.world.blocks.laser;

import arc.*;
import arc.struct.*;
import arc.struct.ObjectMap.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.async.*;
import mindustry.game.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.io.*;
import mindustry.io.SaveFileReader.*;
import mindustry.world.*;
import mindustry.world.consumers.*;
import mma.*;
import org.jetbrains.annotations.*;
import org.jetbrains.annotations.Nullable;

import java.io.*;

/** Механика в разработке. Суть состоит в том, чтобы каждому блоку, который имеет этот ConsumeLaser, в HybridLaserBlockLogic назначить LaserModule, и, таким образом, сделать их де-факто лазерными блоками без LaserBuild, а далее обращаться к ним по мере надобности. */
@SuppressWarnings("ALL")
public class HybridLaserBlockLogic implements AsyncProcess{
    public LaserModule[] hybridBuildings = new LaserModule[0];
    //    public ObjectMap<Building, LaserModule> hybridBuildings = new ObjectMap<>();
    private ConsumeLaser[] blocksWithLasers = new ConsumeLaser[0];

    public HybridLaserBlockLogic(){
        Runnable blockloader = () -> {

            Seq<Block> blocks = Vars.content.blocks();
            blocksWithLasers=new ConsumeLaser[blocks.size];
            for(Block block : blocks){
                for(Consume consume : block.consumers){
                    if(consume instanceof ConsumeLaser c){
                        blocksWithLasers[block.id]=c;
                        break;
                    }
                }
            }
        };
        Events.run(ClientLoadEvent.class, blockloader);
        Events.run(ServerLoadEvent.class, blockloader);

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

    private void addBlock(@Nullable Building b){
        if(b == null || blocksWithLasers[b.block().id]==null)
            return;

        hybridBuildings[b.tile.array()] = new LaserModule(b, blocksWithLasers[b.block().id]);

    }

    private void removeBlock(@NotNull Building b){
        hybridBuildings[b.tile.array()] = null;
    }

    public LaserModule laserModule(Building b){
        if (b==null)return null;
        return hybridBuildings[b.tile.array()];
    }
}
