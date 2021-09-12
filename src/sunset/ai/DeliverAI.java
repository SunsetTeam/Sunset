package sunset.ai;

import arc.math.geom.Vec2;
import mindustry.ai.types.FlyingAI;
import sunset.world.blocks.units.Airport;

public class DeliverAI extends FlyingAI {
    public boolean inited = false;
    public Airport.AirportBuild base;
    public int state = 0;
    // 0 - move to "base"
    // 1 - pick resources from "base"
    // 2 - move to "base.linked"
    // 1 - pick resources from "base.linked"
    public void setup(Airport.AirportBuild base) {
        this.base = base;
        inited = true;
    }
    @Override
    public void updateMovement() {
        if(!inited) return;
        if(base == null || !base.added) {
            unit.killed();
        } else {
            if(state == 0) {
                float len = new Vec2(unit.x, unit.y).sub(base.x, base.y).len();
                if(len > unit.hitSize * 2) {
                    moveTo(new Vec2(base.x, base.y), unit.hitSize);
                } else {
                    state = 1;
                }
            } else if(state == 1) {
                if(unit.stack.amount > 0 && (unit.stack.item != base.takeItem)) {
                    state = 2;
                } else {
                    if(base.takeItem != null) {
                        int need = unit.itemCapacity() - unit.stack.amount;
                        if (need > 0) {
                            int toMove = Math.min(need, base.items.get(base.takeItem));
                            base.items.remove(base.takeItem, toMove);
                            unit.addItem(base.takeItem, toMove);
                        } else {
                            state = 2;
                        }
                    }
                }
            } else if(state == 2) {
                if(base.linked != null) {
                    float len = new Vec2(unit.x, unit.y).sub(base.linked.x, base.linked.y).len();
                    if(len > unit.hitSize * 2) {
                        moveTo(new Vec2(base.linked.x, base.linked.y), unit.hitSize);
                    } else {
                        state = 3;
                    }
                }
            } else if(state == 3) {
                if(base.linked != null) {
                    if(unit.stack.amount > 0) {
                        int remains = base.linked.getMaximumAccepted(unit.stack.item) -
                                base.linked.items.get(unit.stack.item);
                        int toMove = Math.min(unit.stack.amount, remains);
                        base.linked.items.add(unit.stack.item, toMove);
                        unit.stack.amount -= toMove;
                    } else {
                        state = 0;
                    }
                } else {
                    state = 2;
                }
            }
        }
    }
}
