package sunset.world.blocks.defense.walls;

import mindustry.gen.Bullet;
import mindustry.world.blocks.defense.Wall;
import sunset.world.meta.SnStats;
import sunset.world.meta.SnStatsUser;

/** Стена, которая снижает пробитие пуль. */
public class AntiPierceWall extends Wall implements SnStatsUser {
    /** Дополнительный штраф к пробитию. */
    public int pierceDebuff = 1;
    /** Множитель урона пули. */
    public float damageDebuff = 0.95f;
    public AntiPierceWall(String name) {
        super(name);
    }

    private SnStats snStats;
    @Override
    public SnStats snStats() { return snStats; }
    @Override
    public void setStats() {
        super.setStats();
        snStats = new SnStats(stats);
        if(pierceDebuff > 0) snStats.addU("piercedebuff", "durability", pierceDebuff);
        if(damageDebuff < 1) snStats.addPercentU("piercedamagedebuff", "durability", 1-damageDebuff);
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
