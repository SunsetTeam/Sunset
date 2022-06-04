package sunset.content.blocks;

import arc.graphics.Color;
import gas.world.consumers.ConsumeGas;
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
import mindustry.world.draw.DrawGlow;
import mindustry.world.draw.DrawMixer;
import mindustry.world.draw.DrawRotator;
import mindustry.world.draw.DrawSmelter;
import mma.world.draw.MultiDrawSmelter;
import sunset.content.SnFx;
import sunset.content.SnGas;
import sunset.content.SnItems;
import sunset.content.SnLiquids;
import sunset.world.blocks.gas.GasCrafter;
import sunset.world.draw.DrawAngleRotator;
import sunset.world.draw.DrawModWeave;
import sunset.world.draw.DrawSurge;

import static mindustry.type.ItemStack.with;

public class SnCrafting implements ContentList{
    public static Block

    //advanced
    advancedCompressor, advancedWeaver, advancedKiln, advancedSurge, advancedCryomixer,
    //standard
    collider, purifier, crystallizer, anzarKiln, enojieKiln,
    //missile
    missilecrafter, missilePlant;

    @Override
    public void load(){
        //region advanced

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
            drawer = new mma.world.draw.MultiDrawBlock(new DrawAngleRotator(360), new DrawSurge());

            consumes.items(with(Items.copper, 10, Items.lead, 12, Items.titanium, 8, Items.silicon, 10, Items.pyratite, 3));
            consumes.liquid(Liquids.cryofluid, 0.70f);
            consumes.power(5.5f);
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
            drawer = new DrawSmelter(){{
                flameColor = Color.valueOf("F9ECA3");
            }};

            consumes.liquid(SnLiquids.burheyna, 0.40f);
            consumes.power(3.2f);
        }};
        anzarKiln = new GenericCrafter("anzar-kiln"){{
            requirements(Category.crafting, with(SnItems.fors, 80, SnItems.naturite, 60, SnItems.erius, 40));

            hasItems = true;
            itemCapacity = 30;
            outputItem = new ItemStack(SnItems.anzar, 1);
            craftTime = 40f;
            size = 2;
            hasPower = true;
            drawer = new DrawSmelter(){{
                flameColor = Color.valueOf("F9ECA3");
            }};

            consumes.items(with(SnItems.fors, 2, SnItems.naturite, 1));
            consumes.power(2.3f);
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
            drawer = new DrawRotator();
            ambientSound = Sounds.grinding;
            ambientSoundVolume = 0.025f;

            consumes.items(with(SnItems.fors, 2, SnItems.nedirium, 1, SnItems.erius, 1));
            consumes.power(4.3f);
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

            consumes.item(SnItems.nedirium, 2);
            consumes.addGas(new ConsumeGas(SnGas.hyneris, 0.7f));
            consumes.liquid(SnLiquids.burheyna, 0.8f);
            consumes.power(4.6f);
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

            consumes.items(with(SnItems.nobium, 1, SnItems.planatrium, 2, SnItems.erius, 1));
            consumes.power(6f);
        }};
        //endregion standard
        //region missile
        // I need multicrafters to do this fine
        //endregion missile
    }
}
