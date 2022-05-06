package sunset.type;

import acontent.world.meta.AStats;
import arc.graphics.Color;
import mindustry.type.Item;
import mindustry.world.meta.Stat;
import sunset.world.meta.SnStat;

public class SnItem extends Item {
    public float testStat1 = 0f;
    public float testStat2 = 0f;
    public float testStat3 = 0f;

    public AStats aStats = new AStats();

    public SnItem(String name, Color color){
        super(name);
        this.color = color;
        stats=aStats.copy(stats);
    }

    public SnItem(String name){
        this(name, new Color(Color.black));
    }

    @Override
    public void setStats(){
        stats.addPercent(Stat.explosiveness, explosiveness);
        stats.addPercent(Stat.flammability, flammability);
        stats.addPercent(Stat.radioactivity, radioactivity);
        stats.addPercent(Stat.charge, charge);
        aStats.addPercent(SnStat.testItemStat1, testStat1);
        aStats.addPercent(SnStat.testItemStat2, testStat2);
        aStats.addPercent(SnStat.testItemStat3, testStat3);
    }
}
