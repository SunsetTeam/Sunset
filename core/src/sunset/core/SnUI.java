package sunset.core;

import arc.KeyBinds.*;
import mindustry.*;
import mma.core.*;

public class SnUI extends ModUI{
    public SnUI(){
        super(new KeyBind[0]);
    }

    @Override
    public void init(){
        super.init();
        SnPlanetSystem.setup();
    }
}
