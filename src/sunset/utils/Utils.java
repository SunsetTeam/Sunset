package sunset.utils;

import arc.func.Boolf;
import arc.func.Cons;
import arc.func.Floatc2;
import arc.func.Func;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import arc.struct.IntSet;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Strings;
import arc.util.Structs;
import mindustry.Vars;
import mindustry.ai.Astar;
import mindustry.content.Blocks;
import mindustry.content.StatusEffects;
import mindustry.core.World;
import mindustry.ctype.ContentType;
import mindustry.entities.Fires;
import mindustry.entities.Units;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.MenuRenderer;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import mindustry.ui.fragments.MenuFragment;
import mindustry.world.Tile;
import mindustry.world.Tiles;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

/** Набор различных static утилит. */
public class Utils {
    private static final Rand random = new Rand();
    private static final Vec2 rv = new Vec2();
    private static IntSet collidedBlocks = new IntSet();

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
    /** Принудительно устанавливает юнита в меню. */
    public static void setMenuUnit(UnitType type) {
        try {
            Field rendererF = MenuFragment.class.getDeclaredField("renderer");
            rendererF.setAccessible(true);
            MenuRenderer renderer = (MenuRenderer)rendererF.get(Vars.ui.menufrag);
            Field flyerTypeF = MenuRenderer.class.getDeclaredField("flyerType");
            flyerTypeF.setAccessible(true);
            flyerTypeF.set(renderer, type);
            Method generateM = MenuRenderer.class.getDeclaredMethod("generate");
            generateM.setAccessible(true);
            generateM.invoke(renderer);
        } catch (Throwable e) {
            Log.err(e);
        }
    }
    /** Создаёт случайные векторы. */
    public static void randVectors(long seed, int amount, float lengthFrom, float lengthTo, Floatc2 cons) {
        random.setSeed(seed);

        for(int i = 0; i < amount; ++i) {
            float vang = random.nextFloat() * 360.0F;
            rv.set(random.random(lengthFrom, lengthTo), 0.0F).rotate(vang);
            cons.get(rv.x, rv.y);
        }
    }
    /** Создаёт TileHueristic. Нужен для обхода бага AbstractMethodError на Android, когда runtime игнорирует реализацию методов по умолчанию в интерфейсах. */
    public static Astar.TileHueristic tileHueristic(Func<Tile, Float> costFunc) {
        return new Astar.TileHueristic() {
            @Override
            public float cost(Tile tile) {
                return costFunc.get(tile);
            }
            @Override
            public float cost(Tile from, Tile tile) { //само проблемное место. Runtime на Android почему-то не видит модификатор default и тело метода.
                return cost(tile);
            }
        };
    }

    /** for EMP */
    public static void trueEachBlock(float wx, float wy, float range, Cons<Building> cons){
        collidedBlocks.clear();
        int tx = World.toTile(wx);
        int ty = World.toTile(wy);

        int tileRange = Mathf.floorPositive(range / tilesize);

        for(int x = -tileRange + tx; x <= tileRange + tx; x++){
            for(int y = -tileRange + ty; y <= tileRange + ty; y++){
                if(Mathf.within(x * tilesize, y * tilesize, wx, wy, range)){
                    Building other = world.build(x, y);
                    if(other != null && !collidedBlocks.contains(other.pos())){
                        cons.get(other);
                        collidedBlocks.add(other.pos());
                    }
                }
            }
        }
    }

    /** for EMP */
    public static Seq<Teamc> allNearbyEnemies(Team team, float x, float y, float radius){
        Seq<Teamc> targets = new Seq<>();

        Units.nearbyEnemies(team, x - radius, y - radius, radius * 2f, radius * 2f, unit -> {
            if(Mathf.within(x, y, unit.x, unit.y, radius) && !unit.dead){
                targets.add(unit);
            }
        });

        trueEachBlock(x, y, radius, build -> {
            if(build.team != team && !build.dead && build.block != null){
                targets.add(build);
            }
        });

        return targets;
    }
}
