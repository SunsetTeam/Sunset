package sunset.world.blocks.production.raw;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.annotations.Annotations.Load;
import mindustry.world.blocks.production.Drill;
import mma.ModVars;

/** Mod drill with optional second rotator.  2nd feature undone. */
public class ModDrill extends Drill {
    @Load("@-top")
    public TextureRegion top;
    @Load("@-rotator0")
    public TextureRegion secondRotator;
    @Load("@-bottom")
    public TextureRegion bottom;
    @Load("@-full")
    public TextureRegion full;
    public int m1;
    public int m2;
    public int addTile = 0;

    public ModDrill(String name) {
        super(name);
        m1 = 2;
        m2 = -2;
    }

    @Override
    public TextureRegion[] icons() {
        if (ModVars.packSprites) {
            return secondRotator.found() ?
                    new TextureRegion[]{bottom, rotatorRegion, secondRotator, region, top} :
                    new TextureRegion[]{bottom, rotatorRegion, region, top};
        }
        return new TextureRegion[] {full};
    }

    @Override
    public TextureRegion[] makeIconRegions() {
        return super.makeIconRegions();
    }

    public class ModDrillBuild extends DrillBuild {
        @Override
        public void draw() {
            Draw.rect(bottom, x, y);
            Draw.rect(rotatorRegion, x, y, timeDrilled * m1);
            if (secondRotator.found()) Draw.rect(secondRotator, x, y, timeDrilled * m2);
            Draw.rect(block.region, x, y);
            Draw.rect(top, x, y);
            if (dominantItem != null && drawMineItem) {
                Draw.color(dominantItem.color);
                Draw.rect(itemRegion, x, y);
                Draw.color();
            }
        }
    }
}
