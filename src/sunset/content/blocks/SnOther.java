package sunset.content.blocks;

import static mindustry.type.ItemStack.with;

import arc.graphics.g2d.Draw;
import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.entities.TargetPriority;
import mindustry.game.Team;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.distribution.MassDriver;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.production.Pump;
import mindustry.world.blocks.storage.StorageBlock;
import sunset.content.SnItems;

public class SnOther implements ContentList {
    public static Block

//storage
    naturitcontainer,

//mass-driver
      enojieDriver,
//liquid
    naturitConduit, naturitPump;

    @Override
    public void load() {

        naturitcontainer = new StorageBlock("naturit-container"){{
            requirements(Category.effect, with(Items.titanium, 350, SnItems.naturit, 200));
            size = 5;
            itemCapacity = 3500;
        }};

//liquid
        naturitConduit = new Conduit("naturit-conduit"){{
            requirements(Category.liquid, with(Items.titanium, 2, Items.metaglass, 1, SnItems.naturit, 2));
            liquidCapacity = 18f;
            liquidPressure = 1.035f;
            health = 110;
            placeableLiquid = true;
        }
            @Override
            public boolean canPlaceOn(Tile tile, Team team) {
                return tile.floor().isLiquid;
            }
        };

        naturitPump = new Pump("naturit-pump"){{
            requirements(Category.liquid, with(Items.copper, 120, Items.metaglass, 110, Items.silicon, 40, Items.titanium, 70, SnItems.naturit, 60));
            pumpAmount = 0.28f;
            consumes.power(6.9f);
            liquidCapacity = 100f;
            hasPower = true;
            size = 5;
        }};

        enojieDriver = new MassDriver("enojiemass-driver"){{
            requirements(Category.distribution, with(Items.titanium, 450, Items.thorium, 200, SnItems.nobium, 100, SnItems.enojie, 75));
            size = 5;
            itemCapacity = 210;
            reloadTime = 250f;
            range = 540f;
            consumes.power(4.0f);
        }};
    }
}