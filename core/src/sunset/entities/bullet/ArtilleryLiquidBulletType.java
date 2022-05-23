package sunset.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Angles;
import arc.math.Mathf;
import mindustry.entities.bullet.LiquidBulletType;
import mindustry.gen.Bullet;
import mindustry.type.Liquid;

/** "Artillery" liquid bullet. */
public class ArtilleryLiquidBulletType extends LiquidBulletType {
    public ArtilleryLiquidBulletType(Liquid liquid) {
        super(liquid);
        collidesTiles = false;
        collides = false;
        collidesAir = false;
        scaleLife = true;
    }

    @Override
    public void hit(Bullet b, float x, float y) {
        super.hit(b, x, y);
        if(fragBullet != null){
            for(int i = 0; i < fragBullets; i++){
                float len = Mathf.random(1f, 7f);
                float a = b.rotation() + Mathf.range(fragRandomSpread/2) + fragAngle;
                fragBullet.create(b, x + Angles.trnsx(a, len), y + Angles.trnsy(a, len), a, Mathf.random(fragVelocityMin, fragVelocityMax), Mathf.random(fragLifeMin, fragLifeMax));
            }
        }
    }

    @Override
    public float calculateRange() {
        return speed * lifetime;
    }

    @Override
    public void draw(Bullet b){
        float baseScale = 0.7f;
        float scale = (baseScale + b.fslope() * (1f - baseScale));

        Draw.color(liquid.color, Color.white, b.fout() / 100f);
        Fill.circle(b.x, b.y, orbSize * scale);
        Draw.color();
    }
}
