package sunset.type.blocks;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;

public class Rotor{
    public final String name;

    public float offsetX = 0f;
    public float offsetY = 0f;
    public float rotorRotateSpeed = 27;

    public int rotorCount = 1;
    public boolean underUnit = false;

    public float smokeChance = 0.2f;
    public float smokeX = 0f;
    public float smokeY = 0f;
    public Effect smokeEffect = Fx.fallSmoke;
    public Effect burningEffect = Fx.burning;

    public TextureRegion rotorRegion, topRegion, outlineRegion;

    public Rotor(String name){
        this.name = ("sunset-" + name);
    }

    public void load(){
        rotorRegion = Core.atlas.find(name);
        topRegion = Core.atlas.find(name + "-top");
        outlineRegion = Core.atlas.find(name + "-outline");
    }

    public void drawOutline(Unit unit, boolean drawTop){
        Vec2 rotor = Tmp.v1.trns(unit.rotation - 90, offsetX, offsetY).add(unit);

        float z = Draw.z();
        if(underUnit) Draw.z(z - 0.001f);

        for(int i = 0; i < rotorCount; i++){
            float angle = (i * 360f / rotorCount + (Time.time * rotorRotateSpeed) % 360);

            Draw.rect(outlineRegion, rotor.x, rotor.y, angle);
        }
        if(drawTop){
            Draw.rect(topRegion, rotor.x, rotor.y, unit.rotation - 90);
        }
        Draw.z(z);
    }

    public void draw(Unit unit){
        drawOutline(unit, true);
        Vec2 rotor = Tmp.v1.trns(unit.rotation - 90, offsetX, offsetY).add(unit);

        float z = Draw.z();
        if(underUnit) Draw.z(z - 0.001f);

        for(int i = 0; i < rotorCount; i++){
            float angle = (i * 360f / rotorCount + (Time.time * rotorRotateSpeed) % 360);
            Draw.rect(rotorRegion, rotor.x, rotor.y, angle);
        }
        if(unit.health <= 0 || unit.dead()){
            if(Mathf.chanceDelta(smokeChance)){
                float rotorSmokeX = rotor.x + Angles.trnsx(unit.rotation - 90, smokeX, smokeY);
                float rotorSmokeY = rotor.y + Angles.trnsy(unit.rotation - 90, smokeX, smokeY);
                smokeEffect.at(rotorSmokeX, rotorSmokeY);
                burningEffect.at(rotorSmokeX, rotorSmokeY);
            }
        }
        Draw.rect(topRegion, rotor.x, rotor.y, unit.rotation - 90);

        Draw.z(z);
    }
}
