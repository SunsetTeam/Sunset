package sunset.world.blocks.laser;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.util.Log;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import mma.type.pixmap.*;
import sunset.*;
import sunset.type.*;
import sunset.world.blocks.laser.LaserBlock.*;

/** Class used for drawing laser blocks. */
public class LaserDraw extends DrawBlock implements ImageDrawBlockGenerator{
    public TextureRegion bottom;
    public TextureRegion lens;
    public TextureRegion plugDark;
    public TextureRegion plugLight;
    public TextureRegion allEdge;

    @Override
    public void preGenerate(Block block, PixmapProcessor processor){
        Pixmap plugDark = processor.get(this.plugDark).copy();
        Pixmap plugLight = processor.get(this.plugLight).copy();

        Pixmap pixmap = new Pixmap(plugDark.width, plugDark.height);

        pixmap.draw(plugLight);
        PixmapProcessor.rotatePixmap(plugLight, 1);
        pixmap.draw(plugLight,true);

        PixmapProcessor.rotatePixmap(plugDark, 2);
        pixmap.draw(plugDark,true);
        PixmapProcessor.rotatePixmap(plugDark, 1);
        pixmap.draw(plugDark,true);

        processor.save(pixmap, block.name + "-all-edge");
//        Core.atlas.find()
    }

    @Override
    public void load(Block block){
        //override
        allEdge = Core.atlas.find(block.name + "-all-edge");
        bottom = Core.atlas.find(block.name + "-bottom");
        lens = Core.atlas.find(block.name + "-lens");
        plugDark = Core.atlas.find(block.name + "-dark-edge");
        plugLight = Core.atlas.find(block.name + "-light-edge");
    }

    @Override
    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{allEdge};
    }

    protected void drawLenses(Building build, LaserModule m){
        drawLenses(build, m.leftInput || m.leftOutput,
        m.topInput || m.topOutput,
        m.rightInput || m.rightOutput,
        m.downInput || m.downOutput);
    }

    protected void drawLenses(Building build, boolean left, boolean top, boolean right, boolean down){
        lens.flip(false, true);
        if(left){
            Draw.rect(lens, build.x, build.y, 180);
        }else{
            Draw.rect(plugDark, build.x, build.y, 180);
        }
        if(top){
            Draw.rect(lens, build.x, build.y, 90);
        }else{
            Draw.rect(plugLight, build.x, build.y, 90);
        }
        lens.flip(false, true);
        if(right){
            Draw.rect(lens, build.x, build.y);
        }else{
            Draw.rect(plugLight, build.x, build.y);
        }
        if(down){
            Draw.rect(lens, build.x, build.y, 270);
        }else{
            Draw.rect(plugDark, build.x, build.y, 270);
        }
    }

    @Override
    public void draw(Building b){
        if(b instanceof LaserBuild build)
            drawLenses(build, build.laser);
        else {
            LaserModule laser = SnVars.logic.hybridLaserBlockLogic.hybridBuildings.get(b);
            if(laser != null){
                drawLenses(b, laser);
            }
        }
    }
}
