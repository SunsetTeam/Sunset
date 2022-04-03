package sunset.world.blocks.laser;

import arc.graphics.g2d.Draw;

public class LaserBlockDrawer {
    public LaserBlock.LaserBlockBuild self = null;
    public boolean leftInput = false,
                topInput = false,
                rightInput = false,
                downInput = false;

    public LaserBlockDrawer(LaserBlock.LaserBlockBuild self){
        this.self = self;
    }

    public void drawBase(){
        Draw.rect(self.block().base, self.x, self.y);
    }

    public void drawTop(){
        Draw.rect(self.block().top, self.x, self.y);
    }

    public void drawPlugs(boolean left, boolean top, boolean right, boolean down){
        if(left){
            Draw.rect(self.block().plugDark, self.x, self.y, 180);
        }
        if(top){
            Draw.rect(self.block().plugLight, self.x, self.y, 90);
        }
        if(right){
            Draw.rect(self.block().plugLight, self.x, self.y);
        }
        if(down){
            Draw.rect(self.block().plugDark, self.x, self.y, 270);
        }
    }

    public void drawLenses(boolean left, boolean top, boolean right, boolean down){
        if(left){
            Draw.rect(self.block().lens, self.x, self.y, 180);
        }
        if(top){
            Draw.rect(self.block().lens, self.x, self.y, 90);
        }
        if(right){
            Draw.rect(self.block().lens, self.x, self.y);
        }
        if(down){
            Draw.rect(self.block().lens, self.x, self.y, 270);
        }
    }

    public void draw(){
        drawBase();
        drawPlugs(!leftInput, !topInput, !rightInput, !downInput);
        drawLenses(leftInput, topInput, rightInput, downInput);
        drawTop();
    }
}
