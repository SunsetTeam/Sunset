package sunset.world.blocks.defense.turrets;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import mindustry.entities.bullet.BulletType;

/** Turret that shoots 360 degrees. (and rotates optionally) */
public class Turret360 extends ModItemTurret {
    public boolean rotate;

    public Turret360(String name) {
        super(name);
        rotate = false;
        shootCone = 360;
        shots = 4;
        spread = shootCone / shots;
    }

    public class T360 extends ModItemTurretBuild {
        @Override
        public void bullet(BulletType type, float angle) {
            Vec2 vec = new Vec2();
            vec.trns(this.rotation, 0);

            float lifeScl = type.scaleVelocity ? Mathf.clamp(Mathf.dst(x + vec.x, y + vec.y, targetPos.x, targetPos.y) / type.range(), minRange / type.range(), range / type.range()) : 1f;

            type.create(this, team, x + vec.x, y + vec.y, angle, 1f + Mathf.range(velocityInaccuracy), lifeScl);
            
            if (totalShoots == 2) {
                powerBullet.create(this, team, x + vec.x, y + vec.y, angle, 1f + Mathf.range(velocityInaccuracy), lifeScl);
            }
        }

        @Override
        protected void turnToTarget(float targetRot) {
            if (rotate) {
                rotation += rotateSpeed;
            } else  {
                super.turnToTarget(targetRot);
            }
        }
    }
}
