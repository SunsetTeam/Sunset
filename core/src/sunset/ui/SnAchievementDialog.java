package sunset.ui;

import arc.*;
import arc.scene.event.*;
import mindustry.gen.*;
import mindustry.ui.dialogs.*;
import sunset.game.*;

public class SnAchievementDialog extends BaseDialog{
    SnAchievementTab tab = null;

    public SnAchievementDialog(){
        super("sn-achievements.title");
        resized(this::rebuild);
        rebuild();
    }

    private void rebuild(){
        clearChildren();
        image(Core.atlas.find("sn-achivements-icon")).left();
        add("@sn-achivements.title").left();
        add().growX();
        label(() -> Core.bundle.format("@sn.acheivements.completed", countCompleted()));
        button(Icon.exit, this::hide).size(32f).row();
        if(tab == null){
            buildTabs();
        }

    }

    private void buildSelected(){
        int cols = (int)(Core.scene.getWidth() / 274);
        float colsSize = Core.scene.getWidth() / cols;
        pane(cont).width(colsSize);
        for(int i = 0; i < SnAchievementTab.tabs.size; i++){
            SnAchievementTab tab = SnAchievementTab.tabs.get(i);
            cont.table(tab::buildTable).size(colsSize, colsSize * 1.5f).touchable(Touchable.enabled).get().clicked(() -> {

            });
            cont.row();
        }
        pane(tab::buildContent).grow();
    }

    private void buildTabs(){
        pane(cont);
        int cols = (int)(Core.scene.getWidth() / 274);
        float colsSize = Core.scene.getWidth() / cols;
        for(int i = 0; i < SnAchievementTab.tabs.size; i++){
            SnAchievementTab tab = SnAchievementTab.tabs.get(i);
            cont.table(tab::buildTable).size(colsSize, colsSize * 1.5f).touchable(Touchable.enabled).get().clicked(() -> {
                this.tab = tab;
                rebuild();
            });
            if((i + 1) % cols == 0){
                cont.row();
            }
        }
    }

    private int countCompleted(){
        return 0;
    }
}
