package sunset.world.blocks.sandbox;

import acontent.world.meta.AStats;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.meta.StatUnit;
import sunset.world.meta.SnStat;

public class InfoTestBlock extends Block {
    public AStats aStats = new AStats();
    public int nullStat = 0;

    public String faction;
    public String subFaction;
    public String branch;

    public InfoTestBlock(String name) {
        super(name);
        this.faction = "null";
        this.subFaction = "null";
        this.branch = "null";
        stats = aStats.copy(stats);
        destructible = true;
        solid = true;
        update = true;
        buildVisibility = BuildVisibility.sandboxOnly;
        category = Category.effect;
        hideDetails = false;
    }
    @Override
    public void setStats() {
        aStats.add(SnStat.health, nullStat, StatUnit.none);
        aStats.add(SnStat.armor, nullStat, StatUnit.none);
        aStats.add(SnStat.size, nullStat, StatUnit.none);
        aStats.add(SnStat.displaySize, nullStat, StatUnit.none);
        aStats.add(SnStat.buildTime, nullStat, StatUnit.none);
        aStats.add(SnStat.buildCost, nullStat, StatUnit.none);
        aStats.add(SnStat.memoryCapacity, nullStat, StatUnit.none);

        aStats.add(SnStat.explosiveness, nullStat, StatUnit.none);
        aStats.add(SnStat.flammability, nullStat, StatUnit.none);
        aStats.add(SnStat.radioactivity, nullStat, StatUnit.none);
        aStats.add(SnStat.charge, nullStat, StatUnit.none);

        aStats.add(SnStat.heatCapacity, nullStat, StatUnit.none);
        aStats.add(SnStat.viscosity, nullStat, StatUnit.none);
        aStats.add(SnStat.temperature, nullStat, StatUnit.none);

        aStats.add(SnStat.flying, nullStat, StatUnit.none);
        aStats.add(SnStat.speed, nullStat, StatUnit.none);
        aStats.add(SnStat.buildSpeed, nullStat, StatUnit.none);
        aStats.add(SnStat.mineSpeed, nullStat, StatUnit.none);
        aStats.add(SnStat.mineTier, nullStat, StatUnit.none);
        aStats.add(SnStat.payloadCapacity, nullStat, StatUnit.none);
        aStats.add(SnStat.commandLimit, nullStat, StatUnit.none);

        aStats.add(SnStat.baseDeflectChance, nullStat, StatUnit.none);
        aStats.add(SnStat.lightningChance, nullStat, StatUnit.none);
        aStats.add(SnStat.lightningDamage, nullStat, StatUnit.none);

        aStats.add(SnStat.abilities, nullStat, StatUnit.none);
        aStats.add(SnStat.canBoost, nullStat, StatUnit.none);

        aStats.add(SnStat.maxUnits, nullStat, StatUnit.none);

        aStats.add(SnStat.damageMultiplier, nullStat, StatUnit.none);
        aStats.add(SnStat.healthMultiplier, nullStat, StatUnit.none);
        aStats.add(SnStat.speedMultiplier, nullStat, StatUnit.none);
        aStats.add(SnStat.reloadMultiplier, nullStat, StatUnit.none);
        aStats.add(SnStat.buildSpeedMultiplier, nullStat, StatUnit.none);
        aStats.add(SnStat.reactive, nullStat, StatUnit.none);
        aStats.add(SnStat.healing, nullStat, StatUnit.none);

        aStats.add(SnStat.powerCapacity, nullStat, StatUnit.none);
        aStats.add(SnStat.powerUse, nullStat, StatUnit.none);
        aStats.add(SnStat.powerDamage, nullStat, StatUnit.none);
        aStats.add(SnStat.powerRange, nullStat, StatUnit.none);
        aStats.add(SnStat.powerConnections, nullStat, StatUnit.none);
        aStats.add(SnStat.basePowerGeneration, nullStat, StatUnit.none);

        aStats.add(SnStat.itemCapacity, nullStat, StatUnit.none);
        aStats.add(SnStat.itemsMoved, nullStat, StatUnit.none);
        aStats.add(SnStat.launchTime, nullStat, StatUnit.none);
        aStats.add(SnStat.maxConsecutive, nullStat, StatUnit.none);

        aStats.add(SnStat.liquidCapacity, nullStat, StatUnit.none);

        aStats.add(SnStat.tiles, nullStat, StatUnit.none);
        aStats.add(SnStat.input, nullStat, StatUnit.none);
        aStats.add(SnStat.output, nullStat, StatUnit.none);
        aStats.add(SnStat.productionTime, nullStat, StatUnit.none);
        aStats.add(SnStat.drillTier, nullStat, StatUnit.none);
        aStats.add(SnStat.drillSpeed, nullStat, StatUnit.none);
        aStats.add(SnStat.linkRange, nullStat, StatUnit.none);
        aStats.add(SnStat.instructions, nullStat, StatUnit.none);

        aStats.add(SnStat.weapons, nullStat, StatUnit.none);
        aStats.add(SnStat.bullet, nullStat, StatUnit.none);

        aStats.add(SnStat.speedIncrease, nullStat, StatUnit.none);
        aStats.add(SnStat.repairTime, nullStat, StatUnit.none);
        aStats.add(SnStat.repairSpeed, nullStat, StatUnit.none);
        aStats.add(SnStat.range, nullStat, StatUnit.none);
        aStats.add(SnStat.shootRange, nullStat, StatUnit.none);
        aStats.add(SnStat.inaccuracy, nullStat, StatUnit.none);
        aStats.add(SnStat.shots, nullStat, StatUnit.none);
        aStats.add(SnStat.reload, nullStat, StatUnit.none);
        aStats.add(SnStat.powerShot, nullStat, StatUnit.none);
        aStats.add(SnStat.targetsAir, nullStat, StatUnit.none);
        aStats.add(SnStat.targetsGround, nullStat, StatUnit.none);
        aStats.add(SnStat.damage, nullStat, StatUnit.none);
        aStats.add(SnStat.ammo, nullStat, StatUnit.none);
        aStats.add(SnStat.ammoUse, nullStat, StatUnit.none);
        aStats.add(SnStat.shieldHealth, nullStat, StatUnit.none);
        aStats.add(SnStat.cooldownTime, nullStat, StatUnit.none);

        aStats.add(SnStat.booster, nullStat, StatUnit.none);
        aStats.add(SnStat.boostEffect, nullStat, StatUnit.none);

        aStats.add(SnStat.affinities, nullStat, StatUnit.none);
        aStats.add(SnStat.opposites, nullStat, StatUnit.none);

        aStats.add(SnStat.laserProduction, nullStat, StatUnit.none);
        aStats.add(SnStat.laserConsumption, nullStat, StatUnit.none);
        aStats.add(SnStat.maxCharge, nullStat, StatUnit.none);
        aStats.add(SnStat.maxLinks, nullStat, StatUnit.none);

        aStats.add(SnStat.primaryArmor, nullStat, StatUnit.none);
        aStats.add(SnStat.secondaryArmor, nullStat, StatUnit.none);

        aStats.add(SnStat.pierceDamageDebuff, nullStat, StatUnit.none);
        aStats.add(SnStat.pierceMultiplier, nullStat, StatUnit.none);

        aStats.add(SnStat.maxControlled, nullStat, StatUnit.none);

        aStats.add(SnStat.minimalRange, nullStat, StatUnit.none);

        aStats.add(SnStat.nullStat, nullStat, StatUnit.none);

        aStats.add(SnStat.faction, faction, StatUnit.none);
        aStats.add(SnStat.subFaction, subFaction, StatUnit.none);
        aStats.add(SnStat.branch, branch, StatUnit.none);
    }
}
