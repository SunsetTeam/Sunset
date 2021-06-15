package sunset.content;

import arc.*;
import arc.graphics.*;
import arc.math.*;

import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.game.EventType.*;
import mindustry.type.*;
import mindustry.graphics.*;


import static mindustry.Vars.*;


public class SnStatusEffects implements ContentList{
    public static StatusEffect frostbite;

    @Override
    public void load(){

        frostbite = new StatusEffect("frostbite"){{
            color = Color.valueOf("6ecdec");
            damage = 0.20f;
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
    }
}

Heal = new StatusEffect("heal"){{
            color = Pal.heal
            damage = -20f;
            speedMultiplier = 1f;
            healthMultiplier = 1f;
            //effect = Fx.heal
            transitionDamage = 18f;

        init(() -> {
                opposite(StatusEffects.blasted);
                affinity(StatusEffects.overlock, ((unit, time, newTime, result) -> {
                    unit.damagePierce(transitionDamage);
                    result.set(frostbite, time);
                }));
            });
        }};
    }
}
