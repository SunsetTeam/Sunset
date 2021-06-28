package sunset.ai.weapon;

import arc.math.Mathf;
import arc.math.geom.Position;
import arc.util.Log;
import mindustry.Vars;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentType;
import mindustry.entities.Fires;
import mindustry.entities.Units;
import mindustry.gen.*;
import mindustry.type.StatusEffect;

public class ExtinguishWeaponAI extends BaseWeaponAI {
    @Override
    public void update() {
        Posc t = findTarget();
        if(t == null) {
            super.update();
            return;
        }
        Position target = new Position() {
            @Override
            public float getX() { return t.getX(); }
            @Override
            public float getY() { return t.getY(); }
        };
        aim(target);
    }

    private Posc findTarget() {
        final Posc[] ret = new Posc[] { null };
        // Ищем горящих юнитов
        ret[0] = Units.closest(unit.team, weaponX(), weaponY(),
                range(), un -> un != unit && isUnitBurning(un),
                (unit1, xx, yy) -> Mathf.pow(unit1.health / unit1.maxHealth, 2f));
        if(ret[0] != null) return ret[0];
        // Ищем горящие постройки
        float[] cost = new float[] { Float.MAX_VALUE };
        Vars.indexer.eachBlock(unit.team, unit.x, unit.y, range(), bld -> ret[0] == null, building -> {
            Fire fire = getBuildingFire(building);
            if(fire == null) return;
            float cs = Mathf.pow(building.health / building.maxHealth, 2f)
                    * Mathf.dst(unit.x, unit.y, building.x, building.y);
            if(cs < cost[0]) {
                cost[0] = cs;
                ret[0] = fire;
            }
        });
        if(ret[0] != null) return ret[0];
        return null;
    }

    //TODO - DRY: "Ну да ну да пошёл я ... "

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
}
