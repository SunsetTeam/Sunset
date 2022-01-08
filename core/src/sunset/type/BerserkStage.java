package sunset.type;

import mindustry.content.StatusEffects;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;

public class BerserkStage {
    /** The maximum possible percentage of strength at which this stage will be active.*/
    public float healthMaximum;
    /** Ordinal index. The lower, the less health. Do not install manually! */
    public int index;
    /** The effect of this stage. */
    public StatusEffect effect = StatusEffects.none;
    public float bulletWidthMultiplier = 1f;

    /** Override to handle the logic for this step. */
    public void update(Unit unit) { }
    /** Override to render this stage. */
    public void draw(Unit unit) { }
}
