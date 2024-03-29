package sunset.type.unitTypes;

import arc.struct.Seq;
import mindustry.content.StatusEffects;
import mindustry.gen.Unit;
import sunset.type.BerserkStage;
import mindustry.gen.*;

public class BerserkUnitType extends SnUnitType {
    public final Seq<BerserkStage> stages = new Seq<>();

    /** Returns the stage for the given unit, or null if the unit is not in any stage. */
    public static BerserkStage getStage(Unit unit) {
        if(!(unit.type instanceof BerserkUnitType)) return null;
        return ((BerserkUnitType)unit.type).stages.find(stage -> stage.healthMaximum < unit.healthf());
    }

    public BerserkUnitType(String name) {
        super(name);
        constructor = LegsUnit::create;
    }

    @Override
    public void init() {
        super.init();
        stages.sort(stage -> stage.healthMaximum);
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);
        BerserkStage curr = stages.find(stage -> unit.healthf() < stage.healthMaximum);
        if(curr != null) {
            curr.update(unit);
            if(curr.effect != StatusEffects.none && !unit.hasEffect(curr.effect)) {
                stages.each(stage -> unit.unapply(stage.effect));
                unit.apply(curr.effect);
            }
        } else {
            stages.each(stage -> unit.unapply(stage.effect));
        }
    }

    @Override
    public void draw(Unit unit) {
        super.draw(unit);
        BerserkStage curr = stages.find(stage -> stage.healthMaximum < unit.healthf());
        if(curr != null) curr.draw(unit);
    }
}
