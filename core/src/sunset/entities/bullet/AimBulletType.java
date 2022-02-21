package sunset.entities.bullet;

import arc.func.Cons;
import arc.math.Angles;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Posc;
import mindustry.gen.Unitc;
import mindustry.world.blocks.defense.turrets.Turret.TurretBuild;
import sunset.utils.Utils.Targeting;

public class AimBulletType extends BasicBulletType {
    public boolean homing = true;
    public BulletType bullet;

    public AimBulletType(float speed, float damage){
        super(speed, damage);
    }


    public void targetPos(Bullet bullet) {
        if (homing) {
            if (bullet.fdata() != 1 && bullet.collided.size < 2) {
                Tmp.v1.set(bullet.x, bullet.y);
                if (bullet.owner instanceof TurretBuild) {
                    Tmp.v1.set(((TurretBuild) bullet.owner).targetPos.x, ((TurretBuild) bullet.owner).targetPos.y);
                } else if (bullet.owner instanceof Unitc) {
                    Tmp.v1.set(((Unitc) bullet.owner).aimX(), ((Unitc) bullet.owner).aimY());
                } else if (bullet.owner instanceof Targeting) {
                    Tmp.v1.set(((Targeting) bullet.owner).targetPos());
                }
                bullet.vel.setAngle(Angles.moveToward(bullet.rotation(), bullet.angleTo(Tmp.v1.x, Tmp.v1.y), Time.delta * 261f * bullet.fin()));
                if (bullet.within(Tmp.v1.x, Tmp.v1.y, bullet.hitSize)) {
                    bullet.fdata = 1;
                }
            }
        }
    }
}
