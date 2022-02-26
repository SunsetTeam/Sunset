package sunset.world.blocks.laser;

import arc.math.Mathf;
import arc.struct.EnumSet;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.StatUnit;
import sunset.world.meta.SnStat;

/** Laser generator. Generates laser if consValid(). */
public class LaserGenerator extends LaserNode{
    public float warmupSpeed = 0.019f;
    public float burnTime = 80f;
    public LaserGenerator(String name) {
        super(name);
        laserGeneration = 5f;
        maxCharge = 5f;
        enableRange = true;
        consumesLaser = false;
        outputsLaser = true;
        ambientSound = Sounds.machine;
        ambientSoundVolume = 0.03f;
        flags = EnumSet.of(BlockFlag.generator);
    }
    @Override
    public void setBars(){
        super.setBars();
        bars.remove("laser-energy");
        bars.add("laser-energy", (LaserBlockBuild entity) -> new Bar(()->{
            return "Energy: " + entity.laserModule.out;
        },
        ()->{
            return Pal.accent;
        },
        ()->{
            return entity.laserModule.out / ((LaserGenerator)entity.block).maxCharge;
        }));
    }
    public class LaserGeneratorBuild extends LaserNodeBuild{
        float progress, warmup, totalProgress;

        @Override
        public boolean shouldConsume(){
            return true;
        }
        @Override
        public float getLaserProduction(){
            return consValid() ? laserGeneration : 0f;
        }
        @Override
        public void updateTile(){
            super.updateTile();

            if(consValid()){
                progress += getProgressIncrease(burnTime);
                totalProgress += delta();
                warmup = Mathf.approachDelta(warmup, 1f, warmupSpeed);
            }
            else{
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }
            if (progress >= 1f)
                burn();
        }

        public void burn(){
            consume();
            progress %= 1f;
        }
    }
}
