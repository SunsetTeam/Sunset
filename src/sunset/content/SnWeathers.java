package sunset.content;

import arc.graphics.*;
import arc.util.*;
import mindustry.ctype.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.weather.*;
import mindustry.world.meta.*;
import sunset.content.*;

public class SnWeathers implements ContentList{
    public static Weather
    blizzard;

    @Override
    public void load(){
        blizzard = new ParticleWeather("blizzard"){{
            color = noiseColor = Color.valueOf("F7F7F7");
            particleRegion = "particle";
            drawNoise = true;
            useWindVector = true;
            sizeMax = 70f;
            sizeMin = 30f;
            minAlpha = 0f;
            maxAlpha = 0.2f;
            density = 1800f;
            baseSpeed = 5.9f;
            attrs.set(Attribute.light, -0.40f);
            attrs.set(Attribute.water, -0.1f);
            status = SnStatusEffects.frostbite;
            opacityMultiplier = 0.35f;
            force = 0.3f;
            duration = 3f * Time.toMinutes;

            sound = SnSounds.blizzardWind;
            soundVol = 0.9f;
        }};
    }
}