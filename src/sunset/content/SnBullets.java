package sunset.content;

import arc.graphics.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import sunset.graphics.*;

public class SnBullets implements ContentList{
    public static BulletType 

//artillery
    artilleryForsFrag, artilleryFors, artilleryBlastFrag, artilleryBlast, 
//burner 
    heavyCoalFlame, heavyPyraFlame, blastFlame, flameidFlame,
//liquid
    typhoonWaterShot, typhoonCryoShot, typhoonSlagShot, typhoonOilShot,
//blast
    lightBlastGraphite, lightBlastSilicon, mediumBlastGraphite, mediumBlastSilicon, mediumBlastBlast,
    bigBlastPlastanium, bigBlastBlast, bigBlastPyratite, maxBlastPlastanium, maxBlastBlast, maxBlastSurge,
    maxBlastPlastaniumFrag;

    @Override
    public void load(){

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
            backColor = SnPal.redBomb;
            frontColor = SnPal.redBombBack;
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
            backColor = SnPal.redBomb;
            frontColor = SnPal.redBombBack;
        }};
    
//burner
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
//blast
        lightBlastGraphite = new BasicBulletType(14, 16){{
            lifetime = 11f;
            splashDamage = 34f;
            splashDamageRadius = 13f;
            width = 4f;
            height = 8f;
            frontColor = Color.valueOf("ccccdd");
            backColor = Color.valueOf("888888");
            collidesAir = true;
        }};

        lightBlastSilicon = new BasicBulletType(9, 29){{
            lifetime = 19f;
            splashDamage = 11f;
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

        mediumBlastGraphite = new BasicBulletType(9,28){{
            lifetime = 21f;
            splashDamage = 47f;
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
            damage = 46f;
            splashDamage = 16f;
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

        mediumBlastBlast = new BasicBulletType(9, 23) {{
            lifetime = 21f;
            splashDamage = 49f;
            splashDamageRadius = 35f;
            ammoMultiplier = 1f;
            reloadMultiplier = 0.9f;
            width = 8f;
            height = 12f;
            frontColor = Pal.lightFlame;
            backColor = Pal.darkFlame;
            collidesAir = true;
        }};

        bigBlastPlastanium = new BasicBulletType(12, 76){{
            lifetime = 20f;
            splashDamage = 14f;
            splashDamageRadius = 14f;
            width = 12f;
            height = 16f;
            frontColor = Pal.plastaniumFront;
            backColor = Pal.plastaniumBack;
            collidesAir = false;
        }};

        bigBlastBlast = new BasicBulletType(12, 53){{
            lifetime = 20f;
            splashDamage = 67f;
            splashDamageRadius = 58f;
            width = 12f;
            height = 16f;
            frontColor = Pal.lightFlame;
            backColor = Pal.darkFlame;
            collidesAir = false;
        }};

        bigBlastPyratite = new BasicBulletType(12, 49){{
            lifetime = 20f;
            splashDamage = 51f;
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

        maxBlastPlastaniumFrag = new BasicBulletType(18, 29) {{
            lifetime = 2;
            collidesAir = false;
            frontColor = Pal.plastaniumFront;
            backColor = Pal.plastaniumBack;
            width = 2f;
            height = 3f;
        }};

        maxBlastPlastanium = new BasicBulletType(16, 109){{
            lifetime = 22;
            splashDamage = 57;
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

        maxBlastBlast = new BasicBulletType(16, 72){{
            lifetime = 22f;
            splashDamage = 127f;
            splashDamageRadius = 127f;
            hitSound = Sounds.explosion;
            width = 16f;
            height = 20f;
            frontColor = Pal.lightFlame;
            backColor = Pal.darkFlame;
            collidesAir = false;
        }};

        maxBlastSurge = new BasicBulletType(16,107) {{
            lifetime = 22f;
            splashDamage = 125f;
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
    }
}
