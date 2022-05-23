package sunset;

import arc.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.game.*;
import mma.*;
import sunset.content.*;
import sunset.core.*;

import static mindustry.Vars.*;

public class SnVars extends ModVars{

    //core region
    private static final Seq<Runnable> onLoad = new Seq<>();
    private static final Runnable[] snContent = Seq.<Runnable>with(
    SnTeams::load,
    SnItems::load,
    SnGas::load,
    SnStatusEffects::load,
    SnLiquids::load,
    SnPayload::load,
    SnBullets::load,
    SnUnitTypes::load,
    new SnBlocks(),
//    SnOverride::load,
    //SnLoadouts::load,
    SnWeathers::load,
    SnPlanets::load,
    SnSectorPresets::load,
    SnTechTree::load
    ).flatMap(contentList -> Seq.with(contentList instanceof SnBlocks b ? b.list : new Runnable[]{contentList})).toArray(Runnable.class);
    //end region
    public static SnLogic logic;
    public static SnSettings settings;
    public static SnSoundControl sound;
    public static SnUI ui;

    static{
        new SnVars();
    }

    /**
     * just void method
     */
    public static void create(){

    }

    public static void init(){
        experimental = true;
    }

    public static void load(){
        onLoad.each(Runnable::run);
        onLoad.clear();

        if(!headless){
            sound = new SnSoundControl();
            listener.add(ui = new SnUI());
        }
        listener.add(logic = new SnLogic());
        listener.add(settings = new SnSettings());
    }

    public static String realName(MappableContent content){
        return content.minfo.mod == null ? content.name : content.name.substring(content.minfo.mod.name.length() + 1);
    }

    @Override
    protected void onLoad(Runnable runnable){
        onLoad.add(runnable);
    }

    @Override
    protected void showException(Throwable ex){
        Log.err(ex);
        if(headless){
            return;
        }
        if(Vars.ui == null){
            Events.on(EventType.ClientLoadEvent.class, e -> showException(ex));
            return;
        }
        Vars.ui.showException(ex);
    }

    @Override
    public void loadContent(){
        for(Runnable runnable : snContent){
            runnable.run();
        }
    }

    //    @Override
    public String getFullName(String name){
        return null;
    }
}
