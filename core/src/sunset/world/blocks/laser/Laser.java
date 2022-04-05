package sunset.world.blocks.laser;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.math.geom.Vec2;
import arc.util.Log;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.entities.Damage;
import mindustry.gen.Building;
import mindustry.gen.Healthc;
import mindustry.graphics.Drawf;
import sunset.utils.LaserUtils;
import sunset.utils.Utils;

public class Laser {
    public Vec2 start = new Vec2(),
                end = new Vec2();

    public float length = 16f, angle = 0f;
    public float size = 8f;
    public float offset = 3f;
    public float charge = 0f;
    public float damage = 1f;

    public boolean enabled = true;

    public LaserBlock.LaserBlockBuild self;
    //null if nothing found
    public Healthc target = null;

    public Laser(){
    }

    public Laser(Vec2 start){
        this.start = start;
    }

    public void draw(){
        if (enabled){
            //Log.info("draw\nangle: @\nstart: @  @\nend: @  @", angle, start.x, start.y, end.x, end.y);
            Tmp.v1.set(0, 0).trns(angle, offset);
            //Log.info("tmpv1\nx: @\ny: @\noffset: @\nangle: @", Tmp.v1.x, Tmp.v1.y, offset, angle);
            Draw.alpha(charge);
            Drawf.laser(null, Core.atlas.find("minelaser"), Core.atlas.find("minelaser-end"), start.x + Tmp.v1.x, start.y + Tmp.v1.y, end.x - Tmp.v1.x, end.y - Tmp.v1.y);
            Draw.reset();
            //change lens drawing in drawer
            if(target instanceof LaserBlock.LaserBlockBuild b){
                //Log.info("angle: @", angle);
                //from right
                if(angle > 135 && angle < 225){
                    b.drawer.rightInput = true;
                    //Log.info("from right");
                }
                //from down
                if(angle > 45 && angle < 135){
                    b.drawer.downInput = true;
                    //Log.info("from down");
                }
                //from left
                if((angle - 360 > -45 && angle - 360 <= 0) || (angle < 45)){
                    b.drawer.leftInput = true;
                    //Log.info("from left");
                }
                //from top
                if(angle > 225 && angle < 315){
                    b.drawer.topInput = true;
                    //Log.info("from top");
                }
            }
        }
    }

    public void update(){
        charge = self.laser.out;
        if(enabled){
            //start offset vector
            Tmp.v1.trns(angle, offset);
            Healthc entity = LaserUtils.linecast(start.x + Tmp.v1.x, start.y + Tmp.v1.y, angle, length, false, true, boolf -> true);
            target = entity;
            //don't cast with yourself
            if(self != null && target == self)
                return;
            if(target != null){
                //for correct drawing
                Tmp.v1.set(start.x, start.y);
                float len = Tmp.v1.dst(entity.x(), entity.y());
                Tmp.v1.trns(angle, len);
                end.x = start.x + Tmp.v1.x;
                end.y = start.y + Tmp.v1.y;
                //////////////
                //this is for laser mechanic
                if(target instanceof LaserBlock.LaserBlockBuild b){
                    b.laser.in += charge;
                }
                else{
                    if(charge != 0)
                        Damage.damage(null, target.x(), target.y(), 8f, damage * charge,false, true);
                }
            }
            else{
                Tmp.v1.setLength(length).setAngle(angle);
                end.set(start.x + Tmp.v1.x, start.y + Tmp.v1.y);
            }
        }
    }

    public void start(Vec2 start){
        this.start.set(start);
    }

    public void end(Vec2 end){
        this.end.set(end);
    }
}
