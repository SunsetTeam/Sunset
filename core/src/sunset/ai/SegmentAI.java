package sunset.ai;

import arc.func.*;
import arc.util.*;
import mindustry.entities.units.*;
import sunset.ai.wrappers.*;
import sunset.gen.*;

public class SegmentAI extends AIController{
    public SegmentAI(AIController fallback){
        this.fallback = fallback;
    }

    public static FormationAIWrapper wrapper(AIController fallback){
        return new FormationAIWrapper(new SegmentAI(fallback));
    }

    public static Prov<FormationAIWrapper> wrapper(Prov<AIController> fallback){
        return () -> new FormationAIWrapper(new SegmentAI(fallback.get()));
    }

    @Override
    public boolean useFallback(){
        return unit.<Segmentc>as().isHead();
    }

    public float getDstSegment(Segmentc segment){
        var next = segment.previous();
        Tmp.v1.trns(segment.angleTo(next), -segment.segmentType().offsetSegment);

        return segment.dst(next.x() + Tmp.v1.x, next.y() + Tmp.v1.y) - (segment.hitSize() + 10);
    }

    @Override
    public void updateMovement(){
        this.unloadPayloads();
        Segmentc unit = this.unit.as();
        //check segment
        //get unit
        Segmentc previous = unit.previous();
        //get dst
        float dst = getDstSegment(unit);
        //calculated pos
        previous.calculateNextPosition(Tmp.v1);
        //check dst > offset
        if(dst > unit.segmentType().offsetSegment){
            //move unit
//            Tmp.v2.trns(unit.angleTo(Tmp.v1), unit.speed());
//            unit.moveAt(Tmp.v2);
            moveTo(Tmp.v1,unit.segmentType().offsetSegment,0);
        }
    }
}
