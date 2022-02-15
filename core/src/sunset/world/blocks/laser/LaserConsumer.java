package sunset.world.blocks.laser;


/** Consumes laser. For now, it consumes everything. */
public class LaserConsumer extends LaserNode{
    public LaserConsumer(String name) {
        super(name);
        laserConsumption = 20f;
        consumesLaser = true;
        outputsLaser = false;
        configurable = false;
        overheats = false;
    }

    @Override
    public void setBars(){
        super.setBars();
        bars.remove("laser-energy");
    }

    public class LaserConsumerBuild extends LaserNodeBuild{
        //consume everything for now
        @Override
        public float getLaserConsumption(){
            return laserModule.in;
        }
    }
}
