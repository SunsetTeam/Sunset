package sunset.world.blocks.laser;

import arc.struct.Seq;

public class Lasers {
    LaserBlock.LaserBlockBuild self;
    public Seq<Laser> allLasers = new Seq<>();

    public Lasers(){
    }

    public void updateTile(){
        for (Laser laser : allLasers){
            laser.updateTile();
        }
    }
    public void draw(){
        for (Laser laser : allLasers){
            laser.draw();
        }
    }
    public Laser getByAngle(float angle){
        return allLasers.find(laser -> laser.angle == angle);
    }
    public Laser getLeft(){
        return getByAngle(180f);
    }
    public Laser getTop(){
        return getByAngle(90f);
    }
    public Laser getRight(){
        return getByAngle(0f);
    }
    public Laser getDown(){
        return getByAngle(270f);
    }
}
