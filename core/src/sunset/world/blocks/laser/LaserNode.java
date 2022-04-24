package sunset.world.blocks.laser;

import arc.*;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;

import static mindustry.Vars.*;

/*@Struct
class LaserEnableStateStruct{
    boolean right;
    boolean top;
    boolean left;
    boolean down;
}*/

public class LaserNode extends LaserBlock{
    @SuppressWarnings("PointlessBitwiseExpression")
    public LaserNode(String name){
        super(name);
        update = true;
        configurable = true;
        solid = true;
        clipSize = 500f;
        config(Integer.class, (LaserNodeBuild b, Integer value) -> {
            Log.info("config");
            b.rightOutput = (value & 0b0001) >> 0 == 1;
            b.topOutput = (value & 0b0010) >> 1 == 1;
            b.leftOutput = (value & 0b0100) >> 2 == 1;
            b.downOutput = (value & 0b1000) >> 3 == 1;
        });
    }

    @SuppressWarnings("PointlessBitwiseExpression")
    protected static void configureState(LaserNodeBuild build, boolean right, boolean top, boolean left, boolean down){
        int state = 0;
        if(right) state += 1 << 0;
        if(top) state += 1 << 1;
        if(left) state += 1 << 2;
        if(down) state += 1 << 3;
        build.configure(state);
    }

    public class LaserNodeBuild extends LaserBlockBuild{
        Lasers lasers;
        @Override
        public Building init(Tile tile, Team team, boolean shouldAdd, int rotation){
            lasers = new Lasers();
            super.init(tile, team, shouldAdd, rotation);
            //top
            lasers.allLasers.add(new Laser(){{
                build = LaserNodeBuild.this;
                angle = 90f;
                length = Math.max(Vars.world.width() * tilesize, Vars.world.height() * tilesize);
                offset = size * 1.5f;
                start.set(tile.x * tilesize + block().offset, tile.y * tilesize + block().offset);
            }});
            //left
            lasers.allLasers.add(new Laser(){{
                build = LaserNodeBuild.this;
                angle = 180f;
                length = Math.max(Vars.world.width() * tilesize, Vars.world.height() * tilesize);
                offset = size * 1.5f;
                start.set(tile.x * tilesize + block().offset, tile.y * tilesize + block().offset);
            }});
            //right
            lasers.allLasers.add(new Laser(){{
                build = LaserNodeBuild.this;
                angle = 0f;
                length = Math.max(Vars.world.width() * tilesize, Vars.world.height() * tilesize);
                offset = size * 1.5f;
                start.set(tile.x * tilesize + block().offset, tile.y * tilesize + block().offset);
            }});
            //down
            lasers.allLasers.add(new Laser(){{
                build = LaserNodeBuild.this;
                angle = 270f;
                length = Math.max(Vars.world.width() * tilesize, Vars.world.height() * tilesize);
                offset = size * 1.5f;
                start.set(tile.x * tilesize + block().offset, tile.y * tilesize + block().offset);
            }});
            return this;
        }

        @Override
        public void updateTile(){
            super.updateTile();
            laser.outputs = (leftOutput ? 1 : 0) + (topOutput ? 1 : 0) + (rightOutput ? 1 : 0) + (downOutput ? 1 : 0);
            lasers.getLeft().enabled = leftOutput;
            lasers.getTop().enabled = topOutput;
            lasers.getRight().enabled = rightOutput;
            lasers.getDown().enabled = downOutput;
            lasers.updateTile();
        }

        @Override
        public void draw(){
            super.draw();
            lasers.draw();
            //float z = Draw.z();
            //Draw.z(Layer.blockOver);
            //трёхэтажный дебаг : )
            //block().drawPlaceText("Laser\nin: " + laser.in + "\nout: " + laser.out + "\ntopIn: " + topInput + ", topOut: " + topOutput + ", leftIn: " + leftInput + ", leftOut: " + leftOutput + ", downIn: " + downInput + ", downOut: " + downOutput + ", rightIn: " + rightInput + ", rightOut: " + rightOutput + "", tileX(), tileY(), true);
            //Draw.z(z);
        }

        @Override
        public void updateTableAlign(Table t){
            float addPos = Mathf.ceil(this.block.size / 2f) - 1;
            Vec2 pos = Core.input.mouseScreen((this.x) + addPos - 0.5f, this.y + addPos);
            t.setSize(block.size * 12f);
            t.setSize(this.block.size * 12f);
            t.setPosition(pos.x, pos.y, 0);
        }

        @Override
        public void buildConfiguration(Table t){
            t.add();
            t.button(Icon.up, () -> {
                Log.info("top");
                topOutput = !topOutput;
                configureState();
            }).update(b -> {
                b.setDisabled(topInput && !topOutput);
                b.setColor(topOutput ? Color.green : Color.red);
            });
            t.add().row();
            t.button(Icon.left, () -> {
                Log.info("left");
                leftOutput = !leftOutput;
                configureState();
            }).update(b -> {
                b.setDisabled(leftInput && !leftOutput);
                b.setColor(leftOutput ? Color.green : Color.red);
            });
            t.add();
            t.button(Icon.right, () -> {
                Log.info("right");
                rightOutput = !rightOutput;
                configureState();
            }).update(b -> {
                b.setDisabled(rightInput && !rightOutput);
                b.setColor(rightOutput ? Color.green : Color.red);
            });
            t.row();
            t.add();
            t.button(Icon.down, () -> {
                Log.info("down");
                downOutput = !downOutput;
                configureState();
            }).update(b -> {
                b.setDisabled(downInput && !downOutput);
                b.setColor(downOutput ? Color.green : Color.red);
            });
        }

        private void configureState(){
            LaserNode.configureState(this, rightOutput, topOutput, leftOutput, downOutput);
        }

        @Override
        public void write(Writes w){
            super.write(w);
            w.bool(leftOutput);
            w.bool(topOutput);
            w.bool(rightOutput);
            w.bool(downOutput);
        }

        @Override
        public void read(Reads r, byte revision){
            leftOutput = r.bool();
            topOutput = r.bool();
            rightOutput = r.bool();
            downOutput = r.bool();
        }
    }
}
