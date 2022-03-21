package sunset.world.blocks.effect;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.gen.Building;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;

public class Stand extends Block {
    public TextureRegion baseRegion;
    public float spinSpeed = 2;

    public Stand(String name) {
        super(name);
        size = 3;
        health = 120 * size * size;
        category = Category.effect;
        buildVisibility = BuildVisibility.shown;
        destructible = true;
    }
    @Override
    public void load() {
        super.load();
        baseRegion = Core.atlas.find("stand-base-" + size);
    }

    public class StandBuild extends Building {
        @Override
        public void draw() {
            Draw.rect(baseRegion, x, y);
            super.draw();
            //Draw.z(Layer.turret);
            //Drawf.spinSprite(region, x, y, spinSpeed);
        }
    }
}
