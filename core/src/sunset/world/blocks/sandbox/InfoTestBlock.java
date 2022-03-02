package sunset.world.blocks.sandbox;

import acontent.world.meta.AStats;
import arc.graphics.Color;
import arc.struct.ObjectMap;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.Nullable;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.abilities.Ability;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Building;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.meta.StatUnit;
import mma.graphics.ADrawf;
import sunset.content.SnAttribute;
import sunset.world.meta.SnStat;

public class InfoTestBlock extends Block {
    public String info;

    public AStats aStats = new AStats();
    public int nullStat = 0;

    //block fields
    public int displaySize = 5;
    public int memoryCapacity = 32;
    public static float chanceDeflect = 10;

    public String guild;
    public String subGuild;
    public String branch;

    public float lightningChance = -1;
    public float lightningDamage = 20;

    public Attribute attribute = SnAttribute.none;
    public float reloadTime = 10;
    public float craftTime = 80;
    public @Nullable ItemStack outputItem = new ItemStack(Items.copper, 1);
    public @Nullable LiquidStack outputLiquid = new LiquidStack(Liquids.water, 0.1f);
    public int tier = 1;
    public float drillTime = 300;
    public float range = 8 * 10;
    public int instructionsPerTick = 1;

    public float speedBoost = 1.5f;
    public float healPercent = 12;
    public float reload = 250;
    public float repairHealth = 50;
    public float repairSpeed = 0.3f;
    public float maxConnections = 10;
    public float minRange = -1;
    public float inaccuracy = 0;
    public int shots = 1;
    public boolean alternate = false;
    public boolean targetAir = true;
    public boolean targetGround = true;
    public float damage = 0;
    public ObjectMap<Item, BulletType> ammoTypes = new ObjectMap<>();
    public int ammoPerShot = 1;
    public BulletType powerBullet;
    public float maxShootTime = 120f;
    public float shieldHealth = 700;
    public float cooldownBrokenBase = 0.35f;
    public float liquidBoostIntensity = 1.6f;

    public float laserGeneration = 0;
    public float laserConsumption = 0;
    public float maxCharge = 20;
    public int maxLinks = 4;

    public float primaryArmor = 0;
    public float secondaryArmor = 0;

    public float collidedDamageMultiplier = 1;
    public int pierceMultiplier = 0;

    public float capacity = 0;
    public float usage = 0;
    public float laserRange = 6;
    public int maxNodes = 3;
    public float powerProduction = 0;

    public float displayedSpeed = 0;
    public float launchTime = 1;

    //unit fields
    public float armor = 0;
    public float hitSize = 6;
    public boolean flying = true;
    public float speed = 1.1f;
    public float buildSpeed = -1;
    public float mineSpeed = 1;
    public int mineTier = -1;
    public float payloadCapacity = 8;
    public int commandLimit = 8;
    public Seq<Ability> abilities = new Seq<>();
    public boolean canBoost = false;
    public float maxRange = -1f;
    public Seq<Weapon> weapons = new Seq<>();

    //item and liquid fields
    public float explosiveness = 0;
    public float flammability = 0;
    public float radioactivity = 0;
    public float charge = 0;
    public float heatCapacity = 0.5f;
    public float viscosity = 0.5f;
    public float temperature = 0.5f;

    //status fields
    public float damageMultiplier = 1f;
    public float healthMultiplier = 1f;
    public float speedMultiplier = 1f;
    public float reloadMultiplier = 1f;
    public float buildSpeedMultiplier = 1f;
    public ObjectSet<StatusEffect> affinities = new ObjectSet<>(), opposites = new ObjectSet<>();

    public InfoTestBlock(String name) {
        super(name);
        this.guild = "Sandbox";
        this.subGuild = "Testers";
        this.branch = "Blocks";
        stats = aStats.copy(stats);
        destructible = solid = update = true;
        //buildVisibility = BuildVisibility.sandboxOnly;
        buildVisibility = BuildVisibility.debugOnly;
        category = Category.effect;
        hideDetails = false;
        health = 300;
        size = 3;
        //consumes.add(new ConsumeCoolant(0.01f)).update(false);
    }
    @Override
    public void setStats() {
        aStats.add(SnStat.nullStat, nullStat, StatUnit.none);
    }
    public class InfoBuild extends Building {
        @Override
        public void draw() {
            super.draw();
            ADrawf.drawText(this, Color.white, ": " + info + " :");
        }
    }
}
