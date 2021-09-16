package sunset.entities.bullet;

import mindustry.Vars;
import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.*;
import mindustry.gen.Bullet;
import sunset.content.*;

public class TorpedoBulletType extends BasicBulletType {
public TorpedoBulletType (float speed, float damage) {
        super(speed, damage);
        layer = Layer.debris + 0.001f;
        collidesAir = absorbable = keepVelocity = false;
        trailEffect = SnFx.torpedoTrail;
    }

    @Override
    public void update(Bullet b) {
        if(!Vars.world.tileWorld(b.x, b.y).floor().isLiquid) b.remove();
    }
}
