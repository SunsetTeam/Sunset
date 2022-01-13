package sunset.entities.comp;

import arc.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.annotations.Annotations.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import sunset.gen.*;
import sunset.type.unitTypes.*;

@Component
abstract class SegmentComp implements Entityc, Unitc{
    public Seq<BodySegmentc> _segments = new Seq<>();
    public int totalSegments = 0;
    public boolean setSneak = false;
    public float timeOut = 1;
    public boolean tryFindSegment = false;

    @Import
    float x, y, hitSize, minFormationSpeed, rotation, elevation;

    public void add(){
        //check is on snake
        if(!this.setSneak) this.createSegments();
    }

    ;

    public void update(){
        //update
        this._update();
    }

    ;

    public void _update(){
        if(!tryFindSegment) timeOutSegment();
        //if save map on creating segments
        if(!setSneak) createSegments();
        //if has changes array segments
        if(_segments.count(unitSegment -> unitSegment != null && !unitSegment.dead()) != totalSegments){
            //update
            _segments.filter(unitSegment -> unitSegment != null && !unitSegment.dead());
            this.totalSegments = this._segments.size;
            //update segments
            for(var i = 0; i < this._segments.size; i++){
                Unitc lastSegment = this._segments.size - 1 > i ? this._segments.get(i - 1) : self();
                var segment = this._segments.get(i);
                //set segment
                if((segment != null && !segment.dead()) && (lastSegment != null && !lastSegment.dead())) segment.setSegment(lastSegment.as());
            }
        }
        //detroy head ohno
        if(this.canDead(this.totalSegments) && this.tryFindSegment) this.kill();
    }

    ;

    /*
     *Any event you need segments has to verify if the term segment search
     */
    public void timeOutSegment(){
        //remove time
        this.timeOut -= Time.delta;
        //stop
        if(this.timeOut < 0) this.tryFindSegment = true;
    }

    ;

    public boolean canDead(int amount){
        return amount < 1;
    }

    ;

    public void createSegments(){
        //:b anti error
        SegmentUnitType type = segmentType();
        if(type.body != null && type.end != null){
            //create childs
            int total = type.lengthSnake - this.totalSegments;
            for(int i = 0; i < total; i++){
                //last unit
                Unit lastSegment = this._segments.size - 1 > i ? this._segments.get(i - 1).as() : self();

                //unit
                BodySegmentc segment = (i + 1 == total ? type.end.create(team()) : type.body.create(team())).as();
                //caculated pos
                Tmp.v1.trns(this.rotation, -(segment.hitSize() + 10));
                Tmp.v1.add(lastSegment.x, lastSegment.y);

                //set unit
                segment.setParent(self());
                //set x,y
                segment.set(Tmp.v1.x, Tmp.v1.y);
                //rotation
                segment.rotation((float)(Math.atan2(lastSegment.y - segment.y(), lastSegment.x - segment.x()) * 180 / Math.PI));
                //set segment
                segment.setSegment(lastSegment);
                //multiplayer compatibility
                Events.fire(new UnitCreateEvent(segment.as(), null, self()));
                if(!Vars.net.client()){
                    segment.add();
                }
                // register segment
                this.addChild(segment);
            }
            //set on snake
            this.setSneak = true;
        }
        ;
    }

    protected SegmentUnitType segmentType(){
        return (SegmentUnitType)type();
    }

    ;

    public void addChild(BodySegmentc child){
        if(child != null){
            //add child
            this._segments.add(child);
            //updateList
            this.totalSegments = this._segments.size;
        }
        ;
    }

    ;
    /*write(write) {
        this.super$write(write);
        //write id
        write.i(this.id);
        //write snake its on
        write.bool(this.setSneak);
    },
    read(read) {
        this.super$read(read);
        //read id
        this.id = read.i();
        //read snake its on
        this.setSneak = read.bool();
    }*/
}
