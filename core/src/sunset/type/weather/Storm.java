package sunset.type.weather;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.noise.Noise;
import mindustry.Vars;
import mindustry.content.StatusEffects;
import mindustry.entities.Lightning;
import mindustry.game.Team;
import mindustry.gen.Sounds;
import mindustry.gen.WeatherState;
import mindustry.graphics.Pal;
import mindustry.type.weather.ParticleWeather;

public class Storm extends ParticleWeather {

    public Color lightningColor = Pal.lancerLaser;
    public float lightningDamage = 7f;
    public float lightningChance = 0.05f;
    public float angleRand = 360f;
    public int lightningScl = 30;
    public int lightningLength = 7;
    public int lightningLengthRand = 0;

    public Storm(String name) {
        super(name);
    }

    @Override
    public void update(WeatherState state) {
        super.update(state);
        if (Mathf.chanceDelta(lightningChance)) {
            for (int i = 0; i < Math.sqrt(Vars.world.height() * Vars.world.width()) / lightningScl; i++) {
                Lightning.create(Team.derelict, lightningColor, lightningDamage, Mathf.random(Vars.world.unitWidth()), Mathf.random(Vars.world.unitHeight()), Mathf.range(angleRand), lightningLength + Mathf.random(lightningLengthRand));
            }
        }
    }
}