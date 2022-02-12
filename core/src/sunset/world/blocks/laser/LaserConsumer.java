package sunset.world.blocks.laser;

public class LaserConsumer extends LaserNode{
    public LaserConsumer(String name) {
        super(name);
        consumesLaser = true;
        outputsLaser = false;
        configurable = false;
    }
    public class LaserConsumerBuild extends LaserNodeBuild{
        //consume everything for now
        @Override
        public float getLaserConsumption(){
            return laserModule.in;
        }
    }
}
