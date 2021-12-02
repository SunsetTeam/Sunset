package sunset.world.blocks.test;

import mindustry.gen.Building;
import mindustry.world.Block;
import sunset.world.draw.DrawModBlock;

public class ModBlock extends Block {
    public int mode;

    /** Mod block for testing various things. */
    public ModBlock(String name) {
        super(name);
    }

    public int mode() {
        if (mode < 0) return 0;
        return mode;
    }

    public DrawModBlock drawer = new DrawModBlock();

    public class ModBuilding extends Building {
        @Override
        public void draw(){
            drawer.draw(this);
        }
    }
}
