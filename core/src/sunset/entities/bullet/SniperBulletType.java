package sunset.entities.bullet;

import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.entities.Damage;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Healthc;
import sunset.utils.Utils;

public class SniperBulletType extends BasicBulletType {
    public float trailDelay = 3f;
    public float trailSpace = 32f;
    public float range = 0f;
    public float pierceDamageMultiplier = 0.9f, pierceSizeMultiplier = 0.95f;
    public SniperBulletType() { super(); }

    @Override
    public void init() {
        super.init();
        speed = range; //for correct auto aiming
        lifetime = 1f;
    }

    // Used to store the values ​​of variables during a delay
    private Runnable runEffect(float x, float y, float rotation, float scale) {
        return () -> trailEffect.at(x, y, rotation, frontColor, scale);
    }
    @Override
    public void init(Bullet b) {
        super.init(b);
        int eIteration = 0;
        Vec2 basePoint = new Vec2(b.x + b.vel.x * Time.delta, b.y + b.vel.y * Time.delta); //reset position for correct collision handling
        Vec2 curPoint = new Vec2(basePoint), //starting point and offset for each new trail
                eOffset = new Vec2(0, trailSpace).setAngle(b.rotation());
        float eLengthPassed = 0, eToPassed = 0; //the distance at which the trails are already drawn and the distance to which they need to be drawn
        float curDmgScale = 1, curSizeScale = 1; //current bullet damage and trail size multipliers
        Seq<Healthc> hitted = new Seq<>(); //already damaged targets
        Healthc current = Utils.linecast(b, basePoint.x, basePoint.y, b.rotation(), range, (bb) -> true); //current goal
        while (current != null) {
            //update the size of the trail and the distance to which we draw the trails
            eToPassed = current.dst(basePoint);
            //отрисовка
            while (eLengthPassed < eToPassed) {
                Time.run(trailDelay*eIteration, runEffect(curPoint.x, curPoint.y, b.rotation(), curSizeScale));
                curPoint.add(eOffset);
                eLengthPassed += trailSpace * curSizeScale;
                eIteration++;
            }
            //урон
            current.damage(damage * curDmgScale * (current instanceof Building ? buildingDamageMultiplier : 1));
            for(int i = 0; i < fragBullets; i++) {
                float len = Mathf.random(fragVelocityMin, fragVelocityMax);
                float a = b.rotation() + Mathf.range(fragCone / 2);
                float fragDamage = damage/fragBullets*curDmgScale*(1-pierceDamageMultiplier);
                Time.run(trailDelay * eIteration, runEffect(current.x(), current.y(), a, curSizeScale*len));
                fragBullet.create(b, b.team, current.x(), current.y(), a, fragDamage, len,1, null);
            }
            //обновляем данные
            curDmgScale *= pierceDamageMultiplier;
            curSizeScale *= pierceSizeMultiplier;
            eOffset.scl(pierceSizeMultiplier);
            hitted.add(current);
            current = Utils.linecast(b, basePoint.x, basePoint.y, b.rotation(), range, (bb) -> hitted.indexOf(bb) < 0);
        }
        eToPassed = range;
        while (eLengthPassed < eToPassed) {
            Time.run(trailDelay*eIteration, runEffect(curPoint.x, curPoint.y, b.rotation(), curSizeScale));
            curPoint.add(eOffset);
            eLengthPassed += trailSpace * curSizeScale;
            eIteration++;
        }
        b.remove();
    }
}
