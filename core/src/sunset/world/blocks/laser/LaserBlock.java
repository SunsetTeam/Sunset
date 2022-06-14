package sunset.world.blocks.laser;

import arc.*;
import arc.graphics.g2d.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.world.*;

;

/** Base class for all laser blocks. */
public class LaserBlock extends Block{
    //on preUpdate, default all side-vars
    static{
        Events.run(Trigger.update, () -> {
            if(!Vars.state.isPlaying()) return;
            /*for(Building rawbuild : SnGroups.laserBuilds){
                LaserBlockBuild build = (LaserBlockBuild)rawbuild;
                build.leftInput = false;
                build.topInput = false;
                build.rightInput = false;
                build.downInput = false;
            }*/
        });
    }

//    @Annotations.Load("@-base")
    public TextureRegion base;
//    @Annotations.Load("@-top")
    public TextureRegion top;
//    @Annotations.Load("@-lens")
    public TextureRegion lens;
//    @Annotations.Load("@-edge0")
    public TextureRegion plugDark;
//    @Annotations.Load("@-edge1")
    public TextureRegion plugLight;
//    @Annotations.Load("@-all-edge")
    public TextureRegion allEdge;
    public boolean inputsLaser = false;
    public boolean outputsLaser = false;
    public boolean heats = false;
    public float heatLaserLimit = 50f;
    public float laserProduction = 0f;
    public float laserConsumption = 0f;
    public LaserBlockDrawer drawer = new LaserBlockDrawer();

    public LaserBlock(String name){
        super(name);
    }

    @Override
    public void load(){
        super.load();
        drawer.load(this);
    }

    @Override
    public TextureRegion[] icons(){
        return  new TextureRegion[]{base, allEdge, top};
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    public class LaserBlockBuild extends Building{
        public boolean leftInput = false,
        topInput = false,
        rightInput = false,
        downInput = false;
        LaserModule laser;
        boolean topOutput = false,
        rightOutput = false,
        downOutput = false,
        leftOutput = false;

        @Override
        public Building init(Tile tile, Team team, boolean shouldAdd, int rotation){
            laser = new LaserModule(this);
            return super.init(tile, team, shouldAdd, rotation);
        }

        @Override
        public void update(){
            super.update();
            laser.update();
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
            if(!this.added){
                Groups.all.add(this);
                Groups.build.add(this);
                this.added = true;
            }
        }

        @Override
        public void remove(){
            if(this.added){
                Groups.all.remove(this);
                Groups.build.remove(this);
                if(this.sound != null){
                    this.sound.stop();
                }

                this.added = false;
            }
        }

        @Override
        public LaserBlock block(){
            return (LaserBlock)this.block;
        }
    }
}