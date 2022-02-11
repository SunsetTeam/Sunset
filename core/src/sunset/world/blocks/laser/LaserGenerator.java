package sunset.world.blocks.laser;

import arc.math.Mathf;
import arc.struct.EnumSet;
import mindustry.gen.Sounds;
import mindustry.world.meta.BlockFlag;

public class LaserGenerator extends LaserNode{
    public float laserGeneration = 5f;
    public float warmupSpeed = 0.019f;
    public float burnTime = 80f;
    public LaserGenerator(String name) {
        super(name);
        enableRange = true;
        consumesLaser = false;
        outputsLaser = true;
        ambientSound = Sounds.machine;
        ambientSoundVolume = 0.03f;
        flags = EnumSet.of(BlockFlag.generator);
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
