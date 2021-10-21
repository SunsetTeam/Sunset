package sunset;

import arc.Events;
import arc.util.Log;
import mindustry.Vars;
import mindustry.ctype.*;
import mindustry.game.EventType.FileTreeInitEvent;
import mma.*;
import mma.annotations.ModAnnotations.*;
import sunset.content.*;
import sunset.core.*;
import sunset.gen.*;
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
@ModAssetsAnnotation
public class Sunset extends MMAMod{

    private static final Seq<Music> prevAmbient = new Seq<>(), prevDark = new Seq<>();
    private boolean lastMapSn;


    public Sunset(){
        super();
        SnVars.load();
        if(!headless){
            LoadedMod sn = mods.locateMod("sunset");

            Events.on(WorldLoadEvent.class, e -> {
                boolean isSn = state.map.mod != null && state.map.mod == sn;

                if(isSn != lastMapSn){
                    lastMapSn = !lastMapSn;
                    if(isSn){
                        Music(control.sound.ambientMusic, SnAudio.snAmbientMusic, prevAmbient);
                        Music(control.sound.darkMusic, SnAudio.snDarkMusic, prevDark);
                    }else{
                        Music(control.sound.ambientMusic, prevAmbient, null);
                        Music(control.sound.darkMusic, prevDark, null);
                    }
                }
            });
        }
        Events.on(ClientLoadEvent.class, (e) -> {
            SnAudio.reload();
        });
    }

    @Override
    public void init() {
        super.init();
        MissileLogic.init();
        UnitData.init();
        GeyserLogic.init();
        Vars.ui.content = new ContentInfoDialogExt(Vars.ui.content);
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
    protected void modContent(Content content){
        super.modContent(content);
        if (content instanceof MappableContent){
            SnContentRegions.loadRegions((MappableContent)content);
        }
    }

    @Override
    public void loadContent() {
        SnVars.inTry(SnMusics::load);
        SnVars.inTry(SnSounds::load);
        super.loadContent();
    }
}
