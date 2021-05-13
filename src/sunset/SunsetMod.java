package sunset;

import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import sunset.content.*;
import arc.util.Log;
import mindustry.ctype.*;

    public class SunsetMod extends Mod{


    private static final ContentList[] content = {
    new Snitems(),
    new Snblocks(),

    };

    @Override
    public void init(){
        
    }
    
    @Override
    public void loadContent(){
        for(ContentList list : sunsetContent){
            list.load();

            Log.info("@: Loaded content list: @", getClass().getSimpleName(), list.getClass().getSimpleName());
        }
    }
}
