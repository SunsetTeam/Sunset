package sunset.utils;

import mindustry.content.Blocks;
import mindustry.content.TechTree;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitFactory;
import sunset.content.blocks.SnUnitBlocks;

/** Утилиты для удобной настройки улучшения и исследования юнитов. */
public class UnitsUtils {
    private static Reconstructor[] recs;

    public static void init() {
        recs = new Reconstructor[]{
            (Reconstructor) SnUnitBlocks.upgradedAdditiveReconstructor,
            (Reconstructor) SnUnitBlocks.upgradedMultiplicativeReconstructor,
            (Reconstructor) SnUnitBlocks.upgradedExponentialReconstructor,
            (Reconstructor) SnUnitBlocks.upgradedTetrativeReconstructor,
            (Reconstructor) Blocks.tetrativeReconstructor //TODO: T6 reconstructor
        };
    }

    public static void addUnitGroup(Block t1factory, float t1time, ItemStack[] t1cost, UnitType... types) {
        ((UnitFactory)t1factory).plans.add(new UnitFactory.UnitPlan(types[0], t1time, t1cost));
        TechTree.TechNode last = new TechTree.TechNode(TechTree.get(t1factory), types[0], types[0].researchRequirements());
        for (int i = 1; i < types.length; i++) {
            recs[i - 1].upgrades.add(new UnitType[]{types[i - 1], types[i]});
            last = new TechTree.TechNode(last, types[i], types[i].researchRequirements());
        }
    }
}
