package sunset.utils;

import arc.func.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import kotlin.jvm.internal.Ref.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.core.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.fragments.*;

import java.lang.reflect.*;

import static mindustry.Vars.*;

/**
 * Набор различных static утилит.
 */
public class Utils{
    private static final Rand random = new Rand();
    private static final Vec2 rv = new Vec2(), tr = new Vec2();
    private static Rect rect = new Rect(), hitrect = new Rect();
    private static IntSet collidedBlocks = new IntSet();
    private static boolean check;

    public static int getByIndex(IntSet set, int index){
        ObjectRef<Integer> value = new ObjectRef<>();
        IntRef counter = new IntRef();
        set.each(v -> {
            if(counter.element == index){
                value.element = v;
            }
            counter.element++;
        });
        return value.element.intValue();
    }

    public static Posc findFireTarget(float x, float y, Team team, float range, Boolf<Unit> unitFilter, Boolf<Building> buildingFilter){
        Posc target[] = {null};
        float minCost[] = {0};
        target[0] = team.data().units.min(un -> unitFilter.get(un) && isUnitBurning(un), un -> {
            return un.healthf() * un.healthf() * un.dst2(x, y);
        });
        // если не нашли юнитов, то ищем постройки
        if(target[0] == null){
            minCost[0] = Float.MAX_VALUE;
            Vars.indexer.eachBlock(team, x, y, range, buildingFilter, building -> {
                Fire fire = getBuildingFire(building);
                if(fire == null) return;
                float cost = Mathf.sqr(building.healthf()) * Mathf.dst(x, y, building.x, building.y);
                if(cost < minCost[0]){
                    minCost[0] = cost;
                    target[0] = fire;
                }
            });
        }
        return target[0];
    }

    /**
     * Определяет, горит ли юнит.
     */
    public static boolean isUnitBurning(Unit u){
        // Считаем, что эффект - горение, если он наносит урон и
        // среди его противоположностей есть вода. Такой подход позволит
        // тушить "пожары" из других модов.
        return Vars.content.getBy(ContentType.status).find(content -> {
            StatusEffect s = (StatusEffect)content;
            return u.hasEffect(s) && s.damage > 0 && s.opposites.contains(StatusEffects.wet);
        }) != null;
    }

    /**
     * Возвращает один из огней на блоке или null, если блок не горит.
     */
    public static Fire getBuildingFire(Building b){
        int offsetx = -(b.block.size - 1) / 2;
        int offsety = -(b.block.size - 1) / 2;
        for(int dx = 0; dx < b.block.size; dx++)
            for(int dy = 0; dy < b.block.size; dy++){
                int x = b.tileX() + dx - offsetx;
                int y = b.tileY() + dy - offsety;
                if(Fires.has(x, y))
                    return Fires.get(x, y);
            }
        return null;
    }

    /**
     * Принудительно устанавливает юнита в меню.
     */
    public static void setMenuUnit(UnitType type){
        if(Vars.headless) return;
        try{
            Field rendererF = MenuFragment.class.getDeclaredField("renderer");
            rendererF.setAccessible(true);
            MenuRenderer renderer = (MenuRenderer)rendererF.get(Vars.ui.menufrag);
            Field flyerTypeF = MenuRenderer.class.getDeclaredField("flyerType");
            flyerTypeF.setAccessible(true);
            flyerTypeF.set(renderer, type);
            Method generateM = MenuRenderer.class.getDeclaredMethod("generate");
            generateM.setAccessible(true);
            generateM.invoke(renderer);
            Method cacheM = MenuRenderer.class.getDeclaredMethod("cache");
            cacheM.setAccessible(true);
            cacheM.invoke(renderer);
        }catch(Throwable e){
            Log.err(e);
        }
    }

    /**
     * Ищет коллизию, игнорируя некоторые цели.
     */
    public static Healthc linecast(Bullet hitter, float x, float y, float angle, float length, Boolf<Healthc> predicate){
        final Building[] tmpBuilding = new Building[]{null};
        final Unit[] tmpUnit = new Unit[]{null};

        tr.trns(angle, length);

        if(hitter.type.collidesGround){
            world.raycastEachWorld(x, y, x + tr.x, y + tr.y, (cx, cy) -> {
                Building tile = world.build(cx, cy);
                if(tile == null || tile.team == hitter.team || !predicate.get(tile)) return false;
                tmpBuilding[0] = tile;
                return true;
            });
        }

        rect.setPosition(x, y).setSize(tr.x, tr.y);
        float x2 = tr.x + x, y2 = tr.y + y;

        if(rect.width < 0){
            rect.x += rect.width;
            rect.width *= -1;
        }
        if(rect.height < 0){
            rect.y += rect.height;
            rect.height *= -1;
        }

        float expand = 3f;
        rect.x -= expand;
        rect.y -= expand;
        rect.width += expand * 2;
        rect.height += expand * 2;

        Units.nearbyEnemies(hitter.team, rect, e -> {
            if((tmpUnit[0] != null && e.dst2(x, y) > tmpUnit[0].dst2(x, y)) || !e.checkTarget(hitter.type.collidesAir, hitter.type.collidesGround) || !predicate.get(e))
                return;

            e.hitbox(hitrect);
            Rect other = hitrect;
            other.x -= expand;
            other.y -= expand;
            other.width += expand * 2;
            other.height += expand * 2;

            if(Geometry.raycastRect(x, y, x2, y2, other) != null) tmpUnit[0] = e;
        });

        if(tmpBuilding[0] != null && tmpUnit[0] != null){
            if(Mathf.dst2(x, y, tmpUnit[0].getX(), tmpUnit[0].getY()) <= Mathf.dst2(x, y, tmpBuilding[0].getX(), tmpBuilding[0].getY()))
                return tmpUnit[0];
        }else if(tmpBuilding[0] != null) return tmpBuilding[0];

        return tmpUnit[0];
    }

    /**
     * for EMP
     */
    public static void trueEachBlock(float wx, float wy, float range, Cons<Building> cons){
//        Units.nearbyBuildings(wx, wy, range, cons);
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

    public static Seq<Teamc> allNearbyEnemiesOld(Team team, float x, float y, float radius){
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

    /**
     * For reload bar.
     */
    public static String stringsFixed(float value){
        return Strings.autoFixed(value, 2);
    }

    public static float mountX(Unit unit, WeaponMount mount){
        Weapon weapon = mount.weapon;
        float
        rotation = unit.rotation - 90,
        weaponRotation = rotation + (weapon.rotate ? mount.rotation : 0);
        return unit.x + Angles.trnsx(rotation, weapon.x, weapon.y) + Angles.trnsx(weaponRotation, 0, -mount.recoil);
    }

    public static float mountY(Unit unit, WeaponMount mount){
        Weapon weapon = mount.weapon;
        float
        rotation = unit.rotation - 90,
        weaponRotation = rotation + (weapon.rotate ? mount.rotation : 0);
        return unit.y + Angles.trnsy(rotation, weapon.x, weapon.y) + Angles.trnsy(weaponRotation, 0, -mount.recoil);
    }

    // Some powers below because Math.Pow is VERY slow
    public static float Pow3(float a){
        return a * a * a;
    }

    public interface Targeting {
        default Vec2 targetPos() {
            return null;
        }
    }
}
