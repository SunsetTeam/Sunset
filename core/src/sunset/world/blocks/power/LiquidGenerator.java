package sunset.world.blocks.power;

import mindustry.type.*;
import mindustry.world.blocks.power.ItemLiquidGenerator;


public class LiquidGenerator extends ItemLiquidGenerator{

    public LiquidGenerator(String name){
        super(name);
        defaults = true;
    }
    @Override
    protected float getLiquidEfficiency(Liquid liquid){
        return 1f;
    }
}