package sunset.content.blocks;

import arc.graphics.Color;
import gas.world.blocks.gas.*;
import gas.world.blocks.sandbox.GasSource;
import gas.world.blocks.sandbox.GasVoid;
import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import sunset.content.SnItems;

import static mindustry.type.ItemStack.with;

public class SnGasBlocks implements ContentList {
    public static Block
    //sandbox
    gasSource, gasVoid,

    //gas-transportation
    gasPipe, armoredGasPipe, naturiteGasPipe, nobiumGasPipe, armoredNobiumGasPipe, gasJunction, gasRouter, gasBridge, nobiumGasBridge,

    //storage
    gasStorage;

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
        naturiteGasPipe = new GasConduit("naturite-gas-pipe") {{
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
        armoredNobiumGasPipe = new GasArmoredConduit("plated-gas-pipe") {{
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
    }
}
