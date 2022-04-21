package sunset.content;

import arc.graphics.Color;
import gas.type.Gas;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;

public class SnGas implements ContentList {
    public static Gas hyneris, gyner;

    @Override
    public void load() {
        hyneris = new Gas("hyneris") {{
            color = Color.valueOf("E17C7C");
            explosiveness = 0.07f;
            temperature = 0.3f;
            effect = StatusEffects.wet;
        }};

        gyner = new Gas("gyner") {{
            color = Color.valueOf("8FE5FA");
            explosiveness = 0.1f;
            flammability = 0.1f;
            temperature = 0.4f;
            effect = StatusEffects.wet;
        }};
    }
}
