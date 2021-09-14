package sunset.maps.generators;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Vec3;
import arc.struct.IntSeq;
import arc.struct.ObjectMap;
import arc.util.Tmp;
import arc.util.noise.Noise;
import arc.util.noise.RidgedPerlin;
import arc.util.noise.Simplex;
import mindustry.content.Blocks;
import mindustry.graphics.g3d.HexMesher;
import mindustry.graphics.g3d.PlanetGrid;
import mindustry.maps.generators.BaseGenerator;
import mindustry.maps.generators.BasicGenerator;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.type.Sector;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.TileGen;
import mindustry.world.Tiles;

public class ModGenerator extends PlanetGenerator {
    public final Simplex simplex = new Simplex();
    public final RidgedPerlin rid = new RidgedPerlin(1, 2);
    public  Block[][] arr;
    public  float scl = 0.f;
    public  float waterOffset = 0.f;
    public   BaseGenerator basegen = new BaseGenerator();
    public float water = 0.f;
    ObjectMap<Block,Block> dec;

    ObjectMap<Block,Block> tars;

    public ModGenerator() {
        dec=new ObjectMap<>();
        tars=new ObjectMap<>();
        arr=new Block[0][0];
    }

    public Color getColor(Vec3 position) {
        Block block = this.getBlock(position);

        Tmp.c1.set(block.mapColor).a = 1 - block.albedo;
        return Tmp.c1;
    }

    public Block getBlock(Vec3 pos) {
        float height = this.rawHeight(pos);

        Tmp.v31.set(pos);
        pos = Tmp.v33.set(pos).scl(scl);
        float rad = scl;
        float temp = Mathf.clamp(Math.abs(pos.y * 2) / rad);
        float tnoise =(float) simplex.octaveNoise3D(7d, 0.56d, 1d / 3d, pos.x, pos.y + 999d, pos.z);
        temp = Mathf.lerp(temp, tnoise, 0.5f);
        height *= 0.9f;
        height = Mathf.clamp(height);

        float tar =(float) simplex.octaveNoise3D(4, 0.55, 0.5, pos.x, pos.y + 999, pos.z) * 0.3f + Tmp.v31.dst(0, 0, 1) * 0.2f;
        Block res = arr[
                Mathf.clamp(Mathf.floor(temp * arr.length), 0, arr[0].length - 1)][Mathf.clamp(Mathf.floor(height * arr[0].length), 0, arr[0].length - 1)
                ];

        if (tar > 0.5) {
            return tars.get(res, res);
        } else {
            return res;
        }

    }

    public float rawHeight(Vec3 pos) {
        pos = Tmp.v33.set(pos);
        pos.scl(scl);

        return (Mathf.pow((float) simplex.octaveNoise3D(7, 0.5, 1d / 3d, pos.x, pos.y, pos.z), 2.3f) + waterOffset) / (1 + waterOffset);
    }

    public float getHeight(Vec3 position) {
        float height = this.rawHeight(position);
        return Math.max(height, water);
    }

    public void genTile(Vec3 position, TileGen tile) {
        tile.floor = this.getBlock(position);
        tile.block = tile.floor.asFloor().wall;

        if (rid.getValue(position.x, position.y, position.z, 22) > 0.32) {
            tile.block = Blocks.air;
        }
    }

    //public abstract class PlanetGenerator extends BasicGenerator implements HexMesher {
    //protected IntSeq ints = new IntSeq();
    //protected Sector sector;
    //protected Simplex noise = new Simplex();

    /** Should generate sector bases for a planet. */
    /*public void generateSector(Sector sector){
        PlanetGrid.Ptile tile = sector.tile;

        boolean any = false;
        float noise = Noise.snoise3(tile.v.x, tile.v.y, tile.v.z, 0.001f, 0.5f);

        if(noise > 0.027){
            any = true;
        }

        if(noise < 0.15){
            for(PlanetGrid.Ptile other : tile.tiles){
                //no sectors near start sector!
                if(sector.planet.getSector(other).id == sector.planet.startSector){
                    return;
                }

                if(sector.planet.getSector(other).generateEnemyBase){
                    any = false;
                    break;
                }
            }
        }

        if(any){
            sector.generateEnemyBase = true;
        }
    }

    protected void genTile(Vec3 position, TileGen tile){

    }

    @Override
    protected float noise(float x, float y, double octaves, double falloff, double scl, double mag){
        Vec3 v = sector.rect.project(x, y);
        return (float)noise.octaveNoise3D(octaves, falloff, 1f / scl, v.x, v.y, v.z) * (float)mag;
    }

    public void generate(Tiles tiles, Sector sec){
        this.tiles = tiles;
        this.sector = sec;
        this.rand.setSeed(sec.id);

        TileGen gen = new TileGen();
        tiles.each((x, y) -> {
            gen.reset();
            Vec3 position = sector.rect.project(x / (float)tiles.width, y / (float)tiles.height);

            genTile(position, gen);
            tiles.set(x, y, new Tile(x, y, gen.floor, gen.overlay, gen.block));
        });

        generate(tiles);
    }*/
    //}
}