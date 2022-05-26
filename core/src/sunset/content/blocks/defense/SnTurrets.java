package sunset.content.blocks.defense;

import arc.graphics.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;
import sunset.content.*;
import sunset.content.affilitiation.*;
import sunset.entities.bullet.*;
import sunset.entities.pattern.*;
import sunset.graphics.*;
import sunset.type.*;
import sunset.world.blocks.defense.turrets.*;
import sunset.world.consumers.*;

import static mindustry.type.ItemStack.with;
import static mma.ModVars.fullName;

public class SnTurrets{
    public static Block
    //1x1
    sting, spine, eagle,

    //2x2
    excellence, carbine, pulsation, reflection, discharge,

    //3x3
    major, burner, payloadTurret, eternity, hemrus, artLightTurret, trigger,

    //4x4
    shotgunTurret, admiral, scorpio, ammirus, flood, chain, rockfall,
    drr,

    //5x5
    pressure, field, sniper, fanatic, defibrillator,

    //6x6
    trident, disappearance, radius,

    //7x7
    halberd, pinwheel,

    //missile
    sunrise,//2x2
    spark,//3x3
    dissector,//4x4
    art,//5x5

    //EMP and synthesis
    discharger, dischargerEvo,
    synthesis1, synthesis2, synthesis3, synthesis4, synthesis5;

    public static void load(){
        //region 1x1
        sting = new ModPowerTurret("sting"){{
            requirements(Category.turret, with(SnItems.fors, 50, Items.lead, 30));
            health = 400;
            reload = 40f;
            range = 95f;
            recoil = 1f;
            inaccuracy = 2f;
            rotateSpeed = 7.5f;
            shootCone = 2f;
            shootSound = Sounds.laser;
            ammoUseEffect = Fx.casing1;
            targetAir = true;
            shootType = new BasicBulletType(3f, 20){{
                sprite = fullName("circle-bullet");
                shrinkX = 0f;
                shrinkY = 0f;
                lifetime = 30f;
                height = 4f;
                width = 4f;
                hitEffect = Fx.flakExplosion;
                frontColor = SnPal.redBomb;
                backColor = SnPal.redBombBack;
                chargeEffect = SnFx.smallEnergySphereCharge;
            }};// SnBullets.smallEnergySphere;
            shoot.firstShotDelay = 30f;//chargeTime = 30f;
            //chargeMaxDelay = 20f;
            consumePower(1f);
            guild = SnGuilds.aymirus;
        }};
        spine = new ModItemTurret("spine"){{
            requirements(Category.turret, with(Items.copper, 55, Items.graphite, 25));
            ammo(
            Items.graphite, SnBullets.lightBlastGraphite,
            Items.silicon, SnBullets.lightBlastSilicon
            );
            health = 360;
            reload = 78f;
            range = 154f;
            recoil = 2.5f;
            inaccuracy = 2f;
            rotateSpeed = 7.5f;
            shootCone = 2f;
            shootSound = Sounds.bang;
            ammoUseEffect = Fx.casing1;
            targetAir = true;
        }};
        eagle = new ModItemTurret("eagle"){{
            requirements(Category.turret, with(Items.titanium, 40, SnItems.naturite, 25));
            ammo(
            Items.sporePod, SnBullets.sporePodPoisonBullet,
            SnItems.naturite, SnBullets.naturitePoisonBullet
            );
            health = 350;
            size = 1;
            shoot.shots = 3;
            reload = 30f;
            range = 115f;
            recoil = 1f;
            cooldownTime = 0.02f;
            velocityRnd = 0.2f;
            recoilTime = reload / 0.01f;//restitution = 0.01f;
            inaccuracy = 14f;
            rotateSpeed = 8f;
            shootCone = 2f;
            shootSound = Sounds.flame;
            ammoUseEffect = Fx.casing1;
            targetAir = true;
            targetGround = true;
            reloadBar = false;
        }};
        //endregion 1x1
        //region 2x2
        excellence = new ModPowerTurret("excellence"){{
            requirements(Category.turret, with(SnItems.fors, 70, Items.lead, 60, Items.silicon, 40));
            size = 2;
            reload = 50f;
            range = 175f;
            recoil = 2f;
            inaccuracy = 3f;
            rotateSpeed = 6f;
            shootCone = 3f;
            shootSound = Sounds.laser;
            ammoUseEffect = Fx.casing1;
            targetAir = true;
            shootType = new BasicBulletType(3f, 60){{
                sprite = fullName("circle-bullet");
                shrinkX = 0f;
                shrinkY = 0f;
                lifetime = 50f;
                height = 9f;
                width = 9f;
                despawnEffect = SnFx.mediumEnergySphereHit;
                frontColor = SnPal.redBomb;
                backColor = SnPal.redBombBack;

                trailColor = SnPal.redBombBack;
                trailWidth = 2f;
                trailLength = 6;
                trailEffect = Fx.artilleryTrail;

                fragBullet = new LaserBulletType(40){{
                    colors = new Color[]{SnPal.redBomb.cpy().a(0.4f), SnPal.redBomb, Color.white};
                    width = 20f;
                    lifetime = 15f;
                    length = 60f;
                    laserEffect = Fx.lancerLaserShootSmoke;
                    collidesAir = true;
                    collidesGround = true;
                }};
                fragBullets = 1;
                fragRandomSpread = 0.0001f;

                chargeEffect = SnFx.mediumEnergySphereCharge;
            }};//SnBullets.mediumEnergySphere;
            shoot.firstShotDelay = 30f;//chargeTime = 30f;
            //chargeMaxDelay = 30f;
            consumePower(3f);
        }};
        carbine = new ChainLightningTurret("carbine"){{
            requirements(Category.turret, with(Items.copper, 150, Items.lead, 140, Items.surgeAlloy, 60, SnItems.nobium, 90, SnItems.naturite, 120));
            range = 132.2f;
            damage = 1.4f;
            health = 900;
            size = 2;
            shootCone = 6f;
            damageMultiplier = 0.30f;
            coolantMultiplier = 1.0f;
            rotateSpeed = 8.75f;
            powerUse = 5f;
            coolant = (ConsumeLiquidBase)consumeCoolant(0.3f).update(false).boost();
            laserColor = SnPal.chainLaser;
            consume(new TimeScaleConsume());
        }};
        pulsation = new ModItemTurret("pulsation"){{
            requirements(Category.turret, with(SnItems.fors, 95, Items.graphite, 85, Items.lead, 70, SnItems.planatrium, 35));
            ammo(
            Items.lead, SnBullets.leadSap,
            Items.sporePod, SnBullets.sporeSap,
            SnItems.planatrium, SnBullets.planatriumSap
            );
            health = 780;
            size = 2;
            reload = 7f;
            range = 140f;
            recoil = 0.3f;
            inaccuracy = 1.1f;
            rotateSpeed = 7f;
            shootSound = Sounds.sap;
            targetAir = true;
            targetGround = false;
            reloadBar = false;
        }};
        reflection = new ModItemTurret("reflection"){{
            requirements(Category.turret, with(Items.copper, 95, SnItems.fors, 55, SnItems.naturite, 50, SnItems.nobium, 25));
            ammo(
            Items.sporePod, SnBullets.heavyPlastaniumPoison,
            SnItems.naturite, SnBullets.heavyNaturitePoison,
            SnItems.nobium, SnBullets.heavyNobiumPoison
            );
            health = 780;
            size = 2;
            shoot.shots = 4;
            reload = 55f;
            range = 160f;
            recoil = 1.1f;
            cooldownTime = 0.02f;
            velocityRnd = 0.2f;
            recoilTime = ((recoilTime == -1) ? reload : recoilTime) / 0.01f;//restitution = 0.01f;
            inaccuracy = 20f;
            rotateSpeed = 7f;
            shootCone = 2f;
            shootSound = Sounds.flame;
            ammoUseEffect = Fx.casing1;
            targetAir = true;
            targetGround = true;
            guild = SnGuilds.aymirus;
        }};
        discharge = new ModPowerTurret("discharge"){{
            requirements(Category.turret, with(Items.silicon, 150, Items.graphite, 75));
            shootType = new LightningBulletType(){{
                damage = 14;
                lightningLength = 39;
                collidesAir = false;
                ammoMultiplier = 1f;
            }};
            reload = 7.1f;
            shootCone = 10f;
            rotateSpeed = 8f;
            consumePower(5.2f);
            targetAir = false;
            range = 144f;
            shootEffect = Fx.lightningShoot;
            heatColor = Color.red;
            recoil = 1f;
            size = 2;
            health = 460;
            shootSound = Sounds.spark;
            reloadBar = false;
        }};
        //endregion 2x2
        //region 3x3
       /* major = new MultiBarrelItemTurret("major"){{
            requirements(Category.turret, with(Items.plastanium, 175, Items.titanium, 75, Items.metaglass, 50, SnItems.naturite, 30));
            ammo(
            Items.blastCompound, SnBullets.bigBlastBlast,
            Items.pyratite, SnBullets.bigBlastPyratite,
            Items.plastanium, SnBullets.bigBlastPlastanium
            );
            shoot.shots = 2;
            shoot=new ShootMultiBarrel(){{
                shots=2;
                shotDelay = 18f;//burstSpacing = 18f;
                barrelPoints(
                new Vec2(30f / 96f, 4f / 96f),
                new Vec2(66f / 96f, 4f / 96f)
                );
                ejectPoints(
                new Vec2(33f / 96f, 40f / 96f),
                new Vec2(63f / 96f, 40f / 96f)
                );
            }};
            health = 1960;
            size = 3;
            reload = 80f;
            range = 237f;
            recoil = 3f;
            inaccuracy = 4f;
            rotateSpeed = 4f;
            shootCone = 2f;
            ammoPerShot = 2;
            shootSound = Sounds.shootBig;
            ammoUseEffect = Fx.casing2;
            targetAir = false;

            speedupPerShot = 0.15f;
            maxReloadMultiplier = 2f;
        }};*/
        burner = new ModItemTurret("burner"){{
            requirements(Category.turret, with(Items.metaglass, 75, Items.lead, 200, Items.graphite, 100));
            ammo(
            Items.coal, SnBullets.heavyCoalFlame,
            Items.pyratite, SnBullets.heavyPyraFlame,
            SnItems.flameid, SnBullets.flameidFlame
            );
            health = 140 * size * size;
            size = 3;
            range = 100f;
            reload = 5f;
            recoil = 2f;
            inaccuracy = 3f;
            rotateSpeed = 7.5f;
            shootCone = 25f;
            targetAir = true;
            shootSound = Sounds.flame;
            reloadBar = false;
        }};
        eternity = new ModItemTurret("eternity"){{
            requirements(Category.turret, with(SnItems.fors, 230, Items.plastanium, 100, Items.graphite, 60, Items.surgeAlloy, 30));
            ammo(
            Items.plastanium, SnBullets.smallPlastaniumBullet,
            Items.pyratite, SnBullets.smallPyratiteBullet,
            Items.surgeAlloy, SnBullets.smallSurgeAlloyBullet,
            SnItems.fors, SnBullets.smallForsSpine,
            SnItems.reneubite, SnBullets.smallBlueMissile,
            SnItems.enojie, SnBullets.smallEnojieMissile
            );
            health = 1780;
            size = 3;
            range = 225f;
            reload = 39f;
            recoil = 3f;
            inaccuracy = 3f;
            rotateSpeed = 5f;
            shootCone = 25f;
            shoot.shots = 4;
            shake = 1.5f;
            shoot.shotDelay = 3f;//burstSpacing = 3f;
            targetAir = true;
            shootSound = Sounds.flame;
            guild = SnGuilds.aymirus;
        }};
        hemrus = new ModItemTurret("hemrus"){{
            requirements(Category.turret, with(SnItems.fors, 250, Items.plastanium, 90, Items.graphite, 60));
            ammo(
            SnItems.naturite, SnBullets.naturiteReversBullet,
            SnItems.fors, SnBullets.forsReversBullet,
            SnItems.nobium, SnBullets.nobiumReversBullet
            );
            health = 1780;
            size = 3;
            range = 225f;
            reload = 4 * Time.toSeconds;
            recoil = 4f;
            inaccuracy = 2f;
            rotateSpeed = 4.6f;
            shootCone = 25f;
            shoot.shots = 1;
            shake = 2f;
            shootSound = Sounds.shootBig;
            reloadBar = false;
            guild = SnGuilds.aymirus;
        }};
        /*
        artLightTurret = new ModPowerTurret("art-light-turret"){{
            requirements(Category.turret, with(Items.silicon, 150, Items.graphite, 75));
            shootType = SnBullets.shotArtLight;
            shoot.shots = 1;
            inaccuracy = 6f;
            reload = 40f;
            shootCone = 10f;
            rotateSpeed = 6f;
            consumePower(4f);
            targetAir = true;
            range = 170f;
            shootEffect = Fx.lightningShoot;
            recoil = 1f;
            size = 2;
            health = 460;
            shootSound = Sounds.spark;
            reloadBar = true;
        }};
         */
        trigger = new ModPowerTurret("trigger"){{
            requirements(Category.turret, with(Items.silicon, 275, Items.titanium, 125, SnItems.enojie, 45));
            size = 3;
            consumePower(9f);
            range = 340f;
            reload = 18f;
            recoil = 0f;
            targetAir = true;
            shootType = SnBullets.overheatBullet;
            reloadBar = false;
        }};
        //endregion 3x3
        //region 4x4
        /*
        shotgunTurret = new ModItemTurret("shotgun-turret"){{
            requirements(Category.turret, with(Items.copper, 660, SnItems.fors, 400, Items.silicon, 300));
            ammo(
                    Items.copper, SnBullets.shotgunArt
            );
            shoot.shots = 5;
            inaccuracy = 13f;
            velocityRnd = 0.2f;
            reload = 60f;
            ammoEjectBack = 5f;
            ammoUseEffect = Fx.casing3Double;
            ammoPerShot = 2;
            cooldownTime = 0.03f;
            recoilTime=((recoilTime==-1)?reload:recoilTime) / 0.02f;//restitution = 0.02f;
            recoil = 6f;
            shake = 2f;
            range = 170f;
            minRange = 25f;
        }};
         */
        /*admiral = new MultiBarrelItemTurret("admiral"){{
            requirements(Category.turret, with(Items.copper, 1100, Items.titanium, 800, Items.silicon, 600, Items.surgeAlloy, 300, SnItems.naturite, 190));
            ammo(
            Items.blastCompound, SnBullets.maxBlastBlast,
            Items.plastanium, SnBullets.maxBlastPlastanium,
            Items.surgeAlloy, SnBullets.maxBlastSurge
            );
            shoot.shots = 3;
            barrelPoints = Seq.with(
            new Vec2(32f / 128f, 10f / 128f),
            new Vec2(64f / 128f, 3f / 128f),
            new Vec2(96f / 128f, 10f / 128f)
            );
            ejectPoints = Seq.with(
            new Vec2(32f / 128f, 47f / 128f),
            new Vec2(64f / 128f, 40f / 128f),
            new Vec2(96f / 128f, 47f / 128f)
            );
            health = 3200;
            size = 4;
            reload = 120f;
            range = 300f;
            recoil = 6f;
            inaccuracy = 3f;
            shoot.shotDelay = 12f;//burstSpacing = 12f;
            rotateSpeed = 3f;
            shootCone = 4f;
            ammoPerShot = 3;
            shootSound = Sounds.artillery;
            ammoUseEffect = Fx.casing2;
            targetAir = false;

            speedupPerShot = 0.16f;
            maxReloadMultiplier = 1.7f;
        }};*/
        scorpio = new ModItemTurret("scorpio"){{
            requirements(Category.turret, with(Items.copper, 600, SnItems.fors, 560, Items.plastanium, 480, SnItems.nobium, 450, SnItems.naturite, 400));
            ammo(
            Items.sporePod, SnBullets.bigSporePodPoison,
            SnItems.naturite, SnBullets.bigNaturitePoison,
            SnItems.nobium, SnBullets.bigNobiumPoison,
            Items.plastanium, SnBullets.bigPlastaniumPoison
            );
            health = 2900;
            size = 4;
            shoot.shots = 1;
            reload = 60f;
            range = 210f;
            recoil = 2f;
            cooldownTime = 0.03f;
            velocityRnd = 0.2f;
            recoilTime = ((recoilTime == -1) ? reload : recoilTime) / 0.02f;//restitution = 0.02f;
            inaccuracy = 3f;
            rotateSpeed = 5f;
            shootCone = 3f;
            shootSound = Sounds.flame;
            ammoUseEffect = Fx.casing1;
            targetAir = true;
            targetGround = true;
        }};
        ammirus = new ModItemTurret("ammirus"){{
            requirements(Category.turret, with(Items.copper, 700, SnItems.fors, 570, Items.plastanium, 475, SnItems.nobium, 470));
            ammo(
            SnItems.nobium, SnBullets.nobiumAimMissile,
            SnItems.naturite, SnBullets.naturiteAimMissile,
            SnItems.reneubite, SnBullets.renubiteAimMissile
            );
            health = 3000;
            size = 4;
            shoot.shots = 5;
            reload = 5f * Time.toSeconds;
            range = 37 * Vars.tilesize;
            recoil = 4f;
            cooldownTime = 0.03f;
            inaccuracy = 3f;
            rotateSpeed = 4f;
            shake = 2f;
            shoot.shotDelay = 4f;//burstSpacing = 4f;
            shootSound = Sounds.missile;
            ammoUseEffect = Fx.casing1;
            targetAir = true;
            targetGround = true;
            guild = SnGuilds.aymirus;
        }};
        flood = new LiquidTurretExt("flood"){{
            shoot = new ShootAlternate();

            requirements(Category.turret, with(Items.metaglass, 230, Items.lead, 370f, Items.titanium, 330, Items.thorium, 190, SnItems.fors, 180));
            ammo(
            Liquids.water, SnBullets.floodWaterShot,
            Liquids.slag, SnBullets.floodSlagShot,
            Liquids.cryofluid, SnBullets.floodCryoShot,
            Liquids.oil, SnBullets.floodOilShot,
            SnLiquids.burheyna, SnBullets.floodBurheynaShot
            );
            size = 4;
            reload = 45f;
            liquidCapacity = 60;
            velocityRnd = 0.025f;
            inaccuracy = 3f;
            recoil = 4f;
            recoilTime = ((recoilTime == -1) ? reload : recoilTime) / 0.05f;//restitution = 0.05f;
            shootCone = 8f;
            shootEffect = SnFx.typhoonShootLiquid;
            range = 390f;
            health = 200 * size * size;
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
            ammoPerShot = 15;
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
            consumePower(16f);
            coolant = (ConsumeLiquidBase)consumeCoolant(0.5f).update(false).boost();
            laserColor = SnPal.chainLaser;
            consume(new TimeScaleConsume());
        }};
        /*rockfall = new ModItemTurret("rockfall") {{
            requirements(Category.turret, with(Items.copper, 700, Items.lead, 700, Items.graphite, 500, Items.titanium, 880, Items.silicon, 600));
            ammo(
                Items.graphite, SnBullets.graphiteShell,
                Items.titanium, SnBullets.titaniumShell,
                Items.thorium, SnBullets.thoriumShell,
                SnItems.lightBurstMissile, SnBullets.lightBurstMissile
            );
            health = 3000;
            size = 4;
            itemCapacity = 20;
            range = 45f * Vars.tilesize;
            shoot.shots = 6;
            inaccuracy = 12;
            velocityRnd = 0.3f;
            ammoEjectBack = 6f;
            cooldownTime = 0.9f;
            recoilTime=((recoilTime==-1)?reload:recoilTime) / 0.01f;//restitution = 0.01f;
            recoil = 5.1f;
            shake = 3;
            targetAir = false;
            targetGround = true;
            reload = 2f * Time.toSeconds;
            guild = SnGuilds.kryonix;
            branch = SnBranches.heavyArt;
        }};*/

        drr = new ModItemTurret("drr"){{
            shoot = new ShootSpread(5, 0);
            hideDetails = false;
            size = 4;
            health = 14000;
            buildVisibility = BuildVisibility.debugOnly;
            ammo(SnItems.flameid, SnBullets.drrRocket);
            range = 50 * Vars.tilesize;
            reload = 0.5f * Time.toSeconds;
            shoot.shotDelay = 0.1f * Time.toSeconds;//burstSpacing = 0.1f * Time.toSeconds;
            category = Category.turret;
            inaccuracy = 0;

            shake = 2f;
            shootSound = Sounds.missile;
            shootEffect = Fx.fireSmoke;
        }};
        //endregion 4x4
        //region 5x5
        pressure = new ModItemTurret("pressure"){{
            requirements(Category.turret, with(SnItems.fors, 600, Items.silicon, 500, Items.plastanium, 450, Items.graphite, 400, Items.surgeAlloy, 300, SnItems.naturite, 100));
            ammo(
            Items.plastanium, SnBullets.mediumPlastaniumBullet,
            Items.surgeAlloy, SnBullets.bigEnergySphere,
            SnItems.nobium, SnBullets.smallNobiumSpine,
            SnItems.reneubite, SnBullets.reneubiteBullet,
            SnItems.enojie, SnBullets.mediumEnojieMissile
            );
            health = 4180;
            size = 5;
            range = 40f * Vars.tilesize;
            reload = 3f * Time.toSeconds;
            recoil = 4f;
            inaccuracy = 5f;
            rotateSpeed = 3f;
            shootCone = 25f;
            shoot.shots = 4;
            shake = 2f;
            shoot.shotDelay = 4f;//burstSpacing = 4f;
            recoilTime = ((recoilTime == -1) ? reload : recoilTime) / 0.05f;//restitution = 0.05f;
            targetAir = true;
            hasPower = true;
            shootSound = Sounds.flame;
            consumePower(7f);
            reloadBar = false;
            guild = SnGuilds.aymirus;
        }};
        /*field = new ModItemTurret("field") {{
            requirements(Category.turret, with(Items.copper, 1200, Items.lead, 800, Items.plastanium, 350, Items.thorium, 400, SnItems.fors, 400, SnItems.nobium, 300));
            ammo(
                SnItems.raMissile, SnBullets.raMissile,
                SnItems.empMissile, SnBullets.empMissile//,
                //SnItems.lightningMissile, SnBullets.lightningMissile,
                //SnItems.lightMissile, SnBullets.lightMissile
            );
            health = 4000;
            size = 5;
            itemCapacity = 40;
            range = 46.25f * Vars.tilesize;
            minRange = 6.875f * Vars.tilesize;
            shoot.shots = 1;
            inaccuracy = 4f;
            velocityRnd = 0.2f;
            ammoEjectBack = 4f;
            cooldownTime = 0.06f;
            reload = 0.84f * Time.toSeconds;
            recoilTime=((recoilTime==-1)?reload:recoilTime) / 0.02f;//restitution = 0.02f;
            recoil = 4f;
            shake = 2f;
            targetAir = false;
            targetGround = true;
            guild = SnGuilds.kryonix;
        }};*/
        sniper = new ModItemTurret("sniper"){{
            requirements(Category.turret, with(Items.copper, 1200, SnItems.fors, 700, Items.surgeAlloy, 600, SnItems.naturite, 500, Items.silicon, 400, SnItems.nobium, 250));
            ammo(
            Items.surgeAlloy, SnBullets.sniperSurge
            );

            ammoPerShot = 9;
            rotateSpeed = 1.5f;
            reload = 370f;
            recoil = 12f;
            recoilTime = ((recoilTime == -1) ? reload : recoilTime) / 0.02f;//restitution = 0.02f;
            range = 768.0f;
            cooldownTime = 0.02f;
            shake = 4f;
            size = 5;
            shootCone = 2f;
            shootSound = Sounds.railgun;
            unitSort = (u, x, y) -> -u.maxHealth;

            coolantMultiplier = 0.4f;

            health = 4200;
            coolant = consumeCoolant(2f);

            consumePowerCond(21f, TurretBuild::isActive);
        }};
        /*fanatic = new ModItemTurret("fanatic") {{
            requirements(Category.turret, with(
            Items.copper, 700, Items.graphite, 580, Items.titanium, 900, Items.thorium, 890, Items.silicon, 570, Items.surgeAlloy, 430,
            SnItems.fors, 570, SnItems.naturite, 410, SnItems.nobium, 310));
            ammo(
                //SnItems.burstMissile, SnBullets.burstMissile,
                //SnItems.detonatorMissile, SnBullets.detonatorMissile,
                //SnItems.pointMissile, SnBullets.pointMissile,
                //SnItems.spotMissile, SnBullets.spotMissile
            );
            health = 4200;
            size = 5;
            itemCapacity = 40;
            range = 47.5f * Vars.tilesize;
            minRange = 10f * Vars.tilesize;
            rotateSpeed = 2;
            shoot.shots = 1;
            inaccuracy = 7;
            cooldownTime = 0.2f;
            reload = 2.5f * Time.toSeconds;
            recoilTime=((recoilTime==-1)?reload:recoilTime) / 0.07f;//restitution = 0.07f;
            recoil = 1;
            targetAir = false;
            targetGround = true;
            shoot.firstShotDelay = 20;//chargeTime = 20;
            shootCone = 4;
            shootSound = Sounds.shootBig;
            drawLight = true;
            guild = SnGuilds.kryonix;
        }};*/
        /*defibrillator = new Turret360("defibrillator"){{
            shoot = new ShootSpread(20, 18f);
            requirements(Category.turret, with(Items.copper, 650, Items.graphite, 550, Items.titanium, 600, Items.thorium, 600, SnItems.fors, 700));
            //todo /\
            ammo(
            SnItems.nobium, SnBullets.defLight
            );
            health = 2100;
            size = 5;
            reload = 1 * Time.toSeconds;
            range = 20f * Vars.tilesize;
            recoil = 0;
            cooldownTime = 0.3f;
            recoilTime = ((recoilTime == -1) ? reload : recoilTime) / 0.8f;//restitution = 0.8f;
            inaccuracy = 360;
            shootCone = 360;
            shootSound = Sounds.spark;
            targetAir = targetGround = true;
            rotateSpeed = 0;
            powerBullet = SnBullets.powerLight;
            chargeShots = 3;
            debug = true;
        }};*/
        //endregion 5x5
        //region 6x6
        trident = new ModPowerTurret("trident"){{
            requirements(Category.turret, with(Items.copper, 1000, Items.lead, 810, Items.silicon, 790, Items.plastanium, 780, Items.surgeAlloy, 670, SnItems.enojie, 600, SnItems.nobium, 400, SnItems.planatrium, 370));
            health = 5400;
            range = 247f;
            size = 6;
            shoot.firstShotDelay = 210f;//chargeTime = 210f;
            //chargeMaxDelay = 0f;
            reload = 460f;
            recoil = 8f;
            consumePower(38f);
            category = Category.turret;
            buildVisibility = BuildVisibility.shown;
            shootType = new EnergySphereBulletType(2.5f, 290f){
                {
                    hitSize = 10f;
                    splashDamage = 910f;
                    splashDamageRadius = 97f;
                    lightningPeriod = 10f;
                    lightningChance = 0.4f;
                    lightningLength = 111;
                    healPercent = 5f;
                    lightningDamage = 96;
                    lifetime = 480f;
                    frontColor = Pal.sap;
                    hitEffect = SnFx.tridentHit;
                    //draw
                    count = 15;
                    timeSclMin = 0.66f;
                    timeSclMax = 1.66f;
                    lenMin = 3f;
                    lenMax = 5f;
                    radMin = 2.5f;
                    radMax = 4f;
                    chargeEffect = SnFx.energyCharge((EnergySphereBulletType)shootType, 210);
                }

                @Override
                public float continuousDamage(){
                    return 720;
                }

                ;
            };
            reloadBar = false;
        }};

        radius = new MinigunTurret("radius"){{
            requirements(Category.turret, with(Items.copper, 2500, Items.graphite, 1700, Items.surgeAlloy, 1450, Items.plastanium, 955, SnItems.enojie, 620));
            ammo(
            Items.graphite, SnBullets.heavyStandardDense,
            Items.pyratite, SnBullets.heavyStandardIncendiary,
            Items.thorium, SnBullets.heavyStandardThorium,
            Items.blastCompound, SnBullets.standardBlastBig,
            SnItems.fors, SnBullets.standardForsBig,
            SnItems.reneubite, SnBullets.reneubiteBlast
            );
            reload = 4f;
            coolantMultiplier = 0.5f;
            recoilTime = ((recoilTime == -1) ? reload : recoilTime) / 0.15f;//restitution = 0.15f;
            ammoUseEffect = Fx.casing3;
            range = 50.0f * Vars.tilesize;
            inaccuracy = 3f;
            inaccuracyUp = 0.4f;
            recoil = 2.7f;

            shoot = new ShootAlternate(16f){{
                shots = 2;
            }};
            shake = 4f;

            size = 6;
            shootCone = 24f;
            shootSound = Sounds.shootBig;
            maxShootTime = 3f * Time.toSeconds;

            health = 160 * size * size;
            coolant = consumeCoolant(0.9f);

            reloadBar = false;
            debug = true;
        }};
        disappearance = new ModLaserTurret("disappearance"){{
            requirements(Category.turret, with(Items.copper, 2400, Items.lead, 1800, Items.graphite, 1500, Items.surgeAlloy, 1460, Items.silicon, 1200, SnItems.enojie, 700));
            shootEffect = Fx.shootBigSmoke2;
            shootCone = 45f;
            recoil = 5f;
            size = 6;
            shake = 5f;
            range = 50f * Vars.tilesize;
            reload = 5f * Time.toSeconds;
            shootDuration = 11f * Time.toSeconds;
            recoilTime = ((recoilTime == -1) ? reload : recoilTime) / 0.01f;//restitution = 0.01f;
            cooldownTime = 0.05f;
            heatColor = Pal.turretHeat;
            consumePower(26f);
            shootSound = Sounds.laserbig;
            loopSound = Sounds.beam;
            loopSoundVolume = 2.5f;

            shootType = new LightningContinuousLaserBulletType(150){{
                length = 55f * Vars.tilesize;
                shake = 4f;
                width = 15f;
                drawSize = 60f * Vars.tilesize;
                hitEffect = Fx.hitMeltdown;
                hitColor = Pal.meltdownHit;
                status = SnStatusEffects.incineration;
                statusDuration = 9f * Time.toSeconds;

                lightning = 1;
                lightningDamage = 40;
                lightningLength = 50;
                lightningCone = 120;
                lightningColor = Pal.meltdownHit;

                incendChance = 0.7f;
                incendSpread = 6f;
                incendAmount = 3;
            }};
            health = 240 * size * size;
            consumeCoolant(1.1f).update(false);
            reloadBar = false;
            guild = SnGuilds.aymirus;
        }};
        //endregion 6x6
        //region 7x7
        halberd = new ModPowerTurret("halberd"){{
            requirements(Category.turret, with(Items.copper, 3800, Items.metaglass, 2700, Items.lead, 2520, Items.silicon, 2200, Items.plastanium, 1480, SnItems.enojie, 1390));
            range = 350f;
            shoot.shots = 1;
            shoot.firstShotDelay = 145f;//chargeTime = 145f;
            rotateSpeed = 1.6f;
            //chargeMaxDelay = 110f;
            recoil = 8f;
            reload = 8f * Time.toSeconds;
            cooldownTime = 10f;
            consumePower(30f);
            shake = 8f;
            shootEffect = SnFx.galebardShoot;
            smokeEffect = Fx.none;
//            chargeEffect = SnFx.galebardLaserCharge;
//            chargeBeginEffect = SnFx.galebardLaserChargeBegin;
            heatColor = Pal.turretHeat;
            size = 7;
            health = 170 * size * size;
            targetGround = true;
            targetAir = false;
            shootSound = Sounds.laser;
            guild = SnGuilds.aymirus;

            shootType = new LaserBulletType(7500){{
                colors = new Color[]{Pal.meltdownHit.cpy().a(0.4f), Pal.meltdownHit, Color.white};
                chargeEffect = new MultiEffect(SnFx.galebardLaserCharge, SnFx.galebardLaserChargeBegin);
                despawnEffect = Fx.none;
                lifetime = 60f;
                drawSize = 440f;
                collidesAir = false;
                length = 370f;
                width = 60f;
            }};
        }};
        /*pinwheel = new Turret360("pinwheel"){{
            requirements(Category.turret, with(Items.copper, 900, Items.lead, 900, Items.silicon, 710, Items.titanium, 800, Items.thorium, 750, Items.surgeAlloy, 450, SnItems.planatrium, 450, SnItems.fors, 870, SnItems.enojie, 200));
            //todo /\
            ammo(
            Items.thorium, SnBullets.thoriumFlak,
            SnItems.fors, SnBullets.forsFlak
            );
            health = 4000;
            size = 7;
            shoot=new ShootSpread(16,22.5f);
            reload = 0.75f * Time.toSeconds;
            range = 35f * Vars.tilesize;
            recoil = 0;
            cooldownTime = 0.3f;
            recoilTime = ((recoilTime == -1) ? reload : recoilTime) / 1f;//restitution = 1f;
            inaccuracy = 0;
            shootCone = 360;
            shootSound = Sounds.shootBig;
            targetAir = targetGround = true;
            rotateTurret = true;
            shake = 2;
            rotateSpeed = 5;
            powerBullet = SnBullets.powerRocket;
            chargeShots = 3;
            debug = true;
        }};*/
        //endregion 7x7
        //region missile
        sunrise = new MissileSiloTurret("sunrise"){{
            requirements(Category.turret, with(Items.copper, 180, Items.lead, 175, Items.graphite, 165, Items.silicon, 150));
            launchEffect = SnFx.missileLaunchSmall;
            size = 2;
            itemCapacity = 240;
            missile = new MissileType(this){{
                damage = 0f;
                splashDamage = 590f;
                splashDamageRadius = 105f;
                speed = 1.75f;
                explodeEffect = SnFx.sunriseMissileExplosion;
            }};
            craftTime = 1200f;
            consumeItems(with(Items.silicon, 60, Items.graphite, 90, Items.pyratite, 120));
            consumePower(1.75f);
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
                splashDamageRadius = 135f;
                speed = 1.5f;
                explodeEffect = SnFx.sparkMissileExplosion;
            }};
            craftTime = 4200f;
            consumeItems(with(Items.graphite, 270, Items.silicon, 360, Items.plastanium, 450, Items.blastCompound, 540));
            consumePower(4.5f);
            health = 720;
            maxRange = 450f;
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
                splashDamage = 17000f;
                splashDamageRadius = 160f;
                speed = 1.25f;
                explodeEffect = SnFx.dissectorMissileExplosion;
            }};
            craftTime = 16800f;
            consumeItems(with(Items.phaseFabric, 440, Items.silicon, 620, Items.surgeAlloy, 800, Items.thorium, 980, Items.plastanium, 1200));
            consumePower(16f);
            health = 1280;
            maxRange = 550f;
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
                splashDamageRadius = 145f;
                speed = 1f;
                explodeEffect = SnFx.artMissileExplosion;
            }};
            craftTime = 4500f;
            consumeItems(with(Items.plastanium, 180, Items.silicon, 270, Items.surgeAlloy, 90, Items.thorium, 330));
            consumePower(7.5f);
            health = 2000;
            maxRange = 620f;
            minRange = 276.0f;
            category = Category.turret;
            buildVisibility = BuildVisibility.shown;
        }};
        //endregion missile
        //region EMP and synthesis
        discharger = new EMPFacility("discharger"){{
            requirements(
            Category.turret, with(
            Items.copper, 1600, Items.lead, 1500, Items.titanium, 1100, Items.thorium, 900, Items.silicon, 1300, Items.plastanium, 850, Items.phaseFabric, 780, Items.surgeAlloy, 910, Items.metaglass, 1000,
            SnItems.fors, 760, SnItems.enojie, 1100));
            size = 3;
            health = 980;

            consumePowerCond(9f, TurretBuild::isShooting);
            reload = 11 * Time.toSeconds;
            heatColor = Color.valueOf("7FFFD4");
            shoot.firstShotDelay = 180;//chargeTime = 180;
            shootType = SnBullets.empBullet;
            range = 22.5f * Vars.tilesize;
            shoot.shots = 10;
            zaps = 1;
            zapAngleRand = 360;
            cores.add(new Core(1.2f));
            liquidCapacity = 60;
        }};
        dischargerEvo = new EMPFacility("discharger-evo"){{
            requirements(Category.turret, with(
            Items.copper, 2000, Items.lead, 1800, Items.thorium, 1200, Items.silicon, 1600, Items.plastanium, 910, Items.phaseFabric, 900, Items.surgeAlloy, 1100, Items.metaglass, 1100,
            SnItems.fors, 910, SnItems.planatrium, 1000, SnItems.enojie, 1500, SnItems.reneubite, 1000));
            size = 4;
            health = 1200;
            consumePowerCond(13.1f, TurretBuild::isShooting);
            reload = 13 * Time.toSeconds;
            heatColor = Color.valueOf("7FFFD4");
            shoot.firstShotDelay = 300;//chargeTime = 300;
            shootType = SnBullets.empBulletEvo;
            range = 30 * Vars.tilesize;
            shoot.shots = 20;
            zaps = 1;
            zapAngleRand = 360;
            cores.add(new Core(5));
            liquidCapacity = 120;
        }};

        /*synthesis1 = new SynthesisTurret("synthesis-1") {{
            requirements(Category.turret, with(Items.copper, 120, Items.lead, 120, Items.titanium, 100));
            ammo(
            SnItems.naturite, SnBullets.naturiteBolt1
            );
            primaryArmor = 700;
            regenCooldown = Time.toSeconds;
            regenAmount = 70;
            secondaryArmor = 50;
            size = 4;
            health = 350 * size;
            reload = 1.33f * Time.toSeconds;
            range = 25 * Vars.tilesize;
            shoot.shots = 1;
            shootCone = 3;
            shootSound = Sounds.railgun;
            maxAmmo = 30;
            spread = 3;
            recoil = 5;
            recoilTime=((recoilTime==-1)?reload:recoilTime) / 0.1f;//restitution = 0.1f;
            cooldownTime = 0.2f;
            coolantUsage = 1.1f;
            //consumePower(3.8f);
            consumePower(3.8f);
        }};
        synthesis2 = new SynthesisTurret("synthesis-2") {{
            requirements(Category.turret, ItemStack.mult(synthesis1.requirements, 2));
            ammo(
            SnItems.naturite, SnBullets.naturiteBolt2
            );
            primaryArmor = 875;
            regenCooldown = Time.toSeconds;
            regenAmount = 87.5f;
            secondaryArmor = 120;
            size = 5;
            health = synthesis1.health * size;
            reload = 1.25f * Time.toSeconds;
            range = 30 * Vars.tilesize;
            shoot.shots = 1;
            shootCone = 4;
            shootSound = Sounds.railgun;
            maxAmmo = 30;
            spread = 5.1f;
            recoil = 3;
            recoilTime=((recoilTime==-1)?reload:recoilTime) / 1f;//restitution = 1f;
            cooldownTime = 0.01f;
            coolantUsage = 1.7f;
            //consumePower(6.9f);
            consumePower(6.9f);
        }};
        synthesis3 = new SynthesisTurret("synthesis-3") {{
            requirements(Category.turret, ItemStack.mult(synthesis1.requirements, 3));
            ammo(
            SnItems.naturite, SnBullets.naturiteBolt3
            );
            primaryArmor = 1050;
            regenCooldown = Time.toSeconds;
            regenAmount = 105;
            secondaryArmor = 260;
            size = 6;
            health = synthesis1.health * size;
            reload = 1 * Time.toSeconds;
            range = 40 * Vars.tilesize;
            shoot.shots = 3;
            shootCone = 3;
            shootSound = Sounds.railgun;
            shake = 5;
            minRange = 7 * Vars.tilesize;
            maxAmmo = 30;
            spread = 0.1f;
            recoil = 2.1f;
            recoilTime=((recoilTime==-1)?reload:recoilTime) / 0.55f;//restitution = 0.55f;
            cooldownTime = 0.3f;
            coolantUsage = 1.55f;
            //consumePower(9.2f);
            consumePower(9.2f);
            shoot.shotDelay = 5f;//burstSpacing = 5f;
        }};
        synthesis4 = new SynthesisTurret("synthesis-4") {{
            requirements(Category.turret, ItemStack.mult(synthesis1.requirements, 4));
            ammo(
            SnItems.naturite, SnBullets.naturiteBolt4
            );
            primaryArmor = 1225;
            regenCooldown = Time.toSeconds;
            regenAmount = 82.5f;
            secondaryArmor = 450;
            size = 7;
            health = synthesis1.health * size;
            reload = 1.66f * Time.toSeconds;
            range = 48 * Vars.tilesize;
            shoot.shots = 3;
            shootCone = 1;
            shootSound = Sounds.railgun;
            shake = 10;
            minRange = 10 * Vars.tilesize;
            maxAmmo = 30;
            spread = 0;
            recoil = 5;
            recoilTime=((recoilTime==-1)?reload:recoilTime) / 0.9f;//restitution = 0.9f;
            cooldownTime = 0.7f;
            coolantUsage = 0.9f;
            //consumePower(11.3f);
            consumePower(11.3f);
            shoot.shotDelay = 2f;//burstSpacing = 2f;
        }};
        synthesis5 = new SynthesisTurret("synthesis-5") {{
            requirements(Category.turret, ItemStack.mult(synthesis1.requirements, 5));
            ammo(
            SnItems.naturite, SnBullets.naturiteBolt5
            );
            primaryArmor = 1400;
            regenCooldown = Time.toSeconds;
            regenAmount = 100;
            secondaryArmor = 600;
            size = 8;
            health = synthesis1.health * size;
            reload = 3 * Time.toSeconds;
            range = 60 * Vars.tilesize;
            shoot.shots = 5;
            shootCone = 2.1f;
            shootSound = Sounds.release;
            shake = 13;
            minRange = 15 * Vars.tilesize;
            maxAmmo = 30;
            spread = 0.2f;
            recoil = 9;
            recoilTime=((recoilTime==-1)?reload:recoilTime) / 1.3f;//restitution = 1.3f;
            cooldownTime = 0.85f;
            coolantUsage = 3.2f;
            //consumePower(25);
            consumePower(25);
            shoot.shotDelay = 3f;//burstSpacing = 3f;
        }};*/
        //endregion synthesis
    }
}
