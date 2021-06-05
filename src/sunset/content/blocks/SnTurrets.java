package sunset.content.blocks;

import static mindustry.type.ItemStack.with;


import arc.graphics.*;
import arc.struct.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.*;
import arc.math.geom.Vec2;
import sunset.content.SnFx;
import sunset.content.SnBullets;
import sunset.content.SnItems;
import sunset.entities.bullet.EnergySphereBulletType;
import sunset.graphics.SnPal;
import sunset.world.MissileLogic;
import sunset.world.blocks.turrets.ChainLightningTurret;
import sunset.world.blocks.turrets.MissileSiloTurret;

public class SnTurrets implements ContentList {
    public static Block

    //turrets
    burner, tetramite, typhoon, chain, field, triden, galebard, sunrise, spark, dissector, art;
    @Override
    public void load() {

//3 on 3
        burner = new ItemTurret("burner"){{
            requirements(Category.turret, with(Items.copper, 70, Items.graphite, 40, SnItems.fors, 36));
            ammo(
                Items.coal, SnBullets.heavycoalFlame,
                Items.pyratite, SnBullets.heavypyraFlame,
                Items.blastCompound, SnBullets.blastFlame,
                SnItems.flameid, SnBullets.flameidFlame
            );
            recoilAmount = 0f;
            reloadTime = 5f;
            coolantMultiplier = 1.5f;
            range = 90f;
            itemCapacity = 20;
            size = 3;
            shootCone = 53f;
            targetAir = true;
            ammoUseEffect = Fx.none;
            health = 140 * size * size;
            shootSound = Sounds.flame;
        }};

        tetramite = new TractorBeamTurret("tetramite"){{
            requirements(Category.turret, with(Items.silicon, 120, Items.titanium, 90, Items.graphite, 30));

            hasPower = true;
            size = 3;
            force = 8f;
            scaledForce = 7f;
            range = 300f;
            damage = 0.4f;
            health = 150 * size * size;
            rotateSpeed = 8;

            consumes.powerCond(5f, (TractorBeamBuild e) -> e.target != null);
        }};
// 4 on 4
        typhoon = new LiquidTurret("typhoon"){{
            requirements(Category.turret, with(Items.metaglass, 200, Items.lead, 530, Items.titanium, 340, Items.thorium, 170));
            ammo(
                Liquids.water, SnBullets.typhoonWaterShot,
                Liquids.slag, SnBullets.typhoonSlagShot,
                Liquids.cryofluid, SnBullets.typhoonCryoShot,
                Liquids.oil, SnBullets.typhoonOilShot
            );
            size = 4;
            reloadTime = 2f;
            shots = 2;
            liquidCapacity = 60;
            velocityInaccuracy = 0.15f;
            inaccuracy = 6f;
            recoilAmount = 1f;
            restitution = 0.05f;
            shootCone = 46f;
            liquidCapacity = 70f;
            shootEffect = Fx.shootLiquid;
            range = 230f;
            health = 200 * size * size;
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
        }};

        chain = new ChainLightningTurret("chain",4,16f,0.5f){{
            range = 168.0f;
            damage = 8.75f;
            health = 2800;
            size = 4;
            shootCone = 8f;
            damageMultiplier = 0.85f;
            coolantMultiplier = 1.2f;
            rotateSpeed = 6.75f;
            laserColor = SnPal.chainLaser;

//5 on 5
       field = new ItemTurret("field"){{
        requirements(Category.turret, with(Items.copper, 1200, Items.lead, 800, Items.plastanium, 350, Items.thorium, 400, SnItems.fors, 400));
        ammo(
            SnItems.fors, SnBullets.artilleryFors,
            Items.blastCompound, SnBullets.artilleryBlast
        );
        targetAir = true;
        targetGround = true;
        itemCapacity = 40;
            size = 5;
            shots = 1;
            inaccuracy = 4f;
            reloadTime = 50f;
            ammoEjectBack = 4f;
            ammoUseEffect = Fx.casing3Double;
            alternate = true;
            spread = 3f;
            cooldown = 0.06f;
            velocityInaccuracy = 0.2f;
            restitution = 0.02f;
            recoilAmount = 4f;
            shootShake = 2f;
            range = 270f;
            minRange = 30f;

        health = 160 * size * size;
       }};

//6 on 6
        }};
        triden = new PowerTurret("triden"){{
            health = 5400;
            range = 247f;
            size = 6;
            chargeTime = 210f;
            chargeMaxDelay = 0f;
            reloadTime = 460f;
            recoilAmount = 8f;
            chargeEffects = 1;
            powerUse = 38f;
            chargeEffect = SnFx.tridenCharge;
            category = Category.turret;
            buildVisibility = BuildVisibility.shown;
            shootType = new EnergySphereBulletType("triden-bullets", 1.75f, 240f){{
                hitSize = 8f;
                splashDamage = 1280f;
                splashDamageRadius = 112f;
                lightningDamage = 48f;
                lightningPeriod = 30f;
                lightningLength = 192;
                healPercent = 5f;
                lifetime = 250f;
                hitEffect = SnFx.tridenHit;
            }};
        }};

//7 on 7
        galebard = new PowerTurret("galebard"){{
            requirements(Category.turret, with(Items.copper, 1200,  Items.metaglass, 600, Items.lead, 800, Items.silicon, 700, Items.plastanium, 400, SnItems.nobium, 300));
            range = 350f;
            chargeTime = 145f;
            rotateSpeed = 1.6F;
            chargeMaxDelay = 140f;
            chargeEffects = 15;
            recoilAmount = 8f;
            reloadTime = 345f;
            cooldown = 10f;
            powerUse = 24f;
            shootShake = 7f;
            shootEffect = Fx.lancerLaserShoot;
            smokeEffect = Fx.none;
            chargeEffect = SnFx.galebardLaserCharge;
            chargeBeginEffect = SnFx.galebardLaserChargeBegin;
            heatColor = Color.red;
            size = 7;
            health = 170 * size * size;
            targetGround = true;
            shootSound = Sounds.laser;

            shootType = new LaserBulletType(1730){{
                colors = new Color[]{Pal.lancerLaser.cpy().a(0.4f), Pal.lancerLaser, Color.white};
                hitEffect = SnFx.hitGalebard;
                despawnEffect = Fx.none;
                lifetime = 60f;
                drawSize = 440f;
                collidesAir = false;
                length = 370f;
                width = 50.0F;
            }};
        }};

//missle
        sunrise = new MissileSiloTurret("sunrise"){{
            size = 2;
            itemCapacity = 240;
            missile = new MissileLogic.MissileType(){{
                damage = 0f;
                splashDamage = 590f;
                splashDamageRadius = 98f;
                speed = 1.75f;
                explodeEffect = Fx.massiveExplosion;
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
        spark = new MissileSiloTurret("spark"){{
            size = 3;
            itemCapacity = 900;
            missile = new MissileLogic.MissileType(){{
                damage = 0f;
                splashDamage = 4400f;
                splashDamageRadius = 129f;
                speed = 1.5f;
                explodeEffect = Fx.massiveExplosion;
            }};
            craftTime = 4200f;
            consumes.items(with(Items.graphite, 270, Items.silicon, 360, Items.plastanium, 450, Items.blastCompound, 540));
            consumes.power(4.5f);
            health = 720;
            maxRange = 434.0f;
            minRange = 168.0f;
            category = Category.turret;
            buildVisibility = BuildVisibility.shown;
        }};
        dissector = new MissileSiloTurret("dissector"){{
            size = 4;
            itemCapacity = 2400;
            missile = new MissileLogic.MissileType(){{
                damage = 0f;
                splashDamage = 18600f;
                splashDamageRadius = 154f;
                speed = 1.25f;
                explodeEffect = Fx.massiveExplosion;
            }};
            craftTime = 16800f;
            consumes.items(with(Items.phaseFabric, 440, Items.silicon, 620, Items.surgeAlloy, 800, Items.thorium, 980, Items.plastanium, 1200));
            consumes.power(16f);
            health = 1280;
            maxRange = 524.0f;
            minRange = 248.0f;
            category = Category.turret;
            buildVisibility = BuildVisibility.shown;
        }};
        art = new MissileSiloTurret("art"){{
            size = 5;
            itemCapacity = 2400;
            float p1 = 53f / 162f, p2 = 109f / 162f;
            rockets = new Seq<>(new Vec2[] {
                    new Vec2(p1, p1),
                    new Vec2(p1, p2),
                    new Vec2(p2, p1),
                    new Vec2(p2, p2)
            });
            missile = new MissileLogic.MissileType(){{
                damage = 0f;
                splashDamage = 7200f;
                splashDamageRadius = 137f;
                speed = 1f;
                explodeEffect = Fx.massiveExplosion;
            }};
            craftTime = 4500f;
            consumes.items(with(Items.plastanium, 180, Items.silicon, 270, Items.surgeAlloy, 90, Items.thorium, 330));
            consumes.power(7.5f);
            health = 2000;
            maxRange = 578.0f;
            minRange = 276.0f;
            category = Category.turret;
            buildVisibility = BuildVisibility.shown;
        }};
    }
}
