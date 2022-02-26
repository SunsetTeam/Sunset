package sunset.type.unitTypes;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Layer;
import sunset.type.blocks.Engine;

public class EngineUnitType extends SnUnitType{
    public final Seq<Engine> engines = new Seq<>();

    public EngineUnitType(String name){
        super(name);

        constructor = UnitEntity::create;
    }

    public void engines(Engine... engines){
        this.engines.set(engines);
    }

    public void draw(Unit unit){
        super.draw(unit);
        //float z = unit.elevation > 0.5f ? (lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f);
        //Draw.z(z);
        if(engineSize > 0) drawEngine(unit);
        Draw.reset();
    }

    @Override
    public void drawEngine(Unit unit){
        engines.each(engine -> engine.draw(unit));
        Draw.reset();
    }
}
