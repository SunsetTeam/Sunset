package sunset.world.blocks.laser;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Log;
import mindustry.graphics.Layer;

/** Drawer only used for multikiln. */
public class LaserKilnDrawer extends LaserBlockDrawer{
    public TextureRegion flame;
    public Color[] heatColor = new Color[]{Color.darkGray, Color.red, Color.white};
    @Override
    public void load(LaserBlock block){
        flame = Core.atlas.find(block.name + "-flame");
    }

    @Override
    public void draw(LaserBlock.LaserBlockBuild b){
        super.draw(b);
        Draw.draw(Layer.effect - 0.001f, ()->{
            Draw.alpha(1f);
            //todo this is bad
            Draw.color(heatColor[0], heatColor[1], heatColor[2], b.warmup());
            Draw.rect(flame, b.x, b.y);
            Draw.reset();
        });
    }
}
