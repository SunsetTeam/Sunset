package sunset.ui;

import arc.*;
import arc.scene.event.*;
import arc.scene.ui.*;
import mindustry.gen.*;
import mindustry.ui.dialogs.*;
import sunset.content.*;
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
            table.button("RESET", SnAchievements::resetAchievements);
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
            pane.name = "left-selection-table";
            pane.fillParent = true;
            for(int i = 0; i < SnAchievementTab.tabs.size; i++){
                SnAchievementTab tab = SnAchievementTab.tabs.get(i);
                pane.table(tab::buildTable).size(colsSize, colsSize * 1.5f).expandX().touchable(Touchable.enabled).get().clicked(() -> {
                    this.tab = tab;

                    ScrollPane scrollPane = cont.find("left-selection-table.pane");
//                    scrollPane.pack();
//                    scrollPane.act(0.001f);
                    float scrollY = scrollPane.getScrollY();
//                    float maxY = Reflect.get(ScrollPane.class, scrollPane, "maxY");
                    rebuild();
                    scrollPane = cont.find("left-selection-table.pane");

                    pack();
                    scrollPane.layout();
                    scrollPane.act(0.001f);

//                    float maxY2 = Reflect.get(ScrollPane.class, scrollPane, "maxY");
//                    Log.info("max(@, @) scrollY=@",maxY,maxY2,scrollY);
                    scrollPane.setScrollYForce(scrollY);
                });
                pane.row();
            }
//            scrollPane.
        }).scrollX(false).scrollY(true).width(colsSize).left().growY().get().name = "left-selection-table.pane";
        cont.pane(tab::buildContent).grow().get().name = "selected-tab-content-pane";
    }

    private void buildTabs(){
        int cols = (int)(Core.scene.getWidth() / 274);
        float colsSize = Core.scene.getWidth() / cols;
        cont.pane(table -> {
            for(int i = 0; i < SnAchievementTab.tabs.size; i++){
                SnAchievementTab tab = SnAchievementTab.tabs.get(i);
                table.table(tab::buildTable).size(colsSize, colsSize * 1.5f).touchable(Touchable.enabled).expand().get().clicked(() -> {
                    this.tab = tab;
                    rebuild();
                });
                if((i + 1) % cols == 0){
                    table.row();
                }
            }
        }).grow();
    }

    private int countCompleted(){
        return 0;
    }
}
