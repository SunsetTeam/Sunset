package sunset.entities.bullet;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.*;
import sunset.graphics.SnPal;

import static sunset.utils.ArtLBulletUtils.createLength;

public class ArtilleryLightningBulletType extends BulletType {
    public int lightNumber = 1;
    public static Color lColor = SnPal.chainLaser;
    public static float effectLifetime = 50;

    public ArtilleryLightningBulletType(float damage) {
        super(0.0001f, damage);
        hitShake = 2f;
        hitSound = Sounds.spark;
        absorbable = keepVelocity = collides = false;
        instantDisappear = collidesAir = collidesGround = scaleLife = true;
        lightning = 3;
        lightningDamage = damage;
        lightningLength = lightningLengthRand = 6;
        despawnEffect = Fx.none;
    }

    @Override
    public void init() {
        super.init();
        drawSize = Math.max(drawSize, maxRange * 2);
    }

    public float range() {
        return maxRange;
    }

    @Override
    public void init(Bullet b) {
        float length = b.lifetime * range() / lifetime;

        Healthc target = Damage.linecast(b, b.x, b.y, b.rotation(), length + 4f);
        b.data = target;

        if (target instanceof Hitboxc) {
            Hitboxc hit = (Hitboxc)target;
            hit.collision(b, hit.x(), hit.y());
            b.collision(hit, hit.x(), hit.y());
        } else if (target instanceof Building) {
            Building tile = (Building)target;
            if (tile.collide(b)) {
                tile.collision(b);
                hit(b, tile.x, tile.y);
            }
        }

        createLength(b, b.team, b, length, b.rotation(), lightningColor, true, 0, 0, 2.5f, lightNumber, p -> {
            hitEffect.at(p);
            Effect.shake(hitShake, hitShake, p);
        });
        super.init(b);
    }
    @Override
    public void despawned(Bullet b) {
        despawnEffect.at(b.x, b.y, b.rotation(), lightningColor);
    }
    @Override
    public void hit(Bullet b) {}
    @Override
    public void hit(Bullet b, float x, float y) {}
    @Override
    public void draw(Bullet b) {}
    @Override
    public void drawLight(Bullet b) {}
}
