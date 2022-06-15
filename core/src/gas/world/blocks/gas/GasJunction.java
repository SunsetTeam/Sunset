package gas.world.blocks.gas;

import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.liquid.*;

public class GasJunction extends LiquidJunction{
    public GasJunction(String name){
        super(name);
    }
    public class GasJunctionBuild extends LiquidJunctionBuild{
        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
            return super.acceptLiquid(source, liquid) && (liquid==null || liquid.gas);
        }

        @Override
        public Building getLiquidDestination(Building source, Liquid liquid){
            if (!liquid.gas)return this;
            return super.getLiquidDestination(source, liquid);
        }
    }
}
