package sunset.maps.generators;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.noise.Simplex;
import mindustry.content.Blocks;
import mindustry.world.Block;
import sunset.content.blocks.SnEnvironment;

public class RimeGenerator extends SnPlanetGenerator{
    public RimeGenerator(){
        arr = new Block[][]{
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

        tars = ObjectMap.of(
        Blocks.snow, Blocks.snow,
        Blocks.ice, Blocks.ice
        );

        dec = ObjectMap.of(
        Blocks.ice, Blocks.snowBoulder,
        Blocks.ice, Blocks.snowBoulder,
        Blocks.ice, Blocks.snowBoulder,
        Blocks.iceSnow, Blocks.snowBoulder
        );
    }

    @Override
    protected void setupOres(Seq<Block> ores, float poles, float nmag, float scl, float addscl){
        super.setupOres(ores, poles, nmag, scl, addscl);

        if(Simplex.noise3d(seed, 3, 0.5f, scl, sector.tile.v.x + 1, sector.tile.v.y, sector.tile.v.z) * nmag + poles > 0.55f * addscl){
            ores.add(SnEnvironment.oreColdent);
        }
    }
}
