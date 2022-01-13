package sunset.ai;

import arc.func.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.units.*;
import sunset.ai.wrappers.*;
import sunset.gen.*;

public class SegmentAI extends AIController{
    public SegmentAI(AIController fallback){
        this.fallback = fallback;
    }

    public static FormationAIWrapper wrapper(Prov<AIController> fallback){
        return new FormationAIWrapper(new SegmentAI(fallback.get()));
    }

    @Override
    public boolean useFallback(){
        return unit.<Segmentc>as().isHead();
    }

    public float getDstSegment(Segmentc segment){
        var next = segment.next();

        if(next == null || next.dead()) return -100;

        Tmp.v1.trns(segment.angleTo(next), -segment.segmentType().offsetSegment);

        return segment.dst(next.x() + Tmp.v1.x, next.y() + Tmp.v1.y) - (segment.hitSize() + 10);
    }

    @Override
    public void updateMovement(){
        this.unloadPayloads();
        Segmentc unit = this.unit.as();
        //check segment
        //get unit
        Segmentc next = unit.next();
        //get dst
        float dst = getDstSegment(unit);
        //calculated pos
        Tmp.v1.trns(Angles.angle(unit.x(), unit.y(), next.x(), next.y()), -unit.segmentType().offsetSegment);
        //check dst > offset
        if(dst > unit.segmentType().offsetSegment){
            //move unit
            Tmp.v2.trns(
            Angles.angle(unit.x(), unit.y(), next.x() + Tmp.v1.x, next.y() + Tmp.v1.y),
            unit.speed()
            );
            unit.moveAt(Tmp.v2);
        }
    }
}
