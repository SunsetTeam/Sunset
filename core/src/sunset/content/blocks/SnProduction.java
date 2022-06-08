package sunset.content.blocks;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.AttributeCrafter;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.SolidPump;
import mindustry.world.draw.*;
import mindustry.world.meta.Env;
import sunset.content.SnAttribute;
import sunset.content.SnFx;
import sunset.content.SnItems;
import sunset.content.SnLiquids;
import sunset.type.blocks.DrillItem;
import sunset.world.blocks.production.raw.DRDrill;
import sunset.world.blocks.production.raw.PrecussionDrill;

import static mindustry.type.ItemStack.with;

/** This category is for blocks that produce raw products. (Such as cultivator, drill etc.) */
public class SnProduction{
    public static Block
    //pumps
    mechanicalWaterExtractor,

    //drills
    streamlinedDrill, destructiveDrill, thermoPlasmDrill;

    public static void load(){

        //region crafters
        //endregion crafters
        //region drills
        streamlinedDrill = new Drill("streamlined-drill"){{
            requirements(Category.production, with(SnItems.fors, 30, SnItems.erius, 15));
            drillTime = 167;
            size = 2;
            hasPower = true;
            itemCapacity = 20;
            tier = 3;
            consumePower(0.65f);
            consumeLiquid(SnLiquids.burheyna, 3f / 60f).boost();
        }};
        //endregion drills
    }
}
