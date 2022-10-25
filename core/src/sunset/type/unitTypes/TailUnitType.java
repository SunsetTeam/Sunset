package sunset.type.unitTypes;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import sunset.utils.*;
import sunset.utils.TailPhysics.*;

public class TailUnitType extends UnitTypeExt{
    public final static UnitData.DataKey<TailJoint[]> jointsKey = UnitData.dataKey(null);
    public TailPhysics physics=new TailPhysics();

    public TailUnitType(String name){
        super(name);
    }

    public void tail(String sprite, float jx, float jy, float length){
        physics.tails.add(new TailSector(sprite, new Vec2(jx, jy), length));
    }

    @Override
    public void load(){
        super.load();
        physics.load();

    }

    @Override
    public void landed(Unit unit){
        super.landed(unit);
        physics.ensureJoints(jointsKey.get(unit),unit.x,unit.y,unit.rotation, true);
    }

    @Override
    public void update(Unit unit){
        super.update(unit);
        ;
        TailJoint[] js = physics.processPoints(jointsKey.get(unit),unit.x,unit.y,unit.rotation);
        UnitData.setData(unit, jointsKey, js);

        float angle = Mathf.atan2(js[1].x - js[0].x, js[1].y - js[0].y) * Mathf.radDeg;
        unit.rotation(angle + 180);
    }

    @Override
    public void drawBody(Unit unit){
        applyColor(unit);
        TailJoint[] joints = physics.ensureJoints(jointsKey.get(unit),unit.x,unit.y,unit.rotation, false);
        jointsKey.set(unit,joints);
        float z = !unit.isAdded() ? Draw.z() : unit.elevation > 0.5f ? (lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f);
        Draw.z(z - 0.01f);
        physics.eachJointWithAngle(joints,(index, angle)->{
            //degrees!!!
            physics.tails.get(index).draw(joints[index + 1], angle);//degrees!!!
        });
        Draw.z(z);

        Draw.reset();
    }

    @Override
    public void drawShadow(Unit unit){
        super.drawShadow(unit);

        TailJoint[] joints = physics.ensureJoints(jointsKey.get(unit), unit.x, unit.y, unit.rotation, false);
        jointsKey.set(
        unit,
        joints
        );
        physics.eachJointWithAngle(joints,(index, angle) -> {
            physics.tails.get(index).drawShadow(joints[index+1], angle);
        });
    }


}
