package sunset.world.blocks.defense.turrets;

import acontent.world.meta.AStats;
import arc.Core;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.bullet.BulletType;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mma.graphics.ADrawf;
import sunset.SnVars;
import sunset.utils.Utils;
import sunset.world.meta.SnStat;

public class MinigunTurret extends ItemTurret {
    public float inaccuracyUp = 0f;
    public float maxShootTime = 120f;
    public AStats aStats = new AStats();
    public boolean debug = false, reloadBar = true;

    public MinigunTurret(String name) {
        super(name);
        stats = aStats.copy(stats);
    }

    @Override
    public void setBars() {
        super.setBars();
        bars.add("sunset-heat", (MinigunTurret.MinigunTurretBuild entity) -> new Bar(
        () -> Core.bundle.format("bar.sunset-heat", Utils.stringsFixed(Mathf.clamp(entity.totalShootingTime / maxShootTime) * 100f)),
        () -> entity.team.color,
        () -> Mathf.clamp(entity.totalShootingTime / maxShootTime)
        ));
        if (reloadBar) {
            SnVars.settings.registerReloadBarBlock(this, (ItemTurretBuild entity) -> new Bar(
                    () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reload / reloadTime) * 100f)),
                    () -> entity.team.color,
                    () -> Mathf.clamp(entity.reload / reloadTime)
            ));
        }
    }

    @Override
    public void setStats() {
        super.setStats();
        aStats.add(Stat.inaccuracy, inaccuracyUp, StatUnit.degrees);
        aStats.add(SnStat.overheatTime, maxShootTime / Time.toSeconds, StatUnit.seconds);
    }

    public class MinigunTurretBuild extends ItemTurretBuild{
        boolean isShoot = false;
        float totalShootingTime = 0;
        @Override
        protected void shoot(BulletType type) {
            isShoot = false;
            super.shoot(type);
        }
        @Override
        protected void updateShooting() {
            boolean canShoot = reload + delta() * peekAmmo().reloadMultiplier * baseReloadSpeed() >= reloadTime && !charging;
            this.isShoot = canShoot;
            super.updateShooting();
            if(!canShoot || isShoot) return;
            if (totalShootingTime < maxShootTime) {
                if (liquids.currentAmount() == 0) totalShootingTime += Time.delta / 0.1f;
                else totalShootingTime += Time.delta / liquids.current().heatCapacity;
            }
        }

        @Override
        public void bullet(BulletType type, float angle) {
            super.bullet(type, angle + Mathf.range(inaccuracyUp * (totalShootingTime / 2)));
        }

        @Override
        public void updateTile() {
            super.updateTile();
            if(!wasShooting) {
                totalShootingTime = Math.max(0, totalShootingTime - Time.delta);
            }
        }

        @Override
        protected void updateCooling() {
            super.updateCooling();
        }

        @Override
        public void draw() {
            super.draw();
            if (debug) ADrawf.drawText(this, "totalShootingTime: " + totalShootingTime);
        }

        @Override
        public BulletType useAmmo() {
            return super.useAmmo();
        }
    }
}
