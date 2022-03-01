package sunset.world.meta;

import acontent.world.meta.AStatCat;
import mindustry.world.meta.StatCat;

public class SnStatCat {
    public static final AStatCat nullCat = AStatCat.get("null-cat", StatCat.general.ordinal()-1);
    public static final AStatCat affiliation = AStatCat.get("affiliation", StatCat.general.ordinal()+1);
    public static final AStatCat lasers = AStatCat.get("lasers", StatCat.liquids.ordinal()+1);
    public static final AStatCat armor = AStatCat.get("armor", StatCat.liquids.ordinal()+2);
    public static final AStatCat durability = AStatCat.get("durability", StatCat.liquids.ordinal()+3);
}
