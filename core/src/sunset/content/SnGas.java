package sunset.content;

import arc.graphics.Color;
import gas.type.Gas;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;

public class SnGas implements ContentList {
    public static Gas hyneris;

    @Override
    public void load() {
        hyneris = new Gas("hyneris") {{
            color = Color.valueOf("E17C7C");
            explosiveness = 0.07f;
            temperature = 0.3f;
            effect = StatusEffects.wet;
        }};
    }
}
