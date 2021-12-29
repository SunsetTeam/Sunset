package sunset.ai;

import mindustry.ai.types.*;
import mindustry.entities.units.*;
import mindustry.gen.*;

public class AIWrapper extends FormationAI{
    final AIController wrapped;

    public AIWrapper(AIController wrapped){
        super(null, null);
        this.wrapped = wrapped;
    }

    @Override
    public void init(){
        wrapped.init();
    }

    @Override
    public void updateUnit(){
        wrapped.unit(unit);
        wrapped.updateUnit();
    }

    @Override
    public void removed(Unit unit){
        wrapped.removed(unit);
    }

    @Override
    public boolean isBeingControlled(Unit player){
        return wrapped.isBeingControlled(player);
    }
}