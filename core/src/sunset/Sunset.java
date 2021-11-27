package sunset;

import acontent.ui.AdvancedContentInfoDialog;
import arc.Events;
import mindustry.Vars;
import mindustry.core.*;
import mindustry.ctype.*;
import mindustry.gen.Groups;
import mma.*;
import mma.annotations.ModAnnotations.*;
import mma.gen.ModGroups;
import sunset.content.*;
import sunset.core.*;
import sunset.gen.*;
import sunset.type.UnitData;
import sunset.utils.Utils;
import sunset.world.GeyserLogic;
import arc.audio.*;
import arc.struct.*;
import mindustry.game.EventType.*;
import mindustry.mod.Mods.*;

import java.io.IOException;

import static mindustry.Vars.*;
import static mindustry.Vars.headless;
import static mma.ModVars.modInfo;

@ModAssetsAnnotation
public class Sunset extends MMAMod{

    private static final Seq<Music> prevAmbient = new Seq<>(), prevDark = new Seq<>();
    private boolean lastMapSn;


    public Sunset(){
        super();
        SnVars.load();
        SnEntityMapping.init();
        SnCall.registerPackets();
        SnGroups.init();
        Events.on(ResetEvent.class,e->{
            SnGroups.clear();
        });
        ModListener.updaters.add(()->{
            Seq<Deliverc> removed=new Seq<>();
            for (Deliverc deliver : SnGroups.delivers) {
                if (Groups.unit.getByID(deliver.id())==null){
                    removed.add(deliver);
                }
            }
            removed.each(SnGroups.delivers::remove);
        });
        if(!headless){

            Events.on(WorldLoadEvent.class, e -> {
                boolean isSn = state.map.mod != null && state.map.mod == modInfo;

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
            SnSettings.init();
        });
    }

    @Override
    public void init() {
        super.init();
        UnitData.init();
        AdvancedContentInfoDialog.init();
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
        if (content instanceof MappableContent && !headless){
            SnContentRegions.loadRegions((MappableContent)content);
        }
    }

    @Override
    public void loadContent() {
        if (!headless){
            SnVars.inTry(SnMusics::load);
            SnVars.inTry(SnSounds::load);
        }
        ContentLoader prev = Vars.content;
        /*content=new ContentLoader(){
            @Override
            public void handleContent(Content content){
                if (content instanceof MappableContent){

                }
                super.handleContent(content);
            }
        };*/
        super.loadContent();
    }
}
