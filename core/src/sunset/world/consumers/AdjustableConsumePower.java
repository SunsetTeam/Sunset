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
    public final float baseUsage;
    private final Func<Building, Float> generationFactor;
    public AdjustableConsumePower(float baseUsage, Func<Building, Float> generationFactor)
    {
        this.baseUsage = baseUsage;
        this.generationFactor = generationFactor;
    }

    @Override
    public float requestedPower(Building entity) {
        return baseUsage * generationFactor.get(entity);
    }
}
