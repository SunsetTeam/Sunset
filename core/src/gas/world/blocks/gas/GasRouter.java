package gas.world.blocks.gas;

import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.liquid.*;

public class GasRouter extends LiquidRouter{
    public GasRouter(String name){
        super(name);
    }
    public class GasRouterBuild extends LiquidRouterBuild{
        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
            return super.acceptLiquid(source, liquid) && (liquid==null || liquid.gas);
        }
    }
}
