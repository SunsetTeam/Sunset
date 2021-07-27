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
import sunset.entities.bullet.ArtilleryLiquidBulletType;
import sunset.entities.bullet.ExtinguishBulletType;
import sunset.graphics.*;
import sunset.type.StackableStatusEffect;


public class SnBullets implements ContentList{
    public static BulletType 

//sap
    leadSap, sporeSap, planatriumSap,
//artillery
    artilleryForsMine, artilleryFors, artilleryBlastMine, artilleryBlast,
//heavy-standart
    heavyCopper, heavyDense, heavyThorium, heavyTitanium, standartBlast, heavyIncendiary, 
    heavystandardDense, heavystandardThorium, heavystandardIncendiary, standartBlastBig, standardForsBig,
//burner 
    heavyCoalFlame, heavyPyraFlame, flameidFlame,
//liquid
    typhoonWaterShot, typhoonCryoShot, typhoonSlagShot, typhoonOilShot,
    floodWaterShot, floodCryoShot, floodSlagShot, floodOilShot,
//blast
    lightBlastGraphite, lightBlastSilicon,
    bigBlastPlastanium, bigBlastBlast, bigBlastPyratite, maxBlastPlastanium, maxBlastBlast, maxBlastSurge,
    maxBlastPlastaniumFrag,
//units
    BasicHelicopterGun, HelicopterShootgun, HelicopterMissile, HelicopterBomb, HelicopterRocket, bigHelicopterGun, laserHelicopterFrag, largeHelicopterGun, bigHelicopterRocket, HelicopterMissiles,
    cometWaterShot, starStunBullet, galaxyKnockbackBullet,
//misc
    emptyBullet, overheatBullet,
//test
    testbullet;

//exoticBullets (new) i will make it later... i must make more bulets (soulBullet, iceSpike, and more)
// spiralPllastanium, spiralSurge, SpiralFors, SpiralThorium, SpiralSmall;

    @Override
    public void load(){

//sap
        leadSap = new SapBulletType(){{
        sapStrength = 0.30f;
        length = 137f;
        damage = 10f;
        shootEffect = Fx.shootSmall;
        despawnEffect = Fx.none;
        width = 1f;
        hitColor = color = Color.valueOf("bf92f9");
        lifetime = 23f;
}};

        sporeSap = new SapBulletType(){{
        sapStrength = 0.50f;
        length = 137f;
        damage = 14f;
        shootEffect = Fx.shootSmall;
        despawnEffect = Fx.none;
        width = 1f;
        hitColor = color = Color.valueOf("bf92f9");
        lifetime = 23f;
}};

        planatriumSap = new SapBulletType(){{
        sapStrength = 0.85f;
        length = 137f;
        damage = 17f;
        shootEffect = Fx.shootSmall;
        despawnEffect = Fx.none;
        width = 1f;
        hitColor = color = Color.valueOf("bf92f9");
        lifetime = 23f;
}};

//artillery
        artilleryForsMine = new FlakBulletType(3f, 20){{
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
            fragBullet = artilleryForsMine;
            fragBullets = 2;
            backColor = SnPal.redBomb;
            frontColor = SnPal.redBombBack;
        }};

        artilleryBlastMine = new FlakBulletType(2.9f, 25){{
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

        artilleryBlast = new ArtilleryBulletType(3.0f, 75, "shell"){{
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
        heavyCoalFlame = new BulletType(12f, 28f){{
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

        heavyPyraFlame = new BulletType(12f, 35f){{
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

        flameidFlame = new BulletType(12f, 41f){{
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
        BasicHelicopterGun = new BasicBulletType(5.6f, 11){{
            width = 8f;
            height = 11f;
            lifetime = 35f;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
        }};

        HelicopterShootgun = new BasicBulletType(5f, 9){{
            width = 10f;
            height = 13f;
            lifetime = 45f;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
        }};
        
        HelicopterMissile = new MissileBulletType(6f, 31){{
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
        
        HelicopterBomb = new BasicBulletType(){{
            sprite = "sunset-copter-bomb";
            width = height = 70/2f;

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

            speed = 2f;
            collides = true;

            splashDamage = 140f;
            splashDamageRadius = 45f;
        }};
        
        bigHelicopterGun = new BasicBulletType(9f, 53){{
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

        HelicopterRocket = new MissileBulletType(5.0f, 50){{
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
        
        laserHelicopterFrag = new LaserBulletType (50){{
            colors = new Color[]{SnPal.copterLaser.cpy().a(0.4f), SnPal.copterLaser, Color.white};
            width = 20f;
            lifetime = 15f;
            length = 50f;
            laserEffect = Fx.lancerLaserShootSmoke;
            collidesAir = true;
            collidesGround = true;
        }};

        largeHelicopterGun = new BasicBulletType(10f, 80){{
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

        bigHelicopterRocket = new MissileBulletType(6.3f, 70){{
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
        
        HelicopterMissiles = new MissileBulletType(4.4f, 45){{
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

        cometWaterShot = new ExtinguishBulletType(Liquids.water){{
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

        starStunBullet = new BasicBulletType(){{
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
            public void update(Bullet b){
                if(b.timer(0, 3f)){
                    trailEffect.at(b.x, b.y);
                }
            }
        };
        
        galaxyKnockbackBullet = new BasicBulletType(12, 79){{
            lifetime = 36;
            knockback = 384;
            status = SnStatusEffects.stun;
            statusDuration = 30;
            height = 30;
            width = 6;
        }};
//misc
        overheatBullet = new BasicBulletType(0, 2){{
            status = SnStatusEffects.overheat;
            statusDuration = 120f;
            shootEffect = Fx.pointBeam;
            lightningColor = Color.red;
            instantDisappear = true;
        }

            @Override
            public void init(Bullet b) {
                Unit u = Units.closestEnemy(b.team, b.x, b.y, range(),
                    unit -> true || Angles.within(b.rotation(), b.angleTo(unit.x, unit.y), 10));
                if(u != null) {
                    shootEffect.at(b.x, b.y, b.rotation(), lightningColor, new Vec2().set(u.x, u.y));
                    u.damagePierce(b.damage);
                    ((StackableStatusEffect)status).apply(u, statusDuration);
                } else {
                    shootEffect.at(b.x, b.y, b.rotation(), lightningColor,
                        new Vec2().setLength(range()).setAngle(b.rotation()));
                }
            }

            @Override
            public float range() { return 370; }
        };

        emptyBullet = new BasicBulletType(0, 0, "error"){{
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

//test
        testbullet = new MissileBulletType(2.7f, 14){{
            sprite = "sunset-guardian-rocket";
            width = 9f;
            height = 11f;
            lifetime = 67f;
            shrinkY = 0f;
            drag = -0.003f;
            homingRange = 30f;
            homingPower = 0.2f;
            despawnEffect = SnFx.heavyCopterBomb;
            hitEffect = Fx.massiveExplosion;
            ammoMultiplier = 2;
        }};
    }
}
