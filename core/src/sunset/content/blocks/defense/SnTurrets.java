package sunset.content.blocks.defense;

import arc.graphics.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import sunset.content.*;
import sunset.content.blocks.*;
import sunset.entities.bullet.*;
import sunset.graphics.*;
import sunset.type.*;
import sunset.world.blocks.defense.turrets.*;
import sunset.world.blocks.payload.*;
import sunset.world.consumers.*;

import static mindustry.type.ItemStack.with;
import static mma.ModVars.fullName;

public class SnTurrets{
    public static Block
    //2x2
    sway, excellence, carbine, pulsation, reflection,
    //3x3
    fulmina, stailer, trigger, flower,
    //4x4
    aimus, chain,
    //5x5
    //6x6
    trident, radius,
    //7x7

    //missile
    sunrise,//2x2
    spark,//3x3
    dissector,//4x4
    art;//5x5

    public static void load(){

        //region 2x2
        sway = new ModItemTurret("sway"){{
            requirements(Category.turret, with(SnItems.fors, 50, SnItems.erius, 40));
            ammo(
                    SnItems.fors, SnBullets.forsBullet,
                    SnItems.erius, SnBullets.eriusBullet
            );
            shoot = new ShootSpread(3, 6f);
            size = 2;
            reload = 37f;
            range = 19 * Vars.tilesize;
            recoil = 1.4f;
            inaccuracy = 0.2f;
            rotateSpeed = 6f;
            shootCone = 20f;
            shootSound = Sounds.shoot;
            ammoUseEffect = Fx.casing1;
            targetAir = true;
            targetGround = true;
        }};
        /*
        excellence = new ModPowerTurret("excellence"){{
            requirements(Category.turret, with(SnItems.fors, 70, SnItems.erius, 60, SnItems.naturite, 40));
            size = 2;
            reload = 50f;
            range = 175f;
            recoil = 2f;
            inaccuracy = 3f;
            rotateSpeed = 6f;
            shootCone = 3f;
            shootSound = Sounds.laser;
            ammoUseEffect = Fx.none;
            targetAir = false;
            shoot.firstShotDelay = 30f;
            consumePower(1.6f);
            shootType = new BasicBulletType(3f, 60){{
                sprite = fullName("circle-bullet");
                shrinkX = 0f;
                shrinkY = 0f;
                lifetime = 50f;
                height = 7f;
                width = 7f;
                despawnEffect = SnFx.mediumEnergySphereHit;
                frontColor = SnPal.redBomb;
                backColor = SnPal.redBombBack;

                trailColor = SnPal.redBombBack;
                trailWidth = 2f;
                trailLength = 6;
                trailEffect = Fx.artilleryTrail;

                fragBullet = new LaserBulletType(40){{
                    colors = new Color[]{SnPal.redBomb.cpy().a(0.4f), SnPal.redBomb, Color.white};
                    width = 15f;
                    lifetime = 20f;
                    length = 46f;
                    laserEffect = Fx.none;
                    collidesAir = true;
                    collidesGround = true;
                }};
                fragBullets = 1;
                fragRandomSpread = 0.0001f;

                chargeEffect = SnFx.mediumEnergySphereCharge;
            }};
            drawer = new DrawTurret("reinforced-"){{
                parts.add(new RegionPart("-big-spine"){{
                    mirror = true;
                    under = false;
                    moveX = 0.9f;
                    moveY = -1f;
                }});
                parts.add(new RegionPart("-small-spine"){{
                    mirror = true;
                    under = false;
                    moveX = 0.7f;
                    moveY = -1f;
                }});
            }};
        }};
         */
        reflection = new ModItemTurret("reflection"){{
            requirements(Category.turret, with(SnItems.fors, 85, SnItems.erius, 80, SnItems.naturite, 50));
            ammo(
            SnItems.naturite, SnBullets.naturiteCircleBullet,
            SnItems.zarium, SnBullets.anzarCircleBullet
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
        }};
        carbine = new ChainLightningTurret("carbine"){{
            requirements(Category.turret, with(SnItems.fors, 160, SnItems.erius, 140, SnItems.naturite, 60, SnItems.zerinium, 90));
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
        //endregion 2x2
        //region 3x3
        stailer = new ModItemTurret("stailer"){{
            requirements(Category.turret, with(SnItems.fors, 160f, SnItems.erius, 155f, SnItems.naturite, 30f));
            ammo(
                    SnItems.erius, SnBullets.eriusStailerMissile,
                    SnItems.zarium, SnBullets.anzarStailerMissile
            );
            size = 3;
            shoot = new ShootBarrel(){{
                shotDelay = 3f;
                shots = 10;
            }};
            velocityRnd = 0.15f;
            reload = 50f;
            shootCone = 35f;
            rotateSpeed = 2f;
            recoil = 1f;
            shake = 2f;
            range = 25 * Vars.tilesize;
            inaccuracy = 25;
            shootCone = 40;
            targetGround = false;
            targetAir = true;

        }};

        fulmina = new SpeedUpPowerTurret("fulmina"){{
            requirements(Category.turret, with(SnItems.fors, 150, SnItems.naturite, 100, SnItems.zarium, 75));

            shootType = new ArtilleryLightningBulletType(40f){{
                hitShake = 3f;
                lightning = 3;
                lightningColor = SnPal.anzarBullet;
                maxRange = 200;
                hitEffect = SnFx.fulminaLightningHit;
                despawnEffect = Fx.none;
                collidesAir = false;
                collidesGround = true;
            }};
            size = 3;
            inaccuracy = 3f;
            shoot.shots = 1;
            reload = 80f;
            shootCone = 19f;
            rotateSpeed = 5f;
            shotSpeedUp = 0.009f;
            maxReloadMultiplier = 2.4f;
            consumePower(3.5f);
            targetAir = false;
            targetGround = true;
            range = 190f;
            shootEffect = Fx.lightningShoot;
            recoil = 1f;

            shootSound = Sounds.spark;

            shoot = new ShootAlternate(){{
                spread = 5f;
            }};
        }};

        trigger = new ModPowerTurret("trigger"){{
            requirements(Category.turret, with(Items.silicon, 275, Items.titanium, 125, SnItems.entarium, 45));
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
        flower = new FlowerTurret("flower"){{
            requirements(Category.turret, with(SnItems.fors, 95, Items.graphite, 85, Items.lead, 70, SnItems.planatrium, 35));
            size = 4;
            health = 2800;
            reload = 15;
            range = 320;
            shootCone = 3f;
            shootSound = Sounds.bigshot;

            ammo(
                Items.copper, SnBullets.flowerMode1Bullet,
                Items.coal, SnBullets.flowerMode2Bullet
            );
        }};
        aimus = new ModItemTurret("aimus"){{
            requirements(Category.turret, with(Items.copper, 700, SnItems.fors, 570, Items.plastanium, 475, SnItems.nobium, 470));
            ammo(
            SnItems.nobium, SnBullets.nobiumAimMissile,
            SnItems.naturite, SnBullets.naturiteAimMissile,
            SnItems.lenothium, SnBullets.renubiteAimMissile
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
        //endregion 4x4
        //endregion 5x5
        //region 6x6
        trident = new ModPowerTurret("trident"){{
            requirements(Category.turret, with(Items.copper, 1000, Items.lead, 810, Items.silicon, 790, Items.plastanium, 780, Items.surgeAlloy, 670, SnItems.entarium, 600, SnItems.nobium, 400, SnItems.planatrium, 370));
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
            shootType = new EnergySphereBulletType(2.5f, 290f){{
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
            };
            reloadBar = false;
        }};
        //region 7x7
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
            requirements(Category.turret, with(Items.copper, 1400, Items.lead, 1300, Items.plastanium, 1010, Items.silicon, 980, Items.graphite, 975, Items.surgeAlloy, 800, SnItems.entarium, 750));
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
    }
}
