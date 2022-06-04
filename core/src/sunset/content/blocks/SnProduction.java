package sunset.content.blocks;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.AttributeCrafter;
import mindustry.world.blocks.production.SolidPump;
import mindustry.world.draw.DrawRotator;
import mindustry.world.meta.Env;
import sunset.content.SnAttribute;
import sunset.content.SnFx;
import sunset.content.SnItems;
import sunset.content.SnLiquids;
import sunset.type.blocks.DrillItem;
import sunset.world.blocks.production.raw.DRDrill;
import sunset.world.blocks.production.raw.PrecussionDrill;

import static mindustry.type.ItemStack.with;

/** This category is for blocks that produce raw products. (Such as cultivator, drill etc.) */
public class SnProduction implements ContentList{
    public static Block
    //pumps
    mechanicalWaterExtractor,
    //crafters
    advancedCultivator,

    //drills
    electroPneumaticDrill,
    magneticDrill,
    percussionDrill,
    enojieDrill;

    @Override
    public void load(){

        //region crafters
        //endregion crafters
        //region drills

        percussionDrill = new PrecussionDrill("percussion-drill"){{
            requirements(Category.production, with(Items.copper, 100, Items.silicon, 90, Items.titanium, 90, Items.thorium, 85, SnItems.nobium, 80, SnItems.naturite, 70));
            size = 5;
            hasPower = true;
            powerUse = 5f;
            shakeIntensity = 5f;
            shakeDuration = 10f;
            hardnessDrillMultiplier = 8;
            liquidBoost = 3.86f;
            itemCountMultiplier = 0.5f;
            canDump = true;
            consumes.liquid(Liquids.water, 0.15f).boost();
            drillItems(
            new DrillItem(Items.graphite, 1f),
            new DrillItem(Items.surgeAlloy, 1.25f),
            new DrillItem(SnItems.nobium, 1.6f)
            );
        }};

        //endregion drills
    }
}
