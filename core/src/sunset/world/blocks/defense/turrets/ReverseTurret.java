package sunset.world.blocks.defense.turrets;

import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.bullet.BulletType;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import sunset.entities.bullet.ReverseBulletType;

public class ReverseTurret extends ItemTurret {

    public ReverseTurret(String name) {
        super(name);
    }

    public class ReverseTurretBuild extends ItemTurretBuild {
        @Override
        protected void shoot(BulletType type) {

            for (int i = 0; i < shots; i++) {
                final int[] b = new int[]{i};
                Time.run(i * burstSpacing/timeScale, () -> {
                    if(this == null || this.dead || !this.isAdded()) return;;
                    int a = b[0];
                    tr.trns(a * spread + rotation, shootLength);

                    float lifeScl = type.scaleVelocity ? Mathf.clamp(Mathf.dst(x + tr.x, y + tr.y, targetPos.x, targetPos.y) / type.range(), minRange / type.range(), range / type.range()) : 1f;

                    ((ReverseBulletType) type).createReverse(this, team, x + tr.x, y + tr.y,  spread + rotation, type.damage, 1f + Mathf.range(velocityInaccuracy), lifeScl, this);
                });
            }
            shotCounter++;

            recoil = recoilAmount;
            heat = 1f;
            useAmmo();
        }
    }
}
