package sunset.world.blocks.power;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.annotations.Annotations.*;
import mindustry.graphics.*;
import mindustry.world.blocks.power.*;
import mindustry.world.meta.*;
import mma.*;
import sunset.content.*;

import static mindustry.Vars.*;

public class WindTurbine extends PowerGenerator{

    public float rotorRotateSpeed = 4f;

    @Load("@-bottom")
    public TextureRegion bottomRegion;
    @Load("@-rotator")
    public TextureRegion rotatorRegion;
    @Load("@-top")
    public TextureRegion topRegion;

    public WindTurbine(String name){
        super(name);

        flags = EnumSet.of();
        envEnabled = Env.any;
    }

    @Override
    public TextureRegion[] icons(){
        return !ModVars.packSprites ? new TextureRegion[]{region} : new TextureRegion[]{region, bottomRegion, rotatorRegion, topRegion};
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.remove(generationType);
        stats.add(generationType, powerProduction * 60f, StatUnit.powerSecond);
    }

    public class WindTurbineBuild extends GeneratorBuild{
        @Override
        public void updateTile(){
            productionEfficiency = Mathf.maxZero(SnAttribute.wind.env() +
                                                 (state.rules.lighting ? 1f - state.rules.ambientLight.a : 1f)
            );
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            Draw.rect(bottomRegion, x, y);
            Drawf.spinSprite(rotatorRegion, x, y, Time.time * rotorRotateSpeed);
            Draw.rect(topRegion, x, y);
        }
    }
}