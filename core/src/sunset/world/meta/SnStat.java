package sunset.world.meta;

import acontent.world.meta.AStat;
import acontent.world.meta.AStatCat;
import acontent.world.meta.AStats;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;

public class SnStat {
    public static final AStat pierceMultiplier = AStat.get("piercedebuff",SnStatCat.durability);
    public static final AStat pierceDamageDebuff = AStat.get("piercedamagedebuff",SnStatCat.durability);

    public static final AStat maxControlled = AStat.get("maxcontroller", StatCat.function);
    public static final AStat inaccuracy = AStat.get("inaccuracy", Stat.inaccuracy.category);

    public static final AStat laserConsumption = AStat.get("laserconsumption", SnStatCat.lasers);
    public static final AStat laserProduction = AStat.get("laserproduction", SnStatCat.lasers);
    public static final AStat maxCharge = AStat.get("maxcharge", SnStatCat.lasers);
    public static final AStat maxLinks = AStat.get("maxlinks", SnStatCat.lasers);
}
