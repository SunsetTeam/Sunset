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
import sunset.type.CopterUnitType;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public class SnUnitTypes implements ContentList{
   public static UnitType

//flying

   //attacks copters
      guardcopter, bladecopter;

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

}
}
