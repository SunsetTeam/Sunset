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
    public Color[] colors = new Color[]{Color.valueOf("FFFFFF55"), Color.valueOf("CCCDDAaa"), Color.valueOf("9799A3"), Color.white};
    public float[] tskales = {1f, 0.8f, 0.6f, 0.3f};
    public float[] strokes = {1.6f, 1.3f, 1f, 0.3f};
    public float[] lenscales = {1f, 1.2f, 1.24f, 1.30f};


    @Override
    public void update(Bullet b){
        Healthc target = Damage.linecast(b, b.x, b.y, b.rotation(), maxLaserLength);
        float dst = maxLaserLength;
        float bs = 0;
        Vec2 v = new Vec2().trns(b.rotation(), maxLaserLength).add(b.x, b.y);
        if(target != null) {
            dst = Mathf.dst(b.x, b.y, target.x(), target.y());
        } else {
            dst = maxLaserLength;
        }
        b.data = new Object[]{target, dst};
        if(b.timer.get(5))Damage.collideLine(b, b.team, hitEffect, b.x, b.y, b.rotation(), dst);
        if(target instanceof Hitboxc){
            Hitboxc hit = (Hitboxc)target;
            hit.collision(b, hit.x(), hit.y());
            b.collision(hit, hit.x(), hit.y());
        } else if(target instanceof Building){
            Building tile = (Building)target;
            b.data = new Object[]{target, dst - tile.block.size};
            if(tile.collide(b)){
                tile.collision(b);
                hit(b, tile.x, tile.y);
            }
        } else {

            b.data = new Object[]{v, dst};
        }
        Log.info("Длина лазера = "+dst);
        Log.info("Ширина лазера = "+swidth);
        Log.info("Длина массива = " + bw.size);
    }

    @Override
    public void draw(Bullet b) {
        if(((Object[])b.data)[0] instanceof Position){
            Position data = (Position)((Object[])b.data)[0];
            Tmp.v1.set(data).lerp(b, b.fin());
            float fout = Mathf.clamp(b.time > b.lifetime - 15 ? 1f - (b.time - (lifetime - 15)) / 15 : 1f);
            float dst = (float)((Object[])b.data)[1];
            for(int i = 0; i < 4; i++){
                Draw.color(Tmp.c1.set(colors[i]).mul(1f + Mathf.absin(Time.time, 1f, 0.1f)));
                for(int j = 0; j < tskales.length; j++){
                    Tmp.v1.trns(b.rotation() + 180f, (lenscales[j] - 0.8f) * 55f);
                    Lines.stroke((swidth + Mathf.absin(Time.time, 0.8f, 1.5f)) * fout * strokes[i] * tskales[i]);
                    Lines.lineAngle(b.x + Tmp.v1.x, b.y + Tmp.v1.y, b.rotation(),dst * lenscales[j]);
                }
            }
        }
        for(int i = 0; i<bw.size; i++){
            if(b.owner instanceof Unit){
                Unit u = (Unit) b.owner;
                Log.info("Здоровье цели = "+u.health);
                if(u.health < bw.get(i).needHealth){
                    this.swidth = bw.get(i).damageMultiplier;
                } else {
                    swidth = width;
                }
            }
        }
    }
    public void setWidth(BerserkAbility... berserkAbilities){
        this.bw = Seq.with(berserkAbilities);
    }
}
