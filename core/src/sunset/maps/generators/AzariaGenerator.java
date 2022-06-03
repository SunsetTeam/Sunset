package sunset.maps.generators;

import arc.math.Mathf;
import arc.math.geom.Vec3;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.noise.Ridged;
import arc.util.noise.Simplex;
import mindustry.content.Blocks;
import mindustry.world.Block;
import sunset.content.blocks.SnEnvironment;

public class AzariaGenerator extends SnPlanetGenerator {

    public AzariaGenerator() {
         arr = new Block[][]{//13 blocks
         {SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonSand, SnEnvironment.burheynaLiquidFloor, Blocks.basalt, SnEnvironment.crimsonDirt},
         {SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, Blocks.basalt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt},
         {SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.crimsonSand, SnEnvironment.crimsonDirt, SnEnvironment.granite, SnEnvironment.crimsonGrass, SnEnvironment.crimsonSand, SnEnvironment.burheynaLiquidFloor, Blocks.grass, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt},
         {SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, Blocks.stone, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt},
         {SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, Blocks.stone, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonSand, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass},
         {SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonMoss, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, Blocks.stone, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt},
         {SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonSand, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt},
         {SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, SnEnvironment.granite, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass},
         {SnEnvironment.crimsonSand, SnEnvironment.crimsonMoss, SnEnvironment.crimsonSand, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonDirt, Blocks.basalt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass},
         {SnEnvironment.crimsonDirt, SnEnvironment.crimsonSnow, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonSand, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, Blocks.stone, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt},
         {SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonSnow, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonSand, SnEnvironment.crimsonSand, Blocks.basalt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt},
         {SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.granite, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass},
         {SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.crimsonDirt, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonGrass, SnEnvironment.crimsonGrass, SnEnvironment.burheynaLiquidFloor, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt, SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt}
         };

        dec = ObjectMap.of(
        SnEnvironment.crimsonGrass, SnEnvironment.crimsonPine,
        SnEnvironment.granite, Blocks.boulder,
        SnEnvironment.crimsonGrass, SnEnvironment.crimsonPine,
        SnEnvironment.granite, Blocks.boulder
        );

        tars = ObjectMap.of(
        SnEnvironment.crimsonGrass, SnEnvironment.crimsonDirt,
        SnEnvironment.granite, SnEnvironment.crimsonDirt
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
    @Override
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
    }
    protected void passRivers(Room fspawn, int x, int y){
        if(block.solid) return;

        Vec3 v = sector.rect.project(x, y);

        float rr = Simplex.noise2d(sector.id, (float)2, 0.6f, 1f / 7f, x, y) * 0.1f;
        float value = Ridged.noise3d(2, v.x, v.y, v.z, 1, 1f / 53f) + rr - rawHeight(v) * 0f;
        float rrscl = rr * 44 - 2;

        if(value > 0.12f && !Mathf.within(x, y, fspawn.x, fspawn.y, 12 + rrscl)){
            boolean deep = value > 0.12f + 0.1f && !Mathf.within(x, y, fspawn.x, fspawn.y, 15 + rrscl);
            boolean spore = floor != SnEnvironment.crimsonSand && floor != Blocks.salt;
            //do not place rivers on ice, they're frozen
            //ignore pre-existing liquids
            if(!(floor == SnEnvironment.crimsonIce || floor == SnEnvironment.crimsonIceSnow || floor == SnEnvironment.crimsonSnow || floor.asFloor().isLiquid)){
                floor = spore ?
                        (deep ? SnEnvironment.burheynaLiquidFloor : SnEnvironment.burheynaLiquidFloor) :
                        (deep ? SnEnvironment.burheynaLiquidFloor :
                                (floor == SnEnvironment.crimsonSand ? SnEnvironment.burheynaLiquidFloor : SnEnvironment.burheynaLiquidFloor));
            }
        }
    }
}
