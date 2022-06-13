package sunset.content.blocks;

import gas.world.blocks.power.GasConsumeGenerator;
import gas.world.blocks.production.GasGenericCrafter;
import mindustry.content.*;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.power.*;
import sunset.content.*;
import sunset.world.blocks.power.*;

import static mindustry.Vars.state;
import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;

public class SnPower{
    public static Block

    //generatorss
    thermalEvaporator, oxidativeCombustionGenerator, chemicalReactor;


    public static void load(){
        thermalEvaporator = new ThermalGenerator("thermal-evaporator"){{
            requirements(Category.power, with(SnItems.fors, 60, SnItems.erius, 40));
            powerProduction = 2.7f;
            displayEfficiency = true;
            spinners = false;
            size = 2;
            floating = true;
            placeableLiquid = true;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
            attribute = SnAttribute.thermalBurheyna;
        }};

        oxidativeCombustionGenerator = new GasConsumeGenerator("oxidative-combustion-generator"){{
            requirements(Category.power, with(SnItems.fors, 120, SnItems.erius, 100, SnItems.naturite, 70));
            size = 2;
            powerProduction = 6.1f;

            hasGasses = true;
            hasItems = true;
            hasPower = true;
            hasLiquids = false;
            itemCapacity = 20;
            gasCapacity = 35f;
            itemDuration = 100f;
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.06f;

            consumeGas(SnGas.hyneris, 5f / 60f);
            consumeItem(SnItems.azalia_bud, 2);
        }};

        chemicalReactor = new GasConsumeGenerator("chemical-reactor"){{
            requirements(Category.power, with(SnItems.fors, 200, SnItems.erius, 190, SnItems.naturite, 140, SnItems.anzar, 110));
            size = 3;
            powerProduction = 13f;

            hasGasses = true;
            hasPower = true;
            hasLiquids = true;
            itemCapacity = 20;
            gasCapacity = 35f;
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.06f;

            consumeGas(SnGas.gyner, 6f / 60f);
            consumeLiquid(SnLiquids.sayang, 3f / 60f);
        }};
    }
}
