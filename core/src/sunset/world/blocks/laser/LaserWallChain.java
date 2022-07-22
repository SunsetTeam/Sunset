package sunset.world.blocks.laser;

import arc.struct.*;
import arc.util.*;
import sunset.world.blocks.laser.LaserWall.*;
//TODO add updateChain function with filter in Utils
public class LaserWallChain{
    public LaserWallBuild build;
    public boolean chainUpdater = true;
    Seq<LaserWallBuild> chainedWalls = new Seq<>();

    public LaserWallChain(LaserWallBuild build){
        this.build = build;
    }

    public void updateChain(){
        chainedWalls = new Seq<>();
        var queue = build.block().queue;
        queue.clear();
        queue.add(build);

        while(!queue.isEmpty()){
            var next = queue.removeLast();
            chainedWalls.add(next);

            for(var b : next.proximity){
                if(b instanceof LaserWallBuild w && w.laserChain.chainedWalls != chainedWalls){
                    Log.info("      accepted: @", build.id);
                    w.laserChain.chainedWalls = chainedWalls;
                    queue.addFirst(w);
                }
            }
        }
        chainUpdater = true;
        chainedWalls.each(w -> {
            if(w != build && w.laserChain.chainUpdater)
                chainUpdater = false;
        });
    }

    public void updateCharge(){
        if(!chainUpdater)
            return;
        float totalCharge = 0f;
        float chainSize = chainedWalls.size;
        for(LaserWallBuild w : chainedWalls){
            totalCharge += w.laser.out;
        }
        totalCharge /= chainSize;
        for(LaserWallBuild w : chainedWalls){
            w.additiveCharge = totalCharge;
        }
    }
}
