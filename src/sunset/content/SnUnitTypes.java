package sunset.content; 

import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.type.*;
import sunset.ai.ExtinguishAI;
import sunset.type.CopterUnitType;
import sunset.type.ExtinguishWeapon;

public class SnUnitTypes implements ContentList{
    public static UnitType

    //flying
        guardcopter, bladecopter, //attacks copters
        comet; //h
    
    @Override
    public void load() {
        guardcopter = new CopterUnitType("guard_copter"){{
         health = 170;
         hitSize = 20;
         speed = 3.3f;
         accel = 0.1f;
         drag = 0.02f;
        
         flying = true;
         circleTarget = false;
         range = 130;
        
         offsetY = 2.2f;
         weapons.add(
                new Weapon("sunset-guard-gun"){{
                    rotate = false;
                    mirror = true;
                    top = true;
                    x = 8f;
                    y = 0f;
                    shots = 1;
                    inaccuracy = 3;
                    reload = 9f;
                    shootSound = Sounds.pew;
                    bullet = SnBullets.BasicHelicopterGun;
            }});
        }};
        
        bladecopter = new CopterUnitType("blade_copter"){{
            health = 370;
            hitSize = 28;
            speed = 3.0f;
            accel = 0.1f;
            drag = 0.02f;
            
            flying = true;
            circleTarget = false;
            range = 170;
        
            offsetY = 2.5f;
            weapons.add(
                   new Weapon("sunset-blade-gun"){{
                       rotate = false;
                       mirror = true;
                       top = true;
                       x = 10f;
                       y = 4f;
                       shots = 4;
                       inaccuracy = 6;
                       reload = 19f;
                       shootSound = Sounds.artillery;
                       bullet = SnBullets.HelicopterShootgun;
               }});
         }};

        comet = new UnitType("comet"){{
            health = 150;
            hitSize = 12;
            speed = 3f;
            accel = 0.15f;
            drag = 0.1f;

            flying = true;
            circleTarget = false;
            range = 75;

            itemCapacity = 20;
            commandLimit = 4;

            defaultController = ExtinguishAI::new;

            constructor = UnitEntity::create;

            weapons.add(new ExtinguishWeapon("comet-cell"){{
                rotate = true;
                mirror = false;
                x = 0;
                top = true;
                inaccuracy = 4;
                alternate = false;
                reload = 2f;
                shootSound = Sounds.wave;
                bullet = SnBullets.cometWaterShot;
            }});
        }};
    }
}
