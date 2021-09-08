package sunset.entities.units;

import mindustry.entities.units.StatusEntry;
import sunset.type.StackableStatusEffect;

/** Запись о накладываемом статус-эффекте. */
public class StackableStatusEntry extends StatusEntry {
    /** Текущее количество наложений. */
    public int stackCount;
    /** Базовая длительность. */
    public float duration;
    /** Базовый статус-эффект, из которого необходимо брать наложения. */
    public StackableStatusEffect baseEffect;

    public StackableStatusEntry(StackableStatusEffect baseEffect, int stackCount, float duration) {
        super();
        this.baseEffect = baseEffect;
        this.stackCount = stackCount;
        this.duration = duration;
        set(baseEffect.stacks.get(stackCount-1), duration);
    }
    public void stack() {
        stackCount++;
        if(stackCount > baseEffect.maxStacks) stackCount = baseEffect.maxStacks;
        set(baseEffect.stacks.get(stackCount-1), duration);
    }
}