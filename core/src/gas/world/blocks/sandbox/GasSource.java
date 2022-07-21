package gas.world.blocks.sandbox;

import arc.scene.ui.layout.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.sandbox.*;

import static mindustry.Vars.content;

public class GasSource extends LiquidSource{
    public GasSource(String name){
        super(name);
    }

    public class GasSourceBuild extends LiquidSourceBuild{
        @Override
        public void updateTile(){
            if(source != null && !source.gas) source = null;
            if(source == null){
                liquids.clear();
            }else{
                liquids.add(source, liquidCapacity);
                dumpLiquid(source);
            }
        }

        @Override
        public void buildConfiguration(Table table){
            ItemSelection.buildTable(GasSource.this, table, content.liquids().select(it -> it.gas), () -> source, this::configure);
        }
    }
}
