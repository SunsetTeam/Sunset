package sunset.type.unitTypes;

import arc.math.geom.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import sunset.ai.*;

public class WheelUnitType extends SnUnitType{
    protected static final float speedScl = 20f;

    public WheelUnitType(String name){
        super(name);
    }

    @Override
    public void init(){
        controller = u -> new AIController();
        constructor = MechUnit::create;
        faceTarget = false;
        super.init();
        mechSideSway = mechFrontSway = stepShake = mechStride = 0f;
        speed /= speedScl; // lower the speed to independently determine the movement of the unit
    }

    @Override
    public void setStats(){
        speed *= speedScl; // revert to old speed for digital display of characteristics
        super.setStats();
        speed /= speedScl;
    }

    @Override
    public void update(Unit unit){
        super.update(unit);
        Vec2 vel = unit.vel.cpy();
        unit.move(-vel.x * Time.delta, -vel.y * Time.delta);
        vel.setAngle(unit.rotation).scl(speedScl);
        unit.move(vel.x * Time.delta, vel.y * Time.delta);
    }

    @Override
    public void drawMech(Mechc mech){
    } //do not draw legs

}
