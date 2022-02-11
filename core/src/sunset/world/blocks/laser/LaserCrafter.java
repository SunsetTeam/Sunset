package sunset.world.blocks.laser;

import arc.math.Mathf;
import arc.struct.EnumSet;
import arc.util.Nullable;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Sounds;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.meta.BlockFlag;

//this class is slightly modified GenericCrafter
public class LaserCrafter extends LaserBlock{
    public @Nullable ItemStack outputItem;
    public @Nullable ItemStack[] outputItems;
    public @Nullable LiquidStack outputLiquid;

    public float craftTime = 80f;
    public Effect craftEffect = Fx.none;
    public Effect updateEffect = Fx.none;
    public float updateEffectChance = 0.04f;
    public float warmupSpeed = 0.019f;

    public float laserConsumption = 0f;

    public LaserCrafter(String name) {
        super(name);
        update = true;
        solid = true;
        hasItems = true;
        ambientSound = Sounds.machine;
        sync = true;
        ambientSoundVolume = 0.03f;
        flags = EnumSet.of(BlockFlag.factory);
        consumesLaser = true;
        outputsLaser = false;
    }

    @Override
    public void init(){
        outputsLiquid = outputLiquid != null;
        if(outputItems == null && outputItem != null){
            outputItems = new ItemStack[]{outputItem};
        }
        super.init();
    }

    @Override
    public boolean outputsItems(){
        return outputItems != null;
    }

    public class LaserCrafterBuild extends LaserBlockBuild{
        public float progress;
        public float totalProgress;
        public float warmup;

        @Override
        public boolean shouldConsume(){
            if(outputItems != null){
                for (ItemStack output : outputItems){
                    if(items.get(output.item) + output.amount > itemCapacity)
                        return false;
                }
            }
            return enabled;
        }

        @Override
        public boolean consValid(){
            //if consValid and enough of laser energy
            return super.consValid() && (consumesLaser ? laserModule.in >= 0 : true);
        }

        @Override
        public void updateTile(){
            super.updateTile();
            if(consValid()){
                progress += getProgressIncrease(craftTime);
                totalProgress += delta();
                warmup = Mathf.approachDelta(warmup, 1f, warmupSpeed);

                if(Mathf.chanceDelta(updateEffectChance)){
                    updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
                }
            }
            else{
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }

            if(progress >= 1f)
                craft();

            dumpOutputs();
        }

        public void craft(){
            consume();

            if(outputItems != null){
                for (ItemStack output : outputItems){
                    for (int i = 0; i < output.amount; i++){
                        offload(output.item);
                    }
                }
            }

            if(outputLiquid != null){
                handleLiquid(this, outputLiquid.liquid, outputLiquid.amount);
            }

            craftEffect.at(x, y);
            progress %= 1f;
        }

        public void dumpOutputs(){
            if(outputItems != null && timer(timerDump, dumpTime / timeScale)){
                for (ItemStack output : outputItems){
                    dump(output.item);
                }
            }

            if(outputLiquid != null){
                dumpLiquid(outputLiquid.liquid);
            }
        }

        @Override
        public boolean shouldAmbientSound(){
            return consValid();
        }

        @Override
        public float getLaserConsumption(){
            return consValid() ? laserConsumption : 0f;
        }
    }
}
