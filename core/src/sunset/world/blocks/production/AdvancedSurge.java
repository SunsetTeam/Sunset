package sunset.world.blocks.production;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
//import mindustry.annotations.Annotations.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.blocks.production.GenericCrafter;

// A GenericCrafter with a static top region
public class AdvancedSurge extends GenericCrafter{

    public AdvancedSurge(String name){
        super(name);
    }

    @Override
    public void load(){
      super.load();
    }
    
    public class StaticTopSmelterBuild extends GenericCrafterBuild{
        public void draw(StaticTopSmelterBuild entity){
            super.draw();

        }

    }
}
