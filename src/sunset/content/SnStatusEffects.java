package sunset.content;

import arc.graphics.*;

import arc.graphics.g2d.Draw;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.gen.Unit;
import mindustry.type.*;
import mindustry.graphics.*;
import mindustry.ui.Fonts;
import sunset.type.StackableStatusEffect;

public class SnStatusEffects implements ContentList{
    public static StatusEffect frostbite, stun, starBuff, overheat;
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
            speedMultiplier = 1.3f;
            healthMultiplier = 1.15f;
            damageMultiplier = 1.1f;
            reloadMultiplier = 1.1f;
       }};

       overheat = new StackableStatusEffect("overheat"){{
           maxStacks = 20;
           healthMultipliers.addAll(0.99f, 0.98f, 0.97f, 0.96f, 0.95f, 0.94f,
                                    0.92f, 0.90f, 0.86f, 0.84f, 0.82f,
                                    0.79f, 0.76f, 0.73f, 0.70f,
                                    0.66f, 0.62f, 0.58f,
                                    0.53f, 0.48f);
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
               Draw.z(Layer.end);
               Fonts.chat.draw(String.valueOf(unit.healthMultiplier()), unit.x, unit.y);
           }
       };
    }
}