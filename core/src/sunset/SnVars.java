package sunset;

import arc.struct.*;
import mindustry.*;
import mindustry.ctype.*;
import mma.*;
import sunset.content.*;
import sunset.content.blocks.*;
import sunset.content.blocks.defense.*;
//import sunset.utils.V7.SunsetWaveSpawner;

public class SnVars extends ModVars{

    //public static SunsetWaveSpawner spawner;

    public static final int maxSchematicSize = 128;
    private static final Seq<Runnable> onLoad = new Seq<>();
    private static final ContentList[] snContent = {
    new SnStatusEffects(),
    new SnLiquids(),
    new SnItems(),
    new SnBullets(),
    new SnUnitTypes(),
    new SnTurrets(),
    new SnEnvironment(),
    new SnProjectors(),
    new SnWalls(),
    new SnDistribution(),
    new SnPower(),
    new SnProduction(),
    new SnWeathers(),
    new SnPlanets(),
    new SnLiquidBlocks(),
    new SnLogic(),
    new SnUnitBlocks(),
    new SnRaw(),
    new SnSectorPresets()
    };

    static{
        new SnVars();
    }
    /**just void method*/
    public static void create(){

    }

    public static void init(){

    }

    public static void load(){

    }

    @Override
    protected void onLoad(Runnable runnable){
        onLoad.add(runnable);
    }

    @Override
    protected void showException(Throwable ex){
        Vars.ui.showException(ex);
    }

    @Override
    public ContentList[] getContentList(){
        return snContent;
    }

    @Override
    public String getFullName(String name){
        return null;
    }
}
