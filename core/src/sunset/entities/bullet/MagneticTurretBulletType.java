package sunset.entities.bullet;

import arc.util.*;
import mindustry.ai.types.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import sunset.gen.*;

import static mindustry.Vars.*;

public class MagneticTurretBulletType extends BasicBulletType{
//    public float pathFollowHomingPower = 0.025f;
    public float pathFollowHomingPower = 0.125f;
//    public float pathFollowHomingPower = 1f;

    {
        homingPower = pathFollowHomingPower;
        pierce=pierceBuilding=true;
        backColor=backColor.cpy();
        frontColor=frontColor.cpy();
    }

    public MagneticTurretBulletType(float speed, float damage){
        super(speed, damage);
    }

    public MagneticTurretBulletType(){
        super();
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
//        Log.info("MagneticTurretBulletType.hitEntity(@, @, @, @, @, @);", b, entity,health);
        super.hitEntity(b, entity, health);
    }

    @Override
    public void draw(Bullet b){
        ConcentratorBullet bullet = b.as();
        if(bullet.attackMode){
            backColor.set(Pal.redderDust);
            frontColor.set(Pal.redDust);
        }else{
            backColor.set(Pal.bulletYellowBack);
            frontColor.set(Pal.bulletYellow);
        }
        super.draw(b);

    }

    @Override
    public void hitTile(Bullet b, Building build, float x, float y, float initialHealth, boolean direct){
//        Log.info("MagneticTurretBulletType.hitTile(@, @, @, @, @, @);", b, build, x, y, initialHealth, direct);
        super.hitTile(b, build, x, y, initialHealth, direct);
    }

    @Override
    public void removed(Bullet b){
        super.removed(b);
        ConcentratorBullet bullet = b.as();
        bullet.buildOwner().removeBullet(bullet);
    }

    @Override
    public final ConcentratorBullet create(Entityc owner, Team team, float x, float y, float angle, float damage, float velocityScl, float lifetimeScl, Object data, Mover mover, float aimX, float aimY){
        if(spawnUnit != null){
            //don't spawn units clientside!
            if(!net.client()){
                Unit spawned = spawnUnit.create(team);
                spawned.set(x, y);
                spawned.rotation = angle;
                //immediately spawn at top speed, since it was launched
                spawned.vel.trns(angle, spawnUnit.speed);
                //assign unit owner
                if(spawned.controller() instanceof MissileAI ai && owner instanceof Unit unit){
                    ai.shooter = unit;
                }
                spawned.add();
            }

            //no bullet returned
            return null;
        }

        ConcentratorBullet bullet = ConcentratorBullet.create();
        bullet.type = this;
        bullet.owner = owner;
//        bullet.buildingOwner = owner;
        bullet.team = team;
        bullet.time = 0f;
        bullet.originX = x;
        bullet.originY = y;
        bullet.aimTile = world.tileWorld(aimX, aimY);
        bullet.aimX = aimX;
        bullet.aimY = aimY;
        bullet.initVel(angle, speed * velocityScl);
        if(backMove){
            bullet.set(x - bullet.vel.x * Time.delta, y - bullet.vel.y * Time.delta);
        }else{
            bullet.set(x, y);
        }
        bullet.lifetime = lifetime * lifetimeScl;
        bullet.data = data;
        bullet.drag = drag;
        bullet.hitSize = hitSize;
        bullet.mover = mover;
        bullet.damage = (damage < 0 ? this.damage : damage) * bullet.damageMultiplier();
        //reset trail
        if(bullet.trail != null){
            bullet.trail.clear();
        }
        bullet.add();

        if(keepVelocity && owner instanceof Velc v) bullet.vel.add(v.vel());
        return bullet;
    }

    //region override

    @Override
    public final @Nullable
    ConcentratorBullet create(Teamc owner, float x, float y, float angle){
        return create(owner, owner.team(), x, y, angle);
    }

    @Override
    public final @Nullable
    ConcentratorBullet create(Entityc owner, Team team, float x, float y, float angle){
        return create(owner, team, x, y, angle, 1f);
    }

    @Override
    public final @Nullable
    ConcentratorBullet create(Entityc owner, Team team, float x, float y, float angle, float velocityScl){
        return create(owner, team, x, y, angle, -1, velocityScl, 1f, null);
    }

    @Override
    public final @Nullable
    ConcentratorBullet create(Entityc owner, Team team, float x, float y, float angle, float velocityScl, float lifetimeScl){
        return create(owner, team, x, y, angle, -1, velocityScl, lifetimeScl, null);
    }


    @Override
    public final @Nullable
    ConcentratorBullet create(Entityc owner, Team team, float x, float y, float angle, float velocityScl, float lifetimeScl, Mover mover){
        return create(owner, team, x, y, angle, -1, velocityScl, lifetimeScl, null, mover);
    }

    @Override
    public final @Nullable
    ConcentratorBullet create(Bullet parent, float x, float y, float angle){
        return create(parent.owner, parent.team, x, y, angle);
    }

    @Override
    public final @Nullable
    ConcentratorBullet create(Bullet parent, float x, float y, float angle, float velocityScl, float lifeScale){
        return create(parent.owner, parent.team, x, y, angle, velocityScl, lifeScale);
    }

    @Override
    public final @Nullable
    ConcentratorBullet create(Bullet parent, float x, float y, float angle, float velocityScl){
        return create(parent.owner(), parent.team, x, y, angle, velocityScl);
    }

    @Override
    public final @Nullable
    ConcentratorBullet create(@Nullable Entityc owner, Team team, float x, float y, float angle, float damage, float velocityScl, float lifetimeScl, Object data){
        return create(owner, team, x, y, angle, damage, velocityScl, lifetimeScl, data, null);
    }

    @Override
    public final @Nullable
    ConcentratorBullet create(@Nullable Entityc owner, Team team, float x, float y, float angle, float damage, float velocityScl, float lifetimeScl, Object data, @Nullable Mover mover){
        return create(owner, team, x, y, angle, damage, velocityScl, lifetimeScl, data, mover, -1f, -1f);
    }

    //endregion
}
