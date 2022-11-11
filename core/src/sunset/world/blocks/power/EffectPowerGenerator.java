package sunset.world.blocks.power;

import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.world.blocks.power.ConsumeGenerator;

import java.awt.*;

public class EffectPowerGenerator extends ConsumeGenerator {
    public Seq<EffectBuild> effects = new Seq<>();

    public EffectPowerGenerator(String name) {
        super(name);
    }

    public class EffectBuild extends ConsumeGeneratorBuild {
        public float x, y = 0;
        public float effectRot = 0;
        public Effect effect = Fx.none;
        public Color color;

        public void effects() {
            boolean valid = efficiency > 0;

            if (valid) {
                effect.at(x, y, effectRot, color);
            }
        }
    }
}

