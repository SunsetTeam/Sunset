package azaria.content;

import arc.graphics.*;
import arc.struct.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.campaign.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.experimental.*;
import mindustry.world.blocks.legacy.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.sandbox.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

public class Azblocks implements ContentList{
    public static Block

//envoriment

//ores
    oreAdamantium, orePlanatrium, oreFlameid;

//floors
    crimsondirt;

//walls
    crimsonWall;

    @Override
    public void load(){
//ore

        oreAdamantium = new OreBlock(Azitems.adamantium){{
            oreDefault = false;
            oreThreshold = 0.921f;
            oreScale = 26.1234f;
        }};

        orePlanatrium = new OreBlock(Azitems.planatrium){{
            oreDefault = false;
            oreThreshold = 0.921f;
            oreScale = 26.1234f;
        }};
 
        oreFlameid = new OreBlock(Azitems.flameid){{
            oreDefault = false;
            oreThreshold = 0.921f;
            oreScale = 26.1234f;
        }};

//floors

        crimsondirt = new Floor("crimson-dirt"){{
            variants = 3;
        }};

//walls

        crimsonWall = new StaticWall("crimson-wall"){{
            variants = 2;
}};
}
}