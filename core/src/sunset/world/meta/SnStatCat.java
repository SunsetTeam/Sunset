package sunset.world.meta;

import acontent.world.meta.AStatCat;
import mindustry.world.meta.StatCat;

public class SnStatCat {
    public static final AStatCat nullCat = AStatCat.get("null-cat", StatCat.general.id-1);
    public static final AStatCat affiliation = AStatCat.get("affiliation", StatCat.general.id+1);
    public static final AStatCat lasers = AStatCat.get("lasers", StatCat.liquids.id+1);
    public static final AStatCat armor = AStatCat.get("armor", StatCat.liquids.id+2);
    public static final AStatCat durability = AStatCat.get("durability", StatCat.liquids.id+3);
}
