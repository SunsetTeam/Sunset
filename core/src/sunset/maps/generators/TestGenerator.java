package sunset.maps.generators;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.noise.Simplex;
import mindustry.world.Block;
import sunset.content.blocks.SnEnvironment;

public class TestGenerator extends SnPlanetGenerator {


    public TestGenerator(){
        arr = new Block[][]{//13 blocks
        {SnEnvironment.crimsonWater, SnEnvironment.crimsonSandWater, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonSandWater, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt},
        {SnEnvironment.crimsonWater, SnEnvironment.crimsonSandWater, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonSandWater, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt},
        {SnEnvironment.crimsonWater, SnEnvironment.crimsonSandWater, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.granite, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonSandWater, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt},
        {SnEnvironment.crimsonWater, SnEnvironment.crimsonSandWater, SnEnvironment.crimsonSand, SnEnvironment.granite, SnEnvironment.granite, SnEnvironment.granite, SnEnvironment.crimsonSand, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt},
        {SnEnvironment.crimsonDeepWater, SnEnvironment.crimsonWater, SnEnvironment.crimsonSandWater, SnEnvironment.crimsonSand, SnEnvironment.granite, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt},
        {SnEnvironment.crimsonDeepWater, SnEnvironment.crimsonWater, SnEnvironment.crimsonSandWater, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.granite, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt},
        {SnEnvironment.crimsonDeepWater, SnEnvironment.crimsonSandWater, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.granite, SnEnvironment.granite, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt},
        {SnEnvironment.crimsonDeepWater, SnEnvironment.crimsonSandWater, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonDirt, SnEnvironment.granite, SnEnvironment.crimsonDirt, SnEnvironment.granite, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt},
        {SnEnvironment.crimsonSandWater, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.granite, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt},
        {SnEnvironment.crimsonSandWater, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonIceSnow, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonSand, SnEnvironment.crimsonGrass},
        {SnEnvironment.crimsonSnow, SnEnvironment.crimsonIce, SnEnvironment.crimsonSnow, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonIce, SnEnvironment.crimsonIce, SnEnvironment.crimsonSnow, SnEnvironment.crimsonSnow, SnEnvironment.crimsonIce, SnEnvironment.crimsonIce, SnEnvironment.crimsonIce, SnEnvironment.crimsonIce},
        {SnEnvironment.crimsonIceSnow, SnEnvironment.crimsonIce, SnEnvironment.crimsonIceSnow, SnEnvironment.crimsonIce, SnEnvironment.granite, SnEnvironment.crimsonGrass, SnEnvironment.crimsonIceSnow, SnEnvironment.crimsonSnow, SnEnvironment.crimsonGrass, SnEnvironment.crimsonIceSnow, SnEnvironment.crimsonIceSnow, SnEnvironment.crimsonGrass, SnEnvironment.crimsonIce},
        {SnEnvironment.crimsonIce, SnEnvironment.crimsonSnow, SnEnvironment.crimsonSnow, SnEnvironment.crimsonIce, SnEnvironment.crimsonIceSnow, SnEnvironment.crimsonSnow, SnEnvironment.crimsonSnow, SnEnvironment.crimsonSnow, SnEnvironment.crimsonIce, SnEnvironment.crimsonGrass, SnEnvironment.crimsonIce, SnEnvironment.crimsonIce, SnEnvironment.crimsonIceSnow}
        };

        dec = ObjectMap.of(




        );

        tars = ObjectMap.of(


        );
    }

    @Override
    protected void setupOres(Seq<Block> ores, float poles, float nmag, float scl, float addscl) {
        super.setupOres(ores, poles, nmag, scl, addscl);


        if(Simplex.noise3d(seed, 3, 0.5f, scl, sector.tile.v.x + 1, sector.tile.v.y, sector.tile.v.z)*nmag + poles > 0.55f*addscl){
            ores.add(SnEnvironment.oreFors);
        }

        if(Simplex.noise3d(seed, 2, 0.5f, scl, sector.tile.v.x + 1, sector.tile.v.y, sector.tile.v.z)*nmag + poles > 0.8f*addscl){
            ores.add(SnEnvironment.orePlanatrium);
        }
    }


    @Override
    protected void passTiles(int x, int y) {

        if(floor == SnEnvironment.crimsonSwamp){
            if(Math.abs(0.5f - noise(x - 90, y, 4, 0.8, 65)) > 0.02){
                floor = SnEnvironment.crimsonMoss;
            }
        }
    }

    @Override
    protected void setupTree() {
        block = rand.chance(0.5) ? SnEnvironment.crimsonTree : SnEnvironment.crimsonTreeDead;
    }
}
