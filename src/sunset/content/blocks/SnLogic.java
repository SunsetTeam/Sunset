package sunset.content.blocks;

import mindustry.content.Items;
import mindustry.ctype.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.logic.*;
import sunset.content.SnItems;

import static mindustry.type.ItemStack.*;

public class SnLogic implements ContentList{
    public static Block

    naturitelogicProcessor;

    @Override
    public void load(){
    naturitelogicProcessor = new LogicBlock("logic-processor"){{
        requirements(Category.logic, with(Items.lead, 370, Items.silicon, 140, Items.graphite, 120, SnItems.naturite, 70));

        instructionsPerTick = 8;

        range = 8 * 22;

        size = 2;
    }};
}
}
