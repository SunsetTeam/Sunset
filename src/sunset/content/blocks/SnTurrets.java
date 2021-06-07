package sunset.content.blocks;

import static mindustry.type.ItemStack.with;


import arc.graphics.*;
import arc.struct.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.*;
import arc.math.geom.Vec2;
import sunset.content.SnFx;
import sunset.content.SnBullets;
import sunset.content.SnItems;
import sunset.entities.bullet.EnergySphereBulletType;
import sunset.graphics.SnPal;
import sunset.world.MissileLogic;
import sunset.world.blocks.turrets.ChainLightningTurret;
import sunset.world.blocks.turrets.MissileSiloTurret;
import sunset.world.blocks.turrets.MultiBarrelItemTurret;

public class SnTurrets implements ContentList {
    public static Block

    //turrets
    burner, tetramite, typhoon, chain, field, triden, galebard,
    sunrise, spark, dissector, art,
    spine, partisan, major, admiral;
    @Override
    public void load() {
//1x1
        spine = new ItemTurret("spine") {{
            requirements(Category.turret, with(Items.copper, 55, Items.lead, 25));
            ammo(
                    Items.graphite, SnBullets.lightBlastGraphite,
                    Items.silicon, SnBullets.lightBlastSilicon
            );
            health = 360;
            reloadTime = 78f;
            range = 154f;
            recoilAmount = 2.5f;
            inaccuracy = 2f;
            rotateSpeed = 7.5f;
            shootCone = 2f;
            shootSound = Sounds.bang;
            ammoUseEffect = Fx.casing1;
            targetAir = true;
        }};
//2x2
        partisan = new ItemTurret("partisan") {{
            requirements(Category.turret, with(Items.copper, 100, Items.graphite, 80, Items.lead, 60));
            ammo(
                    Items.graphite, SnBullets.mediumBlastGraphite,
                    Items.silicon, SnBullets.mediumBlastSilicon,
                    Items.blastCompound, SnBullets.mediumBlastBlast
            );
            health = 920;
            size = 2;
            reloadTime = 96f;
            range = 187f;
            recoilAmount = 2.5f;
            inaccuracy = 1.5f;
            rotateSpeed = 6f;
            shootCone = 2f;
            shootSound = Sounds.shootBig;
            ammoUseEffect = Fx.casing1;
            targetAir = true;
        }};
//3x3
        major = new MultiBarrelItemTurret("major") {{
            requirements(Category.turret, with(Items.plastanium, 175, Items.titanium, 75, Items.metaglass, 50));
            ammo(
                    Items.blastCompound, SnBullets.bigBlastBlast,
                    Items.pyratite, SnBullets.bigBlastPyratite,
                    Items.plastanium, SnBullets.bigBlastPlastanium
            );
            shots = 2;
            barrelPoints = new Seq<>(new Vec2[]{
                new Vec2(30f/96f, 4f/96f),
                new Vec2(66f/96f, 4f/96f)
            });
            ejectPoints = new Seq<>(new Vec2[]{
                new Vec2(33f/96f, 40f/96f),
                new Vec2(63f/96f, 40f/96f)
            });
            health = 1960;
            size = 3;
            reloadTime = 141f;
            range = 237f;
            recoilAmount = 3f;
            inaccuracy = 4f;
            burstSpacing = 18f;
            rotateSpeed = 4f;
            shootCone = 2f;
            ammoPerShot = 2;
            shootSound = Sounds.shootBig;
            ammoUseEffect = Fx.casing2;
            targetAir = false;
        }};

        burner = new ItemTurret("burner") {{
            requirements(Category.turret, with(Items.metaglass, 50, Items.lead, 175, Items.graphite, 25));
            ammo(
              Items.coal, SnBullets.heavyCoalFlame,
              Items.pyratite, SnBullets.heavyPyraFlame,
              Items.blastCompound, SnBullets.blastFlame,
              SnItems.flameid, SnBullets.flameidFlame
            );
            health = 140 * size * size;
            size = 3;
            range = 100f;
	        reloadTime = 5f;
	        recoilAmount = 2f;
            inaccuracy = 3f;
	        rotateSpeed = 7.5f;
            shootCone = 25f;
            targetAir = true;
	        shootSound = Sounds.flame;
    }};

        tetramite = new TractorBeamTurret("tetramite"){{
            requirements(Category.turret, with(Items.silicon, 120, Items.titanium, 90, Items.graphite, 30));

            hasPower = true;
            size = 3;
            force = 8f;
            scaledForce = 7f;
            range = 300f;
            damage = 0.4f;
            health = 150 * size * size;
            rotateSpeed = 8;

            consumes.powerCond(5f, (TractorBeamBuild e) -> e.target != null);
        }};
// 4x4
        admiral = new MultiBarrelItemTurret("admiral") {{
            requirements(Category.turret, with(Items.copper, 1100, Items.titanium, 800, Items.silicon, 600, Items.surgeAlloy, 300));
            ammo(
                    Items.blastCompound, SnBullets.maxBlastBlast,
                    Items.plastanium, SnBullets.maxBlastPlastanium,
                    Items.surgeAlloy, SnBullets.maxBlastSurge
            );
            shots = 3;
            barrelPoints = new Seq<>(new Vec2[]{
                new Vec2(32f/128f, 10f/128f),
                new Vec2(64f/128f, 3f/128f),
                new Vec2(96f/128f, 10f/128f),
            });
            ejectPoints = new Seq<>(new Vec2[]{
                new Vec2(32f/128f, 47f/128f),
                new Vec2(64f/128f, 40f/128f),
                new Vec2(96f/128f, 47f/128f),
            });
            health = 3200;
            size = 4;
            reloadTime = 186f;
            range = 342f;
            recoilAmount = 6f;
            inaccuracy = 3f;
            burstSpacing = 12f;
            rotateSpeed = 3f;
            shootCone = 4f;
            ammoPerShot = 3;
            shootSound = Sounds.artillery;
            ammoUseEffect = Fx.casing2;
            targetAir = false;
        }};

        typhoon = new LiquidTurret("typhoon"){{
            requirements(Category.turret, with(Items.metaglass, 200, Items.lead, 530, Items.titanium, 340, Items.thorium, 170));
            ammo(
                Liquids.water, SnBullets.typhoonWaterShot,
                Liquids.slag, SnBullets.typhoonSlagShot,
                Liquids.cryofluid, SnBullets.typhoonCryoShot,
                Liquids.oil, SnBullets.typhoonOilShot
            );
            size = 4;
            reloadTime = 2f;
            shots = 2;
            liquidCapacity = 60;
            velocityInaccuracy = 0.15f;
            inaccuracy = 6f;
            recoilAmount = 1f;
            restitution = 0.05f;
            shootCone = 46f;
            liquidCapacity = 70f;
            shootEffect = Fx.shootLiquid;
            range = 230f;
            health = 200 * size * size;
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
        }};

        chain = new ChainLightningTurret("chain"){{
            range = 168.0f;
            damage = 8.75f;
            health = 2800;
            size = 4;
            shootCone = 8f;
            damageMultiplier = 0.85f;
            coolantMultiplier = 1.2f;
            rotateSpeed = 6.75f;
            powerUse = 16f;
            liquidUse = 0.5f;
            laserColor = SnPal.chainLaser;
        }};
//5x5
       field = new ItemTurret("field"){{
        requirements(Category.turret, with(Items.copper, 1200, Items.lead, 800, Items.plastanium, 350, Items.thorium, 400, SnItems.fors, 400));
        ammo(
            SnItems.fors, SnBullets.artilleryFors,
            Items.blastCompound, SnBullets.artilleryBlast
        );
        targetAir = true;
        targetGround = true;
        itemCapacity = 40;
            size = 5;
            shots = 1;
            inaccuracy = 4f;
            reloadTime = 50f;
            ammoEjectBack = 4f;
            ammoUseEffect = Fx.casing3Double;
            cooldown = 0.06f;
            velocityInaccuracy = 0.2f;
            restitution = 0.02f;
            recoilAmount = 4f;
            shootShake = 2f;
            range = 270f;
            minRange = 30f;

        health = 160 * size * size;
       }};

//6x6
        triden = new PowerTurret("triden"){{
            health = 5400;
            range = 247f;
            size = 6;
            chargeTime = 210f;
            chargeMaxDelay = 0f;
            reloadTime = 460f;
            recoilAmount = 8f;
            chargeEffects = 1;
            powerUse = 38f;
            chargeEffect = SnFx.tridenCharge;
            category = Category.turret;
            buildVisibility = BuildVisibility.shown;
            shootType = new EnergySphereBulletType(1.75f, 240f){{
                hitSize = 8f;
                splashDamage = 1280f;
                splashDamageRadius = 112f;
                lightningDamage = 48f;
                lightningPeriod = 30f;
                lightningLength = 192;
                healPercent = 5f;
                lifetime = 250f;
                hitEffect = SnFx.tridenHit;
            }};
        }};

//7x7
        galebard = new PowerTurret("galebard"){{
            requirements(Category.turret, with(Items.copper, 1200,  Items.metaglass, 600, Items.lead, 800, Items.silicon, 700, Items.plastanium, 400, SnItems.nobium, 300));
            range = 350f;
            chargeTime = 145f;
            rotateSpeed = 1.6F;
            chargeMaxDelay = 140f;
            chargeEffects = 15;
            recoilAmount = 8f;
            reloadTime = 345f;
            cooldown = 10f;
            powerUse = 24f;
            shootShake = 7f;
            shootEffect = Fx.lancerLaserShoot;
            smokeEffect = Fx.none;
            chargeEffect = SnFx.galebardLaserCharge;
            chargeBeginEffect = SnFx.galebardLaserChargeBegin;
            heatColor = Color.red;
            size = 7;
            health = 170 * size * size;
            targetGround = true;
            shootSound = Sounds.laser;

            shootType = new LaserBulletType(1730){{
                colors = new Color[]{Pal.lancerLaser.cpy().a(0.4f), Pal.lancerLaser, Color.white};
                hitEffect = SnFx.hitGalebard;
                despawnEffect = Fx.none;
                lifetime = 60f;
                drawSize = 440f;
                collidesAir = false;
                length = 370f;
                width = 50.0F;
            }};
        }};

//missle
        sunrise = new MissileSiloTurret("sunrise"){{
            size = 2;
            itemCapacity = 240;
            missile = new MissileLogic.MissileType(){{
                damage = 0f;
                splashDamage = 590f;
                splashDamageRadius = 98f;
                speed = 1.75f;
                explodeEffect = Fx.massiveExplosion;
            }};
            craftTime = 1200f;
            consumes.items(with(Items.silicon, 60, Items.graphite, 90, Items.pyratite, 120));
            consumes.power(1.75f);
            health = 320;
            maxRange = 368.0f;
            minRange = 96.0f;
            category = Category.turret;
            buildVisibility = BuildVisibility.shown;
        }};
        spark = new MissileSiloTurret("spark"){{
            size = 3;
            itemCapacity = 900;
            missile = new MissileLogic.MissileType(){{
                damage = 0f;
                splashDamage = 4400f;
                splashDamageRadius = 129f;
                speed = 1.5f;
                explodeEffect = Fx.massiveExplosion;
            }};
            craftTime = 4200f;
            consumes.items(with(Items.graphite, 270, Items.silicon, 360, Items.plastanium, 450, Items.blastCompound, 540));
            consumes.power(4.5f);
            health = 720;
            maxRange = 434.0f;
            minRange = 168.0f;
            category = Category.turret;
            buildVisibility = BuildVisibility.shown;
        }};
        dissector = new MissileSiloTurret("dissector"){{
            size = 4;
            itemCapacity = 2400;
            missile = new MissileLogic.MissileType(){{
                damage = 0f;
                splashDamage = 18600f;
                splashDamageRadius = 154f;
                speed = 1.25f;
                explodeEffect = Fx.massiveExplosion;
            }};
            craftTime = 16800f;
            consumes.items(with(Items.phaseFabric, 440, Items.silicon, 620, Items.surgeAlloy, 800, Items.thorium, 980, Items.plastanium, 1200));
            consumes.power(16f);
            health = 1280;
            maxRange = 524.0f;
            minRange = 248.0f;
            category = Category.turret;
            buildVisibility = BuildVisibility.shown;
        }};
        art = new MissileSiloTurret("art"){{
            size = 5;
            itemCapacity = 2400;
            float p1 = 53f / 162f, p2 = 109f / 162f;
            rockets = new Seq<>(new Vec2[] {
                    new Vec2(p1, p1),
                    new Vec2(p1, p2),
                    new Vec2(p2, p1),
                    new Vec2(p2, p2)
            });
            missile = new MissileLogic.MissileType(){{
                damage = 0f;
                splashDamage = 7200f;
                splashDamageRadius = 137f;
                speed = 1f;
                explodeEffect = Fx.massiveExplosion;
            }};
            craftTime = 4500f;
            consumes.items(with(Items.plastanium, 180, Items.silicon, 270, Items.surgeAlloy, 90, Items.thorium, 330));
            consumes.power(7.5f);
            health = 2000;
            maxRange = 578.0f;
            minRange = 276.0f;
            category = Category.turret;
            buildVisibility = BuildVisibility.shown;
        }};
    }
}
