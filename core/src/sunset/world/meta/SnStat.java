package sunset.world.meta;

import acontent.world.meta.AStat;
import mindustry.world.meta.StatCat;

public class SnStat {
    /**
     * 1)General - main information like health or size.<p>
     * 2)Crafting - resources needed to create something.<p>
     * 3)Function - the action performed by the block.<p>
     * 4)Lasers - special info for the {@link sunset.world.blocks.laser.LaserBlock} and its derivatives.<p>
     * 5)Armor - special info for the {@link sunset.world.blocks.defense.turrets.SynthesisTurret}.<p>
     * 6)Durability - armor info for the {@link sunset.world.blocks.defense.walls.SnWall}.<p>
     * 7)Power - power generation and consumption.<p>
     * 8)Items - item capacity etc.<p>
     * 9)Liquids - liquid capacity etc.<p>
     * 10)Optional - additional info like boosters.<p>
     * <p>
     * (Vanilla Stats need for custom stat order.)
     * */

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


    public static final AStat maxControlled = AStat.get("maxcontroller", StatCat.function);
    //public static final AStat inaccuracy = AStat.get("inaccuracy", Stat.inaccuracy.category);


    public static final AStat minimalRange = AStat.get("min-range", StatCat.function);

    //public static final AStat fraction = AStat.get("fraction", purpose, 1);


    public static final AStat nullStat = AStat.get("null-stat", SnStatCat.nullCat);

    public static final AStat faction = AStat.get("faction", SnStatCat.affiliation, 0);
    public static final AStat subFaction = AStat.get("subfaction", SnStatCat.affiliation, 1);
    public static final AStat branch = AStat.get("branch", SnStatCat.affiliation, 2);

    //region General
    //block
    public static final AStat health = AStat.get("health", SnStatCat.snGeneral);
    public static final AStat armor = AStat.get("armor", SnStatCat.snGeneral);
    public static final AStat size = AStat.get("size", SnStatCat.snGeneral);
    public static final AStat displaySize = AStat.get("displaySize", SnStatCat.snGeneral);
    public static final AStat buildTime = AStat.get("buildTime", SnStatCat.snGeneral);
    public static final AStat buildCost = AStat.get("buildCost", SnStatCat.snGeneral);
    public static final AStat memoryCapacity = AStat.get("memoryCapacity", SnStatCat.snGeneral);
    //item
    public static final AStat explosiveness = AStat.get("explosiveness", SnStatCat.snGeneral);
    public static final AStat flammability = AStat.get("flammability", SnStatCat.snGeneral);
    public static final AStat radioactivity = AStat.get("radioactivity", SnStatCat.snGeneral);
    public static final AStat charge = AStat.get("charge", SnStatCat.snGeneral);
    //liquid
    public static final AStat heatCapacity = AStat.get("heatCapacity", SnStatCat.snGeneral);
    public static final AStat viscosity = AStat.get("viscosity", SnStatCat.snGeneral);
    public static final AStat temperature = AStat.get("temperature", SnStatCat.snGeneral);
    //unit
    public static final AStat flying = AStat.get("flying", SnStatCat.snGeneral);
    public static final AStat speed = AStat.get("speed", SnStatCat.snGeneral);
    public static final AStat buildSpeed = AStat.get("buildSpeed", SnStatCat.snGeneral);
    public static final AStat mineSpeed = AStat.get("mineSpeed", SnStatCat.snGeneral);
    public static final AStat mineTier = AStat.get("mineTier", SnStatCat.snGeneral);
    public static final AStat payloadCapacity = AStat.get("payloadCapacity", SnStatCat.snGeneral);
    public static final AStat commandLimit = AStat.get("commandLimit", SnStatCat.snGeneral);
    //block
    public static final AStat baseDeflectChance = AStat.get("baseDeflectChance", SnStatCat.snGeneral);
    public static final AStat lightningChance = AStat.get("lightningChance", SnStatCat.snGeneral);
    public static final AStat lightningDamage = AStat.get("lightningDamage", SnStatCat.snGeneral);
    //unit
    public static final AStat abilities = AStat.get("abilities", SnStatCat.snGeneral);
    public static final AStat canBoost = AStat.get("canBoost", SnStatCat.snGeneral);
    //block
    public static final AStat maxUnits = AStat.get("maxUnits", SnStatCat.snGeneral);
    //status-effect
    public static final AStat damageMultiplier = AStat.get("damageMultiplier", SnStatCat.snGeneral);
    public static final AStat healthMultiplier = AStat.get("healthMultiplier", SnStatCat.snGeneral);
    public static final AStat speedMultiplier = AStat.get("speedMultiplier", SnStatCat.snGeneral);
    public static final AStat reloadMultiplier = AStat.get("reloadMultiplier", SnStatCat.snGeneral);
    public static final AStat buildSpeedMultiplier = AStat.get("buildSpeedMultiplier", SnStatCat.snGeneral);
    public static final AStat reactive = AStat.get("reactive", SnStatCat.snGeneral);
    public static final AStat healing = AStat.get("healing", SnStatCat.snGeneral);
    //endregion General
    //region Power
    public static final AStat powerCapacity = AStat.get("powerCapacity", SnStatCat.snPower);
    public static final AStat powerUse = AStat.get("powerUse", SnStatCat.snPower);
    public static final AStat powerDamage = AStat.get("powerDamage", SnStatCat.snPower);
    public static final AStat powerRange = AStat.get("powerRange", SnStatCat.snPower);
    public static final AStat powerConnections = AStat.get("powerConnections", SnStatCat.snPower);
    public static final AStat basePowerGeneration = AStat.get("basePowerGeneration", SnStatCat.snPower);
    //endregion Power
    //region Items
    public static final AStat itemCapacity = AStat.get("itemCapacity", SnStatCat.snItems);
    public static final AStat itemsMoved = AStat.get("itemsMoved", SnStatCat.snItems);
    public static final AStat launchTime = AStat.get("launchTime", SnStatCat.snItems);
    public static final AStat maxConsecutive = AStat.get("maxConsecutive", SnStatCat.snItems);
    //endregion Items
    //region Liquids
    public static final AStat liquidCapacity = AStat.get("liquidCapacity", SnStatCat.snLiquids);
    //endregion Liquids
    //region Crafting
    public static final AStat tiles = AStat.get("tiles", SnStatCat.snCrafting);
    public static final AStat input = AStat.get("input", SnStatCat.snCrafting);
    public static final AStat output = AStat.get("output", SnStatCat.snCrafting);
    public static final AStat productionTime = AStat.get("productionTime", SnStatCat.snCrafting);
    public static final AStat drillTier = AStat.get("drillTier", SnStatCat.snCrafting);
    public static final AStat drillSpeed = AStat.get("drillSpeed", SnStatCat.snCrafting);
    public static final AStat linkRange = AStat.get("linkRange", SnStatCat.snCrafting);
    public static final AStat instructions = AStat.get("instructions", SnStatCat.snCrafting);
    //endregion Crafting
    //region Function
    //unit
    public static final AStat weapons = AStat.get("weapons", SnStatCat.snFunction);
    public static final AStat bullet = AStat.get("bullet", SnStatCat.snFunction);
    //block
    public static final AStat speedIncrease = AStat.get("speedIncrease", SnStatCat.snFunction);
    public static final AStat repairTime = AStat.get("repairTime", SnStatCat.snFunction);
    public static final AStat repairSpeed = AStat.get("repairSpeed", SnStatCat.snFunction);
    public static final AStat range = AStat.get("range", SnStatCat.snFunction);
    public static final AStat shootRange = AStat.get("shootRange", SnStatCat.snFunction);
    public static final AStat inaccuracy = AStat.get("inaccuracy", SnStatCat.snFunction);
    public static final AStat shots = AStat.get("shots", SnStatCat.snFunction);
    public static final AStat reload = AStat.get("reload", SnStatCat.snFunction);
    public static final AStat powerShot = AStat.get("powerShot", SnStatCat.snFunction);
    public static final AStat targetsAir = AStat.get("targetsAir", SnStatCat.snFunction);
    public static final AStat targetsGround = AStat.get("targetsGround", SnStatCat.snFunction);
    public static final AStat damage = AStat.get("damage", SnStatCat.snFunction);
    public static final AStat ammo = AStat.get("ammo", SnStatCat.snFunction);
    public static final AStat ammoUse = AStat.get("ammoUse", SnStatCat.snFunction);
    public static final AStat shieldHealth = AStat.get("shieldHealth", SnStatCat.snFunction);
    public static final AStat cooldownTime = AStat.get("cooldownTime", SnStatCat.snFunction);
    //endregion Function
    //region Optional
    //block
    public static final AStat booster = AStat.get("booster", SnStatCat.snOptional);
    public static final AStat boostEffect = AStat.get("boostEffect", SnStatCat.snOptional);
    //status-effect
    public static final AStat affinities = AStat.get("affinities", SnStatCat.snOptional);
    public static final AStat opposites = AStat.get("opposites", SnStatCat.snOptional);
    //endregion Optional
}
