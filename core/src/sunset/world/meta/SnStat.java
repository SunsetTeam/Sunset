package sunset.world.meta;

import acontent.world.meta.AStat;
import mindustry.world.meta.StatCat;

public class SnStat {
    /**
     * 1)General - main information like health or size.<p>
     * 2)Affilitiation - the guild that the block/unit belongs to.<p>
     * 3)Power - power generation and consumption.<p>
     * 4)Liquids - liquid capacity etc.<p>
     * 5)Lasers - special info for the {@link sunset.world.blocks.laser.LaserBlock} and its derivatives.<p>
     * 6)Armor - special info for the {@link sunset.world.blocks.defense.turrets.SynthesisTurret}.<p>
     * 7)Durability - special info for the {@link sunset.world.blocks.defense.walls.SnWall}.<p>
     * 8)Items - item capacity etc.<p>
     * 9)Crafting - resources needed to create something.<p>
     * 10)Function - the action performed by the block.<p>
     * 11)Optional - additional info like boosters.<p>
     * <p>
     * (Vanilla Stats need for custom stat order.)
     * */

    //region NullCat
    public static final AStat nullStat = AStat.get("null-stat", SnStatCat.nullCat, 0);
    //endregion NullCat
    //region General
    //region Block
    public static final AStat blockHealth = AStat.get("health", StatCat.general, 0);
    public static final AStat blockSize = AStat.get("size", StatCat.general, 1);
    public static final AStat displaySize = AStat.get("displaySize", StatCat.general, 2);//Logic Display
    public static final AStat buildTime = AStat.get("buildTime", StatCat.general, 3);
    public static final AStat blockBuildCost = AStat.get("buildCost", StatCat.general, 4);
    public static final AStat memoryCapacity = AStat.get("memoryCapacity", StatCat.general, 5);//Memory Cell
    public static final AStat baseDeflectChance = AStat.get("baseDeflectChance", StatCat.general, 6);//Wall
    public static final AStat lightningChance = AStat.get("lightningChance", StatCat.general, 7);//Wall
    public static final AStat lightningDamage = AStat.get("lightningDamage", StatCat.general, 8);//Wall
    public static final AStat maxUnits = AStat.get("maxUnits", StatCat.general, 9);//Core
    //endregion Block
    //region Unit
    public static final AStat unitHealth = AStat.get("health", StatCat.general, 0);
    public static final AStat armor = AStat.get("armor", StatCat.general, 1);
    public static final AStat unitSize = AStat.get("size", StatCat.general, 2);
    public static final AStat unitBuildCost = AStat.get("buildCost", StatCat.general, 3);
    public static final AStat flying = AStat.get("flying", StatCat.general, 4);
    public static final AStat speed = AStat.get("speed", StatCat.general, 5);
    public static final AStat buildSpeed = AStat.get("buildSpeed", StatCat.general, 6);
    public static final AStat mineSpeed = AStat.get("mineSpeed", StatCat.general, 7);
    public static final AStat mineTier = AStat.get("mineTier", StatCat.general, 8);
    public static final AStat payloadCapacity = AStat.get("payloadCapacity", StatCat.general, 9);
    public static final AStat commandLimit = AStat.get("commandLimit", StatCat.general, 10);
    public static final AStat abilities = AStat.get("abilities", StatCat.general, 11);
    public static final AStat canBoost = AStat.get("canBoost", StatCat.general, 12);
    public static final AStat unitItemCapacity = AStat.get("itemCapacity", StatCat.general, 13);
    public static final AStat unitRange = AStat.get("range", StatCat.general, 14);
    //endregion Unit
    //region Items and Liquids
    //Items and Liquids
    public static final AStat explosiveness = AStat.get("explosiveness", StatCat.general, 0);
    public static final AStat flammability = AStat.get("flammability", StatCat.general, 1);
    //Items
    public static final AStat radioactivity = AStat.get("radioactivity", StatCat.general, 2);
    public static final AStat charge = AStat.get("charge", StatCat.general, 3);
    //Liquids
    public static final AStat heatCapacity = AStat.get("heatCapacity", StatCat.general, 2);
    public static final AStat viscosity = AStat.get("viscosity", StatCat.general, 3);
    public static final AStat temperature = AStat.get("temperature", StatCat.general, 4);
    //endregion Items and Liquids
    //region Status Effect
    public static final AStat damageMultiplier = AStat.get("damageMultiplier", StatCat.general, 0);
    public static final AStat healthMultiplier = AStat.get("healthMultiplier", StatCat.general, 1);
    public static final AStat speedMultiplier = AStat.get("speedMultiplier", StatCat.general, 2);
    public static final AStat reloadMultiplier = AStat.get("reloadMultiplier", StatCat.general, 3);
    public static final AStat buildSpeedMultiplier = AStat.get("buildSpeedMultiplier", StatCat.general, 4);
    public static final AStat statusDamage = AStat.get("damage", StatCat.general, 5);
    public static final AStat reactive = AStat.get("reactive", StatCat.general, 6);
    public static final AStat healing = AStat.get("healing", StatCat.general, 7);
    public static final AStat affinities = AStat.get("affinities", StatCat.general, 8);
    public static final AStat opposites = AStat.get("opposites", StatCat.general, 9);
    //endregion Status Effect
    //endregion General
    //region Affilitiation
    public static final AStat guild = AStat.get("guild", SnStatCat.affiliation, 0);
    public static final AStat subGuild = AStat.get("subguild", SnStatCat.affiliation, 1);
    public static final AStat branch = AStat.get("branch", SnStatCat.affiliation, 2);
    //endregion Affilitiation
    //region Crafting
    public static final AStat tiles = AStat.get("tiles", StatCat.crafting, 0);
    public static final AStat input = AStat.get("input", StatCat.crafting, 1);
    public static final AStat output = AStat.get("output", StatCat.crafting, 2);
    public static final AStat productionTime = AStat.get("productionTime", StatCat.crafting, 3);
    public static final AStat drillTier = AStat.get("drillTier", StatCat.crafting, 4);
    public static final AStat drillSpeed = AStat.get("drillSpeed", StatCat.crafting, 5);
    public static final AStat linkRange = AStat.get("linkRange", StatCat.crafting, 6);
    public static final AStat instructions = AStat.get("instructions", StatCat.crafting, 7);
    //endregion Crafting
    //region Function
    //region Block
    public static final AStat speedIncrease = AStat.get("speedIncrease", StatCat.function, 0);
    public static final AStat repairTime = AStat.get("repairTime", StatCat.function, 1);
    public static final AStat repairSpeed = AStat.get("repairSpeed", StatCat.function, 2);
    public static final AStat blockRange = AStat.get("range", StatCat.function, 3);
    public static final AStat maxControlled = AStat.get("maxcontroller", StatCat.function, 4);
    public static final AStat minimalRange = AStat.get("min-range", StatCat.function, 5);
    public static final AStat shootRange = AStat.get("shootRange", StatCat.function, 6);
    public static final AStat inaccuracy = AStat.get("inaccuracy", StatCat.function, 7);
    public static final AStat shots = AStat.get("shots", StatCat.function, 8);
    public static final AStat reload = AStat.get("reload", StatCat.function, 9);
    public static final AStat targetsAir = AStat.get("targetsAir", StatCat.function, 10);
    public static final AStat targetsGround = AStat.get("targetsGround", StatCat.function, 11);
    public static final AStat blockDamage = AStat.get("damage", StatCat.function, 12);
    public static final AStat ammo = AStat.get("ammo", StatCat.function, 13);
    public static final AStat ammoUse = AStat.get("ammoUse", StatCat.function, 14);
    public static final AStat powerShot = AStat.get("powerShot", StatCat.function, 15);
    public static final AStat shieldHealth = AStat.get("shieldHealth", StatCat.function, 16);
    public static final AStat cooldownTime = AStat.get("cooldownTime", StatCat.function, 17);
    //endregion Block
    //region Unit
    public static final AStat weapons = AStat.get("weapons", StatCat.function, 0);
    public static final AStat bullet = AStat.get("bullet", StatCat.function, 1);
    //endregion Unit
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
    public static final AStat powerCapacity = AStat.get("powerCapacity", StatCat.power, 0);
    public static final AStat powerUse = AStat.get("powerUse", StatCat.power, 1);
    public static final AStat powerDamage = AStat.get("powerDamage", StatCat.power, 2);
    public static final AStat powerRange = AStat.get("powerRange", StatCat.power, 3);
    public static final AStat powerConnections = AStat.get("powerConnections", StatCat.power, 4);
    public static final AStat basePowerGeneration = AStat.get("basePowerGeneration", StatCat.power, 5);
    //endregion Power
    //region Items
    public static final AStat blockItemCapacity = AStat.get("itemCapacity", StatCat.items, 0);
    public static final AStat itemsMoved = AStat.get("itemsMoved", StatCat.items, 1);
    public static final AStat launchTime = AStat.get("launchTime", StatCat.items, 2);
    public static final AStat maxConsecutive = AStat.get("maxConsecutive", StatCat.items, 3);
    //endregion Items
    //region Liquids
    public static final AStat liquidCapacity = AStat.get("liquidCapacity", StatCat.liquids, 0);
    //endregion Liquids
    //region Optional
    public static final AStat booster = AStat.get("booster", StatCat.optional, 0);
    public static final AStat boostEffect = AStat.get("boostEffect", StatCat.optional, 1);
    //endregion Optional
}
