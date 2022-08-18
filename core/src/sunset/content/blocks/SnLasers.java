package sunset.content.blocks;

import arc.graphics.*;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.production.*;
import mindustry.world.draw.*;
import mindustry.world.meta.BuildVisibility;
import sunset.content.SnFx;
import sunset.world.blocks.laser.*;

public class SnLasers {
    public static Block laserNode, laserSource /*, rotato */ /*, laserPrism */, laserWall, laserKiln;

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
        laserWall = new LaserWall("laser-wall"){{
            requirements(Category.defense, ItemStack.with(Items.copper, 1));
            size = 2;
            health = 1000;
            buildVisibility = BuildVisibility.sandboxOnly;
            inputsLaser = true;
            outputsLaser = false;
            heats = true;
        }};
        laserKiln = new GenericCrafter("laser-kiln"){{
            requirements(Category.distribution, ItemStack.with(Items.copper, 1));
            size = 5;
            itemCapacity = 15;
            buildVisibility = BuildVisibility.sandboxOnly;
            consume(new ConsumeLaser(10f, true, 50f));
            consumeItems(ItemStack.with(Items.coal, 5, Items.sand, 10));
            outputItems = ItemStack.with(Items.silicon, 15);
            drawer = new DrawMulti(new DrawRegion("-bottom"),new LaserDraw(),new DrawDefault());
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
