package sunset.world.blocks.gas;

import gas.type.Gas;
import gas.world.GasBlock;
import gas.world.blocks.gas.GasConduit;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Tile;

public class ArmoredGasConduit extends GasConduit {
    public ArmoredGasConduit(String name) {
        super(name);
        leaks = false;
    }

    @Override
    public boolean blends(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblockSimple) {
        if (!(otherblockSimple instanceof GasBlock otherblock)) return false;
        return (otherblock.outputsGas && blendsArmored(tile, rotation, otherx, othery, otherrot, otherblock)) || (lookingAt(tile, rotation, otherx, othery, otherblock) && otherblock.hasGasses);
    }

    public class ArmoredGasConduitBuild extends GasConduitBuild {
        @Override
        public boolean acceptGas(Building source, Gas gas) {
            return super.acceptGas(source, gas) && (tile == null || source.block instanceof GasConduit || source.tile.absoluteRelativeTo(tile.x, tile.y) == rotation);
        }
    }
}
