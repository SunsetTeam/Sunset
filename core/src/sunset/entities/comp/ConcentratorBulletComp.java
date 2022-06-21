package sunset.entities.comp;

import arc.math.geom.*;
import arc.struct.*;
import arc.util.io.*;
import mindustry.annotations.*;
import mindustry.annotations.Annotations.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mma.annotations.ModAnnotations.*;
import sunset.gen.*;
import sunset.type.*;
import sunset.world.blocks.defense.turrets.MagneticTurret.*;

import static mindustry.Vars.*;

/** {@link ConcentratorBullet}*/
@Component()
@EntityDef(value = {ConcentratorBulletc.class,Bulletc.class})
abstract class ConcentratorBulletComp implements Bulletc{
    public Team bornTeam;
    @Import
    Team team;
    @Import
    Entityc owner;
    @Import
    float x, y, damage, lastX, lastY, time, lifetime, aimX, aimY, originX, originY, fdata;
    // setting this variable to true prevents lifetime from decreasing for a frame.
    @Import
    transient boolean keepAlive, absorbed, hit;
    @Import
    transient Trail trail;
    boolean spin;
    BlockBulletState bulletState=BlockBulletState.backHome;
    //    @Import
    Building building;
    @Import
    private float rotation;
    public float startRotationAngle=-1;
    public MagneticTurretBuild buildOwner(){
        return building.as();
    }

    @Override
    @Replace
    public void update(){
        if (building==null && owner!=null){
            building = owner.as();
        }
        if (building == null || !building.isValid() || building.team != team ) {
            absorb();
            return;
        }
        time = 0;
        lifetime = Float.MAX_VALUE;
        originalUpdate();
    }

    public void nextState(){
        bulletState=bulletState.next();
    }
    @Override
    public void afterRead(){
        owner= building;
        if (building!=null){
            buildOwner().registerBullet(self());
        }
    }

    @SuperMethod(parentName = "update")
    protected abstract void originalUpdate();

/*
    @Annotations.CallSuper
    @ReplaceInternalImpl
    public void read(Reads read) {

            this.bornTeam = mindustry.io.TypeIO.readTeam(read);
//            this.building = mindustry.io.TypeIO.readBuilding(read);
            int pos = read.i();
            System.out.println("pos: "+pos);
            System.out.println("tile: "+world.tile(pos));
            this.building= world.build(pos);
            System.out.println("building: "+building);
            this.collided = mindustry.io.TypeIO.readIntSeq(read);
            this.damage = read.f();
            this.data = mindustry.io.TypeIO.readObject(read);
            this.fdata = read.f();
            this.lifetime = read.f();
            this.rotation = read.f();
            this.spin = read.bool();
            this.team = mindustry.io.TypeIO.readTeam(read);
            this.time = read.f();
            this.type = mindustry.Vars.content.getByID(mindustry.ctype.ContentType.bullet, read.s());
            this.vel = mindustry.io.TypeIO.readVec2(read, this.vel);
            this.x = read.f();
            this.y = read.f();

        afterRead();
    }*/

  /*  @Annotations.CallSuper
    @ReplaceInternalImpl
    public void write(Writes write) {
        mindustry.io.TypeIO.writeTeam(write, this.bornTeam);
        mindustry.io.TypeIO.writeBuilding(write, this.building);
        mindustry.io.TypeIO.writeIntSeq(write, this.collided);
        write.f(this.damage);
        mindustry.io.TypeIO.writeObject(write, this.data);
        write.f(this.fdata);
        write.f(this.lifetime);
        mindustry.io.TypeIO.writeEntity(write, this.owner);
        write.f(this.rotation);
        write.bool(this.spin);
        mindustry.io.TypeIO.writeTeam(write, this.team);
        write.f(this.time);
        write.s(this.type.id);
        mindustry.io.TypeIO.writeVec2(write, this.vel);
        write.f(this.x);
        write.f(this.y);

    }*/

}
