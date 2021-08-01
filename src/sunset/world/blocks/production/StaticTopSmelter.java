package sunset.world.blocks.production;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import arc.util.*;
//import mindustry.annotations.Annotations.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.blocks.production.GenericCrafter;

// A GenericCrafter with a static top region
public class StaticTopSmelter extends GenericCrafter{
    public Color flameColor = Color.valueOf("ffc999");
    public TextureRegion topRegion;

    public StaticTopSmelter(String name){
        super(name);
        ambientSound = Sounds.smelter;
        ambientSoundVolume = 0.07f;
    }

    @Override
    public void load(){
      super.load();
          topRegion = Core.atlas.find(name + "-top");
    }
    
    public class StaticTopSmelterBuild extends GenericCrafterBuild{
        public void draw(StaticTopSmelterBuild entity){
            super.draw();
                float g = 0.3f;
                float r = 0.06f;
                float cr = Mathf.random(0.1f);

                Draw.alpha(((1f - g) + Mathf.absin(Time.time, 8f, g) + Mathf.random(r) - r) * warmup);

                Draw.tint(flameColor);
                Fill.circle(x, y, 3f + Mathf.absin(Time.time, 5f, 2f) + cr);
                Draw.color(1f, 1f, 1f, warmup);
                Draw.rect(topRegion, entity.x, entity.y);
                Fill.circle(x, y, 1.9f + Mathf.absin(Time.time, 5f, 1f) + cr);
                Draw.color();
        }

        @Override
        public void drawLight(){
            Drawf.light(team, x, y, (60f + Mathf.absin(10f, 5f)) * warmup * size, flameColor, 0.65f);
        }
    }
}
