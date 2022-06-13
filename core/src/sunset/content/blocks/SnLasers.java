package sunset.content.blocks;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import sunset.world.blocks.laser.*;

public class SnLasers {
    public static Block laserNode, laserSource /*, rotato */ /*, laserPrism *//*, laserWall*/, multikiln;

    public static void load() {
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
            heats = true;
        }};
        multikiln = new LaserCrafter("multikiln"){{
            requirements(Category.distribution, ItemStack.with(Items.copper, 1));
            size = 4;
            buildVisibility = BuildVisibility.sandboxOnly;
            inputsLaser = true;
            outputsLaser = false;

            hasItems = true;
            hasLiquids = true;

            consumeItems(ItemStack.with(Items.sand, 20, Items.coal, 10));
            consumeLiquid(Liquids.nitrogen, 1);
            outputItems = ItemStack.with(Items.silicon, 10);
            outputLiquids = LiquidStack.with(Liquids.hydrogen, 1);
            laserConsumption = 10f;
            laserProduction = 0f;

            itemCapacity = 20;

            craftEffect = Fx.smeltsmoke;
            drawer = new LaserKilnDrawer(){{
                startColor = Pal.darkerGray;
                midColor = Pal.darkPyraFlame;
                endColor = Pal.lightPyraFlame;
            }};
        }};
        /*
        laserWall = new LaserWall("laser-wall"){{
            requirements(Category.defense, ItemStack.with(Items.copper, 1));
            size = 2;
            health = 1000;
            buildVisibility = BuildVisibility.sandboxOnly;
            inputsLaser = true;
            outputsLaser = false;
        }};
         */
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
