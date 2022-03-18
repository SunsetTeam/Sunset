package sunset.content.blocks;

import gas.GasStack;
import gas.world.blocks.gas.*;
import gas.world.blocks.power.GasItemLiquidGenerator;
import gas.world.blocks.sandbox.GasSource;
import gas.world.blocks.sandbox.GasVoid;
import gas.world.consumers.ConsumeGas;
import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import sunset.content.SnFx;
import sunset.content.SnGas;
import sunset.content.SnItems;
import sunset.content.SnLiquids;
import sunset.world.blocks.gas.ArmoredGasConduit;
import sunset.world.blocks.gas.GasCrafter;
import sunset.world.blocks.gas.ModGasPipe;

import static mindustry.type.ItemStack.with;

public class SnGasBlocks implements ContentList {
    public static Block
    //sandbox
    gasSource, gasVoid,

    //gas-transportation
    gasPipe, armoredGasPipe, naturiteGasPipe, nobiumGasPipe, armoredNobiumGasPipe, gasJunction, gasRouter, gasBridge, nobiumGasBridge,

    //storage
    gasStorage, gasContainer, gasTank,

    //crafting
    giardSynthesizer,

    //power
    gasGenerator;

    @Override
    public void load() {
        //sandbox
        gasSource = new GasSource("gas-source") {{
            requirements(Category.liquid, BuildVisibility.sandboxOnly, with());
            alwaysUnlocked = true;
        }};
        gasVoid = new GasVoid("gas-void") {{
            requirements(Category.liquid, BuildVisibility.sandboxOnly, with());
            alwaysUnlocked = true;
        }};

        //gas-transportation
        gasPipe = new GasConduit("gas-pipe") {{
            requirements(Category.liquid, with(Items.metaglass, 1, SnItems.fors, 2));
            health = 55;
            size = 1;
            gasCapacity = 9f;
            junctionReplacement = gasJunction;
            bridgeReplacement = gasBridge;
        }};
        armoredGasPipe = new ArmoredGasConduit("plated-gas-pipe") {{
            requirements(Category.liquid, with(SnItems.fors, 2, Items.metaglass, 1, Items.graphite, 3f));
            health = 200;
            size = 1;
            gasCapacity = 9.6f;
            junctionReplacement = gasJunction;
            bridgeReplacement = gasBridge;
        }};
        naturiteGasPipe = new ModGasPipe("naturite-gas-pipe") {{
            requirements(Category.liquid, with(Items.metaglass, 1, SnItems.naturite, 3, SnItems.fors, 2));
            health = 65;
            size = 1;
            gasCapacity = 13f;
            placeableLiquid = true;
            junctionReplacement = gasJunction;
            bridgeReplacement = gasBridge;
        }};
        nobiumGasPipe = new GasConduit("nobium-gas-pipe") {{
            requirements(Category.liquid, with(Items.metaglass, 2, SnItems.nobium, 2));
            health = 125;
            size = 1;
            gasCapacity = 17f;
            junctionReplacement = gasJunction;
            bridgeReplacement = gasBridge;
        }};
        gasJunction = new GasJunction("gas-junction") {{
            requirements(Category.liquid, with(Items.graphite, 2, Items.metaglass, 1, SnItems.fors, 2));
            health = 75;
            size = 1;
            gasCapacity = 30f;
            hasLiquids = false;
        }};
        gasRouter = new GasRouter("gas-router") {{
            requirements(Category.liquid, with(Items.graphite, 4, Items.metaglass, 1, SnItems.fors, 3));
            health = 80;
            size = 1;
            gasCapacity = 35f;
        }};
        gasBridge = new GasBridge("gas-bridge") {{
            requirements(Category.liquid, with(Items.graphite, 5, Items.metaglass, 9, SnItems.fors, 8));
            health = 85;
            size = 1;
            gasCapacity = 37f;
            hasPower = false;
            outputsGas = true;
            hasGasses = true;
            range = 6;
        }};
        nobiumGasBridge = new GasBridge("nobium-gas-bridge") {{
            requirements(Category.liquid, with(Items.graphite, 8, Items.metaglass, 15, SnItems.nobium, 7));
            health = 110;
            range = 12;
            size = 1;
            gasCapacity = 45f;
            arrowPeriod = 0.9f;
            arrowTimeScl = 2.75f;
            hasPower = true;
            pulse = true;
            consumes.power(0.35f);
        }};
        //storage
        gasStorage = new GasRouter("gas-storage"){{
            requirements(Category.liquid, with(SnItems.fors, 15, Items.metaglass, 20));
            health = 260;
            size = 2;
            gasCapacity = 320f;
        }};

        gasContainer = new GasRouter("gas-container"){{
            requirements(Category.liquid, with(SnItems.fors, 30, Items.metaglass, 45));
            health = 600;
            size = 3;
            gasCapacity = 720f;
        }};

        gasTank = new GasRouter("gas-tank"){{
            requirements(Category.liquid, with(SnItems.fors, 115, Items.metaglass, 95, Items.thorium, 70));
            health = 1300;
            size = 4;
            gasCapacity = 1840f;
        }};

        //crafting
        giardSynthesizer = new GasCrafter("giard-synthesizer") {{
            requirements(Category.crafting, with(SnItems.fors, 155, Items.metaglass, 85, Items.silicon, 90, Items.graphite, 95));
            size = 3;

            outputGas = new GasStack(SnGas.hyneris, 8f);
            hasItems = true;
            hasLiquids = true;
            hasGasses = true;
            gasCapacity = 50f;
            itemCapacity = 30;
            gasCapacity = 180f;
            craftTime = 5f;
            craftEffect = SnFx.giardSynthesizerCraft;
            consumes.items(with(SnItems.naturite, 1f));
            consumes.liquid(SnLiquids.burheyna, 0.25f);
            consumes.power(2f);
        }};

        //power
        gasGenerator = new GasItemLiquidGenerator("gas-generator") {{
            requirements(Category.power, with(Items.copper, 40, Items.lead, 60, Items.silicon, 30f));
            size = 3;
            itemDuration = 200f;
            hasLiquids = true;
            hasItems = true;

            powerProduction = 12f;
            consumes.items(new ItemStack(Items.coal, 2));
            consumes.liquid(SnLiquids.burheyna, 0.6f);
            consumes.add(new ConsumeGas(SnGas.hyneris, 0.5f));
        }};
    }
}
