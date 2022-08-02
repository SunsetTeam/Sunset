package sunset.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.geom.Vec2;
import arc.util.Time;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.pattern.ShootPattern;
import mindustry.gen.Bullet;

// НЕ ТРОГАТЬ НИКОМУ!!! Это - proof-of-concept
public class FlowerBulletType extends BasicBulletType {
    public ShootPattern shoot;
    public Color otherTrailColor;
    public float rotateFactorMin, rotateFactorMax;

    public static class FlowerBulletTypeData {
        public float angleOffset, colorRnd, rotateFactor;

        public FlowerBulletTypeData(float angleOffset, float colorRnd, float rotateFactor) {
            this.angleOffset = angleOffset;
            this.colorRnd = colorRnd;
            this.rotateFactor = rotateFactor;
        }
    }

    static Color colTmp = new Color();

    @Override
    public void draw(Bullet b) {
        drawTrail(b);
        FlowerBulletTypeData data = (FlowerBulletTypeData)b.data;
        Draw.color(trailColor, otherTrailColor, data.colorRnd);
        Fill.circle(b.x, b.y, trailWidth);
    }

    @Override
    public void drawTrail(Bullet b) {
        if(trailLength > 0 && b.trail != null){
            FlowerBulletTypeData data = (FlowerBulletTypeData)b.data;

            float z = Draw.z();
            Draw.z(z - 0.0001f);
            colTmp.set(trailColor).lerp(otherTrailColor, data.colorRnd);
            b.trail.draw(colTmp, trailWidth);
            Draw.z(z);
        }
    }

    @Override
    public void update(Bullet b) {
        FlowerBulletTypeData data = (FlowerBulletTypeData)b.data;

        super.update(b);
        float angleOffs = Time.delta * data.rotateFactor * b.vel.len();
        if(data.angleOffset < 0) angleOffs *= -1;
        b.vel.setAngle(b.vel.angle() + angleOffs);
    }
}
