package sunset.content.blocks;

import mindustry.Vars;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.logic.LogicBlock;
import sunset.content.SnItems;

import static mindustry.type.ItemStack.with;

public class SnLogicBlocks  {
    public static Block

    naturiteProcessor;

    public static void load() {
    naturiteProcessor = new LogicBlock("naturite-processor") {{
        instructionsPerTick = 13;
        range = 25.0f* Vars.tilesize;
        size = 1;
        requirements(Category.logic, with(Items.lead, 370, Items.silicon, 140, Items.graphite, 120, SnItems.naturite, 70));
    }};
}
}
