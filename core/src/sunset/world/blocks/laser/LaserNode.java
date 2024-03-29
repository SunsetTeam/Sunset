package sunset.world.blocks.laser;

import arc.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;

import static mindustry.Vars.tilesize;

/*@Struct
class LaserEnableStateStruct{
    boolean right;
    boolean top;
    boolean left;
    boolean down;
}*/

/** Class for laser-transferring blocks. */
public class LaserNode extends LaserBlock{
    public Effect nodeHitEffect = Fx.none;

    @SuppressWarnings("PointlessBitwiseExpression")
    public LaserNode(String name){
        super(name);
        update = true;
        configurable = true;
        solid = true;
        rotate = true;
        rotateDraw = false;
//        clipSize = 500f;
        config(Integer.class, (LaserNodeBuild b, Integer value) -> {
            b.laser.configOutput(value);
        });
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

    @Override
    public void setBars(){
        super.setBars();
        if(heats){
            addBar("chargeBar", (LaserBuild entity) ->
            new Bar(() -> Core.bundle.format("bar.laser-input", entity.laser.rawInput, entity.block().heatLimit),
            () -> {
                if(entity.laser.rawInput < entity.block().heatLimit){
                    return Pal.powerBar;
                }else
                    return Color.red;
            },
            () -> entity.laser.rawInput / entity.block().heatLimit));
        }
    }

    @Override
    @SuppressWarnings("PointlessBitwiseExpression")
    public Object pointConfig(Object objectConfig, Cons<Point2> transformer){
        if(!(objectConfig instanceof Integer config)) return super.pointConfig(objectConfig, transformer);

        boolean rightOutput = (config & 0b0001) >> 0 == 1;
        boolean topOutput = (config & 0b0010) >> 1 == 1;
        boolean leftOutput = (config & 0b0100) >> 2 == 1;
        boolean downOutput = (config & 0b1000) >> 3 == 1;
        transformer.get(Tmp.p1.set(1, 0));
        int steps = (int)(Tmp.v1.set(Tmp.p1.x, Tmp.p1.y).angle() / 90);
        return getState(
        rotateBoolean(steps, rightOutput, topOutput, leftOutput, downOutput),
        rotateBoolean(steps, topOutput, leftOutput, downOutput, rightOutput),
        rotateBoolean(steps, leftOutput, downOutput, rightOutput, topOutput),
        rotateBoolean(steps, downOutput, rightOutput, topOutput, leftOutput)
        );
    }

    private boolean rotateBoolean(int steps, boolean step0, boolean step1, boolean step2, boolean step3){
        return switch(steps % 4){
            case 0 -> step0;
            case 1 -> step1;
            case 2 -> step2;
            case 3 -> step3;
            default -> throw new IllegalStateException("Unexpected value: " + steps);
        };
    }

    @Override
    @SuppressWarnings("PointlessBitwiseExpression")
    public void flipRotation(BuildPlan req, boolean x){
        super.flipRotation(req, x);
        /*if(!(req.config instanceof Integer config)) return;

        boolean rightOutput = (config & 0b0001) >> 0 == 1;
        boolean topOutput = (config & 0b0010) >> 1 == 1;
        boolean leftOutput = (config & 0b0100) >> 2 == 1;
        boolean downOutput = (config & 0b1000) >> 3 == 1;
//        int steps = (int)(Tmp.v1.set(Tmp.p1.x, Tmp.p1.y).angle() / 90);
        req.config = getState(
        x ? leftOutput : rightOutput,
        !x ? topOutput : downOutput,
        x ? rightOutput : leftOutput,
        !x ? downOutput:topOutput
        );*/
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
            //todo refactor laser.somesideInput copy-paste somehow...
            t.add();
            float size = 48f;
            t.button(Icon.up, () -> {
                laser.topOutput = !laser.topOutput;
                configureState();
            }).size(size).update((b) -> {
                b.setDisabled(laser.topInput && !laser.topOutput);
                b.getStyle().imageUpColor = laser.topOutput ? Color.lime : Color.valueOf("f25555");
//                b.setColor();
            });
            t.add().row();
            t.button(Icon.left, () -> {
                //Log.info("left");
                laser.leftOutput = !laser.leftOutput;
                configureState();
            }).update(b -> {
                b.setDisabled(laser.leftInput && !laser.leftOutput);
                b.getStyle().imageUpColor = laser.leftOutput ? Color.lime : Color.valueOf("f25555");
//                b.setColor(left ? Color.lime : Color.valueOf("f25555"));
            });
            t.add();
            t.button(Icon.right, () -> {
                //Log.info("right");
                laser.rightOutput = !laser.rightOutput;
                configureState();
            }).update(b -> {
                b.setDisabled(laser.rightInput && !laser.rightOutput);
                b.getStyle().imageUpColor = laser.rightOutput ? Color.lime : Color.valueOf("f25555");
//                b.setColor(right ? Color.lime : Color.valueOf("f25555"));
            });
            t.row();
            t.add();
            t.button(Icon.down, () -> {
                //Log.info("down");
                laser.downOutput = !laser.downOutput;
                configureState();
            }).update(b -> {
                b.setDisabled(laser.downInput && !laser.downOutput);
                b.getStyle().imageUpColor = laser.downOutput ? Color.lime : Color.valueOf("f25555");
//                b.setColor(down ? Color.lime : Color.valueOf("f25555"));
            });
        }

        private void configureState(){
            int state = LaserNode.getState(laser.rightOutput, laser.topOutput, laser.leftOutput, laser.downOutput);
            configure(state);
        }

        @Override
        public Integer config(){
            return LaserNode.getState(laser.rightOutput, laser.topOutput, laser.leftOutput, laser.downOutput);
        }


        @Override
        @SuppressWarnings("PointlessBitwiseExpression")
        public void write(Writes w){
            super.write(w);
            laser.writeOutputs(w);
        }

        @Override
        public byte version(){
            return 1;
        }

        @Override
        @SuppressWarnings("PointlessBitwiseExpression")
        public void read(Reads r, byte revision){
            super.read(r, revision);
            laser.readOutputs(r, revision);
        }
    }
}
