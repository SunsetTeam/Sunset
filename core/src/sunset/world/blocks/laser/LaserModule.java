package sunset.world.blocks.laser;

import arc.struct.Seq;
import arc.util.Log;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.entities.Damage;
import mindustry.io.TypeIO;
import sunset.world.blocks.laser.LaserBlock.LaserBlockBuild;

/** Generally, we summarize all laser charge from inputs and distribute it to outputs in equal parts
 * @author Liptonit */
public class LaserModule {
    /** links for block*/
    int maxLinks = 4;
    /** all input and output lasers */
    public Seq<LaserLink> input, output;
    /** Needs for saving */
    public boolean init = false;
    public float in = 0f, out = 0f;
    public LaserBlockBuild self;

    public LaserModule(LaserBlockBuild self){
        this.self = self;
        input = new Seq<>();
        output = new Seq<>();
    }

    public void init(){
        init = true;
        for (LaserLink l : input){
            if(l.build == null)
                l.build = (LaserBlockBuild) Vars.world.build(l.pos);
        }
        for (LaserLink l : output){
            if(l.build == null)
                l.build = (LaserBlockBuild) Vars.world.build(l.pos);
        }
    }

    public void update(){
        if(((LaserBlock)self.block).consumesLaser) {
            in = 0f;
            //get laser charge from all inputs
            for (LaserLink l : input) {
                LaserBlockBuild b = (LaserBlockBuild) Vars.world.build(l.pos);
                in += b.laserModule.out;
            }
        }

        if(((LaserBlock)self.block).outputsLaser){
            //consume
            in -= self.getLaserConsumption();
            //in no outputs mode we save output energy because block can't 'vaporize' light
            if (output.size > 0 || !(((LaserBlock)self.block).overheats))
                out = 0f;

            out += in;

            //produce
            // todo if lasers will have Laser amplifier, this will need a rework
            // todo out += self.getLaserProduction() * output.size; ???
            out += self.getLaserProduction();

            if (output.size > 0)
                out /= output.size;
        }

        if (((LaserBlock)self.block).overheats && out > ((LaserBlock)self.block).maxCharge){
            Damage.tileDamage(null, self.tileX(), self.tileY(), 1f, self.health);
        }
    }

    public void addInput(LaserBlockBuild build){
        LaserLink l = new LaserLink(build.pos());
        l.build = build;
        input.add(l);
    }

    public void removeInput(LaserBlockBuild build){
        input.remove(link -> {
            return link.build == build;
        });
    }

    public void addOutput(LaserBlockBuild build){
        LaserLink l = new LaserLink(build.pos());
        l.build = build;
        output.add(l);
    }

    public void removeOutput(LaserBlockBuild build){
        output.remove(link -> {
            return link.build == build;
        });
    }

    public boolean inputHas(LaserBlockBuild build){
        return input.contains(link -> {
            return link.build == build;
        });
    }

    public boolean outputHas(LaserBlockBuild build){
        return output.contains(link -> {
            return link.build == build;
        });
    }

    public void linkTo(LaserBlockBuild acceptor){
        //if linking block to input block
        if(acceptor.laserModule.outputHas(self)){
            acceptor.laserModule.removeOutput(self);
            acceptor.laserModule.addInput(self);
            removeInput(acceptor);
            addOutput(acceptor);
            return;
        }
        //if linking block again
        if(outputHas(acceptor)) {
            removeOutput(acceptor);
            acceptor.laserModule.removeInput(self);
            return;
        }
        //nothing other
        addOutput(acceptor);
        acceptor.laserModule.addInput(self);
    }

    public boolean checkFreeLinks(){
        return (input.size + output.size) < maxLinks;
    }

    //call this when destroy self
    public void clear(){
        for (LaserLink inp : input){
            inp.build.laserModule.removeOutput(self);
        }
        for (LaserLink outp : output){
            outp.build.laserModule.removeInput(self);
        }
        Log.info("removing, inputs: @, outputs: @", input.size, output.size);
        //throw null;
    }

    public void write(Writes w){
        Log.info("-----------\nWriting Laser module\nblockId: @", self.id);
        w.i(input.size);
        for (LaserLink l : input){
            LaserBlockBuild inp = l.build;
            w.i(inp.pos());
            Log.info("input: @", inp.pos());
        }
        w.i(output.size);
        for (LaserLink l : output){
            LaserBlockBuild outp = l.build;
            w.i(outp.pos());
            Log.info("output: @", outp.pos());
        }
    }

    public void read(Reads r){
        //inputs
        int inputSize = r.i();
        for (int i = 0; i < inputSize; i++){
            input.add(new LaserLink(r.i()));
        }
        //outputs
        int outputSize = r.i();
        for (int i = 0; i < outputSize; i++) {
            output.add(new LaserLink(r.i()));
        }
    }
}
