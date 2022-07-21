package gas.world.blocks.gas;

import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.liquid.*;

public class GasConduit extends Conduit{
    public GasConduit(String name){
        super(name);
    }
    public class GasConduitBuild extends ConduitBuild{
        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
            return super.acceptLiquid(source, liquid) && (liquid==null || liquid.gas);
        }
    }
}
