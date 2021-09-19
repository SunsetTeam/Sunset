package sunset;

import arc.Events;
import arc.util.Log;
import mindustry.Vars;
import mindustry.ctype.ContentList;
import mindustry.game.EventType.FileTreeInitEvent;
import mindustry.mod.Mod;
import sunset.content.*;
import sunset.content.blocks.*;
import sunset.content.blocks.defense.SnProjectors;
import sunset.content.blocks.defense.SnTurrets;
import sunset.content.blocks.defense.SnWalls;
import sunset.type.UnitData;
import sunset.ui.ContentInfoDialogExt;
import sunset.utils.Utils;
import sunset.world.GeyserLogic;
import sunset.world.MissileLogic;
import static mindustry.Vars.headless;

public class Sunset extends Mod {
    private final ContentList[] SnContent = {
            new SnStatusEffects(),
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
            new SnSectorPresets()
    };

    @Override
    public void init() {
        MissileLogic.init();
        UnitData.init();
        GeyserLogic.init();
        Vars.ui.content = new ContentInfoDialogExt();
        Utils.setMenuUnit(SnUnitTypes.router);
        /*Log.info("startUp");
        Events.on(ClientLoadEvent.class, e -> {
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("Start up");
                dialog.cont.add("Start up").row();
                dialog.cont.image(Core.atlas.find("sunset-startUpImage")).pad(20f).row();
                dialog.cont.button("OK", dialog::hide).size(500f, 50f);
                dialog.show();
            });
        });*/
    }
    
    @Override
    public void loadContent() {
        for(ContentList list : SnContent) {
            list.load();
            Log.info("@: Loaded content list: @", getClass().getSimpleName(), list.getClass().getSimpleName());
        }
        if (headless) {
            Events.on(FileTreeInitEvent.class, e -> SnSounds.load());
        } else {
            SnSounds.load();
            Log.info("@: Loaded content list: SnSounds", getClass().getSimpleName());
        }
    }
}
