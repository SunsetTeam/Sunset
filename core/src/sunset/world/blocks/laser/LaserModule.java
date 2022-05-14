package sunset.world.blocks.laser;
import sunset.world.blocks.laser.LaserBlock.LaserBlockBuild;

/** Core class for laser mechanic.
 * Consumes or products laser energy. Splits it to some sides. */
public class LaserModule {
    public LaserBlockBuild self;
    public float in, out;
    public int outputs = 0;

    public LaserModule(LaserBlockBuild self){
        this.self = self;
        in = 0f;
        out = 0f;
    }

    public void update(){
        in -= self.getLaserConsumption();
        in += self.getLaserProduction();
        if(outputs == 0){
            //todo overheats, etc...
            out = in;
        }
        else{
            out = in / outputs;
        }
        in = 0f;
    }
}
