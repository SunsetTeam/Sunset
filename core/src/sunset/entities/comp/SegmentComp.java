package sunset.entities.comp;

import arc.func.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.annotations.Annotations.*;
import mindustry.gen.*;
import sunset.gen.*;
import sunset.type.unitTypes.*;

import java.util.*;

@Component
abstract class SegmentComp implements Entityc, Unitc, Segmentc{
    public int totalSegments = 0;
    public float timeOut = 1;
    public boolean segmentBuilding = false;
    public boolean completedSnake = false;
    public float segmentBuildTimer = 0;
    Segmentc previous = null;
    Segmentc next = null;

    @Import
    float x, y, hitSize, minFormationSpeed, rotation, elevation;

    public static int countSegmentsFromHead(Segmentc head){
        Segmentc segmentc = head;
        int counter = 1;
        while(segmentc.next() != null){
            segmentc = segmentc.next();
            counter++;
        }
        return counter;
    }

    ;

    public static Segmentc findHead(Segmentc segmentc){
        while(segmentc.previous() != null){
            segmentc = segmentc.previous();
        }
        return segmentc;
    }

    public static Segmentc findTail(Segmentc segmentc){
        while(segmentc.next() != null){
            segmentc = segmentc.next();
        }
        return segmentc;
    }

    public static int countSegmentsFromTail(Segmentc tail){
        Segmentc segmentc = tail;
        int counter = 1;
        while(segmentc.previous() != null){
            segmentc = segmentc.previous();
            counter++;
        }
        return counter;
    }

    ;

    @Override
    @Replace
    public boolean serialize(){
        return previous == null;
    }

    ;

    public void update(){
        //update
        this._update();
    }

    ;

    @Override
    public void destroy(){
        if(next != null){
            next.previous(null);
        }
        if(previous != null){
            previous.next(null);
            previous.segmentBuildTimer(segmentBuildTimer);
            previous.segmentBuilding(segmentBuilding);
        }

    }

    public void _update(){
        if(isTail() && !completedSnake){
            if(!segmentBuilding){
                segmentBuilding = segmentType().lengthSnake > sizeFromTail();
                if(!segmentBuilding){
                    eachSegment(cont -> completedSnake = true);
                }
            }
            if(segmentBuilding){
                segmentBuildTimer += Time.delta;
                if(segmentBuildTimer >= segmentType().segmentBuildTime){
                    resetBuilding();
                    calculateNextPosition(Tmp.v1);

                    addChild(segmentType().spawn(team(), Tmp.v1).as());
                }
            }
        }
    }

    public void calculateNextPosition(Vec2 vec2){
        vec2.trns((isHead() ? rotation : angleTo(next))+180, segmentType().offsetSegment);
    }

    public boolean isHead(){
        return previous == null;
    }


    public Iterator<Segmentc> iterator(){
        ;
        return new Iterator<>(){
            Segmentc head = findHead(self());

            @Override
            public boolean hasNext(){
                return head.next() != null;
            }

            @Override
            public Segmentc next(){
                return head = head.next();
            }
        };
    }

    public Iterable<Segmentc> iterable(){
        ;
        return this::iterator;
    }

    public Iterator<Segmentc> reverseIterator(){
        ;
        return new Iterator<>(){
            Segmentc tail = findTail(self());

            @Override
            public boolean hasNext(){
                return tail.previous() != null;
            }

            @Override
            public Segmentc next(){
                return tail = tail.previous();
            }
        };
    }

    public Iterable<Segmentc> reverseIterable(){
        ;
        return this::reverseIterator;
    }

    private void eachSegment(Cons<Segmentc> cons){
        for(Segmentc segmentc : iterable()){
            cons.get(segmentc);
        }
    }

    public int sizeFromTail(){
        return countSegmentsFromTail(findTail(self()));
    }

    private boolean isTail(){
        return next == null;
    }

    private boolean head(){
        return previous == null;
    }

    ;

    public boolean canDead(int amount){
        return amount < 1;
    }

    ;

    public SegmentUnitType segmentType(){
        return (SegmentUnitType)type();
    }


    public void addChild(Segmentc child){
        if(isTail()){
            setNext(child);
        }else findTail(self()).addChild(child);
    }

    public void setNext(Segmentc segmentc){
        next = segmentc;
        segmentc.previous(this);
        segmentc.segmentBuildTimer(segmentBuildTimer);
        segmentc.segmentBuilding(segmentBuilding);
        resetBuilding();
    }

    public void resetBuilding(){
        segmentBuildTimer = 0;
        segmentBuilding = false;
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
