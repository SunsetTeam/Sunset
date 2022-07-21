package sunset.world.meta;

import acontent.world.meta.AStat;
import mindustry.world.meta.StatCat;

public class SnStat {
    /**
     * 1)General - main information like health or size.<p>
     * 2)Affilitiation - the name that the block/unit belongs to.<p>
     * 3)Power - power generation and consumption.<p>
     * 4)Liquids - liquid capacity etc.<p>
     * 5)Lasers - special info for the {@link sunset.world.blocks.laser.LaserBlock} and its derivatives.<p>
     * 6)Armor - currently unused.<p>
     * 7)Durability - special info for the {@link sunset.world.blocks.defense.walls.SnWall}.<p>
     * 8)Items - item capacity etc.<p>
     * 9)Crafting - resources needed to create something.<p>
     * 10)Function - the action performed by the block.<p>
     * 11)Optional - additional info like boosters.
     * */

    //region General
    //empty
    //endregion General
    //region Affilitiation
    public static final AStat guild = AStat.get("guild", SnStatCat.affiliation, 0);
    public static final AStat subGuild = AStat.get("subguild", SnStatCat.affiliation, 1);
    public static final AStat branch = AStat.get("branch", SnStatCat.affiliation, 2);
    //endregion Affilitiation
    //region Crafting
    //empty
    //endregion Crafting
    //region Function
    public static final AStat maxControlled = AStat.get("maxcontroller", StatCat.function, 4);
    public static final AStat minimalRange = AStat.get("min-range", StatCat.function, 5);
    public static final AStat lightningDiff = AStat.get("lightning-diff", StatCat.function, 14);
    public static final AStat powerShot = AStat.get("powerShot", StatCat.function, 15);
    public static final AStat overheatTime = AStat.get("overheat-time", StatCat.function, 16);
    //endregion Function
    //region Lasers
    public static final AStat laserProduction = AStat.get("laserproduction", SnStatCat.lasers, 0);
    public static final AStat laserConsumption = AStat.get("laserconsumption", SnStatCat.lasers, 1);
    public static final AStat maxCharge = AStat.get("maxcharge", SnStatCat.lasers, 2);
    public static final AStat maxLinks = AStat.get("maxlinks", SnStatCat.lasers, 3);
    //endregion Lasers
    //region Armor
    public static final AStat primaryArmor = AStat.get("primary-armor", SnStatCat.armor, 0);
    public static final AStat secondaryArmor = AStat.get("secondary-armor", SnStatCat.armor, 1);
    //endregion Armor
    //region Durability
    public static final AStat pierceDamageDebuff = AStat.get("piercedamagedebuff", SnStatCat.durability, 0);
    public static final AStat pierceMultiplier = AStat.get("piercedebuff", SnStatCat.durability, 1);
    //endregion Durability
    //region Power
    //empty
    //endregion Power
    //region Items
    public static final AStat testItemStat1 = AStat.get("test-item-stat1", StatCat.items, 1);
    public static final AStat testItemStat2 = AStat.get("test-item-stat2", StatCat.items, 2);
    public static final AStat testItemStat3 = AStat.get("test-item-stat3", StatCat.items, 3);
    //empty
    //endregion Items
    //region Liquids
    //empty
    //endregion Liquids
    //region Optional
    //empty
    //endregion Optional
}
