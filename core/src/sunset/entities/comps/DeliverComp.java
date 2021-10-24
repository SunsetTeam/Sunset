package sunset.entities.comps;

import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Strings;
import mindustry.ai.formations.Formation;
import mindustry.annotations.Annotations;
import mindustry.entities.units.UnitController;
import mindustry.gen.*;
import sunset.ai.DeliverAI;
import sunset.gen.DeliverUnit;
import sunset.type.DeliverState;
import sunset.world.blocks.units.Airport;

@Annotations.Component
abstract class DeliverComp implements Entityc, Unitc {
    Airport.AirportBuild base;
    DeliverState deliverState = DeliverState.base;

    @Annotations.Import
    UnitController controller;

    public void set(Airport.AirportBuild base) {
        this.base = base;
        deliverState = DeliverState.base;
    }

    @Override
    @Annotations.Replace
    public boolean isAI() {
        return false;
    }

    @Override
    @Annotations.MethodPriority(-1000)
    public void update() {
        if (!(controller instanceof DeliverAI)) {
            resetController();
        }
        if (base != null && !(base.units.contains(this.<DeliverUnit>self()) && base.isValid() && base.added)) {
            base = null;
        }
        if (base==null){
            /*for (Building building : Groups.build) {
                if (building instanceof Airport.AirportBuild build && build.needUnits()){
                    build.units.add(self());
                    break;
                }
            }*/
        }
    }

    @Override
    @Annotations.Replace
    public void controller(UnitController controller) {
        if (controller instanceof DeliverAI) {
            this.controller = controller;
            if (controller.unit() != self()) controller.unit(self());
        }
    }

    @Override
    @Annotations.MethodPriority(10)
    public void display(Table table) {
        table.label(() -> "state: " + deliverState.name()).row();

        table.label(() -> Strings.format("base: @(@;@)", base, base == null ? -1 : base.tileX(), base == null ? -1 : base.tileY())).row();
    }

    @Override
    public void afterRead() {
        if (deliverState == null) {
            deliverState = DeliverState.base;
        }
    }

    @Override
    @Annotations.Replace
    public void command(Formation formation, Seq<Unit> units) {
    }
}
