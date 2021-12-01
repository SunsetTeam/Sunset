package sunset.world.draw;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import sunset.world.blocks.test.ModBlock;
import sunset.world.blocks.test.ModBlock.ModBuilding;

/**
 * Mod block for some tests.
 * */
public class DrawModBlock {

    public void draw(ModBuilding build){
        Draw.rect(build.block.region, build.x, build.y, build.block.rotate ? build.rotdeg() : 0);
    }

    public void drawLight(ModBuilding build){

    }

    public void load(ModBlock block){

    }

    public TextureRegion[] icons(ModBlock block){
        return new TextureRegion[]{block.region};
    }
}
