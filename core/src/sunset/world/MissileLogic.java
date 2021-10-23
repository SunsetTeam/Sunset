package sunset.world;

import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Scaled;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.game.EventType;
import mindustry.graphics.Layer;
import sunset.content.SnFx;

public class MissileLogic {
    public static final Seq<Missile> missiles = new Seq<>(false);

    public static void init() {
        Events.run(EventType.Trigger.update, MissileLogic::update);
        Events.run(EventType.Trigger.draw, MissileLogic::draw);
    }

    public static void draw() {
        float z = Draw.z();
        Draw.z(Layer.weather); // ракеты в облаках, наверное.
        missiles.each((m) -> {
            float f = 2 - Mathf.pow(1 - m.fslope(), 2);
            Draw.rect(m.type.rocketRegion, m.current.x, m.current.y,
                    m.type.rocketRegion.width * f / 4f, m.type.rocketRegion.height * f / 4f);
        });
        Draw.z(z);
    }

    static long lastTime = 0;
    public static void update() {
        float secs = (Time.millis() - lastTime) / 1000f * 60f;
        if(!Vars.state.isPaused()) {
            missiles.each((m) -> m.update(secs));
        }
        lastTime = Time.millis();
    }

    /** Описывает ракету в полёте.
     * @apiNote Реализация {@link arc.math.Scaled} предосавляет информацию о части пройденного пути. */
    public static class Missile implements Scaled {
        public final MissileType type;
        public final Vec2 from, to,
                current=new Vec2(), speed=new Vec2()
                ;
        public float flightLength, flightTime;
//        public final long launchTime;
        public Missile(MissileType type, Vec2 from, Vec2 to) {
//            launchTime = Time.millis();
            this.type = type;
            this.from = from;
            this.to = to;
            this.current.set(from);
            speed.set(to).sub(from);
            flightLength = speed.len();
            flightTime = flightLength / type.speed;
            speed.setLength(type.speed);
            missiles.add(this);
            new Effect(flightTime, type.markEffect.renderer).at(to.x, to.y, type.splashDamageRadius, Color.red);
        }

        public void update(float time) {
            current.add(Tmp.v1.set(speed).scl(time));
            if(fout() <= 0f) {
                type.explode(to);
                missiles.remove(this);
            }
        }

        @Override
        public float fin() {
            return (from.dst(current)) / flightLength;
        }
        // Необходимо для обхода бага AbstractMethodError на Android, когда
        // runtime игнорирует реализацию методов по умолчанию в интерфейсах.
        // region Обход бага
        @Override
        public float fout() {
            return 1.0F - this.fin();
        }
        @Override
        public float fout(Interp i) {
            return i.apply(this.fout());
        }
        @Override
        public float fout(float margin) {
            float f = this.fin();
            return f >= 1.0F - margin ? 1.0F - (f - (1.0F - margin)) / margin : 1.0F;
        }
        @Override
        public float fin(Interp i) {
            return i.apply(this.fin());
        }
        @Override
        public float finpow() {
            return Interp.pow3Out.apply(this.fin());
        }
        @Override
        public float fslope() {
            return (0.5F - Math.abs(this.fin() - 0.5F)) * 2.0F;
        }
        //endregion Обход бага
    }

    /** Описывает харакеристики ракеты и её визуальную составляющую. */
    public static class MissileType {
        public float damage;
        public float splashDamage;
        public float splashDamageRadius;
        public float speed;

        public Effect explodeEffect = Fx.none;
        public Effect markEffect = SnFx.missilePoint;

        public TextureRegion rocketRegion;

        public void explode(Vec2 p) {
            Damage.damage(p.x, p.y, splashDamageRadius, splashDamage);
            Damage.damage(p.x, p.y, 0.1f, damage);
            explodeEffect.at(p.x, p.y);
        }

        public Missile launch(Vec2 from, Vec2 to) {
            return new Missile(this, from, to);
        }
    }
}
