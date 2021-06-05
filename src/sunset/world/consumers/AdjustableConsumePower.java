package sunset.world.consumers;

import arc.func.Func;
import mindustry.gen.Building;
import mindustry.world.consumers.ConsumePower;

/**
 * Представляет настраваемый потребиель энергии.
 * @implNote Каждый тик вызывает метод, переданный в конструкторе и
 * умножает полученное число на базовое потребление энергии, получая
 * тем самым итоговое потребление энергии.
 */
public class AdjustableConsumePower extends ConsumePower {
    public float baseUsage;
    private Func<Building, Float> factorGen;
    public AdjustableConsumePower(float baseUsage, Func<Building, Float> factorGen)
    {
        this.baseUsage = baseUsage;
        this.factorGen = factorGen;
    }

    @Override
    public float requestedPower(Building entity) {
        return baseUsage * factorGen.get(entity);
    }
}
