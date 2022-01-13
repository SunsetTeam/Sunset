package sunset.type.unitTypes;

import arc.util.*;
import mindustry.type.*;

public class SegmentUnitType extends SnUnitType{
    public int lengthSnake = 1;
    //body
    public UnitType body = null;
    public UnitType end = null;
    public float offsetSegment;
    public float segmentBuildTime = 10 * Time.toSeconds;

    public SegmentUnitType(String name){
        super(name);
    }

    public enum SegmentType{
        head, body, tail;


        public boolean head(){
            return this == head;
        }

        public boolean body(){
            return this == body;
        }

        public boolean tail(){
            return this == tail;
        }
    }
}
