package sunset.entities.bullet;

import arc.math.Mathf;
import arc.util.Log;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;

public class BerserkLaserBulletType extends BasicBulletType {

    @Override
    public void hit(Bullet b, float x, float y) {
        if(b.data == null) {
            float lenght = Mathf.dst(b.x, b.y, x, y);
            float[] dst = new float[1];
            b.data = dst;
            ((float[])b.data)[0] = lenght;
        }
    }

    @Override
    public void update(Bullet b) {
        if(b.data != null) Log.info(((float[])b.data)[0]);
    }
}
