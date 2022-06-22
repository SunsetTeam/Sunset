package sunset.world.blocks.laser;

import arc.scene.ui.Button;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.world.Tile;

import static mindustry.Vars.tilesize;

/**
 * unused (for now)
 * */
public class FocusingPrism extends LaserBlock{
    public FocusingPrism(String name) {
        super(name);
        solid = true;
        update = true;
        configurable = true;
    }
    public class FocusingPrismBuild extends LaserBuild{
        Laser laserToTarget;
        float angle = 0f;

        @Override
        public Building init(Tile tile, Team team, boolean add, int rotation){
            super.init(tile, team, add, rotation);
            laserToTarget = new Laser(){{
                build = FocusingPrismBuild.this;
                start.set(tile.x * tilesize + block().offset, tile.y * tilesize + block().offset);
                length = Math.max(Vars.world.width() * tilesize, Vars.world.height() * tilesize);
                offset = size * 1.5f;
            }};
            return this;
        }

        @Override
        public void update(){
            super.update();
            laserToTarget.angle = angle;
            laserToTarget.updateTile();
            //angle += edelta();
            //Log.info(angle);
        }
        @Override
        public void draw(){
            super.draw();
        }

        @Override
        public void drawLasers(){
            super.drawLasers();
            laserToTarget.draw();
        }

        @Override
        public void remove(){
            super.remove();
//            laserToTarget.remove();
        }

        @Override
        public void buildConfiguration(Table table){
            Button lb = new ImageButton();
            lb.image(Icon.rotate);
            /*
            lb.dragged((x, y) -> {

            });
            lb.keyDown(KeyCode.mouseLeft, ()->{
                angle += edelta();
            });
             */
            lb.update(()->{
                if (lb.isPressed()){
                    angle += edelta();
                }
            });
            table.add(lb);
            Button rb = new ImageButton();
            rb.image(Icon.rotate);
            /*
            rb.dragged((x, y) -> {
                angle -= edelta();
            });
            rb.keyDown(KeyCode.mouseLeft, ()->{
                angle += edelta();
            });
             */
            rb.update(()->{
                if(rb.isPressed())
                    angle -= edelta();
            });
            table.add(rb);
        }
    }
}
