package sunset.content.blocks;

import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.MassDriver;
import mindustry.world.blocks.storage.StorageBlock;
import sunset.content.SnItems;
import sunset.content.SnUnitTypes;
import sunset.world.blocks.units.Airport;

import static mindustry.type.ItemStack.with;

public class SnDistribution implements ContentList {
    public static Block
            //transportation
            enojieDriver,

            //storage
            crypt,

            //special
            airport;

    @Override
    public void load() {
        //transportation
        enojieDriver = new MassDriver("enojiemass-driver") {{
            requirements(Category.distribution, with(Items.titanium, 450, Items.thorium, 200, Items.surgeAlloy, 180, SnItems.nobium, 150, SnItems.enojie, 130));
            size = 5;
            itemCapacity = 210;
            reloadTime = 250f;
            range = 540f;
            consumes.power(4.0f);
        }};

        //storage
        crypt = new StorageBlock("crypt") {{
            requirements(Category.effect, with(Items.titanium, 350, SnItems.naturite, 200));
            size = 5;
            itemCapacity = 3500;
        }};

        //special
        airport = new Airport("airport"){{
            size = 4;
            health = 1000;
            requirements(Category.distribution, with(Items.titanium, 600, Items.silicon, 300));
            maxUnitCount = 6;
            unitBuildTime = 480;
            unitType = SnUnitTypes.courier;
            powerUse = 3.25f;
            itemCapacity = 1000;
            requirements = with(Items.thorium, 60, SnItems.naturite, 40);
        }};
    }
}
