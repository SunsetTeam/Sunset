package sunset.content.blocks;

import static mindustry.type.ItemStack.with;

import mindustry.content.Items;
import mindustry.world.blocks.defense.*;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.ForceProjector;
import sunset.content.SnItems;
import sunset.world.blocks.SelfhealWall;

public class SnDefense implements ContentList {
    public static Block

//wall
    forsWall, forsWallLarge, naturiteWall, naturiteWallLarge, enojiewall, enojieWallLarge,
    
//projector   
   forcedome;

    @Override
    public void load() {

        forcedome = new ForceProjector("force-dome"){{
            requirements(Category.effect, with(Items.titanium, 600, Items.thorium, 480, Items.silicon, 300, SnItems.naturite, 250, SnItems.nobium, 240, SnItems.enojie, 210));
            size = 5;
            phaseRadiusBoost = 200f;
            phaseUseTime = 220f;
            radius = 0f;
            shieldHealth = 2000f;
            cooldownNormal = 4f;
            cooldownLiquid = 3f;
            cooldownBrokenBase = 3f;

            consumes.items(with(SnItems.enojie, 3, SnItems.nobium, 2, Items.phaseFabric, 2)).boost();
            consumes.power(14f);
        }};

//walls

        forsWall = new Wall("fors-wall"){{
            requirements(Category.defense, with(SnItems.fors, 6));
            size = 1;
            health = 950;
        }};

        forsWallLarge = new Wall("fors-wall-large"){{
            requirements(Category.defense, with(SnItems.fors, 24));
            health = forsWall.health * 4;
            size = 2;
        }};

        naturiteWall = new SelfhealWall("naturite-wall"){{
            requirements(Category.defense, with(SnItems.naturite, 6));
            size = 1;
            health = 750;
            heal = 0.5f;
        }};

        naturiteWallLarge = new SelfhealWall("naturite-wall-large"){{
            requirements(Category.defense, with(SnItems.naturite, 24));
            health = naturiteWall.health * 4;
            heal = 2f;
            size = 2;
        }};
        
        enojiewall = new Wall("enojie-wall"){{
            requirements(Category.defense, with(SnItems.enojie, 6));
            size = 1;
            health = 1225;
            insulated = true;
            absorbLasers = true;
            schematicPriority = 10;
        }};

        enojieWallLarge = new Wall("enojie-wall-large"){{
            requirements(Category.defense, with(SnItems.enojie, 24));
            health = enojiewall.health * 4;
            size = 2;
            insulated = true;
            absorbLasers = true;
            schematicPriority = 10;
        }};
    }
}
