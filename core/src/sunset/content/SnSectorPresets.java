package sunset.content;

import mindustry.ctype.ContentList;
import mindustry.type.SectorPreset;

public class SnSectorPresets implements ContentList {
    public static SectorPreset
            //azaria
            oilfactory;

            //burnout

            //rime

    @Override
    public void load() {
        //region azaria
        oilfactory = new SectorPreset("oil-factory", SnPlanets.azaria, 20) {{
            alwaysUnlocked = true;
            addStartingItems = true;
            captureWave = 1;
            difficulty = 3;
        }};
        //endregion azaria
        //region burnout

        //endregion burnout
        //region rime

        //endregion rime
    }
}