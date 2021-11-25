package sunset.utils;

import arc.func.Boolf;
import arc.func.Cons;
import arc.func.Func;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Geometry;
import arc.math.geom.Rect;
import arc.math.geom.Vec2;
import arc.struct.IntSet;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Strings;
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
import mindustry.world.meta.StatValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static arc.Core.bundle;
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
    private static boolean check;

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

    /**
     * Принудительно устанавливает юнита в меню.
     */
    public static void setMenuUnit(UnitType type) {
        if (Vars.headless)return;
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
    public static Seq<Teamc> allNearbyEnemiesOld(Team team, float x, float y, float radius) {
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
    public static void allNearbyEnemies(Team team, float x, float y, float radius, Cons<Healthc> cons){
        Units.nearbyEnemies(team, x - radius, y - radius, radius * 2f, radius * 2f, unit -> {
            if(unit.within(x, y, radius + unit.hitSize / 2f) && !unit.dead){
                cons.get(unit);
            }
        });

        trueEachBlock(x, y, radius, build -> {
            if(build.team != team && !build.dead && build.block != null){
                cons.get(build);
            }
        });
    }
    public static StatValue empWave(float damage, float maxTargets, StatusEffect status){
        return table -> {
            table.row();
            table.table(t -> {
                t.left().defaults().padRight(3).left();

                t.add(bundle.format("bullet.lightning", maxTargets, damage));
                t.row();

                if(status != StatusEffects.none){
                    t.add((status.minfo.mod == null ? status.emoji() : "") + "[stat]" + status.localizedName);
                }
            }).padTop(-9).left().get().background(Tex.underline);
        };
    }
    public static boolean checkForTargets(Team team, float x, float y, float radius){
        check = false;

        Units.nearbyEnemies(team, x - radius, y - radius, radius * 2f, radius * 2f, unit -> {
            if(unit.within(x, y, radius + unit.hitSize / 2f) && !unit.dead){
                check = true;
            }
        });

        trueEachBlock(x, y, radius, build -> {
            if(build.team != team && !build.dead && build.block != null){
                check = true;
            }
        });

        return check;
    }
    /** For reload bar. */
    public static String stringsFixed(float value){
        return Strings.autoFixed(value, 2);
    }

    /** Extracts a number out of a string by removing every non-numerical character  */
    public static String extractNumber(String s){
        //God, I love google. I have no idea what the "[^\\d.]" part even is. meep moment :D
        return s.replaceAll("[^\\d.]", "");
    }
}
