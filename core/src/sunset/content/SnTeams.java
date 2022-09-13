package sunset.content;

import arc.Events;
import arc.graphics.Color;
import mindustry.game.EventType;
import mindustry.game.Team;

public class SnTeams {
    public static Team aymirus;

    public static void load() {
        aymirus = newTeam(5, "aymirus", Color.valueOf("CA514A"));
    }

    private static Team newTeam(int id, String name, Color color) {
        Team team = Team.get(id);
        team.name = name;
        team.color.set(color);

        team.palette[0] = color;
        team.palette[1] = color.cpy().mul(0.75f);
        team.palette[2] = color.cpy().mul(0.5f);

        for(int i = 0; i < 3; i++){
            team.palettei[i] = team.palette[i].rgba();
        }

        return team;
    }
}
