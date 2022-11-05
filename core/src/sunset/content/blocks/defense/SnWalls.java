package sunset.content.blocks.defense;

import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import sunset.content.*;
import sunset.world.blocks.defense.walls.*;

import static mindustry.type.ItemStack.with;

public class SnWalls{
    public static Block

    forsWall, forsWallLarge,
    eriusWall, eriusWalllarge,
    naturiteWall, naturiteWallLarge,
    enojiewall, enojieWallLarge,

    indestructibleWall, indestructibleWallLarge;

    public static void load(){
        forsWall = new Wall("fors-wall"){{
            requirements(Category.defense, with(SnItems.fors, 6));
            size = 1;
            health = 990;
        }};
        forsWallLarge = new Wall("fors-wall-large"){{
            requirements(Category.defense, with(SnItems.fors, 24));
            size = 2;
            health = forsWall.health * size * size;
        }};
        eriusWall = new Wall("erius-wall"){{
            requirements(Category.defense, with(SnItems.erius, 4));
            health = 640;
            size = 1;
        }};
        eriusWalllarge = new Wall("erius-wall-large"){{
            requirements(Category.defense, with(SnItems.erius, 16));
            size = 2;
            health = eriusWall.health * size * size;
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
            healAmount(2f);
            size = 2;
            health = naturiteWall.health * size * size;
        }};
        //endregion
        //region AntiPierceWall
        enojiewall = new SnWall("enojie-wall"){{
            requirements(Category.defense, with(SnItems.entarium, 6));
            pierceMultiplier = 2;
            collidedDamageMultiplier = 0.85f;
            size = 1;
            health = 1245;
            insulated = true;
            absorbLasers = true;
            schematicPriority = 10;
        }};
        enojieWallLarge = new SnWall("enojie-wall-large"){{
            requirements(Category.defense, with(SnItems.entarium, 24));
            pierceMultiplier = 5;
            collidedDamageMultiplier = 0.7225f;
            size = 2;
            health = enojiewall.health * size * size;
            insulated = true;
            absorbLasers = true;
            schematicPriority = 10;
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
