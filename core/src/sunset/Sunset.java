package sunset;

import acontent.ui.*;
import arc.*;
import arc.scene.event.*;
import arc.scene.ui.Image;
import arc.scene.ui.layout.*;
import arc.struct.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.core.GameState.*;
import mindustry.ctype.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.type.*;
import mma.*;
import sunset.annotations.SnAnnotations.*;
import sunset.content.*;
import sunset.core.*;
import sunset.gen.*;
import sunset.graphics.*;
import sunset.type.unitTypes.*;
import sunset.ui.*;
import sunset.utils.*;
import sunset.world.*;

import javax.swing.*;

import static arc.Core.scene;
import static mindustry.Vars.*;
import static mma.ModVars.*;
@TestAnnotation
public class Sunset extends MMAMod{

//    boolean validDependencies;

    public Sunset(){
        super();
//        Events.on(FileTreeInitEvent.class, e -> Core.app.post(SnShaders::load));

//        Events.on(DisposeEvent.class, e -> {
//            SnShaders.dispose();
//        });
     /*   validDependencies = SnDependencies.valid();
        if(!validDependencies*//* && !SnDependencies.existsGasLibraryJava()*//*){
            Fi file = modDirectory.child("GasLibrary.jar");
            file.write(getClass().getClassLoader().getResourceAsStream("GasLibrary.jar"), false);
            Log.debug("[Sunset] Loading mod @", file);
            LoadedMod mod = Reflect.invoke(Mods.class, mods, "loadMod", new Object[]{file}, Fi.class);
            Seq<LoadedMod> mods = Reflect.get(Mods.class, Vars.mods, "mods");
            mods.add(mod);
            validDependencies = SnDependencies.valid();
        }
        if(!validDependencies) return;*/

//        SnFonts.loadDefaultFont();
//        SnFonts.loadFonts();
        disableBlockOutline = true;
        SnVars.load();
        SnEntityMapping.init();
        SnCall.registerPackets();
        SnGroups.init();
        Events.on(ResetEvent.class, e -> {
            SnGroups.clear();
        });
        ModListener.updaters.add(() -> {
            Seq<Deliverc> removed = new Seq<>();
            for(Deliverc deliver : SnGroups.delivers){
                if(Groups.unit.getByID(deliver.id()) == null){
                    removed.add(deliver);
                }
            }
            removed.each(SnGroups.delivers::remove);
        });

    }

    @Override
    public void init(){
//        if(!validDependencies) return;
        SnAchievements.load();
        EnvironmentUpdateContainer.load();
        super.init();
        ModMetaDialogFinder.onNewListener(prev -> {
//            Group parent = prev.parent;
//            parent.removeChild(prev);
            prev.hide(null);
            new SunsetInfoDialog().show();
        });
//        SnModsDialog.init(SunsetInfoDialog::new);
        UnitData.init();
        AdvancedContentInfoDialog.init();
        Utils.setMenuUnit(SnUnitTypes.router);
        if(!mobile){
            Events.run(ClientLoadEvent.class, () -> {
                float duration = 90f;
                Table t = new Table();
                t.touchable = Touchable.disabled;
                t.margin(8f).button("test-sunset-achievements",  () -> {
                    new SnAchievementDialog().show();
                }).visible(Vars.state::isMenu).minSize(64f,32f);
//            t.update(() -> t.setPosition(Core.graphics.getWidth()/2f, Core.graphics.getHeight()/2f, Align.center));
                State[] state = {Vars.state.getState()};
                t.update(() -> {
                    t.setPosition(0, 162);
//                    t.visible=;
                    /*if(state[0] != Vars.state.getState()){
//                        t.remove();
                    }*/
                });
//                t.actions(Actions.fadeOut(duration, Interp.pow4In), Actions.remove());
                t.pack();
                t.act(0.1f);
                t.touchable=Touchable.enabled;
                scene.add(t);
            });
        }
        Events.on(ClientLoadEvent.class, e -> {
            Table t = new Table();
            t.add(new Image(Core.atlas.find("sunset-logo")));
            t.pack();
            scene.add(t.visible(() -> state.isMenu()));
        });
    }

    @Override
    protected void modContent(Content c){
        super.modContent(c);
        if(c instanceof MappableContent && !headless){
            SnContentRegions.loadRegions((MappableContent)c);
        }
        if(c instanceof SectorPreset sector){
            sector.generator.map.mod = modInfo;
        }
        if(c instanceof UnitType type){
            if(type.constructor == null) return;
            Unit unit = type.constructor.get();
            boolean uncontrollableType = type instanceof UncontrollableUnitType;
            boolean uncontrollableUnit = unit instanceof UncontrollableUnitc;
            if(uncontrollableUnit && !uncontrollableType){
                throw new IllegalArgumentException("uncontrollable unit named '" + type.name + "' must be of uncontrollable type");
            }
            if(!uncontrollableUnit && uncontrollableType){
                throw new IllegalArgumentException("unit named '" + type.name + "' with uncontrollable type wust be implement uncontrollable component");
            }
        }
    }

    @Override
    protected void created(Content c){
        super.created(c);
        if(c instanceof UnlockableContent){
            UnlockableContent content = (UnlockableContent)c;
            SnContentTranslation.checkContent(content);
        }
    }


    @Override
    public void loadContent(){
        /*if(!validDependencies){
            modInfo = mods.getMod(getClass());
            new ErrorContent(){{
                minfo.error = "@sunset-gas-library-disabled";
                minfo.sourceFile = new Fi("error-content");
                modInfo.erroredContent.add(this);
            }};
            return;
        }*/

        if(!headless){
            SnVars.inTry(SnShaders::init);
            SnVars.inTry(SnMusics::load);
            SnVars.inTry(SnSounds::load);
        }
        SnCacheLayer.init();
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
