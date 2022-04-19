package sunset.content;

import arc.graphics.Color;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;
import mindustry.type.Liquid;

public class SnLiquids implements ContentList {
    public static Liquid burheyna;

    @Override
    public void load() {
     
        burheyna = new Liquid("burheyna", Color.valueOf("cc445b")) {{
            viscosity = 0.7f;
            explosiveness = 0.1f;
            heatCapacity = 0.5f;
            barColor = Color.valueOf("d14960");
            effect = StatusEffects.wet;
        }};
    }
}
