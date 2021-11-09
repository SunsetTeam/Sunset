package sunset.content.blocks.defense;

import arc.graphics.Color;
import arc.math.geom.Vec2;
import arc.struct.EnumSet;
import arc.struct.Seq;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.LiquidTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.BuildVisibility;
import sunset.content.SnBullets;
import sunset.content.SnFx;
import sunset.content.SnItems;
import sunset.content.SnStatusEffects;
import sunset.entities.bullet.EnergyBoltBulletType;
import sunset.entities.bullet.EnergySphereBulletType;
import sunset.graphics.SnPal;
import sunset.type.MissileType;
import sunset.world.blocks.defense.turrets.*;

import static mindustry.type.ItemStack.with;

public class SnTurrets implements ContentList {
    public static Block
        //1x1
        spine, eagle,
        //2x2
        carbine, pulsation, reflection,
        //3x3
        major, burner, trigger,
        //4x4
        admiral, scorpio, typhoon, flood, tentacle, chain,
        //5x5
        field, somesniper,
        //6x6
        trident, radius,
        //7x7
        halberd,
        //missile
        sunrise, spark, dissector, art,
        //EMP
        discharger, dischargerEvo,
        //synthesis
        synthesisT1, synthesisT2, synthesisT3, synthesisT4,
        //testing
        testturret;

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
        eagle = new ItemTurret("eagle") {{
            requirements(Category.turret, with(Items.copper, 60, Items.titanium, 40, SnItems.naturite, 25));
            ammo(
                    Items.sporePod, SnBullets.sporePodPoisonBullet,
                    SnItems.naturite, SnBullets.naturitePoisonBullet
            );
            health = 350;
            size = 1;
            shots = 3;
            reloadTime = 30f;
            range = 115f;
            recoilAmount = 1f;
            cooldown = 0.02f;
            velocityInaccuracy = 0.2f;
            restitution = 0.01f;
            inaccuracy = 14f;
            rotateSpeed = 8f;
            shootCone = 2f;
            shootSound = Sounds.flame;
            ammoUseEffect = Fx.casing1;
            targetAir = true;
            targetGround = true;
        }};
        
        //2x2
        carbine = new ChainLightningTurret("carbine"){{
            requirements(Category.turret, with(Items.copper, 150, Items.lead, 140, Items.surgeAlloy, 60, SnItems.nobium, 90, SnItems.naturite, 120));
            range = 132.2f;
            damage = 1.6f;
            health = 900;
            size = 2;
            shootCone = 6f;
            damageMultiplier = 0.30f;
            coolantMultiplier = 1.0f;
            rotateSpeed = 8.75f;
            powerUse = 5f;
            liquidUse = 0.3f;
            laserColor = SnPal.chainLaser;
        }};
        pulsation = new ItemTurret("pulsation") {{
            requirements(Category.turret, with(Items.copper, 95, Items.graphite, 85, Items.lead, 70, SnItems.planatrium, 35));
            ammo(
                    Items.lead, SnBullets.leadSap,
                    Items.sporePod, SnBullets.sporeSap,
                    SnItems.planatrium, SnBullets.planatriumSap
            );
            health = 780;
            size = 2;
            reloadTime = 7f;
            range = 140f;
            recoilAmount = 0.3f;
            inaccuracy = 1.1f;
            rotateSpeed = 7f;
            shootSound = Sounds.sap;
            targetAir = true;
            targetGround = false;
        }};
        reflection = new ItemTurret("reflection") {{
            requirements(Category.turret, with(Items.copper, 100, SnItems.fors, 60, SnItems.naturite, 55, SnItems.nobium, 45));
            ammo(
                    Items.sporePod, SnBullets.heavySporePodPoison,
                    SnItems.naturite, SnBullets.heavyNaturitePoison,
                    SnItems.nobium, SnBullets.heavyNobiumPoison
            );
            health = 780;
            size = 2;
            shots = 4;
            reloadTime = 55f;
            range = 160f;
            recoilAmount = 1.1f;
            cooldown = 0.02f;
            velocityInaccuracy = 0.2f;
            restitution = 0.01f;
            inaccuracy = 20f;
            rotateSpeed = 7f;
            shootCone = 2f;
            shootSound = Sounds.flame;
            ammoUseEffect = Fx.casing1;
            targetAir = true;
            targetGround = true;
        }};

        //3x3
        major = new MultiBarrelItemTurret("major") {{
            requirements(Category.turret, with(Items.plastanium, 175, Items.titanium, 75, Items.metaglass, 50, SnItems.naturite, 30));
            ammo(
                    Items.blastCompound, SnBullets.bigBlastBlast,
                    Items.pyratite, SnBullets.bigBlastPyratite,
                    Items.plastanium, SnBullets.bigBlastPlastanium
            );
            shots = 2;
            barrelPoints = Seq.with(
                new Vec2(30f/96f, 4f/96f),
                new Vec2(66f/96f, 4f/96f)
            );
            ejectPoints = Seq.with(
                new Vec2(33f/96f, 40f/96f),
                new Vec2(63f/96f, 40f/96f)
            );
            health = 1960;
            size = 3;
            reloadTime = 130f;
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

            speedupPerShot = 0.08f;
            slowReloadTime = 180f;
        }};
        burner = new ItemTurret("burner") {{
            requirements(Category.turret, with(Items.metaglass, 75, Items.lead, 200, Items.graphite, 100));
            ammo(
              Items.coal, SnBullets.heavyCoalFlame,
              Items.pyratite, SnBullets.heavyPyraFlame,
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
        trigger = new PowerTurret("trigger"){{
            requirements(Category.turret, with(Items.silicon, 275, Items.titanium, 125, SnItems.enojie, 75));
            size = 3;
            powerUse = 9f;
            range = 340f;
            reloadTime = 18f;
            recoilAmount = 0f;
            targetAir = true;
            shootType = SnBullets.overheatBullet;
        }};

        //4x4
        admiral = new MultiBarrelItemTurret("admiral") {{
            requirements(Category.turret, with(Items.copper, 1100, Items.titanium, 800, Items.silicon, 600, Items.surgeAlloy, 300, SnItems.naturite, 190));
            ammo(
                    Items.blastCompound, SnBullets.maxBlastBlast,
                    Items.plastanium, SnBullets.maxBlastPlastanium,
                    Items.surgeAlloy, SnBullets.maxBlastSurge
            );
            shots = 3;
            barrelPoints = Seq.with(
                new Vec2(32f/128f, 10f/128f),
                new Vec2(64f/128f, 3f/128f),
                new Vec2(96f/128f, 10f/128f)
            );
            ejectPoints = Seq.with(
                new Vec2(32f/128f, 47f/128f),
                new Vec2(64f/128f, 40f/128f),
                new Vec2(96f/128f, 47f/128f)
            );
            health = 3200;
            size = 4;
            reloadTime = 160f;
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

            speedupPerShot = 0.08f;
            slowReloadTime = 210f;
        }};
        scorpio = new ItemTurret("scorpio") {{
            requirements(Category.turret, with(Items.copper, 600, SnItems.fors, 560, Items.plastanium, 480, SnItems.nobium, 450, SnItems.naturite, 400));
            ammo(
                    Items.sporePod, SnBullets.bigSporePodPoison,
                    SnItems.naturite, SnBullets.bigNaturitePoison,
                    SnItems.nobium, SnBullets.bigNobiumPoison,
                    Items.plastanium, SnBullets.bigPlastaniumPoison
            );
            health = 2900;
            size = 4;
            shots = 1;
            reloadTime = 60f;
            range = 210f;
            recoilAmount = 2f;
            cooldown = 0.03f;
            velocityInaccuracy = 0.2f;
            restitution = 0.02f;
            inaccuracy = 3f;
            rotateSpeed = 5f;
            shootCone = 3f;
            shootSound = Sounds.flame;
            ammoUseEffect = Fx.casing1;
            targetAir = true;
            targetGround = true;
        }};
        typhoon = new LiquidTurret("typhoon"){{
            requirements(Category.turret, with(Items.metaglass, 250, Items.lead, 530, Items.titanium, 340, Items.thorium, 170, SnItems.fors, 150));
            ammo(
                Liquids.water, SnBullets.typhoonWaterShot,
                Liquids.slag, SnBullets.typhoonSlagShot,
                Liquids.cryofluid, SnBullets.typhoonCryoShot,
                Liquids.oil, SnBullets.typhoonOilShot
            );
            size = 4;
            reloadTime = 2f;
            shots = 2;
            velocityInaccuracy = 0.15f;
            inaccuracy = 6f;
            recoilAmount = 1f;
            restitution = 0.05f;
            shootCone = 46f;
            liquidCapacity = 90f;
            shootEffect = SnFx.typhoonShootLiquid;
            range = 260f;
            health = 220 * size * size;
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
            ammoPerShot = 4;
        }};
        flood = new LiquidTurretExt("flood"){{
            requirements(Category.turret, with(Items.metaglass, 230, Items.lead, 500, Items.titanium, 330, Items.thorium, 190, SnItems.fors, 180));
            ammo(
                    Liquids.water, SnBullets.floodWaterShot,
                    Liquids.slag, SnBullets.floodSlagShot,
                    Liquids.cryofluid, SnBullets.floodCryoShot,
                    Liquids.oil, SnBullets.floodOilShot
            );
            size = 4;
            reloadTime = 45f;
            liquidCapacity = 60;
            velocityInaccuracy = 0.025f;
            inaccuracy = 3f;
            recoilAmount = 4f;
            restitution = 0.05f;
            shootCone = 8f;
            shootEffect = SnFx.typhoonShootLiquid;
            range = 390f;
            health = 200 * size * size;
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
            ammoPerShot = 15;
        }};
        tentacle = new ItemTurret("tentacle") {{
            requirements(Category.turret, with(Items.copper, 600, Items.graphite, 580, Items.lead, 550, SnItems.planatrium, 430, SnItems.nobium, 300));
            ammo(
                    SnItems.planatrium, SnBullets.heavyplanatriumSap
            );
            range = 290f;
            health = 3400;
            size = 4;
            reloadTime = 15f;
            recoilAmount = 0.4f;
            inaccuracy = 2.5f;
            rotateSpeed = 5f;
            shootSound = Sounds.sap;
            targetAir = true;
        }};
        chain = new ChainLightningTurret("chain"){{
            requirements(Category.turret, with(Items.copper, 600, Items.lead, 500, Items.surgeAlloy, 460, SnItems.nobium, 450, SnItems.naturite, 400, SnItems.planatrium, 350));
            range = 168.0f;
            damage = 7.60f;
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
        requirements(Category.turret, with(Items.copper, 1200, Items.lead, 800, Items.plastanium, 350, Items.thorium, 400, SnItems.fors, 400, SnItems.nobium, 300));
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
            range = 370f;
            minRange = 55f;

        health = 160 * size * size;
       }};
        somesniper = new ItemTurret("somesniper"){{
            requirements(Category.turret, with(Items.copper, 1200, Items.surgeAlloy, 600, SnItems.naturite, 500, Items.silicon, 400));
            ammo(Items.surgeAlloy, SnBullets.somesnipersurge);

            ammoPerShot = 9;
            rotateSpeed = 1.5f;
            reloadTime = 370f;
            recoilAmount = 12f;
            restitution = 0.02f;
            range = 768.0f;
            cooldown = 0.02f;
            shootShake = 4f;
            size = 5;
            shootCone = 2f;
            shootSound = Sounds.railgun;
            unitSort = (u, x, y) -> -u.maxHealth;

            coolantMultiplier = 0.4f;

            health = 4200;
            coolantUsage = 2f;

            consumes.powerCond(21f, TurretBuild::isActive);
        }};

        //6x6
        trident = new PowerTurret("trident"){{
            requirements(Category.turret, with(Items.copper, 1000, Items.lead, 810, Items.silicon, 790, Items.plastanium, 780, Items.surgeAlloy, 670, SnItems.enojie, 600, SnItems.nobium, 400, SnItems.planatrium, 370));
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
        radius = new ItemTurret("radius"){{
            requirements(Category.turret, with(Items.copper, 1400, Items.graphite, 800, Items.surgeAlloy, 650, Items.plastanium, 555, SnItems.fors, 520, Items.thorium, 480, SnItems.enojie, 420));
            ammo(
                Items.graphite, SnBullets.heavystandardDense,
                Items.pyratite, SnBullets.heavystandardIncendiary,
                Items.thorium, SnBullets.heavystandardThorium,
                Items.blastCompound, SnBullets.standartReneubiteBig,
                SnItems.fors, SnBullets.standardForsBig
            );
            reloadTime = 4f;
            coolantMultiplier = 0.5f;
            restitution = 0.15f;
            ammoUseEffect = Fx.casing3;
            range = 400f;
            inaccuracy = 3f;
            recoilAmount = 2.7f;
            spread = 16f;
            alternate = true;
            shootShake = 4f;
            shots = 2;
            size = 6;
            shootCone = 24f;
            shootSound = Sounds.shootBig;

            health = 160 * size * size;
            coolantUsage = 0.9f;
        }};

        //7x7
        halberd = new PowerTurret("halberd"){{
            requirements(Category.turret, with(Items.copper, 2400,  Items.metaglass, 1200, Items.lead, 1120, Items.silicon, 1200, Items.plastanium, 980, SnItems.nobium, 750, SnItems.fors, 710, SnItems.enojie, 690));
            range = 350f;
            shots = 1;
            chargeTime = 145f;
            rotateSpeed = 1.6F;
            chargeMaxDelay = 140f;
            chargeEffects = 15;
            recoilAmount = 8f;
            reloadTime = 345f;
            cooldown = 10f;
            powerUse = 24f;
            shootShake = 8f;
            shootEffect = SnFx.galebardShoot;
            smokeEffect = Fx.none;
            chargeEffect = SnFx.galebardLaserCharge;
            chargeBeginEffect = SnFx.galebardLaserChargeBegin;
            heatColor = Color.red;
            size = 7;
            health = 170 * size * size;
            targetGround = true;
            targetAir = false;
            shootSound = Sounds.laser;

            shootType = new LaserBulletType(2500){{
                colors = new Color[]{Pal.meltdownHit.cpy().a(0.4f), Pal.meltdownHit, Color.white};
                despawnEffect = Fx.none;
                lifetime = 60f;
                drawSize = 440f;
                collidesAir = false;
                length = 370f;
                width = 50.0F;
            }};
        }};

        //missile
        sunrise = new MissileSiloTurret("sunrise"){{
            requirements(Category.turret, with(Items.copper, 180, Items.lead, 175, Items.graphite, 165, Items.silicon, 150));
            launchEffect = SnFx.missileLaunchSmall;
            size = 2;
            itemCapacity = 240;
            missile = new MissileType(this){{
                damage = 0f;
                splashDamage = 590f;
                splashDamageRadius = 98f;
                speed = 1.75f;
                explodeEffect = SnFx.sunriseMissileExplosion;
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
            requirements(Category.turret, with(Items.copper, 290, Items.lead, 280, Items.graphite, 275, Items.silicon, 260, Items.plastanium, 255, SnItems.fors, 230));
            launchEffect = SnFx.missileLaunchMedium;
            size = 3;
            itemCapacity = 900;
            missile = new MissileType(this){{
                damage = 0f;
                splashDamage = 4400f;
                splashDamageRadius = 129f;
                speed = 1.5f;
                explodeEffect = SnFx.sparkMissileExplosion;
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
            requirements(Category.turret, with(Items.copper, 500, Items.lead, 510, Items.plastanium, 490, Items.silicon, 480, Items.graphite, 475, SnItems.fors, 430, SnItems.nobium, 400));
            launchEffect = SnFx.missileLaunchLarge;
            size = 4;
            itemCapacity = 2400;
            missile = new MissileType(this){{
                damage = 0f;
                splashDamage = 18600f;
                splashDamageRadius = 154f;
                speed = 1.25f;
                explodeEffect = SnFx.dissectorMissileExplosion;
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
            requirements(Category.turret, with(Items.copper, 1400, Items.lead, 1300, Items.plastanium, 1010, Items.silicon, 980, Items.graphite, 975, Items.surgeAlloy, 800, SnItems.enojie, 750));
            launchEffect = SnFx.missileLaunchMedium;
            size = 5;
            itemCapacity = 2400;
            float p1 = 53f / 162f, p2 = 109f / 162f;
            rockets(new Vec2(p1, p1),
                    new Vec2(p1, p2),
                    new Vec2(p2, p1),
                    new Vec2(p2, p2));

            missile = new MissileType(this){{
                damage = 0f;
                splashDamage = 7200f;
                splashDamageRadius = 137f;
                speed = 1f;
                explodeEffect = SnFx.artMissileExplosion;
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

        //EMP
        discharger = new EMPFacility("discharger"){{
            requirements(Category.turret, with(Items.copper, 1600, Items.lead, 1500, Items.metaglass, 1000, Items.plastanium, 850, Items.silicon, 1300, Items.surgeAlloy, 910, Items.phaseFabric, 780, SnItems.flameid, 1000, SnItems.enojie, 1100, SnItems.coldent, 670));
            size = 3;
            health = 980;
            powerUse = 9.3f;
            reloadTime = 660;
            heatColor = Color.valueOf("7FFFD4");
            chargeTime = 180;
            shootType = SnBullets.empBullet;
            range = 180;
            shots = 10;
            zaps = 10;
            zapAngleRand = 10f;
            parts.add(new EMPPart(1.2f));
        }};
        dischargerEvo = new EMPFacility("discharger-evo"){{
            requirements(Category.turret, ItemStack.mult(discharger.requirements, 2));
            size = 4;
            health = 1200;
            powerUse = 17f;
            reloadTime = 780;
            heatColor = Color.valueOf("7FFFD4");
            chargeTime = 300;
            shootType = SnBullets.empBulletEvo;
            range = 240;
            shots = 20;
            zaps = 20;
            zapAngleRand = 15f;
            parts.add(new EMPPart(5));
        }};
        
        //synthesis
        synthesisT1 = new SynthesisTurret("synthesis-t1", 50){{
            requirements(Category.turret, with(Items.copper, 120, Items.lead, 120, Items.titanium, 100));
            ammo(
                    SnItems.naturite, SnBullets.synthesisBullet1
            );
            size = 1;
            health = 350;
            reloadTime = 80;
            range = 88;
            shots = 1;
            shootCone = 3;
            shootSound = Sounds.railgun;
            maxAmmo = 20;
            spread = 3;
            recoilAmount = 5;
            restitution = 0.1f;
            cooldown = 0.2f;
            coolantUsage = 1.1f;
        }};

        synthesisT2 = new SynthesisTurret("synthesis-t2", 120){{
            requirements(Category.turret, with(Items.copper, 240, Items.lead, 240, Items.titanium, 200));
            ammo(
                    SnItems.naturite, SnBullets.synthesisBullet2
            );
            size = 2;
            health = synthesisT1.health * size;
            reloadTime = 75;
            range = 112;
            shots = 1;
            shootCone = 4;
            shootSound = Sounds.railgun;
            maxAmmo = 20;
            spread = 5.1f;
            recoilAmount = 3;
            restitution = 1f;
            cooldown = 0.01f;
            coolantUsage = 1.7f;
        }};

        synthesisT3 = new SynthesisTurret("synthesis-t3", 260){{
            requirements(Category.turret, with(Items.copper, 360, Items.lead, 360, Items.titanium, 300));
            ammo(
                    SnItems.naturite, SnBullets.synthesisBullet3
            );
            size = 3;
            health = synthesisT1.health * size;
            reloadTime = 60 / addSpeed;
            range = 160;
            shots = 3;
            shootCone = 3;
            shootSound = Sounds.railgun;
            speed = 2;
            shootShake = 5;
            maxAmmo = 40;
            spread = 0.1f;
            recoilAmount = 2.1f;
            restitution = 0.55f;
            cooldown = 0.3f;
            coolantUsage = 1.55f;
        }};

        synthesisT4 = new SynthesisTurret("synthesis-t4", 450){{
            requirements(Category.turret, with(Items.copper, 480, Items.lead, 480, Items.titanium, 400));
            ammo(
                    SnItems.naturite, SnBullets.synthesisBullet4
            );
            size = 4;
            health = synthesisT1.health * size;
            reloadTime = 100 / addSpeed;
            range = 240;
            shots = 3;
            shootCone = 1;
            shootSound = Sounds.railgun;
            speed = 5;
            shootShake = 10;
            minRange = 48;
            maxAmmo = 50;
            spread = 4;
            recoilAmount = 5;
            restitution = 0.9f;
            cooldown = 0.7f;
            coolantUsage = 0.9f;
        }};

        //testing
        testturret = new ItemTurret("test-turret") {{
            requirements(Category.turret, with(Items.copper, 2));
            ammo(
                Items.copper, SnBullets.testbullet
            );
            range = 130f;
            health = 780;
            size = 2;
            reloadTime = 24f;
            range = 370f;
            recoilAmount = 0.3f;
            inaccuracy = 1.1f;
            rotateSpeed = 7f;
            shootSound = Sounds.pew;
            targetAir = true;
        }};
    }
}
