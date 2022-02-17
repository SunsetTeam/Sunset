package sunset.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Layer;

import static arc.graphics.g2d.Draw.color;

public class TorpedoBulletType extends BulletType {
    protected Color tColor;

    public TorpedoBulletType (float speed, float damage) {
        super(speed, damage);
        collidesAir = absorbable = keepVelocity = false;
        chargeShootEffect = despawnEffect = hitEffect = shootEffect = smokeEffect = trailEffect = Fx.none;
        trailChance = 1;
        collideFloor = true;
        layer = Layer.floor;
    }

    public Effect torpedoTrail = new Effect(30, e -> {
        color(tColor);
        Fill.circle(e.x, e.y, e.rotation * e.fout());
    }).layer(Layer.floor);

    @Override
    public void draw(Bullet b) {
        torpedoTrail.at(b.x, b.y, trailRotation ? b.rotation() : trailParam, tColor);
    }
    @Override
    public void update(Bullet b) {
        super.update(b);
        tColor = b.tileOn().floor().mapColor;
    }
}
