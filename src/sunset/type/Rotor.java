package sunset.type;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.util.Time;
import mindustry.gen.Unit;

public class Rotor {
    public final String name;

    public float offsetX = 0f;
    public float offsetY = 0f;
    public float rotorRotateSpeed = 27;

    public int rotorCount = 4;

    public TextureRegion rotorRegion, topRegion;

    public Rotor(String name){
        this.name = name;
    }

    public void load() {
        rotorRegion = Core.atlas.find(name);
        topRegion = Core.atlas.find(name + "-top");
    }
    
    public void draw(Unit unit){

    float rotorx = unit.x + Angles.trnsx(unit.rotation - 90, offsetX, offsetY);
    float rotory = unit.y + Angles.trnsy(unit.rotation - 90, offsetX, offsetY);

    for (int i = 0; i < rotorCount; i++){
        Draw.rect(rotorRegion,  rotorx,  rotory, Time.time * rotorRotateSpeed);
    }
    Draw.rect(topRegion, rotorx, rotory, unit.rotation - 90);
    }
}
