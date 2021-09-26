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
import arc.audio.*;
import arc.struct.*;
import mindustry.game.EventType.*;
import mindustry.mod.Mods.*;

import static mindustry.Vars.*;
import static mindustry.Vars.headless;

public class Sunset extends Mod {

    private static final Seq<Music> prevAmbient = new Seq<>(), prevDark = new Seq<>();
    private boolean lastMapEso;
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

    public Sunset(){
        if(!headless){
            LoadedMod eso = mods.locateMod("sunset");

            Events.on(WorldLoadEvent.class, e -> {
                boolean isSn = state.map.mod != null && state.map.mod == eso;

                if(isSn != lastMapEso){
                    lastMapEso = !lastMapEso;
                    if(isSn){
                        Music(control.sound.ambientMusic, SnMusic.snAmbientMusic, prevAmbient);
                        Music(control.sound.darkMusic, SnMusic.snDarkMusic, prevDark);
                    }else{
                        Music(control.sound.ambientMusic, prevAmbient, null);
                        Music(control.sound.darkMusic, prevDark, null);
                    }
                }
            });
        }
    }

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

    private void Music(Seq<Music> target, Seq<Music> replacement, Seq<Music> save){
        if(save != null){
            save.clear();
            save.addAll(target);
        }
        target.clear();
        target.addAll(replacement);
    }
    
    @Override
    public void loadContent() {
        new SnMusic().load();
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
