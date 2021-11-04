package sunset;

import arc.Events;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.ctype.ContentList;
import mindustry.game.EventType;
import mma.ModVars;
import sunset.content.*;
import sunset.content.blocks.*;
import sunset.content.blocks.defense.SnProjectors;
import sunset.content.blocks.defense.SnTurrets;
import sunset.content.blocks.defense.SnWalls;
import sunset.core.SnLogic;
//import sunset.utils.V7.SunsetWaveSpawner;

public class SnVars extends ModVars {

    //core region
    private static final Seq<Runnable> onLoad = new Seq<>();
    private static final ContentList[] snContent = {
            new SnItems(),
            new SnStatusEffects(),
            new SnLiquids(),
            new SnBullets(),
            new SnUnitTypes(),
            //blocks region
            new SnProjectors(),
            new SnTurrets(),
            new SnWalls(),

            new SnDistribution(),
            new SnEnvironment(),
            new SnLiquidBlocks(),
            new SnLogicBlocks(),
            new SnPower(),
            new SnProduction(),
            new SnRaw(),
            new SnUnitBlocks(),
            //region end
            //new SnLoadouts(),
            new SnWeathers(),
            new SnPlanets(),
            new SnSectorPresets(),
            new SnTechTree(),
    };
    //end region
    //public static SunsetWaveSpawner spawner;
    public static SnLogic logic;

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
