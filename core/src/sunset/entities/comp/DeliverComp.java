package sunset.entities.comp;

import arc.scene.ui.layout.*;
import mindustry.annotations.*;
import mindustry.annotations.Annotations.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import sunset.ai.*;
import sunset.gen.*;
import sunset.type.*;
import sunset.world.blocks.units.*;

@Annotations.Component
abstract class DeliverComp implements Entityc, UncontrollableUnitc, Unitc{
    Airport.AirportBuild base;
    DeliverState deliverState = DeliverState.base;


    public void set(Airport.AirportBuild base){
        this.base = base;
        deliverState = DeliverState.base;
    }

    @Override
    @Replace
    public boolean validControllerType(UnitController controller){
        return controller instanceof DeliverAI;
    }

    @Override
    @Annotations.MethodPriority(-999)
    public void update(){
        if(base != null && !(base.units.contains(this.<DeliverUnit>self()) && base.isValid() && base.added)){
            base = null;
        }
        if(base == null){
            /*for (Building building : Groups.build) {
                if (building instanceof Airport.AirportBuild build && build.needUnits()){
                    build.units.add(self());
                    break;
                }
            }*/
        }
    }


    @Override
    @Annotations.MethodPriority(10)
    public void display(Table table){
      /*  table.label(() -> "state: " + deliverState.name()).row();

        table.label(() -> Strings.format("base: @(@;@)", base, base == null ? -1 : base.tileX(), base == null ? -1 : base.tileY())).row();*/
    }

    @Override
    public void afterRead(){
        if(deliverState == null){
            deliverState = DeliverState.base;
        }
    }

}
