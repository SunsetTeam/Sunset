package sunset.content;

import mindustry.ctype.*;
import mindustry.type.*;

public class SnSectorPresets implements ContentList{
    public static SectorPreset
            //azaria
            oilfactory;

            //burnout

            //rime

    @Override
    public void load(){
        //azaria
        oilfactory = new SectorPreset("oil-factory", SnPlanets.azaria, 20){{
            alwaysUnlocked = true;
            addStartingItems = true;
            captureWave = 1;
            difficulty = 3;
        }};

        //burnout


        //rime

    }
}