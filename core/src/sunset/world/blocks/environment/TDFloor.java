package sunset.world.blocks.environment;

import mindustry.world.blocks.environment.Floor;
import mindustry.world.meta.BuildVisibility;

public class TDFloor extends Floor {

    public TDFloor(String name) {
        super(name);
        solidifes = true;
        buildVisibility = BuildVisibility.editorOnly;
    }
    //todo: blocks can be placed on this floor
}
