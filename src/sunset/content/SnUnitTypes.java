package sunset.content; 

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import mindustry.ai.types.*;
import mindustry.ctype.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import sunset.content.*;
import sunset.ai.ExtinguishAI;
import sunset.type.CopterUnitType;
import sunset.type.LiquidWeapon;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public class SnUnitTypes implements ContentList{
   public static UnitType

    //flying
    guardcopter, bladecopter, //attacks copters
    
      comet; //buffers

@Override
public void load() {
   
      guardcopter = new CopterUnitType("guard_copter"){{
         health = 170;
         hitSize = 20;
         speed = 3.3f;
         accel = 0.1f;
         drag = 0.02f;
         commandLimit = 4;
         constructor = UnitEntity::create;

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
                    inaccuracy = 1;
                    reload = 5f;
                    shootSound = Sounds.pew;
                    bullet = SnBullets.BasicHelicopterGun;
            }});
      }};

      bladecopter = new CopterUnitType("blade_copter"){{
            health = 370;
            hitSize = 31;
            speed = 3.0f;
            accel = 0.1f;
            drag = 0.02f;
            commandLimit = 4;
            constructor = UnitEntity::create;
            
            flying = true;
            circleTarget = false;
            range = 170;
   
            offsetY = 2.5f;
            weapons.add(
                   new Weapon("sunset-blade-gun"){{
                       rotate = false;
                       mirror = true;
                       top = true;
                       x = -10f;
                       y = 4f;
                       spacing = 2f;
                       shots = 3;
                       inaccuracy = 10;
                       reload = 15f;
                       shootSound = Sounds.shoot;
                       bullet = SnBullets.HelicopterShootgun;
               }});
         }};

//buffers
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

            weapons.add(new LiquidWeapon("comet"){{
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

}
}
