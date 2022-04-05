package sunset.world.blocks.laser;

public class RotatableLaserNode extends LaserNode{
    public RotatableLaserNode(String name) {
        super(name);
    }
    public class RotatableLaserNodeBuild extends LaserNodeBuild{
        @Override
        public void updateTile(){
            super.updateTile();
            lasers.allLasers.each(laser -> laser.angle += edelta());
        }
    }
}