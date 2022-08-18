package sunset.world.blocks.laser;

import arc.util.io.*;
import mindustry.entities.Damage;
import mindustry.gen.*;
import mindustry.world.Tile;
import sunset.world.blocks.laser.LaserBlock.LaserBuild;

/** Core class for laser mechanic.
 * Consumes or produces laser energy. Splits it to some sides. */
public class LaserModule {
    private LaserBuild laserBuild;
    private Building hybridBuild;
    private ConsumeLaser hybridBuildC;
    /** Input and output. Used only for calculating. */
    public float in = 0f, out = 0f;
    /** 'Safe' analog of input. Used for drawing smth. */
    public float rawInput = in;
    public int outputs = 0;
    /** Side inputs.
     *  Used for drawing.
     *  Moved here to give hybrid-blocks ability to draw lenses.*/
    public boolean leftInput = false,
    topInput = false,
    rightInput = false,
    downInput = false;
    /** Side outputs.
     *  Used for drawing.
     *  Moved here to give hybrid-blocks ability to draw lenses.*/
    boolean topOutput = false,
    rightOutput = false,
    downOutput = false,
    leftOutput = false;

    public LaserModule(LaserBuild build){
        laserBuild = build;
    }

    public LaserModule(Building build, ConsumeLaser c){
        hybridBuild = build;
        hybridBuildC = c;
    }

    /** Update function used for usual <? extends LaserBuild> blocks.*/
    public void laserBuildUpdate(){
        LaserBuild build = laserBuild;
        rawInput = in;
        outputs = (leftOutput ? 1 : 0) + (topOutput ? 1 : 0) + (rightOutput ? 1 : 0) + (downOutput ? 1 : 0);
        if(build.block().inputsLaser){
            out = in;
        }
        else{
            out = 0f;
        }
        out -= build.block().laserConsumption;
        out += build.getLaserProduction();
        if(outputs != 0 && build.block().outputsLaser){
            out /= outputs;
        }
        if(build.block().heats && in > build.block().heatLimit){
            heatDamage(build);
        }
        in = 0f;
    }

    private void heatDamage(Building build){
        Tile t = build.tile;
        Damage.tileDamage(null, t.x, t.y, 1f, in * in);
    }

    /** Update function for hybrid-like builds ( like GenericCrafter with consume(new ConsumeLaser(...)); ). */
    public void hybridBuildUpdate(){
        Building build = hybridBuild;
        ConsumeLaser c = hybridBuildC;
        rawInput = in;
        out = in;
        if(outputs != 0){
            out /= outputs;
        }
        if(c.heats && in > c.heatLimit){
            heatDamage(build);
        }
        in = 0f;
    }

    public void configOutput(Integer value){
        rightOutput = (value & 0b0001) >> 0 == 1;
        topOutput = (value & 0b0010) >> 1 == 1;
        leftOutput = (value & 0b0100) >> 2 == 1;
        downOutput = (value & 0b1000) >> 3 == 1;
    }

    public void writeOutputs(Writes w){
        int state = 0;
        if(rightOutput) state += 1 << 0;
        if(topOutput) state += 1 << 1;
        if(leftOutput) state += 1 << 2;
        if(downOutput) state += 1 << 3;
        w.b(state);
    }

    public void readOutputs(Reads r, byte revision){
        if(revision == 0){
            leftOutput = r.bool();
            topOutput = r.bool();
            rightOutput = r.bool();
            downOutput = r.bool();
            return;
        }
        byte value = r.b();
        rightOutput = (value & 0b0001) >> 0 == 1;
        topOutput = (value & 0b0010) >> 1 == 1;
        leftOutput = (value & 0b0100) >> 2 == 1;
        downOutput = (value & 0b1000) >> 3 == 1;
    }
}