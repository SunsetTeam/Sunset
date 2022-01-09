package sunset.entities.bullet;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import sunset.type.*;
import sunset.type.unitTypes.*;


public class BerserkLaserBulletType extends BulletType{
    public float maxLaserLength = 120f;
    public float width = 3;
    public Effect laserHitEffect = Fx.none;
    public Color[] colors = new Color[]{Color.valueOf("FFFFFF55"), Color.valueOf("CCCDDAaa"), Color.valueOf("9799A3"), Color.white};
    public float[] tskales = {1f, 0.8f, 0.6f, 0.3f};
    public float[] strokes = {1.6f, 1.3f, 1f, 0.3f};
    public float[] lenscales = {1f, 1.2f, 1.24f, 1.30f};


    @Override
    public void update(Bullet b){
        Healthc target = Damage.linecast(b, b.x, b.y, b.rotation(), maxLaserLength);
        float dst = maxLaserLength;
        Vec2 h = Tmp.v2;
        Vec2 v = new Vec2().trns(b.rotation(), maxLaserLength).add(b.x, b.y);
        if(target != null){
            dst = Mathf.dst(b.x, b.y, target.x(), target.y());
        }else{
            dst = maxLaserLength;
        }
        LaserData data = new LaserData(target, dst);
//        b.data = new Object[]{target, dst};
        if(target instanceof Hitboxc){
            Hitboxc hit = (Hitboxc)target;
            hit.collision(b, hit.x(), hit.y());
            b.collision(hit, hit.x(), hit.y());
            h.trns(b.rotation(), dst);
            laserHitEffect.at(b.x + h.x, b.y + h.y);
        }else if(target instanceof Building){
            Building tile = (Building)target;
            h.trns(b.rotation(), dst - tile.block.size);
            laserHitEffect.at(b.x + h.x, b.y + h.y);
            data.set(target, dst - tile.block.size);

            if(tile.collide(b)){
                tile.collision(b);
                hit(b, tile.x, tile.y);
            }
        }else{
            data.set(v, dst);
        }
        b.data = data;
    }

    @Override
    public void draw(Bullet b){
        float swidth = width;
        if(b.owner instanceof Unit){
            Unit u = (Unit)b.owner;
            BerserkStage stage = BerserkUnitType.getStage(u);
            if(stage != null) swidth = width * stage.bulletWidthMultiplier;
        }
        LaserData laserData = LaserData.fromObject(b.data);
        if(laserData == null) return;
        Tmp.v1.set(laserData.x, laserData.y).lerp(b, b.fin());
        float fout = Mathf.clamp(b.time > b.lifetime - 15 ? 1f - (b.time - (lifetime - 15)) / 15 : 1f);
        for(int i = 0; i < 4; i++){
            Draw.color(Tmp.c1.set(colors[i]).mul(1f + Mathf.absin(Time.time, 1f, 0.1f)));
            for(int j = 0; j < tskales.length; j++){
                Tmp.v1.trns(b.rotation() + 180f, (lenscales[j] - 0.8f) * 55f);
                Lines.stroke((swidth + Mathf.absin(Time.time, 0.8f, 1.5f)) * fout * strokes[i] * tskales[i]);
                Lines.lineAngle(b.x + Tmp.v1.x, b.y + Tmp.v1.y, b.rotation(), laserData.distance * lenscales[j]);
            }
        }
    }

    private static class LaserData{
        float x;
        float y;
        float distance;

        public LaserData(float x, float y, float distance){
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        public LaserData(Position position, float distance){
            this(position.getX(), position.getY(), distance);
        }

        public LaserData(){
        }

        @Nullable
        public static LaserData fromArray(Object[] array){
            try{
                if(array[0] instanceof Float) return new LaserData((float)array[0], (float)array[1], (float)array[2]);
                return new LaserData((Position)array[0], (float)array[1]);
            }catch(IndexOutOfBoundsException | ClassCastException ignored){
                return null;
            }
        }

        @Nullable
        public static LaserData fromObject(Object object){
            if(object instanceof LaserData data) return data;
            return fromArray((Object[])object);
        }

        public void set(float x, float y, float distance){
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        public void set(Position position, float distance){
            set(position.getX(), position.getY(), distance);
        }

        public Object[] toArray(){
            return new Object[]{x, y, distance};
        }

        public Object toObject(){
            return toArray();
        }
    }
}
