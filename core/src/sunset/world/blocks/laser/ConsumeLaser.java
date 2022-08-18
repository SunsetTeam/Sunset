package sunset.world.blocks.laser;

import arc.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;
import sunset.*;

/** Механика в разработке. Суть состоит в том, чтобы каждому блоку, который имеет этот ConsumeLaser, в HybridLaserBlockLogic назначить LaserModule, и, таким образом, сделать их де-факто лазерными блоками без LaserBuild, а далее обращаться к ним по мере надобности. */
public class ConsumeLaser extends Consume{
    public float consumption;
    public float heatLimit = 50f;
    public boolean heats = true;

    public ConsumeLaser(float consumption, boolean heats, float heatLimit){
        this.consumption = consumption;
        this.heats = heats;
        this.heatLimit = heatLimit;
    }

    protected ConsumeLaser(){
        consumption = 0f;
    }

    @Override
    public void apply(Block block){
        //todo maybe add LaserDraw to block here?...
        block.addBar("requiredLaserCharge", (Building b) -> {
            LaserModule lm = SnVars.logic.hybridLaserBlockLogic.laserModule(b);
            return new Bar(()-> {
                if(heats){
                    return Core.bundle.format("bar.laser-input-heat", lm.out, consumption, heatLimit);
                }
                else{
                    return Core.bundle.format("bar.laser-input", lm.out, consumption);
                }
                //return "Laser: " + lm.out + " / " + consumption;
            },
            ()-> {
                //less than need
                if(lm.out < consumption)
                    return Pal.gray;
                //optimal
                else if(lm.out == consumption || (!heats && lm.out > consumption))
                    return Pal.heal;
                //too high if heats
                else
                    return Pal.redSpark;
            },
            ()-> {
                return lm.out / consumption;
            });
        });
    }

    @Override
    public void display(Stats stats){
        //...
    }

    @Override
    public float efficiency(Building build){
        LaserModule lm = SnVars.logic.hybridLaserBlockLogic.laserModule(build);
        if(lm != null){
            float e = lm.out / consumption;
            return e;
        }
        return 0f;
    }
}
