package sunset.content;

import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;
import mindustry.gen.Sounds;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import sunset.ai.FlyingUnitWeaponAI;
import sunset.entities.abilities.StatusFieldAbility;
import sunset.type.UnitTypeExt;
import sunset.ai.ExtinguishAI;
import sunset.ai.weapon.ExtinguishWeaponAI;
import sunset.type.weapons.WeaponExt;
import sunset.type.weapons.ChainWeapon;
import sunset.type.CopterUnitType;
import sunset.type.weapons.PointDefenseWeapon;

public class SnUnitTypes implements ContentList{
   public static UnitType

    //flying

    guardcopter, bladecopter, swordcopter, //attacks copters
    
    comet, satelite, planet, star, galaxy; //buffers

@Override
public void load() {
   
      guardcopter = new CopterUnitType("guard_copter"){{
         health = 170;
         hitSize = 24;
         speed = 3.3f;
         accel = 0.1f;
         drag = 0.02f;
         fallRotateSpeed = 2.5f;
         rotorDeathSlowdown = 0.01f;
         commandLimit = 3;

         flying = true;
         circleTarget = false;
         range = 130;
        
         rotorDeathSlowdown = 0.07f;
         fallRotateSpeed = 4.0f;
         offsetY = 2.2f;
         weapons.add(
                new Weapon("sunset-guard-gun"){{
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

      bladecopter = new CopterUnitType("blade_copter"){{
            health = 370;
            hitSize = 35;
            speed = 3.0f;
            accel = 0.1f;
            drag = 0.02f;
            commandLimit = 4;
            
            flying = true;
            circleTarget = false;
            range = 170;
            
            rotorDeathSlowdown = 0.09f;
            fallRotateSpeed = 5.0f;
            offsetY = 2.5f;
            weapons.add(
                   new Weapon("sunset-blade-gun"){{
                       rotate = false;
                       mirror = true;
                       top = true;
                       x = -10f;
                       y = 4f;
                       spacing = 4f;
                       shots = 3;
                       inaccuracy = 12;
                       reload = 15f;
                       shootSound = Sounds.shoot;
                       bullet = SnBullets.HelicopterShootgun;
               }});
         }};

         swordcopter = new CopterUnitType("sword_copter"){{
            health = 630;
            hitSize = 42;
            speed = 2.8f;
            accel = 0.1f;
            drag = 0.02f;
            commandLimit = 4;
            
            flying = true;
            circleTarget = false;
            range = 210;
            
            rotorDeathSlowdown = 0.1f;
            fallRotateSpeed = 5.5f;
            rotorRotateSpeed = 27f;
            offsetY = 2.6f;
            weapons.add(
                   new Weapon("sunset-sword-gun"){{
                       rotate = false;
                       mirror = true;
                       top = true;
                       x = -19f;
                       y = 5f;
                       spacing = 3f;
                       reload = 45f;
                       shake = 1f;
                       inaccuracy = 5f;
                       velocityRnd = 0.2f;
                       shots = 3;
                       shootSound = Sounds.missile;
                       bullet = SnBullets.HelicopterMissile;
            }},

                   new Weapon(){{
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

//buffers
         comet = new UnitType("comet"){{
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

            weapons.add(new WeaponExt("comet"){{
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

        satelite = new UnitTypeExt("satellite"){{
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

            defaultController =  FlyingUnitWeaponAI::new;

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

            defaultController = FlyingUnitWeaponAI::new;

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

            abilities.add(new StatusFieldAbility(SnStatusEffects.starBuff, StatusEffects.none, 20, 8 * 24));

            weapons.add(new Weapon("sunset-star-gun"){{
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
        }};

        galaxy = new UnitTypeExt("galaxy"){{
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

            abilities.add(new StatusFieldAbility(SnStatusEffects.starBuff, StatusEffects.freezing, 20, 8 * 24));

            weapons.add(new WeaponExt("sunset-galaxy-segment"){{
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

            weapons.add(new PointDefenseWeapon("sunset-galaxy-weak"){{
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
    }
}
