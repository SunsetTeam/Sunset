package sunset.content.blocks;

import arc.graphics.*;
import arc.util.Time;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.production.*;
import mindustry.world.draw.*;
import mma.type.Recipe;
import mma.world.blocks.production.MultiCrafter;
import mma.world.draw.*;
import sunset.content.*;
import sunset.world.blocks.gas.*;
import sunset.world.blocks.production.crafting.AccelGenericCrafter;
import sunset.world.draw.*;

import static mindustry.type.ItemStack.with;

public class SnCrafting{
    public static Block
    //standard
    anzarCaster, crystallizer, nobiumCaster, nobiumMagnetizer, collider, enojieSynthesizer, zeriniumZavod, bioSynthesizer,
    //missile
    missilecrafter, missilePlant;

    public static void load(){
        //region standard
        anzarCaster = new GenericCrafter("anzar-caster"){{
            requirements(Category.crafting, with(SnItems.fors, 80, SnItems.naturite, 60, SnItems.erius, 40));

            hasItems = true;
            itemCapacity = 15;
            outputItem = new ItemStack(SnItems.zarium, 1);
            craftTime = 36f;
            warmupSpeed = 0.009f;
            size = 2;
            hasPower = true;
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("f9eca3")));

            consumeItems(with(SnItems.fors, 1, SnItems.naturite, 1));
            consumePower(2f);
        }};

        crystallizer = new AttributeCrafter("crystallizer"){{
            requirements(Category.crafting, with(SnItems.fors, 100, SnItems.erius, 130));

            craftEffect = SnFx.crystalyze;
            updateEffect = SnFx.crystalyzeSmall;
            floating = true;
            placeableLiquid = true;
            displayEfficiency = false;
            hasItems = true;
            itemCapacity = 30;
            liquidCapacity = 30f;
            outputItem = new ItemStack(SnItems.naturite, 2);
            craftTime = 40f;
            size = 3;
            hasPower = true;
            squareSprite = false;

            consumeLiquid(SnLiquids.burheyna, 3f / 60f);
            consumePower(2.6f);
        }};

        nobiumCaster = new GenericCrafter("nobium-caster"){{
            requirements(Category.crafting, with(SnItems.fors, 110, SnItems.erius, 95, SnItems.naturite, 80, SnItems.zarium, 65));

            outputItem = new ItemStack(SnItems.nobium, 3);
            craftEffect = Fx.pulverize;
            updateEffect = Fx.pulverizeSmall;
            craftTime = 50f;
            itemCapacity = 25;
            size = 3;
            hasPower = true;
            hasLiquids = false;
            drawer = new DrawMulti(new DrawDefault(), new DrawRegion("-rotator"){{
                spinSprite = true;
                rotateSpeed = 2f;
            }}, new DrawRegion("-top"));//was {new DrawRotator()}
            ambientSound = Sounds.grinding;
            ambientSoundVolume = 0.025f;

            consumeItems(with(SnItems.fors, 3, SnItems.nedirium, 2, SnItems.erius, 1));
            consumePower(4.3f);
        }};

        nobiumMagnetizer = new GenericCrafter("nobium-magnetizer"){{
            requirements(Category.crafting, with(SnItems.fors, 190, SnItems.erius, 150, SnItems.zarium, 100, SnItems.nobium, 40));

            outputItem = new ItemStack(SnItems.nobium, 10);
            craftEffect = Fx.pulverize;
            updateEffect = Fx.pulverizeSmall;
            craftTime = 200f;
            itemCapacity = 40;
            size = 4;
            hasPower = true;
            hasLiquids = false;
            ambientSound = Sounds.grinding;
            ambientSoundVolume = 0.025f;

            consumeItems(with(SnItems.fors, 6, SnItems.nedirium, 5, SnItems.erius, 3));
            consumePower(7f);
        }};

        collider = new GenericCrafter("collider"){{
            requirements(Category.crafting, with(SnItems.fors, 105, SnItems.erius, 100, SnItems.zarium, 65));

            outputItem = new ItemStack(SnItems.lenothium, 1);
            //craftEffect = SnFx.;
            //updateEffect = SnFx.;
            craftTime = 25f;
            size = 3;
            itemCapacity = 15;
            liquidCapacity = 30f;
            hasPower = true;
//            hasGasses = true;
            hasItems = true;
            ambientSound = Sounds.grinding;
            ambientSoundVolume = 0.025f;

            consumeItem(SnItems.nedirium, 1);
            consumeLiquids(new LiquidStack(SnGas.gyner, 6f / 60f), new LiquidStack(SnGas.arhelinium, 4f / 60f));
            consumePower(4.6f);
        }};
        bioSynthesizer = new MultiCrafter("bio-synthesizer"){{
            size = 3;
            Recipe.with().produceTime(1.2f * Time.toSeconds)
                    .output(new ItemStack(SnItems.yantai, 1), null)
                    .consume(ItemStack.with(SnItems.nedirium, 2), LiquidStack.with(SnLiquids.sayang, 12/60));
            Recipe.with().produceTime(1.3f * Time.toSeconds)
                    .output(null, new LiquidStack(SnLiquids.nabunium, 12))
                    .consume(ItemStack.with(SnItems.entirium, 1), LiquidStack.with(SnLiquids.sayang, 12/60, SnLiquids.messira, 12/60, SnGas.arhelinium, 12/60));
            Recipe.with().produceTime(1.2f * Time.toSeconds)
                    .output(new ItemStack(SnItems.entirium, 4), null)
                    .consume(null, LiquidStack.with(SnLiquids.burheyna, 12/60, SnGas.hyneris, 12/60));
            Recipe.with().produceTime(1.2f * Time.toSeconds)
                    .output(new ItemStack(SnItems.naturite, 2), null)
                    .consume(ItemStack.with(SnItems.fors, 1), LiquidStack.with(SnLiquids.burheyna, 32/60));
        }};

        enojieSynthesizer = new GenericCrafter("enojie-synthesizer"){{
            requirements(Category.crafting, with(SnItems.erius, 200, SnItems.nobium, 150, SnItems.fors, 145, SnItems.zarium, 130));

            outputItem = new ItemStack(SnItems.entirium, 2);
            craftTime = 58f;
            size = 4;
            hasPower = true;
            hasLiquids = false;
            craftEffect = SnFx.enojieCraft;
            updateEffect = SnFx.enojieBurn;

            consumeItems(with(SnItems.nobium, 1, SnItems.planatrium, 2, SnItems.erius, 3));
            consumePower(10f);
        }};

        zeriniumZavod = new GenericCrafter("zerinium-zavod"){{
            requirements(Category.crafting, with(SnItems.erius, 180, SnItems.nobium, 160, SnItems.zarium, 155, SnItems.entirium, 40));
            size = 4;
            itemCapacity = 45;
            liquidCapacity = 40f;
            hasPower = true;
            hasLiquids = true;

            outputItem = new ItemStack(SnItems.zerinium, 1);
            craftTime = 37f;
            consumeItems(with(SnItems.zarium, 2, SnItems.planatrium, 3));
            consumeLiquid(SnGas.gyner, 4f / 60f);
            consumePower(8f);
        }};
        //endregion standard
        //region missile
        // I need multicrafters to do this fine
        //endregion missile
    }
}
