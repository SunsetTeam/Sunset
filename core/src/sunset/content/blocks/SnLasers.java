package sunset.content.blocks;

import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import sunset.world.blocks.laser.*;

public class SnLasers{
    public static Block laserNode, laserSource /*, rotato */ /*, laserPrism */;

    public static void load(){
        laserSource = new LaserSource("laser-starter"){{
            requirements(Category.distribution, ItemStack.with(Items.copper, 1));
            size = 2;
            buildVisibility = BuildVisibility.sandboxOnly;
            inputsLaser = false;
            outputsLaser = true;
        }};
        laserNode = new LaserNode("laser-node"){{
            requirements(Category.distribution, ItemStack.with(Items.copper, 1));
            size = 2;
            buildVisibility = BuildVisibility.sandboxOnly;
            inputsLaser = true;
            outputsLaser = true;
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
