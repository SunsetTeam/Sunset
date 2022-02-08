package sunset.type.blocks;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Nullable;
import arc.util.Time;
import mindustry.gen.Trailc;
import mindustry.gen.Unit;
import mindustry.graphics.Trail;
import mma.ModVars;

public class Engine {
    public final String name;

    public float trailScl = 1f;
    public float engineSize = 2.5f;
    public float engineX = 0f;
    public float engineY = -4f;
    public float engineAngle = 180f;
    public @Nullable
    Color engineColor = null;
    public Color engineColorInner = Color.white;

    public Engine(String name) {
        this.name = ModVars.fullName(name);
    }

    public void draw(Unit unit){
        if(!unit.isFlying()) return;

        float scale = unit.elevation;

        if(unit instanceof Trailc){
            Trail trail = ((Trailc)unit).trail();
            trail.draw(unit.team.color, (engineSize + Mathf.absin(Time.time, 2f, engineSize / 4f) * scale) * trailScl);
        }

        Draw.color(engineColor == null ? unit.team.color : engineColor);
        Fill.circle(
                unit.x + Angles.trnsx(unit.rotation - 90f, engineX, engineY),
                unit.y + Angles.trnsy(unit.rotation - 90f, engineX, engineY),
                (engineSize + Mathf.absin(Time.time, 2f, engineSize / 4f)) * scale
        );
        Draw.color(engineColorInner);
        Fill.circle(
                unit.x + Angles.trnsx(unit.rotation - engineAngle, engineX  - 1f, engineY - 1f),
                unit.y + Angles.trnsy(unit.rotation - engineAngle, engineX  - 1f, engineY - 1f),
                (engineSize + Mathf.absin(Time.time, 2f, engineSize / 4f)) / 2f  * scale
        );
        Draw.color();
    }
}
