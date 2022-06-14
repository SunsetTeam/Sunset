package sunset.type.weather;

import arc.audio.*;
import arc.graphics.*;
import arc.math.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.graphics.*;
import sunset.gen.*;

public class WeatherElement{

    public boolean lightningElement = false;
    public float lightningDamage = 50f;
    public float lightningDamageRadius = 20f;
    public int lightningLength = 25;
    public int lightningLengthRand = 0;


    public float angleRand = 360f;
    public int lightning = 1;
    public float lightningShake = 15f;
    public Effect lightningEffect = Fx.lightning;
    public Effect lightningExplosion = Fx.massiveExplosion;
    public Sound lightningSound = SnSounds.thunder;
    public Color lightningColor = Pal.lancerLaser;

    public WeatherElement(String name){
    }

    public void effects(float x, float y){
        Effect.shake(lightningShake, lightningShake, x, y);
        lightningExplosion.at(x, y);
        lightningEffect.at(x, y);
        lightningSound.at(x, y);
    }

    public void update(float x, float y){
        if(lightningElement) drawLightning(x, y);
    }

    public void drawLightning(float x, float y){
        for(int i = 0; i < Math.max(1, Math.round(Math.random() * lightning)); i++){
            effects(x, y);
            Damage.damage(x, y, lightningDamageRadius, lightningDamage);
            Lightning.create(Team.derelict, lightningColor, lightningDamage, x - Vars.world.unitWidth(), y - Vars.world.unitHeight(), (float)(Mathf.range(angleRand) * Math.random()), lightningLength + Mathf.random(lightningLengthRand));
        }
    }
}
