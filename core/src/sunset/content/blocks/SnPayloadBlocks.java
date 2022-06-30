package sunset.content.blocks;

import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import sunset.world.blocks.payload.Ammunition;

import static mindustry.type.ItemStack.with;

public class SnPayloadBlocks {

    public static Block

    //missiles
    zeusRocket;

    public static void load(){

        zeusRocket = new Ammunition("zeus-missile"){{
            buildVisibility = BuildVisibility.sandboxOnly;
            explosionRadius = 30;
            explosionDamage = 40;
        }};
    }
}
