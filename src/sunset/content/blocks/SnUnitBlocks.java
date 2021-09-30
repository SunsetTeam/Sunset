package sunset.content.blocks;

import arc.struct.*;
import mindustry.content.Items;
import mindustry.ctype.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.units.*;
import sunset.content.*;

import static mindustry.type.ItemStack.*;

import javax.net.ssl.SNIMatcher;

public class SnUnitBlocks implements ContentList {
    public static Block

    //air
    bigAirFactory,

    //ground 
    bigGroundFactory,

    //reconstructors
    nobiumAdditiveReconstructor,
    nobiummultiplicativeReconstructor,
    nobiumExponentialReconstructor,
    nobiumTetrativeReconstructor;

    @Override
    public void load(){
    bigAirFactory = new UnitFactory("big-air-factory"){{
        requirements(Category.units, with(SnItems.fors, 80, Items.copper, 70));
        plans = Seq.with(
            new UnitPlan(SnUnitTypes.guardcopter, 70f * 15, with(SnItems.fors, 15, Items.silicon, 20)),
            new UnitPlan(SnUnitTypes.comet, 60f * 40, with(Items.silicon, 40, SnItems.naturite, 20))
        );
        size = 3;
        consumes.power(2.5f);
    }};

    bigGroundFactory = new UnitFactory("big-ground-factory"){{
        requirements(Category.units, with(SnItems.fors, 80, Items.copper, 70, Items.lead, 65));
        plans = Seq.with(
            new UnitPlan(SnUnitTypes.mirage, 75f * 15, with(SnItems.nobium, 20, Items.silicon, 20, SnItems.fors, 15))
        );
        size = 3;
        consumes.power(2.5f);
    }};
//reconstructors

    nobiumAdditiveReconstructor = new Reconstructor("nobium-additive-reconstructor"){{
            requirements(Category.units, with(SnItems.fors, 210, Items.copper, 230, SnItems.nobium, 100, Items.silicon, 90));

            size = 3;
            consumes.power(4f);
            consumes.items(with(Items.silicon, 50, SnItems.nobium, 50));

            constructTime = 65f * 10f;

            upgrades.addAll(
                new UnitType[]{SnUnitTypes.guardcopter, SnUnitTypes.bladecopter},
                new UnitType[]{SnUnitTypes.comet, SnUnitTypes.satelite},
                new UnitType[]{SnUnitTypes.mirage, SnUnitTypes.vision}
            );
        }};

     nobiummultiplicativeReconstructor = new Reconstructor("nobium-multiplicative-reconstructor"){{
            requirements(Category.units, with(SnItems.fors, 670, Items.copper, 530, SnItems.nobium, 400, Items.silicon, 390, Items.metaglass, 100));

            size = 5;
            consumes.power(6f);
            consumes.items(with(Items.silicon, 130, SnItems.fors, 140, SnItems.nobium, 90, Items.metaglass, 40));

            constructTime = 65f * 30f;

            upgrades.addAll(
                new UnitType[]{SnUnitTypes.bladecopter, SnUnitTypes.swordcopter},
                new UnitType[]{SnUnitTypes.satelite, SnUnitTypes.planet}
            );
        }};


         nobiumExponentialReconstructor = new Reconstructor("nobium-exponential-reconstructor"){{
            requirements(Category.units, with(SnItems.fors, 2100, Items.lead, 2000, SnItems.nobium, 1980, Items.silicon, 1420, SnItems.naturite, 700));

            size = 7;
            consumes.power(13f);
            consumes.items(with(Items.silicon, 850, SnItems.fors, 860, SnItems.nobium, 590, Items.plastanium, 650));

            consumes.liquid(SnLiquids.burheyna, 1.5f);

            constructTime = 65f * 60f * 1.5f;
            liquidCapacity = 100f;


            upgrades.addAll(
                new UnitType[]{SnUnitTypes.swordcopter, SnUnitTypes.guardiancopter},
                new UnitType[]{SnUnitTypes.planet, SnUnitTypes.star}
            );
        }};

        nobiumTetrativeReconstructor = new Reconstructor("nobium-tetrative-reconstructor"){{
            requirements(Category.units, with(SnItems.nobium, 4200, Items.lead, 4000, Items.surgeAlloy, 3880, Items.silicon, 3770, SnItems.enojie, 3500, Items.phaseFabric, 3200));

            size = 9;
            consumes.power(26f);
            consumes.items(with(Items.silicon, 1100, SnItems.nobium, 1250, SnItems.enojie, 740, Items.surgeAlloy, 880, Items.phaseFabric, 560));

            consumes.liquid(SnLiquids.burheyna, 3.5f);

            constructTime = 65f * 65f * 4;
            liquidCapacity = 220f;


            upgrades.addAll(
                new UnitType[]{SnUnitTypes.guardcopter, SnUnitTypes.crusadercopter},
                new UnitType[]{SnUnitTypes.star, SnUnitTypes.galaxy}
            );
        }};
    }
}
