package sunset.content.blocks;

import static mindustry.type.ItemStack.with;

import arc.graphics.g2d.Draw;
import mindustry.content.Items;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.entities.TargetPriority;
import mindustry.game.Team;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.type.ItemStack;
import mindustry.world.blocks.distribution.MassDriver;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.production.Pump;
import mindustry.world.blocks.storage.StorageBlock;
import sunset.content.SnItems;

public class SnOther implements ContentList {
    public static Block

//storage
    naturiteContainer,

//mass-driver
    enojieDriver,

//liquid
    naturiteConduit, naturitePump;

    @Override
    public void load() {

        naturiteContainer = new StorageBlock("naturite-container"){{
            requirements(Category.effect, with(Items.titanium, 350, SnItems.naturite, 200));
            size = 5;
            itemCapacity = 3500;
        }};

//liquid
        naturiteConduit = new Conduit("naturite-conduit"){{
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

        naturitePump = new Pump("naturite-pump"){{
            requirements(Category.liquid, with(Items.copper, 120, Items.metaglass, 110, Items.silicon, 40, Items.titanium, 70, SnItems.naturite, 60));
            pumpAmount = 0.22f;
            consumes.power(6.9f);
            liquidCapacity = 100f;
            hasPower = true;
            size = 5;
        }};

//drivers
        enojieDriver = new MassDriver("enojiemass-driver"){{
            requirements(Category.distribution, with(Items.titanium, 450, Items.thorium, 200, Items.surgeAlloy, 180, SnItems.nobium, 150, SnItems.enojie, 130));
            size = 5;
            itemCapacity = 210;
            reloadTime = 250f;
            range = 540f;
            consumes.power(4.0f);
        }};
    }
}
