package sunset.utils;

import arc.func.Boolf;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Rect;
import arc.math.geom.Vec2;
import mindustry.entities.Units;
import mindustry.gen.Building;
import mindustry.gen.Healthc;
import mindustry.gen.Unit;

import static mindustry.Vars.world;

/** временный класс для каста лазеров
 * котлин блять >:( */
public class LaserUtils {
    public static arc.math.geom.Rect rect = new Rect(),
                                    hitrect = new Rect();
    public static Vec2 tr = new Vec2();
    public static Healthc linecast(float x, float y, float angle, float length, boolean collideAir, boolean collideGround, Boolf<Healthc> predicate){
        final Building[] tmpBuilding = new Building[]{null};
        final Unit[] tmpUnit = new Unit[]{null};

        tr.trns(angle, length);

        if(collideGround){
            world.raycastEachWorld(x, y, x + tr.x, y + tr.y, (cx, cy) -> {
                Building tile = world.build(cx, cy);
                if(tile == null || !predicate.get(tile)) return false;
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

        float expand = 1f;
        rect.x -= expand;
        rect.y -= expand;
        rect.width += expand * 2;
        rect.height += expand * 2;

        Units.nearbyEnemies(null, rect, e -> {
            if((tmpUnit[0] != null && e.dst2(x, y) > tmpUnit[0].dst2(x, y)) || !e.checkTarget(collideAir, collideGround) || !predicate.get(e))
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
            else
                return tmpBuilding[0];
        }
        else{
            if(tmpBuilding[0] != null)
                return tmpBuilding[0];
            if(tmpUnit[0] != null)
                return tmpUnit[0];
        }
        return null;
    }
}
