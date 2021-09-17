package sunset.type;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;

public class CopterUnitType extends UnitType {
    public float offsetX = 0f;
    public float offsetY = 0f;
    public float rotorRotateSpeed = 28f;

    public float unitFallRotateSpeed = 6f;

    public int rotorCount = 1;

    public TextureRegion rotorRegion;

    public CopterUnitType(String name) {
        super(name);
        
        constructor = UnitEntity::create;
        flying = lowAltitude = true;
        fallSpeed = 0.005f;
        engineSize = 0f;
    }

    @Override
    public void load() {
        super.load();
        rotorRegion = Core.atlas.find(name + "-rotor");
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
    public void draw(Unit unit) {
        Draw.z(Layer.flyingUnit + 0.01f);
        drawRotor(unit);

        Draw.z(Layer.flyingUnit);
        super.draw(unit);
    }

    public void drawRotor(Unit unit){
        if(!unit.isFlying()) return;

        float rotorx = unit.x + Angles.trnsx(unit.rotation - 90, offsetX, offsetY);
        float rotory = unit.y + Angles.trnsy(unit.rotation - 90, offsetX, offsetY);

        for (int i = 0; i < rotorCount; i++){
            Draw.rect(rotorRegion,  rotorx,  rotory, Time.time * rotorRotateSpeed);
        }
    }
}
