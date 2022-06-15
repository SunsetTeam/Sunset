package sunset.entities.bullet;

import arc.util.*;
import mindustry.ai.types.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.game.*;
import mindustry.gen.*;
import sunset.gen.*;

import static mindustry.Vars.*;

public class MagneticTurretBulletType extends BulletType{
    enum MagneticBulletTypeState{
        waiting,
        attack
    }
    @Override
    public ConcentratorBullet create(Entityc owner, Team team, float x, float y, float angle, float damage, float velocityScl, float lifetimeScl, Object data, Mover mover, float aimX, float aimY){
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
    public @Nullable ConcentratorBullet create(Teamc owner, float x, float y, float angle){
        return create(owner, owner.team(), x, y, angle);
    }

    @Override
    public @Nullable ConcentratorBullet create(Entityc owner, Team team, float x, float y, float angle){
        return create(owner, team, x, y, angle, 1f);
    }

    @Override
    public @Nullable ConcentratorBullet create(Entityc owner, Team team, float x, float y, float angle, float velocityScl){
        return create(owner, team, x, y, angle, -1, velocityScl, 1f, null);
    }

    @Override
    public @Nullable ConcentratorBullet create(Entityc owner, Team team, float x, float y, float angle, float velocityScl, float lifetimeScl){
        return create(owner, team, x, y, angle, -1, velocityScl, lifetimeScl, null);
    }


    @Override
    public @Nullable ConcentratorBullet create(Entityc owner, Team team, float x, float y, float angle, float velocityScl, float lifetimeScl, Mover mover){
        return create(owner, team, x, y, angle, -1, velocityScl, lifetimeScl, null, mover);
    }

    @Override
    public @Nullable ConcentratorBullet create(Bullet parent, float x, float y, float angle){
        return create(parent.owner, parent.team, x, y, angle);
    }

    @Override
    public @Nullable ConcentratorBullet create(Bullet parent, float x, float y, float angle, float velocityScl, float lifeScale){
        return create(parent.owner, parent.team, x, y, angle, velocityScl, lifeScale);
    }

    @Override
    public @Nullable ConcentratorBullet create(Bullet parent, float x, float y, float angle, float velocityScl){
        return create(parent.owner(), parent.team, x, y, angle, velocityScl);
    }

    @Override
    public @Nullable ConcentratorBullet create(@Nullable Entityc owner, Team team, float x, float y, float angle, float damage, float velocityScl, float lifetimeScl, Object data){
        return create(owner, team, x, y, angle, damage, velocityScl, lifetimeScl, data, null);
    }

    @Override
    public @Nullable ConcentratorBullet create(@Nullable Entityc owner, Team team, float x, float y, float angle, float damage, float velocityScl, float lifetimeScl, Object data, @Nullable Mover mover){
        return create(owner, team, x, y, angle, damage, velocityScl, lifetimeScl, data, mover, -1f, -1f);
    }

    //endregion
}
