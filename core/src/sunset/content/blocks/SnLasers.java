package sunset.content.blocks;

import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import sunset.world.blocks.laser.FocusingPrism;
import sunset.world.blocks.laser.LaserNode;
import sunset.world.blocks.laser.LaserSource;
import sunset.world.blocks.laser.RotatableLaserNode;

public class SnLasers implements ContentList {
    public static Block laserNode, laserSource /*, rotato */ /*, laserPrism */;
    @Override
    public void load() {
        laserSource = new LaserSource("laser-starter"){{
            requirements(Category.distribution, ItemStack.with(Items.copper, 1));
            size = 2;
            buildVisibility = BuildVisibility.sandboxOnly;
        }};
        laserNode = new LaserNode("laser-node"){{
            requirements(Category.distribution, ItemStack.with(Items.copper, 1));
            size = 2;
            buildVisibility = BuildVisibility.sandboxOnly;
        }};
        /*
        rotato = new RotatableLaserNode("rotato"){{
            requirements(Category.distribution, ItemStack.with(Items.copper, 1));
            size = 2;
            buildVisibility = BuildVisibility.sandboxOnly;
        }};
         */
        /*
        laserPrism = new FocusingPrism("focusing-prism"){{
            requirements(Category.distribution, ItemStack.with(Items.copper, 1));
            size = 2;
            buildVisibility = BuildVisibility.sandboxOnly;
        }};
         */
    }
}
