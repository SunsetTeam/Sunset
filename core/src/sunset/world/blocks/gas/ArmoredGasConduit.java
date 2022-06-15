package sunset.world.blocks.gas;

import gas.type.Gas;
import gas.world.blocks.gas.GasConduit;
import mindustry.gen.Building;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.Tile;

public class ArmoredGasConduit extends GasConduit {
    public ArmoredGasConduit(String name) {
        super(name);
        leaks = false;
    }

    @Override
    public boolean blends(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock) {
//        if (!(otherblockSimple instanceof GasBlock otherblock)) return false;
        return (otherblock.outputsLiquid && blendsArmored(tile, rotation, otherx, othery, otherrot, otherblock)) || (lookingAt(tile, rotation, otherx, othery, otherblock) && otherblock.hasLiquids);
    }

    public class ArmoredGasConduitBuild extends GasConduitBuild {
        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
//            return super.acceptLiquid(source, liquid);
            return super.acceptLiquid(source, liquid) && (tile == null || source.block instanceof GasConduit || source.tile.absoluteRelativeTo(tile.x, tile.y) == rotation);
        }
    }
}
