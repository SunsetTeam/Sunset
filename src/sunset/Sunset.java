package sunset;

import arc.util.Log;
import sunset.content.Snblocks;
import sunset.content.Snitems;
import mindustry.ctype.ContentList;
import mindustry.mod.Mod;

public class Sunset extends Mod{

    private final ContentList[] sunsetContent = {
      new Snitems(),
      new Snblocks()
    };

    @Override
    public void loadContent(){
        for(ContentList list : sunsetContent){
            list.load();

            Log.info("@: Loaded content list: @", getClass().getSimpleName(), list.getClass().getSimpleName());
        }
    }
}