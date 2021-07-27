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
        this.time = duration;
        effect = baseEffect.stacks.get(stackCount-1);
    }
    public void stack() {
        time = duration;
        stackCount++;
        if(stackCount > ((StackableStatusEffect)baseEffect).maxStacks) {
            stackCount = ((StackableStatusEffect)baseEffect).maxStacks;
        }
        effect = baseEffect.stacks.get(stackCount-1);
    }
}