package sunset.world.blocks.gas;

import arc.Core;
import gas.GasStack;
import gas.type.Gas;
import gas.world.blocks.production.GenericCrafterWithGas;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.world.meta.Stat;

public class Boiler extends GenericCrafterWithGas {

    public float liquidAmount = 30f;
    public float conversionMultiplier = 3f;

    public Boiler(String name) {
        super(name);
        hasGasses = true;
        outputsGas = true;
        hasItems = true;
        hasLiquids = true;
    }

    @Override
    public void init() {
        super.init();
        gasCapacity = liquidAmount * conversionMultiplier;
    }


    @Override
    public void setStats() {
        super.setStats();
        stats.remove(Stat.productionTime);
    }

    @Override
    public void setBars() {
        super.setBars();
        bars.add("heat", (LiquidBoilerBuild entity) ->
                new Bar(() ->
                        Core.bundle.format("bar.heat", (int) (entity.warmup)),
                        () -> Pal.lightOrange,
                        entity::warmupProgress
                ));
    }

    public class LiquidBoilerBuild extends GenericCrafterWithGasBuild {

        public float warmupProgress() {
            return warmup;
        }

        @Override
        public float getProgressIncrease(float base) {
            return super.getProgressIncrease(base) * warmupProgress();
        }
    }
}
