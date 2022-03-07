package sunset.content;

import arc.graphics.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.type.*;

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
