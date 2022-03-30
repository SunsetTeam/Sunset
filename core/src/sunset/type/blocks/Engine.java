package sunset.type.blocks;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mma.*;

public class Engine{
    public final String name;

    public float trailScale = 1f;
    public float size = 2.5f;
    public float x = 0f;
    public float y = -4f;
    public float xoffset = 0f;
    public float yoffset = 0f;
    public int innerAngle = 90;
    public boolean underUnit = false;
    public @Nullable Color color = null;
    public Color innerColor = Color.white;

    public Engine(String name){
        this.name = ModVars.fullName(name);
    }

    public void draw(Unit unit){
        if(!unit.isFlying()) return;

        float scale = unit.elevation;
        float z = Draw.z();
        if(underUnit) Draw.z(z - 0.001f);

        if(unit instanceof Trailc){
            Trail trail = ((Trailc)unit).trail();
            trail.draw(unit.team.color, (size + Mathf.absin(Time.time, 2f, size / 4f) * scale) * trailScale);
        }

        Draw.color(color == null ? unit.team.color : color);
        Fill.circle(
        unit.x + Angles.trnsx(unit.rotation - 90f, x, y),
        unit.y + Angles.trnsy(unit.rotation - 90f, x, y),
        (size + Mathf.absin(Time.time, 2f, size / 4f)) * scale
        );
        Draw.color(innerColor);
        Fill.circle(
        unit.x + Angles.trnsx(unit.rotation - innerAngle, x - xoffset, y - yoffset),
        unit.y + Angles.trnsy(unit.rotation - innerAngle, x - xoffset, y - yoffset),
        (size + Mathf.absin(Time.time, 2f, size / 4f)) / 2f * scale
        );
        Draw.color();
        Draw.z(z);
    }
}
