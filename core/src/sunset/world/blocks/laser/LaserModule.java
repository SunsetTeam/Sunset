package sunset.world.blocks.laser;
import mindustry.entities.Damage;
import mindustry.world.Tile;
import sunset.world.blocks.laser.LaserBlock.LaserBuild;

/** Core class for laser mechanic.
 * Consumes or produces laser energy. Splits it to some sides. */
public class LaserModule {
    public LaserBuild build;
    /** Input and output. Used only for calculating. */
    public float in = 0f, out = 0f;
    /** 'Safe' analog of input. Used for drawing smth. */
    public float rawInput = in;
    public int outputs = 0;

    public LaserModule(LaserBuild build){
        this.build = build;
    }

    public void update(){
        rawInput = in;
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
        if(build.block().heats && in > build.block().heatLaserLimit){
            Tile t = build.tile;
            Damage.tileDamage(null, t.x, t.y, 1f, in * in);
        }
        in = 0f;
    }
}
