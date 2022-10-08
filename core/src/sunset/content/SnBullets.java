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
import mindustry.entities.Effect;
import mindustry.entities.Lightning;
import mindustry.entities.Units;
import mindustry.entities.abilities.MoveEffectAbility;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Bullet;
import mindustry.gen.Hitboxc;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Weapon;
import mindustry.type.unit.MissileUnitType;
import mma.*;
import sunset.entities.bullet.*;
import sunset.gen.SnSounds;
import sunset.graphics.SnPal;
import sunset.type.StackableStatusEffect;

import static mma.ModVars.fullName;

public class SnBullets {
    //region definitions
    public static BulletType
        //standard
        forsBullet, forsBulletFrag, eriusBulletFrag, eriusBullet,
        sniperSurge,
        //missile
        eriusStailerAimMissile, anzarStailerAimMissile, nobiumAimMissile, naturiteAimMissile, renubiteAimMissile,
        //artillery
        //flak
        naturiteCircleBullet, anzarCircleBullet,
        //shrapnel
        //laser
        //lightning
        //sap
        leadSap, sporeSap, planatriumSap,
        //t6 vanilla
        //energy sphere
        mediumEnergySphere,
        //liquid
        floodWaterShot, floodCryoShot, floodSlagShot, floodOilShot, floodBurheynaShot,
        burheynaFrag,//unused
        //flame
        //reverse-bullets
        naturiteReversBulletBack, naturiteReversBullet, forsReversBulletBack, forsReversBullet, nobiumReversBulletBack, nobiumReversBullet,
        //copters
        basicHelicopterGun,
        mediumHelicopterGun, helicopterMissile,
        clusterRocket, copterEnergySphere,
        bigHelicopterGun, laserGun, bigHelicopterMissile,
        largeHelicopterGun, largeHelicopterMissile, smallHelicopterMissile,
        giantHelicopterGun, shrapnelCopterGun, bigClusterRocket, bigCopterEnergySphere,
        //yellow ships
        smallShell,
        salvoArt, smallTorpedo,
        lightningBall, trailRocket,
        //buffer
        cometWaterShot, starStunBullet, galaxyKnockbackBullet, universeLaserBullet, universeEnergySphere,
        //misc and testing
        flowerMode1Bullet, flowerMode2Bullet,
        emptyBullet, overheatBullet,
        temp;
    //endregion definitions

    public static void load() {
        //region shell
        //region standard
        forsBulletFrag = new BasicBulletType(4f, 15, "bullet") {{
            width = 9f;
            height = 14f;
            lifetime = 21f;
            backColor = SnPal.forsBulletBack;
            frontColor = SnPal.forsBullet;
            shootEffect = SnFx.forsShootSmall;
            despawnEffect = SnFx.forsSmallExplosion;
            trailLength = 4;
            trailWidth = 2.16f;
            trailColor = SnPal.forsBulletBack;
            collidesGround = true;
            collidesAir = true;

            homingPower = 0.1f;
            homingRange = 6 * Vars.tilesize;

            weaveMag = 3;
            weaveScale = 3;
        }};
        eriusBulletFrag = new BasicBulletType(5f, 12, "bullet") {{
            width = 9f;
            height = 16f;
            lifetime = 21f;
            backColor = SnPal.eriusBulletBack;
            frontColor = SnPal.eriusBullet;
            shootEffect = SnFx.eriusShootSmall;
            despawnEffect = SnFx.eriusSmallExplosion;
            trailLength = 5;
            trailWidth = 2.16f;
            trailColor = SnPal.eriusBulletBack;
            collidesGround = true;
            collidesAir = true;

            homingPower = 0.1f;
            homingRange = 6 * Vars.tilesize;

            weaveMag = 3;
            weaveScale = 3;
        }};

        forsBullet = new BasicBulletType(5f, 12, "bullet") {{
            width = 7f;
            height = 12f;
            lifetime = 16f;
            drag = 0.1f;
            backColor = SnPal.forsBulletBack;
            frontColor = SnPal.forsBullet;
            shootEffect = SnFx.forsShootSmall;
            despawnEffect = SnFx.forsSmallExplosion;
            trailLength = 2;
            trailWidth = 1.7f;
            trailColor = SnPal.forsBulletBack;
            maxRange = 128;
            collidesGround = true;
            collidesAir = true;

            weaveMag = 3;
            weaveScale = 3;

            fragBullet = forsBulletFrag;
            fragBullets = 1;
            fragRandomSpread = 0.001f;
            fragVelocityMin = 0.7f;
        }};
        eriusBullet = new BasicBulletType(6f, 9, "bullet") {{
            width = 7f;
            height = 14f;
            lifetime = 16f;
            drag = 0.1f;
            backColor = SnPal.eriusBulletBack;
            frontColor = SnPal.eriusBullet;
            shootEffect = SnFx.eriusShootSmall;
            despawnEffect = SnFx.eriusSmallExplosion;
            trailLength = 3;
            trailWidth = 1.7f;
            trailColor = SnPal.eriusBulletBack;
            maxRange = 140;
            collidesGround = true;
            collidesAir = true;

            weaveMag = 3;
            weaveScale = 3;

            fragBullet = eriusBulletFrag;
            fragBullets = 1;
            fragRandomSpread = 0.001f;
            fragVelocityMin = 0.7f;
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
            fragRandomSpread = 150f;
            damage = 6700f;
            pierceSizeMultiplier = 0.9f;
            pierceBuilding = pierce = true;
            buildingDamageMultiplier = 0.4f;
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

        //region missile
        eriusStailerAimMissile = new AimBulletType(5.5f, 8f){{
            sprite = "missile";
            width = 7;
            height = 13;
            lifetime = 90;
            maxRange = 180;
            homingPower = 0.01f;
            homingRange = 0;
            drag = -0.02f;
            weaveScale = 1;
            weaveMag = 1;
            splashDamageRadius = 7f;
            splashDamage = damage / 2;

            trailColor = SnPal.eriusBullet;
            trailEffect = SnFx.eriusStailerMissileTrail;
            trailRotation = true;
            trailInterval = 0.5f;
            despawnEffect = SnFx.eriusStailerMissileHit;
            collidesGround = false;
            collidesAir = true;

            frontColor = SnPal.eriusBullet;
            backColor = SnPal.eriusBulletBack;
        }};

        anzarStailerAimMissile = new AimBulletType(4f, 11f){{
            sprite = "missile";
            width = 7;
            height = 13;
            lifetime = 90;
            maxRange = 180;
            homingPower = 0.01f;
            homingRange = 0;
            drag = -0.02f;
            weaveScale = 1;
            weaveMag = 1;
            splashDamageRadius = 6f;
            splashDamage = damage / 2;

            trailColor = SnPal.anzarBullet;
            trailEffect = SnFx.anzarStailerMissileTrail;
            trailRotation = true;
            trailInterval = 0.5f;
            despawnEffect = SnFx.anzarStailerMissileHit;
            collidesGround = false;
            collidesAir = true;

            frontColor = SnPal.anzarBullet;
            backColor = SnPal.anzarBulletBack;
        }};

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
        //endregion missile
        //region artillery
        //endregion  artillery
        //region flak
        naturiteCircleBullet = new FlakBulletType(3f, 13) {{
            sprite = fullName("circle-bullet");
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
                sprite = fullName("circle-bullet");
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
            fragBullets = 4;

            weaveScale = 9f;
            weaveMag = 1f;
        }};

        anzarCircleBullet = new FlakBulletType(3f, 30) {{
            sprite = fullName("circle-bullet");
            knockback = 0.6f;
            lifetime = 250f;
            width = 13;
            height = 13f;
            hitEffect = Fx.flakExplosion;
            splashDamage = 21f;
            splashDamageRadius = 25f;
            shrinkX = 0.3f;
            shrinkY = 0.3f;
            trailLength = 4;
            trailWidth = 2f;
            drag = 0.02f;
            frontColor = SnPal.poisonBullet;
            backColor = SnPal.poisonBulletBack;
            collidesAir = true;
            collidesGround = true;

            weaveScale = 4f;
            weaveMag = 4f;
        }};

        //endregion  flak
        //region shrapnel
        //endregion shrapnel
        //region laser
        //endregion laser
        //region lightning
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
        mediumEnergySphere = new BasicBulletType(3f, 60) {{
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
            fragRandomSpread = 0.0001f;
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
            fragBullet = new LiquidBulletType(Liquids.water){{
                knockback = 0.7f;
                drag = 0.01f;
            }};
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
            fragBullet =new LiquidBulletType(Liquids.cryofluid){{
                drag = 0.01f;
            }};
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
            fragBullet =  new LiquidBulletType(Liquids.slag){{
                damage = 4;
                drag = 0.01f;
            }};
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
            fragBullet = new LiquidBulletType(Liquids.oil){{
                drag = 0.01f;
            }};
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
            public float calculateRange() {
                return lifetime * speed;
            }
        };

        burheynaFrag = new LiquidBulletType(SnLiquids.burheyna){{
            drag = 0.01f;
        }};

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
        }};//unused
        //endregion liquid
        //region flame
        //endregion flame
        //region reverse-bullets

        naturiteReversBulletBack = new ReverseBulletType(3f, 95f) {{
            sprite = fullName("copter-bomb");
            width = 15f;
            height = 15f;
            lifetime = 60;
            homingPower = 0.05f;
            homingRange = 50f;
            spin = 5f;
            shrinkX = 0f;
            shrinkY = 0f;
            pierceCap = 6;
            rotateMag = 5;
            frontColor = SnPal.copterLaser;
            backColor = SnPal.copterLaserBack;
            drag = 0.01f;
        }};

        naturiteReversBullet = new ReverseBulletType(3f, 97f) {{
            sprite = fullName("copter-bomb");
            width = 15f;
            height = 15f;
            lifetime = 70;
            homingPower = 0.05f;
            homingRange = 50f;
            spin = 5f;
            shrinkX = 0f;
            shrinkY = 0f;
            pierceCap = 6;
            rotateMag = 5;
            frontColor = SnPal.copterLaser;
            backColor = SnPal.copterLaserBack;
            drag = 0.01f;
        }
            public void despawned(Bullet b){
                naturiteReversBulletBack.create(b, b.x, b.y, b.rotation() - 180, 1f, 1f);
            }
        };

        forsReversBulletBack = new ReverseBulletType(3f, 110f) {{
            sprite = fullName("copter-bomb");
            width = 15f;
            height = 15;
            lifetime = 70;
            homingPower = 0.05f;
            homingRange = 50f;
            spin = 5f;
            shrinkX = 0f;
            shrinkY = 0f;
            pierceCap = 4;
            rotateMag = 5;
            frontColor = SnPal.redBomb;
            backColor = SnPal.redBombBack;
            drag = 0.01f;
        }};

        forsReversBullet = new ReverseBulletType(3f, 110f) {{
            sprite = fullName("copter-bomb");
            width = 15f;
            height = 15;
            lifetime = 70;
            homingPower = 0.05f;
            homingRange = 50f;
            spin = 5f;
            shrinkX = 0f;
            shrinkY = 0f;
            pierceCap = 4;
            rotateMag = 5;
            frontColor = SnPal.redBomb;
            backColor = SnPal.redBombBack;
            drag = 0.01f;
        }
            public void despawned(Bullet b){
                forsReversBulletBack.create(b, b.x, b.y, b.rotation() - 180, 1f, 1f);
            }
        };

        nobiumReversBulletBack = new ReverseBulletType(3f, 135f) {{
            sprite = fullName("copter-bomb");
            width = 15f;
            height = 15;
            lifetime = 70;
            homingPower = 0.2f;
            homingRange = 50f;
            spin = 5f;
            shrinkX = 0f;
            shrinkY = 0f;
            pierceCap = 7;
            rotateMag = 5;
            frontColor = SnPal.nobiumBullet;
            backColor = SnPal.nobiumBulletBack;
            drag = 0.01f;
        }};

        nobiumReversBullet = new ReverseBulletType(3f, 135f) {{
            sprite = fullName("copter-bomb");
            width = 15f;
            height = 15;
            lifetime = 70;
            homingPower = 0.2f;
            homingRange = 50f;
            spin = 5f;
            shrinkX = 0f;
            shrinkY = 0f;
            pierceCap = 7;
            rotateMag = 5;
            frontColor = SnPal.nobiumBullet;
            backColor = SnPal.nobiumBulletBack;
            drag = 0.01f;
        }
            public void despawned(Bullet b){
                nobiumReversBulletBack.create(b, b.x, b.y, b.rotation() - 180, 1f, 1f);
            }
        };
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
            fragRandomSpread = 90f;
            fragBullets = 5;
        }};
        copterEnergySphere = new BasicBulletType(3.3f, 13) {{
            sprite = fullName("circle-bullet");
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
            fragRandomSpread = 0.0001f;
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
            fragRandomSpread = 90f;
            fragBullets = 3;
        }};
        bigCopterEnergySphere = new LightningSphereBulletType(4f, 50, fullName("circle-bullet")) {{
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
            frontColor = Color.valueOf("FBFFE8");
            backColor = Color.valueOf("ECF97A");
            lifetime = 0.31f * Time.toSeconds;
            speed = 6.8f;
            damage = 10;
            drawSize = 6.1f;
            pierceCap = 3;
            ammoMultiplier = 2;
            reloadMultiplier = 1.1f;
            buildingDamageMultiplier = 0.6f;
            //recoil = 0;
            width = 6f;
            height = 9f;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
            collidesAir = absorbable = false;
            trailEffect = SnFx.acTrail;
            //trailColor = Pal.lightTrail;
            splashDamage = 15;
            splashDamageRadius = 3 * Vars.tilesize;
        }};
        //T2
        salvoArt = new ArtilleryBulletType(5f, 26, "shell") {{
            frontColor = Color.valueOf("FBFFE8");
            backColor = Color.valueOf("ECF97A");
            width = 5.5f;
            height = 12f;
            shootEffect = Fx.none;
            smokeEffect = Fx.none;
            hitEffect = despawnEffect = SnFx.hitYellowLaser;
            trailEffect = Fx.artilleryTrail;
            ammoMultiplier = 2.1f;
            lifetime = Time.toSeconds;
        }};
        smallTorpedo = new BasicBulletType(3, 60, "mine-bullet") {{
            backColor = SnPal.yellowTrail;
            frontColor = Color.white;
            mixColorTo = Color.white;
            lifetime = 1.1f * Time.toSeconds;
            drawSize = 9.2f;
            pierceCap = -1;
            inaccuracy = 1;
            ammoMultiplier = 1;
            reloadMultiplier = 3;
            buildingDamageMultiplier = 0.9f;
            recoil = 0;
            pierce = true;
            pierceBuilding = false;
            splashDamage = 40;
            splashDamageRadius = 10 * Vars.tilesize;
            hitSound = SnSounds.torpedo_explosion;
            trailEffect = SnFx.torpedoTrail;
            trailChance = 1;
            trailColor = Pal.surge;
            weaveMag = 3f;
            weaveScale = 5;
            layer = Layer.floor + 0.002f;
            width = height = 16;
            collidesAir = absorbable = keepVelocity = false;
            /*chargeShootEffect =*/ despawnEffect = hitEffect = shootEffect = smokeEffect = Fx.none;
            collideFloor = true;
            shrinkX = 0;
            shrinkY = 0;
        }};
        //T3
        lightningBall = new ArtilleryLightningBulletType(50) {{
            lightning = 5;
            lightningColor = SnPal.yellowTrailBack;
            maxRange = 320;
            hitEffect = SnFx.lbHit;
            despawnEffect = Fx.none;
        }};
        trailRocket = new MissileBulletType(5, 30) {{
            width = 5f;
            height = 8;
            shrinkY = 0.7f;
            drag = -0.003f;
            lifetime = 0.9f * Time.toSeconds;
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
            public float calculateRange() {
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
            maxRange = speed = 420; //range
            lifetime = 1;
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
            length = 660f;
            damage = 1470f;
            buildingDamageMultiplier = 0.2f;
            width = 120f;
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
                    ((Unit)entity).apply(SnStatusEffects.stun, stunDuration);
                }
            }
        };

        universeEnergySphere = new EnergySphereBulletType(4f, 290f) {{
            hitSize = 7f;
            splashDamage = 430f;
            splashDamageRadius = 56f;
            lightningPeriod = 25f;
            lightningChance = 0.3f;
            lightningLength = 59;
            healPercent = 10f;
            lightningDamage = 42;
            lifetime = 240f;
            frontColor = Pal.surge;
            //follow
            followRadius = 367;
            followFactor = 0.0375f;
            followMultiplier = 0.5f;
            distancePower = 4f;
            weightPower = 0.5f;
            minSpeedFactor = 0.75f;
            //draw
            count = 8;
            timeSclMin = 0.8f;
            timeSclMax = 2.5f;
            lenMin = 1.5f;
            lenMax = 4f;
            radMin = 1f;
            radMax = 3f;
        }
            @Override
            public float continuousDamage() { return 160; };
        };
        //endregion buffer
        //region misc and testing
        flowerMode1Bullet = new FlowerBulletType(){{
            trailColor = Color.rgb(255,0, 64);
            otherTrailColor = Color.rgb(255,0, 255);
            damage = 130;
            pierceCap = 1;
            speed = 8f;
            lifetime = 40f;
            rotateFactorMin = -0.04f;
            rotateFactorMax = 0.04f;
            trailLength = 48;
            trailWidth = 1.5f;
            trailInterval = 4.5f;
            trailEffect = SnFx.flowerTrail;
            despawnEffect = SnFx.flowerTrail;
            shoot = new ShootSpread(1, 0);
        }};
        flowerMode2Bullet = new FlowerBulletType(){{
            trailColor = Color.rgb(255,64,0);
            otherTrailColor = Color.rgb(255,255,0);
            damage = 160;
            pierceCap = 2;
            speed = 6f;
            lifetime = 45f;
            rotateFactorMin = -0.08f;
            rotateFactorMax = -0.06f;
            trailLength = 48;
            trailWidth = 1.5f;
            trailInterval = 6f;
            trailEffect = SnFx.flowerTrail;
            despawnEffect = SnFx.flowerTrail;
            shoot = new ShootSpread(8, 3);

            reloadMultiplier = 0.125f;
        }};
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
                Unit u = Units.closestEnemy(b.team, b.x, b.y, range,
                        unit -> Angles.within(b.rotation(), b.angleTo(unit.x, unit.y), 10));
                if (u != null) {
                    shootEffect.at(b.x, b.y, b.rotation(), lightningColor, new Vec2(u.x,u.y));
                    u.damagePierce(b.damage);
                    ((StackableStatusEffect) status).apply(u, statusDuration);
                } else {
                    shootEffect.at(b.x, b.y, b.rotation(), lightningColor,
                            new Vec2().setLength(range).setAngle(b.rotation()).add(b.x, b.y));
                }
            }

            @Override
            public float calculateRange() {
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
