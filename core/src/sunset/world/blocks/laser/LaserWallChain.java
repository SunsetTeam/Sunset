package sunset.world.blocks.laser;

import sunset.utils.*;
import sunset.world.blocks.laser.LaserWall.*;

//TODO add updateChain function with filter in Utils
public class LaserWallChain extends ChainOfBuilding<LaserWallBuild>{

    public LaserWallChain(LaserWallBuild build){
        super(build, building -> building instanceof LaserWallBuild);
    }

    public void updateCharge(){
        if(chainUpdater){
            float totalCharge = 0f;
            float chainSize = chained.size;
            for(LaserWallBuild w : chained){
                totalCharge += w.laser.out;
            }
            totalCharge /= chainSize;
            for(LaserWallBuild w : chained){
                w.additiveCharge = totalCharge;
            }
        }
    }
}
