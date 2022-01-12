package sunset.entities.bullet;

import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.entities.Lightning;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Pal;

public class LightningContinuousLaserBulletType extends ContinuousLaserBulletType {
    public float LightningChance = 0f;
    public Color lightningColor = Color.orange;
    public int lightning;
    public int lightningLength = 0, lightningLengthRand = 0;
    public float lightningDamage = -1;
    public float lightningCone = 180f;
    public float lightningAngle = 0f;
    public LightningContinuousLaserBulletType(float damage) {
        drawSize = 420f;
    }

    @Override
    public void init(Bullet b){
        super.init();
        drawSize = Math.max(drawSize, length*2f);
        if (Mathf.chanceDelta(LightningChance)) {
            for(int i = 0; i < lightning; i++) {
                Lightning.create(b, lightningColor, lightningDamage < 0 ? damage : lightningDamage, b.x, b.y, b.rotation() + Mathf.range(lightningCone/2) + lightningAngle, lightningLength + Mathf.random(lightningLengthRand));
            }
        }
    }

    @Override
    public float estimateDPS(){
        return super.estimateDPS() * Math.max(lightningLength / 10f, 1);
    }
}
