package sunset.ai;

import mindustry.*;
import mindustry.ai.types.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;
import sunset.ai.wrappers.*;
import sunset.type.*;
import sunset.world.blocks.units.*;

public class DeliverAI extends FlyingAI{
    private DeliverAI(){

    }

    @Override
    public AIController fallback(){
        return new HealAI();
    }

    // 0 - move to "base"
    // 1 - pick resources from "base"
    // 2 - move to "base.linked"
    // 1 - pick resources from "base.linked"

    @Override
    public void updateMovement(){

    }

    private void state(DeliverState state){
    }
    public static UnitController wrapper(){
        return new CommandAIWrapper(new DeliverAI());
    }
}