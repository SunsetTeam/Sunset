package sunset.world.blocks.environment;

import arc.util.Log;
import mindustry.world.blocks.environment.StaticWall;

//TODO

public class BreakableEnvWall extends StaticWall {
    public int stages;

    public BreakableEnvWall(String name) {
        super(name);
        destructible = true;
        alwaysReplace = false;
        variants = 2;
        stages = 2;
    }

    @Override
    public void init() {
        if(health == health / stages) {
            Log.info("test");
        }
    }
}
