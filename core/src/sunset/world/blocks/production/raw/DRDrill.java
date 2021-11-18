package sunset.world.blocks.production.raw;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.annotations.Annotations.Load;
import mindustry.world.Block;
import mindustry.world.blocks.production.Drill;

public class DRDrill extends Drill {
    @Load("@-item")
    public TextureRegion item;
    @Load("@-top")
    public TextureRegion top;
    @Load("@-rotator")
    public TextureRegion rotator;
    @Load("@-rotator0")
    public TextureRegion rotator0;
    @Load("@-bottom")
    public TextureRegion bottom;
    @Load("@-full")
    public TextureRegion full;
    public int m1;
    public int m2;

    public DRDrill(String name) {
        super(name);
        m1 = 2;
        m2 = -2;
    }

    public TextureRegion[] icons(Block block) {
        return new TextureRegion[] {full};
    }

    public class DRDBuild extends DrillBuild {
        @Override
        public void draw() {
            if (bottom.found()) Draw.rect(bottom, x, y);
            Draw.rect(block.region, x, y);
            Draw.rect(rotator, x, y, timeDrilled * m1);
            if (rotator0.found()) Draw.rect(rotator0, x, y, timeDrilled * m2);
            if (top.found()) Draw.rect(top, x, y);
        }
    }
}
