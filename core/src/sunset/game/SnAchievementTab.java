package sunset.game;

import arc.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import mindustry.graphics.*;
import mindustry.ui.*;

public class SnAchievementTab{
    public static final Seq<SnAchievementTab> tabs = new Seq<>();
    public final String name;
    public String iconname="error";
    public Seq<SnAchievement> achievements = new Seq<>();
    public boolean hasProgress = true;

    public SnAchievementTab(String name){
        this.name = name;
        tabs.add(this);
    }

    protected <T extends SnAchievement> T achievement(T achievement){
        achievement.parent=this;
        achievements.add(achievement);
        return achievement;
    }

    public String localized(){
        return Core.bundle.get(name + ".localized");
    }

    public void buildTable(Table table){
        table.image(Core.atlas.find(iconname)).fillX().row();
        table.labelWrap(localized());
        if(!hasProgress) return;
        table.add(new Bar("@progress", Pal.accent, this::progress)).pad(3).padTop(6).size(500f, 40f);
//        table.add(new ProgressBar(0f, 1f, 1f / achievements.size, false));
    }

    protected float progress(){
        return achievements.count(SnAchievement::unlocked) / (float)achievements.size;
    }

    public void buildContent(Table table){

        for(SnAchievement achievement : achievements){
            table.table(achievement::display).grow().row();
        }
    }
  /*  protected  SnAchievement achievement(T achievement){
        achievements.add(achievement);
        return achievement;
    }*/
}
