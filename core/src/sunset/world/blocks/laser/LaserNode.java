package sunset.world.blocks.laser;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.util.Tmp;
import arc.util.io.*;
import mindustry.*;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.Bar;
import mindustry.world.*;

import static mindustry.Vars.*;

/*@Struct
class LaserEnableStateStruct{
    boolean right;
    boolean top;
    boolean left;
    boolean down;
}*/

/** Class for laser-transferring blocks.*/
public class LaserNode extends LaserBlock{
    public Effect nodeHitEffect = Fx.none;
    @SuppressWarnings("PointlessBitwiseExpression")
    public LaserNode(String name){
        super(name);
        update = true;
        configurable = true;
        solid = true;
//        clipSize = 500f;
        config(Integer.class, (LaserNodeBuild b, Integer value) -> {
            //Log.info("config");
            b.rightOutput = (value & 0b0001) >> 0 == 1;
            b.topOutput = (value & 0b0010) >> 1 == 1;
            b.leftOutput = (value & 0b0100) >> 2 == 1;
            b.downOutput = (value & 0b1000) >> 3 == 1;
        });
    }
    @Override
    public void setBars(){
        super.setBars();
        if(heats){
            addBar("chargeBar", (LaserBuild entity) ->
                    new Bar(() -> Core.bundle.format("bar.laser-input", entity.laser.rawInput, entity.block().heatLaserLimit),
                    () -> {
                        if(entity.laser.rawInput < entity.block().heatLaserLimit){
                            return Pal.powerBar;
                        }
                        else
                            return Color.red;
                    },
                    () -> entity.laser.rawInput / entity.block().heatLaserLimit));
        }
    }

    @SuppressWarnings("PointlessBitwiseExpression")
    protected static int getState(boolean right, boolean top, boolean left, boolean down){
        int state = 0;
        if(right) state += 1 << 0;
        if(top) state += 1 << 1;
        if(left) state += 1 << 2;
        if(down) state += 1 << 3;
        return state;
    }

    public class LaserNodeBuild extends LaserBuild{
        Lasers lasers;

        @Override
        public Building init(Tile tile, Team team, boolean shouldAdd, int rotation){
            lasers = new Lasers(this);
            super.init(tile, team, shouldAdd, rotation);
            //top
            lasers.allLasers.add(new Laser(){{
                angle = 90f;
                build = LaserNodeBuild.this;
                length = Math.max(Vars.world.width() * tilesize, Vars.world.height() * tilesize);
                start.set(build.tile.x * tilesize + build.block().offset, build.tile.y * tilesize + build.block().offset);
                hitEffect = nodeHitEffect;
            }});
            //left
            lasers.allLasers.add(new Laser(){{
                angle = 180f;
                build = LaserNodeBuild.this;
                length = Math.max(Vars.world.width() * tilesize, Vars.world.height() * tilesize);
                start.set(build.tile.x * tilesize + build.block().offset, build.tile.y * tilesize + build.block().offset);
                hitEffect = nodeHitEffect;
            }});
            //right
            lasers.allLasers.add(new Laser(){{
                angle = 0f;
                build = LaserNodeBuild.this;
                length = Math.max(Vars.world.width() * tilesize, Vars.world.height() * tilesize);
                start.set(build.tile.x * tilesize + build.block().offset, build.tile.y * tilesize + build.block().offset);
                hitEffect = nodeHitEffect;
            }});
            //down
            lasers.allLasers.add(new Laser(){{
                angle = 270f;
                build = LaserNodeBuild.this;
                length = Math.max(Vars.world.width() * tilesize, Vars.world.height() * tilesize);
                start.set(build.tile.x * tilesize + build.block().offset, build.tile.y * tilesize + build.block().offset);
                hitEffect = nodeHitEffect;
            }});
            return this;
        }

        @Override
        public void updateTile(){
            super.updateTile();
            laser.outputs = (leftOutput ? 1 : 0) + (topOutput ? 1 : 0) + (rightOutput ? 1 : 0) + (downOutput ? 1 : 0);
            lasers.setEnabled(leftOutput, topOutput, rightOutput, downOutput);
            lasers.updateTile();
        }

        @Override
        public void drawLasers(){
            super.drawLasers();
            lasers.draw();
        }

        @Override
        public void draw(){
            drawer.draw(this);
            float z = Draw.z();
            Draw.z(Layer.blockOver);
            //трёхэтажный дебаг : )
//            ADrawf.drawText();
            //String output = Seq.with(downOutput, leftOutput, topOutput, rightOutput).toString("", it -> Mathf.num(it) + "");
           // String input = Seq.with(downInput, leftInput, topInput, rightInput).toString("", it -> Mathf.num(it) + "");
            //block().drawPlaceText("Laser\nin: " + laser.in + "\nout: " + laser.out + "\noutput: " + output + ",\n_input: " + input, tileX(), tileY(), true);
            //Draw.z(z);
            //Log.info("block draw, time: @", Time.time);
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
            float size = 48f;
            t.button(Icon.up, () -> {
                topOutput = !topOutput;
                configureState();
            }).size(size).update((b) -> {
                b.setDisabled(topInput&& !topOutput);
                b.getStyle().imageUpColor=topOutput ? Color.lime : Color.valueOf("f25555");
//                b.setColor();
            });
            t.add().row();
            t.button(Icon.left, () -> {
                //Log.info("left");
                leftOutput = !leftOutput;
                configureState();
            }).update(b -> {
                b.setDisabled(leftInput&& !leftOutput);
                b.getStyle().imageUpColor=leftOutput ? Color.lime : Color.valueOf("f25555");
//                b.setColor(left ? Color.lime : Color.valueOf("f25555"));
            });
            t.add();
            t.button(Icon.right, () -> {
                //Log.info("right");
                rightOutput = !rightOutput;
                configureState();
            }).update(b -> {
                b.setDisabled(rightInput && !rightOutput);
                b.getStyle().imageUpColor=rightOutput ? Color.lime : Color.valueOf("f25555");
//                b.setColor(right ? Color.lime : Color.valueOf("f25555"));
            });
            t.row();
            t.add();
            t.button(Icon.down, () -> {
                //Log.info("down");
                downOutput = !downOutput;
                configureState();
            }).update(b -> {
                b.setDisabled(downInput && !downOutput);
                b.getStyle().imageUpColor=downOutput ? Color.lime : Color.valueOf("f25555");
//                b.setColor(down ? Color.lime : Color.valueOf("f25555"));
            });
        }

        private void configureState(){
            int state = LaserNode.getState(rightOutput, topOutput, leftOutput, downOutput);
            configure(state);
        }

        @Override
        public Integer config(){
            return LaserNode.getState(rightOutput, topOutput, leftOutput, downOutput);
        }

        @Override
        @SuppressWarnings("PointlessBitwiseExpression")
        public void write(Writes w){
            super.write(w);
            int state = 0;
            if(rightOutput) state += 1 << 0;
            if(topOutput) state += 1 << 1;
            if(leftOutput) state += 1 << 2;
            if(downOutput) state += 1 << 3;
            w.b(state);
        }

        @Override
        public byte version(){
            return 1;
        }

        @Override
        @SuppressWarnings("PointlessBitwiseExpression")
        public void read(Reads r, byte revision){
            if(revision == 0){
                leftOutput = r.bool();
                topOutput = r.bool();
                rightOutput = r.bool();
                downOutput = r.bool();
                return;
            }
            byte value = r.b();
            rightOutput = (value & 0b0001) >> 0 == 1;
            topOutput = (value & 0b0010) >> 1 == 1;
            leftOutput = (value & 0b0100) >> 2 == 1;
            downOutput = (value & 0b1000) >> 3 == 1;
        }
    }
}
