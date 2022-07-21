package sunset.world.blocks.laser;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import sunset.world.blocks.laser.LaserBlock.*;

/** Drawer only used for multikiln. */
public class LaserKilnDrawer extends LaserDraw{
    public TextureRegion flame;
    public Color startColor = Pal.darkerGray,
            midColor = Color.red,
            endColor = Color.white;
/*
    @Override
    public void load(LaserBlock block){
        flame = Core.atlas.find(block.name + "-flame");
    }

    @Override
    public void draw(LaserBuild b){
        super.draw(b);
        Draw.draw(Layer.effect - 0.001f, ()->{
            Draw.alpha(1f);
            Draw.color(startColor, midColor, endColor, b.warmup());
            Draw.rect(flame, b.x, b.y);
            Draw.reset();
        });
    }*/
}
