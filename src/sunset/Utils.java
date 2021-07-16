package sunset;

import arc.func.Boolf;
import arc.func.Cons;
import arc.util.Log;
import mindustry.Vars;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentType;
import mindustry.entities.Fires;
import mindustry.entities.Units;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Fire;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;

/** Набор различных static утилит. */
public class Utils {
    /** Определяет, горит ли юнит. */
    public static boolean isUnitBurning(Unit u) {
        // Считаем, что эффект - горение, если он наносит урон и
        // среди его противоположностей есть вода. Такой подход позволит
        // тушить "пожары" из других модов.
        return Vars.content.getBy(ContentType.status).find(content -> {
            StatusEffect s = (StatusEffect)content;
            return u.hasEffect(s) && s.damage > 0 && s.opposites.contains(StatusEffects.wet);
        }) != null;
    }
    /** Возвращает один из огней на блоке или null, если блок не горит. */
    public static Fire getBuildingFire(Building b) {
        Log.info(b.tileX() + " " + b.tileY());
        for(int dx = 0; dx < b.block.size; dx++)
            for(int dy = 0; dy < b.block.size; dy++)
                if(Fires.has(b.tileX() + dx, b.tileY() + dy))
                    return Fires.get(b.tileX() + dx, b.tileY() + dy);
        return null;
    }
    /** Обрабатывает всех юнитов всех команд в некотором радиусе. */
    public static void unitsNearby(float x, float y, float radius, Cons<Unit> cons){
        for (Team team : Team.all) Units.nearby(team, x, y, radius, cons);
    }
    /** Возвращает ближайшего юнита вне зависимости от команды. */
    public static Unit unitClosest(float x, float y, Boolf<Unit> predicate) {
        Unit result = null;
        float cdist = 0f;

        for(Unit e : Groups.unit){
            if(!predicate.get(e)) continue;

            float dist = e.dst2(x, y);
            if(result == null || dist < cdist){
                result = e;
                cdist = dist;
            }
        }

        return result;
    }
}
