package sunset.content.blocks;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.ctype.ContentList;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.blocks.power.DecayGenerator;
import mindustry.world.blocks.power.NuclearReactor;
import sunset.content.SnItems;
import sunset.content.SnLiquids;
import sunset.world.blocks.power.LiquidGenerator;
import sunset.world.blocks.power.ThermalGeneratorExt;

import static mindustry.type.ItemStack.with;

public class SnPower implements ContentList {
    public static Block
                //generators
                oilGenerator, advrtgGenerator, advThermalGenerator,

                //reactors
                differentialReactor, planatriumReactor;

    @Override
    public void load() {
        //region generators
        oilGenerator = new LiquidGenerator("oil-generator") {{
            requirements(Category.power, with(Items.copper, 110, Items.titanium, 70, Items.lead, 120, Items.silicon, 55, Items.metaglass, 70));
            powerProduction = 8.3f;
            itemDuration = 220f;
            minLiquidEfficiency = 0.2f;
            maxLiquidGenerate = 0.4f;
            liquidCapacity = 50;
            hasLiquids = true;
            hasItems = false;
            size = 3;
            ambientSound = Sounds.steam;
            ambientSoundVolume = 0.03f;

            consumes.liquid(Liquids.oil, 0.18f);
            }};

        advrtgGenerator = new DecayGenerator("advance-rtg-generator") {{
            requirements(Category.power, with(Items.lead, 240, Items.silicon, 185, Items.phaseFabric, 75, Items.plastanium, 125, Items.thorium, 130, SnItems.planatrium, 100));
            size = 4;
            powerProduction = 13.2f;
            itemDuration = 55 * 12f;
        }};

        advThermalGenerator = new ThermalGeneratorExt("advanced-thermal-generator") {{
            requirements(Category.power, with(Items.copper, 140, Items.graphite, 90, SnItems.naturite, 80, Items.silicon, 40, Items.metaglass, 60));
            size = 3;
            powerProduction = 2.8f;
            attributeScale = 1.2352941f;
            generateEffect = Fx.redgeneratespark;
            floating = true;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
            consumes.liquid(Liquids.water, 0.35f);
        }};
        //endregion generators
        //region reactors
        differentialReactor = new NuclearReactor("differential-reactor") {{
            requirements(Category.power, with(Items.copper, 200, Items.titanium, 120, Items.lead, 250, Items.silicon, 130, Items.metaglass, 100));
            ambientSound = Sounds.steam;
            ambientSoundVolume = 0.03f;
            size = 4;
            health = 1300;
            explosionRadius = 10;
            explosionDamage = 1300;
            itemCapacity = 35;
            liquidCapacity = 70;
            itemDuration = 240f;
            powerProduction = 40f;
            explodeEffect = Fx.impactReactorExplosion;
            heating = 0.03f;
            consumes.item(SnItems.reneubite).optional(true, false);
            consumes.liquid(SnLiquids.burheyna, heating / coolantPower).update(false);
        }};

        planatriumReactor = new NuclearReactor("planatrium-reactor") {{
            requirements(Category.power, with(Items.lead, 400, Items.silicon, 270, Items.graphite, 220, SnItems.planatrium, 200, SnItems.fors, 180, SnItems.nobium, 120));
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.24f;
            size = 5;
            explosionRadius = 21;
            explosionDamage = 2100;
            health = 3100;
            itemCapacity = 50;
            liquidCapacity = 80;
            itemDuration = 280f;
            powerProduction = 180f;
            explodeEffect = Fx.reactorExplosion;
            consumes.item(SnItems.planatrium);
            heating = 0.07f;
            consumes.liquid(Liquids.cryofluid, heating / coolantPower).update(false);
        }};
        //endregion reactors
    }
}
