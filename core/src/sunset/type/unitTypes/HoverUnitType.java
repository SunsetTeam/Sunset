package sunset.type.unitTypes;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Layer;

public class HoverUnitType extends UnitTypeExt{
    public boolean drawHover = true;

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
        float z = unit.elevation > 0.1f ? (lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f);
        Draw.z(z);
        if(drawHover) drawHover(unit);
    }

    public void drawHover(Unit unit){
        Draw.rect(hoverRegion, unit.x, unit.y, unit.rotation - 90);
        Draw.reset();
    }
}
