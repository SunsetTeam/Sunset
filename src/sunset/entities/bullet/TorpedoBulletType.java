package sunset.entities.bullet;

import mindustry.Vars;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;

public class TorpedoBulletType extends BasicBulletType {
    @Override
    public void update(Bullet b) {
        if(!Vars.world.tileWorld(b.x, b.y).floor().isLiquid) b.remove();
    }
}
