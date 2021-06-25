package sunset.content;

import arc.graphics.*;
import mindustry.ctype.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import sunset.entities.bullet.ArtilleryLiquidBulletType;
import sunset.graphics.*;


public class SnBullets implements ContentList{
    public static BulletType 

//sap
    leadSap, sporeSap, planatriumSap,
//artillery
    artilleryForsFrag, artilleryFors, artilleryBlastFrag, artilleryBlast,
//heavy-standart
    heavyCopper, heavyDense, heavyThorium, heavyTitanium, standartBlast, heavyIncendiary, 
    heavystandardDense, heavystandardThorium, heavystandardIncendiary, standartBlastBig, standardForsBig,
//burner 
    heavyCoalFlame, heavyPyraFlame, blastFlame, flameidFlame,
//liquid
    typhoonWaterShot, typhoonCryoShot, typhoonSlagShot, typhoonOilShot,
    floodWaterShot, floodCryoShot, floodSlagShot, floodOilShot,
//blast
    lightBlastGraphite, lightBlastSilicon, mediumBlastGraphite, mediumBlastSilicon, mediumBlastBlast,
    bigBlastPlastanium, bigBlastBlast, bigBlastPyratite, maxBlastPlastanium, maxBlastBlast, maxBlastSurge,
    maxBlastPlastaniumFrag,
//units
    BasicHelicopterGun, HelicopterShootgun, cometWaterShot;
//exoticBullets (new) i will make it later... i must make more bulets (soulBullet, iceSpike, and more)
// spiralPllastanium, spiralSurge, SpiralFors, SpiralThorium, SpiralSmall;

    @Override
    public void load(){

//sap
        leadSap = new SapBulletType(){{
        sapStrength = 0.30f;
        length = 137f;
        damage = 7f;
        shootEffect = Fx.shootSmall;
        despawnEffect = Fx.none;
        width = 1f;
        hitColor = color = Color.valueOf("bf92f9");
        lifetime = 23f;
}};

        sporeSap = new SapBulletType(){{
        sapStrength = 0.50f;
        length = 137f;
        damage = 10f;
        shootEffect = Fx.shootSmall;
        despawnEffect = Fx.none;
        width = 1f;
        hitColor = color = Color.valueOf("bf92f9");
        lifetime = 23f;
}};

        planatriumSap = new SapBulletType(){{
        sapStrength = 0.85f;
        length = 137f;
        damage = 15f;
        shootEffect = Fx.shootSmall;
        despawnEffect = Fx.none;
        width = 1f;
        hitColor = color = Color.valueOf("bf92f9");
        lifetime = 23f;
}};

//artillery
        artilleryForsFrag = new BasicBulletType(2.9f, 23, "bullet"){{
            width = 10f;
            height = 12f;
            shrinkY = 1f;
            lifetime = 15f;
            damage = 24f;
            backColor = SnPal.redBomb;
            frontColor = SnPal.redBombBack;
            despawnEffect = Fx.none;
            collidesAir = true;
        }};

        artilleryFors = new ArtilleryBulletType(3.0f, 70, "shell"){{
            hitEffect = SnFx.redBomb;
            knockback = 1f;
            width = height = 19f;
            lifetime = 110f;
            width = height = 25f;
            collidesTiles = false;
            collidesAir = true;
            splashDamageRadius = 60f * 0.75f;
            splashDamage = 120f;
            fragBullet = artilleryForsFrag;
            fragBullets = 10;
            backColor = SnPal.redBomb;
            frontColor = SnPal.redBombBack;
        }};

        artilleryBlastFrag = new BasicBulletType(2.9f, 25, "bullet"){{
            width = 10f;
            height = 12f;
            shrinkY = 1f;
            damage = 20f;
            lifetime = 15f;
            backColor = SnPal.BlastBullet;
            frontColor = SnPal.BlastBulletBack;
            despawnEffect = Fx.none;
            collidesAir = true;
        }};

        artilleryBlast = new ArtilleryBulletType(3.0f, 75, "shell"){{
            hitEffect = SnFx.redBomb;
            knockback = 1f;
            lifetime = 110f;
            width = height = 25f;
            collidesTiles = false;
            collidesAir = true;
            splashDamageRadius = 68f * 0.75f;
            splashDamage = 90f;
            fragBullet = artilleryForsFrag;
            fragBullets = 11;
            makeFire = true;
            backColor = SnPal.BlastBullet;
            frontColor = SnPal.BlastBulletBack;
        }};
//heavy-standart
        heavyCopper = new BasicBulletType(2.7f, 14){{
            width = 9f;
            height = 11f;
            lifetime = 67f;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
            ammoMultiplier = 2;
        }};

        heavyDense = new BasicBulletType(3.9f, 21){{
            width = 11f;
            height = 14f;
            reloadMultiplier = 0.6f;
            ammoMultiplier = 3;
            lifetime = 67f;
        }};

        heavyThorium = new BasicBulletType(4.3f, 34, "bullet"){{
            width = 12f;
            height = 15f;
            shootEffect = Fx.shootBig;
            smokeEffect = Fx.shootBigSmoke;
            ammoMultiplier = 3;
            lifetime = 67f;
        }};

        heavyTitanium = new BasicBulletType(3.4f, 16, "bullet"){{
            width = 9f;
            height = 11f;
            reloadMultiplier = 1.5f;
            ammoMultiplier = 5;
            lifetime = 67f;
        }};

        standartBlast = new BasicBulletType(3.5f, 15, "bullet"){{
            width = 12f;
            height = 14f;
            frontColor = Pal.lightishOrange;
            backColor = Pal.lightOrange;
            status = StatusEffects.burning;
            makeFire = true;
            inaccuracy = 3f;
            lifetime = 67f;
        }};

        heavyIncendiary = new BasicBulletType(3.5f, 15, "bullet"){{
            width = 12f;
            height = 14f;
            frontColor = Pal.lightishOrange;
            backColor = Pal.lightOrange;
            status = StatusEffects.burning;
            makeFire = true;
            inaccuracy = 3f;
            lifetime = 67f;
        }};

//big standart
        heavystandardDense = new BasicBulletType(8f, 80, "bullet"){{
            hitSize = 5;
            width = 19f;
            height = 24f;
            shootEffect = Fx.shootBig;
        }};

        heavystandardThorium = new BasicBulletType(9.4f, 130, "bullet"){{
            hitSize = 7;
            width = 20f;
            height = 27f;
            shootEffect = Fx.shootBig;
            pierceCap = 2;
            pierceBuilding = true;
            knockback = 0.7f;
        }};

        heavystandardIncendiary = new BasicBulletType(7f, 90, "bullet"){{
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

        standartBlastBig = new BasicBulletType(9f, 85, "bullet"){{
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

        standardForsBig = new BasicBulletType(7.8f, 100, "bullet"){{
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

//burn
        heavyCoalFlame = new BulletType(12f, 30f){{
            ammoMultiplier = 3f;
            hitSize = 7f;
	        pierce = true;
	        collidesAir = true;
            lifetime = 9f;
            pierce = true;
            statusDuration = 60f * 4;
            shootEffect = SnFx.heavyFlame;
            hitEffect = Fx.hitFlameSmall;
            despawnEffect = Fx.none;
            status = StatusEffects.burning;
            keepVelocity = false;
            hittable = false;
        }};

        heavyPyraFlame = new BulletType(12f, 33f){{
            ammoMultiplier = 3f;
            hitSize = 7f;
	        pierce = true;
	        collidesAir = true;
            lifetime = 9f;
            pierce = true;
            statusDuration = 60f * 4;
            shootEffect = SnFx.heavyFlame;
            hitEffect = Fx.hitFlameSmall;
            despawnEffect = Fx.none;
            status = StatusEffects.burning;
            keepVelocity = false;
            hittable = false;
        }};
        
        blastFlame = new BulletType(12f, 36f){{
            ammoMultiplier = 3f;
            hitSize = 7f;
	        pierce = true;
	        collidesAir = true;
            lifetime = 9f;
            pierce = true;
            statusDuration = 60f * 4;
            shootEffect = SnFx.heavyFlame;
            hitEffect = Fx.hitFlameSmall;
            despawnEffect = Fx.none;
            status = StatusEffects.burning;
            keepVelocity = false;
            hittable = false;
        }};

        flameidFlame = new BulletType(12f, 38f){{
            ammoMultiplier = 3f;
            hitSize = 7f;
	        pierce = true;
	        collidesAir = true;
            lifetime = 9f;
            pierce = true;
            statusDuration = 60f * 4;
            shootEffect = SnFx.heavyFlame;
            hitEffect = Fx.hitFlameSmall;
            despawnEffect = Fx.none;
            status = StatusEffects.burning;
            keepVelocity = false;
            hittable = false;
        }};
//liquid
        typhoonWaterShot = new LiquidBulletType(Liquids.water){{
            lifetime = 70f;
            speed = 4f;
            knockback = 2.5f;
            puddleSize = 11f;
            orbSize = 6f;
            drag = 0.001f;
            ammoMultiplier = 0.5f;
            statusDuration = 60f * 4f;
            damage = 0.3f;
        }};

        typhoonCryoShot = new LiquidBulletType(Liquids.cryofluid){{
            lifetime = 70f;
            speed = 4f;
            knockback = 1.8f;
            puddleSize = 11f;
            orbSize = 6f;
            drag = 0.001f;
            ammoMultiplier = 0.5f;
            statusDuration = 60f * 4f;
            damage = 0.4f;
        }};

        typhoonSlagShot = new LiquidBulletType(Liquids.slag){{
            lifetime = 49f;
            speed = 4f;
            knockback = 1.8f;
            puddleSize = 8f;
            orbSize = 4f;
            damage = 4.95f;
            drag = 0.001f;
            ammoMultiplier = 0.4f;
            statusDuration = 60f * 4f;
        }};

        typhoonOilShot = new LiquidBulletType(Liquids.oil){{
            lifetime = 70f;
            speed = 4f;
            knockback = 1.8f;
            puddleSize = 11f;
            orbSize = 6f;
            drag = 0.001f;
            ammoMultiplier = 0.5f;
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

        floodCryoShot = new ArtilleryLiquidBulletType(Liquids.cryofluid){{
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

        floodSlagShot = new ArtilleryLiquidBulletType(Liquids.slag){{
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

        floodOilShot = new ArtilleryLiquidBulletType(Liquids.oil){{
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
//blast
        lightBlastGraphite = new BasicBulletType(14, 19){{
            lifetime = 11f;
            splashDamage = 39f;
            splashDamageRadius = 13f;
            width = 4f;
            height = 8f;
            frontColor = Color.valueOf("ccccdd");
            backColor = Color.valueOf("888888");
            collidesAir = true;
        }};

        lightBlastSilicon = new BasicBulletType(9, 34){{
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

        mediumBlastGraphite = new BasicBulletType(9, 32){{
            lifetime = 21f;
            splashDamage = 54f;
            splashDamageRadius = 21f;
            width = 8f;
            height = 12f;
            frontColor = Color.valueOf("ccccdd");
            backColor = Color.valueOf("888888");
            collidesAir = true;
        }};

        mediumBlastSilicon = new BasicBulletType(){{
            lifetime = 21f;
            speed = 9f;
            damage = 51f;
            splashDamage = 19f;
            splashDamageRadius = 11f;
            homingPower = 0.05f;
            homingRange = 197f;
            ammoMultiplier = 3f;
            reloadMultiplier = 1.2f;
            width = 8f;
            height = 12f;
            frontColor = Color.valueOf("ccccdd");
            backColor = Color.valueOf("888888");
            collidesAir = true;
        }};

        mediumBlastBlast = new BasicBulletType(9, 26) {{
            lifetime = 21f;
            splashDamage = 54f;
            splashDamageRadius = 35f;
            ammoMultiplier = 1f;
            reloadMultiplier = 0.9f;
            width = 8f;
            height = 12f;
            frontColor = Pal.lightFlame;
            backColor = Pal.darkFlame;
            collidesAir = true;
        }};

        bigBlastPlastanium = new BasicBulletType(12, 81){{
            lifetime = 20f;
            splashDamage = 16f;
            splashDamageRadius = 14f;
            width = 12f;
            height = 16f;
            frontColor = Pal.plastaniumFront;
            backColor = Pal.plastaniumBack;
            collidesAir = false;
        }};

        bigBlastBlast = new BasicBulletType(12, 59){{
            lifetime = 20f;
            splashDamage = 75f;
            splashDamageRadius = 58f;
            width = 12f;
            height = 16f;
            frontColor = Pal.lightFlame;
            backColor = Pal.darkFlame;
            collidesAir = false;
        }};

        bigBlastPyratite = new BasicBulletType(12, 46){{
            lifetime = 20f;
            splashDamage = 59f;
            splashDamageRadius = 58f;
            width = 12f;
            height = 16f;
            frontColor = Pal.lightishOrange;
            backColor = Pal.lightOrange;
            status = StatusEffects.burning;
            reloadMultiplier = 1.1f;
            makeFire =true;
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

        maxBlastPlastanium = new BasicBulletType(16, 116){{
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

        maxBlastBlast = new BasicBulletType(16, 80){{
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

        maxBlastSurge = new BasicBulletType(16,115) {{
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

//units
        BasicHelicopterGun = new BasicBulletType(3f, 12){{
            width = 8f;
            height = 10f;
            lifetime = 60f;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
        }};

        HelicopterShootgun = new BasicBulletType(2.9f, 18){{
            width = 9f;
            height = 10f;
            lifetime = 90f;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
        }};

        cometWaterShot = new LiquidBulletType(Liquids.water){{
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
    }
}
