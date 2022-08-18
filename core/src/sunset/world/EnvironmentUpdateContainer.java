package sunset.world;

import arc.*;
import mindustry.*;
import mindustry.async.*;
import mindustry.game.EventType.*;
import mindustry.world.*;
import mma.*;
import org.jetbrains.annotations.*;
import sunset.world.type.*;

public class EnvironmentUpdateContainer implements AsyncProcess{
    private EnvironmentUpdateContainer(){

    }

    static Object[] dataArray = new Object[0];
    static UpdateEnvironment[] classArray = new UpdateEnvironment[0];

    public static void load(){
        Vars.asyncCore.processes.add(new EnvironmentUpdateContainer());
        Events.on(TileChangeEvent.class, e -> {
            Tile tile = e.tile;
            Block block = tile.block();
            int index = tile.array();
            if(classArray[index] != block) return;
            if(!(block instanceof UpdateEnvironment<?> environment)){
                dataArray[index] = null;
                classArray[index] = null;
                return;
            }
            dataArray[index] = environment.getDefaultValue();
            classArray[index] = environment;
        });
        Events.run(Trigger.update, EnvironmentUpdateContainer::update);
    }

    @SuppressWarnings("unchecked")
    private static void update(){
        for(int i = 0; i < classArray.length; i++){
            if(classArray[i] == null) continue;
            Tile tile = Vars.world.tiles.geti(i);
            classArray[i].updateTile(tile, dataArray[i]);
        }
    }

    @Override
    public void init(){
        Tiles tiles = Vars.world.tiles;
        dataArray = new Object[tiles.width * tiles.height];
        classArray = new UpdateEnvironment[tiles.width * tiles.height];
        tiles.eachTile(tile -> {
            int array = tile.array();
            //noinspection ConstantConditions
            classArray[array] = tile.block() instanceof UpdateEnvironment<?> updateEnvironment ? updateEnvironment : null;
            if (classArray[array]!=null){
                dataArray[array]=classArray[array].getDefaultValue();
            }
        });

    }

    @Override
    public void reset(){
        dataArray = new Object[0];
        classArray = new UpdateEnvironment[0];
    }
}
