package sunset.entities.bullet;

import arc.graphics.g2d.Draw;
import mindustry.entities.bullet.MissileBulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Layer;

public class CopterRocketBulletType extends MissileBulletType{
    public CopterRocketBulletType(float speed, float damage){
    }
    @Override
    public void draw(Bullet b){
        Draw.z(Layer.legUnit);
        super.draw(b);
    }

}
