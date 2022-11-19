package sunset.entities.bullet;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Hitboxc;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;

public class StickyBulletType extends BasicBulletType {
    public float detonatoinTime = 60;

    public StickyBulletType(float speed, float damage){
        super(speed, damage);
        lifetime += detonatoinTime;
    }

    @Override
    public void init(Bullet b) {
        super.init(b);
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health) {
        super.hitEntity(b, entity, health);
        StickyBulletTypeData data = (StickyBulletTypeData) b.data;
        if (data.hit) return;
        data.hit = true;
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        updateTrail(b);
        updateHoming(b);
        updateWeaving(b);
        updateTrailEffects(b);
        updateBulletInterval(b);

        StickyBulletTypeData data = (StickyBulletTypeData) b.data;

        if(data == null || data.target == null) return;

        float bx = b.x, by = b.y;
        float bx2 = data.target.x();
        float by2 = data.target.y();

        if(data.sAngle == null) data.sAngle = Mathf.angle(bx - bx2, by - by2);
        if(data.target instanceof Unit unit && data.tRotate == null) data.tRotate = unit.rotation;

        b.vel(Vec2.ZERO);
        if(data.target instanceof Unit unit){
            float angle = data.sAngle - data.tRotate + unit.rotation;
            b.x = (float) (unit.x + Math.cos(angle + Mathf.degreesToRadians) * unit.hitSize);
            b.y = (float) (unit.y + Math.cos(angle + Mathf.degreesToRadians) * unit.hitSize);

            if(unit.dead())b.vel(Vec2.ZERO);
        }
    }

    public static class StickyBulletTypeData {
        public boolean hit = false;
        public Teamc target;
        public Float tRotate;
        public Float sAngle;
    }
}
