package sunset.world.blocks.laser;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Nullable;
import arc.util.Time;
;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;

/** Class for all crafting blocks that use lasers.
 * Note: multikiln will have another class. */
public class LaserCrafter extends LaserBlock{
    public @Nullable ItemStack[] outputItems;
    public @Nullable LiquidStack[] outputLiquids;

    public float craftTime = 60f;
    public float warmupSpeed = 0.018f;

    public Effect updateEffect = Fx.none;
    public Effect craftEffect = Fx.none;
    public float updateEffectChance = 0.04f;

    public LaserCrafter(String name) {
        super(name);
        solid = true;
        update = true;
        hasItems = true;
        sync = true;
    }

    @Override
    public void setStats(){
        stats.timePeriod = craftTime;
        super.setStats();
        stats.add(Stat.productionTime, craftTime / 60f, StatUnit.seconds);
        if (outputItems != null){
            stats.add(Stat.output, StatValues.items(outputItems));
        }
        if(outputLiquids != null){
            //nothing here right now
            stats.add(Stat.output, StatValues.liquids(1f, outputLiquids));
        }
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("consumptionBar", (LaserBlockBuild entity) ->
                new Bar(()-> entity.block().heats ? Core.bundle.format("bar.laser-input-heat", entity.laser.rawInput, entity.getLaserConsumption(), entity.block().heatLaserLimit) : Core.bundle.format("bar.laser-input", entity.laser.rawInput, entity.getLaserConsumption()),
                        ()-> {
                            if(entity.laser.rawInput < entity.getLaserConsumption())
                                return Color.yellow;
                            else if(entity.laser.rawInput == entity.getLaserConsumption())
                                return Color.green;
                            else {
                                if(heats && entity.laser.rawInput > entity.block().heatLaserLimit)
                                    return Color.red;
                                else
                                    return Color.orange;
                            }
                        },
                        ()-> entity.laser.rawInput / entity.getLaserConsumption()));
    }

    public class LaserCrafterBuild extends LaserBlockBuild{
        public float progress = 0f;
        public float totalProgress = 0f;
        public float warmup = 0f;

        @Override
        public boolean shouldConsume(){
            if(outputItems != null){
                for (ItemStack output : outputItems){
                    if(items.get(output.item) + output.amount > itemCapacity)
                        return false;
                }
            }
            if (outputLiquids != null){
                for (LiquidStack output : outputLiquids){
                    if(liquids.get(output.liquid) + output.amount > liquidCapacity)
                        return false;
                }
            }
            return enabled;
        }

        @Override
        public void updateTile(){
            super.updateTile();
            //Log.info("warmup: @\nwarmupSpeed: @\ndelta: @", warmup, warmupSpeed, Time.delta);
            if(efficiency() > 0 && laser.out >= 0){
                progress += getProgressIncrease(craftTime);
                warmup = Mathf.approachDelta(warmup, 1f, warmupSpeed);

                if(outputLiquids != null){
                    float inc = getProgressIncrease(1f);
                    for (LiquidStack output : outputLiquids){
                        handleLiquid(this, output.liquid, Math.min(output.amount * inc, liquidCapacity - liquids.get(output.liquid)));
                    }
                }

                if(wasVisible && Mathf.chanceDelta(updateEffectChance)){
                    updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
                }
            }
            else{
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }

            totalProgress += warmup * edelta();

            if(progress >= 1f)
                craft();

            dumpOutputs();
        }

        public void craft(){
            consume();
            if (outputItems != null) {
                for (ItemStack output : outputItems){
                    for(int i = 0; i < output.amount; i++)
                        offload(output.item);
                }
            }

            if(wasVisible)
                craftEffect.at(x, y);

            progress %= 1f;
        }

        public void dumpOutputs(){
            if (outputItems != null && timer(timerDump, dumpTime / timeScale)){
                for (ItemStack output : outputItems){
                    dump(output.item);
                }
            }

            if(outputLiquids != null){
                for (LiquidStack output : outputLiquids){
                    dumpLiquid(output.liquid);
                }
            }
        }

        @Override
        public float progress(){
            return Mathf.clamp(progress);
        }

        @Override
        public float totalProgress(){
            return totalProgress;
        }

        @Override
        public boolean shouldAmbientSound(){
            return efficiency > 0;
        }

        @Override
        public int getMaximumAccepted(Item item){
            return itemCapacity;
        }

        @Override
        public float getLaserConsumption(){
            return laserConsumption;
        }

        @Override
        public LaserCrafter block(){
            return (LaserCrafter) this.block;
        }

        @Override
        public float warmup(){
            return warmup;
        }
    }
}
