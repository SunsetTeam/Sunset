package sunset.type;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.math.*;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.*;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;

public class CopterUnitType extends UnitType {
    public float offsetX = 0f;
    public float offsetY = 0f;
    public float rotorRotateSpeed = 28f;
    public float rotorDeathSlowdown = 0.01f;
    public float fallRotateSpeed = 2.5f;


    public int rotorCount = 1;
    
    float rotorSpeedScl;

    public TextureRegion rotorRegion;

    public CopterUnitType(String name) {
        super(name);
        
        constructor = UnitEntity::create;
        flying = lowAltitude = true;
        engineSize = 0f;
    }

    @Override
    public void load() {
        super.load();
        rotorRegion = Core.atlas.find(name + "-rotor");
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

            if(unit.dead() || unit.health() < 0f){
                unit.rotation(unit.rotation() + fallRotateSpeed * Mathf.signs[unit.id() % 2]);
                rotorSpeedScl = Mathf.lerpDelta(rotorSpeedScl, 0f, rotorDeathSlowdown);
            }
            }
        }
}