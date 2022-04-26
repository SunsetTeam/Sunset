package sunset.world.blocks.laser;

import arc.graphics.g2d.Draw;

public class LaserBlockDrawer {
    public LaserBlock.LaserBlockBuild build;

    public LaserBlockDrawer(LaserBlock.LaserBlockBuild self){
        this.build = self;
    }

    public void drawBase(){
        Draw.rect(build.block().base, build.x, build.y);
    }

    public void drawTop(){
        Draw.rect(build.block().top, build.x, build.y);
    }

    public void drawLenses(boolean left, boolean top, boolean right, boolean down){
        build.block().lens.flip(false, true);
        if(left){
            Draw.rect(build.block().lens, build.x, build.y, 180);
        }
        else{
            Draw.rect(build.block().plugDark, build.x, build.y, 180);
        }
        if(top){
            Draw.rect(build.block().lens, build.x, build.y, 90);
        }
        else{
            Draw.rect(build.block().plugLight, build.x, build.y, 90);
        }
        build.block().lens.flip(false, true);
        if(right){
            Draw.rect(build.block().lens, build.x, build.y);
        }
        else{
            Draw.rect(build.block().plugLight, build.x, build.y);
        }
        if(down){
            Draw.rect(build.block().lens, build.x, build.y, 270);
        }
        else{
            Draw.rect(build.block().plugDark, build.x, build.y, 270);
        }
    }

    public void draw(){
        boolean ll = build.leftInput || build.leftOutput,
                tl = build.topInput || build.topOutput,
                rl = build.rightInput || build.rightOutput,
                dl = build.downInput || build.downOutput;
        drawBase();
        drawLenses(ll, tl, rl, dl);
        drawTop();
    }
}
