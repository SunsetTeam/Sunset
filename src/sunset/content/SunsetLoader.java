package sunset.content;

import mindustry.ctype.*;

public class SunsetLoader implements ContentList{
    private final ContentList[] contents = {
        new Snitems(),
        new Snblocks(),
    };

    public void load(){
        for(ContentList list : contents){
            list.load();
        }
    }
}
