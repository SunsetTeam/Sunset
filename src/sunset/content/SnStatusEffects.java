package sunset.content;

import arc.graphics.*;

import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.type.*;

public class SnStatusEffects implements ContentList{
    public static StatusEffect frostbite, stun;

    @Override
    public void load(){

        frostbite = new StatusEffect("frostbite"){{
            color = Color.valueOf("6ecdec");
            damage = 0.18f;
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
    }
}