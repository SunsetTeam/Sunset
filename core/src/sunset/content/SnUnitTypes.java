package sunset.content;

import mindustry.ai.types.*;
import mindustry.annotations.Annotations.EntityDef;
import mindustry.content.Fx;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.content.UnitTypes;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BombBulletType;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import mindustry.type.weapons.PointDefenseWeapon;
import sunset.ai.*;
import sunset.ai.weapon.ExtinguishWeaponAI;
import sunset.entities.abilities.StatusFieldAbility;
import sunset.entities.bullet.BerserkLaserBulletType;
import sunset.gen.Deliverc;
import sunset.gen.Segmentc;
import sunset.type.BerserkStage;
import sunset.type.ammo.LiquidAmmoType;
import sunset.type.blocks.Rotor;
import sunset.type.unitTypes.*;
import sunset.type.weapons.ChainWeapon;
import sunset.type.weapons.SnWeapon;
import sunset.type.weapons.WeaponExt;

public class SnUnitTypes implements ContentList{
    public static UnitType
    //ground
    mirage, vision, illusion, soothSayer, seer, abyssEye,
    wheelT1, wheelT2, wheelT3, wheelT4, wheelT5, wheelT6,
    freezingT1,
    //air
    wind, thunder, nadir, halo, mudflow, parhelion,
    bufferT1, satelite, planet, star, galaxy, bufferT6,
    //naval
    yellowT1, yellowT2, yellowT3, yellowT4, yellowT5,
    //misc
    router;
    //other
    @EntityDef({Unitc.class, Deliverc.class})
    public static UnitType courier;
    //@EntityDef({Unitc.class, FireFighterc.class})
    public static UnitType comet;
    @EntityDef({Unitc.class, Segmentc.class})
    public static UnitType snake1;

    @Override
    public void load() {
        //region ground
        //region berserk
        mirage = new BerserkUnitType("mirage"){{
            health = 320;
            speed = 1f;
            rotateSpeed = 3f;
            drag = 0.1f;
            armor = 4f;
            hitSize = 10f;
            allowLegStep = true;
            hovering = false;
            groundLayer = Layer.legUnit;
            visualElevation = 0.15f;

            legCount = 4;
            legLength = 7f;
            legTrns = 0.5f;
            legMoveSpace = 1.3f;

            weapons.add(new SnWeapon("mirage-gun"){{
                reload = 30f;
                x = 5;
                range = 100;
                y = -0.3f;
                inaccuracy = 2f;
                rotate = false;
                mirror = true;
                bullet = SnBullets.mirageGunBullet;
            }});
            stages.add(
            new BerserkStage(){{
                healthMaximum = 0.4f;
                bulletWidthMultiplier = 2f;
                effect = StatusEffects.overclock;
            }},
            new BerserkStage(){{
                healthMaximum = 0.15f;
                bulletWidthMultiplier = 3f;
                effect = StatusEffects.burning;
            }}
            );
        }};
        vision = new BerserkUnitType("vision"){{
            health = 980;
            speed = 0.9f;
            rotateSpeed = 2f;
            drag = 0.125f;
            armor = 9f;
            hitSize = 19f;
            allowLegStep = true;
            hovering = false;
            groundLayer = Layer.legUnit - 1f;
            visualElevation = 0.2f;

            legCount = 4;
            legLength = 10f;
            legTrns = 0.8f;
            legMoveSpace = 1.4f;
            legBaseOffset = 2f;

            weapons.add(
            new SnWeapon("vision-gun"){{
                reload = 30f;
                x = 6;
                y = -0.3f;
                alternate = true;
                mirror = true;
                inaccuracy = 2f;
                bullet = SnBullets.mirageGunBullet;
            }}
            );
        }};
        illusion = new BerserkUnitType("illusion"){{
            health = 1400;
            speed = 0.7f;
            rotateSpeed = 2.4f;
            drag = 0.125f;
            hitSize = 29f;
            armor = 14f;
            allowLegStep = true;
            hovering = false;
            groundLayer = Layer.legUnit;
            visualElevation = 0.4f;

            legCount = 6;
            legLength = 15;
            legTrns = 0.8f;
            legMoveSpace = 1.4f;
            legBaseOffset = 4f;

            weapons.add(
            new SnWeapon("illusion-gun"){{
                reload = 40;
                x = 8;
                y = -2;
                alternate = false;
                mirror = true;
                inaccuracy = 3f;
                bullet = new BerserkLaserBulletType(){{
                    hitEffect = despawnEffect = Fx.none;
                    instantDisappear = true;
                    damage = 30;
                    shots = 4;
                    shotDelay = 7f;
                    lifetime = 15f;
                    fragBullets = 14;
                    keepVelocity = true;
                    fragBullet = new BasicBulletType(4f, 30f){{
                        lifetime = 13f;
                        keepVelocity = true;
                    }};
                    fragVelocityMin = 0.85f;
                    fragVelocityMax = 1.25f;
                    fragLifeMin = 0.85f;
                    fragLifeMax = 1.25f;
                    fragCone = 6f;
                }};
            }},
            new SnWeapon("iliusion-lasergun"){{
                reload = 60f;
                bullet = new BerserkLaserBulletType(){{
                    damage = 12;
                    lifetime = 30f;
                }};
            }}
            );
        }};
        soothSayer = new BerserkUnitType("soothSayer"){{
            health = 9700;
            speed = 0.57f;
            rotateSpeed = 2.1f;
            drag = 0.125f;
            hitSize = 38f;
            armor = 20f;
            allowLegStep = true;
            hovering = false;
            groundLayer = Layer.legUnit;
            visualElevation = 0.6f;

            legCount = 6;
            legMoveSpace = 1f;
            legPairOffset = 3;
            legLength = 25f;
            legExtension = -14;
            legBaseOffset = 9f;
            landShake = 1f;
            legLengthScl = 0.97f;

            legSplashDamage = 37;
            legSplashRange = 30;
            weapons.add(
            new SnWeapon("soothSayer-lasergun"){{
                reload = 75f;
                x = 12;
                y = -3;
                alternate = false;
                mirror = true;
                inaccuracy = 3f;
                bullet = new BerserkLaserBulletType(){{
                    hitEffect = despawnEffect = Fx.none;
                    instantDisappear = true;
                    damage = 120;
                    shots = 6;
                    shotDelay = 12f;
                    lifetime = 30f;
                    fragBullets = 8;
                    keepVelocity = true;
                    fragBullet = new BasicBulletType(4f, 50f){{
                        lifetime = 13f;
                        keepVelocity = true;
                    }};
                    fragVelocityMin = 0.85f;
                    fragVelocityMax = 1.25f;
                    fragLifeMin = 0.85f;
                    fragLifeMax = 1.25f;
                    fragCone = 6f;
                    }
                };
            }},
            new SnWeapon("soothSayer-gun"){{
                reload = 40f;
                x = 12f;
                y = 4f;
                bullet = new BerserkLaserBulletType(){{
                    lifetime = 30;
                    damage = 4f;
                }};
            }}
            );
        }};
        seer = new BerserkUnitType("seer"){{
            health = 25900;
            speed = 0.53f;
            rotateSpeed = 1.8f;
            drag = 0.125f;
            hitSize = 47f;
            armor = 29f;
            allowLegStep = true;
            hovering = true;
            groundLayer = Layer.legUnit;
            visualElevation = 0.96f;

            legCount = 8;
            legMoveSpace = 0.8f;
            legPairOffset = 3;
            legLength = 60f;
            legExtension = -19;
            legBaseOffset = 8f;
            landShake = 1f;
            legLengthScl = 0.93f;
            rippleScale = 3f;
            legSpeed = 0.19f;

            legSplashDamage = 45;
            legSplashRange = 40;
        }};
        abyssEye = new BerserkUnitType("abyssEye"){{
            health = 70000;
            speed = 0.45f;
            rotateSpeed = 1.5f;
            drag = 0.125f;
            hitSize = 71;
            armor = 30f;
            allowLegStep = true;
            hovering = true;
            groundLayer = Layer.legUnit;
            visualElevation = 1.1f;

            legCount = 8;
            legMoveSpace = 0.9f;
            legPairOffset = 3;
            legLength = 70f;
            legExtension = -27;
            legBaseOffset = 4f;
            landShake = 4f;
            legLengthScl = 2f;
            rippleScale = 4f;
            legSpeed = 0.3f;

            legSplashDamage = 70;
            legSplashRange = 70;
        }};
        //endregion berserk
        //region wheel
        wheelT1 = new WheelUnitType("wheel-t1"){{
            health = 80;
            speed = 3.5f;
            rotateSpeed = baseRotateSpeed = 2.75f;
            drag = 0.075f;
            weapons.add(new WeaponExt("wheel1-minigun"){{
                reload = 5.5f;
                inaccuracy = 4f;
                rotate = true;
                mirror = false;
                bullet = SnBullets.wheel1Bullet;
                x = y = 0;
            }});
        }};
        wheelT2 = new WheelUnitType("wheel-t2"){{
            health = 420;
            speed = 3.4f;
            rotateSpeed = baseRotateSpeed = 2.66f;
            drag = 0.075f;
            weapons.add(new WeaponExt("wheel2-shotgun"){{
                reload = 48f;
                inaccuracy = 0f;
                rotate = true;
                mirror = false;
                bullet = SnBullets.wheel2Shotgun;
                x = y = 0;
            }});
        }};
        wheelT3 = new WheelUnitType("wheel-t3"){{
            health = 890;
            speed = 3.2f;
            rotateSpeed = baseRotateSpeed = 2.33f;
            drag = 0.075f;
            weapons.add(new WeaponExt("wheel3-burst"){{
                reload = 65f;
                inaccuracy = 1f;
                rotate = true;
                mirror = false;
                bullet = SnBullets.wheel3Burst;
                shootSound = Sounds.shootBig;
                x = y = 0;
            }});
        }};
        wheelT4 = new WheelUnitType("wheel-t4"){{
            health = 6800;
            speed = 3.1f;
            rotateSpeed = baseRotateSpeed = 2.25f;
            drag = 0.075f;
            weapons.add(new WeaponExt("wheel4-shotgun"){{
                reload = 92f;
                rotate = true;
                mirror = false;
                bullet = SnBullets.wheel4Shotgun;
                shootSound = Sounds.shootBig;
                x = y = 0;
            }});
            weapons.add(new WeaponExt("wheel4-burst"){{
                reload = 56f;
                inaccuracy = 3f;
                alternate = true;
                rotate = true;
                bullet = SnBullets.wheel4Artillery;
                shootSound = Sounds.shootBig;
                y = -6;
                x = -3;
            }});
        }};
        wheelT5 = new WheelUnitType("wheel-t5"){{
            health = 19400;
            speed = 3f;
            rotateSpeed = baseRotateSpeed = 2f;
            drag = 0.075f;
            weapons.add(new WeaponExt("wheel5-flame"){{
                reload = 7f;
                rotate = true;
                mirror = false;
                bullet = SnBullets.wheel5Flame;
                shootSound = Sounds.flame;
                x = y = 0;
            }});
            weapons.add(new WeaponExt("wheel5-bullet"){{
                reload = 149f;
                inaccuracy = 1f;
                alternate = true;
                rotate = true;
                rotateShooting = true;
                shots = 9;
                shotDelay = 5f;
                bullet = SnBullets.wheel5Bullet;
                shootSound = Sounds.shootBig;
                y = -12;
                x = -6;
            }});
        }};
        //there should be a wheelT6 here
        //endregion wheel
        //region freezing
        freezingT1 = new UnitTypeExt("freezing-t1"){{
            defaultController = SuicideAI::new;
            constructor = UnitTypes.pulsar.constructor;
            speed = 1f;
            hitSize = 8f;
            health = 500;
            mechSideSway = 0.25f;
            range = 40f;

            immunities.add(StatusEffects.freezing);

            weapons.add(new SnWeapon(){{
                reload = 20f;
                shootCone = 180f;
                ejectEffect = Fx.freezing;
                shootSound = Sounds.explosion;
                x = shootY = 0f;
                mirror = false;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    status = StatusEffects.freezing;
                    splashDamageRadius = 90f;
                    instantDisappear = true;
                    splashDamage = 12f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};
        //endregion freezing
        //endregion ground
        //region air
        //region copters
        wind = new CopterUnitType("wind"){{
            health = 140;
            hitSize = 15;
            speed = 3.2f;
            rotateSpeed = 5f;
            accel = 0.04f;
            drag = 0.016f;
            commandLimit = 3;
            flying = true;
            circleTarget = false;
            range = 130;

            unitFallRotateSpeed = 6f;
            smokeFx = Fx.fallSmoke;
            burningFx = Fx.burning;


            rotors.add(
            new Rotor("rotor-small"){{
                offsetX = 0;
                offsetY = 2;
                rotorRotateSpeed = -27f;
                rotorCount = 2;
            }},
            new Rotor("rotor-small"){{
                offsetX = 0;
                offsetY = 2;
                rotorRotateSpeed = 27f;
                rotorCount = 2;
            }}
            );

            weapons.add(
            new WeaponExt("small-minigun"){{
                rotate = false;
                mirror = true;
                x = 5f;
                y = 2f;
                shots = 1;
                inaccuracy = 1;
                reload = 5.5f;
                shootSound = Sounds.pew;
                bullet = SnBullets.basicHelicopterGun;
            }});
        }};
        thunder = new CopterUnitType("thunder"){{
            health = 310;
            hitSize = 20;
            speed = 2.9f;
            rotateSpeed = 4.5f;
            accel = 0.04f;
            drag = 0.016f;
            commandLimit = 3;

            flying = true;
            circleTarget = false;
            range = 145;

            unitFallRotateSpeed = 5.7f;
            smokeFx = Fx.fallSmoke;
            burningFx = Fx.burning;

            rotors.add(
            new Rotor("rotor-medium"){{
                offsetX = 0;
                offsetY = 2;
                rotorRotateSpeed = -27f;
                rotorCount = 2;
            }},
            new Rotor("rotor-medium"){{
                offsetX = 0;
                offsetY = 2;
                rotorRotateSpeed = 27f;
                rotorCount = 2;
            }});

            weapons.add(
            new WeaponExt("missile-mount-small"){{
                rotate = false;
                mirror = true;
                x = 3f;
                y = -2f;
                spacing = 3f;
                reload = 28f;
                shake = 1f;
                recoil = 3f;
                inaccuracy = 5f;
                velocityRnd = 0.2f;
                shots = 1;
                shootSound = Sounds.missile;
                bullet = SnBullets.helicopterMissile;
            }},

            new WeaponExt("medium-minigun"){{
                rotate = false;
                mirror = true;
                x = -6f;
                y = 5f;
                shots = 1;
                inaccuracy = 1;
                reload = 7f;
                shootSound = Sounds.pew;
                bullet = SnBullets.mediumHelicopterGun;
            }});
        }};
        nadir = new CopterUnitType("nadir"){{
            health = 690;
            hitSize = 30;
            speed = 2.6f;
            rotateSpeed = 3.9f;
            accel = 0.08f;
            drag = 0.03f;
            commandLimit = 4;
            flying = true;
            circleTarget = false;
            range = 145;

            unitFallRotateSpeed = 5f;
            smokeFx = Fx.fallSmoke;
            burningFx = Fx.burning;

            rotors.add(
            new Rotor("rotor-mini"){{
                offsetX = 0;
                offsetY = -9;
                rotorRotateSpeed = -27f;
                rotorCount = 3;
            }},
            new Rotor("rotor-big"){{
                offsetX = 0;
                offsetY = 9;
                rotorRotateSpeed = 28f;
                rotorCount = 3;
            }}
            );
            weapons.add(
            new WeaponExt("missile-launch"){{
                rotate = false;
                mirror = true;
                x = 9f;
                y = 3f;
                spacing = 3f;
                reload = 40f;
                shake = 1f;
                recoil = 5f;
                inaccuracy = 5f;
                velocityRnd = 0.2f;
                shots = 1;
                shootSound = Sounds.missile;
                bullet = SnBullets.clusterRocket;
            }},

            new WeaponExt("small-energy-shpere-generator"){{
                rotate = false;
                mirror = false;
                x = 0f;
                y = 16f;
                layerOffset = -0.01f;
                top = false;
                reload = 25f;
                shake = 1f;
                recoil = 2f;
                inaccuracy = 3f;
                shots = 1;
                shootSound = Sounds.spark;
                bullet = SnBullets.copterEnergySphere;
            }});
        }};
        halo = new CopterUnitType("copter-t4"){{
            health = 6900;
            hitSize = 40;
            speed = 2.3f;
            rotateSpeed = 3.3f;
            accel = 0.06f;
            drag = 0.04f;
            commandLimit = 4;

            flying = true;
            circleTarget = false;
            range = 170f;

            unitFallRotateSpeed = 3.7f;
            smokeFx = Fx.fallSmoke;
            burningFx = Fx.burning;

            rotors.add(
            new Rotor("rotor-small2"){{
                offsetX = 0;
                offsetY = -15;
                rotorRotateSpeed = 27f;
                rotorCount = 3;
            }},
            new Rotor("rotor-medium3"){{
                offsetX = -13;
                offsetY = 10;
                rotorRotateSpeed = -27f;
                rotorCount = 4;
            }},
            new Rotor("rotor-medium3"){{
                offsetX = 13;
                offsetY = 10;
                rotorRotateSpeed = 27f;
                rotorCount = 4;
            }}
            );
            weapons.add(
            new WeaponExt("laser-gun"){{
                rotate = true;
                rotateSpeed = 4.2f;
                mirror = true;
                shake = 2f;
                x = -18f;
                y = -4f;
                reload = 20f;
                shotDelay = 1f;
                shootY = 7f;
                shots = 1;
                inaccuracy = 3f;
                shootSound = Sounds.laser;
                bullet = SnBullets.laserGun;
            }},
            new WeaponExt("big-rocket-launcher"){{
                rotate = false;
                mirror = true;
                x = 17f;
                y = 17f;
                layerOffset = -0.01f;
                top = false;
                spacing = 4;
                reload = 40f;
                recoil = 5f;
                shake = 2f;
                ejectEffect = Fx.casing3;
                inaccuracy = 1.3f;
                shots = 3;
                shootSound = Sounds.bang;
                bullet = SnBullets.bigHelicopterMissile;
            }},
            new WeaponExt("large-salvo"){{
                rotate = false;
                mirror = false;
                shake = 3f;
                x = 0f;
                y = 18f;
                layerOffset = -0.01f;
                top = false;
                shootY = 4f;
                reload = 37f;
                shotDelay = 4f;
                shots = 5;
                inaccuracy = 0.5f;
                shootSound = Sounds.shootBig;
                bullet = SnBullets.bigHelicopterGun;
            }});
        }};
        mudflow = new CopterUnitType("mudflow"){{
            health = 57000;
            hitSize = 90;
            speed = 1.5f;
            rotateSpeed = 2.1f;
            accel = 0.05f;
            drag = 0.04f;
            commandLimit = 6;
            range = 170f;

            flying = true;
            circleTarget = false;

            unitFallRotateSpeed = 2.6f;
            smokeFx = Fx.fallSmoke;
            burningFx = Fx.burning;

            rotors.add(
                    new Rotor("rotor-medium"){{
                        offsetX = 0;
                        offsetY = -35;
                        rotorRotateSpeed = -27f;
                        rotorCount = 6;
                    }},
                    new Rotor("rotor-big"){{
                        offsetX = -30;
                        offsetY = 23;
                        rotorRotateSpeed = -27f;
                        rotorCount = 4;
                    }},
                    new Rotor("rotor-big"){{
                        offsetX = 30;
                        offsetY = 23;
                        rotorRotateSpeed = -27f;
                        rotorCount = 4;
                    }},
                    new Rotor("rotor-gigant"){{
                        offsetX = 0;
                        offsetY = 8;
                        rotorRotateSpeed = 26f;
                        rotorCount = 3;
                    }}
            );

            weapons.add(
                    new WeaponExt("large-shrapnel-gun"){{
                        rotate = false;
                        mirror = true;
                        x = 30f;
                        y = 47f;
                        layerOffset = -0.01f;
                        top = false;
                        reload = 65f;
                        recoil = 7f;
                        shake = 4f;
                        inaccuracy = 1f;
                        ejectEffect = Fx.casing1;
                        shootSound = Sounds.shotgun;
                        bullet = SnBullets.shrapnelCopterGun;
                    }},
                    new WeaponExt("gigant-minigun"){{
                        rotate = true;
                        mirror = false;
                        rotateSpeed = 2.5f;
                        shake = 4f;
                        x = 0f;
                        y = 8f;
                        layerOffset = -0.01f;
                        recoil = 4f;
                        reload = 7f;
                        shots = 1;
                        inaccuracy = 0.5f;
                        shootSound = Sounds.shootBig;
                        bullet = SnBullets.giantHelicopterGun;
                    }},
                    new WeaponExt("gigant-rocket-launcher"){{
                        rotate = false;
                        mirror = true;
                        shake = 3f;
                        x = 45f;
                        y = 40f;
                        recoil = 3f;
                        layerOffset = -0.01f;
                        top = false;
                        reload = 55f;
                        shotDelay = 4f;
                        shots = 2;
                        inaccuracy = 0.5f;
                        shootSound = Sounds.missile;
                        bullet = SnBullets.bigClusterRocket;
                    }},
                    new WeaponExt("medium-energy-sphere-generator"){{
                        rotate = false;
                        mirror = false;
                        shake = 2f;
                        x = 0f;
                        y = 48f;
                        recoil = 4f;
                        layerOffset = -0.01f;
                        top = false;
                        reload = 40f;
                        shotDelay = 1f;
                        shots = 1;
                        inaccuracy = 3f;
                        shootSound = Sounds.spark;
                        bullet = SnBullets.bigCopterEnergySphere;
                    }});
        }};
        parhelion = new CopterUnitType("parhelion"){{
            health = 19700;
            hitSize = 70;
            speed = 2.1f;
            rotateSpeed = 2.6f;
            accel = 0.06f;
            drag = 0.04f;
            commandLimit = 5;
            flying = true;
            circleTarget = false;
            range = 175f;

            unitFallRotateSpeed = 3.1f;
            smokeFx = Fx.fallSmoke;
            burningFx = Fx.burning;
            rotors(
            new Rotor("rotor-medium2"){{
                offsetX = -15;
                offsetY = 20;
                rotorRotateSpeed = -27f;
                rotorCount = 3;
            }},
            new Rotor("rotor-medium2"){{
                offsetX = -15;
                offsetY = 20;
                rotorRotateSpeed = 27f;
                rotorCount = 3;
            }},
            new Rotor("rotor-medium2"){{
                offsetX = 15;
                offsetY = 20;
                rotorRotateSpeed = 27f;
                rotorCount = 3;
            }},
            new Rotor("rotor-medium2"){{
                offsetX = 15;
                offsetY = 20;
                rotorRotateSpeed = -27f;
                rotorCount = 3;
            }},
            new Rotor("rotor-big2"){{
                offsetX = 0;
                offsetY = -14;
                rotorRotateSpeed = -27f;
                rotorCount = 4;
            }},
            new Rotor("rotor-big2"){{
                offsetX = 0;
                offsetY = -14;
                rotorRotateSpeed = 27f;
                rotorCount = 4;
            }}
            );
            weapons.add(
            new WeaponExt("large-rocket-launcher"){{
                rotate = false;
                mirror = true;
                x = 9f;
                y = 4f;
                reload = 50f;
                recoil = 5f;
                shake = 2f;
                ejectEffect = Fx.casing3;
                inaccuracy = 9f;
                shots = 2;
                shotDelay = 12f;
                shootSound = Sounds.bang;
                bullet = SnBullets.largeHelicopterMissile;
            }},
            new SnWeapon("big-salvo"){{
                rotate = false;
                mirror = true;
                shake = 3f;
                x = 20f;
                y = 27f;
                reload = 40f;
                shotDelay = 5f;
                shots = 2;
                inaccuracy = 0.5f;
                shootSound = Sounds.shootBig;
                bullet = SnBullets.largeHelicopterGun;
            }},
            new SnWeapon("big-salvo"){{
                rotate = false;
                mirror = true;
                shake = 3f;
                x = 25f;
                y = 8f;
                reload = 30f;
                shotDelay = 5f;
                shots = 2;
                inaccuracy = 0.5f;
                shootSound = Sounds.shootBig;
                bullet = SnBullets.largeHelicopterGun;
            }},
            new SnWeapon("small-missile-launcher"){{
                rotate = false;
                mirror = false;
                shake = 3f;
                x = 0f;
                y = 35f;
                layerOffset = -0.01f;
                top = false;
                reload = 20f;
                shotDelay = 1f;
                shots = 3;
                inaccuracy = 3f;
                shootSound = Sounds.missile;
                bullet = SnBullets.smallHelicopterMissile;
            }});
        }};
        //endregion copters
        //region buffers
        //there should be a bufferT1 here
        satelite = new UnitTypeExt("satelite"){{
            health = 470;
            hitSize = 17;
            speed = 3f;
            accel = 0.2f;
            drag = 0.15f;

            flying = true;
            circleTarget = false;
            range = 180;

            itemCapacity = 30;
            commandLimit = 6;

            defaultController = HealAI::new;

            constructor = UnitEntity::create;

            weapons.add(new ChainWeapon("satellite"){{
                damageTick = 0.2f;
                healTick = 0.7f;
                alternate = false;
                mirror = false;
                rotate = false;
                x = 0;
                shootCone = 2f;
                range = 180;
            }});
        }};
        planet = new UnitTypeExt("planet"){{
            health = 980;
            hitSize = 23;
            speed = 2.9f;
            accel = 0.25f;
            drag = 0.1f;
            engineSize = 3.5f;

            flying = true;
            circleTarget = false;
            range = 310;

            itemCapacity = 60;
            commandLimit = 6;

            defaultController = HealAI::new;

            constructor = UnitEntity::create;

            weapons.add(new ChainWeapon("planet"){{
                damageTick = 0.7f;
                healTick = 1.8f;
                maxChainLength = 8;
                alternate = false;
                mirror = false;
                rotate = false;
                x = 0;
                shootCone = 2f;
                range = 310;
            }});
        }};
        star = new UnitTypeExt("star"){{
            health = 5800;
            hitSize = 55;
            speed = 2.7f;
            accel = 0.2f;
            drag = 0.1f;

            flying = true;
            circleTarget = false;
            range = 380;

            engineOffset = 18f;
            engineSize = 4f;

            itemCapacity = 100;
            commandLimit = 6;

            defaultController = FlyingWeaponAI::new;

            constructor = UnitEntity::create;

            abilities.add(new StatusFieldAbility(SnStatusEffects.starBuff, StatusEffects.none, 180, 8 * 24));

            weapons.add(new WeaponExt("star-gun"){{
                x = 0;
                y = -12;
                mirror = false;
                rotate = true;
                shootCone = 2f;
                inaccuracy = 0f;
                range = 380;
                reload = 287f;
                recoil = 3.5f;
                bullet = SnBullets.starStunBullet;
            }});
            weapons.add(new ChainWeapon("galaxy-weak"){{
                damageTick = 0f;
                healTick = 2.2f;
                maxChainLength = 3;
                alternate = false;
                mirror = true;
                rotate = true;
                x = 24;
                shootCone = 2f;
                range = 210;
                draw = true;
                laserLayer = Layer.flyingUnit;
            }});
        }};
        galaxy = new UnitTypeExt("galaxy"){{
            health = 20000;
            hitSize = 80;
            speed = 2.4f;
            accel = 0.1f;
            drag = 0.05f;

            flying = true;
            circleTarget = false;
            range = 420;

            engineOffset = 36f;
            engineSize = 7f;

            itemCapacity = 180;
            commandLimit = 6;

            defaultController = FlyingWeaponAI::new;

            constructor = UnitEntity::create;

            abilities.add(new StatusFieldAbility(SnStatusEffects.starBuff, SnStatusEffects.galaxyDebuff, 180, 8 * 24));

            weapons.add(new WeaponExt("galaxy-segment"){{
                mirror = true;
                alternate = true;
                rotate = true;
                x = 18;
                y = -6;
                reload = 117;
                inaccuracy = 0;
                recoil = 7.5f;
                bullet = SnBullets.galaxyKnockbackBullet;
            }});

            weapons.add(new PointDefenseWeapon("galaxy-weak"){{
                mirror = true;
                alternate = true;
                rotate = true;
                x = 36;
                y = -6;
                reload = 3;
                range = 420;
//                damage = 80;
            }});
        }};
        //there should be a bufferT6 here
        //endregion buffers
        //endregion air
        //region naval
        //region yellow
        yellowT1 = new UnitTypeExt("yellow-t1") {{
            speed = 2.1f;
            rotateSpeed = 7;
            drag = 0.5f;
            hitSize = 10;
            accel = 0.7f;
            health = 180;
            range = 160;
            armor = 120;
            faceTarget = false;
            commandLimit = 5;
            commandRadius = 48;
            visualElevation = -1;
            /*weapons.add(
                    new WeaponExt("plankton-mount") {{
                        bullet = SnBullets.plasmaArt;
                        rotate = true;
                        top = true;
                        rotateSpeed = 30;
                        reload = 35;
                        shots = 5;
                        spacing = 15;
                        inaccuracy = 2;
                        x = 3.5f;
                        y = 0;
                        firstShotDelay = 20;
                        shootCone = 3;
                        cooldownTime = 15;
                        ignoreRotation = true;
                        shootSound = Sounds.lasershoot;
                        shootStatus = StatusEffects.shocked;
                    }},
                    new WeaponExt("torpedo-gun") {{
                        bullet = SnBullets.torpedo1;
                        mirror = false;
                        rotate = true;
                        top = false;
                        rotateSpeed = 30;
                        reload = 35;
                        shots = 1;
                        spacing = 15;
                        inaccuracy = 5;
                        x = y = 0;
                        firstShotDelay = 30;
                        shootCone = 6;
                        cooldownTime = 15;
                        ignoreRotation = true;
                        shootSound = Sounds.lasershoot;
                        shootStatus = StatusEffects.shocked;
                    }}
            );*/
            constructor = UnitWaterMove::create;
            immunities.add(StatusEffects.wet);
            immunities.add(StatusEffects.freezing);
        }};
        /*yellowT2 = new UnitTypeExt("yellow-t2") {{
            speed = 2;
            rotateSpeed = 6.9f;
            drag = 0.7f;
            hitSize = 15;
            accel = 0.5f;
            health = 360;
            range = 205;
            armor = 260;
            faceTarget = false;
            commandLimit = 5;
            commandRadius = 48;
            visualElevation = -1;
            weapons.add(
                    new WeaponExt("rocket-launcher") {{
                        bullet = SnBullets.rocketArt;
                        mirror = true;
                        rotate = true;
                        top = true;
                        rotateSpeed = 30;
                        reload = 45;
                        shots = 1;
                        spacing = 1;
                        inaccuracy = 5;
                        x = 5.1f;
                        y = 0;
                        xRand = 5;
                        firstShotDelay = 0;
                        shootCone = 1;
                        cooldownTime = 5;
                        ignoreRotation = true;
                        shootSound = Sounds.missile;
                        shootStatus = StatusEffects.blasted;
                    }},
                    new WeaponExt("torpedo-gun") {{
                        bullet = SnBullets.torpedo2;
                        mirror = false;
                        rotate = true;
                        top = false;
                        rotateSpeed = 30;
                        reload = 35;
                        shots = 1;
                        inaccuracy = 2;
                        x = 0;
                        y = 0;
                        firstShotDelay = 20;
                        shootCone = 6;
                        cooldownTime = 15;
                        ignoreRotation = true;
                        shootSound = Sounds.missile;
                        shootStatus = StatusEffects.disarmed;
                        shootStatusDuration = 30;
                    }}
            );
            constructor = UnitWaterMove::create;
            immunities.add(StatusEffects.wet);
            immunities.add(StatusEffects.freezing);
        }};
        yellowT3 = new UnitTypeExt("yellow-t3") {{
            speed = 1.7f;
            rotateSpeed = 6.9f;
            drag = 0.6f;
            hitSize = 18;
            accel = 1;
            health = 1980;
            range = 350;
            armor = 490;
            faceTarget = false;
            commandLimit = 5;
            commandRadius = 48;
            visualElevation = -1;
            weapons.add(
                    new WeaponExt("laser-gun") {{
                        bullet = SnBullets.laserGun2;
                        mirror = true;
                        rotate = true;
                        top = true;
                        rotateSpeed = 45;
                        reload = 60;
                        shots = 1;
                        spacing = 3;
                        inaccuracy = 6;
                        x = 5.8f;
                        y = 3;
                        firstShotDelay = 20;
                        shootCone = 2.1f;
                        cooldownTime = 5;
                        ignoreRotation = true;
                        shootStatus = StatusEffects.slow;
                        shootStatusDuration = 180;
                    }},
                    new WeaponExt("laser-continuous") {{
                        bullet = SnBullets.laserCGun;
                        mirror = true;
                        rotate = true;
                        top = true;
                        rotateSpeed = 50;
                        reload = 90;
                        shots = 1;
                        spacing = 4.3f;
                        inaccuracy = 7.1f;
                        x = 7;
                        y = -2;
                        firstShotDelay = 0;
                        shootCone = 2.2f;
                        cooldownTime = 5;
                        ignoreRotation = true;
                        shootSound = Sounds.missile;
                        shootStatus = StatusEffects.blasted;
                    }},
                    new WeaponExt("torpedo-gun") {{
                        bullet = SnBullets.torpedo3;
                        mirror = false;
                        rotate = true;
                        top = false;
                        rotateSpeed = 30;
                        reload = 35;
                        shots = 1;
                        inaccuracy = 2;
                        x = y = 0;
                        firstShotDelay = 20;
                        shootCone = 6;
                        cooldownTime = 15;
                        ignoreRotation = true;
                        shootSound = Sounds.missile;
                        shootStatus = StatusEffects.disarmed;
                        shootStatusDuration = 30;
                    }}
            );
            constructor = UnitWaterMove::create;
            immunities.add(StatusEffects.wet);
            immunities.add(StatusEffects.freezing);
        }};
        yellowT4 = new UnitTypeExt("yellow-t4") {{
            speed = 1.5f;
            rotateSpeed = 5.9f;
            drag = 0.75f;
            hitSize = 31;
            accel = 0.7f;
            health = 5400;
            range = 180;
            armor = 900;
            faceTarget = false;
            commandLimit = 5;
            commandRadius = 48;
            visualElevation = -1;
            weapons.add(
                    new WeaponExt("big-rocket-launcher") {{
                        bullet = SnBullets.bigRocketArt;
                        mirror = false;
                        rotate = true;
                        top = true;
                        rotateSpeed = 30;
                        reload = 45;
                        shots = 1;
                        spacing = 1;
                        inaccuracy = 5;
                        x = y = 0;
                        xRand = 5;
                        firstShotDelay = 0;
                        shootCone = 1;
                        cooldownTime = 5;
                        ignoreRotation = true;
                        shootSound = Sounds.missile;
                        shootStatus = StatusEffects.blasted;
                    }},
                    new WeaponExt("machinegun") {{
                        bullet = SnBullets.machineBullet;
                        rotate = true;
                        top = false;
                        rotateSpeed = 30;
                        reload = 35;
                        shots = 1;
                        inaccuracy = 2;
                        x = 9;
                        y = -5.3f;
                        firstShotDelay = 20;
                        shootCone = 6;
                        cooldownTime = 15;
                        ignoreRotation = true;
                        shootSound = Sounds.missile;
                        shootStatus = StatusEffects.disarmed;
                        shootStatusDuration = 30;
                    }},
                    new WeaponExt("torpedo-gun") {{
                        bullet = SnBullets.torpedo4;
                        mirror = true;
                        rotate = true;
                        top = false;
                        rotateSpeed = 30;
                        reload = 35;
                        shots = 1;
                        inaccuracy = 2;
                        x = 5;
                        y = 0;
                        firstShotDelay = 20;
                        shootCone = 6;
                        cooldownTime = 15;
                        ignoreRotation = true;
                        shootSound = Sounds.missile;
                        shootStatus = StatusEffects.disarmed;
                        shootStatusDuration = 30;
                    }}
            );
            constructor = UnitWaterMove::create;
            immunities.add(StatusEffects.wet);
            immunities.add(StatusEffects.freezing);
        }};
        yellowT5 = new UnitTypeExt("yellow-t5") {{
            speed = 1.1f;
            rotateSpeed = 5.4f;
            drag = 1;
            hitSize = 35;
            accel = 0.5f;
            health = 21600;
            range = 160;
            armor = 2000;
            faceTarget = false;
            commandLimit = 5;
            commandRadius = 48;
            visualElevation = -1;
            weapons.add(
                    new WeaponExt("big-machinegun") {{
                        bullet = SnBullets.bigMachineBullet;
                        rotate = true;
                        mirror = false;
                        top = true;
                        rotateSpeed = 30;
                        reload = 35;
                        shots = 1;
                        spacing = 15;
                        inaccuracy = 5;
                        x = 0;
                        y = 3;
                        firstShotDelay = 30;
                        shootCone = 6;
                        cooldownTime = 15;
                        ignoreRotation = true;
                        shootSound = Sounds.lasershoot;
                        shootStatus = StatusEffects.shocked;
                    }},
                    new WeaponExt("heavy-rocket-launcher") {{
                        bullet = SnBullets.heavyRocketArt;
                        rotate = true;
                        top = true;
                        rotateSpeed = 30;
                        reload = 45;
                        shots = 1;
                        spacing = 1;
                        inaccuracy = 5;
                        x = 7;
                        y = 0;
                        xRand = 5;
                        firstShotDelay = 0;
                        shootCone = 1;
                        cooldownTime = 5;
                        ignoreRotation = true;
                        shootSound = Sounds.missile;
                        shootStatus = StatusEffects.blasted;
                    }},
                    new WeaponExt("laser-continuous") {{
                        bullet = SnBullets.bigCLaserGun;
                        mirror = false;
                        rotate = true;
                        top = true;
                        rotateSpeed = 50;
                        reload = 90;
                        shots = 1;
                        spacing = 4.3f;
                        inaccuracy = 7.1f;
                        x = 0;
                        y = -9;
                        firstShotDelay = 0;
                        shootCone = 2.2f;
                        cooldownTime = 5;
                        ignoreRotation = true;
                        shootSound = Sounds.missile;
                        shootStatus = StatusEffects.blasted;
                    }},
                    new WeaponExt("torpedo-gun") {{
                        bullet = SnBullets.torpedo5;
                        mirror = true;
                        rotate = true;
                        top = false;
                        rotateSpeed = 30;
                        reload = 35;
                        shots = 1;
                        inaccuracy = 2;
                        x = 7.3f;
                        y = 0;
                        firstShotDelay = 20;
                        shootCone = 6;
                        cooldownTime = 15;
                        ignoreRotation = true;
                        shootSound = Sounds.missile;
                        shootStatus = StatusEffects.disarmed;
                        shootStatusDuration = 30;
                    }}
            );
            constructor = UnitWaterMove::create;
            immunities.add(StatusEffects.wet);
            immunities.add(StatusEffects.freezing);
        }};*/
        //there should be a yellowT6 here
        //endregion yellow
        //endregion naval
        //region misc
        router = new UnitTypeExt("router"){
            {
                health = 2000000;
                speed = 2.85f;
                hitSize = 16;
                flying = true;
                constructor = UnitEntity::create;
                engineSize = 0;
                drawCell = false;
            }

            @Override
            public boolean isHidden(){
                return true;
            }
        };
        //endregion misc
        //region other
        courier = new UncontrollableUnitType("courier"){{
            speed = 3.9f;
            flying = true;
            itemCapacity = 100;
            health = 50;
            hitSize = 4;
            defaultController = DeliverAI::wrapper;
            //constructor = UnitEntity::create;
        }};
        //comet = new UncontrollableUnitType("comet"){{
        comet = new UnitTypeExt("comet"){{
            health = 150;
            hitSize = 10;
            speed = 3.1f;
            accel = 0.15f;
            drag = 0.1f;

            flying = true;
            circleTarget = false;
            range = 75;

            itemCapacity = 20;
            commandLimit = 4;
            ammoType = new LiquidAmmoType(Liquids.water, 1);

            defaultController = ExtinguishAI::wrapper;

            constructor = UnitEntity::create;

            weapons.add(new WeaponExt("sprite"){{
                ai = new ExtinguishWeaponAI();
                rotate = true;
                mirror = false;
                x = 0;
                top = true;
                inaccuracy = 4;
                alternate = false;
                reload = 2.5f;
                shootSound = Sounds.spray;
                bullet = SnBullets.cometWaterShot;
            }});
        }};
        //region snake
        snake1 = new SegmentUnitType("snake1"){{
            lengthSnake = 5;
            health = 3000f;
            hitSize = 30f;
            speed = 1.4f;
            //commandLimit = 2;
            rotateSpeed = 2f;
            //accel = 0.01f;
            //drag = 0.001f;
            flying = true;
            defaultController = SegmentAI.wrapper(FlyingAI::new);
        }};
        //endregion snake
        //endregion other
    }
}
