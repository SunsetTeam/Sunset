package sunset.world.blocks.defense.turrets;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.io.*;
import mindustry.ui.*;
import mindustry.world.blocks.defense.turrets.*;
import sunset.entities.bullet.*;
import sunset.gen.*;

import static mindustry.Vars.tilesize;

public class ConcentratorTurret extends Turret{
    public final MagneticTurretBulletType bulletType = new MagneticTurretBulletType();
    public int totalSize = 0;
    public float bulletRadius = tilesize;
    public float bulletRadiusTolerance = tilesize / 8f;

    public float buildTime = Time.toSeconds * 3;

    public ConcentratorTurret(String name){
        super(name);

    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("progress",(MagneticTurretBuild build)->new Bar("@progress", Pal.accent,()->build.progress));
        addBar("bullets",(MagneticTurretBuild build)->new Bar("@bullets", Pal.accent,()->build.bullets.size/(float)totalSize));
        addBar("efficiency",(MagneticTurretBuild build)->new Bar("@efficiency", Pal.accent,()->build.efficiency));
        addBar("potentialEfficiency",(MagneticTurretBuild build)->new Bar("@potentialEfficiency", Pal.accent,()->build.potentialEfficiency));
    }

    private class BulletEntity{
        //        public final Trail trail = new Trail(bulletType.trailLength);
        public final MagneticTurretBulletType type;
        public final ConcentratorBullet bullet;
        public final Mover mover;
        public boolean attack = false;
        public int rounds;
        /*angle in range 0..360*/
        public float firstRoundAngle = -1;

        public BulletEntity(MagneticTurretBulletType type, Building parent, Team team, Mover mover){
            this(type, type.create(null, team, parent.x + bulletRadius, parent.y, 90), mover);
            bullet.owner = parent;
            bullet.bornTeam=team;
        }

        public BulletEntity(MagneticTurretBulletType type, ConcentratorBullet bullet, Mover mover){
            this.type = type;
            this.bullet = bullet;
            this.mover = mover;

            bullet.type = type;
            bullet.mover = mover;
        }

        public void draw(){
            bulletType.draw(bullet);
        }

        public void update(){
            float prevHomingPower = bulletType.homingPower;
            bulletType.homingPower = 0f;
            bullet.update();
//            bulletType.update(bullet);
            bulletType.homingPower = prevHomingPower;
//            bulletType.updateTrail(bullet);
        }
    }

    public class MagneticTurretBuild extends TurretBuild{
        private final Seq<BulletEntity> bullets = new Seq<>(totalSize);
        private final Mover bulletMover = b -> {
            BulletEntity entity = bullets.find(it -> it.bullet == b);
            ConcentratorBullet bullet = entity.bullet;

            if(entity.attack){
                /*do some thing*/
                return;
            }
            float dstToBlock = bullet.dst(this);
            float radius = bulletRadius;
            float maxSpeed = entity.type.speed;
            Tmp.v1.set(bullet).add(bullet.vel, Time.delta);

//            b.move(b.vel);
            float dst = Tmp.v1.dst(this.x, this.y);
            if(radius - bulletRadiusTolerance / 2f >= dst || dst >= radius + bulletRadiusTolerance / 2f){
                float currentAngle = this.angleTo(Tmp.v1);
                Tmp.v2.trns(currentAngle, radius).add(this);
                Draw.color(Color.green, 0.4f);
                Lines.line(bullet.x, bullet.y, Tmp.v2.x, Tmp.v2.y, false);
                Vec2 force = Tmp.v3.set(Tmp.v2.sub(bullet).limit(maxSpeed)).sub(bullet.vel);

                force.scl(Time.delta * 2);
                float len2 = bullet.vel.len2();
                bullet.vel.add(force);
                bullet.vel.setLength2(len2);
//            if (velAngle)
            }
/*
            if (Mathf.equal(dstToBlock,radius, bulletRadiusTolerance) && ){

            }*/
        };
        public float progress = 0f;

        @Override
        public void update(){
            super.update();
            for(BulletEntity bullet : bullets){
                bullet.update();
                bullet.attack = false;
            }
        }

        @Override
        public void updateTile(){
            super.updateTile();
            if(potentialEfficiency > 0 && bullets.size < totalSize){
                progress += Time.delta / buildTime;
                if(progress > 1f){
                    craftBullet();
                    consume();
                    progress %= 1f;
                }
            }else{
                progress = Math.max(0, progress - Time.delta / buildTime);
            }
            for(BulletEntity bullet : bullets){
                updateBehavior(bullet);
            }
        }

        private void craftBullet(){
            BulletEntity entity = new BulletEntity(bulletType, this, team, bulletMover);

            entity.bullet.add();
            entity.bullet.vel.trns(bullets.size/(float)totalSize,bulletType.speed);
            bullets.add(entity);
        }

        private void updateBehavior(BulletEntity bullet){
            if(target != null && bullet.rounds > 1){
                bullet.attack = true;
            }
        }

        @Override
        public void draw(){
            super.draw();
            for(BulletEntity bullet : bullets){
                bullet.draw();
            }
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.i(bullets.size);
            for(BulletEntity bullet : bullets){
                TypeIO.writeBulletType(write, bullet.type);
                bullet.bullet.write(write);
//                MindustrySerialization.writeBullet(write, bullet.bullet);
//                bullet.bullet.write(write);
            }
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            int size = read.i();
            bullets.clear();
            for(int i = 0; i < size; i++){
                MagneticTurretBulletType type = (MagneticTurretBulletType)TypeIO.readBulletType(read);
//                type.create()
                ConcentratorBullet bullet = ConcentratorBullet.create();
                bullet.read(read);

                bullet.type = type;
                bullets.add(new BulletEntity(type, bullet, bulletMover));
            }
            /*write.i(bullets.size);
            for(BulletEntity bullet : bullets){
                TypeIO.writeBulletType(write,bullet.type);
                bullet.bullet.write(write);
            }*/
        }
    }
}
