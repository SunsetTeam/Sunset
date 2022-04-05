package sunset.world.blocks.laser;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.annotations.Annotations;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.logic.LogicBlock;
import sunset.graphics.Drawm;

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
    public LaserBlock(String name) {
        super(name);

    }
    public class LaserBlockBuild extends Building{
        LaserModule laser;
        LaserBlockDrawer drawer;

        @Override
        public Building init(Tile tile, Team team, boolean shouldAdd, int rotation){
            laser = new LaserModule(this);
            drawer = new LaserBlockDrawer(this);
            return super.init(tile, team, shouldAdd, rotation);
        }

        @Override
        public void updateTile(){
            laser.update();
        }
        @Override
        public void draw(){
            drawer.draw();
            drawer.leftInput = false;
            drawer.topInput = false;
            drawer.rightInput = false;
            drawer.downInput = false;
            float z = Draw.z();
            Draw.z(Layer.blockOver);
            block().drawPlaceText("Laser\nin: " + laser.in + "\nout: " + laser.out + "", tileX(), tileY(), true);
            Draw.z(z);
        }

        public float getLaserProduction(){
            return 0f;
        }

        public float getLaserConsumption(){
            return 0f;
        }

        @Override
        public LaserBlock block(){
            return (LaserBlock) this.block;
        }

    }
}