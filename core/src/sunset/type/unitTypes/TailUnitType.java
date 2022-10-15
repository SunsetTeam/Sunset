package sunset.type.unitTypes;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Time;
import mindustry.Vars;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import sunset.utils.UnitData;

import java.util.ArrayList;

public class TailUnitType extends UnitTypeExt {
    public final static UnitData.DataKey<TailJoint[]> jointsKey = UnitData.dataKey(null);
    public ArrayList<TailSector> tails = new ArrayList<>();
    public Vec2 firstJoint;
    public float inert = 0.03f;
    public float decel = 0.95f;

    public TailUnitType(String name) { super(name); }

    public void tail(String sprite, float jx, float jy, float length) {
        tails.add(new TailSector(sprite, new Vec2(jx, jy), length));
    }

    @Override
    public void load() {
        super.load();
        for(TailSector tail : tails) tail.load();
    }

    @Override
    public void landed(Unit unit) {
        super.landed(unit);
        ensureJoints(unit, true);
    }

    private TailJoint[] ensureJoints(Unit unit, boolean init) {
        TailJoint[] joints = UnitData.getData(unit, jointsKey, () -> null);

        if(joints == null || init) {
            if(joints == null) joints = new TailJoint[tails.size()+2];
            set(joints, 0, unit.x, unit.y);
            float sin = Mathf.sin(unit.rotation / Mathf.radDeg), cos = Mathf.cos(unit.rotation / Mathf.radDeg), len = firstJoint.len();
            set(joints, 0, unit.x, unit.y);
            for(int i = 0; i < tails.size(); i++) {
                TailSector tail = tails.get(i);
                set(joints, i+1, unit.x + cos * len, unit.y + sin * len);
                len += tail.length;
            }
            set(joints, joints.length-1, unit.x + cos * len, unit.y + sin * len);
            UnitData.setData(unit, jointsKey, joints);
        }

        return joints;
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);
        TailJoint[] js = ensureJoints(unit, false);
        fabrik(js, unit.x, unit.y);
        UnitData.setData(unit, jointsKey, js);

        float angle = Mathf.atan2(js[1].x - js[0].x, js[1].y - js[0].y) * Mathf.radDeg;
        unit.rotation(angle + 180);
    }

    @Override
    public void drawBody(Unit unit) {
        applyColor(unit);
        TailJoint[] joints = ensureJoints(unit, false);

        float z = !unit.isAdded() ? Draw.z() : unit.elevation > 0.5f ? (lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f);
        Draw.z(z - 0.01f);
        for(int i = tails.size()-1; i >= 0; i--) {
            TailSector tail = tails.get(i);
            float angle = Mathf.atan2(joints[i+2].x - joints[i+1].x, joints[i+2].y - joints[i+1].y) + Mathf.PI / 2;
            tail.draw(joints[i+1], angle);
        }
        Draw.z(z);

        Draw.reset();
    }

    @Override
    public void drawShadow(Unit unit) {
        super.drawShadow(unit);
        TailJoint[] joints = ensureJoints(unit, false);
        for(int i = tails.size()-1; i >= 0; i--) {
            TailSector tail = tails.get(i);
            float angle = Mathf.atan2(joints[i+2].x - joints[i+1].x, joints[i+2].y - joints[i+1].y) + Mathf.PI / 2;
            tail.drawShadow(joints[i+1], angle);
        }
    }

    private void fabrik(TailJoint[] joints, float tx, float ty) {
        joints[0].set(tx, ty);

        float dec = Mathf.pow(decel, Time.delta / Time.toSeconds);
        float inr = Mathf.pow(inert, Time.delta / Time.toSeconds);

        for(int i = 1; i < joints.length; i++) {
            joints[i].applySpeed(Time.delta);
            float prevX = joints[i].x, prevY = joints[i].y;

            float dx = joints[i].x - joints[i-1].x;
            float dy = joints[i].y - joints[i-1].y;
            float len = (i>1) ? (tails.get(i-2).length) : (firstJoint.len());
            len /= Mathf.len(dx, dy);

            dx *= len;
            dy *= len;
            joints[i].set(joints[i-1].x + dx, joints[i-1].y + dy);
            joints[i].updateSpeed(prevX, prevY, Time.delta / Time.toSeconds, inr, dec);
        }
    }

    private void set(TailJoint[] joints, int i, float x, float y) {
        if(joints[i] == null) joints[i] = new TailJoint(x, y);
        else joints[i].set(x, y);
    }

    private static class TailJoint
    {
        public float x, y;
        public float speedX = 0, speedY = 0;

        public TailJoint(float x, float y) { this.x = x; this.y = y; }

        public void set(float x, float y) { this.x = x; this.y = y; }

        public void applySpeed(float time) { x += speedX * time; y += speedY * time; }

        public void updateSpeed(float prevx, float prevy, float dt, float inr, float dec) {
            float sx = (x - prevx) / dt;
            float sy = (y - prevy) / dt;
            speedX = (speedX * dec + sx * (1 - dec)) * inr;
            speedY = (speedY * dec + sy * (1 - dec)) * inr;
        }
    }

    public static class TailSector
    {
        private final String textureName;
        public TextureRegion texture;
        public final Vec2 joint;
        public final float length;

        public TailSector(String texture, Vec2 joint, float length) {
            this.textureName = texture;
            this.joint = joint;
            joint.y *= -1;
            this.length = length;
        }

        public void load() { texture = Core.atlas.find("sunset-" + textureName); }

        private final Vec2 tmp = new Vec2();
        public void draw(TailJoint at, float angle) {
            //1=top-left | 2=down-left | 3=down-right | 4=top-right
            //float sin = Mathf.sin(angle), cos = Mathf.cos(angle);
            float w = texture.width / 32f * Vars.tilesize, h = texture.height / 32f * Vars.tilesize;

            tmp.set(-joint.x, -joint.y).rotateRad(angle);
            float x1 = tmp.x, y1 = tmp.y;

            tmp.set(-joint.x, -joint.y - h).rotateRad(angle);
            float x2 = tmp.x, y2 = tmp.y;

            tmp.set(-joint.x + w, -joint.y - h).rotateRad(angle);
            float x3 = tmp.x, y3 = tmp.y;

            tmp.set(-joint.x + w, -joint.y).rotateRad(angle);
            float x4 = tmp.x, y4 = tmp.y;

            Fill.quad(texture,
                    at.x + x1, at.y + y1,
                    at.x + x2, at.y + y2,
                    at.x + x3, at.y + y3,
                    at.x + x4, at.y + y4);
        }

        public void drawShadow(TailJoint at, float angle) {
            //TODO draw shadows
        }
    }
}
