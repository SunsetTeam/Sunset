package sunset.world.blocks;

import mindustry.gen.Bullet;
import mindustry.world.blocks.defense.Wall;

/** Стена, которая снижает пробитие пуль. */
public class AntiPierceWall extends Wall {
    /** Дополнительный штраф к пробитию. */
    public int pierceDebuff = 1;
    /** Множитель урона пули. */
    public float damageDebuff = 0.95f;
    public AntiPierceWall(String name) {
        super(name);
    }
    public class AntiPierceWallBuild extends WallBuild {
        @Override
        public boolean collision(Bullet bullet) {
            super.collision(bullet);
            if(bullet.type.pierceCap != -1 && bullet.type.pierce) {
                for(int i = 0; i < pierceDebuff; i++) bullet.collided.add(id);
            }
            bullet.damage *= damageDebuff;
            return true;
        }
    }
}
