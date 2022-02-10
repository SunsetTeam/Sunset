package sunset.world.blocks.laser;

public class LaserSource extends LaserNode{
    public final float sourceOut = 1f;
    public LaserSource(String name) {
        super(name);
        consumesLaser = false;
    }
    public class LaserSourceBuild extends LaserNodeBuild{
        @Override
        public float getLaserProduction(){
            return sourceOut;
        }
    }
}
