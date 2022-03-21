package sunset.content;

import arc.graphics.Color;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;
import mindustry.type.Liquid;

public class SnLiquids implements ContentList {
    public static Liquid burheyna;

    @Override
    public void load() {
     
        burheyna = new Liquid("burheyna", Color.valueOf("BB2222")) {{
            viscosity = 0.8f;
            explosiveness = 0.1f;
            heatCapacity = 0.5f;
            barColor = Color.valueOf("B52525");
            effect = StatusEffects.wet;
        }};
    }
}
