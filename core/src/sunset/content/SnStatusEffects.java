package sunset.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import sunset.graphics.*;
import sunset.type.*;

import static arc.Core.*;

class SnStatusEffects_ implements ContentList{
    public static StatusEffect
    //common
    frostbite, stun, starBuff, galaxyDebuff, electricalShort, reloading, viscous, inferno,

    //only reactive
    molecula,

    //stackable
    overheat, incineration;

    @Override
    public void load(){

        //region common
        frostbite = new StatusEffect("frostbite"){{
            color = Color.valueOf("6ecdec");
            damage = 0.17f;
            speedMultiplier = 0.4f;
            healthMultiplier = 0.7f;
            effect = Fx.freezing;
            transitionDamage = 18f;

            init(() -> {
                opposite(StatusEffects.melting, StatusEffects.burning);
                affinity(StatusEffects.blasted, ((unit, cur, time) -> {
                    unit.damagePierce(transitionDamage);
                    cur.set(frostbite, time);
                }));
            });
        }};

        stun = new StatusEffect("stun"){{
            color = Color.valueOf("392f17");
            speedMultiplier = 0f;
            disarm = true;
        }};//no sprite

        starBuff = new StatusEffect("star-buff"){{
            color = Pal.surge;
            speedMultiplier = 1.3f;
            healthMultiplier = 1.15f;
            damageMultiplier = 1.1f;
            reloadMultiplier = 1.1f;
        }};

        galaxyDebuff = new StatusEffect("galaxy-debuff"){{
            speedMultiplier = 0.8f;
            damageMultiplier = 0.9f;
            reloadMultiplier = 0.8f;
        }};//no sprite

        electricalShort = new StatusEffect("electric-short"){{
            effectChance = 100;
            speedMultiplier = 0;
            disarm = true;
            color = Color.valueOf("0AFEFF");
            reloadMultiplier = 0.3f;
            effect = Fx.freezing;
            init(() -> {
                opposite(StatusEffects.shocked);
                affinity(StatusEffects.wet, ((unit, result, time) -> {
                    unit.damagePierce(unit.health / 4);
                    result.set(reloading, 300);
                }));
                affinity(StatusEffects.freezing, ((unit, result, time) -> {
                    unit.damagePierce(unit.health / 2);
                    result.set(reloading, 600);
                }));
            });
        }};

        reloading = new StatusEffect("reboot"){{
            speedMultiplier = 0;
            disarm = true;
            color = Color.valueOf("047070");
        }};

        viscous = new StatusEffect("viscous"){{
            color = Color.valueOf("721A1A");
            speedMultiplier = 0.94f;
            effect = SnFx.viscous;
            effectChance = 0.09f;
        }};//no sprite

        inferno = new StatusEffect("inferno"){{
            color = SnPal.redfire1;
            effect = SnFx.redFlame;
            effectChance = 0.3f;
            speedMultiplier = 1.1f;
            reloadMultiplier = 0.6f;
            healthMultiplier = 0.7f;
            damageMultiplier = 0.7f;

            init(() -> {
                opposite(frostbite);
            });
        }};//no sprite
        //endregion common
        //region only reactive
        molecula = new StatusEffect("molecula"){{
            color = Color.valueOf("3DD957");
            permanent = true;
            effect = Fx.heal;
            init(() -> {
                opposite(StatusEffects.freezing, SnStatusEffects.frostbite);
                affinity(StatusEffects.overclock, ((unit, result, time) -> {
                    unit.damagePierce(600);
                    result.set(molecula, 300);
                }));
                affinity(StatusEffects.overdrive, ((unit, result, time) -> {
                    unit.damagePierce(600);
                    result.set(molecula, 300);
                }));
                affinity(StatusEffects.shielded, ((unit, result, time) -> {
                    unit.damagePierce(450);
                    result.set(molecula, 300);
                }));
                affinity(StatusEffects.blasted, ((unit, result, time) -> {
                    unit.damagePierce(30);
                    result.set(molecula, 300);
                }));
                affinity(StatusEffects.shocked, ((unit, result, time) -> {
                    unit.damagePierce(30);
                    result.set(molecula, 300);
                }));
            });
        }};
        //endregion only reactive
        //region stackable
        overheat = new StackableStatusEffect("overheat"){
            boolean draw = false;

            {
                color = Color.valueOf("FF30000");
                maxStacks = 20;
                stackEntries(
                stackEntry().healthMultiplier(0.99f).speedMultiplier(0.99f),
                stackEntry().healthMultiplier(0.98f).speedMultiplier(0.99f),
                stackEntry().healthMultiplier(0.97f).speedMultiplier(0.99f),
                stackEntry().healthMultiplier(0.96f).speedMultiplier(0.99f),
                stackEntry().healthMultiplier(0.95f).speedMultiplier(0.98f),
                stackEntry().healthMultiplier(0.94f).speedMultiplier(0.98f),
                stackEntry().healthMultiplier(0.92f).speedMultiplier(0.98f),
                stackEntry().healthMultiplier(0.90f).speedMultiplier(0.98f),
                stackEntry().healthMultiplier(0.86f).speedMultiplier(0.97f),
                stackEntry().healthMultiplier(0.84f).speedMultiplier(0.96f),
                stackEntry().healthMultiplier(0.82f).speedMultiplier(0.96f),
                stackEntry().healthMultiplier(0.79f).speedMultiplier(0.96f),
                stackEntry().healthMultiplier(0.76f).speedMultiplier(0.95f),
                stackEntry().healthMultiplier(0.73f).speedMultiplier(0.94f),
                stackEntry().healthMultiplier(0.70f).speedMultiplier(0.93f),
                stackEntry().healthMultiplier(0.65f).speedMultiplier(0.93f),
                stackEntry().healthMultiplier(0.60f).speedMultiplier(0.93f),
                stackEntry().healthMultiplier(0.55f).speedMultiplier(0.92f),
                stackEntry().healthMultiplier(0.47f).speedMultiplier(0.91f),
                stackEntry().healthMultiplier(0.39f).speedMultiplier(0.90f),
                null
                );
                healthMultiplier = 0.99f;
                speedMultiplier = 0.99f;
                color = Color.red;

                stacksDrawer=(unit,stackIndex)->{
                    if(draw) return;
                    Vars.renderer.effectBuffer.begin();

                    draw = true;
                    unit.draw();
                    draw = false;

                    Vars.renderer.effectBuffer.end();

                    Draw.z((unit.isFlying() ? Layer.flyingUnit : Layer.groundUnit) + 1);

                    Draw.color(color, (stackIndex / 2f) / maxStacks);
                    TextureRegion wrap = Draw.wrap(Vars.renderer.effectBuffer.getTexture());
                    wrap.flip(false, true);
                    Draw.rect(wrap, camera.position.x, camera.position.y, camera.width, camera.height);
                    //Vars.renderer.effectBuffer.blit(Shaders.screenspace);
                    Draw.color();
                };
            }
        };

        //region stackable
        incineration = new StackableStatusEffect("incineration"){{
            color = Color.valueOf("BD4E17");
            maxStacks = 20;
            stackEntries(
            stackEntry().health(0.97f).speed(0.97f),
            stackEntry().health(0.96f).speed(0.97f),
            stackEntry().health(0.95f).speed(0.97f),
            stackEntry().health(0.94f).speed(0.97f),
            stackEntry().health(0.93f).speed(0.98f),
            stackEntry().health(0.92f).speed(0.95f),
            stackEntry().health(0.88f).speed(0.95f),
            stackEntry().health(0.86f).speed(0.95f),
            stackEntry().health(0.83f).speed(0.974f),
            stackEntry().health(0.80f).speed(0.93f),
            stackEntry().health(0.79f).speed(0.93f),
            stackEntry().health(0.75f).speed(0.93f),
            stackEntry().health(0.71f).speed(0.93f),
            stackEntry().health(0.67f).speed(0.92f),
            stackEntry().health(0.60f).speed(0.92f),
            stackEntry().health(0.55f).speed(0.91f),
            stackEntry().health(0.50f).speed(0.91f),
            stackEntry().health(0.39f).speed(0.91f),
            stackEntry().health(0.21f).speed(0.90f),
            stackEntry().health(0.11f).speed(0.89f),
            stackEntry().health(0.04f).speed(0.85f),
            null
            );
            healthMultiplier = 0.97f;
            speedMultiplier = 0.97f;
            color = SnPal.incineration;
        }};
        //endregion stackable
    }

}