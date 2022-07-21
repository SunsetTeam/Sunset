package sunset.content.blocks;

import mindustry.content.Items;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.power.*;
import sunset.content.*;
import sunset.world.blocks.power.LightningPowerNode;

import static mindustry.type.ItemStack.with;

public class SnPower{
    public static Block

    //generators
    thermalEvaporator, oxidativeCombustionGenerator, chemicalReactor,

    //nodes
    plasmaNode, plasmaNodeLarge, plasmaDistributor, plasmaDistributorLarge;

    public static void load(){
        thermalEvaporator = new ThermalGenerator("thermal-evaporator"){{
            requirements(Category.power, with(SnItems.fors, 60, SnItems.erius, 40));
            powerProduction = 2.7f;
            displayEfficiency = true;
//            spinners = false;
            size = 2;
            floating = true;
            placeableLiquid = true;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
            attribute = SnAttribute.thermalBurheyna;
        }};

        oxidativeCombustionGenerator = new ConsumeGenerator("oxidative-combustion-generator"){{
            requirements(Category.power, with(SnItems.fors, 120, SnItems.erius, 100, SnItems.naturite, 70));
            size = 2;
            powerProduction = 6.1f;

            hasLiquids = true;
            hasItems = true;
            hasPower = true;
            hasLiquids = false;
            itemCapacity = 20;
            liquidCapacity = 35f;
            itemDuration = 100f;
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.06f;

            consumeLiquid(SnGas.hyneris, 5f / 60f);
            consumeItem(SnItems.azaliaBud, 2);
        }};

        chemicalReactor = new ConsumeGenerator("chemical-reactor"){{
            requirements(Category.power, with(SnItems.fors, 200, SnItems.erius, 190, SnItems.naturite, 140, SnItems.anzar, 110));
            size = 3;
            powerProduction = 13f;

            hasLiquids = true;
            hasPower = true;
            hasLiquids = true;
            itemCapacity = 20;
            liquidCapacity = 35f;
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.06f;

            consumeLiquids(LiquidStack.with(
            SnGas.gyner, 6f / 60f,
            SnLiquids.sayang, 3f / 60f
            ));
        }};

        plasmaNode = new LightningPowerNode("plasma-node", 0){{
            requirements(Category.power, with(Items.copper, 5, Items.lead, 20));
            consumePowerBuffered(4000f);
            lightningRange = 16 * 8f;
            thresholdPerTile = 25f / 8;
        }};

        plasmaNodeLarge = new LightningPowerNode("plasma-node-large", 0){{
            requirements(Category.power, with(Items.titanium, 20, Items.lead, 50, Items.silicon, 30));
            consumePowerBuffered(30000f);
            size = 2;
            lightningRange = 29 * 8f;
            thresholdPerTile = 60f / 8;
        }};

        plasmaDistributor = new LightningPowerNode("plasma-distributor", 12){{
            requirements(Category.power, with(Items.copper, 5, Items.lead, 20));
            size = 2;
            consumePowerBuffered(7500f);
            lightningRange = 9 * 8f;
            laserRange = 7;
            thresholdPerTile = 10f / 8;
        }};

        plasmaDistributorLarge = new LightningPowerNode("plasma-distributor-large", 24){{
            requirements(Category.power, with(Items.titanium, 20, Items.lead, 50, Items.silicon, 30));
            consumePowerBuffered(55000f);
            size = 3;
            lightningRange = 16 * 8f;
            laserRange = 12;
            thresholdPerTile = 40f / 8;
        }};
    }
}
