package sunset.content;

import arc.graphics.*;
import mindustry.ctype.*;
import mindustry.type.*;

public class SnItems implements ContentList{
    public static Item
    
    fors, naturite, nobium, planatrium, enojie, flameid, coldent;

    @Override
    public void load(){

        fors = new Item("fors", Color.valueOf("F3A39F")){{
            cost = 2f;
            hardness = 5f;
        }};

        naturite = new Item("naturite", Color.valueOf("f2e878")){{
            flammability = 0.6f;
            radioactivity = 0.1f;
            cost = 3f;
        }};

        nobium = new Item("nobium", Color.valueOf("ffffff")){{
            cost = 4f;
            radioactivity = 0.1f;
        }};
        
        planatrium = new Item("planatrium", Color.valueOf("bc65d4ff")){{
            cost = 5f;
            hardness = 4;
            explosiveness = 0.8f;
            radioactivity = 1f;
        }};

        enojie = new Item("enojie", Color.valueOf("99f2c7")){{
            radioactivity = 0.8f;
            explosiveness = 0.1f;
            cost = 6f;
        }};
        

        flameid = new Item("flameid", Color.valueOf("ea8878")){{
            cost = 4f;
            flammability = 0.3f;
            hardness = 4;
        }};
        
        coldent = new Item("coldent", Color.valueOf("baf4f5ff")){{
            cost = 4f;
            hardness = 4;
        }};
    }
}
