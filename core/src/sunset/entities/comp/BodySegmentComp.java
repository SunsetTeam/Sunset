package sunset.entities.comp;

import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.annotations.*;
import mindustry.annotations.Annotations.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;
import mma.annotations.ModAnnotations.*;
import sunset.ai.*;
import sunset.gen.*;
import sunset.type.unitTypes.*;


@Component
abstract class BodySegmentComp implements Entityc, Unitc, UncontrollableUnitc{
    float _offset = 0;
    Segmentc parent = null;
    BodySegmentc segment = null;

    ;
    //it only read unit
    int idParent = -1;
    int idSegment = -1;

    @Import
    float x, y, hitSize, minFormationSpeed, rotation, elevation;
    @Import
    UnitType type;
    private SegmentAI segmentAI;
    ;

    public void setParent(Unit parent){
        this.parent = parent.as();
        idParent = parent.id;
    }

    public void setSegment(Unit segment){
        this.segment = segment.as();
        idSegment = segment.id;
    }

    @Annotations.MethodPriority(-1000f)
    public void setType(UnitType type_){
        if(!(type_ instanceof SegmentBodyUnitType type)){
            throw new IllegalArgumentException("type must be instance of " + SegmentBodyUnitType.class.getSimpleName());
        }
        this._offset = type.offsetSegment;
    }

    @Override
    public void resetController(){
        controller(new SegmentAI());
    }

    @Override
    @MethodPriority(-100)
    public void controller(UnitController next){
        if(next instanceof SegmentAI segmentAI){
            this.segmentAI = segmentAI;
        }

    }

    @Override
    @IgnoreImplementation({Commanderc.class})
    @MethodPriority(-1_000_000f)
    public void update(){
        controller(segmentAI);
        //find head and segment
        if(this.parent == null || this.segment == null) findFamily();
        //get parent
        //kill if paren is null
        if(parent == null || parent.dead()) kill();
        //update team
        if(parent != null && team() != parent.team()) team(parent.team());
    }
    
    public void findFamily(){
        //get units
        Unit segment = Groups.unit.getByID(this.idSegment);
        Unit parent = Groups.unit.getByID(this.idParent);
        //check segment
        if(segment != null) this.segment = segment.as();
        //check parent
        if(parent != null){
            //set parent
            this.parent = parent.as();

            //call head for add child
            this.parent.addChild(self());
            //update head if segment is null
            if(segment == null) this.parent._update();
        }else{
            this.kill();
        }
    }

    //    return dinstace of next segment
    public float getDstSegment(){
        var next = this.segment;

        if(next == null || next.dead()) return -100;

        Tmp.v1.trns(Angles.angle(this.x, this.y, next.x(), next.y()), -segmentType().offsetSegment);

        return Mathf.dst(this.x, this.y, next.x() + Tmp.v1.x, next.y() + Tmp.v1.y) - (this.hitSize + 10);
    }


//      as the next segment moves further away, it will have higher speed

    private SegmentBodyUnitType segmentType(){
        return (SegmentBodyUnitType)type();
    }

    @Override
    @Replace
    public void impulseNet(Vec2 vec2){
    }

    @Override
    @Replace
    public int cap(){
        return this.count() + 1;
    }

    ;

    @Override
    @GlobalReturn
    @MethodPriority(-1000)
    public float speed(){
        if(this.parent != null){
            return this.parent.speed() + ((this.getDstSegment()) / (segmentType().offsetSegment + this.hitSize));
        }else{
            return superSpeed();
        }
    }


    public float superSpeed(){

        float strafePenalty = isGrounded() || !isPlayer() ? 1.0F : Mathf.lerp(1.0F, type.strafePenalty, Angles.angleDist(vel().angle(), rotation) / 180.0F);
        float boost = Mathf.lerp(1.0F, type.canBoost ? type.boostMultiplier : 1.0F, elevation);
        return (isCommanding() ? minFormationSpeed * 0.98F : type.speed) * strafePenalty * boost * floorSpeedMultiplier();
    }

    public float getOffset(){
        return this._offset;
    }
}
