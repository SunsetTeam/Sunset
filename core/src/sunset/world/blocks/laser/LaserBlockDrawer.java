package sunset.world.blocks.laser;

import arc.graphics.g2d.*;
import mindustry.world.Block;

/** Class used for drawing laser blocks. */
public class LaserBlockDrawer{
    public void load(LaserBlock block){
        //override
    }

    public void drawBase(LaserBlock.LaserBlockBuild build){
        Draw.rect(build.block().base, build.x, build.y);
    }

    public void drawTop(LaserBlock.LaserBlockBuild build){
        Draw.rect(build.block().top, build.x, build.y);
    }

    public void drawLenses(LaserBlock.LaserBlockBuild build, boolean left, boolean top, boolean right, boolean down){
        build.block().lens.flip(false, true);
        if(left){
            Draw.rect(build.block().lens, build.x, build.y, 180);
        }else{
            Draw.rect(build.block().plugDark, build.x, build.y, 180);
        }
        if(top){
            Draw.rect(build.block().lens, build.x, build.y, 90);
        }else{
            Draw.rect(build.block().plugLight, build.x, build.y, 90);
        }
        build.block().lens.flip(false, true);
        if(right){
            Draw.rect(build.block().lens, build.x, build.y);
        }else{
            Draw.rect(build.block().plugLight, build.x, build.y);
        }
        if(down){
            Draw.rect(build.block().lens, build.x, build.y, 270);
        }else{
            Draw.rect(build.block().plugDark, build.x, build.y, 270);
        }
    }

    public void draw(LaserBlock.LaserBlockBuild build){
        drawBase(build);
        drawLenses(build, build.leftInput || build.leftOutput,
        build.topInput || build.topOutput,
        build.rightInput || build.rightOutput,
        build.downInput || build.downOutput);
        drawTop(build);
    }
}
