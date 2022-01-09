package sunset;

import acontent.ui.*;
import arc.*;
import arc.struct.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.ctype.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.type.*;
import mma.*;
import mma.annotations.ModAnnotations.*;
import sunset.content.*;
import sunset.core.*;
import sunset.gen.*;
import sunset.type.*;
import sunset.type.unitTypes.*;
import sunset.utils.*;

import static mindustry.Vars.*;
import static mma.ModVars.*;

@ModAssetsAnnotation
public class Sunset extends MMAMod{


    public Sunset(){
        super();
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
        super.init();
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
