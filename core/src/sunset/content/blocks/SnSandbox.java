package sunset.content.blocks;

import mindustry.Vars;
import mindustry.ctype.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.logic.LogicBlock;
import mindustry.world.blocks.logic.LogicDisplay;
import mindustry.world.meta.*;
import sunset.world.blocks.sandbox.SnMultiSource;
import sunset.world.blocks.sandbox.SnMultiVoid;

public class SnSandbox implements ContentList {
    public static Block

    multiSource, multiVoid, darkMatterProcessor, darkMatterDisplay;

    @Override
    public void load() {
        multiSource = new SnMultiSource("multi-source"){{
            requirements(Category.distribution, BuildVisibility.sandboxOnly, ItemStack.empty);
            hideDetails = false;
        }};

        multiVoid = new SnMultiVoid("multi-void"){{
            requirements(Category.distribution, BuildVisibility.sandboxOnly, ItemStack.empty);
            hideDetails = false;
        }};

        darkMatterProcessor = new LogicBlock("dark-matter-processor"){{
            requirements(Category.logic, BuildVisibility.sandboxOnly, ItemStack.empty);

            size = 1;
            range = 50 * Vars.tilesize;
            instructionsPerTick = 99999;
            maxInstructionScale = 99999;
            hideDetails = false;
        }};

        darkMatterDisplay = new LogicDisplay("dark-matter-display"){{
            requirements(Category.logic, BuildVisibility.sandboxOnly, ItemStack.empty);

            displaySize = 240;
            size = 9;
            hideDetails = false;
        }};
}
}