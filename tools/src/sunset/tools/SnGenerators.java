package sunset.tools;

import arc.files.Fi;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.Pixmap;
import arc.graphics.g2d.TextureRegion;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.gen.ContentRegions;
import mindustry.graphics.Pal;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OverlayFloor;
import mma.tools.ModGenerators;
import mma.tools.ModImagePacker;
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
            TextureRegion region = type.rocketRegion;
            if (!region.found()){
                Log.warn("Cannot find missileType region with name @",type.spriteName);
                continue;
            }
            Pixmap pixmap=get(region);
            pixmap.outline(type.outlineColor,type.outlineRadius);
            save(pixmap,type.spriteName+"-outline");
        }
    }
}
