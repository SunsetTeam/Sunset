package sunset.content.blocks;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.ctype.ContentList;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.LiquidConverter;
import mindustry.world.draw.DrawAnimation;
import mindustry.world.draw.DrawMixer;
import mindustry.world.draw.DrawRotator;
import mindustry.world.draw.DrawSmelter;
import mma.world.draw.MultiDrawSmelter;
import sunset.content.SnFx;
import sunset.content.SnItems;
import sunset.content.SnLiquids;
import sunset.world.draw.DrawAngleRotator;
import sunset.world.draw.DrawModWeave;
import sunset.world.draw.DrawSurge;
import sunset.world.draw.MultiDrawBlock;

import static mindustry.type.ItemStack.with;

public class SnProduction implements ContentList {
    public static Block

            //advanced
            advancedCompressor, advancedWeaver, advancedKiln, advancedSurge, advancedCryomixer,

    //standard
    collider, purifier, crystallizer, enojieKiln;

    @Override
    public void load() {
        //region advanced
        advancedKiln = new GenericCrafter("advanced-kiln") {{
            requirements(Category.crafting, with(Items.copper, 210, Items.lead, 220, Items.metaglass, 190, Items.graphite, 185, Items.silicon, 200, Items.titanium, 210, Items.thorium, 205));

            size = 3;
            health = 1200;
            craftEffect = Fx.smeltsmoke;
            craftTime = 95f;
            outputItem = new ItemStack(Items.metaglass, 9);
            itemCapacity = 15;
            drawer = new MultiDrawSmelter() {{
                flameColor = Color.valueOf("ffc099");
                flameRadius*=1f/2f;
                flameRadiusIn*=1f/2f;
                flameRadiusMag*=1f/1.5f;
                flameRadiusInMag*=1f/1.5f;
                flamePoints(
                        new FlamePoint(1f/3f,1f/3f),
                        new FlamePoint(1f-1f/3f,1f/3f),
                        new FlamePoint(1f/3f,1f-1f/3f),
                        new FlamePoint(1f-1f/3f,1f-1f/3f),
                        new FlamePoint(0.5f,0.5f,0.9f)
                );
                drawTopOnce=true;
            }};
            liquidCapacity = 100f;

            consumes.items(with(Items.lead, 7, Items.sand, 5));
            consumes.liquid(Liquids.water, 0.75f);
            consumes.power(7f);
        }};

        advancedCompressor = new GenericCrafter("advanced-compressor") {{
            requirements(Category.crafting, with(Items.lead, 150, Items.graphite, 120, Items.silicon, 210, Items.titanium, 110, Items.thorium, 200, Items.phaseFabric, 110));

            size = 3;
            craftEffect = SnFx.modPlasticburn;
            craftTime = 60f;
            outputItem = new ItemStack(Items.plastanium, 5);
            drawer = new DrawGlow();
            itemCapacity = 20;
            liquidCapacity = 20f;
            absorbLasers = true;
            consumes.items(with(Items.titanium, 9));
            consumes.liquid(Liquids.oil, 0.35f);
            consumes.power(5.3f);
        }};

        advancedWeaver = new GenericCrafter("advanced-weaver") {{
            requirements(Category.crafting, with(Items.copper, 210, Items.metaglass, 90, Items.silicon, 190, Items.titanium, 100, Items.thorium, 185, Items.surgeAlloy, 110));
            size = 3;
            health = 890;
            craftEffect = SnFx.weaverSmeltsmoke;
            craftTime = 180f;
            outputItem = new ItemStack(Items.phaseFabric, 6);
            drawer = new MultiDrawBlock(new DrawSmelter(), new DrawModWeave());
            itemCapacity = 50;
            liquidCapacity = 30f;

            ambientSound = Sounds.techloop;
            ambientSoundVolume = 0.04f;

            consumes.items(with(Items.sand, 22, Items.thorium, 10));
            consumes.liquid(Liquids.cryofluid, 0.3f);
            consumes.power(6.1f);
        }};

        advancedCryomixer = new LiquidConverter("advanced-cryomixer") {{
            requirements(Category.crafting, with(Items.lead, 180, Items.graphite, 90, SnItems.coldent, 60, Items.metaglass, 40));
            outputLiquid = new LiquidStack(Liquids.cryofluid, 1f);
            craftTime = 40f;
            size = 4;
            hasPower = true;
            hasItems = true;
            hasLiquids = true;
            rotate = false;
            solid = true;
            outputsLiquid = true;
            drawer = new DrawMixer();

            consumes.power(2.5f);
            consumes.item(SnItems.coldent);
            consumes.liquid(Liquids.water, 1f);
        }};

        advancedSurge = new GenericCrafter("advanced-surge") {{
            requirements(Category.crafting, with(Items.lead, 130, Items.metaglass, 100, Items.silicon, 150, Items.thorium, 110, Items.surgeAlloy, 75));

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.07f;

            size = 5;
            craftEffect = SnFx.modSmokeCloud;
            craftTime = 90f;
            outputItem = new ItemStack(Items.surgeAlloy, 7);
            itemCapacity = 50;
            liquidCapacity = 80f;
            drawer = new MultiDrawBlock(new DrawAngleRotator(360), new DrawSurge());

            consumes.items(with(Items.copper, 10, Items.lead, 12, Items.titanium, 8, Items.silicon, 10, Items.pyratite, 3));
            consumes.liquid(Liquids.cryofluid, 0.70f);
            consumes.power(5.5f);
        }};
        //endregion advanced

        //region standard
        collider = new GenericCrafter("collider") {{
            requirements(Category.crafting, with(Items.copper, 110, Items.titanium, 105, Items.silicon, 65, SnItems.fors, 50));

            outputItem = new ItemStack(SnItems.reneubite, 3);
//          craftEffect = SnFx.;
//          updateEffect = SnFx.;
            craftTime = 46f;
            size = 3;
            itemCapacity = 15;
            liquidCapacity = 30f;
            hasPower = true;
            hasLiquids = true;
            ambientSound = Sounds.grinding;
            ambientSoundVolume = 0.025f;

            consumes.items(with(Items.blastCompound, 3, Items.titanium, 2));
            consumes.liquid(SnLiquids.burheyna, 0.4f);
            consumes.power(3.5f);
        }};

        purifier = new GenericCrafter("purifier") {{
            requirements(Category.crafting, with(Items.copper, 120, Items.titanium, 95, Items.silicon, 80, Items.plastanium, 65));

            outputItem = new ItemStack(SnItems.nobium, 2);
            craftEffect = Fx.pulverize;
            updateEffect = Fx.pulverizeSmall;
            craftTime = 25f;
            itemCapacity = 20;
            size = 3;
            hasPower = true;
            hasLiquids = false;
            drawer = new DrawRotator();
            ambientSound = Sounds.grinding;
            ambientSoundVolume = 0.025f;

            consumes.items(with(Items.titanium, 3, Items.thorium, 2, SnItems.fors, 1));
            consumes.power(2.5f);
        }};

        crystallizer = new GenericCrafter("crystallizer") {{
            requirements(Category.crafting, with(Items.lead, 130, Items.thorium, 110, Items.silicon, 120, SnItems.nobium, 90, Items.plastanium, 75));

            craftEffect = SnFx.crystalyze;
            updateEffect = SnFx.crystalyzeSmall;
            hasItems = true;
            itemCapacity = 30;
            liquidCapacity = 30f;
            outputItem = new ItemStack(SnItems.naturite, 2);
            craftTime = 34f;
            size = 3;
            hasPower = true;
            drawer = new DrawSmelter() {{
                flameColor = Color.valueOf("F9ECA3");
            }};

            consumes.item(Items.sand, 4);
            consumes.liquid(Liquids.water, 0.20f);
            consumes.power(2.7f);
        }};

        enojieKiln = new GenericCrafter("enojie-kiln") {{
            requirements(Category.crafting, with(Items.lead, 200, SnItems.nobium, 150, Items.graphite, 140, Items.silicon, 120, Items.surgeAlloy, 80));

            outputItem = new ItemStack(SnItems.enojie, 2);
            craftTime = 55f;
            size = 4;
            hasPower = true;
            hasLiquids = false;
            craftEffect = SnFx.enojiecraft;
            updateEffect = SnFx.enojieburn;

            consumes.items(with(SnItems.nobium, 2, SnItems.planatrium, 3, Items.metaglass, 1));
            consumes.power(5.3f);
        }};
        //endregion standard
    }
}
