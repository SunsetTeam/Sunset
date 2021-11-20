package sunset.content;

import arc.graphics.Color;
import mindustry.ctype.ContentList;
import sunset.game.Team;


public class SnTeams implements ContentList {
    public static Team orange;

    @Override
    public void load() {
        orange = new Team(67864, "orange", Color.orange);
    }
}
