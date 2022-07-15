package sunset.entities.bullet;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;

public class SpriteMissileBulletType extends BasicBulletType {
    public SpriteMissileBulletType(float speed, float damage, String bulletSprite){
        super(speed, damage);
        this.speed = speed;
        this.damage = damage;
    }

    public SpriteMissileBulletType(float speed, float damage){
        this(speed, damage, "bullet");
    }

    public SpriteMissileBulletType(){
        this(1f, 1f, "bullet");
    }

    @Override
    public void load(){
        super.load();
        backRegion = Core.atlas.find(sprite + "-outline");
        frontRegion = Core.atlas.find(sprite);
    }

    @Override
    public void draw(Bullet b) {
        drawTrail(b);
        Draw.rect(frontRegion, b.x, b.y, width, height, b.rotation() - 90);
        Draw.rect(backRegion, b.x, b.y, width, height, b.rotation() - 90);
        Draw.reset();
    }
}
