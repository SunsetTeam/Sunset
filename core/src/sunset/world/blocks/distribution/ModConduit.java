package sunset.world.blocks.distribution;

import arc.util.Nullable;
import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.liquid.Conduit;

public class ModConduit extends Conduit {
    public boolean onLiquid = true;

    public @Nullable Block conduitReplacement = null;
    public ModConduit (String name){
        super(name);
    }

    public void init(){
        super.init();


        if(conduitReplacement == null || !(conduitReplacement instanceof Conduit)) conduitReplacement = Blocks.bridgeConduit;
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team) {
        if(onLiquid){
            return tile.floor().isLiquid;
        }
        return false;
    }
}
