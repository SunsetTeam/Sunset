package sunset.world.blocks.production.crafting;

import arc.Core;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.GenericCrafter;

public class AccelGenericCrafter extends GenericCrafter {

    public AccelGenericCrafter(String name){
        super(name);
    }

    @Override
    public void setBars(){
        super.setBars();

        //set up liquid bars for liquid outputs
        if(outputLiquids != null && outputLiquids.length > 0){
            //no need for dynamic liquid bar
            removeBar("liquid");

            //then display output buffer
            for(var stack : outputLiquids){
                addLiquidBar(stack.liquid);
            }
        }

        addBar("sunset-acceleration", (AccelGenericCrafterBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.sunset-acceleration", Mathf.round(60 * entity.timeScale())),
                () -> Pal.bar,
                () -> entity.productionEfficiency
        ));
    }

    public class AccelGenericCrafterBuild extends GenericCrafterBuild{
        public float productionEfficiency = 0.0f;

        @Override
        public void updateTile(){
            if(efficiency > 0){

                progress += getProgressIncrease(craftTime);
                warmup = Mathf.approachDelta(warmup, warmupTarget(), warmupSpeed);

                //continuously output based on efficiency
                if(outputLiquids != null){
                    float inc = getProgressIncrease(1f);
                    for(var output : outputLiquids){
                        handleLiquid(this, output.liquid, Math.min(output.amount * inc, liquidCapacity - liquids.get(output.liquid)));
                    }
                }

                if(wasVisible && Mathf.chanceDelta(updateEffectChance)){
                    updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
                }
            }else{
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }

            //TODO may look bad, revert to edelta() if so
            totalProgress += warmup * Time.delta;

            productionEfficiency = Mathf.pow(warmup, 5f);

            if(progress >= 1f){
                craft();
            }

            dumpOutputs();
        }

        public float getProgress(){
            return progress * productionEfficiency;
        }
    }
}
