package sunset.content;

import mindustry.ctype.*;
import mindustry.type.*;

import static sunset.content.SnPlanets.*;

public class SnSectorPresets implements ContentList{
    public static SectorPreset
    oilfactory;

    @Override
    public void load(){
        //region azaria

        oilfactory = new SectorPreset("OilFactory", SnPlanets.azaria, 20){{
        alwaysUnlocked = true;
        addStartingItems = true;
        captureWave = 1;
        difficulty = 3;
}};

       //endregion
    }
}