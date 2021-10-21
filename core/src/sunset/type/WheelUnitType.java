package sunset.type;

import arc.math.geom.Vec2;
import arc.util.Time;
import mindustry.gen.MechUnit;
import mindustry.gen.Mechc;
import mindustry.gen.Unit;
import sunset.ai.GroundUnitWeaponAI;

public class WheelUnitType extends UnitTypeExt {
    public WheelUnitType(String name) {
        super(name);
    }

    protected static final float speedScl = 20f;

    @Override
    public void init() {
        defaultController = GroundUnitWeaponAI::new;
        constructor = MechUnit::create;
        rotateShooting = false;
        super.init();
        mechSideSway = mechFrontSway = mechStepShake = mechStride = 0f;
        speed /= speedScl; // снижеам скорость, чтобы самостоятельно определять движение юнита
    }
    @Override
    public void setStats() {
        speed *= speedScl; // возвращаем старую скорость для корректного отображения харакеристик
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
    public void drawMech(Mechc mech) { } //не отрисовываем ноги
}
