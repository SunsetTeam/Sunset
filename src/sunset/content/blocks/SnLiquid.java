package sunset.content.blocks;

import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.game.Team;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.production.Pump;
import sunset.content.SnItems;

import static mindustry.type.ItemStack.with;

public class SnLiquid implements ContentList {
    public static Block
            //transportation
            naturiteConduit,

            //storage

            //extraction
            naturitePump;

    @Override
    public void load(){
        //transportation
        naturiteConduit = new Conduit("naturite-conduit") {{
            requirements(Category.liquid, with(Items.titanium, 2, Items.metaglass, 1, SnItems.naturite, 2));
            liquidCapacity = 18f;
            liquidPressure = 1.035f;
            health = 110;
            placeableLiquid = true;
            hasShadow = false;
        }
            @Override
            public boolean canPlaceOn(Tile tile, Team team) {
                return tile.floor().isLiquid;
            }
        };

        //storage

        //extraction
        naturitePump = new Pump("naturite-pump") {{
            requirements(Category.liquid, with(Items.copper, 120, Items.metaglass, 110, Items.silicon, 40, Items.titanium, 70, SnItems.naturite, 60));
            pumpAmount = 0.24f;
            consumes.power(6.9f);
            liquidCapacity = 100f;
            hasPower = true;
            size = 5;
        }};
    }
}
