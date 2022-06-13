package sunset.utils;

import arc.func.Boolf;
import arc.math.Mathf;
import arc.math.geom.*;
import arc.util.Log;
import arc.util.Tmp;
import mindustry.core.World;
import mindustry.entities.Units;
import mindustry.gen.Building;
import mindustry.gen.Healthc;
import mindustry.gen.Unit;
import mindustry.world.Build;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.StaticWall;
import sunset.world.blocks.laser.LaserBlock;

import static mindustry.Vars.world;

/** Temporary class for laser casts, gotta move elsewhere. */
public class LaserUtils {
    private static arc.math.geom.Rect rect = new Rect(),
                                    hitrect = new Rect();
    private static Vec2 tr = new Vec2();
    private static Building tmpBuilding;
    private static Unit tmpUnit;
    private static Tile tmpTile;


    public static Tile linecastStaticWalls(float x, float y, float angle, float length) {
        tmpTile = null;

        tr.trns(angle, length);
        World.raycastEachWorld(x, y, x + tr.x, y + tr.y, (cx, cy) -> {
            Tile tile = world.tile(cx, cy);
            if (tile == null || !(tile.block() instanceof StaticWall)) {
                Tile[] sideCollided = new Tile[4];
                int i = 0;
                for (Point2 p : Geometry.d4) {
                    tile = world.tile(cx + p.x, cy + p.y);
                    if (tile != null && tile.block() instanceof StaticWall && Intersector.intersectSegmentRectangle(x, y, x + tr.x, y + tr.y, tile.getHitbox(Tmp.r1))) {
                        sideCollided[i] = tile;
                        i++;
                    }
                }
                tile = getClosest(x, y, sideCollided);
                if (tile == null)
                    return false;
            }
            tmpTile = tile;
            return true;
        });

        return tmpTile;
    }

    public static Tile getClosest(float x, float y, Tile[] tiles){
        if(tiles[0] == null){
            return null;
        }
        Tile best = tiles[0];
        Tmp.v1.set(x, y);
        for (int i = 1; i < tiles.length; i++){
            if(tiles[i] == null)
                break;
            Tmp.v2.set(tiles[i].worldx(), tiles[i].worldy());
            if(Tmp.v1.dst2(Tmp.v2) < Tmp.v1.dst2(best.worldx(), best.worldy())){
                best = tiles[i];
            }
        }
        return best;
    }

    public static Healthc getClosest(float x, float y, Healthc[] entities){
        if (entities[0] == null){
            return null;
        }
        Healthc best = entities[0];
        Tmp.v1.set(x, y);
        for (int i = 1; i < entities.length; i++){
            if(entities[i] == null)
                break;
            Tmp.v2.set(entities[i].x(), entities[i].y());
            if (Tmp.v1.dst2(Tmp.v2) < Tmp.v1.dst2(best.x(), best.y())){
                best = entities[i];
            }
        }
        return best;
    }

    public static Healthc linecast(LaserBlock.LaserBlockBuild build, float x, float y, float angle, float length, boolean collideAir, boolean collideGround, Boolf<Healthc> predicate){
        tmpBuilding = null;
        tmpUnit = null;

        tr.trns(angle, length);

        if(collideGround){
            World.raycastEachWorld(x, y, x + tr.x, y + tr.y, (cx, cy) -> {
                Building tile = world.build(cx, cy);
                if(tile == null || !predicate.get(tile)){
                    Building[] sideCollided = new Building[4];
                    int i = 0;
                    for (Point2 p : Geometry.d4){
                        tile = world.build(cx + p.x, cy + p.y);
                        if(tile != null && tile != build){
                            tile.hitbox(Tmp.r1);
                            if(Intersector.intersectSegmentRectangle(x, y, x + tr.x, y + tr.y, Tmp.r1)){
                                sideCollided[i] = tile;
                                i++;
                            }
                        }
                    }
                    tile = (Building) getClosest(x, y, sideCollided);
                    if(tile == null)
                        return false;
                }
                tmpBuilding = tile;
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
            if((tmpUnit != null && e.dst2(x, y) > tmpUnit.dst2(x, y)) || !e.checkTarget(collideAir, collideGround) || !predicate.get(e))
                return;

            e.hitbox(hitrect);
            Rect other = hitrect;
            other.x -= expand;
            other.y -= expand;
            other.width += expand * 2;
            other.height += expand * 2;

            if(Geometry.raycastRect(x, y, x2, y2, other) != null)
                tmpUnit = e;
        });

        if(tmpBuilding != null && tmpUnit != null){
            if(Mathf.dst2(x, y, tmpUnit.getX(), tmpUnit.getY()) <= Mathf.dst2(x, y, tmpBuilding.getX(), tmpBuilding.getY()))
                return tmpUnit;
            else
                return tmpBuilding;
        }
        else if(tmpBuilding != null){
            return tmpBuilding;
        }
        else
            return tmpUnit;
    }
}
