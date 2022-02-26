package sunset.content.blocks.defense;

import arc.util.Time;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.BuildVisibility;
import sunset.content.SnItems;
import sunset.content.SnStatusEffects;
import sunset.world.blocks.defense.walls.IndestructibleWall;
import sunset.world.blocks.defense.walls.SnWall;
import sunset.world.blocks.environment.RadiationDebris;

import static mindustry.type.ItemStack.with;

public class SnWalls implements ContentList{
    public static Block

    forsWall, forsWallLarge,
    naturiteWall, naturiteWallLarge,
    enojiewall, enojieWallLarge,

    radiationWallSmall,

    indestructibleWall, indestructibleWallLarge;

    @Override
    public void load(){
        forsWall = new Wall("fors-wall"){{
            requirements(Category.defense, with(SnItems.fors, 6));
            size = 1;
            health = 990;
        }};
        forsWallLarge = new Wall("fors-wall-large"){{
            requirements(Category.defense, with(SnItems.fors, 24));
            health = forsWall.health * 4;
            size = 2;
        }};
        //region heal
        naturiteWall = new SnWall("naturite-wall"){{
            requirements(Category.defense, with(SnItems.naturite, 6));
            size = 1;
            health = 820;
            healAmount(0.5f);
        }};
        naturiteWallLarge = new SnWall("naturite-wall-large"){{
            requirements(Category.defense, with(SnItems.naturite, 24));
            health = naturiteWall.health * 4;
            healAmount(2f);
            size = 2;
        }};
        //endregion
        //region AntiPierceWall
        enojiewall = new SnWall("enojie-wall"){{
            requirements(Category.defense, with(SnItems.enojie, 6));
            pierceMultiplier = 2;
            collidedDamageMultiplier = 0.85f;
            size = 1;
            health = 1245;
            insulated = true;
            absorbLasers = true;
            schematicPriority = 10;
        }};
        enojieWallLarge = new SnWall("enojie-wall-large"){{
            requirements(Category.defense, with(SnItems.enojie, 24));
            pierceMultiplier = 5;
            collidedDamageMultiplier = 0.7225f;
            health = enojiewall.health * 4;
            size = 2;
            insulated = true;
            absorbLasers = true;
            schematicPriority = 10;
        }};

        radiationWallSmall = new RadiationDebris("radiation-wall-small"){{
            requirements(Category.defense, BuildVisibility.sandboxOnly, with(SnItems.planatrium, 6));
            size = 2;
            variants = 3;
            buildCostMultiplier = 2f;
            radiationStatus = SnStatusEffects.radiation;
        }};
        //endregion

        indestructibleWall = new IndestructibleWall("indestructible-wall"){{
            size = 1;
            placeableLiquid = true;
        }};
        indestructibleWallLarge = new IndestructibleWall("indestructible-wall-large"){{
            size = 2;
            placeableLiquid = true;
        }};
    }

}
