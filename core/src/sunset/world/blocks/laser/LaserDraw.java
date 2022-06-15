package sunset.world.blocks.laser;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import mma.type.pixmap.*;
import sunset.type.*;
import sunset.world.blocks.laser.LaserBlock.*;

/** Class used for drawing laser blocks. */
public class LaserDraw extends DrawBlock implements ImageDrawBlockGenerator{
    public TextureRegion base;
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

    public LaserBlock expectLaserBlock(Block block){
        if(!(block instanceof LaserBlock laserBlock)) throw new ClassCastException("This drawer requires the block to be a GenericCrafter. Use a different drawer.");
        return laserBlock;
    }

    @Override
    public void load(Block block){
        //override
        expectLaserBlock(block);
        allEdge = Core.atlas.find("" + block.name + "-all-edge");
        base = Core.atlas.find("" + block.name + "-base");
        lens = Core.atlas.find("" + block.name + "-lens");
        plugDark = Core.atlas.find("" + block.name + "-light-edge");
        plugLight = Core.atlas.find("" + block.name + "-dark-edge");
    }

    @Override
    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{allEdge};
    }

    protected void drawLenses(LaserBuild build, boolean left, boolean top, boolean right, boolean down){
        LaserBlock block = build.block();
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
        LaserBuild build = b.<LaserBuild>as();
        drawLenses(build, build.leftInput || build.leftOutput,
        build.topInput || build.topOutput,
        build.rightInput || build.rightOutput,
        build.downInput || build.downOutput);
    }

}
