package gas.world.blocks.sandbox;

import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.sandbox.*;

public class GasVoid extends LiquidVoid{
    public GasVoid(String name){
        super(name);
    }

    public class GasVoidBuild extends LiquidVoidBuild{
        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
            return super.acceptLiquid(source, liquid) && (liquid==null || liquid.gas);
        }

        @Override
        public void handleLiquid(Building source, Liquid liquid, float amount){
            if(liquid == null || !liquid.gas) return;
            super.handleLiquid(source, liquid, amount);
        }
    }
}
