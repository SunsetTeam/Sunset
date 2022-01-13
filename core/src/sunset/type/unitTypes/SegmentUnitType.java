package sunset.type.unitTypes;

import mindustry.type.*;

public class SegmentUnitType extends SnUnitType{
    public int lengthSnake= 1;
    //body
    public UnitType body= null;
    public UnitType end= null;
    public SegmentUnitType(String name){
        super(name);
    }
}
