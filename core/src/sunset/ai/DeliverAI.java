package sunset.ai;

import mindustry.Vars;
import mindustry.ai.formations.Formation;
import mindustry.ai.types.FlyingAI;
import mindustry.ai.types.FormationAI;
import mindustry.entities.units.AIController;
import mindustry.entities.units.UnitCommand;
import mindustry.gen.Call;
import mindustry.gen.Unit;
import mindustry.type.ItemStack;
import sunset.gen.DeliverUnit;
import sunset.gen.SnCall;
import sunset.type.DeliverState;
import sunset.world.blocks.units.Airport;

import static sunset.type.DeliverState.*;

public class DeliverAI extends FormationAI {
    DeliverAIWrapped wrapped=new DeliverAIWrapped();
    public DeliverAI() {
        super(null,null);
    }

    @Override
    public void init() {
        wrapped.init();
    }

    @Override
    public void updateUnit() {
        wrapped.unit(unit);
        wrapped.updateUnit();
    }

    @Override
    public void removed(Unit unit) {
        wrapped.removed(unit);
    }

    @Override
    public boolean isBeingControlled(Unit player) {
        return wrapped.isBeingControlled(player);
    }
}
class DeliverAIWrapped extends FlyingAI {
    @Override
    public AIController fallback() {
        return new HealAI();
    }

    // 0 - move to "base"
    // 1 - pick resources from "base"
    // 2 - move to "base.linked"
    // 1 - pick resources from "base.linked"

    @Override
    public void updateMovement() {
        if (!(unit instanceof DeliverUnit deliver)) {
            unit.killed();
            return;
        }
        Airport.AirportBuild base = deliver.base;
        if (base == null || !base.isValid()) {
//            unit.killed();
            return;
        }
        Airport.AirportBuild linked = base.link != -1 && Vars.world.build(base.link) instanceof Airport.AirportBuild build && build.isValid() ? build : null;
        DeliverState state = deliver.deliverState;
        switch (state) {

            case base -> {
                if (!unit.within(base, unit.hitSize * 2)) {
                    moveTo(base, unit.hitSize);
                    break;
                }
                if (unit.stack.amount <= 0 || (unit.stack.item == base.takeItem)) {
                    if (base.takeItem != null) {
//                    Call.transferItemEffect();
                        int accepted = unit.maxAccepted(base.takeItem);
                        int amount = Math.min(accepted,base.items.get(base.takeItem));
                        if (amount > 0) {
                            Call.setItem(base,base.takeItem,base.items().get(base.takeItem)-amount);
                            SnCall.transferItemToUnit(base.takeItem, amount, base.x, base.y, unit);
                        } else if (accepted==0) {
                            state(DeliverState.target);
                        }
                    }
                    break;
                }
                state(DeliverState.target);
            }
            case target -> {
                if (linked != null) {
                    if (!unit.within(linked, unit.hitSize * 2f)) {
                        moveTo(linked, unit.hitSize);
                        break;
                    }
                    if (unit.stack.amount > 0) {
                        ItemStack stack = unit.stack;
                        int accepted = linked.acceptStack(stack.item, stack.amount, unit);
                        if (accepted > 0) {
                            Call.transferItemTo(unit, stack.item, stack.amount, unit.x, unit.y, linked);
                        }
                        break;
                    }
                    state(DeliverState.base);
                }
            }
        }
    }

    private void state(DeliverState state) {
        if (unit instanceof DeliverUnit deliver) {
            deliver.deliverState = state;
        }
    }
}
