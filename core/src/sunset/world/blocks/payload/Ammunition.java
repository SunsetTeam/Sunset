package sunset.world.blocks.payload;

import arc.*;
import arc.audio.*;
import arc.func.*;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.annotations.Annotations.Load;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import mma.ModVars;
import sunset.content.SnFx;
import sunset.utils.*;

import static mindustry.Vars.tilesize;

public class Ammunition extends Block {
    public float explosionRadius = 11*tilesize;
    public int explosionDamage = 70;
    public Effect explodeEffect = SnFx.sunriseMissileExplosion;
    public Sound boomEffect=Sounds.explosionbig;

    private final AmmunitionBuild tmpBuild;

    public Ammunition(String name) {
        super(name);
        health = 50;
        solid = true;
        update = true;
        category = Category.units;
        buildVisibility = BuildVisibility.sandboxOnly;

        tmpBuild=new AmmunitionBuild();
    }

    @Override
    protected void initBuilding(){
        buildType=()->new AmmunitionBuild();
    }

    public void hit(Bullet b, float x, float y){
//        UtilsKt.TODO();

        AmmunitionBuild ammunitionBuild = tmpBuild;
        ammunitionBuild.x=x;
        ammunitionBuild.y=y;
        ammunitionBuild.onDestroyed();
    }

    public void hitTile(Bullet b, Building build, float x, float y, float initialHealth, boolean direct){
//        UtilsKt.TODO();//TODO hitTile
        hit(b,x,y);
    }

    public void hitEntity(Bullet b, Hitboxc entity, float health){
//        UtilsKt.TODO();//TODO hitEntity
//        hit(b,x,y);
    }

    public void displayBulletStats(Table bt){
        bt.add(Core.bundle.format("bullet.damage", explosionDamage));
        bt.row();
        bt.add(Core.bundle.format("bullet.splashdamage", (int)explosionRadius, Strings.fixed(explosionRadius/tilesize, 1)));
//        UtilsKt.TODO();//TODO displayBulletStats
    }

    public void drawBullet(Bullet b){
        b.type.drawTrail(b);

        Draw.rect(region, b.x, b.y, size*tilesize, size*tilesize, b.rotation() - 90);
//        Draw.rect(backRegion, b.x, b.y, width, height, b.rotation() - 90);
        Draw.reset();
    }

    public class AmmunitionBuild extends Building {
        private AmmunitionBuild(){

        }

        @Override
        public void onDestroyed(){
            super.onDestroyed();

            boomEffect.at(this);

            Effect.shake(6f, 16f, x, y);
            // * ((float)fuel / itemCapacity) to scale based on fullness
            Damage.damage(x, y, explosionRadius , explosionDamage * 4);

            explodeEffect.at(x, y);
        }
    }
}