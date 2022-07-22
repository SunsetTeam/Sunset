package sunset.entities.bullet;

import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.world.blocks.defense.turrets.*;
import sunset.world.blocks.payload.*;
import sunset.world.blocks.payload.Ammunition.*;

public class BlockBulletType extends BulletType{
    public BlockBulletType(float speed, float damage){
        super(speed, damage);
    }

    public BlockBulletType(){
        super();
    }

    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public Bullet create(@Nullable Entityc owner, Team team, float x, float y, float angle, float damage, float velocityScl, float lifetimeScl, Object data, @Nullable Mover mover, float aimX, float aimY){

        if(owner instanceof PayloadAmmoTurret.PayloadTurretBuild build){
//            PayloadAmmoTurret block = (PayloadAmmoTurret)build.block;
            data = build.currentBlock();
        }
        return super.create(owner, team, x, y, angle, damage, velocityScl, lifetimeScl, data, mover, aimX, aimY);
    }

    @Override
    public void hitTile(Bullet b, Building build, float x, float y, float initialHealth, boolean direct){
        if (!(b.data instanceof Ammunition ammunitionBlock)){
            super.hitTile(b, build, x, y, initialHealth, direct);
            return;
        }
        ammunitionBlock.hitTile(b, build, x, y, initialHealth, direct);
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        if (!(b.data instanceof Ammunition ammunitionBlock)){
            super.hitEntity(b, entity, health);
            return;
        }
        ammunitionBlock.hitEntity(b, entity, health);
    }

    @Override
    public void hit(Bullet b, float x, float y){
        if (!(b.data instanceof Ammunition ammunitionBlock)){
            super.hit(b, x, y);
            return;
        }
        ammunitionBlock.hit(b,x,y);

    }

    @Override
    public void draw(Bullet b){
        if (!(b.data instanceof Ammunition ammunitionBlock)){
            super.draw(b);
            return;
        }
        ammunitionBlock.drawBullet(b);
    }

    public <T extends UnlockableContent> boolean displayStats(T content, Table bt){
        if (!(content instanceof Ammunition ammunition))return false;
        ammunition.displayBulletStats(bt);
        return true;
    }
}
