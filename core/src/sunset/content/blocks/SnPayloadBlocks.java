package sunset.content.blocks;

import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import sunset.world.blocks.payload.Ammunition;

import static mindustry.type.ItemStack.with;
//TODO renaming
public class SnPayloadBlocks {

    public static Block

    //missiles
    zeusRocket;

    public static void load(){

        zeusRocket = new Ammunition("zeus-missile"){{
            buildVisibility = BuildVisibility.sandboxOnly;
            category = Category.units;
            explosionRadius = 30;
            explosionDamage = 40;
            size = 3;
        }};
    }
}
