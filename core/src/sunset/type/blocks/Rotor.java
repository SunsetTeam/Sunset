package sunset.type.blocks;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.gen.Unit;
import mma.ModVars;

public class Rotor {
    public final String name;

    public float offsetX = 0f;
    public float offsetY = 0f;
    public float rotorRotateSpeed = 27;

    public int rotorCount = 1;
    public boolean underUnit=false;

    public float smokeChance = 0.3f;
    public float smokeX = 0f;
    public float smokeY = 0f;

    public TextureRegion rotorRegion, topRegion, outlineRegion;

    public Rotor(String name) {
        this.name = ModVars.fullName(name);
    }

    public void load() {
        rotorRegion = Core.atlas.find(name);
        topRegion = Core.atlas.find(name + "-top");
        outlineRegion = Core.atlas.find(name + "-outline");
    }

    public void drawOutline(Unit unit,boolean drawTop) {
        Vec2 rotor = Tmp.v1.trns(unit.rotation - 90, offsetX, offsetY).add(unit);
        float rotorX = offsetX + Angles.trnsx(unit.rotation - 90, smokeX, smokeY);
        float rotorY = offsetY + Angles.trnsy(unit.rotation - 90, smokeX, smokeY);

        float z = Draw.z();
        if (underUnit)Draw.z(z-0.001f);

        for (int i = 0; i < rotorCount; i++) {
            float angle = (i * 360f / rotorCount + (Time.time * rotorRotateSpeed) % 360);

            Draw.rect(outlineRegion, rotor.x, rotor.y, angle);
        }
        if (drawTop){
            Draw.rect(topRegion, rotor.x, rotor.y, unit.rotation - 90);
        }
        if(unit.health <= 0 || unit.dead()) {
            if (Mathf.chanceDelta(smokeChance)) {
                Fx.fallSmoke.at(rotorX, rotorY);
                Fx.burning.at(rotorX, rotorY);
            }
        }
        Draw.z(z);
    }

    public void draw(Unit unit) {
        drawOutline(unit,true);

        Vec2 rotor = Tmp.v1.trns(unit.rotation - 90, offsetX, offsetY).add(unit);

        float z = Draw.z();
        if (underUnit)Draw.z(z-0.001f);

        for (int i = 0; i < rotorCount; i++) {
            float angle = (i * 360f / rotorCount + (Time.time * rotorRotateSpeed) % 360);
            Draw.rect(rotorRegion, rotor.x, rotor.y, angle);
        }
        Draw.rect(topRegion, rotor.x, rotor.y, unit.rotation - 90);

        Draw.z(z);
    }
}
