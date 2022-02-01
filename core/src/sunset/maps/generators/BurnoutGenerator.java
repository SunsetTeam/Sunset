package sunset.maps.generators;

import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.noise.*;
import mindustry.content.*;
import mindustry.graphics.g3d.PlanetGrid.*;
import mindustry.type.*;
import mindustry.world.*;
import sunset.content.blocks.*;

public class BurnoutGenerator extends SnPlanetGenerator{
    public BurnoutGenerator(){

        arr = new Block[][]{
        {SnEnvironment.hotSlag3, SnEnvironment.hotSlag2, SnEnvironment.hotSlag1, SnEnvironment.hotSlag1, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag},
        {SnEnvironment.hotSlag3, SnEnvironment.hotSlag2, SnEnvironment.hotSlag1, SnEnvironment.hotSlag1, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag},
        {SnEnvironment.hotSlag1, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.magmarock, Blocks.magmarock, Blocks.magmarock, Blocks.hotrock, Blocks.hotrock, Blocks.charr, Blocks.charr},
        {SnEnvironment.hotSlag3, SnEnvironment.hotSlag2, SnEnvironment.hotSlag1, Blocks.slag, Blocks.magmarock, Blocks.magmarock, Blocks.hotrock, Blocks.hotrock, Blocks.hotrock, Blocks.charr, Blocks.basalt},
        {Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.magmarock, Blocks.magmarock, Blocks.hotrock, Blocks.hotrock},
        {SnEnvironment.hotSlag3, SnEnvironment.hotSlag1, Blocks.slag, Blocks.slag, Blocks.magmarock, Blocks.magmarock, Blocks.hotrock, Blocks.hotrock, Blocks.charr, Blocks.darksand, Blocks.darksand, Blocks.darksand},
        {Blocks.craters, Blocks.craters, Blocks.craters, Blocks.craters, Blocks.charr, Blocks.charr, Blocks.stone, Blocks.stone, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.basalt, Blocks.basalt},
        {Blocks.basalt, Blocks.basalt, Blocks.basalt, Blocks.charr, Blocks.slag, Blocks.slag, Blocks.craters, Blocks.craters, Blocks.craters, Blocks.craters, Blocks.darksand, Blocks.darksand, Blocks.stone},
        {Blocks.craters, Blocks.craters, Blocks.craters, Blocks.charr, Blocks.charr, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.basalt, Blocks.basalt},
        {Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.slag, Blocks.magmarock, Blocks.magmarock, Blocks.magmarock, Blocks.magmarock},
        {Blocks.darksand, Blocks.darksand, Blocks.charr, Blocks.charr, Blocks.charr, Blocks.basalt, Blocks.basalt, Blocks.hotrock, Blocks.hotrock, Blocks.stone, Blocks.stone, Blocks.stone, Blocks.stone},
        {Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.charr, Blocks.charr, Blocks.charr, Blocks.basalt, Blocks.hotrock, Blocks.hotrock, Blocks.stone, Blocks.stone, Blocks.stone, Blocks.stone},
        {Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand}
        };

        tars = ObjectMap.of(
        Blocks.slag, Blocks.slag,
        Blocks.magmarock, Blocks.hotrock
        );

        dec = ObjectMap.of(
        Blocks.darksand, Blocks.darksand,
        Blocks.charr, Blocks.charr,
        Blocks.craters, Blocks.craters,
        Blocks.basalt, Blocks.basalt
        );
    }

    @Override
    public void generateSector(Sector sector){

        //these always have bases
        if(sector.id == 154 || sector.id == 0){
            sector.generateEnemyBase = true;
            return;
        }

        Ptile tile = sector.tile;

        boolean any = false;
        float poles = Math.abs(tile.v.y);
        float noise = Noise.snoise3(tile.v.x, tile.v.y, tile.v.z, 0.001f, 0.58f);

        if(noise + poles / 7.1 > 0.12 && poles > 0.23){
            any = true;
        }

        if(noise < 0.16){
            for(Ptile other : tile.tiles){
                Sector osec = sector.planet.getSector(other);

                //no sectors near start sector!
                if(
                osec.id == sector.planet.startSector || //near starting sector
                osec.generateEnemyBase && poles < 0.85 || //near other base
                (sector.preset != null && noise < 0.11) //near preset
                ){
                    return;
                }
            }
        }

        sector.generateEnemyBase = any;
    }

    @Override
    protected void passRivers(Room fspawn, int x, int y){
        if(block.solid) return;

        Vec3 v = sector.rect.project(x, y);

        float rr = Simplex.noise2d(sector.id, (float)2, 0.6f, 1 / 7, x, y) * 0.1f;
        float value = Ridged.noise3d(2, v.x, v.y, v.z, 1, 1f / 53f) + rr - rawHeight(v) * 0f;
        float rrscl = rr * 44 - 2;

        if(value > 0.12f && !Mathf.within(x, y, fspawn.x, fspawn.y, 12 + rrscl)){
            boolean deep = value > 0.12f + 0.1f && !Mathf.within(x, y, fspawn.x, fspawn.y, 15 + rrscl);
            boolean spore = floor != SnEnvironment.burningAsh && floor != SnEnvironment.obsidian;
            //do not place rivers on ice, they're frozen
            //ignore pre-existing liquids
            if(!(floor == SnEnvironment.ash || floor == Blocks.slag || floor == SnEnvironment.burningAsh || floor.asFloor().isLiquid)){
                floor = spore ?
                (deep ? Blocks.slag : SnEnvironment.ash) :
                (deep ? SnEnvironment.ash :
                (floor == SnEnvironment.burningAsh ? Blocks.darksand : SnEnvironment.obsidian));
            }
        }
    }

    @Override
    protected void setupOres(Seq<Block> ores, float poles, float nmag, float scl, float addscl){
        super.setupOres(ores, poles, nmag, scl, addscl);

        if(Simplex.noise3d(seed, 3, 0.5f, scl, sector.tile.v.x + 1, sector.tile.v.y, sector.tile.v.z) * nmag + poles > 0.55f * addscl){
            ores.add(SnEnvironment.oreFlameid);
        }
    }

    @Override
    protected void passTiles(int x, int y){
        if(floor == SnEnvironment.burningAsh){
            if(Math.abs(0.5f - noise(x - 90, y, 4, 0.8, 65)) > 0.02){
                floor = Blocks.slag;
            }
        }
        super.passTiles(x, y);
    }
}
