package sunset.content;

import arc.graphics.Color;
import gas.type.Gas;
import mindustry.content.StatusEffects;

public class SnGas  {
    public static Gas hyneris, gyner, arhelinium;

    public static void load() {
        hyneris = new Gas("hyneris") {{
            color = Color.valueOf("ff9098");
            explosiveness = 0.07f;
            temperature = 0.3f;
            effect = StatusEffects.wet;
        }};

        gyner = new Gas("gyner") {{
            color = Color.valueOf("d0e2ff");
            explosiveness = 0.1f;
            flammability = 0.1f;
            temperature = 0.4f;
            effect = StatusEffects.wet;
        }};
        arhelinium = new Gas("arhelinium") {{
           color = Color.valueOf("eea2f5");

        }};
    }
}
