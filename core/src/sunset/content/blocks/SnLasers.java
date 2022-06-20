package sunset.content.blocks;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;
import mindustry.world.meta.BuildVisibility;
import mma.world.draw.MultiDrawBlock;
import sunset.content.SnFx;
import sunset.world.blocks.laser.*;

public class SnLasers {
    public static Block laserNode, laserSource /*, rotato */ /*, laserPrism *//*, laserWall*/, laserKiln;

    public static void load() {
        laserSource = new LaserSource("laser-source"){{
            requirements(Category.distribution, ItemStack.with(Items.copper, 1));
            size = 2;
            buildVisibility = BuildVisibility.sandboxOnly;
            inputsLaser = false;
            outputsLaser = true;
            nodeHitEffect = SnFx.alsLaserHit;
        }};
        laserNode = new LaserNode("laser-node"){{
            requirements(Category.distribution, ItemStack.with(Items.copper, 1));
            size = 2;
            buildVisibility = BuildVisibility.sandboxOnly;
            inputsLaser = true;
            outputsLaser = true;
            heats = true;
            nodeHitEffect = SnFx.alsLaserHit;
        }};
        laserKiln = new LaserCrafter("laser-kiln"){{
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
            drawer = new DrawMulti(new DrawRegion("-bottom"), new LaserKilnDrawer(){{
                startColor = Pal.darkerGray;
                midColor = Pal.darkPyraFlame;
                endColor = Pal.lightPyraFlame;
            }}, new DrawDefault());
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
