package sunset;

import acontent.ui.AdvancedContentInfoDialog;
import arc.Core;
import arc.Events;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.core.ContentLoader;
import mindustry.ctype.Content;
import mindustry.ctype.MappableContent;
import mindustry.ctype.UnlockableContent;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.game.EventType.ResetEvent;
import mindustry.gen.Groups;
import mindustry.type.*;
import mma.MMAMod;
import mma.ModListener;
import mma.annotations.ModAnnotations.ModAssetsAnnotation;
import sunset.content.SnUnitTypes;
import sunset.core.SnContentTranslation;
import sunset.gen.*;
import sunset.type.UnitData;
import sunset.utils.Utils;

import static mindustry.Vars.headless;
import static mma.ModVars.modInfo;

@ModAssetsAnnotation
public class Sunset extends MMAMod {


    public Sunset() {
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
            for (Deliverc deliver : SnGroups.delivers) {
                if (Groups.unit.getByID(deliver.id()) == null) {
                    removed.add(deliver);
                }
            }
            removed.each(SnGroups.delivers::remove);
        });

    }

    @Override
    public void init() {
        super.init();
        Events.run(ClientLoadEvent.class,()->Core.app.post(()->Vars.ui.announce(TestKotlin.testField,120f)));
        UnitData.init();
        AdvancedContentInfoDialog.init();
        Utils.setMenuUnit(SnUnitTypes.router);

    }

    @Override
    protected void modContent(Content c) {
        super.modContent(c);
        if (c instanceof MappableContent && !headless) {
            SnContentRegions.loadRegions((MappableContent) c);
        }
        if (c instanceof SectorPreset sector){
            sector.generator.map.mod=modInfo;
        }
    }

    @Override
    protected void created(Content c) {
        super.created(c);
        if (c instanceof UnlockableContent ) {
            UnlockableContent content = (UnlockableContent) c;
            SnContentTranslation.checkContent(content);

        }
    }

    @Override
    public void loadContent() {
        if (!headless) {
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
