package sunset.world.blocks.laser;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import sunset.type.*;
import sunset.utils.*;

public class Laser{
    public Vec2 start = new Vec2(),
    end = new Vec2();

    public float length = 16f, angle = 0f;
    public float size = 8f;
    public float offset = 3f;
    public float charge = 0f;
    public float damage = 1f;
    public boolean enabled = true;
    public LaserBlock.LaserBlockBuild build;
    //null if nothing found
    public Healthc target = null;

    public Laser(){
    }

    public Laser(Vec2 start){
        this.start = start;
    }

    public int side(){
        return BlockSide.sideForAngle(angle);
    }

    public void draw(){
        if(enabled){
            //Log.info("draw\nangle: @\nstart: @  @\nend: @  @", angle, start.x, start.y, end.x, end.y);
            Tmp.v1.trns(angle, offset);
            //Log.info("tmpv1\nx: @\ny: @\noffset: @\nangle: @", Tmp.v1.x, Tmp.v1.y, offset, angle);
            Draw.alpha(charge);
            //Log.info("dst btwn end and start: @\noffset: @", end.dst(start), offset);
            Tmp.v2.set(end.x - Tmp.v1.x, end.y - Tmp.v1.y);
            //if we are too close to laser, draw from start to start
            if(Tmp.v2.dst(start) <= offset){
                Drawf.laser(null, Core.atlas.find("minelaser"), Core.atlas.find("minelaser-end"), start.x + Tmp.v1.x, start.y + Tmp.v1.y, start.x + Tmp.v1.x, start.y + Tmp.v1.y);
            }else{
                Drawf.laser(null, Core.atlas.find("minelaser"), Core.atlas.find("minelaser-end"), start.x + Tmp.v1.x, start.y + Tmp.v1.y, end.x - Tmp.v1.x, end.y - Tmp.v1.y);
            }
            Draw.reset();
        }
    }

    public void updateTile(){
        charge = build.laser.out;
        //start offset vector
        Tmp.v1.trns(angle, offset);
        resetTargetInput();
        Healthc entity = LaserUtils.linecast(start.x + Tmp.v1.x, start.y + Tmp.v1.y, angle, length, false, true, boolf -> true);
        target = entity;
        //don't cast with yourself
        if(build != null && target == build)
            return;
        if(target != null){
            //for correct drawing
            if(start.dst2(entity) == 0f){
                end = start;
            }else{
                Tmp.v1.trns(angle, start.dst(entity.x(), entity.y()));
                end.x = start.x + Tmp.v1.x;
                end.y = start.y + Tmp.v1.y;
            }

            if(enabled){
                setTargetLenses();
                //////////////
                //this is for laser mechanic
                if(target instanceof LaserBlock.LaserBlockBuild b){
                    b.laser.in += charge;
                }else if(charge != 0){
                    Damage.damage(null, target.x(), target.y(), 8f, damage * charge, false, true);
                }
            }
        }else{
            Tmp.v1.trns(angle, length);
            end.set(start.x + Tmp.v1.x, start.y + Tmp.v1.y);
        }

    }

    public void setTargetLenses(){
        if(target instanceof LaserBlock.LaserBlockBuild b){
            //Log.info("angle: @", angle);
            switch(BlockSide.sideForAngle(target.angleTo(build))){
                case BlockSide.right -> b.rightInput = true;
                case BlockSide.bottom -> b.downInput = true;
                case BlockSide.left -> b.leftInput = true;
                case BlockSide.top -> b.topInput = true;
                default -> throw new IllegalStateException("Unexpected value: " + BlockSide.sideForAngle(target.angleTo(build)));
            }
        }
    }

    public void resetTargetInput(){
        /*if(target instanceof LaserBlock.LaserBlockBuild b && enabled){
            //Log.info("angle: @", angle);
            switch(BlockSide.sideForAngle(target.angleTo(build))){
                case BlockSide.right -> b.rightInput = false;
                case BlockSide.bottom -> b.downInput = false;
                case BlockSide.left -> b.leftInput = false;
                case BlockSide.top -> b.topInput = false;
                default -> throw new IllegalStateException("Unexpected value: " + BlockSide.sideForAngle(target.angleTo(build)));
            }
        }*/
    }

    public void start(Vec2 start){
        start(start.x, start.y);
    }

    public void start(float x, float y){
        start.set(x, y);
    }

    public void end(Vec2 end){
        end(end.x, end.y);
    }

    public void end(float x, float y){
        end.set(x, y);
    }

    public void remove(){
        resetTargetInput();
    }
}
