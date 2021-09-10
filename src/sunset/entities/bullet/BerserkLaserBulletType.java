package sunset.entities.bullet;

import arc.math.Mathf;
import arc.util.Log;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;

import java.awt.*;
import java.lang.reflect.Array;

public class BerserkLaserBulletType extends BasicBulletType {

    @Override
    public void hit(Bullet b, float x, float y) {
        if(b.data == null) {
            float lenght = Mathf.dst(b.x, b.y, x, y);
            b.data = lenght;
        }
    }

    @Override
    public void update(Bullet b) {
        if(b.data != null) Log.info((float)b.data);
    }
}
