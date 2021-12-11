package sunset.tools;

import arc.graphics.Pixmap;
import arc.graphics.g2d.TextureRegion;
import arc.util.Log;
import mma.tools.ModGenerators;
import sunset.type.MissileType;

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
