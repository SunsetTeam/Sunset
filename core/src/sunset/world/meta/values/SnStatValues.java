package sunset.world.meta.values;

import arc.Core;
import mindustry.content.StatusEffects;
import mindustry.gen.Tex;
import mindustry.type.StatusEffect;
import mindustry.world.meta.StatValue;

public class SnStatValues{
    /*public static StatValue EMPShot(float damage, float maxTargets, StatusEffect status){
        return table -> {
            table.row();
            table.table(t -> {
                t.left().defaults().padRight(3).left();

                t.add(Core.bundle.format("bullet.lightning", maxTargets, damage));
                t.row();

                if(status != StatusEffects.none){
                    t.add((status.minfo.mod == null ? status.emoji() : "") + "[stat]" + status.localizedName);
                }
            }).padTop(-9).left().get().background(Tex.underline);
        };
    }*/

}
