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
        speed = range; //для корректной автонаводки
        lifetime = 1f;
    }

    // Используется, чтобы сохранить значения переменных во время задержки
    private Runnable runEffect(float x, float y, float rotation, float scale) {
        return () -> trailEffect.at(x, y, rotation, frontColor, scale);
    }
    @Override
    public void init(Bullet b) {
        super.init(b);
        int eIteration = 0;
        Vec2 basePoint = new Vec2(b.x + b.vel.x * Time.delta, b.y + b.vel.y * Time.delta); //сброс позиции для корректной обработки коллизий
        Vec2 curPoint = new Vec2(basePoint), //начальная точка и смещение для каждого нового trail'а
             eOffset = new Vec2(0, trailSpace).setAngle(b.rotation());
        float eLengthPassed = 0, eToPassed = 0; //расстояние, на котором уже отрисованы trail'ы и расстояние, до которого их нужно отрисовать
        float curDmgScale = 1, curSizeScale = 1; //текущие множители урона пули и размера trail'а
        Seq<Healthc> hitted = new Seq<>(); //уже повреждённые цели
        Healthc current = Utils.linecast(b, basePoint.x, basePoint.y, b.rotation(), range, (bb) -> true); //текущая цель
        while (current != null) {
            //обновляем размер trail'а и расстояние, до которого отрисовываем trail'ы
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
