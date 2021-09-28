package sunset.content;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;
import sunset.type.StackableStatusEffect;

public class SnStatusEffects implements ContentList{
    public static StatusEffect
            frostbite, stun, starBuff, galaxyDebuff, overheat, electricalShort, reloading, azaliyed;
    @Override
    public void load(){

        frostbite = new StatusEffect("frostbite"){{
            color = Color.valueOf("6ecdec");
            damage = 0.17f;
            speedMultiplier = 0.4f;
            healthMultiplier = 0.7f;
            effect = Fx.freezing;
            transitionDamage = 18f;

        init(() -> {
                opposite(StatusEffects.melting, StatusEffects.burning);
                affinity(StatusEffects.blasted, ((unit, time, newTime, result) -> {
                    unit.damagePierce(transitionDamage);
                    result.set(frostbite, time);
                }));
            });
        }};

        stun = new StatusEffect("stun"){{
            color = Color.valueOf("392f17");
            speedMultiplier = 0f;
            disarm = true;
        }};

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
       }};

       overheat = new StackableStatusEffect("overheat"){{
           color = Color.valueOf("FF30000");
           maxStacks = 20;
           healthMultipliers.addAll(0.99f, 0.98f, 0.97f, 0.96f, 0.95f, 0.94f, //-0.01
                                    0.92f, 0.90f, 0.86f, 0.84f, 0.82f, //-0.02
                                    0.79f, 0.76f, 0.73f, 0.70f, //-0.03
                                    0.65f, 0.60f, 0.55f, //-0.05
                                    0.47f, 0.39f); //-0.08
           speedMultipliers.addAll(0.99f, 0.99f, 0.99f, 0.99f, 0.98f,
                                   0.98f, 0.98f, 0.98f, 0.97f, 0.96f,
                                   0.96f, 0.96f, 0.95f, 0.94f, 0.93f,
                                   0.93f, 0.93f, 0.92f, 0.91f, 0.90f);
           healthMultiplier = 0.99f;
           speedMultiplier = 0.99f;
           color = Color.red;
       }
           @Override
           public void drawStack(Unit unit, int stackCount) {
               Draw.z((unit.isFlying() ? Layer.flyingUnit : Layer.groundUnit)+1);
               TextureRegion text = unit.type.region;
               Draw.color(color, (stackCount/2f)/maxStacks);
               Draw.rect(text, unit.x, unit.y, unit.rotation - 90f);
               Draw.color();
           }
       };

       electricalShort = new StatusEffect("electric-short"){{
           effectChance = 1;
           speedMultiplier = 0;
           disarm = true;
           color = Color.valueOf("0AFEFF");
           reloadMultiplier = 0.3f;
           transitionDamage = 300; //TODO 50% of unit health
           effect = Fx.freezing;
           init(() -> {
               affinity(StatusEffects.wet, ((unit, time, newTime, result) -> {
                   unit.damagePierce(transitionDamage);
                   result.set(reloading, 300);
               }));
               affinity(StatusEffects.freezing, ((unit, time, newTime, result) -> {
                   unit.damagePierce(transitionDamage * 1.5f);
                   result.set(reloading, 600);
               }));
           });
       }};

       reloading = new StatusEffect("reboot"){{
           speedMultiplier = 0;
           disarm = true;
           color = Color.valueOf("047070");
       }};

       azaliyed = new StatusEffect("azaliyed"){{
        color = Color.valueOf("721A1A");
        speedMultiplier = 0.94f;
        effect = Fx.muddy;
        effectChance = 0.09f;
    }};
    }
}