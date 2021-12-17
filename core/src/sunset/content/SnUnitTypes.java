package sunset.content;

import mindustry.ai.types.SuicideAI;
import mindustry.annotations.Annotations.EntityDef;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.content.UnitTypes;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.*;
import mindustry.gen.Sounds;
import mindustry.gen.UnitEntity;
import mindustry.gen.UnitWaterMove;
import mindustry.gen.Unitc;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.world.meta.BlockFlag;
import sunset.ai.DeliverAI;
import sunset.ai.ExtinguishAI;
import sunset.ai.FlyingUnitWeaponAI;
import sunset.ai.HealAI;
import sunset.ai.weapon.ExtinguishWeaponAI;
import sunset.type.BerserkStage;
import sunset.entities.abilities.StatusFieldAbility;
import sunset.entities.bullet.BerserkLaserBulletType;
import sunset.gen.Deliverc;
import sunset.type.blocks.Rotor;
import sunset.type.unitTypes.BerserkUnitType;
import sunset.type.unitTypes.CopterUnitType;
import sunset.type.unitTypes.UnitTypeExt;
import sunset.type.unitTypes.WheelUnitType;
import sunset.type.weapons.ChainWeapon;
import sunset.type.weapons.PointDefenseWeapon;
import sunset.type.weapons.SnWeapon;
import sunset.type.weapons.WeaponExt;

public class SnUnitTypes implements ContentList {
    public static UnitType
    //attack copters
    wind, thunder, nadir, halo, parhelion, mudflow, testcopter,
    //buffers
    comet, satelite, planet, star, galaxy,
    //berserk
    mirage, vision, illusion, soothSayer, seer, abyssEye,
    //wheel
    wheel1, wheel2, wheel3, wheel4, wheel5,
    //torpedo
    torpedo1, torpedo2,
    //misc
    router,
    //freezing
    snowflake;
    @EntityDef({Unitc.class, Deliverc.class})
    public static UnitType courier;

    @Override
    public void load() {
        //region attack copters
        wind = new CopterUnitType("wind") {{
            health = 140;
            hitSize = 15;
            speed = 3.2f;
            rotateSpeed = 5.4f;
            accel = 0.1f;
            drag = 0.02f;
            commandLimit = 3;
            targetFlags = new BlockFlag[]{BlockFlag.battery, BlockFlag.generator, BlockFlag.reactor, null};
            
            flying = true;
            circleTarget = false;
            range = 130;
            unitFallRotateSpeed = 6f;

            rotors.add(
                    new Rotor("rotor-small") {{
                        offsetX = 0;
                        offsetY = 2;
                        rotorRotateSpeed = -27f;
                        rotorCount = 2;
                    }},


                    new Rotor("rotor-small") {{
                        offsetX = 0;
                        offsetY = 2;
                        rotorRotateSpeed = 27f;
                        rotorCount = 2;
                    }});
            weapons.add(
                    new WeaponExt("small-minigun") {{
                        rotate = false;
                        mirror = true;
                        top = true;
                        x = 4f;
                        y = 2f;
                        shots = 1;
                        inaccuracy = 1;
                        reload = 5.5f;
                        shootSound = Sounds.pew;
                        bullet = SnBullets.basicHelicopterGun;
                    }});
        }};
        thunder = new CopterUnitType("thunder") {{
            health = 310;
            hitSize = 20;
            speed = 2.9f;
            rotateSpeed = 5f;
            accel = 0.1f;
            drag = 0.02f;
            commandLimit = 3;
            targetFlags = new BlockFlag[]{BlockFlag.battery, BlockFlag.generator, BlockFlag.reactor, null};

            flying = true;
            circleTarget = false;
            range = 145;
            unitFallRotateSpeed = 5f;

            rotors.add(
                new Rotor("rotor-medium") {{
                    offsetX = 0;
                    offsetY = 2;
                    rotorRotateSpeed = -27f;
                    rotorCount = 2;
                }},


                new Rotor("rotor-medium") {{
                    offsetX = 0;
                    offsetY = 2;
                    rotorRotateSpeed = 27f;
                    rotorCount = 2;
                }});
            weapons.add(
                    new WeaponExt("missile-mount-small") {{
                        rotate = false;
                        mirror = true;
                        top = true;
                        x = 3f;
                        y = -2f;
                        spacing = 3f;
                        reload = 30f;
                        shake = 1f;
                        recoil = 3f;
                        inaccuracy = 5f;
                        velocityRnd = 0.2f;
                        shots = 1;
                        shootSound = Sounds.missile;
                        bullet = SnBullets.helicopterMissile;
                    }},

                    new WeaponExt("medium-minigun") {{
                        rotate = false;
                        mirror = true;
                        top = true;
                        x = -4f;
                        y = 4f;
                        shots = 1;
                        inaccuracy = 1;
                        reload = 8f;
                        shootSound = Sounds.pew;
                        bullet = SnBullets.mediumHelicopterGun;
                    }});
        }};
        nadir = new CopterUnitType("nadir") {{
            health = 650;
            hitSize = 30;
            speed = 2.6f;
            rotateSpeed = 4.6f;
            accel = 0.1f;
            drag = 0.02f;
            commandLimit = 4;
            targetFlags = new BlockFlag[]{BlockFlag.battery, BlockFlag.generator, BlockFlag.reactor, null};

            flying = true;
            circleTarget = false;
            range = 145;

            unitFallRotateSpeed = 5f;

            rotors.add(
                    new Rotor("rotor-big") {{
                        offsetX = 0;
                        offsetY = 8;
                        rotorRotateSpeed = 27f;
                        rotorCount = 3;
                    }}
            );

            rotors.add(
                    new Rotor("rotor-small") {{
                        offsetX = 0;
                        offsetY = -9;
                        rotorRotateSpeed = 28f;
                        rotorCount = 1;
                    }}
            );
            weapons.add(
                    new WeaponExt("missile-launch") {{
                        rotate = false;
                        mirror = true;
                        top = true;
                        x = -8f;
                        y = 6f;
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

                    new SnWeapon("wind-gun") {{
                        rotate = false;
                        mirror = true;
                        top = true;
                        x = 3f;
                        y = 0f;
                        reload = 25f;
                        shake = 1f;
                        recoil = 2f;
                        inaccuracy = 3f;
                         shots = 1;
                        shootSound = Sounds.spark;
                        bullet = SnBullets.copterEnergySphere;
                    }});
        }};
        halo = new CopterUnitType("halo") {{
            health = 6700;
            hitSize = 40;
            speed = 2.3f;
            rotateSpeed = 4.1f;
            accel = 0.1f;
            drag = 0.02f;
            commandLimit = 4;
            targetFlags = new BlockFlag[]{BlockFlag.battery, BlockFlag.generator, BlockFlag.reactor, null};

            flying = true;
            circleTarget = false;
            range = 170f;
            unitFallRotateSpeed = 5f;
            weapons.add(
                    new WeaponExt("") {{
                        rotate = false;
                        mirror = true;
                        x = 13f;
                        y = -5f;
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
                    new SnWeapon("") {{
                        rotate = false;
                        mirror = false;
                        shake = 3f;
                        x = 0f;
                        y = 14f;
                        reload = 30f;
                        shotDelay = 4f;
                        shots = 3;
                        inaccuracy = 0.5f;
                        shootSound = Sounds.shootBig;
                        bullet = SnBullets.bigHelicopterGun;
                    }},
                    new SnWeapon("") {{
                        rotate = false;
                        mirror = true;
                        shake = 2f;
                        x = 14f;
                        y = 4f;
                        reload = 20f;
                        shotDelay = 1f;
                        shots = 1;
                        inaccuracy = 3f;
                        shootSound = Sounds.laser;
                        bullet = SnBullets.laserGun;
                    }});
        }};
        parhelion = new CopterUnitType("parhelion") {{
            health = 18800;
            hitSize = 57;
            speed = 2.1f;
            rotateSpeed = 3.6f;
            accel = 0.1f;
            drag = 0.02f;
            commandLimit = 5;
            targetFlags = new BlockFlag[]{BlockFlag.battery, BlockFlag.generator, BlockFlag.reactor, null};

            flying = true;
            circleTarget = false;
            range = 175f;

            unitFallRotateSpeed = 5f;
            weapons.add(
                    new WeaponExt("") {{
                        rotate = false;
                        mirror = true;
                        x = 17f;
                        y = 6f;
                        spacing = 5;
                        reload = 50f;
                        recoil = 5f;
                        shake = 2f;
                        ejectEffect = Fx.casing3;
                        inaccuracy = 4f;
                        shots = 3;
                        shootSound = Sounds.bang;
                        bullet = SnBullets.largeHelicopterMissile;
                    }},
                    new SnWeapon("") {{
                        rotate = false;
                        mirror = false;
                        shake = 3f;
                        x = 10f;
                        y = 10f;
                        reload = 40f;
                        shotDelay = 4f;
                        shots = 2;
                        inaccuracy = 0.5f;
                        shootSound = Sounds.shootBig;
                        bullet = SnBullets.largeHelicopterGun;
                    }},
                    new SnWeapon("") {{
                        rotate = false;
                        mirror = true;
                        shake = 2f;
                        x = 9f;
                        y = 7f;
                        reload = 20f;
                        shotDelay = 1f;
                        shots = 3;
                        inaccuracy = 3f;
                        shootSound = Sounds.laser;
                        bullet = SnBullets.smallHelicopterMissiles;
                    }});
        }};
        
        mudflow = new CopterUnitType("mudflow") {{
            health = 48500;
            hitSize = 78;
            speed = 1.7f;
            rotateSpeed = 3f;
            accel = 0.1f;
            drag = 0.02f;
            commandLimit = 4;
            targetFlags = new BlockFlag[]{BlockFlag.battery, BlockFlag.generator, BlockFlag.reactor, null};
            range = 170f;

            flying = true;
            circleTarget = false;

            unitFallRotateSpeed = 5f;
            weapons.add(
                    new WeaponExt("") {{
                        rotate = false;
                        mirror = true;
                        x = 24f;
                        y = 3f;
                        reload = 55f;
                        recoil = 7f;
                        shake = 4f;
                        inaccuracy = 1f;
                        ejectEffect = Fx.casing1;
                        shootSound = Sounds.shotgun;
                        bullet = SnBullets.shrapnelCopterGun;
                    }},
                    new SnWeapon("") {{
                        rotate = false;
                        mirror = false;
                        shake = 3f;
                        x = 17f;
                        y = 14f;
                        reload = 8f;
                        shots = 1;
                        inaccuracy = 0.5f;
                        shootSound = Sounds.shootBig;
                        bullet = SnBullets.gigantHelicopterGun;
                    }},
                    new SnWeapon("") {{
                        rotate = false;
                        mirror = false;
                        shake = 3f;
                        x = 20f;
                        y = 10f;
                        reload = 60f;
                        shotDelay = 4f;
                        shots = 2;
                        inaccuracy = 0.5f;
                        shootSound = Sounds.missile;
                        bullet = SnBullets.bigClusterRocket;
                    }},
                    new SnWeapon("") {{
                        rotate = false;
                        mirror = true;
                        shake = 2f;
                        x = 4f;
                        y = 16f;
                        reload = 40f;
                        shotDelay = 1f;
                        shots = 1;
                        inaccuracy = 3f;
                        shootSound = Sounds.spark;
                        bullet = SnBullets.bigCopterEnergySphere;
                    }});
        }};
        testcopter = new CopterUnitType("test-copter") {{
            health = 200;
            hitSize = 20;
            speed = 2.1f;
            accel = 0.1f;
            drag = 0.02f;
            commandLimit = 3;
            targetFlags = new BlockFlag[]{BlockFlag.battery, BlockFlag.generator, BlockFlag.reactor, null};

            flying = true;
            circleTarget = false;

            unitFallRotateSpeed = 4f;
        }};
        //endregion attack copters
        //region buffers
        comet = new UnitTypeExt("comet") {{
            health = 150;
            hitSize = 12;
            speed = 3.1f;
            accel = 0.15f;
            drag = 0.1f;

            flying = true;
            circleTarget = false;
            range = 75;

            itemCapacity = 20;
            commandLimit = 4;

            defaultController = ExtinguishAI::new;

            constructor = UnitEntity::create;

            weapons.add(new WeaponExt("sprite") {{
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
        satelite = new UnitTypeExt("satellite") {{
            health = 470;
            hitSize = 20;
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

            weapons.add(new ChainWeapon("satellite") {{
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
        planet = new UnitTypeExt("planet") {{
            health = 980;
            hitSize = 26;
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

            weapons.add(new ChainWeapon("planet") {{
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
        star = new UnitTypeExt("star") {{
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

            defaultController = FlyingUnitWeaponAI::new;

            constructor = UnitEntity::create;

            abilities.add(new StatusFieldAbility(SnStatusEffects.starBuff, StatusEffects.none, 180, 8 * 24));

            weapons.add(new WeaponExt("star-gun") {{
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
            weapons.add(new ChainWeapon("galaxy-weak") {{
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
        galaxy = new UnitTypeExt("galaxy") {{
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

            defaultController = FlyingUnitWeaponAI::new;

            constructor = UnitEntity::create;

            abilities.add(new StatusFieldAbility(SnStatusEffects.starBuff, SnStatusEffects.galaxyDebuff, 180, 8 * 24));

            weapons.add(new WeaponExt("galaxy-segment") {{
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

            weapons.add(new PointDefenseWeapon("galaxy-weak") {{
                mirror = true;
                alternate = true;
                rotate = true;
                x = 36;
                y = -6;
                reload = 3;
                range = 420;
                damage = 80;
            }});
        }};
        //endregion buffers
        //region berserk
        mirage = new BerserkUnitType("mirage") {{
            health = 320;
            speed = 1f;
            rotateSpeed = 3f;
            drag = 0.1f;
            armor = 4f;
            hitSize = 11f;
            allowLegStep = true;
            hovering = false;
            groundLayer = Layer.legUnit;
            visualElevation = 0.15f;

            legCount = 4;
            legLength = 7f;
            legTrns = 0.5f;
            legMoveSpace = 1.3f;

            weapons.add(new SnWeapon("mirage-gun") {{
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
                    new BerserkStage() {{
                        healthMaximum = 0.4f;
                        bulletWidthMultiplier = 2f;
                        effect = StatusEffects.overclock;
                    }},
                    new BerserkStage() {{
                        healthMaximum = 0.15f;
                        bulletWidthMultiplier = 3f;
                        effect = StatusEffects.burning;
                    }}
            );
        }};
        vision = new BerserkUnitType("vision") {{
            health = 980;
            speed = 0.8f;
            rotateSpeed = 2f;
            drag = 0.125f;
            armor = 5f;
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
                    new SnWeapon("vision-gun") {{
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
        illusion = new BerserkUnitType("illusion") {{
            health = 1400;
            speed = 1.1f;
            rotateSpeed = 2.4f;
            drag = 0.125f;
            hitSize = 29f;
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
                    new SnWeapon("illusion-gun") {{
                        reload = 40;
                        x = 8;
                        y = -2;
                        alternate = false;
                        mirror = true;
                        inaccuracy = 3f;
                        bullet = new BerserkLaserBulletType() {{
                            hitEffect = despawnEffect = Fx.none;
                            instantDisappear = true;
                            damage = 30;
                            shots = 4;
                            shotDelay = 7f;
                            lifetime = 15f;
                            fragBullets = 14;
                            keepVelocity = true;
                            fragBullet = new BasicBulletType(4f, 30f) {{
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
                    new SnWeapon("iliusion-lasergun") {{
                        reload = 60f;
                        bullet = new BerserkLaserBulletType() {{
                            damage = 12;
                            lifetime = 30f;
                        }};
                    }}
            );
        }};
        soothSayer = new BerserkUnitType("soothSayer") {{
            health = 9700;
            speed = 1.3f;
            rotateSpeed = 2.1f;
            drag = 0.125f;
            allowLegStep = true;
            hovering = false;
            hitSize = 38f;
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
                    new SnWeapon("soothSayer-lasergun") {
                        {
                            reload = 75f;
                            x = 12;
                            y = -3;
                            alternate = false;
                            mirror = true;
                            inaccuracy = 3f;
                            bullet = new BerserkLaserBulletType() {
                                {
                                    hitEffect = despawnEffect = Fx.none;
                                    instantDisappear = true;
                                    damage = 120;
                                    shots = 6;
                                    shotDelay = 12f;
                                    lifetime = 30f;
                                    fragBullets = 8;
                                    keepVelocity = true;
                                    fragBullet = new BasicBulletType(4f, 50f) {{
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
                        }
                    },
                    new SnWeapon("soothSayer-gun") {{
                        reload = 40f;
                        x = 12f;
                        y = 4f;
                        bullet = new BerserkLaserBulletType() {{
                            lifetime = 30;
                            damage = 4f;
                        }};
                    }}
            );
        }};
        seer = new BerserkUnitType("seer") {{
            health = 25900;
            speed = 0.97f;
            rotateSpeed = 1.8f;
            drag = 0.125f;
            hitSize = 47f;
            armor = 12f;
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
        abyssEye = new BerserkUnitType("abyssEye") {{
            health = 70000;
            speed = 0.75f;
            rotateSpeed = 1.5f;
            drag = 0.125f;
            hitSize = 71;
            armor = 16f;
            allowLegStep = true;
            hovering = true;
            groundLayer = Layer.legUnit;
            visualElevation = 1.1f;

            legCount = 8;
            legMoveSpace = 1f;
            legPairOffset = 4;
            legLength = 60f;
            legExtension = -19;
            legBaseOffset = 10f;
            landShake = 1f;
            legLengthScl = 1f;
            rippleScale = 3f;
            legSpeed = 0.2f;

            legSplashDamage = 59;
            legSplashRange = 70;
        }};
        //endregion berserk
        //region wheel
        wheel1 = new WheelUnitType("wheel-t1") {{
            health = 80;
            speed = 3.5f;
            rotateSpeed = baseRotateSpeed = 2.75f;
            drag = 0.075f;
            weapons.add(new WeaponExt("wheel1-minigun") {{
                reload = 5.5f;
                inaccuracy = 4f;
                rotate = true;
                mirror = false;
                bullet = SnBullets.wheel1Bullet;
                x = y = 0;
            }});
        }};
        wheel2 = new WheelUnitType("wheel-t2") {{
            health = 420;
            speed = 3.4f;
            rotateSpeed = baseRotateSpeed = 2.66f;
            drag = 0.075f;
            weapons.add(new WeaponExt("wheel2-shotgun") {{
                reload = 48f;
                inaccuracy = 0f;
                rotate = true;
                mirror = false;
                bullet = SnBullets.wheel2Shotgun;
                x = y = 0;
            }});
        }};
        wheel3 = new WheelUnitType("wheel-t3") {{
            health = 890;
            speed = 3.2f;
            rotateSpeed = baseRotateSpeed = 2.33f;
            drag = 0.075f;
            weapons.add(new WeaponExt("wheel3-burst") {{
                reload = 65f;
                inaccuracy = 1f;
                rotate = true;
                mirror = false;
                bullet = SnBullets.wheel3Burst;
                shootSound = Sounds.shootBig;
                x = y = 0;
            }});
        }};
        wheel4 = new WheelUnitType("wheel-t4") {{
            health = 6800;
            speed = 3.1f;
            rotateSpeed = baseRotateSpeed = 2.25f;
            drag = 0.075f;
            weapons.add(new WeaponExt("wheel4-shotgun") {{
                reload = 92f;
                rotate = true;
                mirror = false;
                bullet = SnBullets.wheel4Shotgun;
                shootSound = Sounds.shootBig;
                x = y = 0;
            }});
            weapons.add(new WeaponExt("wheel4-burst") {{
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
        wheel5 = new WheelUnitType("wheel-t5") {{
            health = 19400;
            speed = 3f;
            rotateSpeed = baseRotateSpeed = 2f;
            drag = 0.075f;
            weapons.add(new WeaponExt("wheel5-flame") {{
                reload = 7f;
                rotate = true;
                mirror = false;
                bullet = SnBullets.wheel5Flame;
                shootSound = Sounds.flame;
                x = y = 0;
            }});
            weapons.add(new WeaponExt("wheel5-bullet") {{
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
        //endregion wheel
        //region torpedo
        torpedo1 = new UnitType("torpedo-t1") {{
            speed = 2.1f;
            boostMultiplier = 0.55f;
            rotateSpeed = 6.3f;
            baseRotateSpeed = 4;
            drag = 0.5f;
            hitSize = 15;
            accel = 0.7f;
            health = 180;
            range = 160;
            armor = 120;
            faceTarget = false;
            commandLimit = 5;
            commandRadius = 48;
            visualElevation = -1;
            weapons.add(
                    new WeaponExt("plasma-gun") {{
                        bullet = new ArtilleryBulletType() {{
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
                        rotate = true;
                        top = true;
                        rotateSpeed = 30;
                        reload = 35;
                        shots = 5;
                        spacing = 15;
                        inaccuracy = 2;
                        firstShotDelay = 20;
                        shootCone = 3;
                        cooldownTime = 15;
                        ignoreRotation = true;
                        shootSound = Sounds.lasershoot;
                        shootStatus = StatusEffects.blasted;
                    }},
                    new WeaponExt("torpedo-gun") {{
                        bullet = new BasicBulletType(2, 120) {{
                            lifetime = 80;
                            drawSize = 9.2f;
                            pierceCap = -1;
                            inaccuracy = 1;
                            ammoMultiplier = 1;
                            reloadMultiplier = 3;
                            buildingDamageMultiplier = 0.9f;
                            recoil = 0;
                            pierce = true;
                            pierceBuilding = false;
                            shootEffect = smokeEffect = Fx.none;
                            collidesAir = absorbable = false;
                            keepVelocity = true;
                            trailColor = Pal.lightTrail;
                            collideFloor = true;
                            layer = Layer.scorch;
                            splashDamage = 40;
                            splashDamageRadius = 80;
                        }};
                        rotate = true;
                        rotateSpeed = 30;
                        reload = 35;
                        shots = 1;
                        spacing = 15;
                        inaccuracy = 2;
                        firstShotDelay = 20;
                        shootCone = 6;
                        cooldownTime = 15;
                        ignoreRotation = true;
                        shootSound = Sounds.missile;
                        shootStatus = StatusEffects.disarmed;
                        shootStatusDuration = 15;
                    }}
            );
            constructor = UnitWaterMove::create;
            immunities.add(StatusEffects.wet);
            immunities.add(StatusEffects.freezing);
        }};
        torpedo2 = new UnitType("torpedo-t2") {{
            speed = 2;
            boostMultiplier = 1;
            rotateSpeed = 7;
            baseRotateSpeed = 5;
            drag = 0.7f;
            hitSize = 17;
            accel = 0.5f;
            health = 170;
            range = 205;
            armor = 260;
            faceTarget = false;
            commandLimit = 5;
            commandRadius = 48;
            visualElevation = -1;
            weapons.add(
                    new WeaponExt("rocket-launcher") {{
                        bullet = new ArtilleryBulletType() {{
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
                        mirror = true;
                        rotate = true;
                        top = true;
                        rotateSpeed = 30;
                        reload = 45;
                        shots = 1;
                        spacing = 1;
                        inaccuracy = 5;
                        xRand = 5;
                        firstShotDelay = 0;
                        shootCone = 1;
                        cooldownTime = 5;
                        ignoreRotation = true;
                        shootSound = Sounds.missile;
                        shootStatus = StatusEffects.blasted;
                    }},
                    new WeaponExt("torpedo-gun") {{
                        bullet = new BasicBulletType(2, 140) {{
                            lifetime = 80;
                            drawSize = 9.2f;
                            pierceCap = -1;
                            inaccuracy = 1;
                            ammoMultiplier = 1;
                            reloadMultiplier = 3;
                            buildingDamageMultiplier = 0.9f;
                            recoil = 0;
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
                        rotate = true;
                        rotateSpeed = 30;
                        reload = 35;
                        shots = 1;
                        spacing = 15;
                        inaccuracy = 2;
                        firstShotDelay = 20;
                        shootCone = 6;
                        cooldownTime = 15;
                        ignoreRotation = true;
                        shootSound = Sounds.missile;
                        shootStatus = StatusEffects.disarmed;
                        shootStatusDuration = 15;
                    }}
            );
            constructor = UnitWaterMove::create;
            immunities.add(StatusEffects.wet);
            immunities.add(StatusEffects.freezing);
        }};
        //endregion torpedo
        //region misc
        router = new UnitTypeExt("router") {
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
            public boolean isHidden() {
                return true;
            }
        };
        //endregion misc
        //region freezing
        snowflake = new UnitType("snowflake") {{
            defaultController = SuicideAI::new;
            constructor = UnitTypes.pulsar.constructor;
            speed = 1f;
            hitSize = 8f;
            health = 500;
            mechSideSway = 0.25f;
            range = 40f;

            immunities.add(StatusEffects.freezing);

            weapons.add(new SnWeapon() {{
                reload = 20f;
                shootCone = 180f;
                ejectEffect = Fx.freezing;
                shootSound = Sounds.explosion;
                x = shootY = 0f;
                mirror = false;
                bullet = new BombBulletType(0f, 0f, "clear") {{
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
        //region delivery
        courier = new UnitTypeExt("courier") {{
            speed = 3.9f;
            flying = true;
            itemCapacity = 100;
            health = 50;
            hitSize = 4;
            defaultController = DeliverAI::new;
            //constructor = UnitEntity::create;
        }};
        //endregion delivery
    }
}
