package sunset.content;

import mindustry.content.Items;
import mindustry.ctype.ContentList;
import sunset.world.blocks.payload.Ammunition;

import static mindustry.type.ItemStack.with;

public class SnPayload implements ContentList {

    public static Ammunition

    testRocket;

    @Override
    public void load(){
        testRocket = new Ammunition("guardian-rocket"){{
            requirements = with(Items.copper, 3, Items.lead, 3, Items.titanium, 4);

            size = 3;
        }};
    }
}
