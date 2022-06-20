package sunset.world.blocks.laser;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.Tile;
import sunset.type.*;
import sunset.utils.*;
import sunset.world.blocks.laser.LaserBlock.*;

import static mindustry.Vars.tilesize;

/**
 * Laser class.
 * Powers laser blocks, damages non-laser blocks and units.
 * */
public class Laser{
    /** start vector (start + offset = laser start) */
    public Vec2 start = new Vec2(),
    /** end vector (end - offset = laser end) */
    end = new Vec2();

    /** laser length. */
    public float length = 16f;
    /** laser angle. In degrees. */
    public float angle = 0f;
    /** laser hit size. Currently, unused.
     * todo use size to fix bug with casting blocks */
    public float size = 8f;
    /** laser offset. Used for drawing. */
    public float offset = 16f;
    /** laser charge. Taken from build.laser.out. Used to transfer charge from one laser build to another.
     * If laser casts with non-laser blocks or units, they take damage in direct ratio with charge. */
    public float charge = 0f;
    /** laser damage. */
    public float damage = 1f;
    /** laser mode. */
    public boolean enabled = true;
    public LaserBuild build;
    /** laser current target. */
    public Healthc target = null;
    public boolean onStaticWall = false;
    public Effect hitEffect = Fx.none;

    public Laser(){
    }

    /**
     * Returns the side laser turned.
     * @see BlockSide
     * */
    public int side(){
        return BlockSide.sideForAngle(angle);
    }

    public void draw(){
        if(enabled){
            Draw.alpha(charge);
            //if we are too close to laser, draw from start to start
            if(end.dst(start) <= offset * 0.25f){
                Drawf.laser(Core.atlas.find("minelaser"), Core.atlas.find("sunset-laser-beam-end"), Core.atlas.find("sunset-laser-beam-end"), start.x, start.y, start.x, start.y);
            }else{
                Drawf.laser(Core.atlas.find("minelaser"), Core.atlas.find("sunset-laser-beam-end"), Core.atlas.find("sunset-laser-beam-end"), start.x, start.y, end.x, end.y);
            }
        }
    }

    public void updateTile(){
        charge = build.laser.out;
        //start offset vector
        Tmp.v1.trns(angle, offset);
        target = LaserUtils.linecast(build, start.x + Tmp.v1.x, start.y + Tmp.v1.y, angle, length, false, true, boolf -> true);
        if(target != null){
            //for correct drawing
            Tmp.v1.trns(angle, start.dst(target.x(), target.y()));
            Tmp.v2.set(0, 0);
            //if block
            if(target instanceof Building b){
                Tmp.v2.trns(angle, offset + (Math.max(0, b.block().size - 2)) / 2f * Vars.tilesize);
            }
            else if(target instanceof Unit u){
                Tmp.v2.trns(angle, offset + Math.max(0, u.hitSize / 2f - 16f));
            }
            end.x = start.x + Tmp.v1.x - Tmp.v2.x;
            end.y = start.y + Tmp.v1.y - Tmp.v2.y;

            if(enabled && charge > 0f){
                //////////////
                //this is for laser mechanic
                if(target instanceof LaserBuild b && b.block().inputsLaser){
                    setTargetLenses(b);
                    b.laser.in += charge;
                }else if(charge != 0){
                    castEffectAt(end.x, end.y, angle, hitEffect);
                    Damage.damage(null, target.x(), target.y(), 8f, damage * charge, false, true);
                }
            }
        }else{
            Tile t = LaserUtils.linecastStaticWalls(start.x + Tmp.v1.x, start.y + Tmp.v1.y, angle, length);
            //cast with static walls
            if(t != null){
                Tmp.v1.trns(angle, start.dst(t.worldx(), t.worldy()));
                Tmp.v2.trns(angle, Vars.tilesize);
                end.x = start.x + Tmp.v1.x - Tmp.v2.x;
                end.y = start.y + Tmp.v1.y - Tmp.v2.y;
                if(enabled && charge > 0f)
                    castEffectAt(end.x, end.y, angle, hitEffect);
            }
            else{
                Tmp.v1.trns(angle, length);
                end.set(start.x + Tmp.v1.x, start.y + Tmp.v1.y);
            }
        }

    }

    public void setTargetLenses(LaserBuild b){
        //Log.info("angle: @", angle);
        switch(BlockSide.sideForAngle(target.angleTo(build))){
            case BlockSide.right -> b.rightInput = true;
            case BlockSide.bottom -> b.downInput = true;
            case BlockSide.left -> b.leftInput = true;
            case BlockSide.top -> b.topInput = true;
            default -> throw new IllegalStateException("Unexpected value: " + BlockSide.sideForAngle(target.angleTo(build)));
        }
    }

    private void castEffectAt(float x, float y, float rot, Effect e){
        //offset because of laser end sprite
        float ofs = 3f;
        Tmp.v4.trns(angle, ofs);
        e.at(x + Tmp.v4.x, y + Tmp.v4.y, rot);
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
}
