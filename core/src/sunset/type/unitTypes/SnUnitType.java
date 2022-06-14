package sunset.type.unitTypes;

import acontent.world.meta.AStats;
import arc.Core;
import mindustry.gen.EntityMapping;
import mindustry.graphics.MultiPacker;
import mindustry.type.UnitType;
import mma.ModVars;
import sunset.content.affilitiation.SnBranches;
import sunset.content.affilitiation.SnGuilds;
import sunset.content.affilitiation.SnSubGuilds;
import sunset.world.meta.SnStat;
import sunset.world.meta.SnStatValues;

public class SnUnitType extends UnitType {
    public SnGuilds guild = SnGuilds.none;
    public SnSubGuilds subGuild = SnSubGuilds.none;
    public SnBranches branch = SnBranches.none;
    public AStats aStats = new AStats();
    public SnUnitType(String name) {
        super(name);
        outlines = ModVars.packSprites;
        if (constructor == null) {
            constructor = EntityMapping.map(name);
        }
        stats = aStats.copy(stats);
    }

    @Override
    public void setStats() {
        super.setStats();
        if (guild != SnGuilds.none) aStats.add(SnStat.guild, SnStatValues.affil(guild));
        if (subGuild != SnSubGuilds.none) aStats.add(SnStat.subGuild, SnStatValues.affil(subGuild));
        if (branch != SnBranches.none) aStats.add(SnStat.branch, SnStatValues.affil(branch));
    }

    @Override
    public void load() {
        super.load();
        //loadIcon();
        shadowRegion = Core.atlas.find(name + "-shadow", shadowRegion);
    }

    @Override
    public void createIcons(MultiPacker packer) {
        super.createIcons(packer);
    }
}
