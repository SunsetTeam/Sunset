package sunset.content;

import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.gen.Sounds;
import mindustry.gen.EntityMapping;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.ai.types.SuicideAI;
import mindustry.entities.bullet.BombBulletType;
import sunset.ai.*;
import sunset.entities.abilities.StatusFieldAbility;
import sunset.type.UnitTypeExt;
import sunset.ai.weapon.ExtinguishWeaponAI;
import sunset.type.WheelUnitType;
import sunset.type.weapons.WeaponExt;
import sunset.type.weapons.ChainWeapon;
import sunset.type.CopterUnitType;
import sunset.type.weapons.PointDefenseWeapon;
import sunset.utils.UnitsUtils;

import static mindustry.type.ItemStack.with;
import static sunset.utils.UnitsUtils.addUnitGroup;

public class SnUnitTypes implements ContentList {
    public static UnitType
            guardcopter, bladecopter, swordcopter, guardiancopter, crusadercopter, //attacks copters
            comet, satelite, planet, star, galaxy, //buffers
            wheel1, //wheel
            router, courier, //misc
            snowflake;
    public void loadFactoryRecipes()
    {
        UnitsUtils.init();
        addUnitGroup(Blocks.airFactory, 60*20f, with(Items.silicon, 30, Items.metaglass, 20),
            comet, satelite, planet, star, galaxy);
        addUnitGroup(Blocks.airFactory, 60*15f, with(Items.silicon, 30, Items.graphite, 15),
            guardcopter, bladecopter, swordcopter, guardiancopter, crusadercopter);
    }
    @Override
    public void load() {
        // region copters
        guardcopter = new CopterUnitType("guard_copter") {{
            health = 170;
            hitSize = 27;
            speed = 3.3f;
            accel = 0.1f;
            drag = 0.02f;
            commandLimit = 3;

            flying = true;
            circleTarget = false;
            range = 130;

            offsetY = 2.2f;
            weapons.add(
                    new WeaponExt("sunset-guard-gun") {{
                        rotate = false;
                        mirror = true;
                        top = true;
                        x = 8f;
                        y = 0f;
                        shots = 1;
                        inaccuracy = 1;
                        reload = 5f;
                        shootSound = Sounds.pew;
                        bullet = SnBullets.BasicHelicopterGun;
                    }});
        }};

        bladecopter = new CopterUnitType("blade_copter") {{
            health = 370;
            hitSize = 39;
            speed = 3.0f;
            accel = 0.1f;
            drag = 0.02f;
            commandLimit = 3;

            flying = true;
            circleTarget = false;
            range = 170;

            offsetY = 2.5f;
            weapons.add(
                    new WeaponExt("sunset-blade-gun") {{
                        rotate = false;
                        mirror = true;
                        top = true;
                        x = -10f;
                        y = 4f;
                        shootCone = 20f;
                        spacing = 4f;
                        recoil = 2f;
                        shots = 6;
                        shootCone = 0.01f;
                        inaccuracy = 12;
                        reload = 15f;
                        shootSound = Sounds.shoot;
                        bullet = SnBullets.HelicopterShootgun;
                    }});
        }};

        swordcopter = new CopterUnitType("sword_copter") {{
            health = 630;
            hitSize = 46;
            speed = 2.8f;
            accel = 0.1f;
            drag = 0.02f;
            commandLimit = 4;

            flying = true;
            circleTarget = false;
            range = 210;

            rotorRotateSpeed = 27f;
            offsetY = 2.6f;
            weapons.add(
                    new WeaponExt("sunset-sword-gun") {{
                        rotate = false;
                        mirror = true;
                        top = true;
                        x = -19f;
                        y = 5f;
                        spacing = 3f;
                        reload = 45f;
                        shake = 1f;
                        recoil = 4f;
                        inaccuracy = 5f;
                        velocityRnd = 0.2f;
                        shots = 3;
                        shootSound = Sounds.missile;
                        bullet = SnBullets.HelicopterMissile;
                    }},

                    new Weapon() {{
                        x = 0f;
                        y = 10f;
                        reload = 50f;
                        top = false;
                        minShootVelocity = 0.01f;

                        soundPitchMin = 1f;
                        shootSound = Sounds.plasmadrop;
                        bullet = SnBullets.HelicopterBomb;
                    }});
        }};

        guardiancopter = new CopterUnitType("guardian_copter") {{
            health = 4600;
            hitSize = 63;
            speed = 2.7f;
            accel = 0.1f;
            drag = 0.02f;
            commandLimit = 4;

            flying = true;
            circleTarget = false;

            rotorRotateSpeed = 26f;
            offsetY = 2.6f;
            weapons.add(
                    new WeaponExt("sunset-guardian-gun") {{
                        rotate = false;
                        mirror = true;
                        top = true;
                        x = -20f;
                        y = 4f;
                        shotDelay = 5f;
                        reload = 50f;
                        recoil = 5f;
                        shake = 2f;
                        ejectEffect = Fx.casing3;

                        inaccuracy = 0.3f;
                        velocityRnd = 0.2f;
                        shots = 4;
                        shootSound = Sounds.bang;
                        bullet = SnBullets.bigHelicopterGun;
                    }},
                    new Weapon() {{
                        rotate = false;
                        mirror = false;
                        shake = 3f;
                        x = 0f;
                        y = 10f;
                        reload = 120f;
                        top = false;
                        inaccuracy = 1f;
                        velocityRnd = 0.2f;
                        shootSound = Sounds.missile;
                        bullet = SnBullets.HelicopterRocket;
                    }});
        }};

        crusadercopter = new CopterUnitType("crusader_copter") {{
            health = 16500;
            hitSize = 77;
            speed = 2.6f;
            accel = 0.1f;
            drag = 0.02f;
            commandLimit = 3;

            flying = true;
            circleTarget = false;

            rotorRotateSpeed = 25f;
            offsetY = 2.6f;
            weapons.add(
                    new WeaponExt("sunset-crusader-gun") {{
                        rotate = false;
                        mirror = true;
                        top = true;
                        x = -33f;
                        y = 4f;
                        shotDelay = 5f;
                        reload = 55f;
                        recoil = 6f;
                        shake = 4f;
                        ejectEffect = Fx.casing3;

                        inaccuracy = 3f;
                        velocityRnd = 0.2f;
                        shots = 2;
                        shootSound = Sounds.bang;
                        bullet = SnBullets.largeHelicopterGun;
                    }},

                    new WeaponExt("sunset-crusader-rocket") {{
                        rotate = false;
                        mirror = true;
                        top = true;
                        x = -10f;
                        y = -10f;
                        reload = 31f;
                        recoil = 1f;
                        shotDelay = 1f;
                        shake = 1f;
                        ejectEffect = Fx.casing3;

                        shots = 3;
                        xRand = 5f;
                        inaccuracy = 10f;
                        velocityRnd = 0.2f;
                        shootSound = Sounds.missile;
                        bullet = SnBullets.HelicopterMissiles;
                    }},

                    new Weapon() {{
                        rotate = false;
                        mirror = false;
                        shake = 3f;
                        x = 0f;
                        y = 16f;
                        reload = 230f;
                        top = false;
                        shotDelay = 8f;
                        inaccuracy = 2f;
                        shots = 3;
                        velocityRnd = 0.2f;
                        shootSound = Sounds.missile;
                        bullet = SnBullets.bigHelicopterRocket;
                    }});
        }};
        // endregion copters
        // region buffers
        comet = new UnitType("comet") {{
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

            weapons.add(new WeaponExt("comet") {{
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
            hitSize = 16;
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
            hitSize = 20;
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
            hitSize = 28;
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

            weapons.add(new WeaponExt("sunset-star-gun") {{
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
            weapons.add(new ChainWeapon("sunset-galaxy-weak") {{
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
            hitSize = 42;
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

            weapons.add(new WeaponExt("sunset-galaxy-segment") {{
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

            weapons.add(new PointDefenseWeapon("sunset-galaxy-weak") {{
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
        //region wheel
        wheel1 = new WheelUnitType("wheel1"){{
            health = 80;
            speed = 3.5f;
            rotateSpeed = baseRotateSpeed = 2.75f;
            drag = 0.075f;
            weapons.add(new WeaponExt("wheel1-minigun"){{
                reload = 5.5f;
                inaccuracy = 4f;
                rotate = true;
                mirror = false;
                bullet = SnBullets.wheel1bullet;
                x = y = 0;
            }});
            defaultController = GroundUnitWeaponAI::new;
        }};
        //endregion wheel
        //region misc
        router = new UnitTypeExt("router") {{
                health = 2000000;
                speed = 2.85f;
                hitSize = 16;
                flying = true;
                constructor = UnitEntity::create;
                engineSize = 0;
                drawCell = false;
            }
            @Override
            public boolean isHidden() { return true; }
        };
        courier = new UnitTypeExt("courier"){{
            speed = 5.6f;
            flying = true;
            itemCapacity = 120;
            health = 50;
            hitSize = 4;
            defaultController = DeliverAI::new;
            constructor = UnitEntity::create;
        }};
        //endregion misc
        //region freezing
        snowflake = new UnitType("snowflake") {{
            defaultController = SuicideAI::new;
            constructor = EntityMapping.map(19);
            speed = 1f;
            hitSize = 8f;
            health = 500;
            mechSideSway = 0.25f;
            range = 40f;

            immunities.add(StatusEffects.freezing);

            weapons.add(new Weapon(){{
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
        loadFactoryRecipes();
    }
}