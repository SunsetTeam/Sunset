package sunset.game;

import arc.Events;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.game.EventType;

public class SnAchievementSystem {
    private static final Seq<SnAchievement> achievements = new Seq<>();

    static {
        Events.run(EventType.ClientLoadEvent.class, SnAchievementSystem::afterLoad);
    }
    public static void registerAchievement(SnAchievement achievement) {
        achievements.add(achievement);
    }
public static void display(Table table){
        //TODO: made display for achievements
    for (SnAchievement achievement : achievements) {
        table.table(achievement::display);
        table.row();
    }
       throw  new RuntimeException("TODO");
}
    private static void afterLoad() {
            achievements.each(SnAchievement::afterLoad);
    }
}
