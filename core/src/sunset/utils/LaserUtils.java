package sunset.utils;

import arc.func.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.core.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import sunset.world.blocks.laser.LaserBlock.*;

import static mindustry.Vars.world;

/** Temporary class for laser casts, gotta move elsewhere. */
public class LaserUtils{
    private static arc.math.geom.Rect rect = new Rect(),
    hitrect = new Rect();
    private static Vec2 tr = new Vec2();
    private static Building tmpBuilding;
    private static Unit tmpUnit;
    private static Tile tmpTile;

    private static void raycastStaticWallsWorld(float x1, float y1, float x2, float y2) {
        int x = toTile(x1), dx = Math.abs(toTile(x2) - x), sx = x < toTile(x2) ? 1 : -1;
        int y = toTile(y1), dy = Math.abs(toTile(y2) - y), sy = y < toTile(y2) ? 1 : -1;
        int e2, err = dx - dy;

        while (true) {
            Tile tile = world.tile(x, y);
            if (tile != null && tile.block() instanceof StaticWall) {
                tmpTile = tile;
                break;
            } else {
                Tile[] sideCollided = new Tile[4];
                int i = 0;
                for (Point2 p : Geometry.d4) {
                    tile = world.tile(x + p.x, y + p.y);
                    if (tile != null && tile.block() instanceof StaticWall && Intersector.intersectSegmentRectangle(x1, y1, x1 + tr.x, y1 + tr.y, tile.getHitbox(Tmp.r1))) {
                        sideCollided[i] = tile;
                        i++;
                    }
                }
                tile = getClosest(x1, y1, sideCollided);
                if (tile != null) {
                    tmpTile = tile;
                    break;
                }
            }


            if (x == toTile(x2) && y == toTile(y2)) break;

            e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x = x + sx;
            }

            if (e2 < dx) {
                err = err + dx;
                y = y + sy;
            }
        }
    }

    public static Tile linecastStaticWalls(float x, float y, float angle, float length){
        tmpTile = null;

        tr.trns(angle, length);
        raycastStaticWallsWorld(x, y, x + tr.x, y + tr.y);
        /*
        World.raycastEachWorld(x, y, x + tr.x, y + tr.y, (cx, cy) -> {
            Tile tile = world.tile(cx, cy);
            if(tile == null || !(tile.block() instanceof StaticWall)){
                Tile[] sideCollided = new Tile[4];
                int i = 0;
                for(Point2 p : Geometry.d4){
                    tile = world.tile(cx + p.x, cy + p.y);
                    if(tile != null && tile.block() instanceof StaticWall && Intersector.intersectSegmentRectangle(x, y, x + tr.x, y + tr.y, tile.getHitbox(Tmp.r1))){
                        sideCollided[i] = tile;
                        i++;
                    }
                }
                tile = getClosest(x, y, sideCollided);
                if(tile == null)
                    return false;
            }
            tmpTile = tile;
            return true;
        });

         */

        return tmpTile;
    }

    public static Tile getClosest(float x, float y, Tile[] tiles){
        if(tiles.length == 0 || tiles[0] == null){
            return null;
        }
        Tile best = tiles[0];
        float bestDst2 = Mathf.dst2(tiles[0].getX(), tiles[0].getY(), x, y),dst2;
        for(int i = 1; i < tiles.length; i++){
            if(tiles[i] == null)
                break;
            dst2 = Mathf.dst2(tiles[i].getX(), tiles[i].getY(), x, y);
            if(dst2 < bestDst2){
                best = tiles[i];
                bestDst2 = dst2;
            }
        }
        return best;
    }

    public static Healthc getClosest(float x, float y, Healthc[] entities){
        if(entities.length == 0 || entities[0] == null){
            return null;
        }
        Healthc best = entities[0];
        float bestDst2 = Mathf.dst2(entities[0].x(), entities[0].y(), x, y),dst2;
        for(int i = 1; i < entities.length; i++){
            if(entities[i] == null)
                break;
            dst2 = Mathf.dst2(entities[i].x(), entities[i].y(), x, y);
            if(dst2 < bestDst2){
                best = entities[i];
                bestDst2 = dst2;
            }
        }
        return best;
    }

    private static int toTile(float coord){
        return Math.round(coord / Vars.tilesize);
    }

    private static void raycastEachWorld(Building build, float x1, float y1, float x2, float y2, Boolf<Healthc> predicate){
        int x = toTile(x1), dx = Math.abs(toTile(x2) - x), sx = x < toTile(x2) ? 1 : -1;
        int y = toTile(y1), dy = Math.abs(toTile(y2) - y), sy = y < toTile(y2) ? 1 : -1;
        int e2, err = dx - dy;

        while(true){
            Building tile = world.build(x, y);
            if(tile != null && predicate.get(tile) && tile != build){
                tmpBuilding = tile;
                break;
            }
            else{
                Building[] sideCollided = new Building[4];
                int i = 0;
                for(Point2 p : Geometry.d4){
                    tile = world.build(x + p.x, y + p.y);
                    if(tile != null && tile != build){
                        tile.hitbox(Tmp.r1);
                        if(Intersector.intersectSegmentRectangle(x1, y1, x1 + tr.x, y1 + tr.y, Tmp.r1)){
                            sideCollided[i] = tile;
                            i++;
                        }
                    }
                }
                tile = (Building)getClosest(x1, y1, sideCollided);
                if(tile != null){
                    tmpBuilding = tile;
                    break;
                }
            }

            if(x == toTile(x2) && y == toTile(y2)) break;

            e2 = 2 * err;
            if(e2 > -dy){
                err -= dy;
                x += sx;
            }

            if(e2 < dx){
                err += dx;
                y += sy;
            }
        }
    }

    public static Healthc linecast(LaserBuild build, float x, float y, float angle, float length, boolean collideAir, boolean collideGround, Boolf<Healthc> predicate){
        tmpBuilding = null;
        tmpUnit = null;

        tr.trns(angle, length);

        if(collideGround){
            raycastEachWorld(build, x, y, x + tr.x, y + tr.y, predicate);

            /*
            World.raycastEachWorld(x, y, x + tr.x, y + tr.y, (cx, cy) -> {
                Building tile = world.build(cx, cy);
                if(tile == null || !predicate.get(tile) || tile == build){
                    Building[] sideCollided = new Building[4];
                    int i = 0;
                    for(Point2 p : Geometry.d4){
                        tile = world.build(cx + p.x, cy + p.y);
                        if(tile != null && tile != build){
                            tile.hitbox(Tmp.r1);
                            if(Intersector.intersectSegmentRectangle(x, y, x + tr.x, y + tr.y, Tmp.r1)){
                                sideCollided[i] = tile;
                                i++;
                            }
                        }
                    }
                    tile = (Building)getClosest(x, y, sideCollided);
                    if(tile == null)
                        return false;
                }
                tmpBuilding = tile;
                return true;
            });
             */
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
        }else if(tmpBuilding != null){
            return tmpBuilding;
        }else
            return tmpUnit;
    }
}
