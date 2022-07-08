package sunset.entities.bullet;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.MissileBulletType;
import mindustry.gen.Bullet;

public class SpriteMissileBulletType extends BasicBulletType {
    public TextureRegion backRegion;
    public TextureRegion frontRegion;

    public SpriteMissileBulletType(float speed, float damage, String missileSprite){
        super(speed, damage);
        this.sprite = missileSprite;
    }

    @Override
    public void load(){
        super.load();
        backRegion = Core.atlas.find(sprite + "-outline");
    }

    @Override
    public void draw(Bullet b) {
        drawTrail(b);
        Draw.rect(frontRegion, b.x, b.y, b.rotation() - 90);
        Draw.rect(backRegion, b.x, b.y, b.rotation() - 90);
        Draw.reset();
    }
}
