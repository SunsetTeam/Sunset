package sunset.world.blocks.laser;

import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Tile;

public class RotatableLaserNode extends LaserNode{
    public RotatableLaserNode(String name) {
        super(name);
    }
    public class RotatableLaserNodeBuild extends LaserNodeBuild{
        public Building init(Tile tile, Team team, boolean shouldAdd, int rotation){
            Building b = super.init(tile, team, shouldAdd, rotation);
            laser.outputs = 4;
            return b;
        }
        @Override
        public void updateTile(){
            lasers.updateTile();
            lasers.allLasers.each(laser -> laser.angle += edelta());
        }
        @Override
        public float getLaserProduction(){
            return 1f;
        }
    }
}