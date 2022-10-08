package sunset.type.unitTypes;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureAtlas;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import mindustry.gen.Unit;
import sunset.utils.UnitData;

import java.util.ArrayList;

public class TailUnitType extends UnitTypeExt {
    public final static UnitData.DataKey<Vec2[]> jointsKey = UnitData.dataKey(null);
    public ArrayList<TailSector> tails = new ArrayList<>();
    public Vec2 firstJoint;

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

    private Vec2[] ensureJoints(Unit unit, boolean init) {
        Vec2[] joints = UnitData.getData(unit, jointsKey, () -> null);

        if(joints == null || init) {
            if(joints == null) joints = new Vec2[tails.size()+2];
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
        Vec2[] js = ensureJoints(unit, false);
        fabrik(js, unit.x, unit.y);
        UnitData.setData(unit, jointsKey, js);
    }

    @Override
    public void drawBody(Unit unit) {
        applyColor(unit);
        Vec2[] joints = ensureJoints(unit, false);

        for(int i = tails.size()-1; i >= 1; i--) {
            TextureRegion region = tails.get(i-1).texture;
            float angle = Mathf.atan2(joints[i+1].x - joints[i].x, joints[i+1].y - joints[i].y) * Mathf.radDeg;
            Draw.rect(region, joints[i+1].x, joints[i+1].y, angle + 90);

            //DEBUG DRAW BELOW
            Fill.tri(joints[i+1].x, joints[i+1].y, joints[i].x, joints[i].y + 3, joints[i].x + 3, joints[i].y);
        }
        float angle = Mathf.atan2(joints[1].x - joints[0].x, joints[1].y - joints[0].y) * Mathf.radDeg;
        Draw.rect(region, joints[0].x, joints[0].y, angle + 90);

        Draw.reset();
    }

    private void fabrik(Vec2[] joints, float tx, float ty) {
        joints[0].set(tx, ty);
        for(int i = 1; i < joints.length; i++) {
            float dx = joints[i].x - joints[i-1].x;
            float dy = joints[i].y - joints[i-1].y;
            float len = (i>1) ? (tails.get(i-2).length) : (firstJoint.len());
            len /= Mathf.len(dx, dy);

            dx *= len;
            dy *= len;
            joints[i].set(joints[i-1].x + dx, joints[i-1].y + dy);
        }
    }

    private void set(Vec2[] joints, int i, float x, float y) {
        if(joints[i] == null) joints[i] = new Vec2(x, y);
        else joints[i].set(x, y);
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
            this.length = length;
        }

        public void load() { texture = Core.atlas.find(textureName); }
    }
}
