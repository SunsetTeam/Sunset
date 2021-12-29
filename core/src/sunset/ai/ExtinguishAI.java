package sunset.ai;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Interval;
import mindustry.Vars;
import mindustry.gen.*;
import sunset.gen.*;
import sunset.utils.Utils;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;
import static sunset.utils.Utils.*;

/** AI, которое преследует горящие союзные пострйки или боевые единицы, если таковые есть. */
public class ExtinguishAI extends FlyingUnitWeaponAI{
    Interval timer=new Interval(10);
    public float ticks=15;
    boolean found = false;
    final Vec2 target = new Vec2() ;
    float minCost = Float.MAX_VALUE;
    @Override
    public void updateMovement() {
        // обновляемся раз в 60 тиков
        if (timer.get(0,ticks)) {
            float range = Math.max(world.width(), world.height()) * tilesize;
            Posc targ= Utils.findFireTarget(unit.x,unit.y,unit.team,range,un->un!=unit,b->true);
            found=targ!=null;
            if (found){
                target.set(targ);
            }
        }
        if (found) {
            moveTo(target, unit.range()*0.9f);
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