package sunset.content.blocks;

import mindustry.content.Items;
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

public class SnDistribution {
    public static Block
            //transportation
            nobiumConveyor,

            //special
            airport, itemTeleporter;

    public static void load() {
        //region transportation
        nobiumConveyor = new StackConveyor("nobium-conveyor") {{
            requirements(Category.distribution, with(SnItems.nobium, 1, Items.silicon, 1, Items.graphite, 1));
            health = 80;
            speed = 6f / 60f;
            itemCapacity = 15;
        }};
        //endregion transportation
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
            requirements(Category.distribution, with(Items.copper, 1));
            itemCapacity = 110;
            health = 100;
            hasItems = true;
            configurable = true;
            range = 15f;
           consumePower(5f);
        }};
        //endregion special
    }
}
