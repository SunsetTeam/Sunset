package sunset;

import arc.Events;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.ctype.ContentList;
import mindustry.game.EventType;
import mma.ModVars;
import sunset.content.*;
import sunset.core.SnLogic;
import sunset.content.SnTeams;
//import sunset.utils.V7.SunsetWaveSpawner;

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
    //public static SunsetWaveSpawner spawner;
    public static SnLogic logic;
    public static SnSettings settings;

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

        listener.add(logic = new SnLogic());
        listener.add(settings=new SnSettings());
    }

    @Override
    protected void onLoad(Runnable runnable) {
        onLoad.add(runnable);
    }

    @Override
    protected void showException(Throwable ex) {
        Log.err(ex);
        if (Vars.headless) {
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
