package sunset.utils;

import arc.func.Boolf;
import arc.func.Cons;
import arc.func.Floatc2;
import arc.func.Func;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Geometry;
import arc.math.geom.Rect;
import arc.math.geom.Vec2;
import arc.struct.IntSet;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.ai.Astar;
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

/**
 * Набор различных static утилит.
 */
public class Utils {
    private static final Rand random = new Rand();
    private static final Vec2 rv = new Vec2(), tr = new Vec2();
    private static Rect rect = new Rect(), hitrect = new Rect();
    private static IntSet collidedBlocks = new IntSet();

    /**
     * Определяет, горит ли юнит.
     */
    public static boolean isUnitBurning(Unit u) {
        // Считаем, что эффект - горение, если он наносит урон и
        // среди его противоположностей есть вода. Такой подход позволит
        // тушить "пожары" из других модов.
        return Vars.content.getBy(ContentType.status).find(content -> {
            StatusEffect s = (StatusEffect) content;
            return u.hasEffect(s) && s.damage > 0 && s.opposites.contains(StatusEffects.wet);
        }) != null;
    }

    /**
     * Возвращает один из огней на блоке или null, если блок не горит.
     */
    public static Fire getBuildingFire(Building b) {
        int offsetx = -(b.block.size - 1) / 2;
        int offsety = -(b.block.size - 1) / 2;
        for (int dx = 0; dx < b.block.size; dx++)
            for (int dy = 0; dy < b.block.size; dy++) {
                int x = b.tileX() + dx - offsetx;
                int y = b.tileY() + dy - offsety;
                if (Fires.has(x, y))
                    return Fires.get(x, y);
            }
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
            MenuRenderer renderer = (MenuRenderer) rendererF.get(Vars.ui.menufrag);
            Field flyerTypeF = MenuRenderer.class.getDeclaredField("flyerType");
            flyerTypeF.setAccessible(true);
            flyerTypeF.set(renderer, type);
            Method generateM = MenuRenderer.class.getDeclaredMethod("generate");
            generateM.setAccessible(true);
            generateM.invoke(renderer);
            Method cacheM = MenuRenderer.class.getDeclaredMethod("cache");
            cacheM.setAccessible(true);
            cacheM.invoke(renderer);
        } catch (Throwable e) {
            Log.err(e);
        }
    }

    /**
     * Создаёт случайные векторы.
     */
    public static void randVectors(long seed, int amount, float lengthFrom, float lengthTo, Floatc2 cons) {
        random.setSeed(seed);

        for (int i = 0; i < amount; ++i) {
            float vang = random.nextFloat() * 360.0F;
            rv.set(random.random(lengthFrom, lengthTo), 0.0F).rotate(vang);
            cons.get(rv.x, rv.y);
        }
    }

    /**
     * Создаёт TileHueristic. Нужен для обхода бага AbstractMethodError на Android, когда runtime игнорирует реализацию методов по умолчанию в интерфейсах.
     */
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

    /**
     * Ищет коллизию, игнорируя некоторые цели.
     */
    public static Healthc linecast(Bullet hitter, float x, float y, float angle, float length, Boolf<Healthc> predicate) {
        final Building[] tmpBuilding = new Building[]{null};
        final Unit[] tmpUnit = new Unit[]{null};

        tr.trns(angle, length);

        if (hitter.type.collidesGround) {
            world.raycastEachWorld(x, y, x + tr.x, y + tr.y, (cx, cy) -> {
                Building tile = world.build(cx, cy);
                if (tile == null || tile.team == hitter.team || !predicate.get(tile)) return false;
                tmpBuilding[0] = tile;
                return true;
            });
        }

        rect.setPosition(x, y).setSize(tr.x, tr.y);
        float x2 = tr.x + x, y2 = tr.y + y;

        if (rect.width < 0) {
            rect.x += rect.width;
            rect.width *= -1;
        }
        if (rect.height < 0) {
            rect.y += rect.height;
            rect.height *= -1;
        }

        float expand = 3f;
        rect.x -= expand;
        rect.y -= expand;
        rect.width += expand * 2;
        rect.height += expand * 2;

        Units.nearbyEnemies(hitter.team, rect, e -> {
            if ((tmpUnit[0] != null && e.dst2(x, y) > tmpUnit[0].dst2(x, y)) || !e.checkTarget(hitter.type.collidesAir, hitter.type.collidesGround) || !predicate.get(e))
                return;

            e.hitbox(hitrect);
            Rect other = hitrect;
            other.x -= expand;
            other.y -= expand;
            other.width += expand * 2;
            other.height += expand * 2;

            if (Geometry.raycastRect(x, y, x2, y2, other) != null) tmpUnit[0] = e;
        });

        if (tmpBuilding[0] != null && tmpUnit[0] != null) {
            if (Mathf.dst2(x, y, tmpUnit[0].getX(), tmpUnit[0].getY()) <= Mathf.dst2(x, y, tmpBuilding[0].getX(), tmpBuilding[0].getY()))
                return tmpUnit[0];
        } else if (tmpBuilding[0] != null) return tmpBuilding[0];

        return tmpUnit[0];
    }

    /**
     * for EMP
     */
    public static void trueEachBlock(float wx, float wy, float range, Cons<Building> cons) {
        collidedBlocks.clear();
        int tx = World.toTile(wx);
        int ty = World.toTile(wy);

        int tileRange = Mathf.floorPositive(range / tilesize);

        for (int x = -tileRange + tx; x <= tileRange + tx; x++) {
            for (int y = -tileRange + ty; y <= tileRange + ty; y++) {
                if (Mathf.within(x * tilesize, y * tilesize, wx, wy, range)) {
                    Building other = world.build(x, y);
                    if (other != null && !collidedBlocks.contains(other.pos())) {
                        cons.get(other);
                        collidedBlocks.add(other.pos());
                    }
                }
            }
        }
    }

    /**
     * for EMP
     */
    public static Seq<Teamc> allNearbyEnemies(Team team, float x, float y, float radius) {
        Seq<Teamc> targets = new Seq<>();

        Units.nearbyEnemies(team, x - radius, y - radius, radius * 2f, radius * 2f, unit -> {
            if (Mathf.within(x, y, unit.x, unit.y, radius) && !unit.dead) {
                targets.add(unit);
            }
        });

        trueEachBlock(x, y, radius, build -> {
            if (build.team != team && !build.dead && build.block != null) {
                targets.add(build);
            }
        });

        return targets;
    }
}
