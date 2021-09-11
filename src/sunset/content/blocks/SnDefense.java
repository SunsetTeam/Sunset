package sunset.content.blocks;

import static mindustry.type.ItemStack.with;

import mindustry.content.Items;
import mindustry.world.blocks.defense.*;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.world.Block;
import sunset.content.SnItems;
import sunset.world.blocks.defense.walls.AntiPierceWall;
import sunset.world.blocks.defense.walls.SelfhealWall;
import sunset.world.blocks.defense.projectors.DeflectorProjector;

public class SnDefense implements ContentList {
    public static Block

    //wall
    forsWall, forsWallLarge, naturiteWall, naturiteWallLarge, enojiewall, enojieWallLarge,
    
    //projector
    forcedome, deflectorProjector;

    @Override
    public void load() {

        forcedome = new ForceProjector("force-dome"){{
            requirements(Category.effect, with(Items.titanium, 600, Items.thorium, 480, Items.silicon, 300, SnItems.naturite, 250, SnItems.nobium, 240, SnItems.enojie, 210));
            size = 5;
            radius = 220f;
            shieldHealth = 2100f;
            cooldownNormal = 2f;
            cooldownLiquid = 3f;
            cooldownBrokenBase = 1;

            consumes.items(with(SnItems.enojie, 2, SnItems.nobium, 2, Items.phaseFabric, 2));
            consumes.power(15f);
        }};

        deflectorProjector = new DeflectorProjector("deflector-projector"){{
           requirements(Category.effect, with(Items.silicon, 100, Items.titanium, 250, Items.thorium, 200, Items.phaseFabric, 120, Items.surgeAlloy, 100, SnItems.enojie, 100, SnItems.coldent, 230));
           size = 3;
           health = 900;
           radius = 150;
           phaseRadiusBoost = 300;
           phaseShieldBoost = 1200;
           shieldHealth = 5000;
           cooldownNormal = 3.1f;
           cooldownLiquid = 4.7f;
           cooldownBrokenBase = 2.1f;
           consumes.items(with(Items.phaseFabric, 3, SnItems.coldent, 5)).boost();
           consumes.power(25);
        }};

        //region walls
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
        
        enojiewall = new AntiPierceWall("enojie-wall"){{
            requirements(Category.defense, with(SnItems.enojie, 6));
            pierceDebuff = 2;
            damageDebuff = 0.85f;
            size = 1;
            health = 1225;
            insulated = true;
            absorbLasers = true;
            schematicPriority = 10;
        }};
        enojieWallLarge = new AntiPierceWall("enojie-wall-large"){{
            requirements(Category.defense, with(SnItems.enojie, 24));
            pierceDebuff = 5;
            damageDebuff = 0.7225f;
            health = enojiewall.health * 4;
            size = 2;
            insulated = true;
            absorbLasers = true;
            schematicPriority = 10;
        }};
        //endregion walls
    }
}
