package sunset.entities.bullet;

import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Healthc;
import mindustry.gen.Hitboxc;

public class SapFlyingBulletType extends BasicBulletType {
    public float sapStrength = 0.8f;
    
    public SapFlyingBulletType(float speed, float damage) { super(speed, damage); }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health) {
        float result = Math.min(damage, health);
        if(b.owner instanceof Healthc h) h.heal(result * sapStrength);
        super.hitEntity(b, entity, health);
    }

    @Override
    public void hitTile(Bullet b, Building build, float initialHealth, boolean direct) {
        if(direct) {
            float result = Math.min(damage, initialHealth);
            if(b.owner instanceof Healthc h) h.heal(result * sapStrength);    
        }
        super.hitTile(b, build, initialHealth, direct);
    }
}