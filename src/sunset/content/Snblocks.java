package sunset.content;

import mindustry.ctype.ContentList;
import mindustry.world.Block;
import mindustry.world.blocks.environment.OreBlock;

public class Snblocks implements ContentList{
    public static Block

//envoriment

//ores
    oreFors, orePlanatrium, oreFlameid;

//floors crimsondirt;

//walls crimsonWall;

    @Override
    public void load(){
//ore

        oreFors = new OreBlock(Snitems.fors){{
            oreDefault = false;
            oreThreshold = 0.921f;
            oreScale = 26.1234f;
        }};

        orePlanatrium = new OreBlock(Snitems.planatrium){{
            oreDefault = false;
            oreThreshold = 0.921f;
            oreScale = 26.1234f;
        }};
 
        oreFlameid = new OreBlock(Snitems.flameid){{
            oreDefault = false;
            oreThreshold = 0.921f;
            oreScale = 26.1234f;
        }};

}
}
