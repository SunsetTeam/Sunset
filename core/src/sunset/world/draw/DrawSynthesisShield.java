package sunset.world.draw;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.draw.*;
import sunset.world.blocks.defense.turrets.*;
import sunset.world.blocks.defense.turrets.SynthesisTurret.*;

public class DrawSynthesisShield extends DrawBlock{
    @Override
    public void draw(Building b){
        SynthesisBuild build = b.as();
        if(build.shieldAlpha > 0){
            float alpha = build.shieldAlpha;
            float radius = build.block.size * Vars.tilesize * 1.3f;
            Draw.z(Layer.blockOver);
            Fill.light(build.x, build.y, Lines.circleVertices(radius), radius, Tmp.c1.set(Pal.shield), Tmp.c2.set(Pal.shield).lerp(Color.white, Mathf.clamp(build.hitTime() / 2f)).a(Pal.shield.a * alpha));
            Draw.reset();
        }
    }
}
