package sunset.content.blocks;

import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.Cultivator;
import mindustry.world.blocks.production.Drill;
import sunset.content.SnFx;
import sunset.content.SnItems;
import sunset.world.blocks.production.raw.PrecussionDrill;
import sunset.world.draw.DrawModRotator;

import static mindustry.type.ItemStack.with;

/** This category is for blocks that produce raw products. (Such as cultivator, drill etc.) */
public class SnRaw implements ContentList{
    public static Block
            //crafters
            advancedCultivator,

            //drills
            electroPneumaticdrill,
            percussionDrill;

    @Override
    public void load(){
        //crafters
        advancedCultivator = new Cultivator("advanced-cultivator") {{
            requirements(Category.production, with(Items.copper, 200, Items.lead, 200, Items.silicon, 180, Items.metaglass, 140, Items.titanium, 170, Items.phaseFabric, 155));
            size = 4;
            health = 990;
            craftEffect = SnFx.cultivatorSmeltsmoke;
            craftTime = 200f;
            drawer = new DrawModRotator();
            outputItem = new ItemStack(Items.sporePod, 6);
            itemCapacity = 30;
            liquidCapacity = 40f;

            consumes.liquid(Liquids.water, 0.4f);
            consumes.power(2f);
        }};

        //drills

        electroPneumaticdrill = new Drill("pneumatic-drill"){{
            requirements(Category.production, with(Items.copper, 20, Items.graphite, 15, Items.silicon, 10));
            drillTime = 340;
            size = 3;
            hasPower = true;
            tier = 4;

            consumes.power(0.6f);
            consumes.liquid(Liquids.water, 0.07f).boost();
        }};

        percussionDrill = new PrecussionDrill("percussion-drill") {{
            requirements(Category.production, with(Items.copper, 100, Items.silicon, 90, Items.titanium, 90, Items.thorium, 85, SnItems.nobium, 80, SnItems.naturite, 70));
            size = 5;
            hasPower = true;
            powerUse = 4.25f;
            hardnessDrillMultiplier = 8;
            liquidBoostIntensity = 3.86f;
            itemCountMultiplier = 0.5f;
            consumes.liquid(Liquids.water, 0.15f).boost();
            drillItems.addAll(
                    new DrillItem(Items.graphite, 1f),
                    new DrillItem(Items.surgeAlloy, 1.25f),
                    new DrillItem(SnItems.nobium, 1.6f)
            );
        }};
    }
}
