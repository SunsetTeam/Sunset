package sunset.content.blocks;

import mindustry.ctype.ContentList;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.meta.BuildVisibility;
import sunset.content.SnItems;

public class SnEnvironment implements ContentList{
    public static Block
    
    //ores
        oreFors, orePlanatrium, oreFlameid,
    
    //floors 
        crimsondirt,
    
    //static walls 
        crimsonwall,

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
      
    //floors
      crimsondirt = new Floor("crimson-dirt"){{
          variants = 3;
      }};
    
    //static walls
      crimsonwall = new StaticWall("crimson-wall"){{
          variants = 2;
      }};

      //hidden
      hotSlag1 = new Floor("hotSlag1"){{
          buildVisibility = BuildVisibility.debugOnly;
          //only for burnout planet generator
      }};

      hotSlag2 = new Floor("hotSlag2"){{
          buildVisibility = BuildVisibility.debugOnly;
          //only for burnout planet generator
      }};

      hotSlag3 = new Floor("hotSlag3"){{
          buildVisibility = BuildVisibility.debugOnly;
          //only for burnout planet generator
      }};

      greenMoss = new Floor("greenMoss"){{
          buildVisibility = BuildVisibility.debugOnly;
          //only for azaria planet generator
      }};

      darkMud = new Floor("darkMud"){{
          buildVisibility = BuildVisibility.debugOnly;
          //only for azaria planet generator
      }};

      glacier1 = new Floor("glacier1"){{
          buildVisibility = BuildVisibility.debugOnly;
          //only for rime planet generator
      }};

      glacier2 = new Floor("glacier2"){{
          buildVisibility = BuildVisibility.debugOnly;
          //only for rime planet generator
      }};

      glacier3 = new Floor("glacier3"){{
          buildVisibility = BuildVisibility.debugOnly;
          //only for rime planet generator
      }};
  }
}