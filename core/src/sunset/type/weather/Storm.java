package sunset.type.weather;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.Liquids;
import mindustry.gen.WeatherState;
import mindustry.type.Liquid;
import mindustry.type.weather.ParticleWeather;
import sunset.gen.SnSounds;

public class Storm extends ParticleWeather {
    public final Seq<WeatherElements> weatherElements = new Seq<>();

    public float lightningChance = 0.15f;
    public float stroke = 1f;

    public Liquid liquids = Liquids.water;
    public Sound sounds = SnSounds.thunder;
    public Color rainColor = Color.valueOf("F24949");

    public TextureRegion[] splashes = new TextureRegion[12];


    public Storm(String name) {
        super(name);
    }


    @Override
    public void load(){
        super.load();

        for(int i = 0; i < splashes.length; i++){
            splashes[i] = Core.atlas.find("splash-" + i);
        }
    }

    /*
    @Override
    public void update(WeatherState state) {
        super.update(state);
        if (Mathf.chanceDelta(lightningChance)) {
            for (int i = 0; i < Math.sqrt(Vars.world.height() * Vars.world.width()) / lightningScl; i++) {
                Lightning.create(Team.derelict, lightningColor, lightningDamage, Mathf.random(Vars.world.unitWidth()), Mathf.random(Vars.world.unitHeight()), Mathf.range(angleRand), lightningLength + Mathf.random(lightningLengthRand));
            }
        }
    }
     */

    @Override
    public void drawOver(WeatherState state) {
        drawRain(sizeMin, sizeMax, xspeed, yspeed, density, state.intensity, stroke, rainColor);
        super.drawOver(state);
    }

    @Override
    public void drawUnder(WeatherState state){
        drawSplashes(splashes, padding, density, state.intensity, state.opacity, padding, stroke, color, liquids);
    }

    @Override
    public void update(WeatherState state){
        super.update(state);

        if(Mathf.chanceDelta(lightningChance * state.opacity * state.intensity / 2)){
            Fx.lightning.at(Vars.world.unitWidth(), Vars.world.unitHeight());

            if(Mathf.chanceDelta(lightningChance * 11)){
                float lightningStrength = (float) (Math.random() * 5);

                if(lightningStrength > 1.2 && !Vars.headless) Vars.renderer.shake(lightningStrength, lightningStrength * 2);

                if(lightningStrength > 3.3){
                    SnSounds.thunder.play(lightningStrength / 5, (float) (0.6 + Math.random() * 0.6), Mathf.random(-0.6f, 1.2f));
                }
            }
        }
    }
}