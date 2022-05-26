package sunset.world.blocks.production.crafting;

import arc.Core;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Time;
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
                () -> Core.bundle.format("bar.sunset-acceleration", Mathf.round(entity.getSpeed() * 100f)),
                () -> Pal.bar,
                entity::getSpeed
        ));
    }

    public class AccelGenericCrafterBuild extends GenericCrafterBuild{
        public Interp interp;

        public void updateTile(){
            if(efficiency > 0){

                float s = getSpeed();

                progress += getProgressIncrease(craftTime) * s;
                warmup = Mathf.approachDelta(warmup, warmupTarget(), warmupSpeed) * s;

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

            if(progress >= 1f){
                craft();
            }

            dumpOutputs();
        }

        public float getSpeed(){
            return interp.apply(totalProgress);
        }
    }
}