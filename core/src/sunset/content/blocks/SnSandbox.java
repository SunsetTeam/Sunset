package sunset.content.blocks;

import mindustry.Vars;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.logic.LogicBlock;
import mindustry.world.blocks.logic.LogicDisplay;
import mindustry.world.meta.BuildVisibility;
import sunset.world.blocks.sandbox.SnMultiSource;
import sunset.world.blocks.sandbox.SnMultiVoid;

public class SnSandbox {
    public static Block
    //main
    multiSource, multiVoid, superProcessor, superDisplay;

    public static void load() {
        //region main
        multiSource = new SnMultiSource("multi-source"){{
            requirements(Category.distribution, BuildVisibility.sandboxOnly, ItemStack.empty);
            hideDetails = false;
        }};

        multiVoid = new SnMultiVoid("multi-void"){{
            requirements(Category.distribution, BuildVisibility.sandboxOnly, ItemStack.empty);
            hideDetails = false;
        }};

        superProcessor = new LogicBlock("super-processor"){{
            requirements(Category.logic, BuildVisibility.sandboxOnly, ItemStack.empty);

            size = 1;
            range = 50 * Vars.tilesize;
            instructionsPerTick = 99999;
            maxInstructionScale = 99999;
            hideDetails = false;
        }};

        superDisplay = new LogicDisplay("super-display"){{
            requirements(Category.logic, BuildVisibility.sandboxOnly, ItemStack.empty);

            displaySize = 240;
            size = 9;
            hideDetails = false;
        }};
        //endregion main
    }
}