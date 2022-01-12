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
    public boolean largeHit = true;
    public Color lightningColor = Pal.meltdownHit;
    public int lightning;
    public int lightningLength = 0, lightningLengthRand = 0;
    public float lightningDamage = -1;
    public float lightningCone = 180f;
    public float lightningAngle = 0f;
    public LightningContinuousLaserBulletType(float damage) {
        drawSize = 420f;
    }

    @Override
    public float estimateDPS(){
        return damage * 100f / 5f * 3f + super.estimateDPS() * Math.max(lightningLength / 10f, 1);
    }

    @Override
    public void update(Bullet b){
        for(int i = 0; i < lightning; i++) {
            Lightning.create(b, lightningColor, lightningDamage < 0 ? damage : lightningDamage, b.x, b.y, b.rotation() + Mathf.range(lightningCone/2) + lightningAngle, lightningLength + Mathf.random(lightningLengthRand));
        }

        //damage every 5 ticks
        if(b.timer(1, 5f)){
            Damage.collideLine(b, b.team, hitEffect, b.x, b.y, b.rotation(), length, largeHit);
        }

        if(shake > 0){
            Effect.shake(shake, shake, b);
        }
    }
}
