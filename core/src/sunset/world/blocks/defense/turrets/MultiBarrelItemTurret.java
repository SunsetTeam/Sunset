package sunset.world.blocks.defense.turrets;

import arc.Core;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.entities.bullet.BulletType;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import sunset.SnVars;
import sunset.utils.Utils;

import static arc.Core.settings;

/**
 * Описывает многоствольную турель.
 */
public class MultiBarrelItemTurret extends ItemTurret {
    public Seq<Vec2> barrelPoints = new Seq<>();
    public Seq<Vec2> ejectPoints = new Seq<>();

    public float maxReloadMultiplier = 0.5f;
    public float speedupPerShot = 0.075f;
    public float slowReloadTime = 90f;

    public MultiBarrelItemTurret(String name) {
        super(name);
    }

    @Override
    public void setBars() {
        super.setBars();
        SnVars.settings.registerReloadBarBlock(this,(MultiBarrelTurretBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reload / reloadTime) * 100f)),
                () -> entity.team.color,
                () -> Mathf.clamp(entity.reload / reloadTime)
        ));
        bars.add("sunset-speedup", (MultiBarrelTurretBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.sunset-speedup", Utils.stringsFixed(Mathf.clamp(reloadTime / slowReloadTime) * 100f)),
                () -> entity.team.color,
                () -> Mathf.clamp(reloadTime / slowReloadTime)
        ));
    }

    public class MultiBarrelTurretBuild extends ItemTurretBuild {
        public float speedupScl = 0f;
        public float slowDownReload = 0f;
        public float maxReloadTime = 0f;

        @Override
        public void updateTile() {
            super.updateTile();
            if (slowDownReload >= 1f) {
                slowDownReload -= Time.delta;
            } else speedupScl = Mathf.lerpDelta(speedupScl, 0f, 0.05f);
        }

        @Override
        protected void updateShooting() {
            if (reload >= reloadTime) {
                BulletType type = peekAmmo();
                shoot(type);
                reload = 0f;
            } else {
                reload += (1 + speedupScl) * delta() * peekAmmo().reloadMultiplier * baseReloadSpeed();
            }
        }

        @Override
        protected void shoot(BulletType type) {

            slowDownReload = slowReloadTime;
            if (speedupScl < maxReloadMultiplier) {
                speedupScl += speedupPerShot;
            } else speedupScl = maxReloadMultiplier;

            for (int i = 0; i < barrelPoints.size; i++) {
                final int index = i;
                Time.run(burstSpacing * i, () -> {
                    if (!isValid() || !hasAmmo()) return;
                    recoil = recoilAmount;
                    Vec2 p = barrelPoints.get(index);
                    tr.trns(rotation - 90, (p.x - 0.5f) * Vars.tilesize * size,
                            (-p.y + 0.5f) * Vars.tilesize * size);
                    bullet(type, rotation + Mathf.range(inaccuracy));
                    p = new Vec2(ejectPoints.get(index));
                    p.trns(rotation - 90, (p.x - 0.5f) * Vars.tilesize * size,
                            (p.y - 0.5f) * Vars.tilesize * size);
                    ammoUseEffect.at(x + p.x, y + p.y, ejectPoints.get(index).x > 0.5f ? rotation : rotation + 180f);
                    effects();
                    useAmmo();
                    recoil = recoilAmount;
                    heat = 1;
                });
            }
        }

        @Override
        protected void ejectEffects() {
            // эффекты в shoot()
        }
    }
}