package sunset.content;

import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import sunset.content.blocks.*;
import sunset.content.blocks.defense.*;
import sunset.entities.bullet.*;
import sunset.world.blocks.defense.turrets.*;

import static mindustry.type.ItemStack.*;

public class SnBlocks implements ContentList{
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
    new SnLasers(),
    new SnTests(),
    };

    @Override
    public void load(){
        throw new RuntimeException("is must not invoked");
//        for (ContentList blockList : list) {
//            blockList.load();
//        }
    }

    private static final class SnTests implements ContentList{
        private static BulletType testBullet0,
        testBullet1,
        testBullet2,
        testBullet3,
        testBullet4;

        private void loadBullets(){
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
                fragCone = 360;
            }};
            testBullet2 = new BasicBulletType(5, 50){{
                fragBullets = 3;
                fragCone = 45;
                fragBullet = new BasicBulletType(5, 50){{
                    fragBullets = 3;
                    fragCone = 45;
                    fragBullet = new BasicBulletType(5, 50){{
                        fragBullets = 3;
                        fragCone = 45;
                        fragBullet = new BasicBulletType(5, 50){{
                            fragBullets = 3;
                            fragCone = 45;
                            fragBullet = new BasicBulletType(5, 50){{
                                fragBullets = 3;
                                fragCone = 45;
                                fragBullet = new BasicBulletType(5, 50){{
                                    fragBullets = 3;
                                    fragCone = 45;
                                    fragBullet = new BasicBulletType(5, 50){{
                                        fragBullets = 3;
                                        fragCone = 45;
                                        fragBullet = new BasicBulletType(5, 50){{
                                            fragBullets = 3;
                                            fragCone = 45;
                                            fragBullet = new BasicBulletType(5, 50){{
                                                fragBullets = 3;
                                                fragCone = 45;
                                                fragBullet = new BasicBulletType(5, 50){{
                                                    fragBullets = 360;
                                                    fragCone = 360;
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

        @Override
        public void load(){
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
                reloadTime = 24f;
                range = 370f;
                recoilAmount = 0.3f;
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
