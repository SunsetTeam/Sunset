package sunset.utils.V7;

import arc.struct.ObjectMap;
import mindustry.content.Blocks;
import mindustry.world.Block;
import sunset.content.blocks.SnEnvironment;

public class temp {

    Block[][] arr = {
            {Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice, SnEnvironment.glacier1, SnEnvironment.glacier1, SnEnvironment.glacier1, SnEnvironment.glacier1, SnEnvironment.glacier1, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow},
            {Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice, SnEnvironment.glacier2, SnEnvironment.glacier2, SnEnvironment.glacier2, SnEnvironment.glacier3, Blocks.snow, Blocks.snow, Blocks.snow},
            {Blocks.ice, SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier3, Blocks.snow, Blocks.snow, Blocks.ice, Blocks.ice, Blocks.iceSnow, Blocks.iceSnow},
            {Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.iceSnow, Blocks.iceSnow},
            {SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier3, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.ice, Blocks.ice},
            {Blocks.ice, Blocks.ice, SnEnvironment.glacier3, SnEnvironment.glacier3, Blocks.snow, Blocks.snow, Blocks.ice, Blocks.ice, Blocks.iceSnow, SnEnvironment.glacier2, SnEnvironment.glacier2, SnEnvironment.glacier3},
            {SnEnvironment.glacier1, SnEnvironment.glacier1, SnEnvironment.glacier1, SnEnvironment.glacier1, Blocks.iceSnow, Blocks.iceSnow, Blocks.ice, Blocks.ice, SnEnvironment.glacier2, SnEnvironment.glacier3, SnEnvironment.glacier3, Blocks.iceSnow, Blocks.iceSnow},
            {Blocks.iceSnow, Blocks.iceSnow, Blocks.iceSnow, Blocks.iceSnow, Blocks.ice, Blocks.ice, SnEnvironment.glacier1, SnEnvironment.glacier1, SnEnvironment.glacier1, SnEnvironment.glacier1, SnEnvironment.glacier2, SnEnvironment.glacier2, Blocks.ice},
            {SnEnvironment.glacier1, SnEnvironment.glacier1, SnEnvironment.glacier1, Blocks.iceSnow, Blocks.iceSnow, SnEnvironment.glacier2, SnEnvironment.glacier2, SnEnvironment.glacier2, SnEnvironment.glacier2, SnEnvironment.glacier3, SnEnvironment.glacier3, Blocks.iceSnow, Blocks.iceSnow},
            {SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier2, SnEnvironment.glacier2, SnEnvironment.glacier1, Blocks.snow, Blocks.snow, Blocks.snow},
            {SnEnvironment.glacier2, SnEnvironment.glacier3, SnEnvironment.glacier2, SnEnvironment.glacier2, SnEnvironment.glacier2, SnEnvironment.glacier1, Blocks.iceSnow, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice},
            {SnEnvironment.glacier2, SnEnvironment.glacier2, SnEnvironment.glacier3, Blocks.iceSnow, Blocks.iceSnow, Blocks.iceSnow, Blocks.iceSnow, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice},
            {SnEnvironment.glacier2, SnEnvironment.glacier2, SnEnvironment.glacier2, SnEnvironment.glacier2, SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier3, SnEnvironment.glacier2, SnEnvironment.glacier2, SnEnvironment.glacier2}
    };

    ObjectMap<Block, Block> tars = ObjectMap.of(
            Blocks.snow, Blocks.snow,
            Blocks.ice, Blocks.ice
    );

    ObjectMap<Block, Block> dec = ObjectMap.of(
            Blocks.ice, Blocks.ice,
            SnEnvironment.glacier3, SnEnvironment.glacier3,
            Blocks.ice, Blocks.ice,
            Blocks.iceSnow, Blocks.iceSnow
    );
}
