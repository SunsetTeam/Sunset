package sunset.content.blocks;

import arc.Core;
import arc.struct.ObjectMap;
import arc.struct.ObjectSet;
import arc.util.Time;
import mindustry.Vars;
import mindustry.ctype.ContentList;
import mindustry.entities.abilities.Ability;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.logic.LogicBlock;
import mindustry.world.blocks.logic.LogicDisplay;
import mindustry.world.consumers.ConsumeLiquidBase;
import mindustry.world.consumers.ConsumeType;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;
import sunset.world.blocks.sandbox.InfoTestBlock;
import sunset.world.blocks.sandbox.SnMultiSource;
import sunset.world.blocks.sandbox.SnMultiVoid;
import sunset.world.meta.SnStat;

import static mindustry.Vars.tilesize;

public class SnSandbox implements ContentList {
    public static Block
    //main
    multiSource, multiVoid, superProcessor, superDisplay,
    //info
    infoBlock, infoUnit, infoItem, infoLiquid, infoStatus;

    @Override
    public void load() {
        //region main
        multiSource = new SnMultiSource("multi-source"){{
            requirements(Category.distribution, BuildVisibility.sandboxOnly, ItemStack.empty);
            hideDetails = false;
        }};

        multiVoid = new SnMultiVoid("multi-void"){{
            requirements(Category.distribution, BuildVisibility.sandboxOnly, ItemStack.empty);
            hideDetails = false;
        }};

        superProcessor = new LogicBlock("super-processor"){{
            requirements(Category.logic, BuildVisibility.sandboxOnly, ItemStack.empty);

            size = 1;
            range = 50 * Vars.tilesize;
            instructionsPerTick = 99999;
            maxInstructionScale = 99999;
            hideDetails = false;
        }};

        superDisplay = new LogicDisplay("super-display"){{
            requirements(Category.logic, BuildVisibility.sandboxOnly, ItemStack.empty);

            displaySize = 240;
            size = 9;
            hideDetails = false;
        }};
        //endregion main
        //region info
        infoBlock = new InfoTestBlock("info-block") {{
            info = "Block";
        }
        @Override
        public void setStats() {
            super.setStats();
            aStats.add(SnStat.blockHealth, health, StatUnit.none);
            aStats.add(SnStat.blockSize, "@x@", size, size);
            aStats.add(SnStat.displaySize, "@x@", displaySize, displaySize);
            aStats.add(SnStat.buildTime, buildCost / 60, StatUnit.seconds);
            aStats.add(SnStat.blockBuildCost, StatValues.items(false, requirements));
            aStats.add(SnStat.memoryCapacity, memoryCapacity, StatUnit.none);
            aStats.add(SnStat.baseDeflectChance, chanceDeflect, StatUnit.none);
            aStats.add(SnStat.lightningChance, lightningChance * 100f, StatUnit.percent);
            aStats.add(SnStat.lightningDamage, lightningDamage, StatUnit.none);
            aStats.add(SnStat.maxUnits, (unitCapModifier < 0 ? "-" : "+") + Math.abs(unitCapModifier));

            if(true)throw null;
            aStats.add(SnStat.guild, guild);
            aStats.add(SnStat.subGuild, subGuild);
            aStats.add(SnStat.branch, branch);

            if(true)throw null;
            aStats.add(SnStat.tiles, attribute, floating, size * size, false);
            //aStats.add(SnStat.input, StatValues.boosters(reloadTime, consumes.<ConsumeLiquidBase>get(ConsumeType.liquid).amount, coolantMultiplier, false, l -> consumes.liquidfilters.get(l.id)));
            aStats.add(SnStat.output, StatValues.items(craftTime, outputItem));
            aStats.add(SnStat.output, outputLiquid.liquid, outputLiquid.amount * (60f / craftTime), true);
            aStats.add(SnStat.productionTime, craftTime / 60f, StatUnit.seconds);
            aStats.add(SnStat.drillTier, StatValues.blocks(b -> b instanceof Floor f && f.itemDrop != null && f.itemDrop.hardness <= tier));
            aStats.add(SnStat.drillSpeed, 60f / drillTime * size * size, StatUnit.itemsSecond);
            aStats.add(SnStat.linkRange, range / 8, StatUnit.blocks);
            aStats.add(SnStat.instructions, instructionsPerTick * 60, StatUnit.perSecond);

            if(true)throw null;
            aStats.add(SnStat.speedIncrease, "+" + (int)(speedBoost * 100f - 100) + "%");
            aStats.add(SnStat.repairTime, (int)(100f / healPercent * reload / 60f), StatUnit.seconds);
            aStats.add(SnStat.repairTime, (repairHealth * reload / 60f), StatUnit.seconds);
            aStats.add(SnStat.repairSpeed, repairSpeed * 60f, StatUnit.perSecond);
            aStats.add(SnStat.blockRange, range / tilesize, StatUnit.blocks);
            aStats.add(SnStat.maxControlled, maxConnections);
            aStats.add(SnStat.minimalRange, minRange / tilesize, StatUnit.blocks);
            aStats.add(SnStat.shootRange, range / tilesize, StatUnit.blocks);
            aStats.add(SnStat.inaccuracy, (int)inaccuracy, StatUnit.degrees);
            aStats.add(SnStat.shots, shots, StatUnit.none);
            aStats.add(SnStat.reload, 60f / (reloadTime) * (alternate ? 1 : shots), StatUnit.perSecond);
            aStats.add(SnStat.targetsAir, targetAir);
            aStats.add(SnStat.targetsGround, targetGround);
            aStats.add(SnStat.blockDamage, damage * 60f, StatUnit.perSecond);
            //aStats.add(SnStat.ammo, StatValues.ammo(ObjectMap.of(this, ammoTypes)));
            aStats.add(SnStat.ammoUse, ammoPerShot, StatUnit.perShot);
            //aStats.add(SnStat.powerShot, StatValues.ammo(ObjectMap.of(this, powerBullet)));
            aStats.add(SnStat.overheatTime, maxShootTime / Time.toSeconds, StatUnit.seconds);
            aStats.add(SnStat.shieldHealth, shieldHealth, StatUnit.none);
            aStats.add(SnStat.cooldownTime, (int) (shieldHealth / cooldownBrokenBase / 60f), StatUnit.seconds);

            if(true)throw null;
            aStats.add(SnStat.laserProduction, laserGeneration, StatUnit.none);
            aStats.add(SnStat.laserConsumption, laserConsumption, StatUnit.none);
            aStats.add(SnStat.maxCharge, maxCharge, StatUnit.none);
            aStats.add(SnStat.maxLinks, maxLinks, StatUnit.none);

            if(true)throw null;
            aStats.add(SnStat.primaryArmor, primaryArmor, StatUnit.none);
            aStats.add(SnStat.secondaryArmor, secondaryArmor, StatUnit.none);

            if(true)throw null;
            aStats.add(SnStat.pierceDamageDebuff, (1f - collidedDamageMultiplier) * 100, StatUnit.percent);
            aStats.add(SnStat.pierceMultiplier, pierceMultiplier);

            if(true)throw null;
            aStats.add(SnStat.powerCapacity, capacity, StatUnit.none);
            aStats.add(SnStat.powerUse, usage * 60f, StatUnit.powerSecond);
            aStats.add(SnStat.powerDamage, damage * 60f, StatUnit.perSecond);
            aStats.add(SnStat.powerRange, laserRange, StatUnit.blocks);
            aStats.add(SnStat.powerConnections, maxNodes, StatUnit.none);
            aStats.add(SnStat.basePowerGeneration, powerProduction * 60.0f, StatUnit.powerSecond);

            if(true)throw null;
            aStats.add(SnStat.blockItemCapacity, itemCapacity, StatUnit.items);
            aStats.add(SnStat.itemsMoved, displayedSpeed, StatUnit.itemsSecond);
            aStats.add(SnStat.launchTime, launchTime / 60f, StatUnit.seconds);
            aStats.add(SnStat.maxConsecutive, 2, StatUnit.none);

            if(true)throw null;
            aStats.add(SnStat.liquidCapacity, liquidCapacity, StatUnit.liquidUnits);

            //aStats.add(SnStat.booster, StatValues.boosters(reloadTime, consumes.<ConsumeLiquidBase>get(ConsumeType.liquid).amount, coolantMultiplier, true, l -> consumes.liquidfilters.get(l.id)));
            aStats.add(SnStat.boostEffect, liquidBoostIntensity * liquidBoostIntensity, StatUnit.timesSpeed);

            if(true)throw null;
        }};
        infoUnit = new InfoTestBlock("info-unit") {{
            info = "Unit";
        }
        @Override
        public void setStats() {
            super.setStats();
            aStats.add(SnStat.unitHealth, health);
            aStats.add(SnStat.armor, armor);
            aStats.add(SnStat.unitSize, hitSize / tilesize, StatUnit.blocksSquared);
            aStats.add(SnStat.unitBuildCost, buildCost / 60, StatUnit.seconds);
            aStats.add(SnStat.flying, flying);
            aStats.add(SnStat.speed, speed * 60f / tilesize, StatUnit.tilesSecond);
            aStats.add(SnStat.buildSpeed, buildSpeed);
            aStats.add(SnStat.mineSpeed, mineSpeed);
            aStats.add(SnStat.mineTier, StatValues.blocks(b -> b instanceof Floor f && f.itemDrop != null && f.itemDrop.hardness <= mineTier && (!f.playerUnmineable || Core.settings.getBool("doubletapmine"))));
            aStats.add(SnStat.payloadCapacity, (payloadCapacity / (tilesize * tilesize)), StatUnit.blocksSquared);
            aStats.add(SnStat.commandLimit, commandLimit);
            if (abilities.any()){
                var unique = new ObjectSet<String>();
                for (Ability a : abilities) {
                    if (unique.add(a.localized())) {
                        stats.add(Stat.abilities, a.localized());
                    }
                }
            }
            aStats.add(SnStat.canBoost, canBoost);
            aStats.add(SnStat.unitItemCapacity, itemCapacity);
            aStats.add(SnStat.unitRange, (int)(maxRange / tilesize), StatUnit.blocks);

            aStats.add(SnStat.guild, guild);
            aStats.add(SnStat.subGuild, subGuild);
            aStats.add(SnStat.branch, branch);

            //aStats.add(SnStat.weapons, StatValues.weapons(this, weapons));
            //aStats.add(SnStat.bullet, StatValues.ammo(ObjectMap.of(this, powerBullet)));
        }};
        infoItem = new InfoTestBlock("info-item") {{
            info = "Item";
        }
        @Override
        public void setStats() {
            super.setStats();
            aStats.add(SnStat.explosiveness, explosiveness);
            aStats.add(SnStat.flammability, flammability);
            aStats.add(SnStat.radioactivity, radioactivity);
            aStats.add(SnStat.charge, charge);

            aStats.add(SnStat.guild, guild);
            aStats.add(SnStat.subGuild, subGuild);
            aStats.add(SnStat.branch, branch);
        }};
        infoLiquid = new InfoTestBlock("info-liquid") {{
            info = "Liquid";
        }
        @Override
        public void setStats() {
            super.setStats();
            aStats.add(SnStat.explosiveness, explosiveness);
            aStats.add(SnStat.flammability, flammability);
            aStats.add(SnStat.heatCapacity, heatCapacity);
            aStats.add(SnStat.viscosity, viscosity);
            aStats.add(SnStat.temperature, temperature);

            aStats.add(SnStat.guild, guild);
            aStats.add(SnStat.subGuild, subGuild);
            aStats.add(SnStat.branch, branch);
        }};
        infoStatus = new InfoTestBlock("info-status") {{
            info = "Status";
        }
        @Override
        public void setStats() {
            super.setStats();
            aStats.add(SnStat.damageMultiplier, damageMultiplier);
            aStats.add(SnStat.healthMultiplier, healthMultiplier);
            aStats.add(SnStat.speedMultiplier, speedMultiplier);
            aStats.add(SnStat.reloadMultiplier, reloadMultiplier);
            aStats.add(SnStat.buildSpeedMultiplier, buildSpeedMultiplier);
            aStats.add(SnStat.statusDamage, damage * 60f, StatUnit.perSecond);
            //var other = Vars.content.statusEffects().find(f -> f.affinities.contains(this));
            //aStats.add(SnStat.reactive, other.emoji() + other + " / [accent]" + (int)other.transitionDamage + "[lightgray] " + Stat.damage.localized());
            aStats.add(SnStat.healing, -damage * 60f, StatUnit.perSecond);
            for(var e : affinities.asArray().sort()){
                aStats.add(SnStat.affinities,  e.emoji() + "" + e);
            }
            for (var e : opposites.asArray().sort()) {
                aStats.add(SnStat.opposites, e.emoji() + "" + e);
            }

            aStats.add(SnStat.guild, guild);
            aStats.add(SnStat.subGuild, subGuild);
            aStats.add(SnStat.branch, branch);
        }};
        //endregion info
    }
}