package sunset.world.blocks.defense.turrets;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.annotations.Annotations.Load;
import mindustry.content.Fx;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.meta.Stat;
import sunset.graphics.SnPal;
import sunset.utils.Utils;

import static mindustry.Vars.minArmorDamage;

public class SynthesisTurret extends ItemTurret {
    @Load("@-liquid")
    public TextureRegion liquid;
    @Load("@-light")
    public TextureRegion light;
    public static float armor = 50;
    //public static int param1;
    //public int addSpeed;
    public int speed = 1;
    float shield;
    transient float shieldAlpha = 0f;

    public SynthesisTurret(String name, float armor) {
        super(name);
    }

    public SynthesisTurret(String name) {
        super(name);
        //armor = 50;
        unitSort = (u, x, y) -> -u.armor;
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.armor, armor);
    }

    @Override
    public void setBars() {
        super.setBars();
        bars.add("sunset-reload", (SynthesisBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reload / reloadTime) * 100f)),
                () -> entity.team.color,
                () -> Mathf.clamp(entity.reload / reloadTime)
        ));
    }
    public class SynthesisBuild extends ItemTurretBuild {
        @Override
        public void updateTile() {
            super.updateTile();

            /* * Increase shooting speed if health less or equal 10%.
             * If parameter speed = 1, shooting speed not be increased.
            if(this.health <= maxHealth * 100 / 10) {
                addSpeed = speed;
            }else{
                addSpeed = 1;
            }*/
        }

        @Override
        public void draw() {
            Draw.rect(baseRegion, x, y);
            Draw.color();
            Draw.z(Layer.turret);
            tr2.trns(rotation, -recoil);
            Drawf.shadow(region, x + tr2.x - elevation, y + tr2.y - elevation, rotation - 90);
            drawer.get(this);
            if (this.health <= maxHealth * 100 / 10) Draw.rect(light, x + tr2.x, y + tr2.y, rotation - 90);
            if (this.isShooting())
            if (this.wasShooting)
            if (heatRegion != Core.atlas.find("error")) {
                heatDrawer.get(this);
            }
            if (size > 2) Drawf.liquid(liquid, x + tr2.x, y + tr2.y, liquids.total() / liquidCapacity, SnPal.synthesis1);
        }

        @Override
        public void drawSelect() {
            Drawf.dashCircle(x, y, range, Pal.heal);
            if (minRange > 0) Drawf.dashCircle(x, y, minRange, Pal.health);
        }

        /*@Override
        public void displayBars(Table bars) {
            super.displayBars(bars);
            if (armor > 0) {
                bars.add(new Bar("stat.armor", Pal.shield, () -> shield)).growX();
                bars.row();
            }
        }*/

        @Override
        public float handleDamage(float amount) {
            return Math.max(amount - armor, minArmorDamage * amount);
        }

        @Override
        public void damage(float amount) {
            //apply armor
            amount = Math.max(amount - armor, minArmorDamage * amount);

            rawDamage(amount);
        }

        private void rawDamage(float amount) {
            boolean hadShields = shield > 0.0001f;

            if(hadShields) {
                shieldAlpha = 1f;
            }

            float shieldDamage = Math.min(Math.max(shield, 0), amount);
            shield -= shieldDamage;
            hitTime = 1f;
            amount -= shieldDamage;

            if (amount > 0) {
                health -= amount;
                if(health <= 0 && !dead) {
                    kill();
                }

                if (hadShields && shield <= 0.0001f) {
                    Fx.unitShieldBreak.at(x, y, 0, this);
                }
            }
        }
    }
}
