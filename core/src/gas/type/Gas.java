package gas.type;

import arc.graphics.*;
import mindustry.type.*;

public class Gas extends Liquid{
    public Gas(String name, Color color){
        super(name, color);
        gas=true;
    }

    public Gas(String name){
        super(name);
        gas=true;
    }
}
