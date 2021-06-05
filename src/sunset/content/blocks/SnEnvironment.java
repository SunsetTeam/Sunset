package sunset.content.blocks;

import mindustry.ctype.ContentList;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.environment.StaticWall;
import sunset.content.SnItems;

public class SnEnvironment implements ContentList{
    public static Block
    
    //ores
        oreFors, orePlanatrium, oreFlameid,
    
    //floors 
        crimsondirt,
    
    //static walls 
        crimsonwall;
        
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
  }
}