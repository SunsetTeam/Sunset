package sunset.world.blocks.defense.turrets;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.blocks.defense.turrets.*;
import mma.graphics.*;
import org.jetbrains.annotations.*;
import sunset.entities.bullet.*;
import sunset.gen.*;
import sunset.utils.*;

import static mindustry.Vars.tilesize;

public class MagneticTurret extends Turret{
    public final MagneticTurretBulletType bulletType = new MagneticTurretBulletType();
    public int totalSize = 1;
    public float bulletRadius = -1;
    public float bulletRadiusTolerance = -1;

    public float buildTime = Time.toSeconds * 3;

    public MagneticTurret(String name){
        super(name);

    }

    @Override
    public void init(){
        super.init();
        if(bulletRadius == -1) bulletRadius = tilesize * size / 2f;
        if(bulletRadiusTolerance == -1) bulletRadiusTolerance = 1f;

        bulletType.collidesGround |= targetGround;
//        bulletType.collideTerrain |= targetGround;

        bulletType.collidesAir |= targetAir;

        bulletType.collides = targetGround || targetAir;

    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("progress", (MagneticTurretBuild build) -> new Bar("@progress", Pal.accent, () -> build.progress));
        addBar("bullets", (MagneticTurretBuild build) -> new Bar("@bullets", Pal.accent, () -> build.bullets.size / (float)totalSize));
//        addBar("efficiency", (MagneticTurretBuild build) -> new Bar("@efficiency", Pal.accent, () -> build.efficiency));
//        addBar("potentialEfficiency", (MagneticTurretBuild build) -> new Bar("@potentialEfficiency", Pal.accent, () -> build.potentialEfficiency));
    }

    private class BulletEntity{
        //        public final Trail trail = new Trail(bulletType.trailLength);
        public final MagneticTurretBulletType type;
        public final ConcentratorBullet bullet;
        public final Mover mover;
        //        public boolean attack = false;
        //        public int rounds;
        /*angle in range 0..360*/
//        public float firstRoundAngle = -1;

        public BulletEntity(MagneticTurretBulletType type, Building parent, Team team, Mover mover){
            this(type, type.create(null, team, parent.x, parent.y, 90), mover);
//            System.out.println("Damage: "+bullet.damage);
            bullet.owner = parent;
            bullet.bornTeam = team;
        }

        public BulletEntity(MagneticTurretBulletType type, ConcentratorBullet bullet, Mover mover){
            this.type = type;
            this.bullet = bullet;
            this.mover = mover;

            bullet.type = type;
            bullet.mover = mover;
        }

        public void draw(){
//            bulletType.draw(bullet);
            if(bullet.attackMode){
                Draw.color(Pal.health);
            }else if(bullet.startRotationAngle == -1){
                Draw.color(Pal.accent);
            }else if(bullet.startRotationAngle == Float.POSITIVE_INFINITY){
                Draw.color(Color.cyan);
            }else{
                Draw.color(Color.grays(Angles.angleDist(bullet.startRotationAngle, bullet.building.angleTo(bullet)) / 180f));
            }
//            Draw.color(bullet.attackMode ? Pal.health : Pal.heal);
            AFill.donut(bullet.x, bullet.y, (tilesize / 4f) * Mathf.absin(4f, 1f), tilesize / 2f);
            Draw.color();
        }

        public void update(){
            float prevHomingPower = bulletType.homingPower;
//            bulletType.homingPower = 0f;
//            bullet.update();
//            bulletType.update(bullet);
//            bulletType.homingPower = prevHomingPower;
//            bulletType.updateTrail(bullet);
        }
    }

    public class MagneticTurretBuild extends TurretBuild{
        private final Seq<BulletEntity> bullets = new Seq<>(totalSize);
        private final Mover bulletMover = b__u__l__l__e__t -> {
            BulletEntity entity = bullets.find(it -> it.bullet == b__u__l__l__e__t);
            ConcentratorBullet bullet = entity.bullet;

            /*if(entity.attack){
             *//*do some thing*//*
                return;
            }*/
            float radius = bulletRadius;
            Tmp.v1.set(bullet).add(bullet.vel, Time.delta);

            final float angleOffset = 1f;
//            b.move(b.vel);
            float aroundAngle = Tmp.v1.set(bullet).sub(this).angle();
            moving:
            {
                Vec2 target;
                float homingPower;
                if(!bullet.attackMode){
                    float currentAngle = this.angleTo(bullet);
                    Tmp.v2.trns(currentAngle + angleOffset * Mathf.sign(bullet.spin), radius).add(this);
                    homingPower = entity.type.pathFollowHomingPower;
                    target = Tmp.v2;

//            if (velAngle)
                }else{
                    target = Tmp.v2.set(targetPos);
                    homingPower = entity.type.homingPower;
                }
                bullet.vel.setAngle(Angles.moveToward(bullet.rotation(), bullet.angleTo(target), homingPower * Time.delta * 50f));
            }
            rangeChecking:
            {
                if(bullet.attackMode){
//                    Vec2 shootPosition = Tmp.v1.set(targetPos).sub(bullet).rotate(-bullet.rotation()).add(bullet);

                    bullet.hitbox(Tmp.r1);
                    float targetLastX, targetLastY;
                    if(target != null && !logicControlled() && !isControlled()){
                        target.<Hitboxc>as().hitbox(Tmp.r2);
                        targetLastX = target.<Hitboxc>as().lastX();
                        targetLastY = target.<Hitboxc>as().lastY();
                    }else{
                        Tmp.r2.setSize(1, 1).setCenter(targetPos);
                        targetLastX = targetPos.x;
                        targetLastY = targetPos.y;
                    }

//                    Tmp.r1.width+=bullet.vel.len();
                    if(CollisionUtils.checkCollide(
                    Tmp.r1, Tmp.r2
                    , bullet.lastX(), bullet.lastY, bullet.x, bullet.y,
                    targetLastX, targetLastY, targetPos.x, targetPos.y
                    )){
                        bullet.attackMode = false;
                    }
//                Vec2 nextPosition = Tmp.v2.set(bullet).add(bullet.vel);

//                Geometry
                }
            }
            if(Mathf.equal(bullet.dst(this), bulletRadius, bulletRadiusTolerance) && !bullet.attackMode && entity.bullet.startRotationAngle == -1){
                bullet.startRotationAngle = aroundAngle;
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
//                bullet.bullet.attackMode = false;
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
            entity.bullet.spin = bullets.size % 2 == 0;
            if(entity.bullet.spin){
                entity.bullet.vel.trns(1f, bulletType.speed);
            }else{
                entity.bullet.vel.trns(90 - 1f, bulletType.speed);
            }
            bullets.add(entity);
        }

        private void updateBehavior(@NotNull BulletEntity entity){
            if(entity.bullet.startRotationAngle == -1 || entity.bullet.attackMode) return;
            float aroundAngle = Tmp.v1.set(entity.bullet).sub(this).angle();

            if(Float.POSITIVE_INFINITY != entity.bullet.startRotationAngle){
                float deltaAngle = Utils.forwardAngleDistance(entity.bullet.startRotationAngle, aroundAngle);
//                System.out.println(deltaAngle);
                if((deltaAngle >= 180 && !entity.bullet.attackMode)){
                    entity.bullet.startRotationAngle = Float.POSITIVE_INFINITY;
                }
            }
            if(entity.bullet.startRotationAngle == Float.POSITIVE_INFINITY){
                if(isShooting()){
                    entity.bullet.startRotationAngle = -1;
                    entity.bullet.attackMode = true;
                }
            }
        }

        @Override
        public void draw(){
            super.draw();
            for(BulletEntity bullet : bullets){
                bullet.draw();
            }
            Draw.color(Pal.accent, 0.1f);
            AFill.donut(x, y, bulletRadius - 1f / tilesize, bulletRadius + 1f / tilesize, 1f, 0f);
            Draw.color();
            Draw.color(isShooting() ? Pal.health : Pal.heal);
            AFill.donut(targetPos.x, targetPos.y, (size - 0.5f) * tilesize * Mathf.absin(4f, 1f), size * tilesize);
            Draw.color();
        }

        @Override
        protected void updateShooting(){

        }
        //region ammo

        @Override
        public void targetPosition(Posc pos){
            if(!hasAmmo() || pos == null) return;

            var offset = Tmp.v1.setZero();

            //when delay is accurate, assume unit has moved by chargeTime already
            if(accurateDelay && pos instanceof Hitboxc h){
                offset.set(h.deltaX(), h.deltaY()).scl(shoot.firstShotDelay / Time.delta);
            }

//            targetPos.set(Predict.intercept(this, pos, offset.x, offset.y, 99999999f ));
            targetPos.set(pos);

            if(targetPos.isZero()){
                targetPos.set(pos);
            }
        }

        @Override
        public BulletType useAmmo(){
            return bulletType;
        }

        @Override
        public boolean hasAmmo(){
            return bullets.any();
        }

        @Override
        public BulletType peekAmmo(){
            return bulletType;
        }
        //endregion

        @Override
        public void write(Writes write){
            super.write(write);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
        }

        public void registerBullet(ConcentratorBullet bullet){
            bullets.add(new BulletEntity(bulletType, bullet, bulletMover));
        }

        public void removeBullet(ConcentratorBullet bullet){
            if (bullet==null)return;
            bullets.remove(it -> it.bullet == bullet);
        }
    }
}
