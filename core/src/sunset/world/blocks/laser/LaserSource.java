package sunset.world.blocks.laser;

/** Laser source. Outputs any laser energy you want. */
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
