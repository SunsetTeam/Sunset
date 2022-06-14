package sunset.content.affilitiation;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.graphics.*;

public enum SnBranches{
    none,
    green,
    yellow,
    missile,
    chaingun,
    energySphere,
    flamethrower,
    antiAir,
    emp,
    degree360,
    heavyArt,
    aim,
    boomerang,
    sniper,
    special,
    cLaser,
    machinegun;

    public static final SnBranches[] all = values();

    public Color color = Pal.shield;

    SnBranches(){
    }

    SnBranches(Color color){
        this.color = color;
    }

    public TextureRegion region(){
        return Core.atlas.find(("sunset-branch-" + name()));
    }

    public String localizedName(){
        return Core.bundle.format("branch." + name().toLowerCase());
    }
}
