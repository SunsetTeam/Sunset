package sunset.entities.bullet;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.Seq;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import sunset.entities.abilities.BerserkAbility;


public class BerserkLaserBulletType extends BulletType {
    public float maxLaserLength = 120f;
    public float width = 3;
    private float swidth = 1;
    public Effect hitEffect = Fx.none;
    public Seq<BerserkAbility> bw = new Seq<>();
    public Color[] colors = new Color[]{Color.yellow, Color.blue};


    @Override
    public void update(Bullet b){
        Healthc target = Damage.linecast(b, b.x, b.y, b.rotation(), maxLaserLength);
        float dst = maxLaserLength;
        Vec2 v = new Vec2().trns(b.rotation(), maxLaserLength).add(b.x, b.y);
        if(target != null) {
            dst = Mathf.dst(b.x, b.y, target.x(), target.y());
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
            for (Color color : colors){
                Draw.color(color);
                Lines.stroke(width * swidth);
                Lines.lineAngle(b.x, b.y, b.rotation(), dst);
            }
        }
        for(int i = 0; i>bw.size; i++){
            if(b.owner instanceof Unit){
                Unit u = (Unit) b.owner;
                if(u.health < bw.get(i).needHealth){
                    this.swidth = bw.get(i).damageMultiplier;
                }
            }
        }
    }
    public void setWidth(BerserkAbility... berserkAbilities){
        this.bw = Seq.with(berserkAbilities);
    }
}
