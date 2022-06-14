package sunset.content.affilitiation;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import mindustry.graphics.Pal;

public enum SnGuilds {
    none,
    ancient,
    tieris,
    kryonix,
    aymirus,
    rixinators,
    weardius,
    taferot,
    coyrant;

    public static final SnGuilds[] all = values();

    public Color color = Pal.shield;

    public TextureRegion region() {
        return Core.atlas.find(("sunset-"+"guild-" + name()));
    }

    public String localizedName() {
        return Core.bundle.format("guild." + name().toLowerCase());
    }

    SnGuilds() {}

    SnGuilds(Color color) {
        this.color = color;
    }
}
