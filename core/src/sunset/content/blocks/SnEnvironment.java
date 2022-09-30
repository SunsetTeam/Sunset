package sunset.content.blocks;

import arc.graphics.Color;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.graphics.CacheLayer;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;
import sunset.content.SnAttribute;
import sunset.content.SnItems;
import sunset.content.SnLiquids;
//import sunset.graphics.SnShaders;
import sunset.graphics.*;
import sunset.world.blocks.environment.*;

public class SnEnvironment  {
    public static Block

    //ores
    oreFors, oreErius, oreNedirium, orePlanatrium, naturiteShard, /*oreFlameid, oreColdent,*/

    //floors
    chromakey,
    burheynaFloor, thermalBurheyna,

    greenStone, jadestone,

    //static walls
    greenStoneWall, jadestoneWall,

    yelliteWall, sayangWall, darkYelliteWall,
    
    //breakable environment walls
    stagesWall,
    
    //pines
    //trees
    //props
    spikeProp,
    
    //special
    geyser;

    public static void load() {
        //region ores
        oreFors = new OreBlock(SnItems.fors) {{
            oreDefault = false;
            oreThreshold = 0.921f;
            oreScale = 26.1234f;
        }};
        oreErius = new OreBlock(SnItems.erius) {{
            oreDefault = false;
            oreThreshold = 0.921f;
            oreScale = 26.1234f;
        }};

        oreNedirium = new OreBlock("ore-wall-nedirium", SnItems.nedirium) {{
            wallOre = true;
            oreDefault = false;
            oreThreshold = 0.921f;
            oreScale = 26.1234f;
        }};

        orePlanatrium = new OreBlock(SnItems.planatrium) {{
            oreDefault = false;
            oreThreshold = 0.921f;
            oreScale = 26.1234f;
        }};

        naturiteShard = new OverlayFloor("naturite-shard"){{
            variants = 3;
        }};

        /*oreFlameid = new OreBlock(SnItems.flameid) {{
            oreDefault = false;
            oreThreshold = 0.921f;
            oreScale = 26.1234f;
            status = StatusEffects.burning;
            statusDuration = 2f * Time.toSeconds;
        }};

        oreColdent = new OreBlock(SnItems.coldent) {{
            oreDefault = false;
            oreThreshold = 0.921f;
            oreScale = 26.1234f;
        }};*/

        //endregion ores
        //region floors
        chromakey = new Floor("chromakey"){{
            variants = 1;
        }};
        burheynaFloor = new Floor("burheyna-floor") {{
            speedMultiplier = 0.8f;
            variants = 0;
            liquidDrop = SnLiquids.burheyna;
            liquidMultiplier = 1.1f;
            isLiquid = true;
            statusDuration = 120f;
            cacheLayer = SnCacheLayer.burheyna;
            drownTime = 180f;
            albedo = 0.5f;
        }};
        thermalBurheyna = new ThermalFloor("burheyna-thermal-floor", 0) {{
            speedMultiplier = 0.8f;
            liquidDrop = SnLiquids.burheyna;
            liquidMultiplier = 1f;
            isLiquid = true;
            statusDuration = 120f;
            drownTime = 210f;
            albedo = 0.5f;
            attributes.set(SnAttribute.thermalBurheyna, 0.25f);
        }};

        greenStone = new Floor("green-stone"){{
            variants = 3;
        }};

        jadestone = new Floor("jadestone") {{
            variants = 3;
            attributes.set(SnAttribute.burheyna, -0.1f);
        }};
        //endregion floors
        //region static walls

        greenStoneWall = new StaticWall("green-stone-wall"){{
            variants = 2;
        }};

        jadestoneWall = new StaticWall("jadestone-wall") {{
            variants = 2;
        }};

        yelliteWall = new StaticWall("yellite-wall"){{
            variants = 2;
        }};

        sayangWall = new StaticWall("sayang-well"){{
            variants = 2;
        }};

        darkYelliteWall = new StaticWall("dark-yellite-wall"){{
            variants = 2;
        }};
        //region breakable environment walls
        stagesWall = new BreakableEnvWall("stages-wall") {{
            variants = 1;
            stages = 5;
            health = 5150;
        }};
        //endregion static walls
        //region trees
        //endregion trees
        //region props
        spikeProp = new SpikeProp("spice-boulder"){{
           variants = 3;
           touchDamage = 0.8f;
           touchRange = 10f;
        }};
        //endregion props
        //region pines
        //endregion pines
        //region special
        geyser = new Geyser("geyser") {{
            damageTaken = 0.2f;
            drownTime = 180f;
            isLiquid = true;
            status = StatusEffects.wet;
            statusDuration = 120f;
            speedMultiplier = 0.9f;
            variants = 2;
            liquidDrop = SnLiquids.burheyna;
            steamEffect = new Effect(30f, Fx.steam.renderer);
            eruptionEffect = new Effect(30f, Fx.ballfire.renderer);
        }};
        //endregion special
    }
}
