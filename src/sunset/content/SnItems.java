package sunset.content;

import arc.graphics.*;
import mindustry.ctype.*;
import mindustry.type.*;

public class SnItems implements ContentList{
    public static Item
    
    naturit, nobium, flameid, fors, enojie, planatrium;

    @Override
    public void load(){

        fors = new Item("fors", Color.valueOf("F3A39F")){{
            cost = 2f;
            hardness = 5;
        }};

        naturit = new Item("naturit", Color.valueOf("f2e878")){{
            flammability = 0.6f;
            radioactivity = 0.1f;
            cost = 4f;
        }};

        nobium = new Item("nobium", Color.valueOf("ffffff")){{
            cost = 4f;
            flammability = 0f;
            radioactivity = 0.2f;
        }};
        
        planatrium = new Item("planatrium", Color.valueOf("bc65d4ff")){{
            cost = 4f;
            hardness = 4;
            radioactivity = 1.0f;
        }};

        enojie = new Item("enojie", Color.valueOf("99f2c7")){{
            radioactivity = 0.8f;
            cost = 5f;
        }};
        

        flameid = new Item("flameid", Color.valueOf("ea8878")){{
            cost = 4f;
            flammability = 0.3f;
            hardness = 4;
        }};
        
        
    }
}