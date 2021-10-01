package sunset.entities.bullet;

import arc.audio.Sound;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;

public class BerserkShotgunBulletType extends BasicBulletType {
    @Override
    public void init(Bullet b) {
        super.init();
        Sounds.shoot.at(b.x, b.y);
    }
}
