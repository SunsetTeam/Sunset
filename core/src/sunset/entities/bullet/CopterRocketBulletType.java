package sunset.entities.bullet;

import arc.graphics.g2d.Draw;
import mindustry.entities.bullet.MissileBulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Layer;
import arc.Core;
import arc.graphics.g2d.Draw;
import mindustry.gen.*;
import mindustry.graphics.*;

public class CopterRocketBulletType extends MissileBulletType{
    public CopterRocketBulletType(float speed, float damage){
        super(speed, damage);
    }

    @Override
	public void load() {
		super.load();
		backRegion = Core.atlas.find(sprite);
	}

    @Override
    public void draw(Bullet b) {
        super.draw(b);
        Draw.rect(frontRegion, b.x, b.y, width, height, b.rotation() - 90);
        Draw.rect(backRegion, b.x, b.y, width, height, b.rotation() - 90);

        Draw.reset();
    }
}
