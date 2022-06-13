package sunset.game;

public class SnEventType{
    public static class AchievementReveiveEvent{
        public final SnAchievement achievement;

        public AchievementReveiveEvent(SnAchievement achievement){
            this.achievement = achievement;
        }
    }
}
