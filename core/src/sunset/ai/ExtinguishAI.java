package sunset.ai;

import arc.math.geom.*;
import arc.util.*;
import mindustry.gen.*;
import sunset.utils.*;

import static mindustry.Vars.*;

/** AI that is chasing burning allied buildings or units, if any. */
public class ExtinguishAI extends FlyingWeaponAI{
    final Vec2 target = new Vec2();
    public float ticks = 15;
    Interval timer = new Interval(10);
    boolean found = false;
    float minCost = Float.MAX_VALUE;

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

    protected void moveTo(Posc target, float length){
        if(target == null) return;
        vec.set(target).sub(unit);
        unit.rotation(vec.angle());
        float scl = vec.len() < length ? 0 : 1f;
        vec.setLength(unit.speed() * scl);
        unit.moveAt(vec);
    }
}