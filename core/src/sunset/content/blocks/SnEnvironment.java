package sunset.content.blocks;

import arc.graphics.*;
import arc.util.Time;
import mindustry.ctype.*;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;
import sunset.content.SnItems;
import sunset.content.SnLiquids;
import sunset.content.SnStatusEffects;
import sunset.world.blocks.environment.*;

public class SnEnvironment implements ContentList {
    public static Block

    //ores
    oreFors, orePlanatrium, oreFlameid, oreColdent,

    //floors
    crimsonGrass, crimsonDirt, crimsonSand, crimsonSwamp, crimsonMoss,
    crimsonSandWater, crimsonWater, crimsonDeepWater,
    crimsonSnow, crimsonIce, crimsonIceSnow,
    granite,

    jadestone,

    orangeSand, stoneSand,
    obsidian, ash, burningAsh,

    //static walls 
    crimsonGrassWall, crimsonDirtWall, crimsonSandWall,
    crimsonSnowWall, crimsonIceWall,
    graniteWall,

    jadestoneWall, gJadestoneWall,

    orangeSandWall, stoneSandWall,
    obsidianWall, ashWall,
    
    //pines
    crimsonPine,
    
    //trees
    crimsonTree, crimsonTreeDead,
    
    //special
    geyser,

    //hidden
    hotSlag1, hotSlag2, hotSlag3, glacier1, glacier2, glacier3;

    @Override
    public void load() {
        //region ores
        oreFors = new OreBlock(SnItems.fors) {{
            oreDefault = false;
            oreThreshold = 0.921f;
            oreScale = 26.1234f;
        }};
        orePlanatrium = new OreBlock(SnItems.planatrium) {{
            oreDefault = false;
            oreThreshold = 0.921f;
            oreScale = 26.1234f;
        }};
        oreFlameid = new OreBlock(SnItems.flameid) {{
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
        }};
        //endregion ores
        //region floors
        crimsonGrass = new Floor("crimson-grass") {{
            variants = 3;
            wall = crimsonGrassWall;
        }};
        crimsonDirt = new Floor("crimson-dirt") {{
            variants = 3;
            wall = crimsonDirtWall;
        }};
        crimsonSand = new Floor("crimson-sand") {{
            itemDrop = Items.sand;
            playerUnmineable = true;
            variants = 3;
            wall = crimsonSandWall;
        }};
        crimsonSwamp = new Floor("crimson-swamp") {{
            speedMultiplier = 0.4f;
            variants = 3;
            liquidDrop = SnLiquids.burheyna;
            liquidMultiplier = 0.4f;
            isLiquid = true;
            status = SnStatusEffects.viscous;
            statusDuration = 120f;
            drownTime = 700f;
            albedo = 0.5f;
        }};
        crimsonMoss = new Floor("crimson-moss") {{
            variants = 3;
            attributes.set(Attribute.spores, 0.2f);
            wall = crimsonPine;
        }};

        crimsonSandWater = new Floor("crimson-sand-water") {{
            speedMultiplier = 0.9f;
            variants = 0;
            liquidDrop = SnLiquids.burheyna;
            liquidMultiplier = 0.7f;
            isLiquid = true;
            status = SnStatusEffects.viscous;
            statusDuration = 120f;
            cacheLayer = CacheLayer.water;
            albedo = 0.5f;
        }};
        crimsonWater = new Floor("crimson-water") {{
            speedMultiplier = 0.8f;
            variants = 0;
            liquidDrop = SnLiquids.burheyna;
            liquidMultiplier = 1.1f;
            isLiquid = true;
            status = SnStatusEffects.viscous;
            statusDuration = 120f;
            cacheLayer = CacheLayer.water;
            albedo = 0.5f;
        }};
        crimsonDeepWater = new Floor("crimson-deep-water") {{
            speedMultiplier = 0.5f;
            variants = 0;
            liquidDrop = SnLiquids.burheyna;
            liquidMultiplier = 1.1f;
            isLiquid = true;
            status = SnStatusEffects.viscous;
            statusDuration = 120f;
            drownTime = 160f;
            cacheLayer = CacheLayer.water;
            albedo = 0.5f;
        }};

        crimsonSnow = new Floor("crimson-snow") {{
            variants = 3;
            attributes.set(Attribute.water, 0.2f);
        }};
        crimsonIce = new Floor("crimson-ice") {{
            variants = 3;
            dragMultiplier = 0.25f;
            speedMultiplier = 0.85f;
            attributes.set(Attribute.water, 0.38f);
            wall = crimsonIceWall;
        }};
        crimsonIceSnow = new Floor("crimson-icesnow") {{
            variants = 3;
            dragMultiplier = 0.55f;
            attributes.set(Attribute.water, 0.28f);
        }};

        granite = new Floor("granite") {{
            variants = 3;
            attributes.set(Attribute.water, -0.4f);
            wall = graniteWall;
        }};

        jadestone = new Floor("jadestone") {{
            variants = 3;
            attributes.set(Attribute.water, -0.1f);
            wall = jadestoneWall;
        }};

        orangeSand = new Floor("orange-sand") {{
            itemDrop = Items.sand;
            playerUnmineable = true;
            variants = 3;
            wall = orangeSandWall;
        }};
        stoneSand = new Floor("stone-sand") {{
            variants = 3;
            wall = stoneSandWall;
        }};

        obsidian = new Floor("obsidian") {{
            variants = 3;
            wall = obsidianWall;
        }};
        ash = new Floor("ash") {{
            variants = 3;
            wall = ashWall;
        }};
        burningAsh = new Floor("burning-ash") {{
            variants = 2;
            attributes.set(Attribute.heat, 0.55f);
            attributes.set(Attribute.water, -0.55f);

            emitLight = true;
            lightRadius = 28f;
            lightColor = Color.orange.cpy().a(0.15f);
        }};
        //endregion floors
        //region static walls
        crimsonGrassWall = new StaticWall("crimson-grass-wall") {{
            variants = 2;
        }};
        crimsonDirtWall = new StaticWall("crimson-dirt-wall") {{
            variants = 2;
        }};
        crimsonSandWall = new StaticWall("crimson-sand-wall") {{
            variants = 2;
        }};

        crimsonSnowWall = new StaticWall("crimson-snow-wall") {{
            variants = 2;
        }};
        crimsonIceWall = new StaticWall("crimson-ice-wall") {{
            variants = 2;
        }};

        graniteWall = new StaticWall("granite-wall") {{
            variants = 3;
        }};

        jadestoneWall = new StaticWall("jadestone-wall") {{
            variants = 2;
        }};

        gJadestoneWall = new StaticWall("g-jadestone-wall") {{
            variants = 2;
        }};

        orangeSandWall = new StaticWall("orange-sand-wall") {{
            variants = 2;
        }};
        stoneSandWall = new StaticWall("stone-sand-wall") {{
            variants = 2;
        }};

        obsidianWall = new StaticWall("obsidian-wall") {{
            variants = 2;
        }};
        ashWall = new StaticWall("ash-wall") {{
            variants = 2;
        }};
        //endregion static walls
        //region trees
        crimsonTree = new StaticTree("crimson-tree") {{
            variants = 0;
        }};
        crimsonTreeDead = new StaticTree("crimson-tree-dead") {{
            variants = 0;
        }};
        //endregion trees
        //region pines
        crimsonPine = new StaticTree("crimson-pine") {{
            variants = 0;
        }};
        //endregion pines
        //region special
        geyser = new Geyser("geyser") {{
            damageTaken = 0.2f;
            drownTime = 180f;
            isLiquid = true;
            status = StatusEffects.wet;
            statusDuration = 120f;
            cacheLayer = CacheLayer.water;
            speedMultiplier = 0.9f;
            variants = 0;
            liquidDrop = Liquids.water;
            steamEffect = new Effect(30f, Fx.steam.renderer);
            eruptionEffect = new Effect(30f, Fx.ballfire.renderer);
        }};
        //endregion special

        //region hidden
        hotSlag1 = new MockEnvironmentBlock("hotSlag1") {{
            replacement=Blocks.slag;
            Color.valueOf(mapColor,"FD7738");
            //only for burnout planet generator
        }};
        hotSlag2 = new MockEnvironmentBlock("hotSlag2") {{
            replacement=Blocks.slag;
            Color.valueOf(mapColor,"FF6324");
            //only for burnout planet generator
        }};
        hotSlag3 = new MockEnvironmentBlock("hotSlag3") {{
            replacement=Blocks.slag;
            Color.valueOf(mapColor,"FF591A");
            //only for burnout planet generator
        }};
        glacier1 = new MockEnvironmentBlock("glacier1") {{
            replacement=Blocks.snow;
            Color.valueOf(mapColor,"5ECBD9");
            //only for rime planet generator
        }};
        glacier2 = new MockEnvironmentBlock("glacier2") {{
            replacement=Blocks.ice;
            Color.valueOf(mapColor,"94DDE6");
            //only for rime planet generator
        }};
        glacier3 = new MockEnvironmentBlock("glacier3") {{
            replacement=Blocks.snow;
            Color.valueOf(mapColor,"8AE4F0");
            //only for rime planet generator
        }};
        //endregion hidden
    }
}
