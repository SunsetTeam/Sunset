package sunset.world.blocks.production.crafting;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import mindustry.annotations.Annotations.Load;
import mindustry.graphics.Drawf;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
import mma.ModVars;

public class ModGenericCrafter extends GenericCrafter {
    @Load("@-rotator")
    public TextureRegion rotator;
    @Load("@-liquid")
    public TextureRegion liquid;
    @Load("@-bottom")
    public TextureRegion bottom;
    public ModGenericCrafter(String name) {
        super(name);
    }
    
    @Override
        public TextureRegion[] icons() {
            return new TextureRegion[] {bottom, rotator, bottom};
        }

        public class ModGenericCrafterBuild extends GenericCrafterBuild{
        public void draw(ModGenericCrafterBuild entity) {
            Draw.rect(bottom, entity.x, entity.y);
            Drawf.liquid(liquid, entity.x, entity.y, liquids.total() / liquidCapacity, liquids.current().color);
            Draw.rect(rotator, entity.x, entity.y, Interp.pow3In.apply(progress));
            Draw.rect(bottom, entity.x, entity.y);
        }
    }
}
