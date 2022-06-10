package sunset.content;

import mindustry.content.Items;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.meta.BuildVisibility;
import sunset.content.blocks.*;
import sunset.content.blocks.defense.SnProjectors;
import sunset.content.blocks.defense.SnTurrets;
import sunset.content.blocks.defense.SnWalls;
import sunset.entities.bullet.LightningContinuousLaserBulletType;
import sunset.world.blocks.defense.turrets.MissileSiloCommander;
import sunset.world.blocks.defense.turrets.ModItemTurret;

import static mindustry.type.ItemStack.with;

public class SnBlocks implements Runnable{
    public Runnable[] list = {
    SnProjectors::load,
    SnTurrets::load,
    SnWalls::load,

    SnCore::load,
    SnDistribution::load,
    SnEnvironment::load,
    SnLiquidBlocks::load,
    //SnLogicBlocks::load,
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

    private static final class SnTests {
        private static BulletType testBullet0,
        testBullet1,
        testBullet2,
        testBullet3,
        testBullet4;

        private static void loadBullets(){
            testBullet0 = new BasicBulletType(1, 1){{
                lifetime = 400f;
                hitEffect = SnFx.unused1;
                drag = -0.003f;
                homingRange = 10f;
                homingPower = 0.2f;
                ammoMultiplier = 2;
            }};
            testBullet1 = new ArtilleryBulletType(5, 100){{
                fragBullets = 6;
                fragBullet = new LaserBulletType(50);
                fragRandomSpread = 360;
            }};
            testBullet2 = new BasicBulletType(5, 50){{
                fragBullets = 3;
                fragRandomSpread = 45;
                fragBullet = new BasicBulletType(5, 50){{
                    fragBullets = 3;
                    fragRandomSpread = 45;
                    fragBullet = new BasicBulletType(5, 50){{
                        fragBullets = 3;
                        fragRandomSpread = 45;
                        fragBullet = new BasicBulletType(5, 50){{
                            fragBullets = 3;
                            fragRandomSpread = 45;
                            fragBullet = new BasicBulletType(5, 50){{
                                fragBullets = 3;
                                fragRandomSpread = 45;
                                fragBullet = new BasicBulletType(5, 50){{
                                    fragBullets = 3;
                                    fragRandomSpread = 45;
                                    fragBullet = new BasicBulletType(5, 50){{
                                        fragBullets = 3;
                                        fragRandomSpread = 45;
                                        fragBullet = new BasicBulletType(5, 50){{
                                            fragBullets = 3;
                                            fragRandomSpread = 45;
                                            fragBullet = new BasicBulletType(5, 50){{
                                                fragBullets = 3;
                                                fragRandomSpread = 45;
                                                fragBullet = new BasicBulletType(5, 50){{
                                                    fragBullets = 360;
                                                    fragRandomSpread = 360;
                                                    fragBullet = new LaserBulletType(100);
                                                }};
                                            }};
                                        }};
                                    }};
                                }};
                            }};
                        }};
                    }};
                }};
            }};
            testBullet3 = new LightningContinuousLaserBulletType(40){{
                length = 1140f;
                width = 13f;
                lightning = 7;
                lightningLength = 20;
                lightningDamage = 30f;
                lifetime = 40f;
                hitEffect = SnFx.unused2;
                drag = -0.003f;
                homingRange = 10f;
                homingPower = 0.2f;
            }};
            testBullet4 = null;
        }

        public static void load(){
            loadBullets();
            //region testing
            new ModItemTurret("test-turret"){{
                requirements(Category.turret, with(Items.copper, 2));

                ammo(
                Items.copper, testBullet0,
                Items.lead, testBullet1,
                Items.metaglass, testBullet2,
                Items.graphite, testBullet3,
                Items.sand, testBullet4
                );
                health = 780;
                size = 2;
                reload = 24f;
                range = 370f;
                recoil = 0.3f;
                inaccuracy = 1.1f;
                rotateSpeed = 7f;
                shootSound = Sounds.pew;
                targetAir = true;
                unlocked = true;
                alwaysUnlocked = true;
                hideDetails = false;
            }};
            //endregion testing
            new MissileSiloCommander("launch-tower"){{

                requirements(Category.turret, BuildVisibility.sandboxOnly, ItemStack.with(Items.copper, 0));
            }};
        }
    }
}
