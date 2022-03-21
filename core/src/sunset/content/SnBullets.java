package sunset.content;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.entities.Units;
import mindustry.entities.bullet.*;
import mindustry.gen.Bullet;
import mindustry.gen.Hitboxc;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import sunset.entities.bullet.*;
import sunset.graphics.SnPal;
import sunset.type.StackableStatusEffect;

public class SnBullets implements ContentList {
    //region definitions
    public static BulletType
        //standard
        heavyStandardDense, heavyStandardThorium, heavyStandardIncendiary, standardBlastBig, standardForsBig,
        sniperSurge,
        bigBlastPlastanium, bigBlastBlast, bigBlastPyratite, maxBlastPlastanium, maxBlastBlast, maxBlastSurge, reneubiteBlast,
        reneubiteBullet,
        //bigHelicopterGun, bigHelicopterBullet, largeHelicopterBullet,
        starStunBullet, galaxyKnockbackBullet,
        wheel1Bullet, wheel2Shotgun, wheel3Burst, wheel4Shotgun,  wheel5Bullet,
        mirageGunBullet,
        machineBullet, bigMachineBullet,
        //rocket
        smallBlueMissile, smallEnojieMissile, mediumEnojieMissile,
        powerRocket, drrRocket,
        thoriumPowerRocket, forsPowerRocket,//unused
        nobiumAimMissile, naturiteAimMissile, renubiteAimMissile,
        //artillery
        artilleryFors, artilleryBlast,
        wheel4Artillery,
        plasmaArt, rocketArt, bigRocketArt, heavyRocketArt,//unused
        //flak
        sporePodPoisonBullet, bigSporePodPoison, heavyPlastaniumPoison,
        naturitePoisonBullet, bigNaturitePoison, heavyNaturitePoison,
        bigNobiumPoison, heavyNobiumPoison,
        bigPlastaniumPoison,
        smallPlastaniumBullet, mediumPlastaniumBullet, smallPyratiteBullet, smallSurgeAlloyBullet, smallForsSpine, smallNobiumSpine,
        thoriumFlak, forsFlak,
        //shrapnel
        lightBlastGraphite, lightBlastSilicon,
        //laser
        laserGun2,
        laserCGun, bigCLaserGun,
        //lightning
        defLight, powerLight,
        //sap
        leadSap, sporeSap, planatriumSap,
        //t6 vanilla
        t6sapBullet, t6crawlerBoom, 
        //energy sphere
        smallEnergySphere, mediumEnergySphere, bigEnergySphere,
        //liquid
        floodWaterShot, floodCryoShot, floodSlagShot, floodOilShot, floodBurheynaShot,
        burheynaFrag,
        cometWaterShot,
        //flame
        heavyCoalFlame, heavyPyraFlame, flameidFlame,
        wheel5Flame,
        //reverse-bullets
        naturiteReversBullet, forsReversBullet, nobiumReversBullet,
        infernoFlame,//unused
        //copters
        basicHelicopterGun,
        mediumHelicopterGun, helicopterMissile,
        clusterRocket, copterEnergySphere,
        bigHelicopterGun, laserGun, bigHelicopterMissile,
        largeHelicopterGun, largeHelicopterMissile, smallHelicopterMissile,
        giantHelicopterGun, shrapnelCopterGun, bigClusterRocket, bigCopterEnergySphere,
        //yellow ships
        smallShell,
        mortarBullet,//unused
        salvoArt, smallTorpedo,
        lightningBall, trailRocket,
        //special
        empBullet, empBulletEvo,
        naturiteBolt1, naturiteBolt2, naturiteBolt3, naturiteBolt4, naturiteBolt5,
        laserArtThorium, laserArtPhase, laserArtEnojie, laserArtReneubite,
        universeLaserBullet,
        //misc and testing
        emptyBullet, overheatBullet,
        temp;
        //exoticBullets (new) I will make it later... I must make more bullets (soulBullet, iceSpike, and more)
        //spiralPlastanium, spiralSurge, spiralFors, spiralThorium, spiralSmall;
    //endregion definitions

    @Override
    public void load() {
        //region shell
        //region standard
        heavyStandardDense = new BasicBulletType(8f, 105, "bullet") {{
            hitSize = 5;
            width = 19f;
            height = 24f;
            shootEffect = Fx.shootBig;
        }};
        heavyStandardThorium = new BasicBulletType(9.4f, 180, "bullet") {{
            hitSize = 7;
            width = 20f;
            height = 27f;
            shootEffect = Fx.shootBig;
            pierceCap = 2;
            pierceBuilding = true;
            knockback = 0.7f;
        }};
        heavyStandardIncendiary = new BasicBulletType(7f, 115, "bullet") {{
            hitSize = 7;
            width = 19f;
            height = 24f;
            frontColor = Pal.lightishOrange;
            backColor = Pal.lightOrange;
            status = StatusEffects.burning;
            shootEffect = Fx.shootBig;
            makeFire = true;
            pierceCap = 3;
            pierceBuilding = true;
            knockback = 0.9f;
        }};
        standardBlastBig = new BasicBulletType(9f, 140, "bullet") {{
            hitSize = 5;
            width = 19f;
            height = 24f;
            frontColor = SnPal.blastBullet;
            backColor = SnPal.blastBulletBack;
            status = StatusEffects.burning;
            shootEffect = Fx.shootBig;
            makeFire = true;
            pierceCap = 2;
            pierceBuilding = true;
            knockback = 0.7f;
        }};
        standardForsBig = new BasicBulletType(7.8f, 155, "bullet") {{
            hitSize = 7;
            width = 19f;
            height = 24f;
            frontColor = SnPal.redBomb;
            backColor = SnPal.redBombBack;
            shootEffect = Fx.shootBig;
            pierceCap = 2;
            pierceBuilding = true;
            knockback = 0.84f;
        }};

        sniperSurge = new SniperBulletType() {{
            trailEffect = SnFx.sniperTrail;
            frontColor = Pal.surge;
            trailDelay = 0.2f;
            trailSpace = 32f;
            range = 768f;
            fragBullets = 6;
            fragBullet = new BulletType(8f, 6700f) {{
                ammoMultiplier = 3f;
                hitSize = 7f;
                lifetime = 4f;
                pierce = true;
                trailEffect = shootEffect = hitEffect = despawnEffect = smokeEffect = Fx.none;
                keepVelocity = hittable = false;
            }};
            fragVelocityMin = 1.2f;
            fragVelocityMax = 1.9f;
            fragCone = 150f;
            damage = 6700f;
            pierceSizeMultiplier = 0.9f;
            pierceBuilding = pierce = true;
            buildingDamageMultiplier = 0.4f;
        }};
        bigBlastPlastanium = new BasicBulletType(12, 91) {{
            lifetime = 20f;
            splashDamage = 16f;
            splashDamageRadius = 14f;
            width = 12f;
            height = 16f;
            frontColor = Pal.plastaniumFront;
            backColor = Pal.plastaniumBack;
            collidesAir = false;
        }};
        bigBlastBlast = new BasicBulletType(12, 65) {{
            lifetime = 20f;
            splashDamage = 80f;
            splashDamageRadius = 58f;
            width = 12f;
            height = 16f;
            frontColor = Pal.lightFlame;
            backColor = Pal.darkFlame;
            collidesAir = false;
        }};
        bigBlastPyratite = new BasicBulletType(12, 55) {{
            lifetime = 20f;
            splashDamage = 59f;
            splashDamageRadius = 58f;
            width = 12f;
            height = 16f;
            frontColor = Pal.lightishOrange;
            backColor = Pal.lightOrange;
            status = StatusEffects.burning;
            reloadMultiplier = 1.1f;
            makeFire = true;
            collidesAir = false;
        }};
        bigBlastPlastanium = new BasicBulletType(12, 91) {{
            lifetime = 20f;
            splashDamage = 16f;
            splashDamageRadius = 14f;
            width = 12f;
            height = 16f;
            frontColor = Pal.plastaniumFront;
            backColor = Pal.plastaniumBack;
            collidesAir = false;
        }};
        bigBlastPyratite = new BasicBulletType(12, 46) {{
            lifetime = 20f;
            splashDamage = 59f;
            splashDamageRadius = 58f;
            width = 12f;
            height = 16f;
            frontColor = Pal.lightishOrange;
            backColor = Pal.lightOrange;
            status = StatusEffects.burning;
            reloadMultiplier = 1.1f;
            makeFire = true;
            collidesAir = false;
        }};
        maxBlastPlastanium = new BasicBulletType(16, 125) {{
            lifetime = 22;
            splashDamage = 63;
            splashDamageRadius = 34;
            fragBullets = 9;
            fragBullet = new BasicBulletType(18, 40) {{
                lifetime = 2;
                collidesAir = false;
                frontColor = Pal.plastaniumFront;
                backColor = Pal.plastaniumBack;
                width = 2f;
                height = 3f;
            }};
            hitSound = Sounds.explosion;
            width = 16f;
            height = 20f;
            frontColor = Pal.plastaniumFront;
            backColor = Pal.plastaniumBack;
            collidesAir = false;
        }};
        maxBlastBlast = new BasicBulletType(16, 95) {{
            lifetime = 22f;
            splashDamage = 135f;
            splashDamageRadius = 127f;
            hitSound = Sounds.explosion;
            width = 16f;
            height = 20f;
            frontColor = Pal.lightFlame;
            backColor = Pal.darkFlame;
            collidesAir = false;
        }};
        maxBlastSurge = new BasicBulletType(16, 140) {{
            lifetime = 22f;
            splashDamage = 134f;
            splashDamageRadius = 94f;
            hitSound = Sounds.explosion;
            width = 16f;
            height = 20f;
            frontColor = Pal.surge;
            lightningDamage = 8f;
            lightning = 4;
            lightningLength = 12;
            reloadMultiplier = 0.9f;
            ammoMultiplier = 3f;
            makeFire = true;
            collidesAir = false;
        }};
        reneubiteBlast = new BasicBulletType(9f, 190) {{
            hitSize = 7;
            width = height = 25;
            frontColor = SnPal.renBlast1;
            backColor = SnPal.renBlast2;
            shootEffect = Fx.shootBig2;
            pierceCap = 15;
            pierceBuilding = true;
            knockback = 1;
            hittable = reflectable = absorbable = false;
            makeFire = true;
        }};

        reneubiteBullet = new BasicBulletType(6f, 150f, "missile") {{
            width = 10;
            height = 14;
            lifetime = 30f;
            frontColor = SnPal.renBlast1;
            backColor = Color.white;
            shootEffect = Fx.shootBig2;
            despawnEffect = SnFx.hitReneubiteBullet;
            hittable = reflectable = absorbable = false;
            trailParam = 5f;
            trailLength = 15;
            trailWidth = 2f;
            trailColor = SnPal.renBlast1;
            fragBullets = 1;
            fragCone = 1f;
            fragVelocityMin = 0.9f;
            fragVelocityMax = 0.9f;
            fragBullet = new BasicBulletType(6f, 130f){{
                width = 10;
                height = 14;
                lifetime = 25f;
                frontColor = SnPal.renBlast1;
                backColor = Color.white;
                trailParam = 5f;
                trailLength = 15;
                trailWidth = 2f;
                trailColor = SnPal.renBlast1;
                despawnEffect = SnFx.hitReneubiteBullet;
            }};
        }};

        starStunBullet = new BasicBulletType() {{
            speed = 5f;
            lifetime = 76f;
            damage = 12f;
            splashDamage = 17f;
            splashDamageRadius = 59f;
            statusDuration = 60f * 2.25f;
            status = SnStatusEffects.stun;
            trailEffect = SnFx.stunTrail;
            hitEffect = SnFx.stunExplode;
        }
            @Override
            public void draw(Bullet b) {
                Draw.color(Color.white);
                Fill.circle(b.x, b.y, 3);
                Draw.color();
            }

            @Override
            public void update(Bullet b) {
                if (b.timer(0, 3f)) {
                    trailEffect.at(b.x, b.y);
                }
            }
        };

        wheel1Bullet = new BasicBulletType(6.5f, 5f) {{
            width = 7f;
            height = 9f;
            lifetime = 25f;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
        }};
        wheel2Shotgun = new BasicBulletType(11f, 7f) {{
            hitEffect = despawnEffect = Fx.none;
            instantDisappear = true;
            lifetime = 11f;
            fragBullets = 9;
            keepVelocity = true;
            fragBullet = new BasicBulletType(11f, 7f) {{
                lifetime = 11f;
                keepVelocity = true;
            }};
            fragVelocityMin = 0.7f;
            fragVelocityMax = 1.3f;
            fragLifeMin = 0.7f;
            fragLifeMax = 1.3f;
            fragCone = 16f;
        }};
        wheel3Burst = new BasicBulletType(11f, 43f) {{
            lifetime = 24f;
            status = StatusEffects.blasted;
            splashDamage = 37f;
            splashDamageRadius = 17f;
            width = 8f;
            height = 12f;
        }};
        wheel4Shotgun = new BasicBulletType(12f, 32f) {{
            hitEffect = despawnEffect = Fx.none;
            instantDisappear = true;
            lifetime = 12f;
            fragBullets = 12;
            keepVelocity = true;
            fragBullet = new BasicBulletType(12f, 32f) {{
                lifetime = 12f;
                keepVelocity = true;
            }};
            fragVelocityMin = 0.85f;
            fragVelocityMax = 1.25f;
            fragLifeMin = 0.85f;
            fragLifeMax = 1.25f;
            fragCone = 6f;
        }};
        wheel5Bullet = new BasicBulletType(8f, 62) {{
            width = 8f;
            height = 14f;
            shootEffect = Fx.shootBig;
            pierceCap = 4;
            pierceBuilding = true;
        }};

        mirageGunBullet = new BasicBulletType(12f, 15f) {{
            hitEffect = despawnEffect = Fx.none;
            instantDisappear = true;
            lifetime = 12f;
            fragBullets = 12;
            keepVelocity = true;
            fragBullet = new BasicBulletType(12f, 8f) {{
                lifetime = 12f;
                keepVelocity = true;
            }};
            fragVelocityMin = 0.85f;
            fragVelocityMax = 1.25f;
            fragLifeMin = 0.85f;
            fragLifeMax = 1.25f;
            fragCone = 6f;
        }};

        machineBullet = new BasicBulletType() {{
            lifetime = 32;
            speed = 11;
            damage = 55;
            drawSize = 6.1f;
            pierceCap = 3;
            inaccuracy = 7;
            ammoMultiplier = 2;
            reloadMultiplier = 1.1f;
            buildingDamageMultiplier = 0.6f;
            recoil = 0;
            width = 3;
            height = 3;
            pierce = true;
            pierceBuilding = false;
            shootEffect = smokeEffect = Fx.none;
            collidesAir = absorbable = false;
            trailColor = Pal.lightTrail;
            keepVelocity = true;
            collideFloor = true;
            layer = Layer.scorch;
            splashDamage = 60;
            splashDamageRadius = 90;
        }};
        bigMachineBullet = new BasicBulletType() {{
            lifetime = 32;
            speed = 11;
            damage = 55;
            drawSize = 6.1f;
            pierceCap = 3;
            inaccuracy = 7;
            ammoMultiplier = 2;
            reloadMultiplier = 1.1f;
            buildingDamageMultiplier = 0.6f;
            recoil = 0;
            width = 3;
            height = 3;
            pierce = true;
            pierceBuilding = true;
        }};
        //endregion  standard
        //region rocket
        smallBlueMissile = new BasicBulletType(5f, 15, "missile") {{//yes
            shrinkX = 0f;
            shrinkY = 0f;
            lifetime = 45f;
            height = 15f;
            width = 9f;
            frontColor = SnPal.blueBullet;
            backColor = SnPal.blueBulletBack;

            splashDamage = 60f;
            splashDamageRadius = 40f;

            trailColor = SnPal.blueBullet;
            trailWidth = 3.2f;
            trailLength = 3;
            trailRotation = false;

            fragBullet = new BasicBulletType(3f, 35, "missile") {{//frag
                shrinkX = 0f;
                shrinkY = 0f;
                lifetime = 25f;
                height = 9f;
                width = 6f;
                frontColor = SnPal.blueBullet;
                backColor = SnPal.blueBulletBack;

                splashDamage = 30f;
                splashDamageRadius = 25f;

                trailColor = SnPal.blueBullet;
                trailWidth = 1.4f;
                trailLength = 2;
                trailRotation = false;

                weaveScale = 5f;
                weaveMag = 3f;
            }};
            fragBullets = 6;
            fragCone = 90;

            weaveScale = 5f;
            weaveMag = 3f;
        }};
        smallEnojieMissile = new MissileBulletType(3f, 70) {{//yes
            width = 7f;
            height = 10f;
            shrinkY = 0f;
            drag = -0.003f;
            homingRange = 60f;
            homingPower = 0.5f;

            splashDamageRadius = 60f;
            splashDamage = 45f;

            keepVelocity = false;
            hitSound = Sounds.explosion;
            trailChance = 0.2f;
            lifetime = 60f;
            backColor = SnPal.enojieBulletBack;
            frontColor = SnPal.enojieBullet;
            hitEffect = Fx.blastExplosion;
            despawnEffect = Fx.blastExplosion;
            weaveScale = 4f;
            weaveMag = 2f;
            pierce = true;
            pierceBuilding = true;
            pierceCap = 3;
        }};
        mediumEnojieMissile = new MissileBulletType(4f, 100) {{//yes
            width = 10f;
            height = 16f;
            shrinkY = 0f;
            drag = -0.003f;
            homingRange = 75f;
            homingPower = 1f;

            splashDamageRadius = 85f;
            splashDamage = 130f;

            keepVelocity = false;
            hitSound = Sounds.explosion;
            lifetime = 65f;
            backColor = SnPal.enojieBulletBack;
            frontColor = SnPal.enojieBullet;
            hitEffect = Fx.blastExplosion;
            despawnEffect = Fx.blastExplosion;
            pierce = true;
            pierceBuilding = true;
            pierceCap = 8;
            trailColor = SnPal.enojieBullet;
            trailWidth = 4f;
            trailLength = 11;
            weaveScale = 4f;
            weaveMag = 2f;
        }};

        powerRocket = new MissileBulletType(7f, 920) {{
            width = 10f;
            height = 20f;
            drag = -0.05f;
            homingRange = 13 * Vars.tilesize;
            homingPower = 6f;
            splashDamageRadius = 20 * Vars.tilesize;
            splashDamage = 380;
            keepVelocity = true;
            hitSound = Sounds.explosion;
            trailChance = 0.7f;
            lifetime = 0.7f * Time.toSeconds;
            frontColor = SnPal.redfire1;
            backColor = SnPal.redfire2;
            hitEffect = despawnEffect = Fx.blastExplosion;
            trailEffect = Fx.missileTrail;
            pierce = true;
            pierceBuilding = true;
            pierceCap = 3;
        }};

        drrRocket = new MissileBulletType(11f, 1200) {{
            width = 15f;
            height = 25f;
            drag = -0.05f;
            homingRange = 20 * Vars.tilesize;
            homingPower = 5f;
            splashDamageRadius = 25 * Vars.tilesize;
            splashDamage = 500;
            keepVelocity = true;
            hitSound = Sounds.explosion;
            lifetime = 0.6f * Time.toSeconds;
            frontColor = SnPal.redfire1;
            backColor = SnPal.redfire2;
            hitEffect = despawnEffect = Fx.blastExplosion;
            trailEffect = Fx.missileTrail;
            pierce = pierceBuilding = true;
            pierceCap = 5;
        }};

        thoriumPowerRocket = new MissileBulletType(3, 465) {{
            width = 5f;
            height = 10f;
            drag = -0.05f;
            homingRange = 10 * Vars.tilesize;
            homingPower = 7f;
            splashDamageRadius = 9 * Vars.tilesize;
            splashDamage = 190;
            keepVelocity = true;
            hitSound = Sounds.explosion;
            trailChance = 0.7f;
            lifetime = 1.6f * Time.toSeconds;
            //frontColor = SnPal.redfire1;
            //backColor = SnPal.redfire2;
            hitEffect = despawnEffect = Fx.blastExplosion;
            trailEffect = SnFx.bigRocketTrail;
            pierce = true;
            pierceBuilding = true;
            pierceCap = 5;
        }};//unused
        forsPowerRocket = new MissileBulletType(5, 870) {{
            width = 5f;
            height = 10f;
            drag = -0.05f;
            homingRange = 13 * Vars.tilesize;
            homingPower = 7f;
            splashDamageRadius = 11 * Vars.tilesize;
            splashDamage = 350;
            keepVelocity = true;
            hitSound = Sounds.explosion;
            trailChance = 0.7f;
            lifetime = Time.toSeconds;
            //frontColor = SnPal.redfire1;
            //backColor = SnPal.redfire2;
            hitEffect = despawnEffect = Fx.blastExplosion;
            trailEffect = SnFx.bigRocketTrail;
            pierce = true;
            pierceBuilding = true;
            pierceCap = 6;
        }};//unused
        nobiumAimMissile = new AimBulletType(3f, 55f){{
            sprite = "missile";
            width = 13;
            height = 16;
            lifetime = 250;
            status = StatusEffects.burning;
            statusDuration = 5 * Time.toSeconds;
            frontColor = SnPal.nobiumBullet;
            backColor = SnPal.nobiumBulletBack;
            maxRange = 190;
            homingPower = 0.09f;
            homingRange = 0;
            trailColor = SnPal.nobiumBulletBack;
            trailEffect = SnFx.whiteAimMissileTrail;
            trailRotation = true;
            trailInterval = 0.5f;
            pierce = true;
            pierceCap = 5;
            weaveScale = 4;
            weaveMag = 3;
            drag = 0.0020f;
        }};

        naturiteAimMissile = new AimBulletType(3f, 45f){{
            sprite = "missile";
            splashDamage = 40f;
            splashDamageRadius = 20f;
            width = 13;
            height = 16;
            lifetime = 250;
            frontColor = Pal.bulletYellow;
            backColor = Pal.bulletYellowBack;
            maxRange = 190;
            homingPower = 0.09f;
            homingRange = 0;
            trailColor = Pal.bulletYellow;
            trailEffect = SnFx.yellowAimMissileTrail;
            trailRotation = true;
            trailInterval = 0.4f;
            weaveScale = 4;
            weaveMag = 3;
            drag = 0.00020f;
        }};

        renubiteAimMissile = new AimBulletType(3f, 75f){{
            sprite = "missile";
            splashDamage = 50f;
            splashDamageRadius = 30f;
            width = 13;
            height = 16;
            lifetime = 250;
            frontColor = SnPal.blueBullet;
            backColor = SnPal.blueBulletBack;
            maxRange = 190;
            homingPower = 0.09f;
            homingRange = 0;
            trailColor = SnPal.blueBullet;
            trailEffect = SnFx.blueAimMissileTrail;
            trailRotation = true;
            trailInterval = 0.5f;
            weaveScale = 4;
            weaveMag = 3;
            drag = 0.0020f;
        }};
        //endregion  rocket
        //region artillery
        artilleryFors = new ArtilleryBulletType(3.0f, 150, "shell") {{
            hitEffect = SnFx.redBomb;
            knockback = 1f;
            lifetime = 110f;
            width = height = 25f;
            collidesTiles = false;
            collidesAir = true;
            splashDamageRadius = 60f;
            splashDamage = 150f;
            fragBullet =  new FlakBulletType(3f, 90) {{
                collidesGround = true;
                collidesAir = false;
                splashDamage = 120f;
                splashDamageRadius = 10f;
                sprite = "sunset-red-mine";
                status = StatusEffects.electrified;
                hitEffect = Fx.blastExplosion;
                backColor = SnPal.redBomb;
                frontColor = SnPal.redBombBack;
                spin = 0.2f;
                height = 10f;
                width = 10f;
                lifetime = 400f;
                shrinkX = 0.2f;
                shrinkY = 0.3f;
                drag = 0.04f;
            }};
            fragBullets = 2;
            backColor = SnPal.redBomb;
            frontColor = SnPal.redBombBack;
        }};
        artilleryBlast = new ArtilleryBulletType(3.0f, 125, "shell") {{
            hitEffect = SnFx.redBomb;
            knockback = 1f;
            lifetime = 110f;
            width = height = 25f;
            collidesTiles = false;
            collidesAir = true;
            splashDamageRadius = 70f;
            splashDamage = 140f;
            fragBullet = new FlakBulletType(2.9f, 30) {{
                sprite = "sunset-red-mine";
                collidesGround = true;
                collidesAir = false;
                splashDamage = 100f;
                splashDamageRadius = 12f;
                status = StatusEffects.electrified;
                hitEffect = Fx.blastExplosion;
                makeFire = true;
                backColor = SnPal.redBomb;
                frontColor = SnPal.redBombBack;
                spin = 0.2f;
                height = 10f;
                width = 10f;
                lifetime = 440f;
                shrinkX = 0.2f;
                shrinkY = 0.3f;
                drag = 0.04f;
            }};
            fragBullets = 3;
            makeFire = true;
            backColor = SnPal.blastBullet;
            frontColor = SnPal.blastBulletBack;
        }};

        wheel4Artillery = new ArtilleryBulletType(3f, 67f) {{
            lifetime = 114f;
            status = StatusEffects.blasted;
            splashDamage = 79f;
            splashDamageRadius = 29f;
            width = 8f;
            height = 12f;
        }};

        plasmaArt = new ArtilleryBulletType() {{
            lifetime = 32;
            speed = 5;
            damage = 75;
            drawSize = 6.1f;
            pierceCap = 3;
            inaccuracy = 7;
            ammoMultiplier = 2;
            reloadMultiplier = 1.1f;
            buildingDamageMultiplier = 0.6f;
            recoil = 0;
            width = 3;
            height = 3;
            pierce = true;
            pierceBuilding = true;
        }};
        rocketArt = new ArtilleryBulletType() {{
            backColor = Pal.missileYellowBack;
            frontColor = Pal.missileYellow;
            lifetime = 52;
            speed = 5;
            damage = 75;
            drawSize = 6.1f;
            pierceCap = 3;
            inaccuracy = 7;
            ammoMultiplier = 2;
            reloadMultiplier = 1.1f;
            buildingDamageMultiplier = 0.6f;
            recoil = 0;
            width = 8;
            height = 8;
            pierce = true;
            pierceBuilding = true;
            homingPower = 0.08f;
            shrinkY = 0f;
            hitSound = Sounds.explosion;
            trailChance = 0.2f;
        }};
        bigRocketArt = new ArtilleryBulletType() {{
            backColor = Pal.missileYellowBack;
            frontColor = Pal.missileYellow;
            lifetime = 52;
            speed = 5;
            damage = 75;
            drawSize = 6.1f;
            pierceCap = 3;
            inaccuracy = 6.1f;
            ammoMultiplier = 2;
            reloadMultiplier = 1.1f;
            buildingDamageMultiplier = 0.6f;
            recoil = 0;
            width = 8;
            height = 8;
            pierce = true;
            pierceBuilding = true;
            homingPower = 0.08f;
            shrinkY = 0f;
            hitSound = Sounds.explosion;
            trailChance = 0.2f;
        }};
        heavyRocketArt = new ArtilleryBulletType() {{
            backColor = Pal.missileYellowBack;
            frontColor = Pal.missileYellow;
            lifetime = 52;
            speed = 5;
            damage = 75;
            drawSize = 6.1f;
            pierceCap = 3;
            inaccuracy = 6.1f;
            ammoMultiplier = 2;
            reloadMultiplier = 1.1f;
            buildingDamageMultiplier = 0.6f;
            recoil = 0;
            width = 8;
            height = 8;
            pierce = true;
            pierceBuilding = true;
            homingPower = 0.08f;
            shrinkY = 0f;
            hitSound = Sounds.explosion;
            trailChance = 0.2f;
        }};
        //endregion  artillery
        //region flak
        sporePodPoisonBullet = new FlakBulletType(4f, 5) {{
            sprite = "sunset-circle-bullet";
            knockback = 0.2f;
            lifetime = 100f;
            width = 8f;
            height = 8f;
            hitEffect = Fx.flakExplosion;
            splashDamage = 9f;
            splashDamageRadius = 25f;
            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.04f;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            weaveScale = 8f;
            weaveMag = 1f;
        }};
        bigSporePodPoison = new FlakBulletType(3f, 16) {{
            sprite = "sunset-circle-bullet";
            knockback = 0.5f;
            lifetime = 200f;
            width = 10f;
            height = 10f;
            hitEffect = Fx.flakExplosion;
            splashDamage = 19f;
            splashDamageRadius = 37f;
            shrinkX = 0f;
            shrinkY = 0f;
            drag = 0.015f;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = new FlakBulletType(2f, 8) {{
                sprite = "sunset-circle-bullet";
                lifetime = 250f;
                splashDamage = 10f;
                splashDamageRadius = 19f;
                height = 6f;
                width = 6f;
                hitEffect = Fx.flakExplosion;
                frontColor = SnPal.poisonBullet;
                backColor = SnPal.poisonBulletBack;

                shrinkX = 0f;
                shrinkY = 0f;
                drag = 0.03f;

                trailColor = SnPal.poisonBullet;
                trailWidth = 2f;
                trailLength = 10;

                weaveScale = 9f;
                weaveMag = 1f;
            }};
            fragCone = 90;
            fragBullets = 4;

            trailColor = SnPal.poisonBullet;
            trailWidth = 4f;
            trailLength = 31;

            weaveScale = 9f;
            weaveMag = 1f;
        }};
        heavyPlastaniumPoison = new FlakBulletType(3f, 15) {{
            sprite = "sunset-circle-bullet";
            knockback = 0.5f;
            lifetime = 250f;
            width = 13;
            height = 13f;
            hitEffect = Fx.flakExplosion;
            splashDamage = 16f;
            splashDamageRadius = 35f;
            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.02f;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = new FlakBulletType(2f, 8) {{
                sprite = "sunset-circle-bullet";
                lifetime = 250f;
                splashDamage = 10f;
                splashDamageRadius = 19f;
                height = 6f;
                width = 6f;
                hitEffect = Fx.flakExplosion;
                frontColor = SnPal.poisonBullet;
                backColor = SnPal.poisonBulletBack;

                shrinkX = 0f;
                shrinkY = 0f;
                drag = 0.03f;

                trailColor = SnPal.poisonBullet;
                trailWidth = 2f;
                trailLength = 10;

                weaveScale = 9f;
                weaveMag = 1f;
            }};
            fragBullets = 3;

            weaveScale = 9f;
            weaveMag = 1f;
        }};

        naturitePoisonBullet = new FlakBulletType(4f, 8) {{
            sprite = "sunset-circle-bullet";
            knockback = 0.3f;
            lifetime = 100f;
            width = 8f;
            height = 8f;
            hitEffect = Fx.flakExplosion;
            splashDamage = 9f;
            splashDamageRadius = 24f;
            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.04f;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            weaveScale = 8f;
            weaveMag = 1f;
        }};
        bigNaturitePoison = new FlakBulletType(3f, 21) {{
            sprite = "sunset-circle-bullet";
            knockback = 0.6f;
            lifetime = 200f;
            width = 10f;
            height = 10f;
            hitEffect = Fx.flakExplosion;
            splashDamage = 24f;
            splashDamageRadius = 30f;
            shrinkX = 0f;
            shrinkY = 0f;
            drag = 0.015f;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = new FlakBulletType(2f, 13) {{
                sprite = "sunset-circle-bullet";
                lifetime = 250f;
                splashDamage = 15f;
                splashDamageRadius = 14f;
                height = 6f;
                width = 6f;
                hitEffect = Fx.flakExplosion;
                frontColor = SnPal.poisonBullet;
                backColor = SnPal.poisonBulletBack;

                shrinkX = 0f;
                shrinkY = 0f;
                drag = 0.03f;

                trailColor = SnPal.poisonBullet;
                trailWidth = 2f;
                trailLength = 10;

                weaveScale = 9f;
                weaveMag = 1f;

                collidesAir = true;
                collidesGround = true;
            }};
            fragCone = 90f;
            fragBullets = 5;

            trailColor = SnPal.poisonBullet;
            trailWidth = 4f;
            trailLength = 31;

            weaveScale = 9f;
            weaveMag = 1f;
        }};
        heavyNaturitePoison = new FlakBulletType(3f, 13) {{
            sprite = "sunset-circle-bullet";
            knockback = 0.6f;
            lifetime = 250f;
            width = 13;
            height = 13f;
            hitEffect = Fx.flakExplosion;
            splashDamage = 21f;
            splashDamageRadius = 25f;
            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.02f;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = new FlakBulletType(2f, 5) {{
                sprite = "sunset-circle-bullet";
                lifetime = 150f;
                splashDamage = 12f;
                splashDamageRadius = 9f;
                height = 6f;
                width = 6f;
                hitEffect = Fx.flakExplosion;
                frontColor = SnPal.poisonBullet;
                backColor = SnPal.poisonBulletBack;

                shrinkX = 0.3f;
                shrinkY = 0.3f;
                drag = 0.04f;

                weaveScale = 6f;
                weaveMag = 1f;

                collidesAir = true;
                collidesGround = true;
            }};
            fragBullets = 2;

            weaveScale = 9f;
            weaveMag = 1f;
        }};

        bigNobiumPoison = new FlakBulletType(3f, 19) {{
            sprite = "sunset-circle-bullet";
            knockback = 0.4f;
            lifetime = 200f;
            width = 10f;
            height = 10f;
            hitEffect = Fx.flakExplosion;
            splashDamage = 22f;
            splashDamageRadius = 27f;
            shrinkX = 0f;
            shrinkY = 0f;
            drag = 0.015f;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = new FlakBulletType(2f, 11) {{
                sprite = "sunset-circle-bullet";
                lifetime = 150f;
                splashDamage = 14f;
                splashDamageRadius = 16f;
                height = 4f;
                width = 4f;
                hitEffect = Fx.flakExplosion;
                frontColor = SnPal.poisonBullet;
                backColor = SnPal.poisonBulletBack;

                shrinkX = 0f;
                shrinkY = 0f;
                drag = 0.03f;

                trailColor = SnPal.poisonBullet;
                trailWidth = 2f;
                trailLength = 10;

                weaveScale = 9f;
                weaveMag = 1f;

                collidesAir = true;
                collidesGround = true;
            }};
            fragCone = 90f;
            fragBullets = 4;

            trailColor = SnPal.poisonBullet;
            trailWidth = 4f;
            trailLength = 31;

            weaveScale = 9f;
            weaveMag = 1f;

            homingRange = 15f;
            homingPower = 0.1f;
        }};
        heavyNobiumPoison = new FlakBulletType(3f, 14) {{
            sprite = "sunset-circle-bullet";
            knockback = 0.4f;
            lifetime = 250f;
            width = 13;
            height = 13f;
            hitEffect = Fx.flakExplosion;
            splashDamage = 18f;
            splashDamageRadius = 27f;
            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.02f;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = new FlakBulletType(2f, 6) {{
                sprite = "sunset-circle-bullet";
                lifetime = 150f;
                splashDamage = 10f;
                splashDamageRadius = 11f;
                height = 6f;
                width = 6f;
                hitEffect = Fx.flakExplosion;
                frontColor = SnPal.poisonBullet;
                backColor = SnPal.poisonBulletBack;

                shrinkX = 0.3f;
                shrinkY = 0.3f;
                drag = 0.04f;

                weaveScale = 6f;
                weaveMag = 1f;

                collidesAir = true;
                collidesGround = true;
            }};
            fragBullets = 4;

            weaveScale = 9f;
            weaveMag = 1f;

            homingRange = 15f;
            homingPower = 0.1f;
        }};

        bigPlastaniumPoison = new FlakBulletType(3f, 22) {{
            sprite = "sunset-circle-bullet";
            knockback = 0.4f;
            lifetime = 200f;
            width = 10f;
            height = 10f;
            hitEffect = Fx.flakExplosion;
            splashDamage = 25f;
            splashDamageRadius = 34f;
            shrinkX = 0f;
            shrinkY = 0f;
            drag = 0.015f;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = new FlakBulletType(2f, 14) {{
                sprite = "sunset-circle-bullet";
                lifetime = 150f;
                splashDamage = 17f;
                splashDamageRadius = 25f;
                height = 4f;
                width = 4f;
                hitEffect = Fx.flakExplosion;
                frontColor = SnPal.poisonBullet;
                backColor = SnPal.poisonBulletBack;

                shrinkX = 0f;
                shrinkY = 0f;
                drag = 0.3f;

                trailColor = SnPal.poisonBullet;
                trailWidth = 2f;
                trailLength = 10;

                weaveScale = 9f;
                weaveMag = 1f;
            }};
            fragCone = 90f;
            fragBullets = 6;

            trailColor = SnPal.poisonBullet;
            trailWidth = 4f;
            trailLength = 31;

            weaveScale = 9f;
            weaveMag = 1f;

            homingRange = 15f;
            homingPower = 0.1f;
        }};

        smallPlastaniumBullet = new BasicBulletType(5f, 45) {{//yes
            shrinkX = 0f;
            shrinkY = 0f;
            lifetime = 45f;
            height = 15f;
            width = 10f;
            frontColor = Pal.plastanium;
            backColor = Pal.plastaniumBack;

            fragBullet = new BasicBulletType(3f, 35, "bullet"){{//frag
                width = 6f;
                height = 10f;
                shrinkY = 1f;
                lifetime = 20f;
                frontColor = Pal.plastanium;
                backColor = Pal.plastaniumBack;
                despawnEffect = Fx.none;
            }};
            fragBullets = 4;
        }};
        mediumPlastaniumBullet = new BasicBulletType(6f, 80) {{//yes
            shrinkX = 0f;
            shrinkY = 0f;
            lifetime = 50f;
            height = 18f;
            width = 13f;
            frontColor = Pal.plastanium;
            backColor = Pal.plastaniumBack;

            fragBullet = new BasicBulletType(4f, 40, "bullet"){{
                width = 8f;
                height = 12f;
                shrinkY = 1f;
                lifetime = 20f;
                frontColor = Pal.plastanium;
                backColor = Pal.plastaniumBack;
                despawnEffect = Fx.none;
                fragBullets = 5;
                fragBullet = new BasicBulletType(3f, 25, "bullet"){{
                    width = 5f;
                    height = 9f;
                    shrinkY = 1f;
                    lifetime = 15f;

                    frontColor = Pal.plastanium;
                    backColor = Pal.plastaniumBack;
                    despawnEffect = Fx.none;
                }};
            }};
            fragBullets = 4;
        }};
        smallPyratiteBullet = new BasicBulletType(5f, 35) {{//yes
            shrinkX = 0f;
            shrinkY = 0f;
            lifetime = 45f;
            height = 13f;
            width = 9f;

            splashDamage = 20f;
            splashDamageRadius = 25f;
            makeFire = true;

            frontColor = Pal.bulletYellow;
            backColor = Pal.bulletYellowBack;
        }};
        smallSurgeAlloyBullet = new BasicBulletType(5f, 65) {{//yes
            shrinkX = 0f;
            shrinkY = 0f;
            lifetime = 45f;
            height = 14f;
            width = 10f;

            splashDamage = 10f;
            splashDamageRadius = 14f;
            makeFire = true;

            lightning = 7;
            lightningLength = 6;
            lightningColor = Pal.surge;
            lightningDamage = 18f;

            frontColor = Pal.bulletYellow;
            backColor = Pal.bulletYellowBack;
        }};
        smallForsSpine = new BasicBulletType(5f, 40) {{//yes
            shrinkX = 0f;
            shrinkY = 0f;
            lifetime = 45f;
            height = 13f;
            width = 8f;
            frontColor = SnPal.blastBullet;
            backColor = SnPal.blastBulletBack;

            spin = 8f;

            splashDamage = 40f;
            splashDamageRadius = 35f;
        }};

        smallNobiumSpine = new FlakBulletType(6f, 75) {{
            shrinkX = 0f;
            shrinkY = 0f;
            lifetime = 50f;
            height = 13f;
            width = 8f;
            frontColor = SnPal.nobiumBullet;
            backColor = SnPal.nobiumBulletBack;

            spin = 8f;

            homingPower = 2f;
            homingRange = 120f;
            trailColor = SnPal.nobiumBullet;
            trailLength = 19;
            trailWidth = 5;

            splashDamage = 75f;
            splashDamageRadius = 45f;
        }};

        thoriumFlak = new FlakBulletType(4f, 310) {{
            knockback = 1;
            lifetime = 1.2f * Time.toSeconds;
            width = 20;
            height = 10;
            hitEffect = despawnEffect = SnFx.thoriumExplosion;
            splashDamage = 110;
            splashDamageRadius = 5 * Vars.tilesize;
            frontColor = Color.valueOf("F9A3C7");
            backColor = Color.valueOf("F9A3C7").saturation(4);
            shootEffect = Fx.none;
        }};
        forsFlak = new FlakBulletType(5f, 580) {{
            knockback = 1.2f;
            lifetime = 1 * Time.toSeconds;
            width = 20;
            height = 10;
            hitEffect = despawnEffect = SnFx.forsExplosion;
            splashDamage = 190;
            splashDamageRadius = 7 * Vars.tilesize;
            frontColor = Color.valueOf("F3A39F");
            backColor = Color.valueOf("F3A39F").saturation(4);
            shootEffect = Fx.none;
        }};
        //endregion  flak
        //region shrapnel
        lightBlastGraphite = new ShrapnelBulletType() {{
            speed = 14;
            damage = 20;
            shootEffect = SnFx.spineShoot;
            fromColor = Pal.bulletYellow;
            toColor = Pal.bulletYellowBack;
            width = 10f;
            collidesAir = true;
        }};
        lightBlastSilicon = new ShrapnelBulletType() {{
            damage = 25;
            shootEffect = SnFx.spineShoot;
            fromColor = Pal.bulletYellow;
            toColor = Pal.bulletYellowBack;
            reloadMultiplier = 1.1f;
            width = 19f;
            collidesAir = true;
        }};
        //endregion  shrapnel
        //endregion shell
        //region energy
        //region laser
        laserGun2 = new LaserBulletType(250) {{
            hitSize = 4;
            lifetime = 14;
            lightColor = Color.yellow;
        }};

        laserCGun = new ContinuousLaserBulletType() {{
            damage = 500;
            hitSize = 3;
            drawSize = 220;
            lightColor = Color.yellow;
        }};
        bigCLaserGun = new ContinuousLaserBulletType() {{
            damage = 1000;
            hitSize = 7;
            drawSize = 530;
            lightColor = Color.yellow;
        }};
        //endregion laser
        //region lightning
        defLight = new LightningBulletType() {{
            damage = 320;
            shootEffect = hitEffect = despawnEffect = smokeEffect = Fx.none;
        }};
        powerLight = new LightningBulletType() {{
            inaccuracy = 360;
            /*shootCone = 360;
            targetAir = false;
            targetGround = true;
            drawLight = true;
            spread = 18f;*/
            damage = 736;
            shootEffect = hitEffect = despawnEffect = smokeEffect = Fx.none;
            lightningColor = SnPal.redfire1;
        }};
        //endregion lightning
        //region sap
        leadSap = new SapBulletType() {{
            sapStrength = 0.30f;
            length = 137f;
            damage = 10f;
            shootEffect = Fx.shootSmall;
            despawnEffect = Fx.none;
            width = 1f;
            hitColor = color = Color.valueOf("bf92f9");
            lifetime = 23f;
        }};
        sporeSap = new SapBulletType() {{
            sapStrength = 0.50f;
            length = 137f;
            damage = 14f;
            shootEffect = Fx.shootSmall;
            despawnEffect = Fx.none;
            width = 1f;
            hitColor = color = Color.valueOf("bf92f9");
            lifetime = 23f;
        }};
        planatriumSap = new SapBulletType() {{
            sapStrength = 0.85f;
            length = 137f;
            damage = 17f;
            shootEffect = Fx.shootSmall;
            despawnEffect = Fx.none;
            width = 1f;
            hitColor = color = Color.valueOf("bf92f9");
            lifetime = 23f;
        }};
        //endregion sap
        //region energy sphere
        smallEnergySphere = new BasicBulletType(3f, 20) {{
            sprite = "sunset-circle-bullet";
            shrinkX = 0f;
            shrinkY = 0f;
            lifetime = 30f;
            height = 4f;
            width = 4f;
            hitEffect = Fx.flakExplosion;
            frontColor = SnPal.redBomb;
            backColor = SnPal.redBombBack;
        }};
        mediumEnergySphere = new BasicBulletType(3f, 60) {{
            sprite = "sunset-circle-bullet";
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

            fragBullet = new LaserBulletType(40) {{
                colors = new Color[]{SnPal.redBomb.cpy().a(0.4f), SnPal.redBomb, Color.white};
                width = 20f;
                lifetime = 15f;
                length = 60f;
                laserEffect = Fx.lancerLaserShootSmoke;
                collidesAir = true;
                collidesGround = true;
            }};
            fragBullets = 1;
            fragCone = 0.0001f;
        }};
        bigEnergySphere = new LightningSphereBulletType(4f, 88, "sunset-circle-bullet") {{
            lifetime = 63f;
            splashDamageRadius = 45f;
            splashDamage = 80f;
            height = 13f;
            width = 13f;
            LightningChance = 0.4f;
            lightning = 3;
            lightningLength = 6;
            lightningColor = Pal.bulletYellow;
            lightningDamage = 27f;
            shrinkX = 0f;
            shrinkY = 0f;
            hitEffect = Fx.flakExplosion;
            frontColor = Pal.bulletYellow;
            backColor = Pal.bulletYellowBack;
        }};
        //endregion energy sphere
        //endregion energy
        //region liquid
        floodWaterShot = new ArtilleryLiquidBulletType(Liquids.water) {{
            lifetime = 130f;
            speed = 3f;
            knockback = 10f;
            puddleSize = 18f;
            orbSize = 9f;
            drag = 0.001f;
            statusDuration = 60f * 4f;
            damage = 2.1f;
            fragBullets = 36;
            fragBullet = Bullets.waterShot;
        }};
        floodCryoShot = new ArtilleryLiquidBulletType(Liquids.cryofluid) {{
            lifetime = 130f;
            speed = 3f;
            knockback = 7.2f;
            puddleSize = 18f;
            orbSize = 9f;
            drag = 0.001f;
            statusDuration = 60f * 4f;
            damage = 2.8f;
            fragBullets = 36;
            fragBullet = Bullets.cryoShot;
        }};
        floodSlagShot = new ArtilleryLiquidBulletType(Liquids.slag) {{
            lifetime = 130f;
            speed = 3f;
            knockback = 7.2f;
            puddleSize = 18f;
            orbSize = 9f;
            drag = 0.001f;
            statusDuration = 60f * 4f;
            damage = 31f;
            fragBullets = 36;
            fragBullet = Bullets.slagShot;
        }};
        floodOilShot = new ArtilleryLiquidBulletType(Liquids.oil) {{
            lifetime = 130f;
            speed = 3f;
            knockback = 7.2f;
            puddleSize = 18f;
            orbSize = 9f;
            drag = 0.001f;
            statusDuration = 60f * 4f;
            damage = 2.8f;
            fragBullets = 36;
            fragBullet = Bullets.oilShot;
        }};

        cometWaterShot = new ExtinguishBulletType(Liquids.water) {{
            lifetime = 30f;
            speed = 2.5f;
            knockback = 2.1f;
            puddleSize = 4f;
            orbSize = 2f;
            drag = 0.001f;
            statusDuration = 60f * 2f;
            damage = 0.2f;
        }
            @Override
            public float range() {
                return lifetime * speed;
            }
        };

        floodBurheynaShot = new ArtilleryLiquidBulletType(SnLiquids.burheyna) {{
            lifetime = 130f;
            speed = 3f;
            knockback = 7f;
            puddleSize = 18f;
            orbSize = 9f;
            drag = 0.001f;
            statusDuration = 60f * 4f;
            damage = 10f;
            fragBullets = 36;
            fragBullet = burheynaFrag;
        }};
        //endregion liquid
        //region flame
        heavyCoalFlame = new BulletType(12f, 28f) {{
            ammoMultiplier = 3f;
            hitSize = 7f;
            pierce = true;
            collidesAir = true;
            lifetime = 9f;
            statusDuration = 60f * 4;
            shootEffect = SnFx.heavyFlame;
            hitEffect = Fx.hitFlameSmall;
            despawnEffect = Fx.none;
            status = StatusEffects.burning;
            keepVelocity = false;
            hittable = false;
        }};
        heavyPyraFlame = new BulletType(12f, 35f) {{
            ammoMultiplier = 3f;
            hitSize = 7f;
            pierce = true;
            collidesAir = true;
            lifetime = 9f;
            statusDuration = 60f * 4;
            shootEffect = SnFx.heavyFlame;
            hitEffect = Fx.hitFlameSmall;
            despawnEffect = Fx.none;
            status = StatusEffects.burning;
            keepVelocity = false;
            hittable = false;
        }};
        flameidFlame = new BulletType(12f, 41f) {{
            ammoMultiplier = 3f;
            hitSize = 7f;
            pierce = true;
            collidesAir = true;
            lifetime = 9f;
            statusDuration = 60f * 4;
            shootEffect = SnFx.heavyFlame;
            hitEffect = Fx.hitFlameSmall;
            despawnEffect = Fx.none;
            status = StatusEffects.burning;
            keepVelocity = false;
            hittable = false;
        }};

        wheel5Flame = new BulletType(20f, 120f) {{
            ammoMultiplier = 3f;
            hitSize = 7f;
            lifetime = 7f;
            pierce = true;
            statusDuration = 60f * 4;
            shootEffect = SnFx.shootWheel5Flame;
            hitEffect = Fx.hitFlameSmall;
            despawnEffect = Fx.none;
            status = StatusEffects.burning;
            keepVelocity = false;
            hittable = false;
        }};

        infernoFlame = new FireBulletType(50f, 320f) {{
            ammoMultiplier = 2.1f;
            hitSize = 11f;
            pierce = true;
            lifetime = 0.05f * Time.toSeconds;
            trailEffect = despawnEffect = Fx.none;
            shootEffect = SnFx.redFlame;
            hitEffect = SnFx.redFlameHit;
            status = SnStatusEffects.inferno;
            statusDuration = 60f * 3;
            keepVelocity = false;
            hittable = false;
            /*colorFrom = SnPal.redfire1;
            colorMid = SnPal.redfire2;
            colorTo = Pal.lightPyraFlame;*/
        }};
        //endregion flame
        //region reverse-bullets
        naturiteReversBullet = new ReverseBulletType(2.6f, 95f) {{
            sprite = "sunset-copter-bomb";
            other = naturiteReversBullet;
            reverseNew = true;
            reversAngle = 180;
            width = 15f;
            height = 15;
            lifetime = 235;
            homingPower = 0.05f;
            homingRange = 50f;
            spin = 5f;
            shrinkX = 0f;
            shrinkY = 0f;
            trailWidth = 0;
            trailLength = 0;
            pierceCap = 6;
            rotateMag = 5;
            rotateRight = true;
            frontColor = SnPal.copterLaser;
            backColor = SnPal.copterLaserBack;
            drag = 0.0025f;
        }};

        forsReversBullet = new ReverseBulletType(2.6f, 100f) {{
            sprite = "sunset-copter-bomb";
            other = forsReversBullet;
            width = 15f;
            height = 15;
            lifetime = 235;
            homingPower = 0.05f;
            homingRange = 50f;
            spin = 5f;
            shrinkX = 0f;
            shrinkY = 0f;
            trailWidth = 0;
            trailLength = 0;
            pierceCap = 4;
            rotateMag = 5;
            rotateRight = true;
            frontColor = SnPal.redBomb;
            backColor = SnPal.redBombBack;
            drag = 0.0025f;
        }};

        nobiumReversBullet = new ReverseBulletType(2.6f, 135f) {{
            sprite = "sunset-copter-bomb";
            other = nobiumReversBullet;
            width = 15f;
            height = 15;
            lifetime = 235;
            homingPower = 0.05f;
            homingRange = 50f;
            spin = 5f;
            shrinkX = 0f;
            shrinkY = 0f;
            trailWidth = 0;
            trailLength = 0;
            pierceCap = 7;
            rotateMag = 5;
            rotateRight = true;
            frontColor = SnPal.nobiumBullet;
            backColor = SnPal.nobiumBulletBack;
            drag = 0.0025f;
        }};
        //endregion reverse-bullets
        //region copters
        //T1
        basicHelicopterGun = new BasicBulletType(4.7f, 10) {{
            width = 8f;
            height = 11f;
            lifetime = 35f;
            splashDamageRadius = 3f;
            splashDamage = 1f;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
        }};
        //T2
        mediumHelicopterGun = new BasicBulletType(6f, 13) {{
            width = 9f;
            height = 12f;
            lifetime = 38f;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
        }};
        helicopterMissile = new MissileBulletType(4.2f, 6) {{
            width = 8f;
            height = 11f;
            shrinkY = 0f;
            drag = -0.003f;
            homingRange = 25f;
            homingPower = 0.1f;

            splashDamageRadius = 25f;
            splashDamage = 15f;

            keepVelocity = false;
            hitSound = Sounds.explosion;
            trailEffect = Fx.artilleryTrail;
            lifetime = 40f;
            backColor = Pal.unitBack;
            frontColor = Pal.unitFront;
            hitEffect = Fx.blastExplosion;
            despawnEffect = Fx.blastExplosion;
            weaveScale = 4f;
            weaveMag = 2f;
        }};
        //T3
        clusterRocket = new MissileBulletType(4.7f, 15) {{
            width = 10f;
            height = 14f;
            shrinkY = 0f;
            drag = -0.003f;
            homingRange = 30f;
            homingPower = 0.14f;
            splashDamageRadius = 35f;
            splashDamage = 35f;
            keepVelocity = false;
            hitSound = Sounds.explosion;
            trailChance = 0.3f;
            lifetime = 35f;
            backColor = Pal.unitBack;
            frontColor = Pal.unitFront;
            hitEffect = Fx.blastExplosion;
            despawnEffect = Fx.blastExplosion;
            weaveScale = 5f;
            weaveMag = 3f;

            fragBullet = new MissileBulletType(4.2f, 9) {{
                width = 6f;
                height = 9f;
                shrinkY = 0f;
                drag = -0.003f;
                homingRange = 25f;
                homingPower = 0.09f;

                splashDamageRadius = 20f;
                splashDamage = 17f;

                keepVelocity = false;
                hitSound = Sounds.explosion;
                trailChance = 0.1f;
                lifetime = 25f;
                backColor = Pal.unitBack;
                frontColor = Pal.unitFront;
                hitEffect = Fx.blastExplosion;
                despawnEffect = Fx.blastExplosion;
                weaveScale = 4f;
                weaveMag = 2f;
            }};
            fragCone = 90f;
            fragBullets = 5;
        }};
        copterEnergySphere = new BasicBulletType(3.3f, 13) {{
            sprite = "sunset-circle-bullet";
            lifetime = 40f;
            splashDamageRadius = 15f;
            splashDamage = 23f;
            height = 10f;
            width = 10f;
            lightning = 4;
            lightningLength = 10;
            lightningColor = SnPal.copterLaser;
            lightningDamage = 7f;
            shrinkX = 0.01f;
            shrinkY = 0.01f;
            hitEffect = Fx.flakExplosion;
            frontColor = SnPal.copterLaser;
            backColor = SnPal.copterLaserBack;
        }};
        //T4
        bigHelicopterGun = new BasicBulletType(5f, 45) {{
            width = 10f;
            height = 15f;
            lifetime = 44f;
            hitEffect = Fx.flakExplosion;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
            fragBullet = new BasicBulletType(3f, 15, "bullet"){{
                width = 6f;
                height = 15f;
                shrinkY = 1f;
                lifetime = 20f;
                backColor = Pal.gray;
                frontColor = Color.white;
                despawnEffect = Fx.none;
            }};
            fragBullets = 5;
        }};
        laserGun = new LaserBulletType() {{
            colors = new Color[]{SnPal.copterLaser.cpy().a(0.4f), SnPal.copterLaser, Color.white};
            damage = 55;
            width = 25f;
            lifetime = 15f;
            length = 135f;
            laserEffect = Fx.lancerLaserShootSmoke;
            collidesAir = true;
            collidesGround = true;
        }};
        bigHelicopterMissile = new MissileBulletType(4.7f, 20) {{
            width = 9f;
            height = 14f;
            shrinkY = 0f;
            drag = -0.003f;
            homingRange = 50f;
            homingPower = 0.2f;

            splashDamageRadius = 40f;
            splashDamage = 40f;

            keepVelocity = false;
            hitSound = Sounds.explosion;
            trailChance = 0.2f;
            lifetime = 40f;
            backColor = Pal.unitBack;
            frontColor = Pal.unitFront;
            hitEffect = Fx.blastExplosion;
            despawnEffect = Fx.blastExplosion;
            weaveScale = 5f;
            weaveMag = 3f;
        }};
        //T5
        largeHelicopterGun = new BasicBulletType(6f, 55) {{
            width = 17f;
            height = 27f;
            lifetime = 38f;
            hitEffect = Fx.hitBulletBig;
            shootEffect = Fx.shootBig2;
            smokeEffect = Fx.shootBigSmoke;
            fragBullet = new LaserBulletType(45) {{
                colors = new Color[]{SnPal.copterLaser.cpy().a(0.4f), SnPal.copterLaser, Color.white};
                width = 20f;
                lifetime = 15f;
                length = 75f;
                laserEffect = Fx.lancerLaserShootSmoke;
                collidesAir = true;
                collidesGround = true;
            }};
            fragBullets = 1;
            fragCone = 0.0001f;
        }};
        largeHelicopterMissile = new BasicBulletType(6.3f, 60, "missile-large") {{
            width = 14f;
            height = 17f;
            hitShake = 3f;
            lifetime = 46f;
            hitEffect = Fx.massiveExplosion;
            keepVelocity = false;
            hitSound = Sounds.explosion;
            shrinkY = 0f;
            drag = -0.015f;
            homingRange = 85f;
            homingPower = 0.3f;
            splashDamage = 75f;
            splashDamageRadius = 70f;
            backColor = Pal.missileYellowBack;
            frontColor = Pal.missileYellow;
            trailEffect = SnFx.bigRocketTrail;
            trailRotation = true;
            trailInterval = 0.5f;
        }};
        smallHelicopterMissile = new MissileBulletType(4.4f, 40) {{
            width = 11f;
            height = 11f;
            shrinkY = 0f;
            lifetime = 60f;
            splashDamageRadius = 35f;
            splashDamage = 40f;
            hitEffect = Fx.blastExplosion;
            despawnEffect = Fx.blastExplosion;
            homingRange = 100;
            homingPower = 0.5f;
            weaveScale = 4f;
            weaveMag = 2f;
            pierce = true;
            pierceBuilding = true;
            pierceCap = 5;
        }};
        //T6
        giantHelicopterGun = new BasicBulletType(6.4f, 75) {{
            hitSize = 6;
            width = 17f;
            height = 24f;
            lifetime = 49f;
            hitEffect = Fx.flakExplosion;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
        }};
        shrapnelCopterGun = new ShrapnelBulletType() {{
            length = 190f;
            width = 50f;
            damage = 170;
            serrationLenScl = 10f;
            serrationSpaceOffset = 70f;
            serrationFadeOffset = 0f;
            serrations = 15;
            serrationWidth = 7f;
            shootEffect = SnFx.copterShoot;
            fromColor = SnPal.copterLaser;
            toColor = SnPal.copterLaserBack;
            collidesAir = true;
            collidesGround = true;
        }};
        bigClusterRocket = new MissileBulletType(4.5f, 65, "missile-large") {{
            width = 14f;
            height = 18f;
            shrinkY = 0f;
            drag = -0.003f;
            homingRange = 10f;
            homingPower = 0.2f;
            splashDamageRadius = 80f;
            splashDamage = 95f;
            keepVelocity = false;
            hitSound = Sounds.explosion;
            trailChance = 0.3f;
            lifetime = 40f;
            backColor = Pal.unitBack;
            frontColor = Pal.unitFront;
            hitEffect = Fx.blastExplosion;
            despawnEffect = Fx.blastExplosion;
            weaveScale = 5f;
            weaveMag = 3f;

            fragBullet = new MissileBulletType(4f, 55) {{
                width = 12f;
                height = 13f;
                shrinkY = 0f;
                lifetime = 55f;
                splashDamageRadius = 45f;
                splashDamage = 60f;
                hitEffect = Fx.blastExplosion;
                despawnEffect = Fx.blastExplosion;
                homingRange = 140;
                homingPower = 2f;
                weaveScale = 4f;
                weaveMag = 2f;
                pierce = true;
                pierceBuilding = true;
                pierceCap = 10;
            }};
            fragCone = 90f;
            fragBullets = 3;
        }};
        bigCopterEnergySphere = new LightningSphereBulletType(4f, 50, "sunset-circle-bullet") {{
            lifetime = 50f;
            splashDamageRadius = 90f;
            splashDamage = 80f;
            height = 13f;
            width = 13f;
            LightningChance = 0.2f;
            lightning = 3;
            lightningLength = 8;
            lightningColor = SnPal.copterLaser;
            lightningDamage = 30f;
            shrinkX = 0.01f;
            shrinkY = 0.01f;
            hitEffect = Fx.flakExplosion;
            frontColor = SnPal.copterLaser;
            backColor = SnPal.copterLaserBack;
        }};
        //endregion copters
        //region yellow ships
        //T1
        smallShell = new BasicBulletType() {{
            lifetime = 0.25f * Time.toSeconds;
            speed = 11;
            damage = 10;
            drawSize = 6.1f;
            pierceCap = 3;
            ammoMultiplier = 2;
            reloadMultiplier = 1.1f;
            buildingDamageMultiplier = 0.6f;
            //recoil = 0;
            width = 5.7f;
            height = 8.7f;
            pierce = true;
            pierceBuilding = false;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
            collidesAir = absorbable = false;
            trailEffect = SnFx.acTrail;
            //trailColor = Pal.lightTrail;
            splashDamage = 15;
            splashDamageRadius = 3 * Vars.tilesize;
        }};

        mortarBullet = new ArtilleryBulletType(4f, 30, "shell") {{
            hitEffect = Fx.flakExplosion;
            knockback = 0.8f;
            lifetime = 0.7f * Time.toSeconds;
            width = 7;
            height = 11f;
            collidesTiles = false;
            splashDamageRadius = 5f * Vars.tilesize;
            splashDamage = 20f;
            fragBullets = 5;
            fragBullet = new FlakBulletType(6, 0) {{
                collidesGround = true;
                collidesAir = false;
                splashDamage = 22;
                splashDamageRadius = 13 * Vars.tilesize;
                sprite = "sunset-red-mine";
                backColor = SnPal.yellowTrail;
                frontColor = SnPal.yellowTrailBack;
                spin = 0.5f;
                height = width = 11f;
                lifetime = 3.7f * Time.toSeconds;
                shrinkX = shrinkY = 0.5f;
                drag = 0.07f;
            }};
        }};//unused
        //T2
        salvoArt = new ArtilleryBulletType(5f, 29, "shell") {{
            frontColor = SnPal.yellowTrail;
            backColor = SnPal.yellowTrailBack;
            width = 7f;
            height = 12f;
            shootEffect = Fx.shootBig2;
            smokeEffect = Fx.shootBigSmoke2;
            ammoMultiplier = 2.1f;
            lifetime = 0.7f * Time.toSeconds;
        }};
        smallTorpedo = new TorpedoBulletType(2, 120) {{
            lifetime = 1.6f * Time.toSeconds;
            drawSize = 9.2f;
            pierceCap = -1;
            inaccuracy = 1;
            ammoMultiplier = 1;
            reloadMultiplier = 3;
            buildingDamageMultiplier = 0.9f;
            recoil = 0;
            pierce = true;
            pierceBuilding = false;
            //splashDamage = 40;
            //splashDamageRadius = 10 * Vars.tilesize;
        }};
        //T3
        lightningBall = new ArtilleryLightningBulletType(180) {{
            lightning = 5;
            lightningColor = SnPal.yellowTrailBack;
            maxRange = 320;
        }};
        trailRocket = new MissileBulletType(5, 210, "shell") {{
            width = 13f;
            height = 19f;
            shrinkY = 0.1f;
            drag = -0.003f;
            lifetime = 65f;
            splashDamageRadius = 45f;
            splashDamage = 65f;
            homingPower = 0.1f;
            homingRange = 5 * Vars.tilesize;
            homingDelay = Time.toSeconds;
            trailColor = SnPal.yellowTrailBack;
        }};
        //T4
        //T5
        //T6
        //endregion yellow ships
        //region buffer
        cometWaterShot = new ExtinguishBulletType(Liquids.water) {{
            lifetime = 30f;
            speed = 2.5f;
            knockback = 2.1f;
            puddleSize = 4f;
            orbSize = 2f;
            drag = 0.001f;
            statusDuration = 60f * 2f;
            damage = 0.2f;
        }
            @Override
            public float range() {
                return lifetime * speed;
            }
        };
        starStunBullet = new BasicBulletType() {{
            speed = 5f;
            lifetime = 76f;
            damage = 12f;
            splashDamage = 17f;
            splashDamageRadius = 59f;
            statusDuration = 60f * 2.25f;
            status = SnStatusEffects.stun;
            trailEffect = SnFx.stunTrail;
            hitEffect = SnFx.stunExplode;
        }
            @Override
            public void draw(Bullet b) {
                Draw.color(Color.white);
                Fill.circle(b.x, b.y, 3);
                Draw.color();
            }

            @Override
            public void update(Bullet b) {
                if (b.timer(0, 3f)) {
                    trailEffect.at(b.x, b.y);
                }
            }
        };
        galaxyKnockbackBullet = new PointBulletType(){{
            speed = 420; //range
            damage = 120;
            knockback = 384;
            shootEffect = Fx.none;
            smokeEffect = Fx.none;
            trailEffect = SnFx.galaxyMainTrail;
            hitEffect = SnFx.galaxyMainHit;
            despawnEffect = SnFx.galaxyMainHit;
            trailSpacing = 8;
        }};

        universeLaserBullet = new LaserBulletType(){{
            length = 520f;
            damage = 1470f;
            width = 105f;
            lifetime = 40f;
            largeHit = true;
            status = SnStatusEffects.universityLaserSlow;
            statusDuration = 180f;
            lightColor = lightningColor = Pal.surge;
            shootEffect = SnFx.univerityLaserCharge;
            colors = new Color[]{ Pal.surge.cpy().a(0.4f), Pal.surge };
        }
            double stunChance = 0.075;
            float stunDuration = 480f;

            @Override
            public void hitEntity(Bullet b, Hitboxc entity, float health) {
                super.hitEntity(b, entity, health);
                if(Mathf.chance(stunChance) && entity instanceof Unit) {
                    Log.info("Hit " + entity);
                    ((Unit)entity).apply(SnStatusEffects.stun, stunDuration);
                }
            }
        };
        //endregion buffer
        //region special
        //region EMP
        empBullet = new LightningBulletType() {{
            damage = 320;
            lifetime = 50;
            status = SnStatusEffects.electricalShort;
            drawSize = 3;
            collidesTeam = true;
            hitSize = 36;
        }};
        empBulletEvo = new LightningBulletType() {{
            damage = 700;
            lifetime = 100;
            status = SnStatusEffects.electricalShort;
            drawSize = 5.3f;
            collidesTeam = true;
            hitSize = 36;
        }};
        //endregion EMP
        //region synthesis
        naturiteBolt1 = new EnergyBoltBulletType(11, 890) {{
            status = SnStatusEffects.molecula;
            lifetime = 7;
            inaccuracy = 7;
            width = 10;
            height = 35;
        }};
        naturiteBolt2 = new EnergyBoltBulletType(14, 1570) {{
            status = SnStatusEffects.molecula;
            lifetime = 6;
            inaccuracy = 11;
            width = 10;
            height = 35;
            fragBullets = 3;
            fragCone = 35;
            fragBullet = new LaserBoltBulletType(12, 880) {{
                status = SnStatusEffects.molecula;
                lifetime = 30;
                backColor = Pal.heal;
                frontColor = Color.white;
            }};
        }};
        naturiteBolt3 = new EnergyBoltBulletType(12, 2100) {{
            status = SnStatusEffects.molecula;
            lifetime = 12;
            inaccuracy = 21;
            width = 10;
            height = 35;
            fragBullets = 1;
            fragBullet = new BulletType() {{
                damage = 0;
                knockback = 15;
                despawnEffect = Fx.none;
                hitEffect = Fx.none;
            }};
        }};
        naturiteBolt4 = new EnergyBoltBulletType(17, 2750) {{
            status = SnStatusEffects.molecula;
            lifetime = 16;
            inaccuracy = 2;
            width = 10;
            height = 35;
            fragBullets = 6;
            fragCone = 360;
            fragBullet = new LaserBoltBulletType(12, 880) {{
                status = SnStatusEffects.molecula;
                lifetime = 7.5f;
                backColor = Pal.heal;
                frontColor = Color.white;
            }};
            splashDamage = 500;
            splashDamageRadius = 10 * Vars.tilesize;
            trailChance = 1;
            trailEffect = Fx.none;
            //trailEffect = SnFx.greenInstTrail;
        }};
        naturiteBolt5 = new ArtilleryBulletType(9, 200) {{
            lifetime = 16;
            inaccuracy = 2;
            width = 12;
            height = 37;
            fragBullets = 3;
            fragCone = 360;
            fragBullet = new BombBulletType(2300, 5 * Vars.tilesize) {{
                lifetime = 11;
                width = 5;
                height = 7;
                backColor = Pal.heal;
                frontColor = Color.white;
                fragBullets = 12;
                fragCone = 360;
                fragBullet = new EnergyBoltBulletType(15, 1500) {{
                    status = SnStatusEffects.molecula;
                    lifetime = 8f;
                    width = 8;
                    height = 33;
                    backColor = Pal.heal;
                    frontColor = Color.white;
                }};
            }};
            splashDamage = 600 * Vars.tilesize;
            splashDamageRadius = 56;
            trailChance = 1;
            trailEffect = Fx.artilleryTrail;
        }};
        //endregion synthesis
        //region laser art
        laserArtThorium = new ArtilleryBulletType(5, 200, "shell") {{
            knockback = 3.4f;
            lifetime = 77;
            width = height = 12;
            splashDamageRadius = 10 * Vars.tilesize;
            splashDamage = 430;
            fragBullets = 6;
            fragCone = 360;
            fragBullet = new ContinuousLaserBulletType(9.1f) {{
                length = 35;
            }};
            despawnEffect = hitEffect = SnFx.laserArtHit;

            trailColor = Items.thorium.color;
            trailWidth = 4;
            trailLength = 7;
            trailEffect = Fx.artilleryTrail;
            status = StatusEffects.blasted;
            statusDuration = 180;
        }};
        laserArtPhase = new ArtilleryBulletType(5, 300, "shell") {{
            knockback = 0.2f;
            lifetime = 77;
            width = height = 12;
            splashDamageRadius = 3 * Vars.tilesize;
            splashDamage = 150;
            fragBullets = 6;
            fragCone = 360;
            fragBullet = new ContinuousLaserBulletType(8.75f) {{
                length = 55;
            }};
            despawnEffect = hitEffect = SnFx.laserArtHit;

            trailColor = Items.phaseFabric.color;
            trailWidth = 4;
            trailLength = 7;
            trailEffect = Fx.artilleryTrail;
            status = StatusEffects.corroded;
            statusDuration = 300;
        }};
        laserArtReneubite = new ArtilleryBulletType(5, 450, "shell") {{
            knockback = 2;
            lifetime = 77;
            width = height = 12;
            splashDamageRadius = 6.25f * Vars.tilesize;
            splashDamage = 100;
            fragBullets = 6;
            fragCone = 360;
            fragBullet = new ContinuousLaserBulletType(20) {{
                length = 40;
            }};
            despawnEffect = hitEffect = SnFx.laserArtHit;

            trailColor = SnItems.reneubite.color;
            trailWidth = 4;
            trailLength = 7;
            trailEffect = Fx.artilleryTrail;
            status = StatusEffects.electrified;
            statusDuration = 180;
        }};
        laserArtEnojie = new ArtilleryBulletType(5, 920, "shell") {{
            knockback = 1;
            lifetime = 77;
            width = height = 12;
            splashDamageRadius = 6.25f * Vars.tilesize;
            splashDamage = 250;
            fragBullets = 6;
            fragCone = 360;
            fragBullet = new ContinuousLaserBulletType(40) {{
                length = 60;
            }};
            despawnEffect = hitEffect = SnFx.laserArtHit;

            trailColor = SnItems.enojie.color;
            trailWidth = 4;
            trailLength = 7;
            trailEffect = Fx.artilleryTrail;
            status = SnStatusEffects.electricalShort;
            statusDuration = 120;
        }};
        //endregion laser art
        //endregion special
        //region t6 vanilla
        t6sapBullet = new SapFlyingBulletType(9f, 120){{
            sprite = "missile-large";
            lifetime = 24;
            homingPower = 0.08f;
            homingRange = 600f;
            
            frontColor = Color.white;
            backColor = Pal.sap;
            trailColor = Pal.sap;
            trailWidth = 4f;
            trailLength = 48;
        }};
        t6crawlerBoom = new BombBulletType(0f, 0f, "clear"){{
            status = StatusEffects.sapped;
            hitEffect = Fx.sapExplosion;
            lifetime = 10f;
            speed = 1f;
            splashDamageRadius = 80f;
            instantDisappear = true;
            splashDamage = 210f;
            killShooter = true;
            hittable = false;
            collidesAir = true;
        }};
        //endregion t6 vanilla
        //region misc and testing
        overheatBullet = new BasicBulletType(0.1f, 7, "error") {{
            //shootEffect = Fx.none;
            hitEffect = Fx.none;
            despawnEffect = Fx.none;
            trailEffect = Fx.none;
            smokeEffect = Fx.none;
            status = SnStatusEffects.overheat;
            statusDuration = 120f;
            shootEffect = Fx.pointBeam;
            lightningColor = Color.red;
            instantDisappear = true;
            width = 0;
            height = 0;
        }
            @Override
            public void init(Bullet b) {
                Unit u = Units.closestEnemy(b.team, b.x, b.y, range(),
                        unit -> Angles.within(b.rotation(), b.angleTo(unit.x, unit.y), 10));
                if (u != null) {
                    shootEffect.at(b.x, b.y, b.rotation(), lightningColor, new Vec2(u.x,u.y));
                    u.damagePierce(b.damage);
                    ((StackableStatusEffect) status).apply(u, statusDuration);
                } else {
                    shootEffect.at(b.x, b.y, b.rotation(), lightningColor,
                            new Vec2().setLength(range()).setAngle(b.rotation()).add(b.x, b.y));
                }
            }

            @Override
            public float range() {
                return 340;
            }
        };
        emptyBullet = new BasicBulletType(0, 0, "error") {{
            shootEffect = Fx.none;
            hitEffect = Fx.none;
            despawnEffect = Fx.none;
            trailEffect = Fx.none;
            smokeEffect = Fx.none;
            instantDisappear = true;
            width = 0;
            height = 0;
            lifetime = 0;
        }};
        //endregion misc and testing
    }
}
