package sunset.world.blocks.payload;

import arc.audio.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.*;
import sunset.content.*;

import static mindustry.Vars.tilesize;

public class Ammunition extends Block{
    //    public float explosionRadius = 11;
//    public int explosionDamage = 70;
    public Effect explodeEffect = SnFx.sunriseMissileExplosion;
    public AmmunitionBulletType bulletType;
    public Sound explosionSound = Sounds.explosionbig;

    private AmmunitionBuild tmpBuild;

    public Ammunition(String name){
        super(name);
        health = 50;
        solid = true;
        update = true;
        category = Category.units;
        buildVisibility = BuildVisibility.sandboxOnly;
        tmpBuild = new AmmunitionBuild();
    }

    public static void ammoAmmunition(PayloadAmmoTurret turret, Block... ammunition){
        ObjectMap<UnlockableContent, BulletType> ammoTypes = turret.ammoTypes;
        ammoTypes.clear();
        for(Block block : ammunition){
            ammoTypes.put(block, ((Ammunition)block).bulletType);
        }
    }

    private void hit(Bullet b, float x, float y){


        AmmunitionBuild ammunitionBuild = tmpBuild;
        ammunitionBuild.x = x;
        ammunitionBuild.y = y;
        ammunitionBuild.onDestroyed();
    }

    public void drawBullet(Bullet b){
        b.type.drawTrail(b);

        Draw.rect(region, b.x, b.y, size * tilesize, size * tilesize, b.rotation() - 90);
//        Draw.rect(backRegion, b.x, b.y, width, height, b.rotation() - 90);
        Draw.reset();
    }

    public class AmmunitionBulletType extends BulletType{
        public AmmunitionBulletType(float speed, float damage){
            super(speed, damage);
        }


        public AmmunitionBulletType(){
            super();
        }

        @Override
        public void hit(Bullet b, float x, float y){
            super.hit(b, x, y);
            Ammunition.this.hit(b, x, y);

        }

        @Override
        public void draw(Bullet b){
            drawBullet(b);
        }
    }

    public class AmmunitionBuild extends Building{
        @Override
        public void onDestroyed(){
            super.onDestroyed();
            //goto BulletTypeSettings
           /* explosionSound.at(this);

            Effect.shake(6f, 16f, x, y);
            // * ((float)fuel / itemCapacity) to scale based on fullness
            Damage.damage(x, y, explosionRadius * tilesize, explosionDamage * 4);

            explodeEffect.at(x, y);*/
        }
    }
}