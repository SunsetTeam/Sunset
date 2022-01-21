package sunset.world.blocks.defense.turrets;

import acontent.world.meta.AStat;
import acontent.world.meta.AStatCat;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.annotations.Annotations.Load;
import mindustry.entities.bullet.BulletType;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;
import sunset.SnVars;
import sunset.content.SnFx;
import sunset.graphics.SnPal;
import sunset.utils.Utils;

import static mindustry.Vars.minArmorDamage;
import static mindustry.Vars.tilesize;

public class SynthesisTurret extends ItemTurret {
    @Load("@-liquid")
    public TextureRegion liquid;
    public int speed = 1;
    public float powerUse;

    public float maxReloadMultiplier = 0.5f;
    public float speedupPerShot = 0.125f;
    public float slowReloadTime = 70;

    public boolean drawShields = true;
    public float primaryArmor = 0;
    public float maxShield = health;
    public float regenCooldown = 2 * Time.toSeconds;
    public float regenAmount = 1;
    public float secondaryArmor = 0;

    public SynthesisTurret(String name) {
        super(name);
        unitSort = (u, x, y) -> -u.armor;
        powerUse = 1;
        update = true;
        sync = true;
    }

    @Override
    public void init(){
        consumes.powerCond(powerUse, TurretBuild::isShooting);

        super.init();
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.armor, primaryArmor);
        stats.add(Stat.armor, secondaryArmor);
        stats.add(Stat.powerUse, powerUse);
    }

    @Override
    public void setBars() {
        super.setBars();
        SnVars.settings.registerReloadBarBlock(this, (SynthesisBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reload / reloadTime) * 100f)),
                () -> entity.team.color,
                () -> Mathf.clamp(entity.reload / reloadTime)
        ));
        bars.add("shield", (SynthesisBuild entity) -> new Bar(Core.bundle.format("bar.shield"),
                Pal.accent,
                ()-> entity.shield / health));
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

        float shield;
        float shieldAlpha;
        float heat = 0f;

        @Override
        public void updateTile() {
            super.updateTile();
            if (this.health < maxHealth / 10) {
                if (slowDownReload >= 1f) {
                    slowDownReload -= Time.delta;
                } else speedupScl = Mathf.lerpDelta(speedupScl, 0f, 0.05f);
            }
            shieldAlpha -= delta() / 15f;
            if(shieldAlpha < 0) shieldAlpha = 0f;

            heat -= delta();
            if(heat <= 0f && shield < maxShield) shield += regenAmount * delta();
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

            if(shieldAlpha > 0 && drawShields) drawShield();
        }

        @Override
        public void drawSelect() {
            Drawf.dashCircle(x, y, range, team.color);
            if (minRange > 0) Drawf.dashCircle(x, y, minRange, Pal.health);
        }

        //armor zone
        //primary armor (active)
        @Override
        public void created() {
            super.created();
            shield = maxShield = health;
        }

        public void drawShield(){
            float alpha = shieldAlpha;
            float radius = block.size * Vars.tilesize * 1.3f;
            Draw.z(Layer.blockOver);
            Fill.light(x, y, Lines.circleVertices(radius), radius, Tmp.c1.set(Pal.shield), Tmp.c2.set(Pal.shield).lerp(Color.white, Mathf.clamp(hitTime() / 2f)).a(Pal.shield.a * alpha));
            Draw.reset();
        }

        @Override
        public void damage(float amount){
            rawDamage(Math.max(amount - primaryArmor, minArmorDamage * amount));
        }

        @Override
        public void damagePierce(float amount, boolean withEffect){
            float pre = hitTime;

            rawDamage(amount);

            if(!withEffect) hitTime = pre;
        }

        protected void rawDamage(float amount){
            boolean hadShields = shield > 0.0001f;

            if(hadShields) shieldAlpha = 1f;
            heat = regenCooldown;

            float shieldDamage = Math.min(Math.max(shield, 0), amount);
            shield -= shieldDamage;
            hitTime = 1f;
            amount -= shieldDamage;

            if(amount > 0){
                health -= amount;
                if(health <= 0 && !dead()) kill();

                if(hadShields && shield <= 0.0001f) SnFx.blockShieldBreak.at(x, y, 0, this);
            }
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(shield);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            shield = read.f();
        }

        //secondary armor (passive)
        @Override
        public float handleDamage(float amount) {
            if (secondaryArmor != 0) {
                return Math.max(amount - secondaryArmor, minArmorDamage * amount); //TODO make my own variant of secondary armor applying (% and etc...)
            } else return super.handleDamage(amount);
        }

        //TODO make a visual display of the secondary armor
    }
}
