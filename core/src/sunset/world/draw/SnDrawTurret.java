package sunset.world.draw;

import mindustry.world.draw.*;

public class SnDrawTurret extends DrawTurret{
    public SnDrawTurret(String basePrefix){
        super(("sunset-"+basePrefix));
    }
    public SnDrawTurret(){
        super("");
    }
}
