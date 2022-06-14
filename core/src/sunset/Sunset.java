package sunset;

import acontent.ui.*;
import arc.*;
import arc.scene.event.*;
import arc.scene.ui.layout.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.core.GameState.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import sunset.content.*;
import sunset.ui.*;
import sunset.utils.*;

import static mindustry.Vars.mobile;

public class Sunset extends Mod{

    public Sunset(){
        super();
//        Events.on(FileTreeInitEvent.class, e -> Core.app.post(SnShaders::load));

//        Events.on(DisposeEvent.class, e -> {
//            SnShaders.dispose();
//        });
        SnVars.load();

    }

    @Override
    public void init(){
        SnAchievements.load();
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
                t.margin(8f).button("test-sunset-achievements", () -> {
                    new SnAchievementDialog().show();
                }).visible(Vars.state::isMenu).minSize(64f, 32f);
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
                t.touchable = Touchable.enabled;
                Core.scene.add(t);
            });
        }
    }


    @Override
    public void loadContent(){
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
