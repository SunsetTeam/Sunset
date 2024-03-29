package sunset.maps.generators;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.noise.Simplex;
import mindustry.content.Blocks;
import mindustry.world.Block;
import sunset.content.blocks.SnEnvironment;

public class HycleGenerator extends SnPlanetGenerator{

// Blocks.basalt, Blocks.stone, Bloks.shale, Blocks.charr, Blocks.dacite, Blocks.ice, SnEnvironment.granite
    /*public HycleGenerator(){
        arr = new Block[][]{
                {Blocks.stone, Blocks.stone, SnEnvironment.granite, Blocks.charr, Blocks.charr, Blocks.charr, Blocks.basalt, Blocks.stone, SnEnvironment.granite, Blocks.basalt, Blocks.basalt, Blocks.stone, SnEnvironment.granite},
                {Blocks.stone, Blocks.stone, Blocks.charr, Blocks.basalt, Blocks.basalt, Blocks.charr, SnEnvironment.granite, Blocks.basalt, Blocks.stone, Blocks.charr, Blocks.stone, Blocks.stone, SnEnvironment.granite},
                {Blocks.stone, Blocks.stone, Blocks.stone, Blocks.basalt, SnEnvironment.granite, Blocks.basalt, Blocks.basalt, SnEnvironment.granite, Blocks.basalt, Blocks.charr, Blocks.stone, Blocks.stone, Blocks.basalt},
                {Blocks.stone, Blocks.stone, Blocks.basalt, Blocks.dacite, Blocks.basalt, SnEnvironment.granite, Blocks.charr, Blocks.stone, Blocks.stone, Blocks.stone, Blocks.stone, Blocks.dacite, Blocks.dacite},
                {Blocks.charr, Blocks.charr, Blocks.stone, Blocks.dacite, Blocks.stone, Blocks.basalt, Blocks.basalt, Blocks.basalt, Blocks.stone, Blocks.stone, Blocks.stone, Blocks.stone, Blocks.charr},
                {Blocks.basalt, Blocks.charr, Blocks.charr, Blocks.dacite, Blocks.basalt, Blocks.basalt, Blocks.charr, Blocks.stone, Blocks.charr, Blocks.stone, Blocks.charr, Blocks.stone, Blocks.basalt},
                {Blocks.basalt, Blocks.basalt, Blocks.charr, Blocks.basalt, Blocks.stone, Blocks.charr, Blocks.charr, SnEnvironment.granite, Blocks.dacite, Blocks.dacite, Blocks.basalt, Blocks.stone, Blocks.charr},
                {Blocks.charr, Blocks.charr, Blocks.basalt, Blocks.stone, Blocks.charr, Blocks.basalt, Blocks.dacite, Blocks.hotrock, SnEnvironment.granite, Blocks.basalt, Blocks.stone, Blocks.dacite, Blocks.charr},
                {Blocks.stone, Blocks.basalt, Blocks.basalt, Blocks.basalt, Blocks.charr, Blocks.stone, Blocks.charr, Blocks.basalt, Blocks.basalt, Blocks.basalt, Blocks.basalt, Blocks.dacite, Blocks.charr},
                {Blocks.charr, Blocks.basalt, Blocks.ice, Blocks.stone, Blocks.dacite, Blocks.stone, Blocks.stone, Blocks.stone, Blocks.ice, Blocks.ice, SnEnvironment.granite, Blocks.basalt, Blocks.basalt},
                {Blocks.stone, Blocks.basalt, SnEnvironment.granite, SnEnvironment.granite,  Blocks.stone, Blocks.basalt, SnEnvironment.granite, SnEnvironment.granite, Blocks.charr, Blocks.basalt, Blocks.charr, Blocks.charr, Blocks.basalt},
                {Blocks.charr, Blocks.ice, Blocks.stone, Blocks.stone, Blocks.ice, Blocks.ice, Blocks.basalt, Blocks.basalt, SnEnvironment.granite, Blocks.ice, Blocks.ice, Blocks.ice, SnEnvironment.granite},
                {Blocks.ice, Blocks.ice, Blocks.ice, Blocks.stone, Blocks.stone, Blocks.stone, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice}
        };

        dec = ObjectMap.of(
                Blocks.stone, Blocks.boulder,
                Blocks.basalt, Blocks.boulder,
                SnEnvironment.granite, Blocks.boulder,
                Blocks.charr, Blocks.boulder
        );

        tars = ObjectMap.of(
                Blocks.basalt, Blocks.basalt,
                Blocks.stone, SnEnvironment.granite
        );
    }

    @Override
    protected void setupOres(Seq<Block> ores, float poles, float nmag, float scl, float addscl){
        super.setupOres(ores, poles, nmag, scl, addscl);

        if(Simplex.noise3d(seed, 3, 0.5f, scl, sector.tile.v.x + 1, sector.tile.v.y, sector.tile.v.z)*nmag + poles > 0.55f*addscl){
            ores.add(SnEnvironment.oreFors);
        }
    }*/
}
