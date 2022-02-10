package sunset.world.blocks.laser;

import arc.graphics.g2d.TextureRegion;
import arc.util.Log;
import arc.util.Strings;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.annotations.Annotations;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.io.TypeIO;
import mindustry.world.Block;
import mindustry.world.Tile;
import sunset.graphics.Drawm;

import java.time.*;
import java.time.format.*;
import java.util.*;

public class LaserBlock extends Block {
    public boolean consumesLaser = true;
    public boolean outputsLaser = false;
    public boolean overheats = true;
    public float maxCharge = 20f;
    public int maxLinks = 4;

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

    protected static DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"),
    autosaveDate = DateTimeFormatter.ofPattern("MM-dd-yyyy_HH-mm-ss");
    public LaserBlock(String name) {
        super(name);
    }

    public class LaserBlockBuild extends Building{
        LaserModule laserModule;
        LaserBlockDrawer drawer;

        @Override
        public void updateTile(){
            laserModule.update();
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
            //Drawm.drawLabel(Tmp.v1.set(x + 8, y + 8), 0.23f, Pal.accent, Strings.format("Inputs: @\nOutputs: @\nInput laser: @\nOutput laser: @", laserModule.input.size, laserModule.output.size, laserModule.in, laserModule.out));
        }

        // override by superclasses
        // laser production for generating blocks
        // todo (Laser amplifier ?)
        public float getLaserProduction(){
            return 0f;
        }

        public float getLaserConsumption(){
            return 0f;
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
