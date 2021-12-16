package sunset;

import arc.Events;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.ctype.ContentList;
import mindustry.ctype.MappableContent;
import mindustry.game.EventType;
import mma.ModVars;
import sunset.content.*;
import sunset.core.SnLogic;
import sunset.core.SnSettings;
import sunset.core.SnSoundControl;

import static mindustry.Vars.headless;

public class SnVars extends ModVars {

    //core region
    private static final Seq<Runnable> onLoad = new Seq<>();
    private static final ContentList[] snContent = Seq.<ContentList>with(
            new SnItems(),
            new SnStatusEffects(),
            new SnLiquids(),
            new SnBullets(),
            new SnUnitTypes(),
            new SnBlocks(),
            //new SnLoadouts(),
            new SnWeathers(),
            new SnPlanets(),
            new SnSectorPresets(),
            new SnTechTree()/*,
            new SnTeams()*/
    ).flatMap(contentList -> Seq.with(contentList instanceof SnBlocks b ? b.list : new ContentList[]{contentList})).toArray(ContentList.class);
    //end region
    public static SnLogic logic;
    public static SnSettings settings;
    public static SnSoundControl sound;

    static {
        new SnVars();
    }

    /**
     * just void method
     */
    public static void create() {

    }

    public static void init() {

    }

    public static void load() {
        onLoad.each(Runnable::run);
        onLoad.clear();

        if (!headless) {
            sound = new SnSoundControl();
        }
        listener.add(logic = new SnLogic());
        listener.add(settings = new SnSettings());
    }

    public static String realName(MappableContent content) {
        return content.minfo.mod == null ? content.name : content.name.substring(content.minfo.mod.name.length() + 1);
    }

    @Override
    protected void onLoad(Runnable runnable) {
        onLoad.add(runnable);
    }

    @Override
    protected void showException(Throwable ex) {
        Log.err(ex);
        if (headless) {
            return;
        }
        if (Vars.ui == null) {
            Events.on(EventType.ClientLoadEvent.class, e -> showException(ex));
            return;
        }
        Vars.ui.showException(ex);
    }

    @Override
    public ContentList[] getContentList() {
        return snContent;
    }

    @Override
    public String getFullName(String name) {
        return null;
    }
}
