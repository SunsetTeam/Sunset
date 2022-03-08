package sunset.world.blocks.gas;

import gas.world.blocks.gas.GasConduit;
import mindustry.game.Team;
import mindustry.world.Tile;

public class ModGasPipe extends GasConduit {

    public boolean onLiquid = true;

    public ModGasPipe (String name){
        super(name);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team) {
        if(onLiquid){
            return tile.floor().isLiquid;
        }
        return false;
    }
}
