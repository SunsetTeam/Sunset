package sunset;

import acontent.ui.*;
import arc.*;
import arc.files.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.ctype.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.mod.Mods.*;
import mindustry.type.*;
import mma.*;
import mma.annotations.ModAnnotations.*;
import sunset.content.*;
import sunset.core.*;
import sunset.gen.*;
import sunset.type.unitTypes.*;
import sunset.ui.*;
import sunset.utils.*;

import static mindustry.Vars.*;
import static mma.ModVars.modInfo;

@ModAssetsAnnotation
@DependenciesAnnotation
public class Sunset extends MMAMod{

    boolean validDependencies;

    public Sunset(){
        super();
        validDependencies = SnDependencies.valid();
        if(!validDependencies){
            if(!SnDependencies.existsGasLibraryJava()){
                Fi file = modDirectory.child("GasLibrary.jar");
                file.write(getClass().getClassLoader().getResourceAsStream("GasLibrary.jar"), false);
                Log.debug("[Sunset] Loading mod @", file);
                LoadedMod mod = Reflect.invoke(Mods.class, mods, "loadMod", new Object[]{file}, Fi.class);
                Seq<LoadedMod> mods = Reflect.get(Mods.class, Vars.mods, "mods");
                mods.add(mod);
            }
            if(!headless){
                /*Events.run(ClientLoadEvent.class,()->{
                    new BaseDialog("sunset-error"){{
                       cont.add("Please download or enable GasLibrary.");
                       addCloseButton();
                       addCloseListener();
                    }};
                });*/

            }
            return;
        }
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
        if(!validDependencies) return;
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
        if(!validDependencies){
            modInfo = mods.getMod(getClass());
            new ErrorContent(){{
                minfo.error = "@sunset-gas-library-disabled";
                modInfo.erroredContent.add(this);
            }};
            return;
        }

        if(!headless){
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
