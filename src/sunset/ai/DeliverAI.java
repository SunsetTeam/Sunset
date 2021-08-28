package sunset.ai;

import arc.math.geom.Vec2;
import arc.util.Log;
import mindustry.ai.types.FlyingAI;
import mindustry.content.Items;
import sunset.world.blocks.Airport;

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
        if(base == null) {
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
                int need = unit.itemCapacity() - unit.stack.amount;
                if(need > 0) {
                    int toMove = Math.min(need, base.items.get(Items.copper));
                    base.items.remove(Items.copper, toMove);
                    unit.addItem(Items.copper, toMove);
                } else {
                    state = 2;
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
                    int remains = base.linked.getMaximumAccepted(Items.copper) -
                                  base.linked.items.get(Items.copper);
                    if(unit.stack.amount > 0) {
                        int toMove = Math.min(unit.stack.amount, remains);
                        base.linked.items.add(Items.copper, toMove);
                        unit.stack.amount -= toMove;
                    } else {
                        state = 0;
                    }
                }
            }
        }
    }
}
