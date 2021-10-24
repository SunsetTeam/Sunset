package sunset.type;

import mindustry.gen.EntityMapping;
import mindustry.type.UnitType;

public class SnUnitType extends UnitType {
    public SnUnitType(String name) {
        super(name);
        if (constructor==null) {
            constructor= EntityMapping.map(name);
        }
    }
}
