package sunset.world.blocks.laser;

import arc.*;
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

public class LaserNode extends LaserBlock{
    public LaserNode(String name){
        super(name);
        update = true;
        configurable = true;
        solid = true;
        clipSize = 500f;
        config(Integer.class, (LaserNodeBuild b, Integer value) -> {
            Log.info("config");
            switch(value){
                case 0 -> b.leftOutput = !b.leftOutput;
                case 1 -> b.topOutput = !b.topOutput;
                case 2 -> b.rightOutput = !b.rightOutput;
                case 3 -> b.downOutput = !b.downOutput;
            }
        });
    }

    public class LaserNodeBuild extends LaserBlockBuild{
        boolean topOutput = false,
        rightOutput = false,
        downOutput = false,
        leftOutput = false;
        Lasers lasers;

        @Override
        public Building init(Tile tile, Team team, boolean shouldAdd, int rotation){
            lasers = new Lasers();
            Building b = super.init(tile, team, shouldAdd, rotation);
            //top
            lasers.allLasers.add(new Laser(){{
                self = LaserNodeBuild.this;
                angle = 90f;
                length = Math.max(Vars.world.width() * tilesize, Vars.world.height() * tilesize);
                offset = size * 1.5f;
                start.set(tile.x * tilesize + block().offset, tile.y * tilesize + block().offset);
            }});
            //left
            lasers.allLasers.add(new Laser(){{
                self = LaserNodeBuild.this;
                angle = 180f;
                length = Math.max(Vars.world.width() * tilesize, Vars.world.height() * tilesize);
                offset = size * 1.5f;
                start.set(tile.x * tilesize + block().offset, tile.y * tilesize + block().offset);
            }});
            //right
            lasers.allLasers.add(new Laser(){{
                self = LaserNodeBuild.this;
                angle = 0f;
                length = Math.max(Vars.world.width() * tilesize, Vars.world.height() * tilesize);
                offset = size * 1.5f;
                start.set(tile.x * tilesize + block().offset, tile.y * tilesize + block().offset);
            }});
            //down
            lasers.allLasers.add(new Laser(){{
                self = LaserNodeBuild.this;
                angle = 270f;
                length = Math.max(Vars.world.width() * tilesize, Vars.world.height() * tilesize);
                offset = size * 1.5f;
                start.set(tile.x * tilesize + block().offset, tile.y * tilesize + block().offset);
            }});
            return b;
        }

        @Override
        public void updateTile(){
            laser.outputs = (leftOutput ? 1 : 0) + (topOutput ? 1 : 0) + (rightOutput ? 1 : 0) + (downOutput ? 1 : 0);
            super.updateTile();
            lasers.getLeft().enabled = leftOutput;
            lasers.getTop().enabled = topOutput;
            lasers.getRight().enabled = rightOutput;
            lasers.getDown().enabled = downOutput;
            lasers.update();
        }

        @Override
        public void draw(){
            drawer.draw();
            drawer.leftInput = leftOutput;
            drawer.topInput = topOutput;
            drawer.rightInput = rightOutput;
            drawer.downInput = downOutput;
            lasers.draw();
            float z = Draw.z();
            Draw.z(Layer.blockOver);
            //block().drawPlaceText("Laser\nin: " + laser.in + "\nout: " + laser.out + "", tileX(), tileY(), true);
            Draw.z(z);
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
                //top = !top;
                configure(1);
            });
            t.add().row();
            t.button(Icon.left, () -> {
                Log.info("left");
                //left = !left;
                configure(0);
            });
            t.add();
            t.button(Icon.right, () -> {
                Log.info("right");
                //right = !right;
                configure(2);
            });
            t.row();
            t.add();
            t.button(Icon.down, () -> {
                Log.info("down");
                //down = !down;
                configure(3);
            });
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
