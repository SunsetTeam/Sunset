package sunset;

import arc.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.game.*;
import sunset.core.*;

import static mindustry.Vars.*;

public class SnVars {

    //end region
    public static SnLogic logic;
    public static SnSettings settings;
    public static SnSoundControl sound;
    public static SnUI ui;

    static{
        new SnVars();
    }

    /**
     * Used to load SnVars to computer memory causing the static block work.
     */
    public static void create(){

    }

    public static void init(){
        experimental = true;
    }

    public static void load(){

        if(!headless){
            sound = new SnSoundControl();
            Core.app.addListener(ui = new SnUI());
        }
        Core.app.addListener(logic = new SnLogic());
        Core.app.addListener(settings = new SnSettings());
    }

    public static String realName(MappableContent content){
        return content.minfo.mod == null ? content.name : content.name.substring(content.minfo.mod.name.length() + 1);
    }

}
