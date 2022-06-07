package sunset.content.blocks;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.AttributeCrafter;
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
    firstDrill;

    public static void load(){
        mechanicalWaterExtractor = new SolidPump("mechanical-water-extractor"){{
            requirements(Category.production, with(Items.metaglass, 30, Items.graphite, 30, Items.lead, 30, SnItems.fors, 30));
            result = SnLiquids.burheyna;
            pumpAmount = 0.12f;
            size = 2;
            liquidCapacity = 35f;
            rotateSpeed = 1.5f;
            attribute = SnAttribute.burheyna;
            envRequired |= Env.groundWater;

            consumePower(2f);
        }};

        //region crafters
        //endregion crafters
        //region drills
        firstDrill = new DRDrill("first-drill"){{
            requirements(Category.production, with(SnItems.fors, 30, SnItems.erius, 15));
            drillTime = 305;
            size = 2;
            hasPower = true;
            tier = 3;
            consumePower(0.65f);
            consumeLiquid(SnLiquids.messira, 3f / 60f).boost();
            m1 = 4;
        }};
        //endregion drills
    }
}
