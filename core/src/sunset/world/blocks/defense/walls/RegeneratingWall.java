package sunset.world.blocks.defense.walls;

import arc.util.Time;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

/** Self-healing wall. */
public class RegeneratingWall extends Wall {
    public float heal = 0.1f;

    public RegeneratingWall(String name) {
        super(name);
        update = true;
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.repairTime, health / heal / Time.toSeconds, StatUnit.seconds);
    }

    public class RegeneratingWallBuild extends WallBuild {
        @Override
        public void updateTile() {
            super.updateTile();
            heal(heal * delta());
        }
    }
}
