package sunset.content;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Angles;
import arc.math.geom.Vec2;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.entities.Units;
import mindustry.entities.bullet.*;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import sunset.entities.bullet.*;
import sunset.graphics.SnPal;
import sunset.type.StackableStatusEffect;

public class SnBullets implements ContentList {
    public static BulletType
    //sap
    leadSap, sporeSap, planatriumSap,
    //artillery
    artilleryForsMine, artilleryFors, artilleryBlastMine, artilleryBlast,
    //heavy-standard
    heavyStandardDense, heavyStandardThorium, heavyStandardIncendiary, standardBlastBig, standardForsBig, reneubiteBlast,
    //sniper
    sniperSurge, sniperSurgeFrag,
    //burner
    heavyCoalFlame, heavyPyraFlame, flameidFlame,
    //liquid
    floodWaterShot, floodCryoShot, floodSlagShot, floodOilShot, burheynaFrag,
    //blast
    lightBlastGraphite, lightBlastSilicon,
    bigBlastPlastanium, bigBlastBlast, bigBlastPyratite,
    maxBlastPlastanium, maxBlastBlast, maxBlastSurge, maxBlastPlastaniumFrag,
    //poison
    sporePodPoisonBullet, naturitePoisonBullet,
    sporePodPoisonFrag, heavySporePodPoison, naturitePoisonFrag, heavyNaturitePoison, nobiumPoisonFrag, heavyNobiumPoison,
    bigSporePodPoisonFrag, bigSporePodPoison, bigNaturitePoisonFrag, bigNaturitePoison, bigNobiumPoisonFrag, bigNobiumPoison, bigPlastaniumPoisonFrag, bigPlastaniumPoison,
    //energy-sphere
    smallEnergySphere, mediumEnergySphereFrag, mediumEnergySphere,
    //units
    basicHelicopterGun, mediumHelicopterGun, helicopterMissile, clusterRocketSmall, clusterRocket, copterEnergySphere, bigHelicopterGunFrag, bigHelicopterGun, bigHelicopterMissile, laserGun, laserHelicopterFrag, largeHelicopterGun, largeHelicopterMissile, smallHelicopterMissiles, shrapnelCopterGun, gigantHelicopterGun, bigClusterRocketSmall, bigClusterRocket, bigCopterEnergySphere,
    cometWaterShot, starStunBullet, galaxyKnockbackBullet,
    wheel1Bullet, wheel2Shotgun, wheel3Burst, wheel4Shotgun, wheel4Artillery, wheel5Flame, wheel5Bullet, mirageGunBullet,
    //misc
    emptyBullet, overheatBullet,
    //special
    empBullet, empBulletEvo, synthesisBullet1, synthesisBullet2, synthesisBullet3, synthesisBullet4, laserArtThorium, laserArtPhase, laserArtEnojie, laserArtReneubite,
    //misc
    testBullet, tempBullet1, tempBullet2, testBullet0, testBullet1, testBullet2, testBullet3;
    //exoticBullets (new) I will make it later... I must make more bullets (soulBullet, iceSpike, and more)
    //spiralPlastanium, spiralSurge, spiralFors, spiralThorium, spiralSmall;

    @Override
    public void load() {
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
        //region artillery
        artilleryBlast = new ArtilleryBulletType(3.0f, 75, "shell") {{
            hitEffect = SnFx.redBomb;
            knockback = 1f;
            lifetime = 110f;
            width = height = 25f;
            collidesTiles = false;
            collidesAir = true;
            splashDamageRadius = 66f * 0.75f;
            splashDamage = 90f;
            fragBullet = artilleryBlastMine;
            fragBullets = 3;
            makeFire = true;
            backColor = SnPal.blastBullet;
            frontColor = SnPal.blastBulletBack;
        }};
        artilleryFors = new ArtilleryBulletType(3.0f, 70, "shell") {{
            hitEffect = SnFx.redBomb;
            knockback = 1f;
            lifetime = 110f;
            width = height = 25f;
            collidesTiles = false;
            collidesAir = true;
            splashDamageRadius = 58f * 0.75f;
            splashDamage = 120f;
            fragBullet = artilleryForsMine;
            fragBullets = 2;
            backColor = SnPal.redBomb;
            frontColor = SnPal.redBombBack;
        }};

        artilleryBlastMine = new FlakBulletType(2.9f, 30) {{
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
        artilleryForsMine = new FlakBulletType(3f, 40) {{
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
        //endregion artillery
        //region sniper
        sniperSurgeFrag = new BulletType(8f, 6700f) {{
            ammoMultiplier = 3f;
            hitSize = 7f;
            lifetime = 4f;
            pierce = true;
            trailEffect = Fx.none;
            shootEffect = Fx.none;
            hitEffect = Fx.none;
            despawnEffect = Fx.none;
            smokeEffect = Fx.none;
            keepVelocity = false;
            hittable = false;
        }};
        sniperSurge = new SniperBulletType() {{
            trailEffect = SnFx.sniperTrail;
            frontColor = Pal.surge;
            trailDelay = 0.2f;
            trailSpace = 32f;
            range = 768f;
            fragBullets = 6;
            fragBullet = sniperSurgeFrag;
            fragVelocityMin = 1.2f;
            fragVelocityMax = 1.9f;
            fragCone = 150f;
            damage = 6700f;
            pierceSizeMultiplier = 0.9f;
            pierceBuilding = pierce = true;
            buildingDamageMultiplier = 0.4f;
        }};
        //endregion sniper
        //region big standard
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
        reneubiteBlast = new BasicBulletType(9f, 190){{
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
        //endregion big standard
        //region burn
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
        //endregion burn
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
        burheynaFrag = new LiquidBulletType(SnLiquids.burheyna) {{
            damage = 3.1f;
            speed = 2.8f;
        }};
        //endregion liquid
        //region blast
        lightBlastGraphite = new ShrapnelBulletType() {{
            speed = 14;
            damage = 20;
            fromColor = Pal.bulletYellow;
            toColor = Pal.bulletYellowBack;
            width = 10f;
            collidesAir = true;
        }};
        lightBlastSilicon = new ShrapnelBulletType() {{
            damage = 25;
            fromColor = Pal.bulletYellow;
            toColor = Pal.bulletYellowBack;
            reloadMultiplier = 1.1f;
            width = 19f;
            collidesAir = true;
        }};
        bigBlastPlastanium = new BasicBulletType(12, 81) {{
            lifetime = 20f;
            splashDamage = 16f;
            splashDamageRadius = 14f;
            width = 12f;
            height = 16f;
            frontColor = Pal.plastaniumFront;
            backColor = Pal.plastaniumBack;
            collidesAir = false;
        }};
        bigBlastBlast = new BasicBulletType(12, 59) {{
            lifetime = 20f;
            splashDamage = 75f;
            splashDamageRadius = 58f;
            width = 12f;
            height = 16f;
            frontColor = Pal.lightFlame;
            backColor = Pal.darkFlame;
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
        maxBlastPlastaniumFrag = new BasicBulletType(18, 36) {{
            lifetime = 2;
            collidesAir = false;
            frontColor = Pal.plastaniumFront;
            backColor = Pal.plastaniumBack;
            width = 2f;
            height = 3f;
        }};
        maxBlastPlastanium = new BasicBulletType(16, 116) {{
            lifetime = 22;
            splashDamage = 63;
            splashDamageRadius = 34;
            fragBullets = 9;
            fragBullet = maxBlastPlastaniumFrag;
            hitSound = Sounds.explosion;
            width = 16f;
            height = 20f;
            frontColor = Pal.plastaniumFront;
            backColor = Pal.plastaniumBack;
            collidesAir = false;
        }};
        maxBlastBlast = new BasicBulletType(16, 80) {{
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
        maxBlastSurge = new BasicBulletType(16, 123) {{
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
        //endregion blast
        //region poison bullets
        sporePodPoisonBullet = new FlakBulletType(4f, 5) {{
            sprite = "sunset-circle-bullet";
            knockback = 0.2f;
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
        //endregion poison bullets
        //region heavy-poison bullets
        sporePodPoisonFrag = new FlakBulletType(2f, 3) {{
            sprite = "sunset-circle-bullet";
            lifetime = 150f;
            splashDamage = 7f;
            splashDamageRadius = 14f;
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
        }};
        heavySporePodPoison = new FlakBulletType(3f, 11) {{
            sprite = "sunset-circle-bullet";
            knockback = 0.5f;
            lifetime = 250f;
            width = 13;
            height = 13f;
            hitEffect = Fx.flakExplosion;
            splashDamage = 16f;
            splashDamageRadius = 31f;
            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.02f;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = sporePodPoisonFrag;
            fragBullets = 3;

            weaveScale = 9f;
            weaveMag = 1f;
        }};
        naturitePoisonFrag = new FlakBulletType(2f, 5) {{
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

            fragBullet = naturitePoisonFrag;
            fragBullets = 2;

            weaveScale = 9f;
            weaveMag = 1f;
        }};
        nobiumPoisonFrag = new FlakBulletType(2f, 6) {{
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

            fragBullet = nobiumPoisonFrag;
            fragBullets = 4;

            weaveScale = 9f;
            weaveMag = 1f;

            homingRange = 15f;
            homingPower = 0.1f;
        }};
        //endregion heavy-poison bullets
        //region big-poison bullets
        bigSporePodPoison = new FlakBulletType(3f, 16) {{
            sprite = "sunset-circle-bullet";
            knockback = 0.5f;
            lifetime = 200f;
            width = 10f;
            height = 10f;
            hitEffect = Fx.flakExplosion;
            splashDamage = 19f;
            splashDamageRadius = 37f;
            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.015f;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = burheynaFrag;
            fragCone = 360;
            fragBullets = 2;

            trailColor = SnPal.poisonBullet;
            trailWidth = 5f;
            trailLength = 31;

            weaveScale = 9f;
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
            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.015f;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = burheynaFrag;
            fragCone = 360f;
            fragBullets = 12;

            trailColor = SnPal.poisonBullet;
            trailWidth = 5f;
            trailLength = 31;

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
            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.015f;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = burheynaFrag;
            fragCone = 360f;
            fragBullets = 12;

            trailColor = SnPal.poisonBullet;
            trailWidth = 5f;
            trailLength = 31;

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
            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.015f;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = burheynaFrag;
            fragCone = 360f;
            fragBullets = 4;

            trailColor = SnPal.poisonBullet;
            trailWidth = 5f;
            trailLength = 31;

            weaveScale = 9f;
            weaveMag = 1f;

            homingRange = 15f;
            homingPower = 0.1f;
        }};

        bigSporePodPoisonFrag = new FlakBulletType(2f, 8) {{
            sprite = "sunset-circle-bullet";
            lifetime = 250f;
            splashDamage = 10f;
            splashDamageRadius = 19f;
            height = 6f;
            width = 6f;
            hitEffect = Fx.flakExplosion;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;

            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.03f;

            trailColor = SnPal.poisonBullet;
            trailWidth = 2f;
            trailLength = 10;

            weaveScale = 9f;
            weaveMag = 1f;
        }};
        bigNaturitePoisonFrag = new FlakBulletType(2f, 13) {{
            sprite = "sunset-circle-bullet";
            lifetime = 250f;
            splashDamage = 15f;
            splashDamageRadius = 14f;
            height = 6f;
            width = 6f;
            hitEffect = Fx.flakExplosion;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;

            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.03f;

            trailColor = SnPal.poisonBullet;
            trailWidth = 2f;
            trailLength = 10;

            weaveScale = 9f;
            weaveMag = 1f;
        }};
        bigNobiumPoisonFrag = new FlakBulletType(2f, 11) {{
            sprite = "sunset-circle-bullet";
            lifetime = 150f;
            splashDamage = 14f;
            splashDamageRadius = 16f;
            height = 4f;
            width = 4f;
            hitEffect = Fx.flakExplosion;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;

            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.03f;

            trailColor = SnPal.poisonBullet;
            trailWidth = 2f;
            trailLength = 10;

            weaveScale = 9f;
            weaveMag = 1f;
        }};
        bigPlastaniumPoisonFrag = new FlakBulletType(2f, 14) {{
            sprite = "sunset-circle-bullet";
            lifetime = 150f;
            splashDamage = 17f;
            splashDamageRadius = 25f;
            height = 4f;
            width = 4f;
            hitEffect = Fx.flakExplosion;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;

            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.3f;

            trailColor = SnPal.poisonBullet;
            trailWidth = 2f;
            trailLength = 10;

            weaveScale = 9f;
            weaveMag = 1f;
        }};
        //endregion big-poison bullets
        //region energy-sphere
        smallEnergySphere = new BasicBulletType(3f, 15) {{
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

        mediumEnergySphereFrag = new LaserBulletType(10) {{
            colors = new Color[]{SnPal.redBomb.cpy().a(0.4f), SnPal.redBomb, Color.white};
            width = 20f;
            lifetime = 15f;
            length = 20f;
            laserEffect = Fx.lancerLaserShootSmoke;
            collidesAir = true;
            collidesGround = true;
        }};

        mediumEnergySphere = new BasicBulletType(3f, 30) {{
            sprite = "sunset-circle-bullet";
            shrinkX = 0f;
            shrinkY = 0f;
            lifetime = 45f;
            height = 8f;
            width = 8f;
            hitEffect = SnFx.mediumEnergySphereHit;
            frontColor = SnPal.redBomb;
            backColor = SnPal.redBombBack;

            trailColor = SnPal.redBombBack;
            trailWidth = 2f;
            trailLength = 4;
            trailEffect = Fx.artilleryTrail;

            fragBullet = mediumEnergySphereFrag;
            fragBullets = 1;
            fragCone = 0.0001f;

        }};
        //endregion energy-sphere
        //region helicopter
        //T1-copter
        basicHelicopterGun = new BasicBulletType(4.7f, 10) {{
            width = 8f;
            height = 11f;
            lifetime = 35f;
            splashDamageRadius = 3f;
            splashDamage = 1f;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
        }};
        //T2-copter
        mediumHelicopterGun = new BasicBulletType(6f, 13) {{
            width = 7f;
            height = 11f;
            lifetime = 35f;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
        }};
        helicopterMissile = new MissileBulletType(4f, 6) {{
            width = 7f;
            height = 10f;
            shrinkY = 0f;
            drag = -0.003f;
            homingRange = 15f;
            homingPower = 0.1f;

            splashDamageRadius = 25f;
            splashDamage = 15f;

            keepVelocity = false;
            hitSound = Sounds.explosion;
            trailChance = 0.2f;
            lifetime = 40f;
            backColor = Pal.unitBack;
            frontColor = Pal.unitFront;
            hitEffect = Fx.blastExplosion;
            despawnEffect = Fx.blastExplosion;
            weaveScale = 4f;
            weaveMag = 2f;
        }};
        //T3-copter
        clusterRocketSmall = new MissileBulletType(4.2f, 5) {{
            width = 6f;
            height = 9f;
            shrinkY = 0f;
            drag = -0.003f;
            homingRange = 9f;
            homingPower = 0.09f;

            splashDamageRadius = 20f;
            splashDamage = 20f;

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
        clusterRocket = new MissileBulletType(4.7f, 7) {{
            width = 10f;
            height = 14f;
            shrinkY = 0f;
            drag = -0.003f;
            homingRange = 10f;
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
            
            fragBullet = clusterRocketSmall;
            fragCone = 90f;
            fragBullets = 5;
        }};
        copterEnergySphere = new BasicBulletType(3.3f, 7) {{
            sprite = "sunset-circle-bullet";
            lifetime = 40f;
            splashDamageRadius = 17f;
            splashDamage = 25f;
            height = 9f;
            width = 9f;
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
        //T4-copter
        bigHelicopterGunFrag = new BasicBulletType(3f, 10, "bullet"){{
            width = 6f;
            height = 15f;
            shrinkY = 1f;
            lifetime = 20f;
            backColor = Pal.gray;
            frontColor = Color.white;
            despawnEffect = Fx.none;
        }};
        bigHelicopterGun = new BasicBulletType(5f, 40) {{
            width = 10f;
            height = 15f;
            lifetime = 44f;
            hitEffect = Fx.flakExplosion;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
            fragBullet = bigHelicopterGunFrag;
            fragBullets = 5;
        }};
        bigHelicopterMissile = new MissileBulletType(4.7f, 9) {{
            width = 9f;
            height = 14f;
            shrinkY = 0f;
            drag = -0.003f;
            homingRange = 20f;
            homingPower = 0.3f;

            splashDamageRadius = 38f;
            splashDamage = 45f;

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
        laserGun = new LaserBulletType() {{
            colors = new Color[]{SnPal.copterLaser.cpy().a(0.4f), SnPal.copterLaser, Color.white};
            damage = 55;
            width = 25f;
            lifetime = 15f;
            length = 125f;
            laserEffect = Fx.lancerLaserShootSmoke;
            collidesAir = true;
            collidesGround = true;
        }};
        //T5-copter
        laserHelicopterFrag = new LaserBulletType(40) {{
            colors = new Color[]{SnPal.copterLaser.cpy().a(0.4f), SnPal.copterLaser, Color.white};
            width = 20f;
            lifetime = 15f;
            length = 50f;
            laserEffect = Fx.lancerLaserShootSmoke;
            collidesAir = true;
            collidesGround = true;
        }};
        largeHelicopterGun = new BasicBulletType(6f, 50) {{
            width = 17f;
            height = 27f;
            lifetime = 38f;
            hitEffect = Fx.hitBulletBig;
            shootEffect = Fx.shootBig2;
            smokeEffect = Fx.shootBigSmoke;
            fragBullet = laserHelicopterFrag;
            fragBullets = 1;
            fragCone = 0.0001f;
        }};
        largeHelicopterMissile = new CopterRocketBulletType(6.3f, 13, "missile-large") {{
            width = 14f;
            height = 17f;
            hitShake = 3f;
            lifetime = 40f;
            hitEffect = Fx.massiveExplosion;
            keepVelocity = false;
            hitSound = Sounds.explosion;
            shrinkY = 0f;
            drag = -0.003f;
            homingRange = 20f;
            homingPower = 0.2f;
            splashDamage = 70f;
            splashDamageRadius = 60f;
            backColor = Pal.missileYellowBack;
		    frontColor = Pal.missileYellow;
            trailLength = 0;
            trailWidth = 5f;
            trailColor = Pal.bulletYellow;
            trailInterval = 0.5f;
            trailEffect = SnFx.bigRocketTrail;
        }};
        smallHelicopterMissiles = new MissileBulletType(4.4f, 10) {{
            width = 11f;
            height = 11f;
            shrinkY = 0f;
            lifetime = 60f;
            splashDamageRadius = 30f;
            splashDamage = 35f;
            hitEffect = Fx.blastExplosion;
            despawnEffect = Fx.blastExplosion;
            homingRange = 30;
            homingPower = 0.4f;
            weaveScale = 4f;
            weaveMag = 2f;
            pierce = true;
            pierceBuilding = true;
            pierceCap = 3;
        }};
        //T6-copter
        shrapnelCopterGun = new ShrapnelBulletType() {{
            length = 140f;
            width = 30f;
            damage = 110;
            fromColor = SnPal.copterLaser;
            toColor = SnPal.copterLaserBack;
            collidesAir = true;
            collidesGround = true;
        }};

        gigantHelicopterGun = new BasicBulletType(5f, 65) {{
            width = 12f;
            height = 17f;
            lifetime = 50f;
            hitEffect = Fx.flakExplosion;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
        }};

        bigClusterRocketSmall = new MissileBulletType(4f, 8) {{
            width = 13f;
            height = 13f;
            shrinkY = 0f;
            lifetime = 55f;
            splashDamageRadius = 40f;
            splashDamage = 55f;
            hitEffect = Fx.blastExplosion;
            despawnEffect = Fx.blastExplosion;
            homingRange = 30;
            homingPower = 0.5f;
            weaveScale = 4f;
            weaveMag = 2f;
            pierce = true;
            pierceBuilding = true;
            pierceCap = 5;
        }};
        bigClusterRocket = new MissileBulletType(4.3f, 10, "missile-large") {{
            width = 16f;
            height = 18f;
            shrinkY = 0f;
            drag = -0.003f;
            homingRange = 10f;
            homingPower = 0.2f;
            splashDamageRadius = 70f;
            splashDamage = 85f;
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
            
            fragBullet = bigClusterRocketSmall;
            fragCone = 90f;
            fragBullets = 3;
        }};

        bigCopterEnergySphere = new BasicBulletType(3f, 7) {{
            sprite = "sunset-circle-bullet";
            lifetime = 37f;
            splashDamageRadius = 80f;
            splashDamage = 70f;
            height = 14f;
            width = 14f;
            lightning = 10;
            lightningLength = 12;
            lightningColor = SnPal.copterLaser;
            lightningDamage = 30f;
            shrinkX = 0.01f;
            shrinkY = 0.01f;
            hitEffect = Fx.flakExplosion;
            frontColor = SnPal.copterLaser;
            backColor = SnPal.copterLaserBack;
        }};
        //endregion helicopter
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
        galaxyKnockbackBullet = new BasicBulletType(12, 79) {{
            lifetime = 36;
            knockback = 384;
            status = SnStatusEffects.stun;
            statusDuration = 30;
            height = 30;
            width = 6;
        }};
        //endregion buffer
        //region berserk
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
        //endregion berserk
        //region wheel
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
        wheel4Artillery = new ArtilleryBulletType(3f, 67f) {{
            lifetime = 114f;
            status = StatusEffects.blasted;
            splashDamage = 79f;
            splashDamageRadius = 29f;
            width = 8f;
            height = 12f;
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
        wheel5Bullet = new BasicBulletType(8f, 62) {{
            width = 8f;
            height = 14f;
            shootEffect = Fx.shootBig;
            pierceCap = 4;
            pierceBuilding = true;
        }};
        //endregion wheel
        //region special
        empBullet = new LightningBulletType() {{
            //speed = 5;
            damage = 150;
            lifetime = 50;
            shootEffect = SnFx.empShootSmall;
            status = SnStatusEffects.electricalShort;
            drawSize = 3;
            collidesTeam = true;
            hitSize = 36;
            hitEffect = SnFx.empHit;
        }
            @Override
            public void draw(Bullet b) {

            }
        };
        empBulletEvo = new LightningBulletType() {{
            //speed = 5;
            damage = 300;
            lifetime = 100;
            shootEffect = SnFx.empShootBig;
            status = SnStatusEffects.electricalShort;
            drawSize = 5.3f;
            collidesTeam = true;
            hitSize = 36;
            hitEffect = SnFx.empHit;
        }
            @Override
            public void draw(Bullet b) {

            }
        };
        //endregion EMP
       //region synthesis
        synthesisBullet1 = new EnergyBoltBulletType(11, 70) {{
            status = SnStatusEffects.greened;
            lifetime = 7;
            inaccuracy = 7;
        }};
        synthesisBullet2 = new EnergyBoltBulletType(14, 245) {{
            status = SnStatusEffects.greened;
            lifetime = 6;
            inaccuracy = 11;
            fragBullets = 3;
            fragCone = 35;
            fragBullet = new LaserBoltBulletType(12, 70) {{
                lifetime = 30;
                backColor = Pal.heal;
                frontColor = Color.white;
            }};
        }};
        synthesisBullet3 = new EnergyBoltBulletType(12, 350) {{
            status = SnStatusEffects.greened;
            lifetime = 12;
            inaccuracy = 21;
            fragBullets = 1;
            fragBullet = new BulletType() {{
                damage = 0;
                knockback = 15;
                despawnEffect = Fx.none;
                hitEffect = Fx.none;
            }};
        }};
        synthesisBullet4 = new EnergyBoltBulletType(17, 700) {{
            status = SnStatusEffects.greened;
            lifetime = 16;
            inaccuracy = 2;
            splashDamage = 110;
            splashDamageRadius = 80;
            trailChance = 1;
            trailEffect = Fx.none;
            //trailEffect = SnFx.greenInstTrail;
        }};
        //endregion synthesis
        //region laser art
        laserArtThorium = new ArtilleryBulletType(5, 200, "shell") {{//TODO: shell sprite
            knockback = 3.4f;
            lifetime = 77;
            width = height = 12;
            splashDamageRadius = 80;
            splashDamage = damage * 2.75f;
            fragBullets = 6;
            fragCone = 360;
            fragBullet = new ContinuousLaserBulletType(8.4f) {{
                length = 35;
            }};
            despawnEffect = hitEffect = SnFx.laserArtHit;

            trailColor = Items.thorium.color;
            trailWidth = 4;
            trailLength = 7;
            trailEffect = Fx.artilleryTrail;
        }};
        laserArtPhase = new ArtilleryBulletType(5, 700, "shell") {{//TODO: shell sprite
            knockback = 0.2f;
            lifetime = 77;
            width = height = 12;
            splashDamageRadius = 24;
            splashDamage = damage * 0.15f;
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
        }};
        laserArtEnojie = new ArtilleryBulletType(5, 450, "shell") {{//TODO: shell sprite
            knockback = 1;
            lifetime = 77;
            width = height = 12;
            splashDamageRadius = 50;
            splashDamage = damage * 0.25f;
            fragBullets = 6;
            fragCone = 360;
            fragBullet = new ContinuousLaserBulletType(50) {{
                length = 60;
            }};
            despawnEffect = hitEffect = SnFx.laserArtHit;

            trailColor = SnItems.enojie.color;
            trailWidth = 4;
            trailLength = 7;
            trailEffect = Fx.artilleryTrail;
        }};
        laserArtReneubite = new ArtilleryBulletType(5, 100, "shell") {{
            knockback = 2;
            lifetime = 77;
            width = height = 12;
            splashDamageRadius = 50;
            splashDamage = damage * 1.5f;
            fragBullets = 6;
            fragCone = 360;
            fragBullet = new ContinuousLaserBulletType(51.7f) {{
                length = 40;
            }};
            despawnEffect = hitEffect = SnFx.laserArtHit;

            trailColor = SnItems.reneubite.color;
            trailWidth = 4;
            trailLength = 7;
            trailEffect = Fx.artilleryTrail;
        }};
        //endregion laser art
        //endregion special
        //region misc
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
                    shootEffect.at(b.x, b.y, b.rotation(), lightningColor, new Vec2().set(u.x, u.y));
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
        testBullet = new BasicBulletType(1, 1) {{
            lifetime = 400f;
            hitEffect = SnFx.unused1;
            drag = -0.003f;
            homingRange = 10f;
            homingPower = 0.2f;
            ammoMultiplier = 2;
        }};
        tempBullet1 = new LaserBoltBulletType(5, 15);
        tempBullet2 = new LaserBoltBulletType(5, 30);
        testBullet0 = new ArtilleryBulletType(5, 100) {{
            fragBullets = 6;
            fragBullet = new LaserBulletType(50);
            fragCone = 360;
        }};
        testBullet1 = new BasicBulletType(5, 50) {{
            fragBullets = 3;
            fragCone = 45;
            fragBullet = new BasicBulletType(5, 50) {{
                fragBullets = 3;
                fragCone = 45;
                fragBullet = new BasicBulletType(5, 50) {{
                    fragBullets = 3;
                    fragCone = 45;
                    fragBullet = new BasicBulletType(5, 50) {{
                        fragBullets = 3;
                        fragCone = 45;
                        fragBullet = new BasicBulletType(5, 50) {{
                            fragBullets = 3;
                            fragCone = 45;
                            fragBullet = new BasicBulletType(5, 50) {{
                                fragBullets = 3;
                                fragCone = 45;
                                fragBullet = new BasicBulletType(5, 50) {{
                                    fragBullets = 3;
                                    fragCone = 45;
                                    fragBullet = new BasicBulletType(5, 50) {{
                                        fragBullets = 3;
                                        fragCone = 45;
                                        fragBullet = new BasicBulletType(5, 50) {{
                                            fragBullets = 3;
                                            fragCone = 45;
                                            fragBullet = new BasicBulletType(5, 50) {{
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
        testBullet2 = new BasicBulletType(5, 50) {{
            fragBullets = 6;
            fragCone = 360;
            fragBullet = new ContinuousLaserBulletType(150);
        }};
        //endregion misc
    }
}
