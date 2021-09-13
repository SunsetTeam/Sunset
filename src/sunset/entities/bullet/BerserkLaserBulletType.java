package sunset.entities.bullet;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.Drawf;


public class BerserkLaserBulletType extends BulletType {
    public float maxLaserLength = 120f;
    public float width = 3;
    public Effect hitEffect = Fx.none;
    public Color color = Color.gray;


    @Override
    public void update(Bullet b){
        Healthc target = Damage.linecast(b, b.x, b.y, b.rotation(), maxLaserLength);
        float dst = Mathf.dst(b.x, b.y, target.getX(), target.getY());
        b.data = dst;
        Damage.collideLine(b, b.team, hitEffect, b.x, b.y, b.rotation(), dst);
        if(target instanceof Hitboxc){
            Hitboxc hit = (Hitboxc)target;
            hit.collision(b, hit.x(), hit.y());
            b.collision(hit, hit.x(), hit.y());
        } else if(target instanceof Building){
            Building tile = (Building)target;
            if(tile.collide(b)){
                tile.collision(b);
                hit(b, tile.x, tile.y);
            }
        } else {
            b.data = new Vec2().trns(b.rotation(), maxLaserLength).add(b.x, b.y);
        }
    }

    @Override
    public void draw(Bullet b) {
        if(b.data instanceof Position){
            Position data = (Position)b.data;
            Tmp.v1.set(data).lerp(b, b.fin());
            Draw.color(color);
            Drawf.laser(b.team, Core.atlas.find("laser"), Core.atlas.find("laser-end"), b.x, b.y, Tmp.v1.x, Tmp.v1.y, width);
            Drawf.light(b.team, b.x, b.y, Tmp.v1.x, Tmp.v1.y,15, lightColor, lightOpacity);
        }
    }
}
