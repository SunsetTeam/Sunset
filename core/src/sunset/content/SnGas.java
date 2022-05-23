package sunset.content;

import arc.graphics.Color;
import gas.type.Gas;
import mindustry.content.StatusEffects;

public class SnGas  {
    public static Gas hyneris, gyner, arhelinium;

    public static void load() {
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
        arhelinium = new Gas("arhelinium") {{
           color = Color.valueOf("d18ffa"); //the color will be changed :)

        }};
    }
}
