package sunset.content.affilitiation;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import mindustry.graphics.Pal;

import static mma.ModVars.fullName;

public enum SnSubGuilds {
    none;

    public static final SnSubGuilds[] all = values();

    public Color color = Pal.shield;

    public TextureRegion region() {
        return Core.atlas.find(fullName("subguild-" + name()));
    }

    public String localizedName() {
        return Core.bundle.format("subguild." + name().toLowerCase());
    }

    SnSubGuilds() {}

    SnSubGuilds(Color color) {
        this.color = color;
    }
}
