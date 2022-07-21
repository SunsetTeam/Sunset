package sunset.world.blocks.environment;

import arc.util.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.StaticWall;

public class BreakableEnvWall extends StaticWall {
    public int stages;
   public BreakableEnvWall(String name) {
        super(name);
        this.update=true;
       breakable = true;
       destructible = true;
//            solid = true;
       alwaysReplace = false;
       instantDeconstruct = false;
    }

    @Override
    public void init() {
        if(health == health / stages) {
            Log.info("test");
        }
    }

    @Override
    public void drawBase(Tile tile) {
        if(tile.build!=null) {
            tile.build.draw();
        } else {
        }
    }

    void superDrawBase(Tile tile) {
        super.drawBase(tile);
    }

    @Override
    public boolean hasBuilding() {
        return true;
    }

    public class BreakableStaticWallBuild extends Building{
        @Override
        public void draw() {
            superDrawBase(this.tile);
            drawCracks();
        }
    }
}
