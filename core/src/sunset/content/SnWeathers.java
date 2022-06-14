package sunset.content;

import arc.graphics.Color;
import arc.util.Time;
import mindustry.content.StatusEffects;
import mindustry.content.Weathers;
import mindustry.gen.Sounds;
import mindustry.type.Weather;
import mindustry.type.weather.ParticleWeather;
import mindustry.world.meta.Attribute;
import sunset.type.weather.*;
import sunset.type.weather.WeatherElement;

public class SnWeathers {
    public static Weather
            blizzard, flood, lightningStorm;

    public static void load() {
        blizzard = new ParticleWeather("blizzard") {{
            color = noiseColor = Color.valueOf("F3F3F3");
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

            sound = Sounds.windhowl;
            soundVol = 0f;
            soundVolOscMag = 1.5f;
            soundVolOscScl = 1100f;
            soundVolMin = 0.02f;
        }};

        flood = new ParticleWeather("flood") {{
            duration = 10f * Time.toMinutes;
            noiseLayers = 3;
            noiseLayerSclM = 0.4f;
            noiseLayerAlphaM = 0.4f;
            noiseLayerSpeedM = 1f;
            noiseLayerSclM = 0.7f;
            baseSpeed = 0.03f;
            color = noiseColor = Color.grays(0.4f);
            noiseScale = 1000f;
            //TODO make new weather

//            noisePath = ("sunset-"+"flood");
//            noisePath = "flood";
            noisePath= ((ParticleWeather) Weathers.fog).noisePath;
            drawParticles = false;
            drawNoise = true;
            useWindVector = false;
            xspeed = 3f;
            yspeed = 0.1f;
            attrs.set(Attribute.water, 0.07f);
            opacityMultiplier = 0.30f;

            statusAir = false;
            statusGround = true;
        }};

        lightningStorm = new Storm("lightning-storm") {{
            drawNoise = false;
            particleRegion = "particle";
            useWindVector = true;

            sizeMin = 18f;
            sizeMax = 40;
            minAlpha = 0f;
            maxAlpha = 0.1f;
            color = Color.valueOf("B24848");
            stroke = 0.75f;
            density = 7000f;
            force = 1f;
            yspeed = 4;
            xspeed = 9;
            baseSpeed = 5.9f;
            padding = 40f;
            attrs.set(Attribute.light, -0.40f);
            attrs.set(SnAttribute.wind, 1f);
            attrs.set(SnAttribute.burheyna, 0.2f);
            status = StatusEffects.wet;
            opacityMultiplier = 0.35f;
            force = 0.4f;
            duration = 3f * Time.toMinutes;

            sound = Sounds.rain;
            soundVol = 0.25f;

            weatherElements.addAll(
                    new WeatherElement("thunder") {{
                        lightningElement = true;
                        lightningChance = 0.07f;
                        lightning = 1;
                    }}
            );
        }};
    }
}