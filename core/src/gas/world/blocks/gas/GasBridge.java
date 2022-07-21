package gas.world.blocks.gas;

import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.liquid.*;

public class GasBridge extends LiquidBridge{
    public GasBridge(String name){
        super(name);
    }
    public class GasBridgeBuild extends LiquidBridgeBuild{

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
            return super.acceptLiquid(source, liquid) && (liquid==null || liquid.gas);
        }
    }
}
