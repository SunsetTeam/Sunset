package sunset.world.blocks.defense.walls;

import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

/** Самовосстанавливающаяся стена. */
public class SelfhealWall extends Wall {
    public float heal = 0.1f;

    public SelfhealWall(String name) {
        super(name);
        update = true;
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.repairTime, health / heal / 60f, StatUnit.seconds);
    }

    public class SelfhealWallBuild extends WallBuild {
        @Override
        public void updateTile() {
            super.updateTile();
            heal(heal * delta());
        }
    }
}