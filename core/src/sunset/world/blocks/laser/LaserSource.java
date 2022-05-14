package sunset.world.blocks.laser;

/** Laser source. Very raw*/
public class LaserSource extends LaserNode{
    public LaserSource(String name) {
        super(name);
    }
    public class LaserSourceBuild extends LaserNodeBuild{
        @Override
        public float getLaserProduction(){
            return 5f;
        }
    }
}
