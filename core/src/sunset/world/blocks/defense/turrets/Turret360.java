package sunset.world.blocks.defense.turrets;

import arc.Core;
import arc.math.Mathf;
import mindustry.entities.bullet.BulletType;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mma.graphics.ADrawf;
import sunset.SnVars;
import sunset.utils.Utils;

/** Turret that shoots 360 degrees and rotates optionally.
 * Extends from {@link ItemTurret} for correct work of power shoot.*/
public class Turret360 extends ItemTurret {
    public boolean rotateTurret;

    public BulletType powerBullet;
    public boolean debug = false;
    public int chargeShots;

    public Turret360(String name) {
        super(name);
        rotateTurret = false;
        shootCone = 360;
        shots = 4;
        spread = shootCone / shots;
    }

    @Override
    public void setBars() {
        super.setBars();
        SnVars.settings.registerReloadBarBlock(this, (ItemTurretBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reload / reloadTime) * 100f)),
                () -> entity.team.color,
                () -> Mathf.clamp(entity.reload / reloadTime)
        ));
    }

    public class T360 extends ItemTurretBuild {
        boolean isShoot = false;
        int totalShoots = 1;
        @Override
        public void draw() {
            super.draw();



            if (debug) ADrawf.drawText(this, "Total Shoots: " + totalShoots);
        }

        @Override
        protected void updateShooting() {
            if (powerBullet != null) {
                boolean canShoot = reload + delta() * peekAmmo().reloadMultiplier * baseReloadSpeed() >= reloadTime && !charging;
                this.isShoot = canShoot;
                super.updateShooting();
                if (!canShoot || isShoot) return;
                totalShoots++;
                if (totalShoots == chargeShots) {
                    totalShoots = 0;
                }
            } else {
                super.updateShooting();
            }
        }

        @Override
        protected void shoot(BulletType type) {
            if (powerBullet != null) isShoot = false;
            super.shoot(type);
        }

        @Override
        public void bullet(BulletType type, float angle) {
            tr.trns(this.rotation, 0);

            float lifeScl = type.scaleVelocity ? Mathf.clamp(Mathf.dst(x + tr.x, y + tr.y, targetPos.x, targetPos.y) / type.range(), minRange / type.range(), range / type.range()) : 1f;

            if (totalShoots == 0) {
                powerBullet.create(this, team, x + tr.x, y + tr.y, angle, 1f + Mathf.range(velocityInaccuracy), lifeScl);
            } else {
                type.create(this, team, x + tr.x, y + tr.y, angle, 1f + Mathf.range(velocityInaccuracy), lifeScl);
            }
        }

        @Override
        protected void turnToTarget(float targetRot) {
            if (rotateTurret) {
                rotation += rotateSpeed;
            } else  {
                rotateSpeed = 0;
            }
        }
    }
}
