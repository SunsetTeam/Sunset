package sunset.world.blocks.laser;

import arc.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.TextureAtlas.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mma.graphics.*;
import sunset.type.*;
import sunset.utils.*;
import sunset.world.blocks.laser.LaserBlock.*;

import static mindustry.Vars.tilesize;

/**
 * Laser class.
 * Powers laser blocks, damages non-laser blocks and units.
 */
public class Laser{
    /** start vector (start + offset = laser start) */
    public Vec2 start = new Vec2(),
    /** end vector (end - offset = laser end) */
    end = new Vec2();

    /** laser length. */
    public float length = 16f;
    /** laser angle. In degrees. */
    public float angle = 0f;
    /**
     * laser hit size. Currently, unused.
     * todo use size to fix bug with casting blocks
     */
    public float size = 8f;
    /** laser offset. Used for drawing. */
    public float offset = 16f;
    /**
     * laser charge. Taken from build.laser.out. Used to transfer charge from one laser build to another.
     * If laser casts with non-laser blocks or units, they take damage in direct ratio with charge.
     */
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

    AtlasRegion laserEndRegion = Core.atlas.find("sunset-laser-beam-end");

    public Laser(){
    }

    /**
     * Returns the side laser turned.
     * @see BlockSide
     */
    public int side(){
        return BlockSide.sideForAngle(angle);
    }

    public void draw(){
        if(!enabled){
            return;
        }
        Tmp.v1.trns(angle, offset);
        Tmp.v3.setZero();

        calculateEndOffset(Tmp.v3,0f);
        Vec2 drawEnd = Tmp.v2.set(end).sub(Tmp.v3);

        Vec2 drawStart = Tmp.v4.set(start).add(Tmp.v1);

        Draw.alpha(charge);

        //if we are too close to laser, draw from start to start
        if(drawEnd.dst2(drawStart) <= (offset * 0.25f) * (offset * 0.25f)){
            Drawf.laser(Core.atlas.find("minelaser"), laserEndRegion, laserEndRegion,
            drawStart.x, drawStart.y, drawStart.x, drawStart.y);
        }else{
            Drawf.laser(Core.atlas.find("minelaser"), laserEndRegion, laserEndRegion,
            drawStart.x, drawStart.y, drawEnd.x, drawEnd.y);
        }
        /*Draw.color(Pal.remove);
        AFill.polyCircle(drawEnd.x, drawEnd.y, tilesize / 4f);
        Draw.color(Pal.accent);
        AFill.polyCircle(drawStart.x, drawStart.y, tilesize / 4f);*/
        Draw.color();
    }

    public void calculateEndOffset(Vec2 endOffset,float extraLength){
        endOffset.trns(angle,calculateEndOffset(extraLength));
    }

    public float calculateEndOffset(float extraLength){
        if(target instanceof Building b){
//                Tmp.v3.trns(angle,-b.block().sizeOffset*tilesize+b.block().offset);
//                b.block().offset
            return ModGeometry.sqrtDstByAngle((b.block().size * tilesize + laserEndRegion.width * Draw.scl) / 2f+extraLength, angle);
//                Tmp.v3.trns(angle,  (b.block().size / 2f * Vars.tilesize));
//                Tmp.v3.trns(angle, offset + (Math.max(0, b.block().size - 2)) / 2f * Vars.tilesize);
        }else if(target instanceof Unit u){
            return (u.hitSize + laserEndRegion.width * Draw.scl) / 2f+extraLength;
//                Tmp.v3.trns(angle, offset + Math.max(0, u.hitSize / 2f - 16f));
        }else if(onStaticWall){
            return tilesize+extraLength;
        }
        return 0;
    }

    public void updateTile(){
        if(!enabled) return;
        charge = build.laser.out;
        //start offset vector
        Tmp.v1.trns(angle, offset);
        target = LaserUtils.linecast(build, start.x + Tmp.v1.x, start.y + Tmp.v1.y, angle, length, false, true, null);
        findStaticWall();
        if(target != null){
            //for correct drawing
            Tmp.v1.trns(angle, start.dst(target));
            end.x = start.x + Tmp.v1.x;
            end.y = start.y + Tmp.v1.y;
        }
        if((onStaticWall || target != null) && charge > 0f){
            //////////////
            //this is for laser mechanic
            if(target instanceof LaserBuild b && b.block().inputsLaser){
                setTargetLenses(b);
                b.laser.in += charge;
            }else{
                castEffectAtEnd();
                if(target != null) Damage.damage(null, target.x(), target.y(), 8f, damage * charge, false, true);
            }
        }
    }

    private void findStaticWall(){
        Tmp.v1.trns(angle, offset);
        Tile t = LaserUtils.linecastStaticWalls(start.x + Tmp.v1.x, start.y + Tmp.v1.y, angle, length);
        //cast with static walls
        if(t != null){
            Tmp.v1.trns(angle, start.dst(t.worldx(), t.worldy()));
            onStaticWall = true;
        }else{
            Tmp.v1.trns(angle, length);
            onStaticWall = false;
        }
        end.set(start).add(Tmp.v1);
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

    private void castEffectAtEnd(){
        //offset because of laser end sprite
        float length = calculateEndOffset(0f);
        Tmp.v2.trns(angle,length-laserEndRegion.width/2f*Draw.scl);
        hitEffect.at(end.x - Tmp.v2.x, end.y - Tmp.v2.y, angle, charge);
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
