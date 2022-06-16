package sunset.content.blocks;

import gas.world.blocks.gas.*;
import gas.world.blocks.sandbox.*;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.power.*;
import mindustry.world.meta.*;
import sunset.content.*;
import sunset.world.blocks.gas.*;

import static mindustry.type.ItemStack.with;

public class SnGasBlocks{
    public static Block
    //sandbox
    /*gasSource,*/ gasVoid,

    //gas-transportation
    gasPipe, armoredGasPipe, nobiumGasPipe, gasJunction, /*gasRouter,*/
    gasBridge, nobiumGasBridge,

    //storage
    gasStorage, gasContainer, gasTank,

    //crafting
    giardWell,

    //power
    gasGenerator;

    public static void load(){
        /*
        //sandbox
        gasSource = new GasSource("gas-source"){{
            requirements(Category.liquid, BuildVisibility.sandboxOnly, with());
            alwaysUnlocked = true;
        }};
         */
        gasVoid = new GasVoid("gas-void"){{
            requirements(Category.liquid, BuildVisibility.sandboxOnly, with());
            alwaysUnlocked = true;
        }};

        //gas-transportation
        gasPipe = new GasConduit("gas-pipe"){{
            requirements(Category.liquid, with(SnItems.erius, 2, SnItems.naturite, 1));
            health = 55;
            size = 1;
            liquidCapacity = 11f;
            junctionReplacement = gasJunction;
            bridgeReplacement = gasBridge;
        }};
        armoredGasPipe = new ArmoredGasConduit("plated-gas-pipe"){{
            requirements(Category.liquid, with(SnItems.fors, 3, SnItems.erius, 2));
            health = 250;
            size = 1;
            liquidCapacity = 11.6f;
            junctionReplacement = gasJunction;
            bridgeReplacement = gasBridge;
        }};
        nobiumGasPipe = new GasConduit("nobium-gas-pipe"){{
            requirements(Category.liquid, with(SnItems.erius, 2, SnItems.nobium, 1));
            health = 125;
            size = 1;
            liquidCapacity = 18f;
            junctionReplacement = gasJunction;
            bridgeReplacement = gasBridge;
        }};
        gasJunction = new GasJunction("gas-junction"){{
            requirements(Category.liquid, with(SnItems.naturite, 2, SnItems.erius, 2, SnItems.fors, 1));
            health = 75;
            size = 1;
            liquidCapacity = 20f;
            hasLiquids = false;
        }};
        /*
        gasRouter = new GasRouter("gas-router"){{
            requirements(Category.liquid, with(SnItems.naturite, 4, SnItems.erius, 2, SnItems.fors, 2));
            health = 80;
            size = 1;
            liquidCapacity = 25f;
        }};
         */
        gasBridge = new GasBridge("gas-bridge"){{
            requirements(Category.liquid, with(SnItems.naturite, 5, SnItems.erius, 9, SnItems.fors, 4));
            health = 85;
            size = 1;
            liquidCapacity = 30f;
            hasPower = false;
            outputsLiquid = true;
            hasLiquids = true;
            range = 6;
        }};
        nobiumGasBridge = new GasBridge("nobium-gas-bridge"){{
            requirements(Category.liquid, with(SnItems.anzar, 5, SnItems.erius, 15, SnItems.nobium, 7, SnItems.fors, 5f));
            health = 110;
            range = 12;
            size = 1;
            liquidCapacity = 45f;
            arrowPeriod = 0.9f;
            arrowTimeScl = 2.75f;
            hasPower = true;
            pulse = true;
            consumePower(0.35f);
        }};
        //storage
        gasStorage = new GasRouter("gas-storage"){{
            requirements(Category.liquid, with(SnItems.fors, 15, SnItems.erius, 20));
            health = 260;
            size = 2;
            liquidCapacity = 350f;
        }};

        gasContainer = new GasRouter("gas-container"){{
            requirements(Category.liquid, with(SnItems.fors, 30, SnItems.erius, 45));
            health = 600;
            size = 3;
            liquidCapacity = 760f;
        }};

        gasTank = new GasRouter("gas-tank"){{
            requirements(Category.liquid, with(SnItems.fors, 115, SnItems.erius, 95, SnItems.nobium, 50));
            health = 1300;
            size = 4;
            liquidCapacity = 1880f;
        }};

        //crafting
        giardWell = new GasWell("giard-well"){{
            requirements(Category.crafting, with(SnItems.fors, 1));
            size = 4;
            attribute = SnAttribute.gas;

            outputLiquid = new LiquidStack(SnGas.gyner, 9f);
            hasItems = true;
            hasLiquids = true;
//            hasLiquids = true;
            liquidCapacity = 100f;
            craftTime = 3f;
            craftEffect = SnFx.giardSynthesizerCraft;
            consumePower(6f);
        }};

        //power
        gasGenerator = new ConsumeGenerator("gas-generator"){{
            requirements(Category.power, with(Items.copper, 40, Items.lead, 60, Items.silicon, 30f));
            size = 3;
            itemDuration = 200f;
            hasLiquids = true;
            hasItems = true;

            powerProduction = 12f;
            consumeItems(new ItemStack(Items.coal, 2));
//            consumeLiquid(SnLiquids.burheyna, 0.6f);
            consumeLiquids(LiquidStack.with(
            SnLiquids.burheyna, 0.6f,
            SnGas.hyneris, 0.5f
            ));
        }};
    }
}
