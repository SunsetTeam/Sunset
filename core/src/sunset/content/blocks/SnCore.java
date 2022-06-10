package sunset.content.blocks;

import mindustry.world.*;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.type.Category;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.BuildVisibility;
import sunset.content.SnItems;
import sunset.content.SnUnitTypes;

import static mindustry.type.ItemStack.with;

public class SnCore {
    public static Block testCore;

    public static void load(){
        testCore = new CoreBlock("test-core"){{
            requirements(Category.effect, BuildVisibility.sandboxOnly, with(SnItems.fors, 1000, SnItems.erius, 1000));
            alwaysUnlocked = true;
            isFirstTier = true;

            size = 4;
            health = 3300;
            armor = 7f;
            itemCapacity = 3000;
            thrusterLength = 40/4f;

            unitType = SnUnitTypes.petal;
            unitCapModifier = 11;
        }};
        /* multiUnitCore = new ZalupahiuCoreBlock(name "tvoya-mamka")
        * summon = new ZalupahiuUnitType
        * /kill @e */
    }
}
