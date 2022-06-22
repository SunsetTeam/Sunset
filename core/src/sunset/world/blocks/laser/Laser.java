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

    private void getEndOffset(Vec2 v){
        v.setZero();
        if(target instanceof Building b){
            v.trns(angle, tilesize * b.block().size / 2f);
        }else if(target instanceof Unit u){
            v.trns(angle, u.hitSize / 2f);
        }else if(onStaticWall){
            v.trns(angle, Vars.tilesize / 2f);
        }
    }

    private void getStartOffset(Vec2 v){
        v.trns(angle, tilesize * build.block().size / 2f);
    }

    public void draw(){
        if(!enabled){
            return;
        }
        Tmp.v6.trns(angle, laserEndRegion.width / 2f * Draw.scl);

        getStartOffset(Tmp.v1);
        getEndOffset(Tmp.v3);

        Vec2 drawStart = Tmp.v2.set(start.x + Tmp.v1.x + Tmp.v6.x, start.y + Tmp.v1.y + Tmp.v6.y);
        Vec2 drawEnd = Tmp.v4.set(end.x - Tmp.v3.x - Tmp.v6.x, end.y - Tmp.v3.y - Tmp.v6.y);

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

    private void buildOrUnitCasted(){
        onStaticWall = false;
        //for correct drawing
        Tmp.v1.trns(angle, start.dst(target));
        end.x = start.x + Tmp.v1.x;
        end.y = start.y + Tmp.v1.y;
        if(enabled && charge > 0f) {
            //////////////
            //this is for laser mechanic
            if (target instanceof LaserBuild b && b.block().inputsLaser) {
                setTargetLenses(b);
                b.laser.in += charge;
            } else {
                Tmp.v2.setZero();
                getEndOffset(Tmp.v2);
                hitEffectAt(end.x - Tmp.v2.x, end.y - Tmp.v2.y, angle, charge);
                Damage.damage(null, target.x(), target.y(), 8f, damage * charge, false, true);
            }
        }
    }

    private void staticWallCasted(Tile t){
        onStaticWall = true;
        //cast with static walls
        Tmp.v1.trns(angle, start.dst(t.worldx(), t.worldy()));
        end.x = start.x + Tmp.v1.x;
        end.y = start.y + Tmp.v1.y;
        if(enabled && charge > 0f){
            Tmp.v2.trns(angle, tilesize / 2f);
            hitEffectAt(end.x - Tmp.v2.x, end.y - Tmp.v2.y, angle, charge);
        }
    }

    private void nothingCasted(){
        onStaticWall = false;
        Tmp.v1.trns(angle, length);
        end.set(start.x + Tmp.v1.x, start.y + Tmp.v1.y);
    }

    public void updateTile(){
        charge = build.laser.out;
        //start offset vector
        Tmp.v1.trns(angle, tilesize * build.block().size / 2f);
        target = LaserUtils.linecast(build, start.x + Tmp.v1.x, start.y + Tmp.v1.y, angle, length, false, true, null);
        Tile t = LaserUtils.linecastStaticWalls(start.x + Tmp.v1.x, start.y + Tmp.v1.y, angle, length);
        if(target != null && t != null){
            if(target.dst2(start.x + Tmp.v1.x, start.y + Tmp.v1.y) < t.dst2(start.x + Tmp.v1.x, start.y + Tmp.v1.y)){
                buildOrUnitCasted();
            }
            else
                staticWallCasted(t);
        }
        else if(target != null){
            buildOrUnitCasted();
        }
        else if(t != null){
            staticWallCasted(t);
        }
        else{
            nothingCasted();
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

    private void hitEffectAt(float x, float y, float angle_, float charge_){
        //offset because of laser end sprite
        //Tmp.v2.trns(angle,laserEndRegion.width / 2f * Draw.scl);
        Tmp.v2.setZero();
        hitEffect.at(x - Tmp.v2.x, y - Tmp.v2.y, angle_, charge_);
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
