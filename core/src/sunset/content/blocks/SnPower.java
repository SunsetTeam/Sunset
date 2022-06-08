package sunset.content.blocks;

import gas.world.blocks.power.GasConsumeGenerator;
import gas.world.blocks.production.GasGenericCrafter;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.power.*;
import sunset.content.*;
import sunset.world.blocks.power.*;

import static mindustry.type.ItemStack.with;

public class SnPower{
    public static Block

    //generatorss
    thermalEvaporator, oxidativeCombustionGenerator;


    public static void load(){
        thermalEvaporator = new ThermalGenerator("thermal-evaporator"){{
            requirements(Category.power, with(SnItems.fors, 60, SnItems.erius, 40));
            powerProduction = 2.5f;
            displayEfficiency = false;
            spinners = false;
            size = 2;
            floating = true;
            placeableLiquid = true;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
            attribute = SnAttribute.thermalBurheyna;
        }};

        oxidativeCombustionGenerator = new GasConsumeGenerator("oxidative-combustion-generator"){{
            requirements(Category.power, with(SnItems.fors, 120, SnItems.erius, 100, SnItems.anzar, 70));
            size = 2;
            powerProduction = 5.4f;

            hasGasses = true;
            hasItems = true;
            hasPower = true;
            hasLiquids = false;
            itemCapacity = 20;
            gasCapacity = 35f;
            itemDuration = 140f;
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.06f;

            consumeGas(SnGas.hyneris, 5f / 60f);
            consumeItem(SnItems.azalia_bud, 2);
        }};
    }
}
