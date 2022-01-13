package sunset.ai;

import arc.math.*;
import arc.util.*;
import mindustry.entities.units.*;
import sunset.ai.wrappers.*;
import sunset.gen.*;

public class SegmentAI extends AIController{
    public static FormationAIWrapper wrapper(){
        return new FormationAIWrapper(new SegmentAI());
    }

    @Override
    public void updateMovement(){
        this.unloadPayloads();
        BodySegmentc unit = this.unit.as();
        //check segment
        if(unit.segment() != null && !unit.segment().dead()){
            //get unit
            BodySegmentc next = unit.segment();
            //get dst
            float dst = unit.getDstSegment();
            //calculated pos
            Tmp.v1.trns(Angles.angle(unit.x(), unit.y(), next.x(), next.y()), -unit.getOffset());
            //check dst > offset
            if(dst > unit.getOffset()){
                //move unit
                Tmp.v2.trns(
                Angles.angle(unit.x(), unit.y(), next.x() + Tmp.v1.x, next.y() + Tmp.v1.y),
                unit.speed()
                );
                unit.moveAt(Tmp.v2);
            }
        }
    }
}
