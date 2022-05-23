package sunset.content.blocks;

import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.production.Pump;
import mma.ModVars;
import sunset.content.SnItems;
import sunset.world.blocks.distribution.ModConduit;

import static mindustry.type.ItemStack.with;

public class SnLiquidBlocks  {
    public static Block
            //transportation
            naturiteConduit, nobiumConduit,

            //storage
            liquidWell, 
    
            //extraction
            naturitePump;

    public static void load() {

        //region transportation
        naturiteConduit = new Conduit("naturite-conduit") {{
            requirements(Category.liquid, with(Items.titanium, 2, Items.metaglass, 1, SnItems.naturite, 2));
            liquidCapacity = 18f;
            liquidPressure = 1.035f;
            health = 110;
            placeableLiquid = true;
            hasShadow = false;
            floating = true;
        }};

        if (!ModVars.packSprites) {
            nobiumConduit = new Conduit("nobium-conduit") {{
                requirements(Category.liquid, with(SnItems.nobium, 2, Items.metaglass, 1));
                liquidCapacity = 19f;
                liquidPressure = 1.050f;
                health = 100;
            }};
        }
        //endregion transportation
        //region storage
        liquidWell = new LiquidRouter("liquid-well") {{
            requirements(Category.liquid, with(Items.titanium, 110, Items.metaglass, 90, Items.thorium, 75));
            size = 4;
            liquidCapacity = 3100f;
            health = 1270;
        }};
        //endregion storage
        //region special
        naturitePump = new Pump("naturite-pump") {{
            requirements(Category.liquid, with(Items.copper, 120, Items.metaglass, 110, Items.silicon, 40, Items.titanium, 70, SnItems.naturite, 60));
            pumpAmount = 0.24f;
            consumePower(6.9f);
            liquidCapacity = 100f;
            hasPower = true;
            size = 5;
        }};
        //endregion special
    }
}
