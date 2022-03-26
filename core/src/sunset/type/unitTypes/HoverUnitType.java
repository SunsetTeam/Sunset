package sunset.type.unitTypes;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;

public class HoverUnitType extends UnitTypeExt{
    public boolean drawHover = true;
    public Color hoverBloomColor = Pal.engine;
    public float alpha = 0.5f;

    public TextureRegion hoverRegion;

    public void load() {
        hoverRegion = Core.atlas.find(name + "-hover");
    }

    public HoverUnitType(String name){
        super(name);

        lowAltitude = true;
        constructor = UnitEntity::create;
    }

    public void draw(Unit unit){
        super.draw(unit);
        float z = unit.elevation > 0.1f ? (lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f);
        Draw.z(z);
        if(drawHover) drawHover(unit);
        Draw.reset();
    }

    public void drawHover(Unit unit){
        Draw.rect(hoverRegion, unit.x, unit.y, unit.rotation - 90);
        if(Core.settings.getBool("bloom")){
            Draw.rect(hoverRegion, unit.x, unit.y, unit.rotation);
        } else {
            Draw.blend(Blending.additive);
            Draw.color(hoverBloomColor, alpha);
            Draw.rect(hoverRegion, unit.x, unit.y, unit.rotation);
            Draw.blend();
        }
        Draw.reset();
    }
}
