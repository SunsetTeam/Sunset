package sunset.entities.comp;

import arc.func.*;
import arc.graphics.g2d.*;
import arc.math.geom.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.annotations.Annotations.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mma.annotations.ModAnnotations.*;
import org.jetbrains.annotations.*;
import sunset.gen.*;
import sunset.type.unitTypes.*;

import java.util.*;

@Component
abstract class SegmentComp implements Entityc, Unitc, Segmentc{
    private static final int nullSegmentId = -1;
    private static final int noSegmentId = -2;
    private static int lastGlobalSegmentId = 0;
    public boolean segmentBuilding = false;
    public boolean completedSnake = false;
    public float segmentBuildTimer = 0;
    transient Segmentc previous = null;
    transient Segmentc next = null;
    @Import
    float x, y, hitSize, minFormationSpeed, rotation, elevation;
    private int previousId = -2;
    private int nextId = -2;
    private int globalSegmentId = nextGlobalSegmentId();

    public static int nextGlobalSegmentId(){
        int id = lastGlobalSegmentId;
        while(lastGlobalSegmentId++ == nullSegmentId || lastGlobalSegmentId == noSegmentId){
        }
        return id;
    }

    public static int countSegmentsFromHead(Segmentc head){
        Segmentc segmentc = head;
        int counter = 1;
        while(segmentc.next() != null){
            segmentc = segmentc.next();
            counter++;
        }
        return counter;
    }

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

    public Segmentc findHead(){
        return findHead(self());
    }

    public Segmentc findTail(){
        return findTail(self());
    }

    ;

    public static int countSegmentsFromTail(Segmentc tail){
        Segmentc segmentc = tail;
        int counter = 1;
        while(segmentc.previous() != null){
            segmentc = segmentc.previous();
            counter++;
        }
        return counter;
    }

    @Override
    @MethodPriority(-1000)
    @Replace
    @ReplaceInternalImpl
    public void write(Writes write){
        nextId = previousId = noSegmentId;
        nextId = next == null ? nullSegmentId : next.globalSegmentId();
        previousId = previous == null ? nullSegmentId : previous.globalSegmentId();
        superWrite(write);
    }

    @SuperMethod(parentName = "write")
    private void superWrite(Writes write){
    }

    ;

    public int globalSegmentId(){
        return globalSegmentId;
    }

    @Override
    @MethodPriority(-1_000_000)
    @GlobalReturn
    public void update(){
//update
        boolean updateValues = false;
        if(nextId != noSegmentId){
            next = SnGroups.segments.find(s -> s.globalSegmentId() == nextId);
            nextId = noSegmentId;
            updateValues = true;
        }
        if(previousId != noSegmentId){
            previous = SnGroups.segments.find(s -> s.globalSegmentId() == previousId);
            previousId = noSegmentId;
            updateValues = true;
        }
        if(updateValues) return;
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

    @Override
    public void draw(){
        calculateNextPosition(Tmp.v1);
        Draw.draw(Layer.blockOver, () -> Drawf.construct(Tmp.v1.x, Tmp.v1.y, type().region,
        Tmp.v1.angleTo(this), segmentBuildTimer/segmentType().segmentBuildTime, 1f, Time.time));
    }

    public void calculateNextPosition(Vec2 vec2){
        vec2.trns((isTail() ? rotation : angleTo(next)) + 180, segmentType().offsetSegment);
    }

    public boolean isHead(){
        return previous == null;
    }


    @NotNull
    public Iterator<Segmentc> iterator(){
        ;
        return new Iterator<>(){
            Segmentc head = findHead();

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

    @NotNull
    public Iterable<Segmentc> iterable(){
        ;
        return this::iterator;
    }

    @NotNull
    public Iterator<Segmentc> reverseIterator(){
        ;
        return new Iterator<>(){
            Segmentc tail = findTail();

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

    @NotNull
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
        return countSegmentsFromTail(findTail());
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
