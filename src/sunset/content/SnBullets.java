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
    heavyCoalFlame, heavyPyraFlame, blastFlame, flameidFlame,
//liquid
    typhoonWaterShot, typhoonCryoShot, typhoonSlagShot, typhoonOilShot;
  
    @Override
    public void load(){

//artillery
        artilleryForsFrag = new BasicBulletType(2.9f, 23, "bullet"){{
            width = 10f;
            height = 12f;
            shrinkY = 1f;
            lifetime = 15f;
            damage = 24f;
            backColor = SnPal.redBomb;
            frontColor = SnPal.redBombBack;
            despawnEffect = Fx.none;
            collidesAir = true;
        }};

        artilleryFors = new ArtilleryBulletType(3.0f, 70, "shell"){{
            hitEffect = SnFx.redBomb;
            knockback = 1f;
            width = height = 19f;
            lifetime = 110f;
            width = height = 25f;
            collidesTiles = false;
            collidesAir = true;
            splashDamageRadius = 60f * 0.75f;
            splashDamage = 120f;
            fragBullet = artilleryForsFrag;
            fragBullets = 10;
            backColor = SnPal.redBomb;
            frontColor = SnPal.redBombBack;
        }};

        artilleryBlastFrag = new BasicBulletType(2.9f, 25, "bullet"){{
            width = 10f;
            height = 12f;
            shrinkY = 1f;
            damage = 20f;
            lifetime = 15f;
            backColor = SnPal.redBomb;
            frontColor = SnPal.redBombBack;
            despawnEffect = Fx.none;
            collidesAir = true;
        }};

        artilleryBlast = new ArtilleryBulletType(3.0f, 75, "shell"){{
            hitEffect = SnFx.redBomb;
            knockback = 1f;
            lifetime = 110f;
            width = height = 25f;
            collidesTiles = false;
            collidesAir = true;
            splashDamageRadius = 68f * 0.75f;
            splashDamage = 90f;
            fragBullet = artilleryForsFrag;
            fragBullets = 11;
            makeFire = true;
            backColor = SnPal.redBomb;
            frontColor = SnPal.redBombBack;
        }};
    
//burner
        heavyCoalFlame = new BulletType(12f, 30f){{
            ammoMultiplier = 3f;
            hitSize = 7f;
	        pierce = true;
	        collidesAir = true;
            lifetime = 9f;
            pierce = true;
            statusDuration = 60f * 4;
            shootEffect = SnFx.heavyFlame;
            hitEffect = Fx.hitFlameSmall;
            despawnEffect = Fx.none;
            status = StatusEffects.burning;
            keepVelocity = false;
            hittable = false;
        }};

        heavyPyraFlame = new BulletType(12f, 33f){{
            ammoMultiplier = 3f;
            hitSize = 7f;
	        pierce = true;
	        collidesAir = true;
            lifetime = 9f;
            pierce = true;
            statusDuration = 60f * 4;
            shootEffect = SnFx.heavyFlame;
            hitEffect = Fx.hitFlameSmall;
            despawnEffect = Fx.none;
            status = StatusEffects.burning;
            keepVelocity = false;
            hittable = false;
        }};
        
        blastFlame = new BulletType(12f, 36f){{
            ammoMultiplier = 3f;
            hitSize = 7f;
	        pierce = true;
	        collidesAir = true;
            lifetime = 9f;
            pierce = true;
            statusDuration = 60f * 4;
            shootEffect = SnFx.heavyFlame;
            hitEffect = Fx.hitFlameSmall;
            despawnEffect = Fx.none;
            status = StatusEffects.burning;
            keepVelocity = false;
            hittable = false;
        }};

        flameidFlame = new BulletType(12f, 38f){{
            ammoMultiplier = 3f;
            hitSize = 7f;
	        pierce = true;
	        collidesAir = true;
            lifetime = 9f;
            pierce = true;
            statusDuration = 60f * 4;
            shootEffect = SnFx.heavyFlame;
            hitEffect = Fx.hitFlameSmall;
            despawnEffect = Fx.none;
            status = StatusEffects.burning;
            keepVelocity = false;
            hittable = false;
        }};
//liquid
        typhoonWaterShot = new LiquidBulletType(Liquids.water){{
            lifetime = 70f;
            speed = 4f;
            knockback = 2.5f;
            puddleSize = 11f;
            orbSize = 6f;
            drag = 0.001f;
            ammoMultiplier = 0.5f;
            statusDuration = 60f * 4f;
            damage = 0.3f;
        }};

        typhoonCryoShot = new LiquidBulletType(Liquids.cryofluid){{
            lifetime = 70f;
            speed = 4f;
            knockback = 1.8f;
            puddleSize = 11f;
            orbSize = 6f;
            drag = 0.001f;
            ammoMultiplier = 0.5f;
            statusDuration = 60f * 4f;
            damage = 0.4f;
        }};

        typhoonSlagShot = new LiquidBulletType(Liquids.slag){{
            lifetime = 49f;
            speed = 4f;
            knockback = 1.8f;
            puddleSize = 8f;
            orbSize = 4f;
            damage = 4.95f;
            drag = 0.001f;
            ammoMultiplier = 0.4f;
            statusDuration = 60f * 4f;
        }};

        typhoonOilShot = new LiquidBulletType(Liquids.oil){{
            lifetime = 70f;
            speed = 4f;
            knockback = 1.8f;
            puddleSize = 11f;
            orbSize = 6f;
            drag = 0.001f;
            ammoMultiplier = 0.5f;
            statusDuration = 60f * 4f;
            damage = 0.3f;
        }};

        
    }
}
