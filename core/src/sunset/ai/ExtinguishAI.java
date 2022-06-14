package sunset.ai;

import arc.math.geom.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import sunset.ai.wrappers.*;
import sunset.utils.*;

import static mindustry.Vars.*;

/** AI that is chasing burning allied buildings or units, if any. */
public class ExtinguishAI extends AIController{
    final Vec2 target = new Vec2();
    public float ticks = 15;
    Interval timer = new Interval(10);
    boolean found = false;

    private ExtinguishAI(){

    }

    public static UnitController wrapper(){
        return new CommandAIWrapper(new ExtinguishAI());
    }

    @Override
    public void updateMovement(){
        // обновляемся раз в 60 тиков
        if(timer.get(0, ticks)){
            float range = Math.max(world.width(), world.height()) * tilesize;
            Posc targ = Utils.findFireTarget(unit.x, unit.y, unit.team, range, un -> un != unit, b -> true);
            found = targ != null;
            if(found){
                target.set(targ);
            }
        }
        if(found){
            moveTo(target, unit.range() * 0.9f);
        }
    }
}