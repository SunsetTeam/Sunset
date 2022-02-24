package sunset.content.blocks;

import mindustry.Vars;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.logic.LogicBlock;
import mindustry.world.blocks.logic.LogicDisplay;
import mindustry.world.meta.BuildVisibility;
import sunset.world.blocks.sandbox.InfoTestBlock;
import sunset.world.blocks.sandbox.SnMultiSource;
import sunset.world.blocks.sandbox.SnMultiVoid;

public class SnSandbox implements ContentList {
    public static Block

    multiSource, multiVoid, superProcessor, superDisplay,
    info;

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

        info = new InfoTestBlock("info") {{
            size = 3;
            health = 300;
        }};
    }
}