package sunset.world.blocks.turrets;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.entities.Units;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.type.Category;
import mindustry.type.Liquid;
import mindustry.world.blocks.defense.turrets.BaseTurret;
import mindustry.world.consumers.ConsumeLiquidFilter;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import sunset.world.consumers.AdjustableConsumePower;
import sunset.world.meta.values.BoosterLiquidList;

/**
 * Представляет турель, которая атакует врагов постоянной
 * цепной молнией, "перепрыгивающей" от одного врага к другому,
 * постепенно теряя урон.
 * @see sunset.world.blocks.turrets.ChainLightningTurret.ChainLightningTurretBuild
 */
public class ChainLightningTurret extends BaseTurret {
    public float damage = 0f;
    /** Определяет, насколько будет уменьшаться максимальная
     * дальность обнружения врага лучом после нанесения
     * урона очереджному врагу.
     */
    public float rangeMultiplier = 0.9f;
    /** Определяет, насколько меньший урон будет получать
     * каждый следующий враг в цепи.
     */
    public float damageMultiplier = 0.9f;
    public boolean targetAir = true, targetGround = true;
    public float coolantMultiplier = 1.5f;
    public float liquidUse = 0f;
    public float shootCone = 0f;
    public TextureRegion baseRegion;
    public TextureRegion laser, laserEnd;
    public float laserWidth = 0.7f;
    public float powerUse;
    public Color laserColor;
    public ChainLightningTurret(String name) {
        super(name);
        category = Category.turret;
        buildVisibility = BuildVisibility.shown;
        hasLiquids = true;
    }

    @Override
    public void load() {
        super.load();
        laser = Core.atlas.find("parallax-laser");
        laserEnd = Core.atlas.find("parallax-laser-end");
        baseRegion = Core.atlas.find("block-" + size);
        consumes.add(new AdjustableConsumePower(powerUse, e -> {
            ChainLightningTurretBuild t = (ChainLightningTurretBuild)e;
            return t.shouldShoot ? t.getBoost() : 0f;
        }));
        consumes.add(new ConsumeLiquidFilter(liquid -> liquid.temperature <= 0.5f && liquid.flammability < 0.1f, liquidUse)).update(false).boost();
        liquidCapacity = liquidUse * 60f;
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[] { baseRegion, region };
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.remove(Stat.powerUse);
        stats.add(Stat.powerUse, powerUse * 60f, StatUnit.powerSecond);
        stats.add(Stat.targetsAir, targetAir);
        stats.add(Stat.targetsGround, targetGround);
        stats.add(Stat.damage, damage * 60f, StatUnit.perSecond);
        stats.add(Stat.booster, new BoosterLiquidList(
                liquid -> liquid.temperature <= 0.5f && liquid.flammability < 0.1f,
                liquid -> {
                    float used = Math.min(liquidUse, Math.max(0, (1f / coolantMultiplier) / liquid.heatCapacity));
                    return 1f + (used * liquid.heatCapacity * coolantMultiplier);
                }, "stat.damage"));
    }
    /** @see sunset.world.blocks.turrets.ChainLightningTurret */
    public class ChainLightningTurretBuild extends BaseTurretBuild {
        public Seq<Unit> units = new Seq<>();
        public boolean shouldShoot = false;
        private float liquidBoost = 1f;

        private long boostEndTime = 0;
        private float boost = 0f;
        public float getBoost(){
            return Time.millis()<=boostEndTime ? Math.max(boost, 1) : 1;
        }
        @Override
        public void applyBoost(float intensity, float duration) {
            boostEndTime = Time.millis() + (long)(duration*1000f/60f);
            boost = intensity;
        }

        @Override
        public void updateTile() {
            //liquid
            if(shouldShoot) {
                Liquid liquid = liquids.current();
                float used = Math.min(Math.min(liquids.get(liquid), liquidUse), Math.max(0, (1f / coolantMultiplier) / liquid.heatCapacity));
                liquids.remove(liquid, used);
                liquidBoost = 1f + (used * liquid.heatCapacity * coolantMultiplier);
            }
            //recalculating units
            float r = range;
            units.clear();
            Unit unit = Units.closestEnemy(team, x, y, r, u -> u.checkTarget(targetAir, targetGround));
            while (unit != null) {
                units.add(unit);
                r *= rangeMultiplier;
                unit = Units.closestEnemy(team, unit.x, unit.y, r,
                        u -> !units.contains(u) && u.checkTarget(targetAir, targetGround));
            }
            shouldShoot = !units.isEmpty() && Angles.within(angleTo(units.first()), rotation, shootCone);
            // damage
            if(shouldShoot) {
                float[] d = {damage * efficiency() * liquidBoost};
                units.each(enemy -> {
                    enemy.damageContinuousPierce(d[0]);
                    d[0] *= damageMultiplier;
                });
            }
            //rotation
            if(units.size > 0) {
                rotation = Angles.moveToward(rotation, angleTo(units.first()), rotateSpeed * edelta());
            }
        }

        @Override
        public float efficiency() {
            return super.efficiency() * getBoost();
        }

        @Override
        public void draw() {
            Draw.rect(baseRegion, x, y);
            if(shouldShoot) {
                Draw.z(Layer.bullet);
                Draw.mixcol(laserColor, 0.85f+Mathf.absin(0.8f,0.15f));
                float unitX = units.get(0).x, unitY = units.get(0).y, nextUnitX = unitX, nextUnitY = unitY;
                float lw = laserWidth*0.8f + Mathf.absin(4f,laserWidth*0.2f);
                Drawf.laser(team, laser, laserEnd, x, y, unitX, unitY, lw);
                for(int i = 0; i < units.size-1; i++)
                {
                    unitX = nextUnitX;
                    unitY = nextUnitY;
                    nextUnitX = units.get(i+1).x;
                    nextUnitY = units.get(i+1).y;
                    Drawf.laser(team, laser, laserEnd, unitX, unitY, nextUnitX, nextUnitY, lw);
                }
            }
            Draw.mixcol();
            Drawf.shadow(region, x - (size / 2f), y - (size / 2f), rotation - 90);
            Draw.rect(region, x, y, rotation - 90);
        }
    }
}