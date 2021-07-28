package sunset.content.blocks;

import static mindustry.type.ItemStack.with;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.ctype.ContentList;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.production.Drill;
import mindustry.world.Block;
import mindustry.world.blocks.production.Cultivator;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.GenericSmelter;
import mindustry.world.draw.DrawAnimation;
import mindustry.world.draw.DrawRotator;
import sunset.content.SnFx;
import sunset.content.SnItems;
import sunset.world.blocks.production.StaticTopSmelter;
import sunset.world.draw.Draw360Rotator;
import sunset.world.draw.DrawRotatorTop;
import sunset.world.draw.DrawWeaveTop;

public class SnProduction implements ContentList{
    public static Block

    //advanced
    advancedCompressor, advancedWeaver, advancedCultivator, advancedKiln, advancedSurge,

    //standart
    crystalyze, purifier, enojieKiln,
    
    //drills
    percussionDrill;


  @Override
  public void load(){

    //advanced
    advancedCompressor = new GenericSmelter("advancedCompressor"){{
        requirements(Category.crafting, with(Items.lead, 150, Items.graphite, 120, Items.silicon, 210, Items.titanium, 110, Items.thorium, 200, Items.phaseFabric, 110));

        size = 3;
        craftEffect = SnFx.modPlasticburn;
        craftTime = 60f;
        outputItem = new ItemStack(Items.plastanium, 5);
        drawer = new DrawAnimation() {
          					{
          						frameCount = 4;
          						frameSpeed = 4.8f;
          					}
        };
        itemCapacity = 20;
        liquidCapacity = 20f;
        absorbLasers = true;

        consumes.items(with(Items.titanium, 9));
        consumes.liquid(Liquids.oil, 0.4f);
        consumes.power(5.3f);
    }};
   
    advancedWeaver = new GenericSmelter("advancedWeaver"){{
        requirements(Category.crafting, with(Items.copper, 210, Items.metaglass, 90, Items.silicon, 190, Items.titanium, 100, Items.thorium, 185, Items.surgeAlloy, 110));
        size = 3;
        health = 890;
        craftEffect = SnFx.modSmeltsmoke;
        craftTime = 180f;
        outputItem = new ItemStack(Items.phaseFabric, 6);
        drawer = new DrawWeaveTop();
        itemCapacity = 50;
        liquidCapacity = 30f;

        ambientSound = Sounds.techloop;
        ambientSoundVolume = 0.04f;

        consumes.items(with(Items.sand, 22, Items.thorium, 10));
        consumes.liquid(Liquids.cryofluid, 0.3f);
        consumes.power(6.1f);
    }};

    advancedCultivator = new Cultivator("advancedCultivator"){{
        requirements(Category.production, with(Items.copper, 200, Items.lead, 200, Items.silicon, 180, Items.metaglass, 140, Items.titanium, 170, Items.phaseFabric, 155));

        size = 4;
        health = 990;
        craftEffect = SnFx.modSmeltsmoke;
        craftTime = 200f;
        drawer = new DrawRotatorTop();
        outputItem = new ItemStack(Items.sporePod, 5);
        itemCapacity = 30;
        liquidCapacity = 40f;

        consumes.liquid(Liquids.water, 0.6f);
        consumes.power(2f);
    }};
    
    advancedKiln = new GenericSmelter("advancedKiln"){{
        requirements(Category.crafting, with(Items.copper, 210, Items.lead, 220, Items.metaglass, 190, Items.graphite, 185, Items.silicon, 200, Items.titanium, 210, Items.thorium, 205));

        size = 4;
        health = 1200;
        craftEffect = SnFx.modSmokeCloud;
        craftTime = 95f;
        outputItem = new ItemStack(Items.metaglass, 9);
        itemCapacity = 15;
        flameColor = Color.valueOf("ffc099");
        liquidCapacity = 100f;

        consumes.items(with(Items.lead, 7, Items.sand, 5));
        consumes.liquid(Liquids.water, 0.75f);
        consumes.power(7f);
    }};


    advancedSurge = new StaticTopSmelter("advancedSurge"){{
        requirements(Category.crafting, with(Items.lead, 130, Items.metaglass, 100, Items.silicon, 150, Items.thorium, 110, Items.surgeAlloy, 75));

        size = 5;
        craftEffect = SnFx.modSmokeCloud;
        craftTime = 90f;
        outputItem = new ItemStack(Items.surgeAlloy, 7);
        itemCapacity = 50;
        liquidCapacity = 80f;
        drawer = new Draw360Rotator();

        consumes.items(with(Items.copper, 10, Items.lead, 12, Items.titanium, 8, Items.silicon, 10, Items.pyratite, 3));
        consumes.liquid(Liquids.water, 0.87f);
        consumes.power(5.5f);
    }};
    
//standart
    purifier = new GenericCrafter("purifier"){{
        requirements(Category.crafting, with(Items.copper, 120, Items.titanium, 95, Items.silicon, 80, Items.plastanium, 65));

        outputItem = new ItemStack(SnItems.nobium, 2);
        craftEffect = Fx.pulverize;
        updateEffect = Fx.pulverizeSmall;
        craftTime = 25f;
        size = 3;
        hasPower = true;
        hasLiquids = false;
        drawer = new DrawRotator();
        ambientSound = Sounds.grinding;
        ambientSoundVolume = 0.025f;

        consumes.items(with(Items.titanium, 3, Items.thorium, 2, SnItems.fors, 1));
        consumes.power(2.5f);
    }};

    crystalyze = new GenericSmelter("crystalyze"){{
        requirements(Category.crafting, with(Items.lead, 130, Items.thorium, 110, Items.silicon, 120, SnItems.nobium, 90, Items.plastanium, 75));

        craftEffect = SnFx.crystalyze;
        updateEffect = SnFx.crystalyzeSmall;
        hasItems = true;
        itemCapacity = 30;
        liquidCapacity = 30f;
        outputItem = new ItemStack(SnItems.naturite, 2);
        craftTime = 34f;
        size = 4;
        hasPower = hasItems = true;
        flameColor = Color.valueOf("F9ECA3");

        consumes.item(Items.sand, 4);
        consumes.liquid(Liquids.water, 0.20f);
        consumes.power(2.7f);
    }};

    enojieKiln = new GenericCrafter("enojie-kiln"){{
        requirements(Category.crafting, with(Items.lead, 200, SnItems.nobium, 150, Items.graphite, 140, Items.silicon, 120, Items.surgeAlloy, 80));

       outputItem = new ItemStack(SnItems.enojie, 1);
       craftTime = 36f;
       size = 4;
       hasPower = true;
       hasLiquids = false;
       craftEffect = SnFx.enojiecraft;
       updateEffect = SnFx.enojieburn;

       consumes.items(with(SnItems.nobium, 2, SnItems.planatrium, 3, Items.metaglass, 1));
       consumes.power(5.3f);
    }};

//drills
        percussionDrill = new Drill("percussion-drill"){{
            requirements(Category.production, with(Items.copper, 100, Items.silicon, 90, Items.titanium, 90, Items.thorium, 85, SnItems.nobium, 80, SnItems.naturite, 70));
            drillTime = 235;
            size = 5;
            drawRim = true;
            hasPower = true;
            tier = 6;
            updateEffect = Fx.pulverizeRed;
            updateEffectChance = 0.04f;
            drillEffect = Fx.mineHuge;
            rotateSpeed = 8f;
            warmupSpeed = 0.01f;

            //more than the laser drill
            liquidBoostIntensity = 2.0f;

            consumes.power(6f);
            consumes.liquid(Liquids.water, 0.3f).boost();
        }};
  }
}
