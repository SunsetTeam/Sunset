package sunset.type.unitTypes;

import arc.math.geom.Vec2;
import arc.util.Time;
import mindustry.gen.MechUnit;
import mindustry.gen.Mechc;
import mindustry.gen.Unit;
import sunset.ai.*;
import sunset.gen.*;

public class WheelUnitType extends SnUnitType {
    public WheelUnitType(String name) {
        super(name);
    }

    protected static final float speedScl = 20f;

    @Override
    public void init() {
        defaultController = GroundWeaponAI::new;
        constructor = MechUnit::create;
        rotateShooting = false;
        super.init();
        mechSideSway = mechFrontSway = mechStepShake = mechStride = 0f;
        speed /= speedScl; // lower the speed to independently determine the movement of the unit
    }
    @Override
    public void setStats() {
        speed *= speedScl; // revert to old speed for digital display of characteristics
        super.setStats();
        speed /= speedScl;
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);
        Vec2 vel = unit.vel.cpy();
        unit.move(-vel.x * Time.delta, -vel.y * Time.delta);
        vel.setAngle(unit.rotation).scl(speedScl);
        unit.move(vel.x * Time.delta, vel.y * Time.delta);
    }
    @Override
    public void drawMech(Mechc mech) { } //do not draw legs

}
