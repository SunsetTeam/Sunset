package sunset.entities.bullet;

import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.Mover;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.pattern.ShootPattern;
import mindustry.gen.Bullet;

public class SummonBulletType extends BasicBulletType {
    public BulletType summonBullet;
    public ShootPattern shootPattern = new ShootPattern();
    public float inaccuracy = 0f;
    public float summoBulletDelay = 0f;
    public float summonBulletInterval = 0f;
    public float rocketAngle = 180f;

    public SummonBulletType(float speed, float damage, String bulletSprite){
        super(speed, damage);
        this.sprite = bulletSprite;
    }

    public SummonBulletType(float speed, float damage){
        this(speed, damage, "bullet");
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        if (b.data == null) b.data = 0;
        if (b.time > summoBulletDelay) b.fdata -= Time.delta;

        if (b.fdata < 0) {
            b.fdata = summonBulletInterval;

            shootPattern.shoot((int) b.data, (xOffset, yOffset, angle, delay, mover) -> {
                if (delay > 0) {
                    Time.run(delay, () -> shoot(b, xOffset, yOffset, angle, mover));
                } else {
                    shoot(b, xOffset, yOffset, angle, mover);
                }

            });
        }
    }

    public void shoot(Bullet owner, float xOffset, float yOffset, float angleOffset, Mover mover) {
        float bulletX = owner.x + Angles.trnsx(owner.rotation(), xOffset, yOffset);
        float bulletY = owner.y + Angles.trnsy(owner.rotation(), xOffset, yOffset);
        float rotateAngle = owner.rotation() + rocketAngle + angleOffset + Mathf.range(inaccuracy);

        summonBullet.create(owner, owner.team, bulletX, bulletY, rotateAngle, 1f, 1f, mover);
        summonBullet.shootEffect.at(bulletX, bulletY, rotateAngle, summonBullet.hitColor);
    }
}
