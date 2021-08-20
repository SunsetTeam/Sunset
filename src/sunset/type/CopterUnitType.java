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
    public float rotorDeathSpeed = 0f;
    public float rotorDeathSlowdown = 0.01f;

    public float unitFallRotateSpeed = 1f;


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
    public void update(Unit unit) {
        unitFallRotateSpeed = 1f;
        super.update(unit);
        if(unit.health <= 0 || unit.dead()) {
            unit.rotation += Time.delta * (fallSpeed * 1000);
            unitFallRotateSpeed = 0.4f;
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

            if(unit.dead() || unit.health() < 0f){
            rotorDeathSpeed = Mathf.lerpDelta(rotorRotateSpeed, 0f, rotorDeathSlowdown);

                }else{
            rotorDeathSpeed = Mathf.lerpDelta(rotorSpeedScl, 1f, type.rotorDeathSlowdown);
            }
        }
        }
    }
}