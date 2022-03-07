package sunset.world.blocks.environment;

import mindustry.world.blocks.environment.StaticWall;

public class BreakableEnvWall extends StaticWall {
    public int stages;

    public BreakableEnvWall(String name) {
        super(name);
        destructible = true;
        alwaysReplace = false;
    }
}
