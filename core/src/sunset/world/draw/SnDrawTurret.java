package sunset.world.draw;

import mindustry.world.draw.*;

import static mma.ModVars.fullName;

public class SnDrawTurret extends DrawTurret{
    public SnDrawTurret(String basePrefix){
        super(fullName(basePrefix));
    }
    public SnDrawTurret(){
        super("");
    }
}
