package sunset.world.draw;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.production.GenericCrafter.*;
import mindustry.world.draw.*;

public class DrawModWeave extends DrawBlock{
    protected TextureRegion bottom;
    protected TextureRegion weave;
    protected TextureRegion top;


    @Override
    public void draw(Building b){
        GenericCrafterBuild entity = b.as();
        Draw.rect(bottom, entity.x, entity.y);
        Draw.rect(weave, entity.x, entity.y, entity.totalProgress);
        Draw.color(Pal.accent);
        Draw.alpha(entity.warmup);
        Lines.lineAngleCenter(entity.x + Mathf.sin(entity.totalProgress, 6f, Vars.tilesize / 3f * entity.block.size), entity.y, 90, entity.block.size * Vars.tilesize / 2f);
        Draw.reset();
        Draw.rect(entity.block.region, entity.x, entity.y);
        Draw.rect(top, entity.x, entity.y);
    }

    @Override
    public void load(Block block){
        expectCrafter(block);
        bottom = Core.atlas.find(block.name + "-bottom");
        weave = Core.atlas.find(block.name + "-weave");
        top = Core.atlas.find(block.name + "-top");
    }

    @Override
    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{bottom, block.region, weave, top};
    }
}