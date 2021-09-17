package sunset.entities.bullet;

import mindustry.Vars;
import mindustry.entities.bullet.*;
import arc.graphics.g2d.Draw;
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
    public void draw(Bullet b){
        Draw.z(Layer.debris);
        super.draw(b);
}



    @Override
    public void update(Bullet b) {
        if(!Vars.world.tileWorld(b.x, b.y).floor().isLiquid) b.remove();
    }
}
