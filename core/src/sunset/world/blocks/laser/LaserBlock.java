package sunset.world.blocks.laser;

import acontent.world.meta.AStats;
import arc.graphics.g2d.TextureRegion;
import arc.util.Log;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.annotations.Annotations;
import mindustry.entities.Damage;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.StatUnit;
import sunset.world.meta.SnStat;

/** Main class for all blocks related to lasers. */
public class LaserBlock extends Block {
    /** Consumes laser. False for laser sources. */
    public boolean consumesLaser = true;
    /** Outputs laser. False for any kind of factory and laser suppressors. */
    public boolean outputsLaser = false;
    /** Allows block overheating if current charge is equals or more than maxCharge. */
    public boolean overheats = true;
    /** Max charge for this block. If charge will be equals or more than this threshold, block will start to overheat.  */
    public float maxCharge = 20f;
    /** Max number of in/out links allowed for this block. */
    public int maxLinks = 4;
    public float laserGeneration = 0f;
    public float laserConsumption = 0f;
    public float criticalHeatTime = 120f;

    @Annotations.Load("@-base")
    public TextureRegion nodeBase;
    @Annotations.Load("@-top")
    public TextureRegion nodeTop;
    @Annotations.Load("@-lens")
    public TextureRegion nodeLens;
    @Annotations.Load("@-edge0")
    public TextureRegion nodeEdgeDark;
    @Annotations.Load("@-edge1")
    public TextureRegion nodeEdgeLight;
    @Annotations.Load("@-all-edge")
    public TextureRegion nodeAllEdge;

    AStats aStats = new AStats();

    //protected static DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"),
    //autosaveDate = DateTimeFormatter.ofPattern("MM-dd-yyyy_HH-mm-ss");
    public LaserBlock(String name) {
        super(name);
        clipSize = 500f;
        stats = aStats.copy(stats);
    }

    @Override
    public void setStats(){
        super.setStats();
        aStats.add(SnStat.maxCharge, maxCharge, StatUnit.none);
        aStats.add(SnStat.maxLinks, maxLinks, StatUnit.none);
        if (laserConsumption > 0)
            aStats.add(SnStat.laserConsumption, laserConsumption, StatUnit.none);
        if (laserGeneration > 0)
            aStats.add(SnStat.laserProduction, laserGeneration, StatUnit.none);
    }

    public class LaserBlockBuild extends Building{
        float heat = 0f;
        LaserModule laserModule;
        LaserBlockDrawer drawer;

        @Override
        public void updateTile(){
            if (!laserModule.init)
                laserModule.init();
            laserModule.update();

            if(overheats){
                Log.info("obj: @", id);
                if(laserModule.getCharge() > maxCharge)
                    heat();
                else
                    coolOff();
            }
        }

        public void heat(){
            Log.info("heating: @", heat);
            heat += getProgressIncrease(criticalHeatTime);
            if(heat >= 1f)
                Damage.tileDamage(null, tileX(), tileY(), 8f, health);
        }

        public void coolOff(){
            Log.info("cooling: @", heat);
            heat -= Math.min(heat,  getProgressIncrease(criticalHeatTime));
        }

        public Building init(Tile tile, Team team, boolean shouldAdd, int rotation){
            laserModule = new LaserModule(this);
            laserModule.maxLinks = maxLinks;
            drawer = new LaserBlockDrawer(this);
            drawer.initRegions(nodeBase, nodeTop, nodeEdgeDark, nodeEdgeLight, nodeLens);

            return super.init(tile, team, shouldAdd, rotation);
        }

        @Override
        public void draw(){
            drawer.draw();
            //ADrawf.drawText(x, y + 24, 0.24f, Pal.accent, Strings.format("Inputs: @\nOutputs: @\nInput laser: @\nOutput laser: @", laserModule.input.size, laserModule.output.size, laserModule.in, laserModule.out));
        }

        /** Laser production. Used for generators.
         * Laser module use this.
         * Override by superclasses. */
        // todo (Laser amplifier ?)
        public float getLaserProduction(){
            return laserGeneration;
        }

        /** Laser consumption. Used for factories.
         * Laser module use this.
         * Override by superclasses. */
        public float getLaserConsumption(){
            return laserConsumption;
        }

        @Override
        public void onRemoved(){
            super.onRemoved();
            laserModule.clear();
        }

        @Override
        public void write(Writes w){
            super.write(w);
            laserModule.write(w);
        }

        @Override
        public void read(Reads r, byte revision){
            super.read(r, revision);
            laserModule.read(r);
        }
    }
}
