package sunset.content.blocks;

import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.MassDriver;
import mindustry.world.blocks.distribution.StackConveyor;
import mindustry.world.blocks.storage.StorageBlock;
import sunset.content.SnItems;
import sunset.content.SnUnitTypes;
import sunset.world.blocks.distribution.ItemTeleporter;
import sunset.world.blocks.units.Airport;

import static mindustry.type.ItemStack.with;

public class SnDistribution implements ContentList {
    public static Block
            //transportation
            enojieDriver, nobiumConveyor,

            //storage
            crypt,

            //special
            airport, itemTeleporter;

    @Override
    public void load() {
        //region transportation
        enojieDriver = new MassDriver("enojie-driver") {{
            requirements(Category.distribution, with(Items.titanium, 450, Items.thorium, 200, Items.surgeAlloy, 180, SnItems.nobium, 150, SnItems.enojie, 130));
            size = 5;
            itemCapacity = 230;
            reloadTime = 240f;
            range = 560f;
            consumes.power(6.0f);
        }};
        nobiumConveyor = new StackConveyor("nobium-conveyor") {{
            requirements(Category.distribution, with(SnItems.nobium, 1, Items.silicon, 1, Items.graphite, 1));
            health = 80;
            speed = 6f / 60f;
            itemCapacity = 15;
        }};
        //endregion transportation
        //region storage
        crypt = new StorageBlock("crypt") {{
            requirements(Category.effect, with(Items.titanium, 350, SnItems.naturite, 200));
            size = 5;
            itemCapacity = 3500;
        }};
        //endregion storage
        //region special
        airport = new Airport("airport") {{
            size = 4;
            health = 1000;
            requirements(Category.distribution, with(Items.titanium, 600, Items.silicon, 300));
            maxUnitCount = 6;
            unitBuildTime = 480;
            unitType = SnUnitTypes.courier;
            powerUse = 3.25f;
            itemCapacity = 1000;
            unitRequirements = with(Items.thorium, 60, SnItems.naturite, 40);
        }};

        itemTeleporter = new ItemTeleporter("item-teleporter"){{
            category = Category.distribution;
            itemCapacity = 110;
            health = 100;
            hasItems = true;
            configurable = true;
        }};
        //endregion special
    }
}
