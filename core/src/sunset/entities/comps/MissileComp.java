package sunset.entities.comps;

import arc.math.Scaled;
import arc.math.geom.Vec2;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.annotations.Annotations;
import mindustry.gen.Drawc;
import mindustry.gen.Entityc;
import mindustry.gen.Posc;
import sunset.gen.Missilec;
import sunset.type.blocks.MissileType;

@Annotations.EntityDef(value = {Missilec.class})
@Annotations.Component(base = true)
abstract class MissileComp implements Entityc, Posc, Drawc, Scaled {
    public Vec2 from=new Vec2(), to=new Vec2();
    public MissileType type;
    public transient float flightLength;
    public transient float clipSize;
    public transient float lifetime;
    float time;
    @Annotations.Import
    float rotation, x, y;
    @Annotations.Import
    int id;

    public void set(Vec2 from, Vec2 to, MissileType type) {
        this.from = from;
        this.to = to;
        this.time = 0;
        this.type = type;
        init();
    }

    @Override
    public void update() {
        if (type==null){
            remove();
            return;
        }
        time += Time.delta;

        if (time >= lifetime) {
            type.landed(self());
            remove();
        }
    }

    @Override
    public void draw() {
        type.drawMissile(self());
    }

    public Vec2 currentPosition(Vec2 vec2) {
        if (vec2 == null) vec2 = new Vec2();
        return vec2.set(from).lerp(to, fin());
    }

    public void init() {
        flightLength = Tmp.v1.set(to).sub(from).len();
        lifetime = flightLength / type.speed;
        set(Tmp.v1.set(to).lerp(from, 0.5f));
        clipSize = flightLength / 2f;
    }

    @Override
    @Annotations.Replace
    public float clipSize() {
        return clipSize;
    }

    @Override
    public void afterRead() {
        if (type == null) {
            remove();
            return;
        }
        init();
    }

    @Override
    public float fin() {
        return time/lifetime;
    }
}
