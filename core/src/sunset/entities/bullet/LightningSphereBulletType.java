package sunset.entities.bullet;

import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Lightning;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;

public class LightningSphereBulletType extends BasicBulletType {
    public float LightningChance = 0f;
    public Color lightningColor = Pal.lancerLaser;
    public int lightning;
    public int lightningLength = 0, lightningLengthRand = 0;
    public float lightningDamage = -1;
    public float lightningCone = 360f;
    public float lightningAngle = 0f;
    public LightningSphereBulletType (float speed, float damage, String bulletSprite){
        super(speed, damage, bulletSprite);
        despawnEffect = Fx.none;
        hitEffect = Fx.hitLancer;
    }

    @Override
    public void update(Bullet b) {
        super.update(b);

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

    @Override
    public void draw(Bullet b){
    }
}
