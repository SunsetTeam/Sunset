package sunset.content.blocks;

import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.units.*;
import sunset.content.*;

import static mindustry.type.ItemStack.with;

public class SnUnitBlocks{
    public static Block

    //factories
    upgradedAirFactory, upgradedGroundFactory,

    //reconstructors
    upgradedAdditiveReconstructor, upgradedMultiplicativeReconstructor, upgradedExponentialReconstructor, upgradedTetrativeReconstructor;

    public static void load(){
        //region factories
        upgradedAirFactory = new UnitFactory("upgraded-air-factory"){{
            requirements(Category.units, with(SnItems.fors, 80, Items.copper, 70));
            size = 3;
           consumePower(2.5f);
        }};

        upgradedGroundFactory = new UnitFactory("upgraded-ground-factory"){{
            requirements(Category.units, with(SnItems.fors, 80, Items.copper, 70, Items.lead, 65));
            size = 3;
           consumePower(2.5f);
        }};
        //endregion factories
        //region reconstructors
        upgradedAdditiveReconstructor = new Reconstructor("upgraded-additive-reconstructor"){{
            requirements(Category.units, with(SnItems.fors, 210, Items.copper, 230, SnItems.naturite, 100, Items.silicon, 90));

            size = 3;
           consumePower(4f);
            consumeItems(with(Items.silicon, 40, SnItems.naturite, 50));

            constructTime = 65f * 10f;
        }};

        upgradedMultiplicativeReconstructor = new Reconstructor("upgraded-multiplicative-reconstructor"){{
            requirements(Category.units, with(SnItems.fors, 670, Items.copper, 530, Items.silicon, 390, SnItems.nobium, 200, Items.metaglass, 100));

            size = 5;
           consumePower(6f);
            consumeItems(with(Items.silicon, 130, SnItems.nobium, 90, Items.metaglass, 40));

            constructTime = 65f * 30f;
        }};


        upgradedExponentialReconstructor = new Reconstructor("upgraded-exponential-reconstructor"){{
            requirements(Category.units, with(SnItems.fors, 2100, Items.lead, 2000, SnItems.nobium, 1980, Items.silicon, 1420, SnItems.naturite, 700));

            size = 7;
           consumePower(13f);
            consumeItems(with(Items.silicon, 510, SnItems.nobium, 510, Items.plastanium, 550));

            consumeLiquid(SnLiquids.burheyna, 1.5f);

            constructTime = 65f * 60f * 1.5f;
            liquidCapacity = 100f;
        }};

        upgradedTetrativeReconstructor = new Reconstructor("upgraded-tetrative-reconstructor"){{
            requirements(Category.units, with(SnItems.nobium, 3900, Items.lead, 3500, Items.phaseFabric, 2880, Items.silicon, 2470, SnItems.enojie, 2300, SnItems.planatrium, 2100));

            size = 9;
           consumePower(26f);
            consumeItems(with(Items.silicon, 800, SnItems.enojie, 730, SnItems.planatrium, 670, Items.phaseFabric, 410));

            consumeLiquid(SnLiquids.burheyna, 3.5f);

            constructTime = 65f * 65f * 4;
            liquidCapacity = 220f;
        }};
        //endregion reconstructors
        setupConstruction();
    }

    static void setupConstruction(){
        Reconstructor[]
        recs = new Reconstructor[]{
        (Reconstructor)SnUnitBlocks.upgradedAdditiveReconstructor,
        (Reconstructor)SnUnitBlocks.upgradedMultiplicativeReconstructor,
        (Reconstructor)SnUnitBlocks.upgradedExponentialReconstructor,
        (Reconstructor)SnUnitBlocks.upgradedTetrativeReconstructor,
        (Reconstructor)Blocks.tetrativeReconstructor //TODO: T6 reconstructor
        };
        addUnitGroup(SnUnitBlocks.upgradedAirFactory, recs, 20 * 60, with(Items.silicon, 30, SnItems.naturite, 20),
        SnUnitTypes.comet, SnUnitTypes.satellite, SnUnitTypes.planet, SnUnitTypes.star, SnUnitTypes.galaxy, SnUnitTypes.universe);
        addUnitGroup(SnUnitBlocks.upgradedAirFactory, recs, 15 * 60, with(SnItems.fors, 15, Items.silicon, 20),
        SnUnitTypes.wind, SnUnitTypes.thunder, SnUnitTypes.nadir, SnUnitTypes.halo, SnUnitTypes.mudflow, SnUnitTypes.parhelion);
    }

    public static void addUnitGroup(Block t1factory, Reconstructor[] recs, float t1time, ItemStack[] t1cost, UnitType... types){
        ((UnitFactory)t1factory).plans.add(new UnitFactory.UnitPlan(types[0], t1time, t1cost));
        TechTree.TechNode last = new TechTree.TechNode(t1factory.techNode, types[0], types[0].researchRequirements());
        for(int i = 1; i < types.length; i++){
            recs[i - 1].upgrades.add(new UnitType[]{types[i - 1], types[i]});
            last = new TechTree.TechNode(last, types[i], types[i].researchRequirements());
        }
    }
}
