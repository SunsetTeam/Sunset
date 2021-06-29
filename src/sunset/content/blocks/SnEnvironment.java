package sunset.content.blocks;

import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.content.*;
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
import mindustry.world.blocks.legacy.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.sandbox.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import sunset.content.SnItems;

public class SnEnvironment implements ContentList{
    public static Block
    
    //ores
    oreFors, orePlanatrium, oreFlameid, oreColdent,
    
    //floors 
    crimsongrass, crimsondirt, crimsonswamp, crimsonsand, crimsonsandwater, crimsonwater, crimsondeepwater,
    orangesand, obsidian, ash, burningash,
    
    //static walls 
    crimsondirtwall, crimsongrasswall, crimsonsandwall, 
    stonesandwall, stonesand, orangesandwall, obsidianwall, ashwall,
    
    //trees
    crimsontree,

    //hidden
    hotSlag1, hotSlag2, hotSlag3, greenMoss, darkMud, glacier1, glacier2, glacier3;
        
  @Override
  public void load(){
    
    //ores
    oreFors = new OreBlock(SnItems.fors){{
        oreDefault = false;
        oreThreshold = 0.921f;
        oreScale = 26.1234f;
      }};

    orePlanatrium = new OreBlock(SnItems.planatrium){{
        oreDefault = false;
        oreThreshold = 0.921f;
        oreScale = 26.1234f;
      }};
 
    oreFlameid = new OreBlock(SnItems.flameid){{
        oreDefault = false;
        oreThreshold = 0.921f;
        oreScale = 26.1234f;
      }};
    
    oreColdent= new OreBlock(SnItems.coldent){{
        oreDefault = false;
        oreThreshold = 0.921f;
        oreScale = 26.1234f;
      }};
    //floors
    crimsongrass = new Floor("crimson-grass"){{
      variants = 3;
    }};  

    crimsondirt = new Floor("crimson-dirt"){{
        variants = 3;
      }};  
    
    crimsonsand = new Floor("crimson-sand"){{
      itemDrop = Items.sand;
      playerUnmineable = true;
      variants = 3;
      }};  

    crimsonswamp = new Floor("crimson-swamp"){{
        speedMultiplier = 0.4f;
        variants = 3;
        liquidDrop = Liquids.water;
        liquidMultiplier = 0.4f;
        isLiquid = true;
        status = StatusEffects.wet;
        statusDuration = 120f;
        drownTime = 700f;
        albedo = 0.5f;
    }};
    
    crimsonsandwater = new Floor("crimson-sand-water"){{
        speedMultiplier = 0.9f;
        variants = 0;
        liquidDrop = Liquids.water;
        liquidMultiplier = 0.7f;
        isLiquid = true;
        status = StatusEffects.wet;
        statusDuration = 120f;
        cacheLayer = CacheLayer.water;
        albedo = 0.5f;
}};

    crimsonwater = new Floor("crimson-water"){{
        speedMultiplier = 0.8f;
        variants = 0;
        liquidDrop = Liquids.water;
        liquidMultiplier = 1.1f;
        isLiquid = true;
        status = StatusEffects.wet;
        statusDuration = 120f;
        cacheLayer = CacheLayer.water;
        albedo = 0.5f;
}};

    crimsondeepwater = new Floor("crimson-deep-water"){{
        speedMultiplier = 0.5f;
        variants = 0;
        liquidDrop = Liquids.water;
        liquidMultiplier = 1.1f;
        isLiquid = true;
        status = StatusEffects.wet;
        statusDuration = 120f;
        drownTime = 160f;
        cacheLayer = CacheLayer.water;
        albedo = 0.5f;
}};

    stonesand = new Floor("stone-sand"){{
        variants = 3;
        }};

    orangesand = new Floor("orange-sand"){{
      itemDrop = Items.sand;
      playerUnmineable = true;
      variants = 3;
      }};      

      obsidian = new Floor("obsidian"){{
          variants = 3;
        }};

      ash = new Floor("ash"){{
          variants = 3;
        }};

      burningash = new Floor("burning-ash"){{
          variants = 2;
          attributes.set(Attribute.heat, 0.55f);
          attributes.set(Attribute.water, -0.55f);
          
          emitLight = true;
          lightRadius = 28f;
          lightColor = Color.orange.cpy().a(0.15f);
        }}; 

//static walls
    crimsondirtwall = new StaticWall("crimson-dirt-wall"){{
        variants = 2;
      }};

    crimsongrasswall = new StaticWall("crimson-grass-wall"){{
        variants = 2;
      }};

    crimsonsandwall = new StaticWall("crimson-sand-wall"){{
        variants = 2;
      }};
    
    stonesandwall = new StaticWall("stone-sand-wall"){{
        variants = 2;
      }};
    
    orangesandwall = new StaticWall("orange-sand-wall"){{
        variants = 2;
      }};

    obsidianwall = new StaticWall("obsidian-wall"){{
        variants = 2;
      }};

    ashwall = new StaticWall("ash-wall"){{
        variants = 2;
      }};

//trees
    crimsontree = new StaticTree("crimson-tree"){{
        variants = 0;
      }};
      
//hidden
      hotSlag1 = new Floor("hotSlag1"){{
          buildVisibility = BuildVisibility.debugOnly;
          inEditor = false;
          //only for burnout planet generator
      }};

      hotSlag2 = new Floor("hotSlag2"){{
          buildVisibility = BuildVisibility.debugOnly;
          inEditor = false;
          //only for burnout planet generator
      }};

      hotSlag3 = new Floor("hotSlag3"){{
          buildVisibility = BuildVisibility.debugOnly;
          inEditor = false;
          //only for burnout planet generator
      }};

      greenMoss = new Floor("greenMoss"){{
          buildVisibility = BuildVisibility.debugOnly;
          inEditor = false;
          //only for azaria planet generator
      }};

      darkMud = new Floor("darkMud"){{
          buildVisibility = BuildVisibility.debugOnly;
          inEditor = false;
          //only for azaria planet generator
      }};

      glacier1 = new Floor("glacier1"){{
          buildVisibility = BuildVisibility.debugOnly;
          inEditor = false;
          //only for rime planet generator
      }};

      glacier2 = new Floor("glacier2"){{
          buildVisibility = BuildVisibility.debugOnly;
          inEditor = false;
          //only for rime planet generator
      }};

      glacier3 = new Floor("glacier3"){{
          buildVisibility = BuildVisibility.debugOnly;
          inEditor = false;
          //only for rime planet generator
      }};
  }
}