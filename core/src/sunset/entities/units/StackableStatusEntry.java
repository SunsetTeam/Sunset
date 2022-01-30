package sunset.entities.units;

import mindustry.entities.units.StatusEntry;
import sunset.type.StackableStatusEffect;

/** A record of the superimposed status effect. */
public class StackableStatusEntry extends StatusEntry {
    /** A record of the superimposed status effect. */
    public int stackCount;
    /** Base duration. */
    public float duration;
    /** The base status effect from which to take overlays. */
    public StackableStatusEffect baseEffect;

    public StackableStatusEntry(StackableStatusEffect baseEffect, int stackCount, float duration) {
        super();
        this.baseEffect = baseEffect;
        this.stackCount = stackCount;
        this.duration = duration;
        set(baseEffect.stack(stackCount-1), duration);
    }
    public void stack() {
        stackCount++;
        if(stackCount > baseEffect.maxStacks) stackCount = baseEffect.maxStacks;
        set(baseEffect.stack(stackCount-1), duration);
    }
}