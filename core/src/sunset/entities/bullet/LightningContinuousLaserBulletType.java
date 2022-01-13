package sunset.entities.bullet;

import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Lightning;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Pal;

public class LightningContinuousLaserBulletType extends ContinuousLaserBulletType {
    public float length = 220f;
    public float shake = 1f;
    public Color lightningColor = Pal.meltdownHit;
    public int lightning;
    public int lightningLength = 0, lightningLengthRand = 0;
    public float lightningDamage = -1;
    public float lightningCone = 180f;
    public float lightningAngle = 0f;
    public LightningContinuousLaserBulletType(float damage) {
        this.damage = damage;
        this.speed = 0f;

        drawSize = 420f;
    }

    @Override
    public void init(Bullet b){
        super.init(b);

        drawSize = Math.max(drawSize, length*2f);

        for(int i = 0; i < lightning; i++) {
            Lightning.create(b, lightningColor, lightningDamage < 0 ? damage : lightningDamage, b.x, b.y, b.rotation() + Mathf.range(lightningCone/2) + lightningAngle, lightningLength + Mathf.random(lightningLengthRand));
        }
    }

}
