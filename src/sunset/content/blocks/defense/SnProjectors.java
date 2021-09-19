package sunset.content.blocks.defense;

import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.ForceProjector;
import sunset.content.SnItems;
import sunset.world.blocks.defense.projectors.DeflectorProjector;

import static mindustry.type.ItemStack.with;

public class SnProjectors implements ContentList {
    public static Block forcedome, deflectorProjector;

    @Override
    public void load(){
        forcedome = new ForceProjector("force-dome"){{
            requirements(Category.effect, with(Items.titanium, 600, Items.thorium, 480, Items.silicon, 300, SnItems.naturite, 250, SnItems.nobium, 240, SnItems.enojie, 210));
            size = 5;
            radius = 220f;
            shieldHealth = 2100f;
            cooldownNormal = 2f;
            cooldownLiquid = 3f;
            cooldownBrokenBase = 1.1f;

            consumes.items(with(SnItems.enojie, 2, SnItems.nobium, 2, Items.phaseFabric, 2));
            consumes.power(15f);
        }};

        deflectorProjector = new DeflectorProjector("deflector-projector"){{
            requirements(Category.effect, with(Items.silicon, 1200, Items.titanium, 2500, Items.thorium, 1300, Items.phaseFabric, 900, Items.surgeAlloy, 1000, SnItems.enojie, 850, SnItems.coldent, 900));
            size = 3;
            health = 900;
            radius = 150;
            phaseRadiusBoost = 100;
            phaseShieldBoost = 1200;
            shieldHealth = 3500;
            cooldownNormal = 3.1f;
            cooldownLiquid = 4.7f;
            cooldownBrokenBase = 2.1f;
            consumes.items(with(Items.phaseFabric, 3, SnItems.coldent, 5));
            consumes.power(20f);
        }};
    }
}
