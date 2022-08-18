package sunset.world.blocks.laser;

import arc.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.TextureAtlas.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import sunset.*;
import sunset.type.*;
import sunset.utils.*;
import sunset.world.blocks.laser.LaserBlock.*;

import static mindustry.Vars.tilesize;

/**
 * Laser class.
 * Powers laser blocks, damages non-laser blocks and units.
 */
@SuppressWarnings({"UnusedReturnValue", "SameParameterValue"})
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
     */
    public float size = 8f;
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

    private Vec2 getEndOffset(Vec2 endOffset){
        endOffset.setZero();
        if(target instanceof Building b){
            endOffset.trns(angle, tilesize * b.block().size / 2f);
        }else if(target instanceof Unit u){
            endOffset.trns(angle, u.hitSize / 2f);
        }else if(onStaticWall){
            endOffset.trns(angle, Vars.tilesize / 2f);
        }
        return endOffset;
    }

    private Vec2 getStartOffset(Vec2 startOffset){
        startOffset.trns(angle, tilesize * build.block().size / 2f);
        return startOffset;
    }

    public void draw(){
        if(!enabled || charge <= 0f){
            return;
        }
        Tmp.v6.trns(angle, laserEndRegion.width / 2f * Draw.scl);

        getStartOffset(Tmp.v1);
        Vec2 drawStart = Tmp.v2.set(start).add(Tmp.v1).add(Tmp.v6);

        getEndOffset(Tmp.v1);
        Vec2 drawEnd = Tmp.v4.set(end).sub(Tmp.v1).sub(Tmp.v6);

        Draw.alpha(charge);

        //if we are too close to laser, draw from start to start
        if(drawEnd.dst2(drawStart) <= tilesize){
            Drawf.laser(Core.atlas.find("minelaser"), laserEndRegion, laserEndRegion,
            drawStart.x, drawStart.y, drawStart.x, drawStart.y);
        }else{
            Drawf.laser(Core.atlas.find("minelaser"), laserEndRegion, laserEndRegion,
            drawStart.x, drawStart.y, drawEnd.x, drawEnd.y);
        }
        Draw.color();
    }


    public void updateTile(){
        charge = build.laser.out;
        onStaticWall = false;
        target=null;
        if(!enabled || charge <= 0f){
//            charge=0f;
            return;
        }
        //start offset vector
        Vec2 drawStart = getStartOffset(Tmp.v2).add(start);

        target = LaserUtils.linecast(build, drawStart.x, drawStart.y, angle, length, false, true, null);
        Tile t = LaserUtils.linecastStaticWalls(drawStart.x, drawStart.y, angle, length);

        //remove object target if static wall is closer
        if(target != null && t != null && t.dst2(drawStart) <= target.dst2(drawStart)){
            target = null;
        }

        if(target != null){
            //cast with entity
            //for correct drawing
            end.trns(angle, start.dst(target)).add(start);
        }else if(t != null){
            //cast with static walls
            onStaticWall = true;
            end.trns(angle, start.dst(t.worldx(), t.worldy())).add(start);
        }else{
            end.trns(angle, length).add(start);
            return;
        }

        if(target instanceof LaserBuild b && b.block().inputsLaser){
            setTargetLenses(b.laser);
            b.laser.in += charge;
        }
        else{
            LaserModule lm = SnVars.logic.hybridLaserBlockLogic.laserModule(target.buildOn());
            if(lm != null){
                setTargetLenses(lm);
                lm.in += charge;
            }
            else{
                getEndOffset(Tmp.v2);
                hitEffectAt(end.x - Tmp.v2.x, end.y - Tmp.v2.y);
                if(target != null){
                    Damage.damage(null, target.x(), target.y(), 8f, damage * charge, false, true);
                }
            }
        }
    }

    public void setTargetLenses(LaserModule lm){
        //Log.info("angle: @", angle);
        switch(BlockSide.sideForAngle(target.angleTo(build))){
            case BlockSide.right -> lm.rightInput = true;
            case BlockSide.bottom -> lm.downInput = true;
            case BlockSide.left -> lm.leftInput = true;
            case BlockSide.top -> lm.topInput = true;
            default -> throw new IllegalStateException("Unexpected value: " + BlockSide.sideForAngle(target.angleTo(build)));
        }
    }

    private void hitEffectAt(float x, float y){
        hitEffect.at(x, y, angle, charge);
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
