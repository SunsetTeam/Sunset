package sunset.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Angles;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Layer;

public class TorpedoBulletType extends BasicBulletType {
    protected Color mColor;
    protected Color tColor = Color.valueOf("929AEA");
    public static final Effect defaultTorpedoTrail=new Effect(30, e -> {
        Draw.color(e.<TorpedoBulletType>data().trailColor);
        Fill.circle(e.x, e.y, e.rotation * e.fout());
        Draw.color(e.<TorpedoBulletType>data().tColor.a(95).clamp(), e.<TorpedoBulletType>data().mColor, 0.5f);
        Angles.randLenVectors(e.id, 15, 1 + e.fin(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.rotation * e.fin() * 1.9f);
        });
        //Draw.color(Pal.lightishOrange.a(25));
        //Fill.circle(e.x, e.y, e.rotation * e.fout() / 3);
    }).layer(Layer.floor);


    public TorpedoBulletType (float speed, float damage) {
        super(speed, damage);
        collidesAir = absorbable = keepVelocity = false;
        /*chargeShootEffect =*/ despawnEffect = hitEffect = shootEffect = smokeEffect = trailEffect = Fx.none;
        trailChance = 1;
        collideFloor = true;
        layer = Layer.floor;
        sprite = "shell";
    }

    public Effect torpedoTrail = defaultTorpedoTrail;

    @Override
    public void draw(Bullet b) {
        super.draw(b);
        torpedoTrail.at(b.x, b.y, trailRotation ? b.rotation() : trailParam, tColor,this);
    }
    @Override
    public void update(Bullet b) {
        super.update(b);
        mColor = b.tileOn().floor().mapColor;
    }
}
