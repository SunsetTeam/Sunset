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
    gasPipe, armoredGasPipe, gasJunction, gasRouter, gasBridge,

    //storage
    gasStorage;


    @Override
    public void load() {


        gasSource = new GasSource("gas-source") {{
            requirements(Category.liquid, BuildVisibility.sandboxOnly, with());
            alwaysUnlocked = true;
        }};
        gasVoid = new GasVoid("gas-void") {{
            requirements(Category.liquid, BuildVisibility.sandboxOnly, with());
            alwaysUnlocked = true;
        }};

        gasPipe = new GasConduit("gas-pipe") {{
            requirements(Category.liquid, with(Items.metaglass, 1, SnItems.fors, 2));
            health = 50;
            size = 1;
            botColor = Color.valueOf("282933");
            gasCapacity = 7.5f;
            junctionReplacement = gasJunction;
            bridgeReplacement = gasBridge;
        }};

        armoredGasPipe = new GasArmoredConduit("plated-gas-pipe") {{
            requirements(Category.liquid, with(Items.metaglass, 2, SnItems.fors, 5, Items.graphite, 3));
            health = 300;
            size = 1;
            gasCapacity = 30f;
            junctionReplacement = gasJunction;
            bridgeReplacement = gasBridge;
            botColor = Color.valueOf("2c2d38");
        }};

        gasJunction = new GasJunction("gas-junction") {{
            requirements(Category.liquid, with(Items.metaglass, 2, SnItems.fors, 6));
            health = 75;
            size = 1;
            gasCapacity = 26f;
            hasLiquids = false;
        }};
        gasRouter = new GasRouter("gas-router") {{
            requirements(Category.liquid, with(Items.metaglass, 2, SnItems.fors, 6));
            health = 80;
            size = 1;
            gasCapacity = 30f;
        }};
        gasBridge = new GasBridge("gas-bridge") {{
            requirements(Category.liquid, with(Items.metaglass, 7, SnItems.fors, 7, Items.graphite, 5));
            health = 85;
            size = 1;
            gasCapacity = 35f;
            outputsGas = true;
            hasGasses = true;
            range = 6;
        }};

        gasStorage = new GasRouter("gas-storage"){{
            requirements(Category.liquid, with(Items.metaglass, 15, SnItems.fors, 20));
            health = 150;
            size = 3;
            gasCapacity = 70f;
        }};
    }
}
