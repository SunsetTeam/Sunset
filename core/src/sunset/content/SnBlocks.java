package sunset.content;

import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.meta.BuildVisibility;
import sunset.content.blocks.*;
import sunset.content.blocks.defense.SnProjectors;
import sunset.content.blocks.defense.SnTurrets;
import sunset.content.blocks.defense.SnWalls;
import sunset.world.blocks.defense.turrets.MissileSiloCommander;

public class SnBlocks implements ContentList {
    public ContentList[] list = {
            new SnProjectors(),
            new SnTurrets(),
            new SnWalls(),

            new SnDistribution(),
            new SnEnvironment(),
            new SnLiquidBlocks(),
            new SnLogicBlocks(),
            new SnPower(),
            new SnCrafting(),
            new SnProduction(),
            new SnUnitBlocks(),
            new SnTests()
    };

    @Override
    public void load() {
        throw new RuntimeException("is must not invoked");
//        for (ContentList blockList : list) {
//            blockList.load();
//        }
    }

    private static final class SnTests implements ContentList {

        @Override
        public void load() {
            new MissileSiloCommander("launch-tower") {{

                requirements(Category.turret, BuildVisibility.sandboxOnly, ItemStack.with(Items.copper, 0));
            }};
        }
    }
}
