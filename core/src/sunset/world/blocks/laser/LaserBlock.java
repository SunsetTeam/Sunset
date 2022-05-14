package sunset.world.blocks.laser;

import arc.*;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.struct.*;
import arc.util.Log;
import arc.util.Time;
import mindustry.*;
import mindustry.annotations.Annotations;
import mindustry.game.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.logic.LogicBlock;
import sunset.gen.*;
import sunset.graphics.Drawm;

/** Base class for all laser blocks. */
public class LaserBlock extends Block{
    @Annotations.Load("@-base")
    public TextureRegion base;
    @Annotations.Load("@-top")
    public TextureRegion top;
    @Annotations.Load("@-lens")
    public TextureRegion lens;
    @Annotations.Load("@-edge0")
    public TextureRegion plugDark;
    @Annotations.Load("@-edge1")
    public TextureRegion plugLight;

    public boolean inputsLaser = false;
    public boolean outputsLaser = false;

    public float laserProduction = 0f;
    public float laserConsumption = 0f;
    //on preUpdate, default all side-vars
    static {
        Events.run(Trigger.update,()->{
            if (!Vars.state.isPlaying())return;
            for(Building rawbuild : SnGroups.laserBuilds){
                LaserBlockBuild build = (LaserBlockBuild)rawbuild;
                build.leftInput = false;
                build.topInput = false;
                build.rightInput = false;
                build.downInput = false;
            }
        });
    }
    public LaserBlock(String name) {
        super(name);

    }
    @SuppressWarnings("InnerClassMayBeStatic")
    public class LaserBlockBuild extends Building{
        LaserModule laser;
        LaserBlockDrawer drawer;
        public boolean leftInput = false,
                topInput = false,
                rightInput = false,
                downInput = false;
        boolean topOutput = false,
                rightOutput = false,
                downOutput = false,
                leftOutput = false;

        @Override
        public Building init(Tile tile, Team team, boolean shouldAdd, int rotation){
            laser = new LaserModule(this);
            drawer = new LaserBlockDrawer(this);
            return super.init(tile, team, shouldAdd, rotation);
        }

        @Override
        public void update(){
            super.update();
            laser.update();
        }

        @Override
        public void draw(){
            drawer.draw();
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
    }
}