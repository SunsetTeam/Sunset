package sunset.content;

import arc.util.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import sunset.content.blocks.*;
import sunset.content.blocks.defense.*;
import sunset.entities.bullet.*;
import sunset.world.blocks.defense.turrets.*;

import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;

public class SnBlocks implements Runnable{
    public Runnable[] list = {
    SnPayloadBlocks::load,
    SnProjectors::load,
    SnTurrets::load,
    SnWalls::load,

    SnOtherBlocks::load,
    SnDistribution::load,
    SnEnvironment::load,
    SnLiquidBlocks::load,
    SnLogicBlocks::load,
    SnPower::load,
    SnCrafting::load,
    SnProduction::load,
    SnSandbox::load,
    SnUnitBlocks::load,
    SnLasers::load,
    SnGasBlocks::load,
    SnTests::load,
    };

    public static void load(){
        throw new RuntimeException("is must not invoked");
//        for (ContentList blockList : list) {
//            blockList.load();
//        }
    }

    @Override
    public void run(){
        load();
    }

    private static final class SnTests{
        private static BulletType testBullet0,
        testBullet1,
        testBullet2,
        testBullet3,
        testBullet4;

        private static void loadBullets(){
        }

        public static void load(){
            loadBullets();
            new MagneticTurret("concent"){{
                requirements(Category.turret, with(Items.copper, 2));
                bulletType.speed=2f;
                bulletType.damage=10f;
                bulletType.splashDamage = 26f * 1.5f;
                bulletType.splashDamageRadius = 1f*tilesize;
                
                bulletRadius=4*tilesize;
//                bulletType.=10f;
                totalSize=10;

                buildTime = 10 * Time.toSeconds;
                consumeItem(Items.copper, 20);
                health = 780;
                size = 2;
                reload = 24f;
                range = 46.25f*tilesize;
                recoil = 0.3f;
                inaccuracy = 1.1f;
                rotateSpeed = 7f;
                shootSound = Sounds.pew;
                targetAir = true;
                unlocked = true;
                alwaysUnlocked = true;
                hideDetails = false;
                itemCapacity=40;
            }};
            //region testing
            //endregion testing
            new MissileSiloCommander("launch-tower"){{

                requirements(Category.turret, BuildVisibility.sandboxOnly, ItemStack.with(Items.copper, 0));
            }};
        }
    }
}
