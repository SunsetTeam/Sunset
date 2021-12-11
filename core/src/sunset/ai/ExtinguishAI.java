package sunset.ai;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Interval;
import mindustry.Vars;
import mindustry.gen.*;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;
import static sunset.utils.Utils.*;

/** AI, которое преследует горящие союзные пострйки или боевые единицы, если таковые есть. */
public class ExtinguishAI extends FlyingUnitWeaponAI {
    Interval timer=new Interval(10);
    public float ticks=15;
    boolean found = false;
    final Vec2 target = new Vec2() ;
    float minCost = Float.MAX_VALUE;
    @Override
    public void updateMovement() {
        // обновляемся раз в 60 тиков
        if (timer.get(0,ticks)) {
            // ниже здоровье - выше приоритет
            // ближе к юниту - выше приоритет
            // сначала ищем горящих союзных юнитов
            minCost = Float.MAX_VALUE;
            found = false;
            unit.team.data().units.each(un -> {
                if (un == unit || !isUnitBurning(un)) return;
                float cost = Mathf.sqr(un.healthf()) * Mathf.len(unit.x - un.x, un.y - unit.y);
                if (cost > minCost) return;
                minCost = cost;
                target.set(un);
                found = true;
            });
            // если не нашли юнитов, то ищем постройки
            if(!found) {
                minCost = Float.MAX_VALUE;
                float range = Math.max(world.width(), world.height()) * tilesize;
                Vars.indexer.eachBlock(unit.team, unit.x, unit.y, range, bld -> true, building -> {
                    Fire fire = getBuildingFire(building);
                    if (fire == null) return;
                    float cost = Mathf.sqr(building.healthf()) * Mathf.dst(unit.x, unit.y, building.x, building.y);
                    if (cost > minCost) return;
                    minCost = cost;
                    target.set(building);
                    found = true;
                });
            }
        }
        if (found) {
            moveTo(target[0], unit.range()*0.9f);
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