package sunset.game;

import arc.Core;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.Rand;
import arc.struct.Seq;
import arc.util.Nullable;
import mindustry.game.Rules;
import mindustry.game.Team;
import mindustry.game.Teams;
import mindustry.graphics.Pal;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.modules.ItemModule;
import org.jetbrains.annotations.NotNull;

import static mindustry.Vars.state;

public class SnTeam extends Team implements Comparable<Team>{
    public final int id;
    public final Color color;
    public final Color[] palette;
    public final int[] palettei = new int[3];
    public String emoji = "";
    public boolean hasPalette;
    public String name;
    public static final Team[] SnTeams = new SnTeam[2];

    public final static Team
            veprum = new SnTeam(44, "veprum", Color.valueOf("219db1"), Color.valueOf("4aeac4"), Color.valueOf("219db1"), Color.valueOf("1c6192")),
            aymir = new SnTeam(33, "aymir", Color.valueOf("249c7f"), Color.valueOf("51e88b"), Color.valueOf("249c7f"), Color.valueOf("136d68"));

    protected SnTeam(int id, String name, Color color){
        super(id, name, color);
        this.name = name;
        this.color = color;
        this.id = id;

        if(id < 2) SnTeams[id] = this;

        palette = new Color[3];
        palette[0] = color;
        palette[1] = color.cpy().mul(0.75f);
        palette[2] = color.cpy().mul(0.5f);

        for(int i = 0; i < 3; i++){
            palettei[i] = palette[i].rgba();
        }
    }

    /** Specifies a 3-color team palette. */
    protected SnTeam(int id, String name, Color color, Color pal1, Color pal2, Color pal3){
        this(id, name, color);

        palette[0] = pal1;
        palette[1] = pal2;
        palette[2] = pal3;
        for(int i = 0; i < 3; i++){
            palettei[i] = palette[i].rgba();
        }
        hasPalette = true;
    }

    /** @return the core items for this team, or an empty item module.
     * Never add to the resulting item module, as it is mutable. */
    public ItemModule items(){
        return core() == null ? ItemModule.empty : core().items;
    }

    /** @return the team-specific rules. */
    public Rules.TeamRule rules(){
        return state.rules.teams.get(this);
    }

    public Teams.TeamData data(){
        return state.teams.get(this);
    }

    @Nullable
    public CoreBlock.CoreBuild core(){
        return data().core();
    }

    public boolean active(){
        return state.teams.isActive(this);
    }

    /** @return whether this team is supposed to be AI-controlled. */
    public boolean isAI(){
        return (state.rules.waves || state.rules.attackMode) && this == state.rules.waveTeam && !state.rules.pvp;
    }

    /** @return whether this team is solely comprised of AI (with no players possible). */
    public boolean isOnlyAI(){
        return isAI() && data().players.size == 0;
    }

    /** @return whether this team needs a flow field for "dumb" wave pathfinding. */
    public boolean needsFlowField(){
        return isAI() && !rules().rtsAi;
    }

    public boolean isEnemy(Team other){
        return this != other;
    }

    public Seq<CoreBlock.CoreBuild> cores(){
        return state.teams.cores(this);
    }

    public String localized(){
        return Core.bundle.get("team." + name + ".name", name);
    }

    @Override
    public int compareTo(Team team){
        return Integer.compare(id, team.id);
    }

    @Override
    public String toString(){
        return name;
    }
}
