package sunset.world.blocks.defense.turrets;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.annotations.Annotations.Load;
import mindustry.core.UI;
import mindustry.entities.bullet.BulletType;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.consumers.ConsumePower;
import mindustry.world.meta.Stat;
import sunset.SnVars;
import sunset.graphics.SnPal;
import sunset.utils.Utils;

import static mindustry.Vars.minArmorDamage;
import static mindustry.Vars.tilesize;

public class SynthesisTurret extends ItemTurret {
    @Load("@-liquid")
    public TextureRegion liquid;
    public float armor;
    public int speed = 1;
    public float powerUse;
    public float powerStored;

    public float maxReloadMultiplier = 0.5f;
    public float speedupPerShot = 0.125f;
    public float slowReloadTime = 70;

    public SynthesisTurret(String name) {
        super(name);
        unitSort = (u, x, y) -> -u.armor;
        powerUse = 1;
    }

    @Override
    public void init(){
        consumes.powerCond(powerUse, TurretBuild::isShooting);
        consumes.powerBuffered(powerStored);
        super.init();
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.armor, armor);
        stats.add(Stat.powerUse, powerUse);
        stats.add(Stat.powerCapacity, powerStored);
    }

    @Override
    public void setBars() {
        super.setBars();
        SnVars.settings.registerReloadBarBlock(this, (SynthesisBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reload / reloadTime) * 100f)),
                () -> entity.team.color,
                () -> Mathf.clamp(entity.reload / reloadTime)
        ));
        ConsumePower cons = consumes.getPower();
        boolean buffered = cons.buffered;
        float capacity = cons.capacity;
        bars.add("power", entity -> new Bar(() -> buffered ? Core.bundle.format("bar.poweramount", Float.isNaN(entity.power.status * capacity) ? "<ERROR>" : UI.formatAmount((int)(entity.power.status * capacity))) :
                Core.bundle.get("bar.power"), () -> Pal.powerBar, () -> Mathf.zero(cons.requestedPower(entity)) && entity.power.graph.getPowerProduced() + entity.power.graph.getBatteryStored() > 0f ? 1f : entity.power.status));
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.placing);
        if (minRange > 0) Drawf.dashCircle(x, y, minRange, Pal.health);
    }

    public class SynthesisBuild extends ItemTurretBuild {
        public float speedupScl = 0f;
        public float slowDownReload = 0f;

        @Override
        public void updateTile() {
            super.updateTile();

            if (this.health < maxHealth / 10) {
                if (slowDownReload >= 1f) {
                    slowDownReload -= Time.delta;
                } else speedupScl = Mathf.lerpDelta(speedupScl, 0f, 0.05f);
            }
        }

        @Override
        protected void updateShooting() {
            if (this.health < maxHealth / 10) {
                if (reload >= reloadTime && !charging) {
                    BulletType type = peekAmmo();
                    shoot(type);
                    reload %= reloadTime;
                } else {
                    reload += (1 + speedupScl) * delta() * peekAmmo().reloadMultiplier * baseReloadSpeed();
                }
            } else {
                super.updateShooting();
            }
        }

        @Override
        protected void shoot(BulletType b) {
            super.shoot(b);

            if (this.health < maxHealth / 10) {
                slowDownReload = slowReloadTime;
                if (speedupScl < maxReloadMultiplier) {
                    speedupScl += speedupPerShot;
                } else speedupScl = maxReloadMultiplier;
            } else {
                super.shoot(b);
            }
        }

        @Override
        public void draw() {
            Draw.rect(baseRegion, x, y);
            Draw.color();
            Draw.z(Layer.turret);
            tr2.trns(rotation, -recoil);
            Drawf.shadow(region, x + tr2.x - elevation, y + tr2.y - elevation, rotation - 90);
            drawer.get(this);

            if (heatRegion != Core.atlas.find("error")) {
                heatDrawer.get(this);
            }

            if (size > 2) Drawf.liquid(liquid, x + tr2.x, y + tr2.y, liquids.total() / liquidCapacity, SnPal.synthesis1);
        }

        @Override
        public void drawSelect() {
            Drawf.dashCircle(x, y, range, team.color);
            if (minRange > 0) Drawf.dashCircle(x, y, minRange, Pal.health);
        }

        @Override
        public float handleDamage(float amount) {
            return Math.max(amount - armor, minArmorDamage * amount); //TODO make my own variant of armor applying (% and etc...)
        }

        //TODO make a visual display of the armor
    }
}
