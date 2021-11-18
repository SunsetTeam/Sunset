package sunset.world.draw;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.production.GenericCrafter.*;
import mindustry.world.draw.*;

public class DrawSurge extends DrawBlock {
    public Color flameColor = Color.valueOf("ffc999");
    public TextureRegion topRegion;

    public DrawSurge() {
    }

    public DrawSurge(Color flameColor) {
        this.flameColor = flameColor;
    }

    @Override
    public void drawLight(GenericCrafterBuild build) {
        super.drawLight(build);

            Drawf.light(build.team, build.x, build.y, (60f + Mathf.absin(10f, 5f)) * build.warmup * build.block.size, flameColor, 0.65f);

    }

    @Override
    public void draw(GenericCrafterBuild build) {
        float g = 0.3f;
        float r = 0.06f;
        float cr = Mathf.random(0.1f);

        Draw.alpha(((1f - g) + Mathf.absin(Time.time, 8f, g) + Mathf.random(r) - r) * build.warmup);

        Draw.tint(flameColor);
        Fill.circle( build.x,  build.y, 3f + Mathf.absin(Time.time, 5f, 2f) + cr);
        Draw.color(1f, 1f, 1f,  build.warmup);
        Draw.rect(topRegion, build.x, build.y);
        Fill.circle( build.x,  build.y, 1.9f + Mathf.absin(Time.time, 5f, 1f) + cr);
        Draw.color();
    }

    @Override
    public void load(Block block) {
        super.load(block);
        topRegion = Core.atlas.find(block.name + "-top");
    }
}
