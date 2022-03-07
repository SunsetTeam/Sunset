package sunset.content;

import arc.graphics.Color;
import gas.type.Gas;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;

public class SnGas implements ContentList {
    public static Gas giard;

    @Override
    public void load() {
        giard = new Gas("giard") {{
            color = Color.valueOf("E17C7C");
            explosiveness = 0.07f;
            temperature = 0.3f;
            effect = StatusEffects.wet;
        }};
    }
}
