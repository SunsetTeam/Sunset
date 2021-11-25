package sunset.content;

import arc.graphics.Color;
import mindustry.ctype.ContentList;
import sunset.game.SnTeam;


public class SnTeams implements ContentList {
    public static SnTeam orange;

    @Override
    public void load() {
        orange = new SnTeam(67864, "orange", Color.orange);
    }
}
