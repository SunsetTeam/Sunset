package sunset.world.blocks.defense.turrets;

import arc.struct.*;
import arc.util.io.*;
import mindustry.entities.bullet.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.io.*;
import mindustry.world.blocks.defense.turrets.*;

import static mindustry.Vars.*;

public class ConcentratorTurret extends Turret{
    public int totalSize = 0;
    public BulletType bulletType;
    public float bulletRadius = tilesize;

    public ConcentratorTurret(String name){
        super(name);

    }

    private class BulletEntity{
        //        public final Trail trail = new Trail(bulletType.trailLength);
        public final BulletType type;
        public final Bullet bullet;

        public BulletEntity(BulletType type, Building parent, Team team){
            this.type = type;
            this.bullet = type.create(null, team, parent.x + bulletRadius, parent.y, 90);
        }

        public BulletEntity(BulletType type, Bullet bullet){
            this.type = type;
            this.bullet = bullet;
        }

        public void draw(){
            bulletType.draw(bullet);
        }

        public void update(){
            float prevHomingPower = bulletType.homingPower;
            bulletType.homingPower=0f;
            bulletType.update(bullet);
            bulletType.homingPower=prevHomingPower;
//            bulletType.updateTrail(bullet);
        }
    }

    public class MagneticTurret extends TurretBuild{
        private Seq<BulletEntity> bullets = new Seq<>(totalSize);

        @Override
        public void update(){
            super.update();
            for(BulletEntity bullet : bullets){
                bullet.update();
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
                TypeIO.writeBulletType(write,bullet.type);
                bullet.bullet.write(write);
            }
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            int size = read.i();
            bullets.clear();
            for(int i = 0; i < size; i++){
                BulletType type = TypeIO.readBulletType(read);

            }
            /*write.i(bullets.size);
            for(BulletEntity bullet : bullets){
                TypeIO.writeBulletType(write,bullet.type);
                bullet.bullet.write(write);
            }*/
        }
    }
}
