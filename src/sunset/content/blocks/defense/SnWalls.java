package sunset.content.blocks.defense;

import static mindustry.type.ItemStack.with;

import mindustry.content.Items;
import mindustry.world.blocks.defense.*;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.BuildVisibility;
import sunset.content.SnItems;
import sunset.world.blocks.defense.walls.AntiPierceWall;
import sunset.world.blocks.defense.walls.SelfhealWall;

import static mindustry.type.ItemStack.with;

public class SnWalls implements ContentList {
    public static Block

            forsWall, forsWallLarge,
            naturiteWall, naturiteWallLarge,
            enojiewall, enojieWallLarge,

            sandboxWall;

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

        naturiteWall = new SelfhealWall("naturite-wall"){{
            requirements(Category.defense, with(SnItems.naturite, 6));
            size = 1;
            health = 810;
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

        sandboxWall = new AntiPierceWall("sandbox-wall"){{
            requirements(Category.defense, BuildVisibility.sandboxOnly, with(SnItems.enojie, 24));
            pierceDebuff = 5;
            damageDebuff = 0.7225f;
            health = 999999999;
            size = 2;
            insulated = true;
            absorbLasers = true;
            schematicPriority = 10;
        }};
    }
}
