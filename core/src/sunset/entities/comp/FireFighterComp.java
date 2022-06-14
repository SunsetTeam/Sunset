package sunset.entities.comp;

import mindustry.annotations.Annotations.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import sunset.ai.*;
import sunset.gen.*;
import sunset.type.*;
import sunset.world.blocks.units.*;
import sunset.world.blocks.units.FireFightersStation.*;

@Component
abstract class FireFighterComp implements Entityc, UncontrollableUnitc,Unitc{
    FireFightersStationBuild base;

    public void set(FireFightersStationBuild base){
        this.base = base;
    }

    @Override
    public void update(){
        if(base != null && !(base.units.contains(this.<DeliverUnit>self()) && base.isValid() && base.isAdded())){
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
    public boolean validControllerType(UnitController controller){
        return controller instanceof ExtinguishAI;
    }
}
