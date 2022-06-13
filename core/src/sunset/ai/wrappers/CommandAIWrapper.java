package sunset.ai.wrappers;

import arc.math.geom.*;
import mindustry.ai.types.*;
import mindustry.entities.units.*;
import mindustry.gen.*;

public class CommandAIWrapper extends CommandAI{
   public final AIController wrapped;

    public CommandAIWrapper(AIController wrapped){
        super();
        this.wrapped = wrapped;
    }

    @Override
    public boolean hasCommand(){
        return true;
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

    @Override
    public void hit(Bullet bullet){

    }

    @Override
    public boolean keepState(){
        return true;
    }

    @Override
    public Teamc findTarget(float x, float y, float range, boolean air, boolean ground){
        return null;
    }

    @Override
    public boolean retarget(){
        return false;
    }

    @Override
    public void setupLastPos(){

    }

    @Override
    public void commandPosition(Vec2 pos){

    }

    @Override
    public void commandTarget(Teamc moveTo){

    }

    @Override
    public void commandTarget(Teamc moveTo, boolean stopAtTarget){

    }
}