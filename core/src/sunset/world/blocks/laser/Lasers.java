package sunset.world.blocks.laser;

import arc.struct.*;
import sunset.type.*;

/** Lasers class manages {@link Laser} for {@link LaserNode}*/
public class Lasers{
    public Seq<Laser> allLasers = new Seq<>();
    LaserBlock.LaserBlockBuild build;

    public Lasers(LaserBlock.LaserBlockBuild build){
        this.build = build;
    }

    public void updateTile(){
        for(Laser laser : allLasers){
            laser.updateTile();
        }
    }

    public void draw(){
        for(Laser laser : allLasers){
            laser.draw();
        }
    }

    private Laser getBySide(int side){
        return allLasers.find(laser -> laser.side() == side);
    }

    public Laser getLeft(){
        return getBySide(BlockSide.left);
    }

    public Laser getTop(){
        return getBySide(BlockSide.top);
    }

    public Laser getRight(){
        return getBySide(BlockSide.right);
    }

    public Laser getDown(){
        return getBySide(BlockSide.bottom);
    }

    public void setEnabled(boolean leftOutput, boolean topOutput, boolean rightOutput, boolean downOutput){
        for(Laser laser : allLasers){
            switch(laser.side()){
                case BlockSide.right -> laser.enabled = rightOutput;
                case BlockSide.top -> laser.enabled = topOutput;
                case BlockSide.left -> laser.enabled = leftOutput;
                case BlockSide.bottom -> laser.enabled = downOutput;
            }
        }
    }
}
