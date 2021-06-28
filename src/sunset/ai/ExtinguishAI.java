package sunset.ai;

import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.util.Log;
import mindustry.Vars;
import mindustry.ai.types.FlyingAI;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentType;
import mindustry.entities.Fires;
import mindustry.entities.Units;
import mindustry.gen.*;
import mindustry.type.StatusEffect;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

/** AI, которое преследует горящие союзные пострйки или боевые единицы, если таковые есть. */
public class ExtinguishAI extends FlyingUnitWeaponAI {
    protected static boolean isUnitBurning(Unit u) {
        // Считаем, что эффект - горение, если он наносит урон и
        // среди его противоположностей есть вода. Такой подход позволит
        // тушить "пожары" из других модов.
        return Vars.content.getBy(ContentType.status).find(content -> {
            StatusEffect s = (StatusEffect)content;
            return u.hasEffect(s) && s.damage > 0 && s.opposites.contains(StatusEffects.wet);
        }) != null;
    }

    protected static Fire getBuildingFire(Building b) {
        Log.info(b.tileX() + " " + b.tileY());
        for(int dx = 0; dx < b.block.size; dx++)
            for(int dy = 0; dy < b.block.size; dy++)
                if(Fires.has(b.tileX() + dx, b.tileY() + dy))
                    return Fires.get(b.tileX() + dx, b.tileY() + dy);
        return null;
    }

    @Override
    public void updateMovement() {
        Unit u = Units.closest(unit.team, unit.x, unit.y,
                Float.MAX_VALUE, un -> un != unit && isUnitBurning(un),
                (unit1, x, y) -> Mathf.pow(unit1.health / unit1.maxHealth, 2f));
        if(u != null) {
            moveTo(u, unit.range());
            return;
        }
        Fire[] b = new Fire[] { null };
        float[] cost = new float[] { Float.MAX_VALUE };
        float range = Math.max(world.width(), world.height()) * tilesize;
        Vars.indexer.eachBlock(unit.team, unit.x, unit.y, range, bld -> true, building -> {
            Fire fire = getBuildingFire(building);
            if(fire == null) return;
            float cs = Mathf.pow(building.health / building.maxHealth, 2f)
                    * Mathf.dst(unit.x, unit.y, building.x, building.y);
            if(b[0] == null || cs < cost[0]) {
                cost[0] = cs;
                b[0] = fire;
            }
        });
        if(b[0] != null) {
            moveTo(b[0], unit.range());
            return;
        }
        super.updateMovement();
    }

    protected void moveTo(Posc target, float length){
        if(target == null) return;

        vec.set(target).sub(unit);

        float scl = 1f;

        if(vec.len() < length) {
            scl = 0;
        }

        vec.setLength(unit.realSpeed() * scl);

        unit.moveAt(vec);
    }
}