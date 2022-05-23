package sunset.world.blocks.defense.turrets;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.annotations.Annotations.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;
import mma.*;
import sunset.world.meta.*;

/**
 * Turret that attacks use constant
 * chain lightning, "jumping" from one enemy to another,
 * gradually losing damage.
 * @see sunset.world.blocks.defense.turrets.ChainLightningTurret.ChainLightningTurretBuild
 */
public class ChainLightningTurret extends BaseTurret{
    public float damage = 0f;
    /**
     * Determination of how much the maximum will decrease
     * range of detection of the enemy by the beam after application
     * damage to the next enemy.
     */
    public float rangeMultiplier = 0.9f;
    /**
     * Determines how much less damage will be received
     * each next enemy in the chain.
     */
    public float damageMultiplier = 0.9f;
    public boolean targetAir = true, targetGround = true;
    public float coolantMultiplier = 1.5f;
    public float liquidUse = 0f;
    public float shootCone = 0f;
    public float powerUse = 0f;
    @Load("block-@size")
    public TextureRegion baseRegion;
    @Load("parallax-laser")
    public TextureRegion laser;
    @Load("parallax-laser-end")
    public TextureRegion laserEnd;
    public float laserWidth = 0.7f;
    public Color laserColor;

    public ChainLightningTurret(String name){
        super(name);
        category = Category.turret;
        buildVisibility = BuildVisibility.shown;
        hasLiquids = true;
    }

    @Override
    public void init(){
        consumePowerCond(powerUse, ChainLightningTurretBuild::isActive);
        coolant = (ConsumeLiquidBase)consumeCoolant(liquidUse).update(false).boost();
        liquidCapacity = liquidUse * 60f;
        super.init();
    }

    @Override
    public void load(){
        super.load();
//        laser = Core.atlas.find("parallax-laser");
//        laserEnd = Core.atlas.find("parallax-laser-end");
//        baseRegion = Core.atlas.find("block-" + size);
//        SnContentRegions.loadRegions(this);
    }

    @Override
    public TextureRegion[] icons(){
//        return super.makeIconRegions();
        return !ModVars.packSprites ? new TextureRegion[]{region} : new TextureRegion[]{baseRegion, region};
    }


    @Override
    public void createIcons(MultiPacker packer){
        super.createIcons(packer);
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.targetsAir, targetAir);
        stats.add(Stat.targetsGround, targetGround);
        stats.add(Stat.damage, damage * 60f, StatUnit.perSecond);
        stats.add(Stat.booster, SnStatValues.boosterLiquidList(
        liquid -> liquid.temperature <= 0.5f && liquid.flammability < 0.1f,
        liquid -> {
            float used = Math.min(liquidUse, Math.max(0, (1f / coolantMultiplier) / liquid.heatCapacity));
            return 1f + (used * liquid.heatCapacity * coolantMultiplier);
        }, "bullet.damagefactor"));
    }

    /**
     * @see sunset.world.blocks.defense.turrets.ChainLightningTurret
     */
    public class ChainLightningTurretBuild extends BaseTurretBuild{
        public final Seq<Unit> units = new Seq<>();
        private boolean haveTarget = false;
        private boolean shouldShoot = false;
        private float liquidBoost = 1f;

        private long boostEndTime = 0;
        private float boost = 0f;

        public boolean isActive(){
            return haveTarget;
        }

        public float getBoost(){
            return Time.millis() <= boostEndTime ? Math.max(boost, 1) : 1;
        }

        @Override
        public void applyBoost(float intensity, float duration){
            boostEndTime = Time.millis() + (long)(duration * 1000f / 60f);
            boost = intensity;
        }

        @Override
        public void updateTile(){
            //liquid
            if(shouldShoot){
                Liquid liquid = liquids.current();
                float used = Math.min(Math.min(liquids.get(liquid), liquidUse), Math.max(0, (1f / coolantMultiplier) / liquid.heatCapacity));
                liquids.remove(liquid, used);
                liquidBoost = 1f + (used * liquid.heatCapacity * coolantMultiplier);
            }
            //recalculating units
            float r = range;
            units.clear();
            Unit unit = Units.closestEnemy(team, x, y, r, u -> u.checkTarget(targetAir, targetGround));
            while(unit != null){
                units.add(unit);
                r *= rangeMultiplier;
                unit = Units.closestEnemy(team, unit.x, unit.y, r,
                u -> !units.contains(u) && u.checkTarget(targetAir, targetGround));
            }
            haveTarget = !units.isEmpty();
            shouldShoot = haveTarget && Angles.within(angleTo(units.first()), rotation, shootCone) && efficiency() > 0.01f;
            // damage
            if(shouldShoot){
                float[] d = {damage * efficiency() * liquidBoost};
                units.each(enemy -> {
                    enemy.damageContinuousPierce(d[0]);
                    d[0] *= damageMultiplier;
                });
            }
            //rotation
            if(units.size > 0){
                rotation = Angles.moveToward(rotation, angleTo(units.first()), rotateSpeed * edelta());
            }
        }

        @Override
        public float efficiency(){
            return super.efficiency() * getBoost();
        }

        @Override
        public void draw(){
            Draw.rect(baseRegion, x, y);
            if(shouldShoot){
                Draw.z(Layer.bullet);
                float ef = Math.min(1f, efficiency());
                Draw.mixcol(laserColor, 0.85f + Mathf.absin(0.8f, 0.15f));
                float unitX = units.get(0).x, unitY = units.get(0).y, nextUnitX = unitX, nextUnitY = unitY;
                float lw = laserWidth * 0.8f + Mathf.absin(4f, laserWidth * 0.2f);
                Drawf.laser( laser, laserEnd, x, y, unitX, unitY, lw * ef);
                for(int i = 0; i < units.size - 1; i++){
                    unitX = nextUnitX;
                    unitY = nextUnitY;
                    nextUnitX = units.get(i + 1).x;
                    nextUnitY = units.get(i + 1).y;
                    Drawf.laser( laser, laserEnd, unitX, unitY, nextUnitX, nextUnitY, lw);
                }
            }
            Draw.mixcol();
            Drawf.shadow(region, x - (size / 2f), y - (size / 2f), rotation - 90);
            Draw.rect(region, x, y, rotation - 90);
        }
    }
}