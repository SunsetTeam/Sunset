package sunset.content.blocks;

import arc.graphics.*;
import gas.world.consumers.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.production.*;
import mindustry.world.draw.*;
import mma.world.draw.*;
import sunset.content.*;
import sunset.world.blocks.gas.*;
import sunset.world.draw.*;

import static mindustry.type.ItemStack.with;

public class SnCrafting{
    public static Block

    //advanced
    advancedCompressor, advancedWeaver, advancedKiln, advancedSurge, advancedCryomixer,
    //standard
    collider, purifier, crystallizer, anzarKiln, enojieKiln,
    //missile
    missilecrafter, missilePlant;

    public static void load(){
        //region advanced
        advancedKiln = new GenericCrafter("advanced-kiln"){{
            requirements(Category.crafting, with(Items.titanium, 150, Items.graphite, 80, Items.metaglass, 80, Items.silicon, 60, Items.plastanium, 35));

            size = 3;
            health = 1200;
            craftEffect = Fx.smeltsmoke;
            craftTime = 95f;
            outputItem = new ItemStack(Items.metaglass, 9);
            itemCapacity = 15;
            drawer = new DrawMulti(new DrawDefault(), new MultiDrawFlame(){{
                flameColor = Color.valueOf("ffc099");
                flameRadius *= 1f / 2f;
                flameRadiusIn *= 1f / 2f;
                flameRadiusMag *= 1f / 1.5f;
                flameRadiusInMag *= 1f / 1.5f;
                flamePoints(
                new FlamePoint(1f / 3f, 1f / 3f),
                new FlamePoint(1f - 1f / 3f, 1f / 3f),
                new FlamePoint(1f / 3f, 1f - 1f / 3f),
                new FlamePoint(1f - 1f / 3f, 1f - 1f / 3f),
                new FlamePoint(0.5f, 0.5f, 0.9f)
                );
                drawTopOnce = true;
            }})/*new MultiDrawSmelter(){{
                flameColor = Color.valueOf("ffc099");
                flameRadius *= 1f / 2f;
                flameRadiusIn *= 1f / 2f;
                flameRadiusMag *= 1f / 1.5f;
                flameRadiusInMag *= 1f / 1.5f;
                flamePoints(
                new FlamePoint(1f / 3f, 1f / 3f),
                new FlamePoint(1f - 1f / 3f, 1f / 3f),
                new FlamePoint(1f / 3f, 1f - 1f / 3f),
                new FlamePoint(1f - 1f / 3f, 1f - 1f / 3f),
                new FlamePoint(0.5f, 0.5f, 0.9f)
                );
                drawTopOnce = true;
            }}*/;
            liquidCapacity = 100f;

            consumeItems(with(Items.lead, 7, Items.sand, 5));
            consumeLiquid(Liquids.water, 0.75f);
            consumePower(7f);
        }};
        advancedCompressor = new GenericCrafter("advanced-compressor"){{
            requirements(Category.crafting, with(Items.titanium, 150, Items.graphite, 120, Items.silicon, 110, Items.metaglass, 80, Items.plastanium, 60));

            size = 3;
            craftEffect = SnFx.modPlasticBurn;
            craftTime = 60f;
            outputItem = new ItemStack(Items.plastanium, 5);
            drawer = new DrawMulti(new DrawDefault(), new DrawFade());
            itemCapacity = 20;
            liquidCapacity = 20f;
            absorbLasers = true;
            consumeItems(with(Items.titanium, 9));
            consumeLiquid(Liquids.oil, 0.35f);
            consumePower(5.3f);
        }};
        advancedWeaver = new GenericCrafter("advanced-weaver"){{
            requirements(Category.crafting, with(Items.lead, 190, Items.thorium, 160, Items.silicon, 145, Items.titanium, 80, Items.phaseFabric, 70));
            size = 3;
            health = 890;
            craftEffect = SnFx.weaverSmeltsmoke;
            craftTime = 180f;
            outputItem = new ItemStack(Items.phaseFabric, 6);
            drawer = new DrawModWeave();
            itemCapacity = 50;
            liquidCapacity = 30f;

            ambientSound = Sounds.techloop;
            ambientSoundVolume = 0.04f;

            consumeItems(with(Items.sand, 22, Items.thorium, 10));
            consumeLiquid(Liquids.cryofluid, 0.3f);
            consumePower(6.1f);
        }};
        advancedCryomixer = new GenericCrafter("advanced-cryomixer"){{
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
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.cryofluid), new DrawDefault());

            consumePower(2.5f);
            consumeItem(SnItems.coldent);
            consumeLiquid(Liquids.water, 1f);
        }};
        advancedSurge = new GenericCrafter("advanced-surge"){{
            requirements(Category.crafting, with(Items.thorium, 280, Items.silicon, 200, Items.lead, 160, Items.surgeAlloy, 130, Items.plastanium, 110));

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.07f;

            size = 5;
            craftEffect = SnFx.modSmokeCloud;
            craftTime = 90f;
            outputItem = new ItemStack(Items.surgeAlloy, 7);
            itemCapacity = 50;
            liquidCapacity = 80f;
            drawer = new DrawMulti(new DrawAngleRotator(360), new DrawSurge());

            consumeItems(with(Items.copper, 10, Items.lead, 12, Items.titanium, 8, Items.silicon, 10, Items.pyratite, 3));
            consumeLiquid(Liquids.cryofluid, 0.70f);
            consumePower(5.5f);
        }};
        //endregion advanced
        //region standard
        crystallizer = new GenericCrafter("crystallizer"){{
            requirements(Category.crafting, with(SnItems.fors, 100, SnItems.erius, 130));

            craftEffect = SnFx.crystalyze;
            updateEffect = SnFx.crystalyzeSmall;
            hasItems = true;
            itemCapacity = 30;
            liquidCapacity = 30f;
            outputItem = new ItemStack(SnItems.naturite, 2);
            craftTime = 43f;
            size = 3;
            hasPower = true;
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("f9eca3")));/* new DrawSmelter(){{
                flameColor = Color.valueOf("F9ECA3");
            }};*/

            consumeLiquid(SnLiquids.burheyna, 0.40f);
            consumePower(3.2f);
        }};
        anzarKiln = new GenericCrafter("anzar-kiln"){{
            requirements(Category.crafting, with(SnItems.fors, 80, SnItems.naturite, 60, SnItems.erius, 40));

            hasItems = true;
            itemCapacity = 30;
            outputItem = new ItemStack(SnItems.anzar, 1);
            craftTime = 40f;
            size = 2;
            hasPower = true;
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("f9eca3"))); /*new DrawSmelter(){{
                flameColor = Color.valueOf("F9ECA3");
            }};*/

            consumeItems(with(SnItems.fors, 2, SnItems.naturite, 1));
            consumePower(2.3f);
        }};

        purifier = new GenericCrafter("purifier"){{
            requirements(Category.crafting, with(SnItems.fors, 110, SnItems.erius, 95, SnItems.naturite, 80, SnItems.anzar, 65));

            outputItem = new ItemStack(SnItems.nobium, 1);
            craftEffect = Fx.pulverize;
            updateEffect = Fx.pulverizeSmall;
            craftTime = 20f;
            itemCapacity = 20;
            size = 3;
            hasPower = true;
            hasLiquids = false;
            drawer = new DrawMulti(new DrawDefault(), new DrawRegion("-rotator"){{
                spinSprite = true;
                rotateSpeed = 2f;
            }}, new DrawRegion("-top"));//was {new DrawRotator()}
            ambientSound = Sounds.grinding;
            ambientSoundVolume = 0.025f;

            consumeItems(with(SnItems.fors, 2, SnItems.nedirium, 1, SnItems.erius, 1));
            consumePower(4.3f);
        }};

        collider = new GasCrafter("collider"){{
            requirements(Category.crafting, with(SnItems.fors, 105, SnItems.erius, 100, SnItems.anzar, 65));

            outputItem = new ItemStack(SnItems.reneubite, 3);
            //craftEffect = SnFx.;
            //updateEffect = SnFx.;
            craftTime = 55f;
            size = 3;
            itemCapacity = 15;
            liquidCapacity = 40f;
            gasCapacity = 40f;
            hasPower = true;
            hasLiquids = true;
            ambientSound = Sounds.grinding;
            ambientSoundVolume = 0.025f;

            consumeItem(SnItems.nedirium, 2);
            consumeGas(SnGas.hyneris, 0.7f);
            consumeLiquid(SnLiquids.burheyna, 0.8f);
            consumePower(4.6f);
        }};

        enojieKiln = new GenericCrafter("enojie-kiln"){{
            requirements(Category.crafting, with(SnItems.erius, 180, SnItems.nobium, 150, SnItems.naturite, 140, SnItems.anzar, 120));

            outputItem = new ItemStack(SnItems.enojie, 1);
            craftTime = 58f;
            size = 4;
            hasPower = true;
            hasLiquids = false;
            craftEffect = SnFx.enojieCraft;
            updateEffect = SnFx.enojieBurn;

            consumeItems(with(SnItems.nobium, 1, SnItems.planatrium, 2, SnItems.erius, 1));
            consumePower(6f);
        }};
        //endregion standard
        //region missile
        // I need multicrafters to do this fine
        //endregion missile
    }
}
