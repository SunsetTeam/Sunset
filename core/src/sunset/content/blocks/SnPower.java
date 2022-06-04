package sunset.content.blocks;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.ctype.ContentList;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.power.DecayGenerator;
import mindustry.world.blocks.power.NuclearReactor;
import sunset.content.SnItems;
import sunset.world.blocks.power.LiquidGenerator;
import sunset.world.blocks.power.ThermalGeneratorExt;
import sunset.world.blocks.power.WindTurbine;

import static mindustry.type.ItemStack.with;

public class SnPower implements ContentList{
    public static Block
    //generators
    windTurbine, oilGenerator, advrtgGenerator, advThermalGenerator,

    //reactors
    differentialReactor, planatriumReactor;

    @Override
    public void load(){
        windTurbine = new WindTurbine("wind-turbine"){{
            requirements(Category.power, with(Items.copper, 30, Items.silicon, 20));
            size = 2;
            powerProduction = 1f;
        }};


        //region generator
        //endregion generators
        //region reactors
        //endregion reactors
    }
}
