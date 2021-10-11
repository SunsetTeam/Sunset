package sunset.type;

import arc.graphics.g2d.Draw;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;

public class CopterUnitType extends UnitType {
    public final Seq<Rotor> rotors = new Seq<>();
    public float unitFallRotateSpeed = 6f;

    public CopterUnitType(String name) {
        super(name);
        
        constructor = UnitEntity::create;
        flying = lowAltitude = true;
        fallSpeed = 0.005f;
        engineSize = 0f;
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);
        if(unit.health <= 0 || unit.dead()) {
            unit.rotation += Time.delta *(fallSpeed * 2000);
            unit.rotation = Time.time * unitFallRotateSpeed;
        }
    }

    @Override
    public void draw(Unit unit){
        super.draw(unit);
        drawRotor(unit);
    }

    public void drawRotor(Unit unit){
        applyColor(unit);
        rotors.each(rotor -> rotor.draw(unit));
        Draw.reset();
    }

    @Override
    public void load(){
        super.load();
        rotors.each(Rotor::load);
    }
}