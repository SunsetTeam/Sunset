package sunset.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.Angles;
import arc.math.geom.Vec2;
import mindustry.ctype.*;
import mindustry.content.*;
import mindustry.entities.Units;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import sunset.entities.bullet.*;
import sunset.graphics.*;
import sunset.type.StackableStatusEffect;

public class SnBullets implements ContentList {
    public static BulletType
        //sap
        leadSap, sporeSap, planatriumSap,
        //heavy-sap
        heavyplanatriumSap,
        //artillery
        artilleryForsMine, artilleryFors, artilleryBlastMine, artilleryBlast,
        //heavy-standart
        heavyCopper, heavyDense, heavyThorium, heavyTitanium, standartBlast, heavyIncendiary,
        heavystandardDense, heavystandardThorium, heavystandardIncendiary, standartBlastBig, standardForsBig,
        //sniper
        somesnipersurge,
        //burner
        heavyCoalFlame, heavyPyraFlame, flameidFlame,
        //liquid
        typhoonWaterShot, typhoonCryoShot, typhoonSlagShot, typhoonOilShot,
        floodWaterShot, floodCryoShot, floodSlagShot, floodOilShot,
        //blast
        lightBlastGraphite, lightBlastSilicon,
        bigBlastPlastanium, bigBlastBlast, bigBlastPyratite,
        maxBlastPlastanium, maxBlastBlast, maxBlastSurge, maxBlastPlastaniumFrag,
        //poison
        sporePodPoisonBullet, naturitePoisonBullet,
        SporePodPoisonFrag, heavySporePodPoison, NaturitePoisonFrag, heavyNaturitePoison, NobiumPoisonFrag, heavyNobiumPoison,
        bigSporePodPoisonFrag, bigSporePodPoison, bigNaturitePoisonFrag, bigNaturitePoison, bigNobiumPoisonFrag, bigNobiumPoison, bigPlastaniumPoisonFrag, bigPlastaniumPoison,
        //units
        BasicHelicopterGun, HelicopterShootgun, HelicopterMissile, HelicopterBomb, HelicopterRocket, bigHelicopterGun, laserHelicopterFrag, largeHelicopterGun, bigHelicopterRocket, HelicopterMissiles,
        cometWaterShot, starStunBullet, galaxyKnockbackBullet,
        wheel1bullet, wheel2shotgun, wheel3burst, wheel4shotgun, wheel4artillery, wheel5flame, wheel5bullet,
        //misc
        emptyBullet, overheatBullet,
        //special
        empBullet, empBulletEvo,
        //test&temp
        testbullet, tempBullet1, tempBullet2;
        //exoticBullets (new) i will make it later... i must make more bulets (soulBullet, iceSpike, and more)
        // spiralPllastanium, spiralSurge, SpiralFors, SpiralThorium, SpiralSmall;

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
        //region heavy-sap
        heavyplanatriumSap = new SapBulletType() {{
            sapStrength = 0.95f;
            length = 240f;
            damage = 60f;
            shootEffect = Fx.shootSmall;
            despawnEffect = Fx.none;
            width = 1.8f;
            hitColor = color = Color.valueOf("bf92f9");
            lifetime = 30f;
        }};
        //endregion heavy-sap
        //region artillery
        artilleryForsMine = new FlakBulletType(3f, 20) {{
            collidesGround = true;
            collidesAir = false;
            splashDamage = 120f;
            splashDamageRadius = 100f;
            sprite = "sunset-red-mine";
            status = StatusEffects.melting;
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
        artilleryFors = new ArtilleryBulletType(3.0f, 70, "shell") {{
            hitEffect = SnFx.redBomb;
            knockback = 1f;
            width = height = 19f;
            lifetime = 110f;
            width = height = 25f;
            collidesTiles = false;
            collidesAir = true;
            splashDamageRadius = 60f * 0.75f;
            splashDamage = 120f;
            fragBullet = artilleryForsMine;
            fragBullets = 2;
            backColor = SnPal.redBomb;
            frontColor = SnPal.redBombBack;
        }};
        artilleryBlastMine = new FlakBulletType(2.9f, 25) {{
            sprite = "sunset-red-mine";
            collidesGround = true;
            collidesAir = false;
            splashDamage = 100f;
            splashDamageRadius = 120f;
            status = StatusEffects.melting;
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
        artilleryBlast = new ArtilleryBulletType(3.0f, 75, "shell") {{
            hitEffect = SnFx.redBomb;
            knockback = 1f;
            lifetime = 110f;
            width = height = 25f;
            collidesTiles = false;
            collidesAir = true;
            splashDamageRadius = 68f * 0.75f;
            splashDamage = 90f;
            fragBullet = artilleryBlastMine;
            fragBullets = 3;
            makeFire = true;
            backColor = SnPal.BlastBullet;
            frontColor = SnPal.BlastBulletBack;
        }};
        //endregion artillery
        //region heavy-standart
        heavyCopper = new BasicBulletType(2.7f, 14) {{
            width = 9f;
            height = 11f;
            lifetime = 67f;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
            ammoMultiplier = 2;
        }};
        heavyDense = new BasicBulletType(3.9f, 21) {{
            width = 11f;
            height = 14f;
            reloadMultiplier = 0.6f;
            ammoMultiplier = 3;
            lifetime = 67f;
        }};
        heavyThorium = new BasicBulletType(4.3f, 34, "bullet") {{
            width = 12f;
            height = 15f;
            shootEffect = Fx.shootBig;
            smokeEffect = Fx.shootBigSmoke;
            ammoMultiplier = 3;
            lifetime = 67f;
        }};
        heavyTitanium = new BasicBulletType(3.4f, 16, "bullet") {{
            width = 9f;
            height = 11f;
            reloadMultiplier = 1.5f;
            ammoMultiplier = 5;
            lifetime = 67f;
        }};
        standartBlast = new BasicBulletType(3.5f, 15, "bullet") {{
            width = 12f;
            height = 14f;
            frontColor = Pal.lightishOrange;
            backColor = Pal.lightOrange;
            status = StatusEffects.burning;
            makeFire = true;
            inaccuracy = 3f;
            lifetime = 67f;
        }};
        heavyIncendiary = new BasicBulletType(3.5f, 15, "bullet") {{
            width = 12f;
            height = 14f;
            frontColor = Pal.lightishOrange;
            backColor = Pal.lightOrange;
            status = StatusEffects.burning;
            makeFire = true;
            inaccuracy = 3f;
            lifetime = 67f;
        }};
        //endregion heavy-standart
        //region somesniper
        somesnipersurge = new SniperBulletType(){{
            trailEffect = SnFx.somesnipertrail;
            frontColor = Pal.surge;
            trailDelay = 0.2f;
            trailSpace = 32f;
            range = 768f;
            damage = 3400f;
            pierceSizeMultiplier = 0.9f;
            buildingDamageMultiplier = 0.4f;
            pierceBuilding = pierce = true;
        }};
        //endregion somesniper
        //region big standart
        heavystandardDense = new BasicBulletType(8f, 100, "bullet") {{
            hitSize = 5;
            width = 19f;
            height = 24f;
            shootEffect = Fx.shootBig;
        }};
        heavystandardThorium = new BasicBulletType(9.4f, 170, "bullet") {{
            hitSize = 7;
            width = 20f;
            height = 27f;
            shootEffect = Fx.shootBig;
            pierceCap = 2;
            pierceBuilding = true;
            knockback = 0.7f;
        }};
        heavystandardIncendiary = new BasicBulletType(7f, 110, "bullet") {{
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
        standartBlastBig = new BasicBulletType(9f, 105, "bullet") {{
            hitSize = 5;
            width = 19f;
            height = 24f;
            frontColor = SnPal.BlastBullet;
            backColor = SnPal.BlastBulletBack;
            status = StatusEffects.burning;
            shootEffect = Fx.shootBig;
            makeFire = true;
            pierceCap = 2;
            pierceBuilding = true;
            knockback = 0.7f;
        }};
        standardForsBig = new BasicBulletType(7.8f, 150, "bullet") {{
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
        //endregion big standart
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
        typhoonWaterShot = new LiquidBulletType(Liquids.water) {{
            lifetime = 70f;
            speed = 4f;
            knockback = 2.5f;
            puddleSize = 11f;
            orbSize = 6f;
            drag = 0.001f;
            ammoMultiplier = 0.4f;
            statusDuration = 60f * 4f;
            damage = 0.3f;
        }};
        typhoonCryoShot = new LiquidBulletType(Liquids.cryofluid) {{
            lifetime = 70f;
            speed = 4f;
            knockback = 1.8f;
            puddleSize = 11f;
            orbSize = 6f;
            drag = 0.001f;
            ammoMultiplier = 0.4f;
            statusDuration = 60f * 4f;
            damage = 0.4f;
        }};
        typhoonSlagShot = new LiquidBulletType(Liquids.slag) {{
            lifetime = 49f;
            speed = 4f;
            knockback = 1.8f;
            puddleSize = 8f;
            orbSize = 4f;
            damage = 4.95f;
            drag = 0.001f;
            ammoMultiplier = 0.3f;
            statusDuration = 60f * 4f;
        }};
        typhoonOilShot = new LiquidBulletType(Liquids.oil) {{
            lifetime = 70f;
            speed = 4f;
            knockback = 1.8f;
            puddleSize = 11f;
            orbSize = 6f;
            drag = 0.001f;
            ammoMultiplier = 0.4f;
            statusDuration = 60f * 4f;
            damage = 0.3f;
        }};
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
        //endregion liquid
        //region blast
        lightBlastGraphite = new BasicBulletType(14, 19) {{
            lifetime = 11f;
            splashDamage = 39f;
            splashDamageRadius = 13f;
            width = 4f;
            height = 8f;
            frontColor = Color.valueOf("ccccdd");
            backColor = Color.valueOf("888888");
            collidesAir = true;
        }};
        lightBlastSilicon = new BasicBulletType(9, 34) {{
            lifetime = 19f;
            splashDamage = 16f;
            splashDamageRadius = 7.4f;
            homingPower = 0.033f;
            homingRange = 154f;
            reloadMultiplier = 1.1f;
            width = 4f;
            height = 8f;
            frontColor = Color.valueOf("ccccdd");
            backColor = Color.valueOf("888888");
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
        maxBlastSurge = new BasicBulletType(16, 115) {{
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
        //region posion bullets
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
            frontColor = SnPal.PoisonBullet;
            backColor = SnPal.PoisonBulletBack;
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
            frontColor = SnPal.PoisonBullet;
            backColor = SnPal.PoisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            weaveScale = 8f;
            weaveMag = 1f;
        }};
        //endregion posion bullets
        //region heavy-poison bullets
        SporePodPoisonFrag = new FlakBulletType(2f, 5) {{
            sprite = "sunset-circle-bullet";
            lifetime = 150f;
            splashDamage = 7f;
            splashDamageRadius = 14f;
            height = 6f;
            width = 6f;
            hitEffect = Fx.flakExplosion;
            frontColor = SnPal.PoisonBullet;
            backColor = SnPal.PoisonBulletBack;

            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.04f;

            weaveScale = 6f;
            weaveMag = 1f;
        }};
        heavySporePodPoison = new FlakBulletType(3f, 14) {{
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
            frontColor = SnPal.PoisonBullet;
            backColor = SnPal.PoisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = SporePodPoisonFrag;
            fragBullets = 3;

            weaveScale = 9f;
            weaveMag = 1f;
        }};
        NaturitePoisonFrag = new FlakBulletType(2f, 7) {{
            sprite = "sunset-circle-bullet";
            lifetime = 150f;
            splashDamage = 12f;
            splashDamageRadius = 9f;
            height = 6f;
            width = 6f;
            hitEffect = Fx.flakExplosion;
            frontColor = SnPal.PoisonBullet;
            backColor = SnPal.PoisonBulletBack;

            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.04f;

            weaveScale = 6f;
            weaveMag = 1f;
        }};
        heavyNaturitePoison = new FlakBulletType(3f, 16) {{
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
            frontColor = SnPal.PoisonBullet;
            backColor = SnPal.PoisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = NaturitePoisonFrag;
            fragBullets = 2;

            weaveScale = 9f;
            weaveMag = 1f;
        }};
        NobiumPoisonFrag = new FlakBulletType(2f, 6) {{
            sprite = "sunset-circle-bullet";
            lifetime = 150f;
            splashDamage = 10f;
            splashDamageRadius = 11f;
            height = 6f;
            width = 6f;
            hitEffect = Fx.flakExplosion;
            frontColor = SnPal.PoisonBullet;
            backColor = SnPal.PoisonBulletBack;

            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.04f;

            weaveScale = 6f;
            weaveMag = 1f;
        }};
        heavyNobiumPoison = new FlakBulletType(3f, 15) {{
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
            frontColor = SnPal.PoisonBullet;
            backColor = SnPal.PoisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = NobiumPoisonFrag;
            fragBullets = 4;

            weaveScale = 9f;
            weaveMag = 1f;
            homingRange = 15f;
            homingPower = 0.1f;
        }};
        //endregion heavy-poison bullets
        //region big-poison bullets
        bigSporePodPoisonFrag = new FlakBulletType(2f, 8) {{
            sprite = "sunset-circle-bullet";
            lifetime = 250f;
            splashDamage = 10f;
            splashDamageRadius = 19f;
            height = 8f;
            width = 8f;
            hitEffect = Fx.flakExplosion;
            frontColor = SnPal.PoisonBullet;
            backColor = SnPal.PoisonBulletBack;

            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.04f;

            weaveScale = 9f;
            weaveMag = 1f;
        }};
        bigSporePodPoison = new FlakBulletType(3f, 16) {{
            sprite = "sunset-circle-bullet";
            knockback = 0.5f;
            lifetime = 200f;
            width = 16f;
            height = 16f;
            hitEffect = Fx.flakExplosion;
            splashDamage = 19f;
            splashDamageRadius = 37f;
            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.015f;
            frontColor = SnPal.PoisonBullet;
            backColor = SnPal.PoisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = bigSporePodPoisonFrag;
            fragCone = 90f;
            fragBullets = 2;

            weaveScale = 9f;
            weaveMag = 1f;
        }};
        bigNaturitePoisonFrag = new FlakBulletType(2f, 13) {{
            sprite = "sunset-circle-bullet";
            lifetime = 250f;
            splashDamage = 15f;
            splashDamageRadius = 14f;
            height = 8f;
            width = 8f;
            hitEffect = Fx.flakExplosion;
            frontColor = SnPal.PoisonBullet;
            backColor = SnPal.PoisonBulletBack;

            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.04f;

            weaveScale = 9f;
            weaveMag = 1f;
        }};
        bigNaturitePoison = new FlakBulletType(3f, 21) {{
            sprite = "sunset-circle-bullet";
            knockback = 0.6f;
            lifetime = 200f;
            width = 16f;
            height = 16f;
            hitEffect = Fx.flakExplosion;
            splashDamage = 24f;
            splashDamageRadius = 30f;
            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.015f;
            frontColor = SnPal.PoisonBullet;
            backColor = SnPal.PoisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = bigNaturitePoisonFrag;
            fragCone = 90f;
            fragBullets = 3;

            weaveScale = 9f;
            weaveMag = 1f;
        }};
        bigNobiumPoisonFrag = new FlakBulletType(2f, 11) {{
            sprite = "sunset-circle-bullet";
            lifetime = 150f;
            splashDamage = 14f;
            splashDamageRadius = 16f;
            height = 6f;
            width = 6f;
            hitEffect = Fx.flakExplosion;
            frontColor = SnPal.PoisonBullet;
            backColor = SnPal.PoisonBulletBack;

            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.04f;

            weaveScale = 9f;
            weaveMag = 1f;
        }};
        bigNobiumPoison = new FlakBulletType(3f, 19) {{
            sprite = "sunset-circle-bullet";
            knockback = 0.4f;
            lifetime = 200f;
            width = 16f;
            height = 16f;
            hitEffect = Fx.flakExplosion;
            splashDamage = 22f;
            splashDamageRadius = 27f;
            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.015f;
            frontColor = SnPal.PoisonBullet;
            backColor = SnPal.PoisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = bigNobiumPoisonFrag;
            fragCone = 90f;
            fragBullets = 4;

            weaveScale = 9f;
            weaveMag = 1f;
            homingRange = 15f;
            homingPower = 0.1f;
        }};
        bigPlastaniumPoisonFrag = new FlakBulletType(2f, 14) {{
            sprite = "sunset-circle-bullet";
            lifetime = 150f;
            splashDamage = 17f;
            splashDamageRadius = 25f;
            height = 6f;
            width = 6f;
            hitEffect = Fx.flakExplosion;
            frontColor = SnPal.PoisonBullet;
            backColor = SnPal.PoisonBulletBack;

            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.04f;

            weaveScale = 9f;
            weaveMag = 1f;
        }};
        bigPlastaniumPoison = new FlakBulletType(3f, 22) {{
            sprite = "sunset-circle-bullet";
            knockback = 0.4f;
            lifetime = 200f;
            width = 16f;
            height = 16f;
            hitEffect = Fx.flakExplosion;
            splashDamage = 25f;
            splashDamageRadius = 34f;
            shrinkX = 0.3f;
            shrinkY = 0.3f;
            drag = 0.015f;
            frontColor = SnPal.PoisonBullet;
            backColor = SnPal.PoisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            fragBullet = bigNobiumPoisonFrag;
            fragCone = 90f;
            fragBullets = 4;

            weaveScale = 9f;
            weaveMag = 1f;
            homingRange = 15f;
            homingPower = 0.1f;
        }};
        //endregion big-poison bullets
        //region helicopter
        BasicHelicopterGun = new BasicBulletType(5.6f, 11) {{
            width = 8f;
            height = 11f;
            lifetime = 35f;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
        }};
        HelicopterShootgun = new BasicBulletType(4.7f, 9) {{
            width = 11f;
            height = 15f;
            lifetime = 45f;
            hitEffect = despawnEffect = Fx.none;
            instantDisappear = true;
            fragBullets = 5;
            keepVelocity = true;
            fragBullet = new BasicBulletType(4.7f, 9f) {{
                lifetime = 45f;
                keepVelocity = true;
            }};
            fragVelocityMin = 0.8f;
            fragVelocityMax = 1.5f;
            fragLifeMin = 0.6f;
            fragLifeMax = 1.5f;
            fragCone = 18f;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
        }};
        HelicopterMissile = new MissileBulletType(6f, 31) {{
            width = 14f;
            height = 17f;
            shrinkY = 0f;
            drag = -0.003f;
            homingRange = 30f;
            homingPower = 0.2f;
            keepVelocity = false;
            hitSound = Sounds.explosion;
            trailChance = 0.3f;
            lifetime = 47f;
            backColor = Pal.unitBack;
            frontColor = Pal.unitFront;
            hitEffect = Fx.blastExplosion;
            despawnEffect = Fx.blastExplosion;
            weaveScale = 5f;
            weaveMag = 2f;
        }};
        HelicopterBomb = new BasicBulletType(2f, 5) {{
            sprite = "sunset-copter-bomb";
            width = height = 70 / 2f;

            maxRange = 30f;

            backColor = SnPal.copterBomb;
            frontColor = Color.white;
            mixColorTo = Color.white;

            hitSound = Sounds.plasmaboom;
            hitShake = 2f;

            collidesAir = false;

            lifetime = 40f;

            despawnEffect = SnFx.CopterBomb;
            hitEffect = Fx.massiveExplosion;
            keepVelocity = true;
            spin = 2f;

            shrinkX = shrinkY = 0.55f;

            collides = true;

            splashDamage = 140f;
            splashDamageRadius = 45f;
        }};
        bigHelicopterGun = new BasicBulletType(9f, 53) {{
            width = 17f;
            height = 24f;
            lifetime = 28f;
            shootEffect = Fx.shootBig;
            smokeEffect = Fx.shootBigSmoke;

            lightning = 4;
            lightningLength = 7;
            lightningColor = Pal.surge;
            lightningDamage = 18;

        }};
        HelicopterRocket = new CopterRocketBulletType(5.0f, 50) {{
            sprite = "sunset-guardian-rocket";
            width = 13f;
            height = 20f;
            hitShake = 2f;
            lifetime = 38f;
            despawnEffect = SnFx.heavyCopterBomb;
            hitEffect = Fx.massiveExplosion;
            keepVelocity = false;
            hitSound = Sounds.explosion;
            trailChance = 0.3f;
            shrinkY = 0f;
            drag = -0.003f;
            homingRange = 30f;
            homingPower = 0.2f;
            splashDamage = 350f;
            splashDamageRadius = 50f;
        }};
        laserHelicopterFrag = new LaserBulletType(50) {{
            colors = new Color[]{SnPal.copterLaser.cpy().a(0.4f), SnPal.copterLaser, Color.white};
            width = 20f;
            lifetime = 15f;
            length = 50f;
            laserEffect = Fx.lancerLaserShootSmoke;
            collidesAir = true;
            collidesGround = true;
        }};
        largeHelicopterGun = new BasicBulletType(10f, 80) {{
            width = 17f;
            height = 27f;
            lifetime = 27f;
            hitEffect = Fx.hitBulletBig;
            shootEffect = Fx.shootBig2;
            smokeEffect = Fx.shootBigSmoke;
            fragBullet = laserHelicopterFrag;
            fragBullets = 1;
            fragCone = 0.0001f;
        }};
        bigHelicopterRocket = new CopterRocketBulletType(6.3f, 70) {{
            sprite = "sunset-guardian-rocket";
            width = 13f;
            height = 23f;
            hitShake = 3f;
            lifetime = 34f;
            despawnEffect = SnFx.bigCopterBomb;
            hitEffect = Fx.massiveExplosion;
            keepVelocity = false;
            hitSound = Sounds.explosion;
            trailChance = 0.5f;
            shrinkY = 0f;
            drag = -0.003f;
            homingRange = 20f;
            homingPower = 0.4f;
            splashDamage = 350f;
            splashDamageRadius = 78f;
        }};
        HelicopterMissiles = new MissileBulletType(4.4f, 45) {{
            width = 11f;
            height = 11f;
            shrinkY = 0f;
            lifetime = 58f;
            splashDamageRadius = 35f;
            splashDamage = 39f * 1.5f;
            hitEffect = Fx.blastExplosion;
            despawnEffect = Fx.blastExplosion;
            weaveScale = 4f;
            weaveMag = 2f;
        }};
        //endregion copter
        //region air-support
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
        //endregion air-support
        //region wheel
        wheel1bullet = new BasicBulletType(6.5f, 5f) {{
            width = 7f;
            height = 9f;
            lifetime = 25f;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
        }};
        wheel2shotgun = new BasicBulletType(11f, 7f) {{
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
        wheel3burst = new BasicBulletType(11f, 43f){{
            lifetime = 24f;
            status = StatusEffects.blasted;
            splashDamage = 37f;
            splashDamageRadius = 17f;
            width = 8f;
            height = 12f;
        }};
        wheel4shotgun = new BasicBulletType(12f, 32f) {{
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
        wheel4artillery = new ArtilleryBulletType(3f, 67f){{
            lifetime = 114f;
            status = StatusEffects.blasted;
            splashDamage = 79f;
            splashDamageRadius = 29f;
            width = 8f;
            height = 12f;
        }};
        wheel5flame = new BulletType(20f, 120f){{
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
        wheel5bullet = new BasicBulletType(8f, 62){{
            width = 8f;
            height = 14f;
            shootEffect = Fx.shootBig;
            pierceCap = 4;
            pierceBuilding = true;
        }};
        //endregion wheel
        //region special
        empBullet = new LightningBulletType(){{
            //speed = 5;
            damage = 150;
            lifetime = 50;
            shootEffect = SnFx.empShootSmall;
            status = SnStatusEffects.electricalShort;
            drawSize = 3;
            collidesTeam = true;
            hitSize = 36;
            hitEffect = SnFx.empHit;
        }};
        empBulletEvo = new LightningBulletType(){{
            //speed = 5;
            damage = 300;
            lifetime = 100;
            shootEffect = SnFx.empShootBig;
            status = SnStatusEffects.electricalShort;
            drawSize = 5.3f;
            collidesTeam = true;
            hitSize = 36;
            hitEffect = SnFx.empHit;
        }};
        //endregion special
        //region misc
        overheatBullet = new BasicBulletType(0.1f, 7, "error") {{
            shootEffect = Fx.none;
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
        testbullet = new TorpedoBulletType(1, 1){{
            lifetime = 400f;
            trailEffect = SnFx.torpedoTrail;
            drag = -0.003f;
            homingRange = 10f;
            homingPower = 0.2f;
            ammoMultiplier = 2;
        }};
        tempBullet1 = new LaserBoltBulletType(5, 15){{
        }};
        tempBullet2 = new LaserBoltBulletType(5, 30){{
        }};
        //endregion misc
    }
}
