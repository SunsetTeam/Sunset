package sunset.world.draw;

import arc.graphics.g2d.*;
import arc.struct.*;
import mindustry.world.*;
import mindustry.world.blocks.production.GenericCrafter.*;
import mindustry.world.draw.*;

public class MultiDrawBlock extends DrawBlock {
    Seq<DrawBlock> drawBlocks=new Seq<>();
    public MultiDrawBlock(DrawBlock iconProvider,DrawBlock... drawBlocks) {
        super();
        this.drawBlocks.add(iconProvider);
        this.drawBlocks.addAll(drawBlocks);
    }

    @Override
    public void draw(GenericCrafterBuild build) {
        super.draw(build);
        drawBlocks.each(d->d.draw(build));
    }

    @Override
    public void drawLight(GenericCrafterBuild build) {
        super.drawLight(build);
        drawBlocks.each(d->d.drawLight(build));
    }

    @Override
    public void load(Block block) {
        super.load(block);
        drawBlocks.each(d->d.load(block));
    }

    @Override
    public TextureRegion[] icons(Block block) {

        return drawBlocks.get(0).icons(block);
    }
}
