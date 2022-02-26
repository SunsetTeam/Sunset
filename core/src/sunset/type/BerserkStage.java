package sunset.type;

import arc.func.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.type.*;

public class BerserkStage{
    /** The maximum possible percentage of strength at which this stage will be active. */
    public float healthMaximum;
    /** Ordinal index. The lower, the less health. Do not install manually! */
    public int index;
    /** The effect of this stage. */
    public StatusEffect effect = StatusEffects.none;
    public float bulletWidthMultiplier = 1f;
    protected Cons<Unit> update=u->{};
    protected Cons<Unit> draw=u->{};

    public BerserkStage(){
    }

    /** Override to handle the logic for this step. */
    public final void update(Unit unit){
        update.get(unit);
    }

    /** Override to render this stage. */
    public final void draw(Unit unit){
        draw.get(unit);
    }
}
