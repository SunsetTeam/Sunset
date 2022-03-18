package sunset.world.blocks.distribution;

import mindustry.game.Team;
import mindustry.world.Tile;
import mindustry.world.blocks.liquid.Conduit;

public class ModConduit extends Conduit {
    public boolean onLiquid = true;
    public boolean onFloor = true;

    public ModConduit (String name){
        super(name);
        leaks = false;
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team) {
        if(onLiquid){
            return tile.floor().isLiquid;
        }

        if(onFloor){
            return tile.floor().isFloor();
        }
        return false;
    }
}

