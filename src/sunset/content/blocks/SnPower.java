package sunset.content.blocks;

import static mindustry.type.ItemStack.with;

import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.ctype.ContentList;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.power.NuclearReactor;
import mindustry.world.blocks.power.DecayGenerator;
import sunset.world.blocks.power.LiquidGenerator;
import sunset.content.SnItems;

public class SnPower implements ContentList{
    public static Block

//reactors
    oilGenerator, advrtgGenerator, planatriumReactor;

    @Override
    public void load() {
    
    oilGenerator = new LiquidGenerator("oil-generator"){{
            requirements(Category.power, with(Items.copper, 110, Items.titanium, 70, Items.lead, 120, Items.silicon, 55, Items.metaglass, 70));
            powerProduction = 13f;
            itemDuration = 220f;
            minLiquidEfficiency = 0.2f;
            maxLiquidGenerate = 0.4f;
            liquidCapacity = 20;
            hasLiquids = true;
            hasItems = false;
            size = 3;
            ambientSound = Sounds.steam;
            ambientSoundVolume = 0.03f;

            consumes.liquid(Liquids.oil, 0.18f);
        }};

    advrtgGenerator = new DecayGenerator("advance-rtg-generator"){{
            requirements(Category.power, with(Items.lead, 240, Items.silicon, 185, Items.phaseFabric, 75, Items.plastanium, 125, Items.thorium, 130, SnItems.planatrium, 100));
            size = 4;
            powerProduction = 9.5f;
            itemDuration = 55 * 12f;
        }};
     
    planatriumReactor = new NuclearReactor("planatrium-reactor"){{
            requirements(Category.power, with(Items.lead, 400, Items.silicon, 270, Items.graphite, 220, SnItems.planatrium, 200, SnItems.fors, 180, SnItems.nobium, 120));
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.24f;
            size = 5;
            explosionRadius = 21;
            explosionDamage = 2100;
            health = 4100;
            itemCapacity = 50;
            liquidCapacity = 80;
            itemDuration = 280f;
            powerProduction = 38f;
//latter            explodeEffect = Fx.reactorExplosion;
            consumes.item(SnItems.planatrium);
            heating = 0.07f;
            consumes.liquid(Liquids.cryofluid, heating / coolantPower).update(false);
        }};
}
}
