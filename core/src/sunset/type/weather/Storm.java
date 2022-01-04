package sunset.type.weather;

import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.Vars;
import mindustry.entities.Lightning;
import mindustry.game.Team;
import mindustry.gen.WeatherState;
import mindustry.type.weather.ParticleWeather;

public class Storm extends ParticleWeather {

    public Color lightningColor = Color.yellow;
    public float lightningDamage = 7f;
    public float lightningChance = 0.01f;
    public float angleRand = 10f;
    public int lightningScl = 40;
    public int lightningLength = 3;
    public int lightningLengthRand = 10;

    public Storm(String name) {
        super(name);
    }

    @Override
    public void update(WeatherState state) {
        super.update(state);
        if (Mathf.chanceDelta(lightningChance)) {
              for (int i = 0;i < Math.sqrt(Vars.world.height() * Vars.world.width()) / lightningScl; i++){
                  Lightning.create(Team.derelict, lightningColor, lightningDamage,  Mathf.random(Vars.world.unitWidth()), Mathf.random(Vars.world.unitHeight()), state.windVector.angle() + Mathf.range(angleRand), lightningLength + Mathf.random(lightningLengthRand));
              }
        }
    }
}