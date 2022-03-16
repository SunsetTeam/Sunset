package sunset.world.blocks.distribution;

import mindustry.game.Team;
import mindustry.world.Tile;
import mindustry.world.blocks.liquid.ArmoredConduit;

public class ModConduit extends ArmoredConduit {
    public boolean onLiquid = true;

    public ModConduit (String name){
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

