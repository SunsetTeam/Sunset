package sunset.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.io.*;
import mindustry.world.*;
import mindustry.type.*;
import sunset.graphics.*;
import mindustry.world.blocks.power.BurnerGenerator;

import static mindustry.Vars.*;

public class SnBullets implements ContentList{
    public static BulletType 

//artillery
    artilleryForsFrag, artilleryFors, artilleryBlastFrag, artilleryBlast, 
//burner 
    heavycoalFlame, heavypyraFlame, blastFlame, flameidFlame,
    
//liquid
    typhoonWaterShot, typhoonCryoShot, typhoonSlagShot, typhoonOilShot;
  
    @Override
    public void load(){

//artillery
        artilleryForsFrag = new BasicBulletType(2.9f, 23, "bullet"){{
            width = 12f;
            height = 13f;
            shrinkY = 1f;
            lifetime = 15f;
            backColor = SnPal.redBomb;
            frontColor = SnPal.redBombBack;
            despawnEffect = Fx.none;
            collidesAir = false;
        }};

        artilleryFors = new ArtilleryBulletType(4.0f, 70, "shell"){{
            hitEffect = SnFx.redBomb;
            knockback = 1f;
            width = 15f;
            height = 17f;
            lifetime = 90f;
            width = height = 13f;
            collidesTiles = false;
            splashDamageRadius = 43f * 0.75f;
            splashDamage = 50f;
            fragBullet = artilleryForsFrag;
            fragBullets = 10;
            backColor = SnPal.redBomb;
            frontColor = SnPal.redBombBack;
        }};

        artilleryBlastFrag = new BasicBulletType(2.9f, 25, "bullet"){{
            width = 10f;
            height = 12f;
            shrinkY = 1f;
            lifetime = 15f;
            backColor = SnPal.redBomb;
            frontColor = SnPal.redBombBack;
            despawnEffect = Fx.none;
            collidesAir = false;
        }};

        artilleryBlast = new ArtilleryBulletType(4.0f, 75, "shell"){{
            hitEffect = SnFx.redBomb;
            knockback = 1f;
            lifetime = 90f;
            width = 15f;
            height = 17f;
            width = height = 13f;
            collidesTiles = false;
            splashDamageRadius = 45f * 0.75f;
            splashDamage = 54f;
            fragBullet = artilleryForsFrag;
            fragBullets = 11;
            makeFire = true;
            backColor = SnPal.redBomb;
            frontColor = SnPal.redBombBack;
        }};

    
//burner
        heavycoalFlame = new BulletType(3.60f, 7f){{
            ammoMultiplier = 3f;
            hitSize = 7f;
            lifetime = 18f;
            pierce = true;
            collidesAir = false;
            statusDuration = 60f * 4;
            shootEffect = Fx.shootSmallFlame;
            hitEffect = Fx.hitFlameSmall;
            despawnEffect = Fx.none;
            status = StatusEffects.burning;
            keepVelocity = false;
            hittable = false;
        }};

        heavypyraFlame = new BulletType(3.60f, 10f){{
            ammoMultiplier = 4f;
            hitSize = 7f;
            lifetime = 18f;
            pierce = true;
            collidesAir = false;
            statusDuration = 60f * 6;
            shootEffect = Fx.shootPyraFlame;
            hitEffect = Fx.hitFlameSmall;
            despawnEffect = Fx.none;
            status = StatusEffects.burning;
            hittable = false;
        }};
        
        blastFlame = new BulletType(3.60f, 13f){{
            ammoMultiplier = 4f;
            hitSize = 8f;
            lifetime = 19f;
            pierce = true;
            collidesAir = true;
            statusDuration = 60f * 4;
            shootEffect = Fx.shootSmallFlame;
            hitEffect = Fx.hitFlameSmall;
            despawnEffect = Fx.none;
            status = StatusEffects.burning;
            keepVelocity = false;
            hittable = false;
        }};

        flameidFlame = new BulletType(3.60f, 17f){{
            ammoMultiplier = 4f;
            hitSize = 8f;
            lifetime = 20f;
            pierce = true;
            collidesAir = true;
            statusDuration = 60f * 6;
            shootEffect = Fx.shootPyraFlame;
            hitEffect = Fx.hitFlameSmall;
            despawnEffect = Fx.none;
            status = StatusEffects.burning;
            hittable = false;
        }};
//liquid
        typhoonWaterShot = new LiquidBulletType(Liquids.water){{
            lifetime = 70f;
            speed = 4f;
            knockback = 1.8f;
            puddleSize = 10f;
            orbSize = 6f;
            drag = 0.001f;
            ammoMultiplier = 0.5f;
            statusDuration = 60f * 4f;
            damage = 0.1f;
        }};

        typhoonCryoShot = new LiquidBulletType(Liquids.cryofluid){{
            lifetime = 70f;
            speed = 4f;
            knockback = 1.8f;
            puddleSize = 10f;
            orbSize = 6f;
            drag = 0.001f;
            ammoMultiplier = 0.5f;
            statusDuration = 60f * 4f;
            damage = 0.2f;
        }};

        typhoonSlagShot = new LiquidBulletType(Liquids.slag){{
            lifetime = 70f;
            speed = 4f;
            knockback = 1.8f;
            puddleSize = 10f;
            orbSize = 6f;
            drag = 0.001f;
            ammoMultiplier = 0.5f;
            statusDuration = 60f * 4f;
            ammoMultiplier = 0.4f;
            statusDuration = 60f * 4f;
        }};

        typhoonOilShot = new LiquidBulletType(Liquids.oil){{
            lifetime = 70f;
            speed = 4f;
            knockback = 1.8f;
            puddleSize = 10f;
            orbSize = 6f;
            drag = 0.001f;
            ammoMultiplier = 0.5f;
            statusDuration = 60f * 4f;
            damage = 0.2f;
        }};

        
    }
}