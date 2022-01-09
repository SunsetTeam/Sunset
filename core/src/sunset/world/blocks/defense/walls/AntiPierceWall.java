package sunset.world.blocks.defense.walls;

import acontent.world.meta.AStats;
import mindustry.gen.Bullet;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.StatUnit;
import sunset.world.meta.SnStat;

/** Wall that reduces bullet penetration. */
public class AntiPierceWall extends Wall {
    /** Additional penalty to penetration. */
    public int pierceDebuff = 1;
    /** Bullet damage multiplier. */
    public float damageDebuff = 0.95f;
    private final AStats snStats=new AStats();
    public AntiPierceWall(String name) {
        super(name);
        stats=snStats.copy(stats);
    }
    @Override
    public void setStats() {
        super.setStats();

        if(pierceDebuff > 0) snStats.add(SnStat.pierceDebuff, pierceDebuff);
        if(damageDebuff < 1) snStats.add(SnStat.pierceDamageDebuff, (1f-damageDebuff)*100, StatUnit.percent);
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
