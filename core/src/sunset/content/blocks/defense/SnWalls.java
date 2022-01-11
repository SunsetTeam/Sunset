package sunset.content.blocks.defense;

import mindustry.ctype.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import sunset.content.*;
import sunset.world.blocks.defense.walls.*;

import static mindustry.type.ItemStack.*;

public class SnWalls implements ContentList{
    public static Block

    forsWall, forsWallLarge,
    naturiteWall, naturiteWallLarge,
    enojiewall, enojieWallLarge,

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
        //heal region
        naturiteWall = new SnWall("naturite-wall"){{
            requirements(Category.defense, with(SnItems.naturite, 6));
            size = 1;
            health = 810;
            healAmount(0.5f);
        }};
        naturiteWallLarge = new SnWall("naturite-wall-large"){{
            requirements(Category.defense, with(SnItems.naturite, 24));
            health = naturiteWall.health * 4;
            healAmount(2f);
            size = 2;
        }};
        //region end

        //AntiPierceWall region
        enojiewall = new SnWall("enojie-wall"){{
            requirements(Category.defense, with(SnItems.enojie, 6));
            pierceMultiplier = 2;
            collidedDamageMultiplier = 0.85f;
            size = 1;
            health = 1225;
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
        //end region

        indestructibleWall = new IndestructibleWall("indestructible-wall"){{
            size = 1;
        }};
        indestructibleWallLarge = new IndestructibleWall("indestructible-wall-large"){{
            size = 2;
        }};
    }

}
