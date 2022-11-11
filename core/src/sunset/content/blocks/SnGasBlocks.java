package sunset.content.blocks;

import gas.world.blocks.gas.*;
import gas.world.blocks.gas.ArmoredGasConduit;
import gas.world.blocks.sandbox.*;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.liquid.ArmoredConduit;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.liquid.LiquidJunction;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.power.*;
import mindustry.world.meta.*;
import sunset.content.*;
import sunset.world.blocks.gas.*;

import static mindustry.type.ItemStack.with;

public class SnGasBlocks{
    public static Block

    //transportation
    zariumPipe, nediriumPipe,
    gasJunction, gasRouter;

    public static void load(){
        //region transportation
        zariumPipe = new GasConduit("zarium-gas-pipe") {{
            requirements(Category.liquid, with(SnItems.zarium, 2, SnItems.erius, 1));
            liquidCapacity = 23f;
            liquidPressure = 1.06f;
            leaks = true;
            health = 230;
        }};

        nediriumPipe = new ArmoredGasConduit("nedirium-gas-pipe") {{
            requirements(Category.liquid, with(SnItems.nedirium, 2, SnItems.erius, 2));
            liquidCapacity = 28f;
            liquidPressure = 1.09f;
            health = 400;
            leaks = true;
        }};

        gasJunction = new GasJunction("gas-junction"){{
            requirements(Category.liquid, with(SnItems.zarium, 7, SnItems.erius, 4));

            ((Conduit)zariumPipe).junctionReplacement = this;
            ((Conduit)nediriumPipe).junctionReplacement = this;
            solid = false;
            health = 500;
        }};

        gasRouter = new GasRouter("gas-router"){{
            requirements(Category.liquid, with(SnItems.zarium, 4, SnItems.erius, 6));
            liquidCapacity = 25f;
            health = 400;
        }};
        //endregion transportation
    }
}
