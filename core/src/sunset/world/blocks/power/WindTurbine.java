package sunset.world.blocks.power;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.EnumSet;
import arc.util.Time;
import mindustry.annotations.Annotations.Load;
import mindustry.graphics.Drawf;
import mindustry.world.Block;
import mindustry.world.blocks.power.PowerGenerator;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.Env;
import mindustry.world.meta.StatUnit;
import mma.ModVars;
import sunset.content.SnAttribute;

import static mindustry.Vars.state;

public class WindTurbine extends PowerGenerator {

    public float rotorRotateSpeed = 4f;

    @Load("@-bottom")
    public TextureRegion bottomRegion;
    @Load("@-rotator")
    public TextureRegion rotatorRegion;
    @Load("@-top")
    public TextureRegion topRegion;

    public WindTurbine(String name) {
        super(name);

        flags = EnumSet.of();
        envEnabled = Env.any;
    }

    @Override
    public TextureRegion[] icons() {
        return !ModVars.packSprites ? new TextureRegion[]{region} : new TextureRegion[]{region, bottomRegion, rotatorRegion, topRegion};
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.remove(generationType);
        stats.add(generationType, powerProduction * 60f, StatUnit.powerSecond);
    }

    public class WindTurbineBuild extends GeneratorBuild {
        @Override
        public void updateTile(){
            productionEfficiency = enabled ?
                    Mathf.maxZero(SnAttribute.wind.env() +
                            (state.rules.lighting ?
                                    1f - state.rules.ambientLight.a :
                                    1f
                            )) : 0f;
        }

        @Override
        public void draw() {
            Draw.rect(region, x, y);
            Draw.rect(bottomRegion, x, y);
            Drawf.spinSprite(rotatorRegion, x, y, Time.time * rotorRotateSpeed);
            Draw.rect(topRegion, x, y);
        }
    }
}