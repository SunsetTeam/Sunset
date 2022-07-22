package sunset.content.blocks;

import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import sunset.content.*;
import sunset.utils.*;
import sunset.world.blocks.payload.*;

//TODO renaming
public class SnPayloadBlocks{

    public static Block

    //missiles
    zeusRocket;

    public static void load(){

        zeusRocket = new Ammunition("zeus-missile"){{
            buildVisibility = BuildVisibility.sandboxOnly;
            category = Category.units;
            bulletType = new AmmunitionBulletType(5.4f, 30f){{


                shootEffect = Fx.shootBig;
                smokeEffect = SnFx.shootZeusMissile;
                homingPower = 0.15f;
                homingRange = 16f * Vars.tilesize;
                maxRange = 200f;
                lifetime = 4.7f * Time.toSeconds;
                trailColor = Pal.lightPyraFlame;
                trailLength = 9;
                collidesGround = false;
                collidesAir = true;
                UtilsKt.TODO("Choose one of this things");
                UtilsKt.TODO("this");
                splashDamage = 130f;
                splashDamageRadius = 35f;
                UtilsKt.TODO("or this");
                splashDamage = 40;
                splashDamageRadius = 30;
            }};
            size = 3;
        }};
    }
}
