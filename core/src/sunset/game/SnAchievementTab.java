package sunset.game;

import arc.*;
import arc.graphics.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.graphics.*;
import mma.*;
import sunset.ui.*;

public class SnAchievementTab{
    public static final Seq<SnAchievementTab> tabs = new Seq<>();

    static{
        ModListener.updaters.add(SnAchievementTab::updateAchievements);
    }

    public final String name;
    public String iconname = "error";
    public Seq<SnAchievement> achievements = new Seq<>();
    public boolean hasProgress = true;

    public SnAchievementTab(String name){
        this.name = name;
        tabs.add(this);
    }

    private static void updateAchievements(){
        for(SnAchievementTab tab : tabs){
            for(SnAchievement achievement : tab.achievements){
                achievement.check();
            }
        }
    }

    protected <T extends SnAchievement> T achievement(T achievement){
        achievement.parent = this;
        achievements.add(achievement);
        return achievement;
    }

    public String localized(){
        return Core.bundle.get(name + ".localized");
    }

    public void buildTable(Table table){
        table.image(Core.atlas.find(iconname)).minSize(64f).row();
        table.table(t -> {
            t.label(this::localized)/*.update(l->{
                Log.info("t(@, @)",l.getPrefWidth(),l.getPrefHeight());
            })*/.growX().fillY();
        });
        if(!hasProgress) return;
        table.row();
        table.add(new SnBar("@progress", Pal.accent, this::progress)).pad(3).padTop(6).update(bar -> {
            float aspect = 40f / 500f;
//            Log.info("t(@, @)", bar.getPrefWidth(), bar.getPrefHeight());
            bar.setHeight(bar.getWidth() * aspect);
        }).growX().fillY();
//        table.add(new ProgressBar(0f, 1f, 1f / achievements.size, false));
    }

    protected float progress(){
        return achievements.count(SnAchievement::unlocked) / (float)achievements.size;
    }

    public void buildContent(Table table){
        table.align(Align.top);
        for(SnAchievement achievement : achievements.copy().sort(it->it.unlocked()?1:0)){
            table.table(achievement::display).top().fillY().growX().row();
        }
    }
  /*  protected  SnAchievement achievement(T achievement){
        achievements.add(achievement);
        return achievement;
    }*/
}
