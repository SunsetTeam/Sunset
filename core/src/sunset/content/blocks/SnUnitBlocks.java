package sunset.content.blocks;

import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitFactory;
import sunset.content.SnItems;
import sunset.content.SnLiquids;
import sunset.content.SnUnitTypes;

import static mindustry.type.ItemStack.with;

public class SnUnitBlocks implements ContentList {
    public static Block

            //factories
            upgradedAirFactory, upgradedGroundFactory,

            //reconstructors
            upgradedAdditiveReconstructor, upgradedMultiplicativeReconstructor, upgradedExponentialReconstructor, upgradedTetrativeReconstructor;

    @Override
    public void load() {
        //region factories
        upgradedAirFactory = new UnitFactory("upgraded-air-factory") {{
            requirements(Category.units, with(SnItems.fors, 80, Items.copper, 70));
            plans = Seq.with(
                    new UnitPlan(SnUnitTypes.copterT1, 70f * 15, with(SnItems.fors, 15, Items.silicon, 20))
//                    new UnitPlan(SnUnitTypes.comet, 60f * 40, with(Items.silicon, 30, SnItems.naturite, 20))
            );
            size = 3;
            consumes.power(2.5f);
        }};

        upgradedGroundFactory = new UnitFactory("upgraded-ground-factory") {{
            requirements(Category.units, with(SnItems.fors, 80, Items.copper, 70, Items.lead, 65));
            plans = Seq.with(
                    new UnitPlan(SnUnitTypes.berserkT1, 75f * 15, with(Items.silicon, 20, SnItems.fors, 15))
            );
            size = 3;
            consumes.power(2.5f);
        }};
        //endregion factories
        //region reconstructors
        upgradedAdditiveReconstructor = new Reconstructor("upgraded-additive-reconstructor") {{
            requirements(Category.units, with(SnItems.fors, 210, Items.copper, 230, SnItems.naturite, 100, Items.silicon, 90));

            size = 3;
            consumes.power(4f);
            consumes.items(with(Items.silicon, 40, SnItems.naturite, 50));

            constructTime = 65f * 10f;

            upgrades.addAll(
                    new UnitType[]{SnUnitTypes.copterT1, SnUnitTypes.copterT2},
//                    new UnitType[]{SnUnitTypes.comet, SnUnitTypes.satelite},
                    new UnitType[]{SnUnitTypes.berserkT1, SnUnitTypes.berserkT2}
            );
        }};

        upgradedMultiplicativeReconstructor = new Reconstructor("upgraded-multiplicative-reconstructor") {{
            requirements(Category.units, with(SnItems.fors, 670, Items.copper, 530,  Items.silicon, 390, SnItems.nobium, 200, Items.metaglass, 100));

            size = 5;
            consumes.power(6f);
            consumes.items(with(Items.silicon, 130, SnItems.nobium, 90, Items.metaglass, 40));

            constructTime = 65f * 30f;

            upgrades.addAll(
                    new UnitType[]{SnUnitTypes.copterT2, SnUnitTypes.copterT3},
                    new UnitType[]{SnUnitTypes.bufferT2, SnUnitTypes.bufferT3}
            );
        }};


        upgradedExponentialReconstructor = new Reconstructor("upgraded-exponential-reconstructor") {{
            requirements(Category.units, with(SnItems.fors, 2100, Items.lead, 2000, SnItems.nobium, 1980, Items.silicon, 1420, SnItems.naturite, 700));

            size = 7;
            consumes.power(13f);
            consumes.items(with(Items.silicon, 510, SnItems.nobium, 510, Items.plastanium, 550));

            consumes.liquid(SnLiquids.burheyna, 1.5f);

            constructTime = 65f * 60f * 1.5f;
            liquidCapacity = 100f;


            upgrades.addAll(
                    new UnitType[]{SnUnitTypes.copterT3, SnUnitTypes.copterT4},
                    new UnitType[]{SnUnitTypes.bufferT3, SnUnitTypes.bufferT4}
            );
        }};

        upgradedTetrativeReconstructor = new Reconstructor("upgraded-tetrative-reconstructor") {{
            requirements(Category.units, with(SnItems.nobium, 3900, Items.lead, 3500, Items.phaseFabric, 2880, Items.silicon, 2470, SnItems.enojie, 2300, SnItems.planatrium, 2100));

            size = 9;
            consumes.power(26f);
            consumes.items(with(Items.silicon, 800, SnItems.enojie, 730, SnItems.planatrium, 670, Items.phaseFabric, 410));

            consumes.liquid(SnLiquids.burheyna, 3.5f);

            constructTime = 65f * 65f * 4;
            liquidCapacity = 220f;


            upgrades.addAll(
                    new UnitType[]{SnUnitTypes.copterT4, SnUnitTypes.copterT6},
                    new UnitType[]{SnUnitTypes.bufferT4, SnUnitTypes.bufferT5}
            );
        }};
        //endregion reconstructors
    }
}
