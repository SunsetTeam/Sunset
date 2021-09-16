package sunset.entities.bullet;

import mindustry.Vars;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.graphics.Layer;
import mindustry.gen.Bullet;
import sunset.content.*;

public class TorpedoBulletType extends BasicBulletType {
public TorpedoBulletType (float speed, float damage) {
        super(speed, damage);
        collidesAir = absorbable = keepVelocity = false;
        trailEffect = SnFx.torpedoTrail;
    }

    @Override
    public void update(Bullet b) {
        if(!Vars.world.tileWorld(b.x, b.y).floor().isLiquid) b.remove();
    }
}
