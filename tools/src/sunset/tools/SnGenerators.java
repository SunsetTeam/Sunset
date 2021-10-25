package sunset.tools;

import arc.func.Cons;
import arc.func.Func;
import arc.graphics.Color;
import arc.graphics.Pixmap;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import arc.struct.ObjectSet;
import arc.util.Log;
import arc.util.noise.Noise;
import arc.util.noise.Ridged;
import arc.util.noise.VoronoiNoise;
import mindustry.gen.Legsc;
import mindustry.gen.Mechc;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.type.Weapon;
import mma.tools.ModGenerators;
import sunset.type.MissileType;

import static mindustry.Vars.content;
import static mma.tools.gen.MindustryImagePacker.*;

public class SnGenerators extends ModGenerators {
    @Override
    protected void run() {
        super.run();
        generate("missileIcons", this::missileIcons);
    }

    protected void missileIcons() {
        for (MissileType type : MissileType.missileTypes) {
            TextureRegion rocketRegion = type.rocketRegion;
            if (!rocketRegion.found()) {
                Log.warn("Cannot find missileType region with name @", type.spriteName);
                continue;
            }
            Pixmap region = get(rocketRegion);
            int radius = type.outlineRadius + 1;
            Pixmap pixmap = new Pixmap(region.width + radius * 2, region.height + radius * 2);
            pixmap.draw(region, radius, radius);

            pixmap = pixmap.outline(type.outlineColor, type.outlineRadius);
            save(pixmap, type.spriteName + "-outline");
        }
    }
}
