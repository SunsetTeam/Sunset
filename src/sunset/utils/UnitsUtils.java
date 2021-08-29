package sunset.utils;

import mindustry.content.Blocks;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitFactory;

/** Утилиты для удобной настройки улучшения и исследования юнитов. */
public class UnitsUtils {
    private static Reconstructor[] recs;

    public static void init() {
        recs = new Reconstructor[]{
            (Reconstructor) Blocks.additiveReconstructor,
            (Reconstructor) Blocks.multiplicativeReconstructor,
            (Reconstructor) Blocks.exponentialReconstructor,
            (Reconstructor) Blocks.tetrativeReconstructor
        };
    }

    public static void addUnitGroup(Block t1factory, float t1time, ItemStack[] t1cost, UnitType... types) {
        ((UnitFactory)t1factory).plans.add(new UnitFactory.UnitPlan(types[0], t1time, t1cost));
        for (int i = 1; i < types.length; i++) {
            recs[i - 1].upgrades.add(new UnitType[]{types[i - 1], types[i]});
        }
    }
}
