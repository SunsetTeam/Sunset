package sunset.content.blocks;

import static mindustry.type.ItemStack.with;

import mindustry.content.Items;
import mindustry.world.blocks.defense.*;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.ForceProjector;
import sunset.content.SnItems;

public class SnDefense implements ContentList {
    public static Block

//wall
    forsWall, forsWallLarge, naturitWall, naturitWallLarge, enojiewall, enojieWallLarge,
    
//projector   
   forcedome;

    @Override
    public void load() {

        forcedome = new ForceProjector("force-dome"){{
            requirements(Category.effect, with(Items.titanium, 600, Items.thorium, 480, Items.silicon, 300, SnItems.naturit, 250, SnItems.nobium, 240, SnItems.enojie, 210));
            size = 5;
            phaseRadiusBoost = 65f;
            radius = 153.6f;
            shieldHealth = 2000f;
            cooldownNormal = 5.0f;
            cooldownLiquid = 3.8f;
            cooldownBrokenBase = 1.0f;

            consumes.item(Items.phaseFabric).boost();
            consumes.power(11f);
        }};

//walls

        naturitWall = new Wall("naturit-wall"){{
            requirements(Category.defense, with(SnItems.naturit, 6));
            size = 1;
            health = 750;
        }};

        naturitWallLarge = new Wall("naturit-wall-large"){{
            requirements(Category.defense, with(SnItems.naturit, 24));
            health = 3000;
            size = 2;
        }};

        forsWall = new Wall("fors-wall"){{
            requirements(Category.defense, with(SnItems.fors, 6));
            size = 1;
            health = 950;
        }};

        forsWallLarge = new Wall("fors-wall-large"){{
            requirements(Category.defense, with(SnItems.fors, 24));
            size = 1;
            health = 4800;
            size = 2;
        }};
        
        enojiewall = new Wall("enojie-wall"){{
            requirements(Category.defense, with(SnItems.enojie, 6));
            size = 1;
            health = 1350;
            insulated = true;
            absorbLasers = true;
            schematicPriority = 10;
        }};

        enojieWallLarge = new Wall("enojie-wall-large"){{
            requirements(Category.defense, with(SnItems.enojie, 24));
            health = 5400;
            size = 2;
            insulated = true;
            absorbLasers = true;
            schematicPriority = 10;
        }};
    }
}
