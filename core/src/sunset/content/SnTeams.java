package sunset.content;

import arc.Events;
import arc.graphics.Color;
import mindustry.ctype.ContentList;
import mindustry.game.EventType;
import mindustry.game.Team;

public class SnTeams implements ContentList {
    public static Team
            aymirus;

    @Override
    public void load() {
        Events.on(EventType.ClientLoadEvent.class, e -> {
            aymirus = Team.get(6);
            aymirus.name = "aymirus";
            aymirus.palette[0].set(aymirus.color.set(Color.valueOf("CA514A")));
            aymirus.palette[1] = aymirus.color.cpy().mul(0.75f);
            aymirus.palette[2] = aymirus.color.cpy().mul(0.5f);
        });
    }
}
