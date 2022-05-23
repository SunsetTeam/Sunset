package sunset.world.draw;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.blocks.production.GenericCrafter.*;
import mindustry.world.draw.*;

public class DrawAngleRotator extends DrawBlock{
    protected final float angle;
    protected TextureRegion rotatorRegion;
    protected TextureRegion top;

    public DrawAngleRotator(float angle){
        this.angle = angle;
    }

    @Override
    public void draw(Building b){
        GenericCrafterBuild entity = b.as();
        Draw.rect(entity.block.region, entity.x, entity.y);
        Draw.rect(rotatorRegion, entity.x, entity.y, angle * Interp.pow3Out.apply(entity.progress));
        Draw.rect(top, entity.x, entity.y);
    }

    @Override
    public void load(Block block){
        expectCrafter(block);
        rotatorRegion = Core.atlas.find(block.name + "-rotator");
        top = Core.atlas.find(block.name + "-top");
    }

    @Override
    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{block.region, rotatorRegion, top};
    }
}