package sunset.world.blocks.laser;

import arc.*;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.*;
import mindustry.annotations.Annotations;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.draw.*;
import mma.ModVars;
import sunset.gen.*;

/** Base class for all laser blocks. */
public class LaserBlock extends Block{
    public boolean inputsLaser = false;
    public boolean outputsLaser = false;

    public boolean heats = false;
    public float heatLimit = 50f;

    public float laserProduction = 0f;
    public float laserConsumption = 0f;

    public DrawBlock drawer = new DrawMulti(new DrawRegion("-bottom"),new LaserDraw(),new DrawDefault());

    //on preUpdate, default all side-vars
    static {
        Events.run(Trigger.update,()->{
            if (!Vars.state.isPlaying())return;
            for(Building rawbuild : SnGroups.laserBuilds){
                LaserModule module = ((LaserBuild)rawbuild).laser;
                module.leftInput = false;
                module.topInput = false;
                module.rightInput = false;
                module.downInput = false;
            }
        });
        Events.run(Trigger.draw,()->{
            Draw.draw(Layer.blockOver, () -> {

                for(Building rawbuild : SnGroups.laserBuilds){
                    LaserBuild build = (LaserBuild)rawbuild;
                    build.drawLasers();
                }
            });
        });
    }
    public LaserBlock(String name) {
        super(name);
    }

    @Override
    public void load(){
        super.load();
        drawer.load(this);
    }

    @Override
    public TextureRegion[] icons(){
        return !ModVars.packSprites ? new TextureRegion[]{fullIcon} : drawer.icons(this);
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    public class LaserBuild extends Building{
        LaserModule laser;

        @Override
        public Building init(Tile tile, Team team, boolean shouldAdd, int rotation){
            laser = new LaserModule(this);
            return super.init(tile, team, shouldAdd, rotation);
        }

        @Override
        public void update(){
            super.update();
            laser.laserBuildUpdate();
        }

        @Override
        public void draw(){
            drawer.draw(this);
        }

        public float getLaserProduction(){
            return 0f;
        }

        public float getLaserConsumption(){
            return 0f;
        }

        @Override
        public void add(){
            if (!this.added) {
                Groups.all.add(this);
                Groups.build.add(this);
                SnGroups.laserBuilds.add(this);
                this.added = true;
            }
        }

        @Override
        public void remove(){
            if (this.added) {
                Groups.all.remove(this);
                Groups.build.remove(this);
                SnGroups.laserBuilds.remove(this);
                if (this.sound != null) {
                    this.sound.stop();
                }

                this.added = false;
            }
        }

        @Override
        public LaserBlock block(){
            return (LaserBlock) this.block;
        }

        public void drawLasers(){

        }
    }
}