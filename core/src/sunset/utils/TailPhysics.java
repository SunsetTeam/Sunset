package sunset.utils;

import arc.*;
import arc.func.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;

import java.util.*;

public class TailPhysics{
    public Seq<TailSector> tails = new Seq<>();

    private void set(TailJoint[] joints, int i, float x, float y){
        if(joints[i] == null) joints[i] = new TailJoint(x, y);
        else joints[i].set(x, y);
    }

    public TailJoint[] ensureJoints(TailJoint[] joints, float x, float y, float rotation, boolean init){

        if(joints == null || init){
            if(joints == null) joints = new TailJoint[tails.size + 2];
            set(joints, 0, x, y);
            float sin = Mathf.sin(rotation / Mathf.radDeg), cos = Mathf.cos(rotation / Mathf.radDeg), len = firstJoint.len();
            set(joints, 0, x, y);
            for(int i = 0; i < tails.size; i++){
                TailSector tail = tails.get(i);
                set(joints, i + 1, x + cos * len, y + sin * len);
                len += tail.length;
            }
            set(joints, joints.length - 1, x + cos * len, y + sin * len);
//            UnitData.setData(unit, jointsKey, joints);
        }

        return joints;
    }

    public Vec2 firstJoint;
    public float inert = 0.03f;
    public float decel = 0.95f;
    private void fabrik(TailJoint[] joints, float tx, float ty){
        joints[0].set(tx, ty);

        float dec = Mathf.pow(decel, Time.delta / Time.toSeconds);
        float inr = Mathf.pow(inert, Time.delta / Time.toSeconds);

        for(int i = 1; i < joints.length; i++){
            joints[i].applySpeed(Time.delta);
            float prevX = joints[i].x, prevY = joints[i].y;

            float dx = joints[i].x - joints[i - 1].x;
            float dy = joints[i].y - joints[i - 1].y;
            float len = (i > 1) ? (tails.get(i - 2).length) : (firstJoint.len());
            len /= Mathf.len(dx, dy);

            dx *= len;
            dy *= len;
            joints[i].set(joints[i - 1].x + dx, joints[i - 1].y + dy);
            joints[i].updateSpeed(prevX, prevY, Time.delta / Time.toSeconds, inr, dec);
        }
    }

    public void load(){
        for(TailSector tail : tails) tail.load();
    }

    public TailJoint[] processPoints(TailJoint[] joints, float x, float y, float rotation){
        ensureJoints(joints,x,y,rotation,false);
        fabrik(joints,x,y);
        return joints;
    }

    public void eachJointWithAngle(TailJoint[] joints, Cons2<Integer,Float> consumer){
        for(int i = tails.size - 1; i >= 0; i--){
            float angle = Mathf.atan2(joints[i + 2].x - joints[i + 1].x, joints[i + 2].y - joints[i + 1].y) + Mathf.PI / 2;
            consumer.get(i,angle*Mathf.radiansToDegrees);
        }
    }

    public static class TailSector{
        public final Vec2 joint;
        public final float length;
        private final String textureName;
        private final Vec2 tmp = new Vec2();
        public TextureRegion texture;

        public TailSector(String texture, Vec2 joint, float length){
            this.textureName = texture;
            this.joint = joint;
            joint.y *= -1;
            this.length = length;
        }

        public void load(){
            texture = Core.atlas.find("sunset-" + textureName);
        }

        public void draw(TailJoint at, float angle){
            //1=top-left | 2=down-left | 3=down-right | 4=top-right
            //float sin = Mathf.sin(angle), cos = Mathf.cos(angle);
            float w = texture.width / 32f * Vars.tilesize, h = texture.height / 32f * Vars.tilesize;

            tmp.set(-joint.x, -joint.y).rotate(angle);
            float x1 = tmp.x, y1 = tmp.y;

            tmp.set(-joint.x, -joint.y - h).rotate(angle);
            float x2 = tmp.x, y2 = tmp.y;

            tmp.set(-joint.x + w, -joint.y - h).rotate(angle);
            float x3 = tmp.x, y3 = tmp.y;

            tmp.set(-joint.x + w, -joint.y).rotate(angle);
            float x4 = tmp.x, y4 = tmp.y;

            Fill.quad(texture,
            at.x + x1, at.y + y1,
            at.x + x2, at.y + y2,
            at.x + x3, at.y + y3,
            at.x + x4, at.y + y4);
        }

        public void drawShadow(TailJoint at, float angle){
            //TODO draw shadows
        }
    }

    public static class TailJoint{
        public float x, y;
        public float speedX = 0, speedY = 0;

        public TailJoint(float x, float y){
            this.x = x;
            this.y = y;
        }

        public void set(float x, float y){
            this.x = x;
            this.y = y;
        }

        public void applySpeed(float time){
            x += speedX * time;
            y += speedY * time;
        }

        public void updateSpeed(float prevx, float prevy, float dt, float inr, float dec){
            float sx = (x - prevx) / dt;
            float sy = (y - prevy) / dt;
            speedX = (speedX * dec + sx * (1 - dec)) * inr;
            speedY = (speedY * dec + sy * (1 - dec)) * inr;
        }
    }
}
