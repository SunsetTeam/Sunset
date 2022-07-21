package sunset.maps.generators;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.noise.Simplex;
import mindustry.content.Blocks;
import mindustry.world.Block;
import sunset.content.blocks.SnEnvironment;

public class TestGenerator extends SnPlanetGenerator {


    public TestGenerator() {
        arr = new Block[][]{//13 blocks
                {SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonSand, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt},
                {SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt},
                {SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.crimsonSand, SnEnvironment.crimsonDirt, SnEnvironment.granite, SnEnvironment.crimsonGrass, SnEnvironment.crimsonSand, SnEnvironment.burheynaLiquidFloor, Blocks.grass, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt},
                {SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.granite, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt},
                {SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonSand, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass},
                {SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonMoss, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt},
                {SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonSand, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt},
                {SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.granite, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass},
                {SnEnvironment.crimsonSand, SnEnvironment.crimsonMoss, SnEnvironment.crimsonSand, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass},
                {SnEnvironment.crimsonDirt, SnEnvironment.crimsonSnow, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonSand, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt},
                {SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonSnow, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt},
                {SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.granite, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass},
                {SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt}
        };

        dec = ObjectMap.of(
                Blocks.sporeMoss, Blocks.sporeCluster,
                Blocks.moss, Blocks.sporeCluster,
                Blocks.taintedWater, Blocks.water,
                Blocks.darksandTaintedWater, Blocks.darksandWater
        );

        tars = ObjectMap.of(
                Blocks.sporeMoss, Blocks.shale,
                Blocks.moss, Blocks.shale
        );
    }

    @Override
    protected void setupOres(Seq<Block> ores, float poles, float nmag, float scl, float addscl) {
        super.setupOres(ores, poles, nmag, scl, addscl);

        if(Simplex.noise3d(seed, 3, 0.5f, scl, sector.tile.v.x + 1, sector.tile.v.y, sector.tile.v.z) * nmag + poles > 0.7f * addscl) {
            ores.add(SnEnvironment.oreFors);
        }

        if(Simplex.noise3d(seed, 2, 0.5f, scl, sector.tile.v.x + 1, sector.tile.v.y, sector.tile.v.z) * nmag + poles > 0.45f * addscl) {
            ores.add(SnEnvironment.orePlanatrium);
        }
    }


    /*@Override
    protected void passTiles(int x, int y) {

        if(floor == SnEnvironment.crimsonSwamp) {
            if(Math.abs(0.5f - noise(x - 90, y, 4, 0.8, 65)) > 0.02) {
                floor = SnEnvironment.crimsonMoss;
            }
        }
    }

    @Override
    protected void setupTree() {
        block = rand.chance(0.5) ? SnEnvironment.crimsonTree : SnEnvironment.crimsonTreeDead;
    }*/
}
