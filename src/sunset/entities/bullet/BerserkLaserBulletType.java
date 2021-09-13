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
        float dst = maxLaserLength;
        Vec2 v = new Vec2().trns(b.rotation(), maxLaserLength).add(b.x, b.y);
        if(target != null) {
            Mathf.dst(b.x, b.y, target.x(), target.y());
        } else {
            dst = maxLaserLength;
        }
        b.data = new Object[]{target, dst};
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

            b.data = new Object[]{v, dst};
        }
    }

    @Override
    public void draw(Bullet b) {
        if(((Object[])b.data)[0] instanceof Position){
            Position data = (Position)((Object[])b.data)[0];
            Tmp.v1.set(data).lerp(b, b.fin());
            float dst = (float)((Object[])b.data)[1];
            Draw.color(color);
            Drawf.laser(b.team, Core.atlas.find("laser"), Core.atlas.find("laser-end"), b.x, b.y, Tmp.v1.x, Tmp.v1.y, dst);
            Drawf.light(b.team, b.x, b.y, Tmp.v1.x, Tmp.v1.y,15, lightColor, lightOpacity);
        }
    }
}
