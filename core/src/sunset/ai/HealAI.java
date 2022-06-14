package sunset.ai;

import arc.math.Mathf;
import mindustry.entities.Units;
import mindustry.entities.units.*;
import mindustry.gen.*;


/** AI that is chasing damaged allied units, if any. */
public class HealAI extends AIController{
    @Override
    public void updateMovement() {
        Unit u = Units.closest(unit.team, unit.x, unit.y,
                Float.MAX_VALUE, un -> un != unit && un.damaged(),
                (unit1, x, y) -> Mathf.pow(unit1.health / unit1.maxHealth, 2f));
        if(u != null) {
            moveTo(u, unit.range());
            return;
        }
        super.updateMovement();
    }

    protected void moveTo(Posc target, float length){
        if(target == null) return;
        vec.set(target).sub(unit);
        unit.rotation(vec.angle());
        float scl = vec.len() < length ? 0 : 1f;
        vec.setLength(unit.speed() * scl);
        unit.moveAt(vec);
    }
}