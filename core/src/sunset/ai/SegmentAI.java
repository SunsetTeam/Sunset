package sunset.ai;

import arc.func.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.entities.units.*;
import sunset.ai.wrappers.*;
import sunset.gen.*;

public class SegmentAI extends AIController{
    private static Vec2 tmp = new Vec2();

    public SegmentAI(AIController fallback){
        this.fallback = fallback;
    }

    public static CommandAIWrapper wrapper(AIController fallback){
        return new CommandAIWrapper(new SegmentAI(fallback));
    }

    public static Prov<CommandAIWrapper> wrapper(Prov<AIController> fallback){
        return () -> new CommandAIWrapper(new SegmentAI(fallback.get()));
    }

    @Override
    public boolean useFallback(){
        return unit.<Segmentc>as().isHead();
    }

    @Override
    public void updateMovement(){
        this.unloadPayloads();
        Segmentc unit = this.unit.as();
        //check segment
        //get unit
        Segmentc previous = unit.previous();
        //calculated pos
        previous.calculateNextPosition(tmp);
//        previous.nextPosition(tmp,);
        //check dst > offset
        if(!Mathf.zero(tmp.dst(unit),0.1f)){
            //move unit
//            tmp.sub(unit).limit(unit.speed()* Time.delta).add(unit);
//            unit.set(tmp);
            moveTo(tmp, 0.1f, 1f);
        }
    }
}
