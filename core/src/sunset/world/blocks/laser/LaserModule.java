package sunset.world.blocks.laser;
import sunset.world.blocks.laser.LaserBlock.LaserBlockBuild;

/** Core class for laser mechanic.
 * Consumes or products laser energy. Splits it to some sides. */
public class LaserModule {
    public LaserBlockBuild build;
    public float in, out;
    public int outputs = 0;

    public LaserModule(LaserBlockBuild build){
        this.build = build;
        in = 0f;
        out = 0f;
    }

    public void update(){
        if(build.block().inputsLaser){
            out = in;
        }
        else{
            out = 0f;
        }
        out -= build.getLaserConsumption();
        out += build.getLaserProduction();
        if(outputs != 0 && build.block().outputsLaser){
            out /= outputs;
        }
        in = 0f;
    }
}
