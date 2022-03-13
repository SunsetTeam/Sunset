package sunset.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Draw.reset;
import static arc.math.Angles.randLenVectors;

public class TorpedoBulletType extends BasicBulletType {
    protected Color mColor;
    protected Color tColor = Color.valueOf("929AEA");


    public TorpedoBulletType (float speed, float damage) {
        super(speed, damage);
        collidesAir = absorbable = keepVelocity = false;
        chargeShootEffect = despawnEffect = hitEffect = shootEffect = smokeEffect = trailEffect = Fx.none;
        trailChance = 1;
        collideFloor = true;
        layer = Layer.floor;
        sprite = "shell";
    }

    /*public Effect torpedoTrail = new Effect(30, e -> {
        color(tColor);
        Fill.circle(e.x, e.y, e.rotation * e.fout());
    }).layer(Layer.floor);*/

    public Effect torpedoTrail = new Effect(30, e -> {
        color(tColor.a(95).clamp(), mColor, 0.5f);
        randLenVectors(e.id, 15, 1 + e.fin(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.rotation * e.fin() * 1.9f);
        });
        color(Pal.lightishOrange.a(25));
        Fill.circle(e.x, e.y, e.rotation * e.fout() / 3);
    }).layer(Layer.floor);

    @Override
    public void draw(Bullet b) {
        torpedoTrail.at(b.x, b.y, trailRotation ? b.rotation() : trailParam, tColor);
        super.draw(b);
    }
    @Override
    public void update(Bullet b) {
        super.update(b);
        mColor = b.tileOn().floor().mapColor;
    }
}
