package sunset.content.blocks;

import mindustry.content.Items;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.liquid.ArmoredConduit;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.liquid.LiquidJunction;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.production.Pump;
import mma.ModVars;
import sunset.content.SnItems;

import static mindustry.type.ItemStack.with;

public class SnLiquidBlocks  {
    public static Block
    //transportation
    naturiteConduit, advancedConduit,
    naturiteJunction, naturiteRouter;

    public static void load() {

        //region transportation
        naturiteConduit = new Conduit("naturite-conduit") {{
            requirements(Category.liquid, with(SnItems.naturite, 1, SnItems.erius, 1));
            liquidCapacity = 18f;
            liquidPressure = 1.035f;
            leaks = true;
            health = 170;
            placeableLiquid = true;
            hasShadow = false;
            floating = true;
        }};

        advancedConduit = new ArmoredConduit("advanced-conduit") {{
            requirements(Category.liquid, with(SnItems.nobium, 2, SnItems.erius, 1));
            liquidCapacity = 20f;
            liquidPressure = 1.050f;
            health = 290;
        }};

        naturiteJunction = new LiquidJunction("naturite-liquid-junction"){{
            requirements(Category.liquid, with(SnItems.naturite, 6, SnItems.erius, 4));

            ((Conduit)naturiteConduit).junctionReplacement = this;
            ((Conduit)advancedConduit).junctionReplacement = this;
            solid = false;
            health = 200;
        }};

        naturiteRouter = new LiquidRouter("naturite-liquid-router"){{
            requirements(Category.liquid, with(SnItems.naturite, 5, SnItems.erius, 7));
            liquidCapacity = 25f;
        }};
        //endregion transportation
    }
}
