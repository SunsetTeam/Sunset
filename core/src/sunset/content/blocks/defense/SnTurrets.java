package sunset.content.blocks.defense;

import arc.graphics.Color;
import arc.math.geom.Vec2;
import arc.struct.EnumSet;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.entities.bullet.LightningBulletType;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.consumers.ConsumeCoolant;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.BuildVisibility;
import sunset.content.*;
import sunset.content.affilitiation.SnGuilds;
import sunset.entities.bullet.EnergySphereBulletType;
import sunset.entities.bullet.LightningContinuousLaserBulletType;
import sunset.graphics.SnPal;
import sunset.type.MissileType;
import sunset.world.blocks.defense.turrets.*;

import static mindustry.type.ItemStack.with;

public class SnTurrets implements ContentList {
    public static Block
    //1x1
    sting, spine, eagle,

    //2x2
    excellence, carbine, pulsation, reflection, discharge,

    //3x3
    major, burner, eternity, hemrus, trigger,

    //4x4
    admiral, scorpio, ammirus, flood, chain, drr,

    //5x5
    pressure, field, sniper, fanatic, defibrillator,

    //6x6
    trident, disappearance, radius,

    //7x7
    halberd, pinwheel, inferno/*reserved*/,

    //missile
    sunrise/*2x2*/, spark/*3x3*/, dissector/*4x4*/, art/*5x5*/,

    //EMP and synthesis
    discharger, dischargerEvo,
    synthesis1, synthesis2, synthesis3, synthesis4, synthesis5;

    @Override
    public void load() {
        //region 1x1
        sting = new ModPowerTurret("sting") {{
            requirements(Category.turret, with(SnItems.fors, 50, Items.lead, 30));
            health = 400;
            reloadTime = 40f;
            range = 95f;
            recoilAmount = 1f;
            inaccuracy = 2f;
            rotateSpeed = 7.5f;
            shootCone = 2f;
            shootSound = Sounds.laser;
            ammoUseEffect = Fx.casing1;
            targetAir = true;
            shootType = SnBullets.smallEnergySphere;
            chargeTime = 30f;
            chargeMaxDelay = 20f;
            powerUse = 1f;
            chargeEffect = SnFx.smallEnergySphereCharge;
            guild = SnGuilds.aymirus;
        }};
        spine = new ModItemTurret("spine") {{
            requirements(Category.turret, with(Items.copper, 55, Items.graphite, 25));
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
        eagle = new ModItemTurret("eagle") {{
            requirements(Category.turret, with(Items.titanium, 40, SnItems.naturite, 25));
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
            reloadBar = false;
        }};
        //endregion 1x1
        //region 2x2
        excellence = new ModPowerTurret("excellence") {{
            requirements(Category.turret, with(SnItems.fors, 70, Items.lead, 60, Items.silicon, 40));
            size = 2;
            reloadTime = 50f;
            range = 175f;
            recoilAmount = 2f;
            inaccuracy = 3f;
            rotateSpeed = 6f;
            shootCone = 3f;
            shootSound = Sounds.laser;
            ammoUseEffect = Fx.casing1;
            targetAir = true;
            shootType = SnBullets.mediumEnergySphere;
            chargeTime = 30f;
            chargeMaxDelay = 30f;
            powerUse = 2f;
            chargeEffect = SnFx.mediumEnergySphereCharge;
        }};
        carbine = new ChainLightningTurret("carbine") {{
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
            liquidUse = 0.3f;
            laserColor = SnPal.chainLaser;
        }};
        pulsation = new ModItemTurret("pulsation") {{
            requirements(Category.turret, with(SnItems.fors, 95, Items.graphite, 85, Items.lead, 70, SnItems.planatrium, 35));
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
            reloadBar = false;
        }};
        reflection = new ModItemTurret("reflection") {{
            requirements(Category.turret, with(Items.copper, 95, SnItems.fors, 55, SnItems.naturite, 50, SnItems.nobium, 25));
            ammo(
            Items.sporePod, SnBullets.heavyPlastaniumPoison,
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
            reloadTime = 7.1f;
            shootCone = 10f;
            rotateSpeed = 8f;
            powerUse = 5.2f;
            targetAir = false;
            range = 144f;
            shootEffect = Fx.lightningShoot;
            heatColor = Color.red;
            recoilAmount = 1f;
            size = 2;
            health = 460;
            shootSound = Sounds.spark;
            reloadBar = false;
        }};
        //endregion 2x2
        //region 3x3
        major = new MultiBarrelItemTurret("major") {{
            requirements(Category.turret, with(Items.plastanium, 175, Items.titanium, 75, Items.metaglass, 50, SnItems.naturite, 30));
            ammo(
            Items.blastCompound, SnBullets.bigBlastBlast,
            Items.pyratite, SnBullets.bigBlastPyratite,
            Items.plastanium, SnBullets.bigBlastPlastanium
            );
            shots = 2;
            barrelPoints = Seq.with(
            new Vec2(30f / 96f, 4f / 96f),
            new Vec2(66f / 96f, 4f / 96f)
            );
            ejectPoints = Seq.with(
            new Vec2(33f / 96f, 40f / 96f),
            new Vec2(63f / 96f, 40f / 96f)
            );
            health = 1960;
            size = 3;
            reloadTime = 80f;
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

            speedupPerShot = 0.15f;
            maxReloadMultiplier = 2f;
        }};
        burner = new ModItemTurret("burner") {{
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
            reloadBar = false;
        }};
        eternity = new ModItemTurret("eternity") {{
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
            reloadTime = 39f;
            recoilAmount = 3f;
            inaccuracy = 3f;
            rotateSpeed = 5f;
            shootCone = 25f;
            shots = 4;
            shootShake = 1.5f;
            burstSpacing = 3f;
            targetAir = true;
            shootSound = Sounds.flame;
            guild = SnGuilds.aymirus;
        }};
        hemrus = new ModItemTurret("hemrus") {{
            requirements(Category.turret, with(SnItems.fors, 250, Items.plastanium, 90, Items.graphite, 60));
            ammo(
                    SnItems.naturite, SnBullets.naturiteReversBullet
            );
            health = 1780;
            size = 3;
            range = 225f;
            reloadTime = 4 * Time.toSeconds;
            recoilAmount = 4f;
            inaccuracy = 2f;
            rotateSpeed = 4.6f;
            shootCone = 25f;
            shots = 1;
            shootShake = 2f;
            shootSound = Sounds.shootBig;
            reloadBar = false;
            guild = SnGuilds.aymirus;
        }};
        trigger = new ModPowerTurret("trigger") {{
            requirements(Category.turret, with(Items.silicon, 275, Items.titanium, 125, SnItems.enojie, 45));
            size = 3;
            powerUse = 9f;
            range = 340f;
            reloadTime = 18f;
            recoilAmount = 0f;
            targetAir = true;
            shootType = SnBullets.overheatBullet;
            reloadBar = false;
        }};
        //endregion 3x3
        //region 4x4
        admiral = new MultiBarrelItemTurret("admiral") {{
            requirements(Category.turret, with(Items.copper, 1100, Items.titanium, 800, Items.silicon, 600, Items.surgeAlloy, 300, SnItems.naturite, 190));
            ammo(
            Items.blastCompound, SnBullets.maxBlastBlast,
            Items.plastanium, SnBullets.maxBlastPlastanium,
            Items.surgeAlloy, SnBullets.maxBlastSurge
            );
            shots = 3;
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
            reloadTime = 120f;
            range = 300f;
            recoilAmount = 6f;
            inaccuracy = 3f;
            burstSpacing = 12f;
            rotateSpeed = 3f;
            shootCone = 4f;
            ammoPerShot = 3;
            shootSound = Sounds.artillery;
            ammoUseEffect = Fx.casing2;
            targetAir = false;

            speedupPerShot = 0.16f;
            maxReloadMultiplier = 1.7f;
        }};
        scorpio = new ModItemTurret("scorpio") {{
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
        ammirus = new ModItemTurret("ammirus") {{
            requirements(Category.turret, with(Items.copper, 700, SnItems.fors, 570, Items.plastanium, 475, SnItems.nobium, 470));
            ammo(
                    SnItems.nobium, SnBullets.nobiumAimMissile
            );
            health = 3000;
            size = 4;
            shots = 5;
            reloadTime = 5f * Time.toSeconds;
            range = 37 * Vars.tilesize;
            recoilAmount = 4f;
            cooldown = 0.03f;
            inaccuracy = 3f;
            rotateSpeed = 4f;
            shootShake = 2f;
            burstSpacing = 4f;
            shootSound = Sounds.missile;
            ammoUseEffect = Fx.casing1;
            targetAir = true;
            targetGround = true;
            guild = SnGuilds.aymirus;
        }};
        flood = new LiquidTurretExt("flood") {{
            requirements(Category.turret, with(Items.metaglass, 230, Items.lead, 370f, Items.titanium, 330, Items.thorium, 190, SnItems.fors, 180));
            ammo(
            Liquids.water, SnBullets.floodWaterShot,
            Liquids.slag, SnBullets.floodSlagShot,
            Liquids.cryofluid, SnBullets.floodCryoShot,
            Liquids.oil, SnBullets.floodOilShot,
            SnLiquids.burheyna, SnBullets.floodBurheynaShot
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
            alternate = true;
        }};
        chain = new ChainLightningTurret("chain") {{
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
        drr = new ModItemTurret("drr") {{
            hideDetails = false;
            size = 4;
            health = 14000;
            buildVisibility = BuildVisibility.debugOnly;
            ammo(SnItems.flameid, SnBullets.drrRocket);
            range = 50 * Vars.tilesize;
            reloadTime = 0.5f * Time.toSeconds;
            shots = 5;
            burstSpacing = 0.1f * Time.toSeconds;
            category = Category.turret;
            inaccuracy = spread = 0;
            shootShake = 2f;
            shootSound = Sounds.missile;
            shootEffect = Fx.fireSmoke;
        }};
        //endregion 4x4
        //region 5x5
        pressure = new ModItemTurret("pressure") {{
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
            reloadTime = 3f * Time.toSeconds;
            recoilAmount = 4f;
            inaccuracy = 5f;
            rotateSpeed = 3f;
            shootCone = 25f;
            shots = 4;
            shootShake = 2f;
            burstSpacing = 4f;
            restitution = 0.05f;
            targetAir = true;
            hasPower = true;
            shootSound = Sounds.flame;
            consumes.power(7f);
            reloadBar = false;
            guild = SnGuilds.aymirus;
        }};
        field = new ModItemTurret("field") {{
            requirements(Category.turret, with(Items.copper, 1200, Items.lead, 800, Items.plastanium, 350, Items.thorium, 400, SnItems.fors, 400, SnItems.nobium, 300));
            ammo(//todo: phase-fabric, surge-alloy, enojie
            SnItems.fors, SnBullets.artilleryFors, //no
            Items.blastCompound, SnBullets.artilleryBlast //no
            );
            targetAir = true;
            targetGround = true;
            itemCapacity = 40;
            size = 5;
            shots = 1;
            inaccuracy = 4f;
            reloadTime = 50f;
            ammoEjectBack = 4f;
            cooldown = 0.06f;
            velocityInaccuracy = 0.2f;
            restitution = 0.02f;
            recoilAmount = 4f;
            shootShake = 2f;
            range = 370f;
            minRange = 55f;

            health = 160 * size * size;
            guild = SnGuilds.kryonix;
        }};
        sniper = new ModItemTurret("sniper") {{
            requirements(Category.turret, with(Items.copper, 1200, SnItems.fors, 700, Items.surgeAlloy, 600, SnItems.naturite, 500, Items.silicon, 400, SnItems.nobium, 250));
            ammo(
            Items.surgeAlloy, SnBullets.sniperSurge
            );

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
        fanatic = new ModItemTurret("fanatic") {{
            requirements(Category.turret, with(
            Items.copper, 700, Items.graphite, 580, Items.titanium, 900, Items.thorium, 890, Items.silicon, 570, Items.surgeAlloy, 430,
            SnItems.fors, 570, SnItems.naturite, 410, SnItems.nobium, 310));
            ammo(//todo: blast-compound, thorium, reneubite, fors
            Items.thorium, SnBullets.laserArtThorium, //yes
            Items.phaseFabric, SnBullets.laserArtPhase, //no
            SnItems.enojie, SnBullets.laserArtEnojie, //no
            SnItems.reneubite, SnBullets.laserArtReneubite //yes
            );
            health = 4200;
            size = 5;
            shots = 1;
            reloadTime = 2.5f * Time.toSeconds;
            range = 47.5f * Vars.tilesize;
            recoilAmount = 1;
            cooldown = 0.2f;
            restitution = 0.07f;
            inaccuracy = 7;
            rotateSpeed = 2;
            shootCone = 4;
            shootSound = Sounds.shootBig;
            targetAir = false;
            targetGround = true;
            drawLight = true;
            chargeTime = 20;
            guild = SnGuilds.kryonix;
        }};
        defibrillator = new Turret360("defibrillator") {{
            requirements(Category.turret, with(Items.copper, 650, Items.graphite, 550, Items.titanium, 600, Items.thorium, 600, SnItems.fors, 700));
            //todo /\
            ammo(
                    SnItems.nobium, SnBullets.defLight
            );
            health = 2100;
            size = 5;
            shots = 20;
            reloadTime = 1 * Time.toSeconds;
            range = 20f * Vars.tilesize;
            recoilAmount = 0;
            cooldown = 0.3f;
            restitution = 0.8f;
            inaccuracy = 360;
            shootCone = 360;
            shootSound = Sounds.spark;
            targetAir = targetGround = true;
            spread = 18f;
            rotateSpeed = 0;
            powerBullet = SnBullets.powerLight;
            chargeShots = 3;
            debug = true;
        }};
        //endregion 5x5
        //region 6x6
        trident = new ModPowerTurret("trident") {{
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
            chargeEffect = SnFx.tridentCharge;
            category = Category.turret;
            buildVisibility = BuildVisibility.shown;
            shootType = new EnergySphereBulletType(1.75f, 290f) {{
                hitSize = 8f;
                splashDamage = 1280f;
                splashDamageRadius = 7 * Vars.tilesize;
                lightningDamage = 48f;
                lightningPeriod = 30f;
                lightningLength = 192;
                healPercent = 5f;
                lifetime = 250f;
                hitEffect = SnFx.tridentHit;
            }};
            reloadBar = false;
        }};
        radius = new MinigunTurret("radius") {{
            requirements(Category.turret, with(Items.copper, 2500, Items.graphite, 1700, Items.surgeAlloy, 1450, Items.plastanium, 955, SnItems.enojie, 620));
            ammo(
            Items.graphite, SnBullets.heavyStandardDense,
            Items.pyratite, SnBullets.heavyStandardIncendiary,
            Items.thorium, SnBullets.heavyStandardThorium,
            Items.blastCompound, SnBullets.standardBlastBig,
            SnItems.fors, SnBullets.standardForsBig,
            SnItems.reneubite, SnBullets.reneubiteBlast
            );
            reloadTime = 4f;
            coolantMultiplier = 0.5f;
            restitution = 0.15f;
            ammoUseEffect = Fx.casing3;
            range = 50.0f * Vars.tilesize;
            inaccuracy = 3f;
            inaccuracyUp = 0.4f;
            recoilAmount = 2.7f;
            spread = 16f;
            alternate = true;
            shootShake = 4f;
            shots = 2;
            size = 6;
            shootCone = 24f;
            shootSound = Sounds.shootBig;
            maxShootTime = 5f * Time.toSeconds;

            health = 160 * size * size;
            coolantUsage = 0.9f;

            reloadBar = false;
            debug = true;
        }};
        disappearance = new ModLaserTurret("disappearance") {{
            requirements(Category.turret, with(Items.copper, 2400, Items.lead, 1800, Items.graphite, 1500, Items.surgeAlloy, 1460, Items.silicon, 1200, SnItems.enojie, 700));
            shootEffect = Fx.shootBigSmoke2;
            shootCone = 45f;
            recoilAmount = 5f;
            size = 6;
            shootShake = 5f;
            range = 50f * Vars.tilesize;
            reloadTime = 5f * Time.toSeconds;
            shootDuration = 11f * Time.toSeconds;
            restitution = 0.01f;
            cooldown = 0.05f;
            heatColor = Pal.turretHeat;
            powerUse = 26f;
            shootSound = Sounds.laserbig;
            loopSound = Sounds.beam;
            loopSoundVolume = 2.5f;

            shootType = new LightningContinuousLaserBulletType(150) {{
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
            consumes.add(new ConsumeCoolant(1.1f)).update(false);
            reloadBar = false;
            guild = SnGuilds.aymirus;
        }};
        //endregion 6x6
        //region 7x7
        halberd = new ModPowerTurret("halberd") {{
            requirements(Category.turret, with(Items.copper, 3800, Items.metaglass, 2700, Items.lead, 2520, Items.silicon, 2200, Items.plastanium, 1480, SnItems.enojie, 1390));
            range = 350f;
            shots = 1;
            chargeTime = 145f;
            rotateSpeed = 1.6f;
            chargeMaxDelay = 110f;
            chargeEffects = 15;
            recoilAmount = 8f;
            reloadTime = 8f * Time.toSeconds;
            cooldown = 10f;
            powerUse = 30f;
            shootShake = 8f;
            shootEffect = SnFx.galebardShoot;
            smokeEffect = Fx.none;
            chargeEffect = SnFx.galebardLaserCharge;
            chargeBeginEffect = SnFx.galebardLaserChargeBegin;
            heatColor = Pal.turretHeat;
            size = 7;
            health = 170 * size * size;
            targetGround = true;
            targetAir = false;
            shootSound = Sounds.laser;
            alternate = false;
            guild = SnGuilds.aymirus;

            shootType = new LaserBulletType(7500) {{
                colors = new Color[]{Pal.meltdownHit.cpy().a(0.4f), Pal.meltdownHit, Color.white};
                despawnEffect = Fx.none;
                lifetime = 60f;
                drawSize = 440f;
                collidesAir = false;
                length = 370f;
                width = 60f;
            }};
        }};
        pinwheel = new Turret360("pinwheel") {{
            requirements(Category.turret, with(Items.copper, 900, Items.lead, 900, Items.silicon, 710, Items.titanium, 800, Items.thorium, 750, Items.surgeAlloy, 450, SnItems.planatrium, 450, SnItems.fors, 870, SnItems.enojie, 200));
            //todo /\
            ammo(
                    Items.thorium, SnBullets.thoriumFlak,
                    SnItems.fors, SnBullets.forsFlak
            );
            health = 4000;
            size = 7;
            shots = 16;
            reloadTime = 0.75f * Time.toSeconds;
            range = 35f * Vars.tilesize;
            recoilAmount = 0;
            cooldown = 0.3f;
            restitution = 1f;
            inaccuracy = 0;
            shootCone = 360;
            shootSound = Sounds.shootBig;
            targetAir = targetGround = true;
            rotateTurret = true;
            shootShake = 2;
            spread = 22.5f;
            rotateSpeed = 5;
            powerBullet = SnBullets.powerRocket;
            chargeShots = 3;
            debug = true;
        }};
        //endregion 7x7
        //region missile
        sunrise = new MissileSiloTurret("sunrise") {{
            requirements(Category.turret, with(Items.copper, 180, Items.lead, 175, Items.graphite, 165, Items.silicon, 150));
            launchEffect = SnFx.missileLaunchSmall;
            size = 2;
            itemCapacity = 240;
            missile = new MissileType(this) {{
                damage = 0f;
                splashDamage = 590f;
                splashDamageRadius = 105f;
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
        spark = new MissileSiloTurret("spark") {{
            requirements(Category.turret, with(Items.copper, 290, Items.lead, 280, Items.graphite, 275, Items.silicon, 260, Items.plastanium, 255, SnItems.fors, 230));
            launchEffect = SnFx.missileLaunchMedium;
            size = 3;
            itemCapacity = 900;
            missile = new MissileType(this) {{
                damage = 0f;
                splashDamage = 4400f;
                splashDamageRadius = 135f;
                speed = 1.5f;
                explodeEffect = SnFx.sparkMissileExplosion;
            }};
            craftTime = 4200f;
            consumes.items(with(Items.graphite, 270, Items.silicon, 360, Items.plastanium, 450, Items.blastCompound, 540));
            consumes.power(4.5f);
            health = 720;
            maxRange = 450f;
            minRange = 168.0f;
            category = Category.turret;
            buildVisibility = BuildVisibility.shown;
        }};
        dissector = new MissileSiloTurret("dissector") {{
            requirements(Category.turret, with(Items.copper, 500, Items.lead, 510, Items.plastanium, 490, Items.silicon, 480, Items.graphite, 475, SnItems.fors, 430, SnItems.nobium, 400));
            launchEffect = SnFx.missileLaunchLarge;
            size = 4;
            itemCapacity = 2400;
            missile = new MissileType(this) {{
                damage = 0f;
                splashDamage = 17000f;
                splashDamageRadius = 160f;
                speed = 1.25f;
                explodeEffect = SnFx.dissectorMissileExplosion;
            }};
            craftTime = 16800f;
            consumes.items(with(Items.phaseFabric, 440, Items.silicon, 620, Items.surgeAlloy, 800, Items.thorium, 980, Items.plastanium, 1200));
            consumes.power(16f);
            health = 1280;
            maxRange = 550f;
            minRange = 248.0f;
            category = Category.turret;
            buildVisibility = BuildVisibility.shown;
        }};
        art = new MissileSiloTurret("art") {{
            requirements(Category.turret, with(Items.copper, 1400, Items.lead, 1300, Items.plastanium, 1010, Items.silicon, 980, Items.graphite, 975, Items.surgeAlloy, 800, SnItems.enojie, 750));
            launchEffect = SnFx.missileLaunchMedium;
            size = 5;
            itemCapacity = 2400;
            float p1 = 53f / 162f, p2 = 109f / 162f;
            rockets(new Vec2(p1, p1),
            new Vec2(p1, p2),
            new Vec2(p2, p1),
            new Vec2(p2, p2));

            missile = new MissileType(this) {{
                damage = 0f;
                splashDamage = 7200f;
                splashDamageRadius = 145f;
                speed = 1f;
                explodeEffect = SnFx.artMissileExplosion;
            }};
            craftTime = 4500f;
            consumes.items(with(Items.plastanium, 180, Items.silicon, 270, Items.surgeAlloy, 90, Items.thorium, 330));
            consumes.power(7.5f);
            health = 2000;
            maxRange = 620f;
            minRange = 276.0f;
            category = Category.turret;
            buildVisibility = BuildVisibility.shown;
        }};
        //endregion missile
        //region EMP and synthesis
        discharger = new EMPFacility("discharger") {{
            requirements(
            Category.turret, with(
            Items.copper, 1600, Items.lead, 1500, Items.titanium, 1100, Items.thorium, 900, Items.silicon, 1300, Items.plastanium, 850, Items.phaseFabric, 780, Items.surgeAlloy, 910, Items.metaglass, 1000,
            SnItems.fors, 760, SnItems.enojie, 1100));
            size = 3;
            health = 980;
            powerUse = 9f;
            reloadTime = 11 * Time.toSeconds;
            heatColor = Color.valueOf("7FFFD4");
            chargeTime = 180;
            shootType = SnBullets.empBullet;
            range = 22.5f * Vars.tilesize;
            shots = 10;
            zaps = 1;
            zapAngleRand = 360;
            cores.add(new Core(1.2f));
            liquidCapacity = 60;
        }};
        dischargerEvo = new EMPFacility("discharger-evo") {{
            requirements(Category.turret, with(
                    Items.copper, 2000, Items.lead, 1800, Items.thorium, 1200, Items.silicon, 1600, Items.plastanium, 910, Items.phaseFabric, 900, Items.surgeAlloy, 1100, Items.metaglass, 1100,
                    SnItems.fors, 910, SnItems.planatrium, 1000, SnItems.enojie, 1500, SnItems.reneubite, 1000));
            size = 4;
            health = 1200;
            powerUse = 13.1f;
            reloadTime = 13 * Time.toSeconds;
            heatColor = Color.valueOf("7FFFD4");
            chargeTime = 300;
            shootType = SnBullets.empBulletEvo;
            range = 30 * Vars.tilesize;
            shots = 20;
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
            reloadTime = 1.33f * Time.toSeconds;
            range = 25 * Vars.tilesize;
            shots = 1;
            shootCone = 3;
            shootSound = Sounds.railgun;
            maxAmmo = 30;
            spread = 3;
            recoilAmount = 5;
            restitution = 0.1f;
            cooldown = 0.2f;
            coolantUsage = 1.1f;
            //consumes.power(3.8f);
            powerUse = 3.8f;
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
            reloadTime = 1.25f * Time.toSeconds;
            range = 30 * Vars.tilesize;
            shots = 1;
            shootCone = 4;
            shootSound = Sounds.railgun;
            maxAmmo = 30;
            spread = 5.1f;
            recoilAmount = 3;
            restitution = 1f;
            cooldown = 0.01f;
            coolantUsage = 1.7f;
            //consumes.power(6.9f);
            powerUse = 6.9f;
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
            reloadTime = 1 * Time.toSeconds;
            range = 40 * Vars.tilesize;
            shots = 3;
            shootCone = 3;
            shootSound = Sounds.railgun;
            shootShake = 5;
            minRange = 7 * Vars.tilesize;
            maxAmmo = 30;
            spread = 0.1f;
            recoilAmount = 2.1f;
            restitution = 0.55f;
            cooldown = 0.3f;
            coolantUsage = 1.55f;
            //consumes.power(9.2f);
            powerUse = 9.2f;
            burstSpacing = 5f;
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
            reloadTime = 1.66f * Time.toSeconds;
            range = 48 * Vars.tilesize;
            shots = 3;
            shootCone = 1;
            shootSound = Sounds.railgun;
            shootShake = 10;
            minRange = 10 * Vars.tilesize;
            maxAmmo = 30;
            spread = 0;
            recoilAmount = 5;
            restitution = 0.9f;
            cooldown = 0.7f;
            coolantUsage = 0.9f;
            //consumes.power(11.3f);
            powerUse = 11.3f;
            burstSpacing = 2f;
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
            reloadTime = 3 * Time.toSeconds;
            range = 60 * Vars.tilesize;
            shots = 5;
            shootCone = 2.1f;
            shootSound = Sounds.release;
            shootShake = 13;
            minRange = 15 * Vars.tilesize;
            maxAmmo = 30;
            spread = 0.2f;
            recoilAmount = 9;
            restitution = 1.3f;
            cooldown = 0.85f;
            coolantUsage = 3.2f;
            //consumes.power(25);
            powerUse = 25;
            burstSpacing = 3f;
        }};*/
        //endregion synthesis
    }
}
