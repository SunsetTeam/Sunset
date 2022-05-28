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
        addCloseListener();
    }

    private void rebuild(){
        clearChildren();
        cont.clearChildren();
        table(table -> {
            table.image(Core.atlas.find("sn-achivements-icon")).left();
            table.add("@sn-achivements.title").left();
            table.add().growX();
            table.label(() -> Core.bundle.format("@sn.acheivements.completed", countCompleted()));
            table.button(Icon.exit, this::hide).size(32f).right();
        }).top().growX().row();
        add(cont).grow();
        if(tab == null){
            buildTabs();
        }else{
            buildSelected();
        }

    }

    private void buildSelected(){
        int cols = (int)(Core.scene.getWidth() / 274);
        float colsSize = Core.scene.getWidth() / cols;
        cont.pane(pane -> {
            for(int i = 0; i < SnAchievementTab.tabs.size; i++){
                SnAchievementTab tab = SnAchievementTab.tabs.get(i);
                pane.table(tab::buildTable).size(colsSize, colsSize * 1.5f).touchable(Touchable.enabled).get().clicked(() -> {

                });
                pane.row();
            }
        }).width(colsSize).left().growY();
        cont.pane(tab::buildContent).grow();
    }

    private void buildTabs(){
        int cols = (int)(Core.scene.getWidth() / 274);
        float colsSize = Core.scene.getWidth() / cols;
        cont.pane(table -> {
            for(int i = 0; i < SnAchievementTab.tabs.size; i++){
                SnAchievementTab tab = SnAchievementTab.tabs.get(i);
                table.table(tab::buildTable).size(colsSize, colsSize * 1.5f).touchable(Touchable.enabled).get().clicked(() -> {
                    this.tab = tab;
                    rebuild();
                });
                if((i + 1) % cols == 0){
                    table.row();
                }
            }
        });
    }

    private int countCompleted(){
        return 0;
    }
}
