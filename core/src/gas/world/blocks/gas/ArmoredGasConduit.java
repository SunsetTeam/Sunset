package gas.world.blocks.gas;

import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.distribution.DirectionLiquidBridge;
import mindustry.world.blocks.liquid.Conduit;

public class ArmoredGasConduit extends GasConduit{
    public ArmoredGasConduit(String name){
        super(name);
        leaks = false;
    }

    @Override
    public boolean blends(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock){
        return (otherblock.outputsLiquid && blendsArmored(tile, rotation, otherx, othery, otherrot, otherblock)) ||
                (lookingAt(tile, rotation, otherx, othery, otherblock) && otherblock.hasLiquids);
    }

    public class ArmoredGasConduitBuild extends GasConduitBuild{
        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
            return super.acceptLiquid(source, liquid) && (liquid==null || liquid.gas) && (tile == null || source.block instanceof GasConduit || source.block instanceof DirectionLiquidBridge ||
                    source.tile.absoluteRelativeTo(tile.x, tile.y) == rotation);
        }
    }
}
