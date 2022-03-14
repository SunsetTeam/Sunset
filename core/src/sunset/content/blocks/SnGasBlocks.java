package sunset.content.blocks;

import arc.graphics.Color;
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
    gasStorage,

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
            requirements(Category.liquid, with(Items.graphite, 1, SnItems.fors, 2));
            health = 50;
            size = 1;
            gasCapacity = 7.5f;
            junctionReplacement = gasJunction;
            bridgeReplacement = gasBridge;
        }};
        armoredGasPipe = new GasArmoredConduit("plated-gas-pipe") {{
            requirements(Category.liquid, with(SnItems.fors, 5, Items.graphite, 3));
            health = 300;
            size = 1;
            gasCapacity = 7.7f;
            junctionReplacement = gasJunction;
            bridgeReplacement = gasBridge;
        }};
        naturiteGasPipe = new ModGasPipe("naturite-gas-pipe") {{
            requirements(Category.liquid, with(SnItems.naturite, 1, SnItems.fors, 2));
            health = 50;
            size = 1;
            gasCapacity = 10f;
            junctionReplacement = gasJunction;
            bridgeReplacement = gasBridge;
        }};
        nobiumGasPipe = new GasConduit("nobium-gas-pipe") {{
            requirements(Category.liquid, with(Items.graphite, 1, SnItems.nobium, 2));
            health = 50;
            size = 1;
            gasCapacity = 13f;
            junctionReplacement = gasJunction;
            bridgeReplacement = gasBridge;
        }};
        armoredNobiumGasPipe = new GasArmoredConduit("nobium-plated-gas-pipe") {{
            requirements(Category.liquid, with(Items.graphite, 2, SnItems.nobium, 5));
            health = 300;
            size = 1;
            gasCapacity = 13.5f;
            junctionReplacement = gasJunction;
            bridgeReplacement = gasBridge;
        }};
        gasJunction = new GasJunction("gas-junction") {{
            requirements(Category.liquid, with(Items.graphite, 2, SnItems.fors, 6));
            health = 75;
            size = 1;
            gasCapacity = 29f;
            hasLiquids = false;
        }};
        gasRouter = new GasRouter("gas-router") {{
            requirements(Category.liquid, with(Items.graphite, 2, SnItems.fors, 6));
            health = 80;
            size = 1;
            gasCapacity = 36f;
        }};
        gasBridge = new GasBridge("gas-bridge") {{
            requirements(Category.liquid, with(Items.graphite, 7, SnItems.fors, 7));
            health = 85;
            size = 1;
            gasCapacity = 35f;
            outputsGas = true;
            hasGasses = true;
            range = 6;
        }};
        nobiumGasBridge = new GasExtendingBridge("nobium-gas-bridge") {{
            requirements(Category.liquid, with(Items.graphite, 10, SnItems.nobium, 5));
            health = 85;
            size = 1;
            gasCapacity = 35f;
            outputsGas = true;
            hasGasses = true;
            range = 6;
        }};

        //storage
        gasStorage = new GasRouter("gas-storage"){{
            requirements(Category.liquid, with(Items.graphite, 15, SnItems.fors, 20));
            health = 200;
            size = 3;
            gasCapacity = 140f;
        }};

        //crafting
        giardSynthesizer = new GasCrafter("giard-synthesizer") {{
            requirements(Category.crafting, with(SnItems.fors, 155, Items.metaglass, 85, Items.silicon, 90, Items.graphite, 95));
            size = 3;

            outputGas = new GasStack(SnGas.hyneris, 5f);
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
