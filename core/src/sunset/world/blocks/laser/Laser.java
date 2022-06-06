package sunset.world.blocks.laser;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.math.geom.*;
import arc.util.*;
import mindustry.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.Tile;
import sunset.content.SnBlocks;
import sunset.content.blocks.defense.SnWalls;
import sunset.type.*;
import sunset.utils.*;
import sunset.world.blocks.defense.walls.SnWall;

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
    public float offset = 3f;
    /** laser charge. Taken from build.laser.out. Used to transfer charge from one laser build to another.
     * If laser casts with non-laser blocks or units, they take damage in direct ratio with charge. */
    public float charge = 0f;
    /** laser damage. */
    public float damage = 1f;
    /** laser mode. */
    public boolean enabled = true;
    public LaserBlock.LaserBlockBuild build;
    /** laser current target. */
    public Healthc target = null;
    public boolean onStaticWall = false;

    public Laser(){
    }

    public Laser(Vec2 start){
        this.start = start;
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
            Tmp.v1.trns(angle, offset);
            if(target instanceof Building b){
                Tmp.v3.trns(angle, offset + (b.block().size - 1) / 2f * Vars.tilesize);
            }
            else if(target instanceof Unit u){
                Tmp.v3.trns(angle, offset + u.hitSize / 2f);
            }
            else if(onStaticWall){
                Tmp.v3.trns(angle, offset);
            }

            Draw.alpha(charge);
            Tmp.v2.set(end.x - Tmp.v3.x, end.y - Tmp.v3.y);
            //if we are too close to laser, draw from start to start
            if(Tmp.v2.dst(start) <= offset){
                Drawf.laser(Core.atlas.find("minelaser"), Core.atlas.find("sunset-als-laser-end"), Core.atlas.find("sunset-als-laser-end"), start.x + Tmp.v1.x, start.y + Tmp.v1.y, start.x + Tmp.v1.x, start.y + Tmp.v1.y);
            }else{
                Drawf.laser(Core.atlas.find("minelaser"), Core.atlas.find("sunset-als-laser-end"), Core.atlas.find("sunset-als-laser-end"), start.x + Tmp.v1.x, start.y + Tmp.v1.y, end.x - Tmp.v3.x, end.y - Tmp.v3.y);
            }
        }
    }

    public void updateTile(){
        charge = build.laser.out;
        //start offset vector
        Tmp.v1.trns(angle, offset);
        target = LaserUtils.linecast(start.x + Tmp.v1.x, start.y + Tmp.v1.y, angle, length, false, true, boolf -> true);
        //don't cast with yourself
        if(build != null && target == build)
            return;
        if(target != null){
            //for correct drawing
            Tmp.v1.trns(angle, start.dst(target.x(), target.y()));
            end.x = start.x + Tmp.v1.x;
            end.y = start.y + Tmp.v1.y;

            if(enabled){
                //////////////
                //this is for laser mechanic
                if(target instanceof LaserBlock.LaserBlockBuild b && b.block().inputsLaser){
                    setTargetLenses(b);
                    b.laser.in += charge;
                }else if(charge != 0){
                    Damage.damage(null, target.x(), target.y(), 8f, damage * charge, false, true);
                }
            }
        }else{
            Tile t = LaserUtils.linecastStaticWalls(start.x + Tmp.v1.x, start.y + Tmp.v1.y, angle, length);
            //cast with static walls
            if(t != null){
                Tmp.v1.trns(angle, start.dst(t.worldx(), t.worldy()));
                end.x = start.x + Tmp.v1.x;
                end.y = start.y + Tmp.v1.y;
                onStaticWall = true;
            }
            else{
                Tmp.v1.trns(angle, length);
                end.set(start.x + Tmp.v1.x, start.y + Tmp.v1.y);
                onStaticWall = false;
            }
        }

    }

    public void setTargetLenses(LaserBlock.LaserBlockBuild b){
        //Log.info("angle: @", angle);
        switch(BlockSide.sideForAngle(target.angleTo(build))){
            case BlockSide.right -> b.rightInput = true;
            case BlockSide.bottom -> b.downInput = true;
            case BlockSide.left -> b.leftInput = true;
            case BlockSide.top -> b.topInput = true;
            default -> throw new IllegalStateException("Unexpected value: " + BlockSide.sideForAngle(target.angleTo(build)));
        }
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
