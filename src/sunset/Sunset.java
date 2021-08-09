package sunset;

import arc.Events;
import arc.util.Log;
import mindustry.Vars;
import mindustry.ctype.ContentList;
import mindustry.game.EventType.FileTreeInitEvent;
import mindustry.mod.Mod;
//import mindustry.ui.dialogs.BaseDialog;
import sunset.content.*;
import sunset.content.blocks.*;
import sunset.ui.ContentInfoDialogExt;
import sunset.world.MissileLogic;


public class Sunset extends Mod {
    private final ContentList[] SnContent = {
            new SnItems(),
            new SnEnvironment(),
            new SnStatusEffects(),
            new SnBullets(),
            new SnDefense(),
            new SnOther(),
            new SnPower(),
            new SnTurrets(),
            new SnUnitTypes(),
            new SnWeathers(),
            new SnProduction(),
            new SnPlanets(),
            new SnSectorPresets()
    };

    @Override
    public void init() {
        MissileLogic.init();
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
    public void loadContent(){
        for(ContentList list : SnContent){
            list.load();
            Log.info("@: Loaded content list: @", getClass().getSimpleName(), list.getClass().getSimpleName());
        }
        if (Vars.headless) {
            Events.on(FileTreeInitEvent.class, e -> {
                SnSounds.load();
            });
        } else {
            SnSounds.load();
            Log.info("@: Loaded content list: SnSounds", getClass().getSimpleName());
        }
    }
}
