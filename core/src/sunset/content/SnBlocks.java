package sunset.content;

import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.gen.*;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.meta.BuildVisibility;
import sunset.content.blocks.*;
import sunset.content.blocks.defense.SnProjectors;
import sunset.content.blocks.defense.SnTurrets;
import sunset.content.blocks.defense.SnWalls;
import sunset.world.blocks.defense.turrets.*;

import static mindustry.type.ItemStack.*;

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
            new SnSandbox(),
            new SnUnitBlocks(),
            new SnTests(),
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
            //region testing
            new ModItemTurret("test-turret"){{
                requirements(Category.turret, with(Items.copper, 2));
                ammo(
                Items.copper, SnBullets.testBullet,
                Items.lead, SnBullets.testBullet0,
                Items.metaglass, SnBullets.testBullet1,
                Items.graphite, SnBullets.testBullet2,
                Items.sand, SnBullets.testBullet3
                );
                health = 780;
                size = 2;
                reloadTime = 24f;
                range = 370f;
                recoilAmount = 0.3f;
                inaccuracy = 1.1f;
                rotateSpeed = 7f;
                shootSound = Sounds.pew;
                targetAir = true;
                unlocked = true;
                alwaysUnlocked = true;
            }};
            //endregion testing
            new MissileSiloCommander("launch-tower") {{

                requirements(Category.turret, BuildVisibility.sandboxOnly, ItemStack.with(Items.copper, 0));
            }};
        }
    }
}
