package sunset.entities.comp;

import arc.struct.*;
import mindustry.ai.formations.*;
import mindustry.annotations.*;
import mindustry.annotations.Annotations.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;
import sunset.ai.wrappers.*;
import sunset.type.unitTypes.*;

@Annotations.Component
abstract class UncontrollableUnitComp implements Unitc{
    @Annotations.Import
    UnitController controller;
    @Import UnitType type;

    public UncontrollableUnitType mytype(){
        return (UncontrollableUnitType)type;
    }

    @Override
    @Annotations.Replace
    public boolean isAI(){
        return false;
    }

    abstract boolean validControllerType(UnitController controller);

    @Final
    public boolean validController(UnitController controller){
        return controller instanceof FormationAIWrapper wrapper && validControllerType(wrapper.wrapped);
    }

    public boolean validController(){
        return validController(controller());
    }

    @Override
    @Annotations.Replace
    public void command(Formation formation, Seq<Unit> units){
    }

    @Override
    @Annotations.MethodPriority(-1000)
    public void update(){
        if(!validController()){
            resetController();
        }
    }

    @Override
    @Annotations.Replace
    public void controller(UnitController controller){
        if(validControllerType(controller)){
            this.controller = controller;
            if(controller.unit() != self()) controller.unit(self());
        }
    }
}
