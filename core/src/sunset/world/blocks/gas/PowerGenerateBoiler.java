package sunset.world.blocks.gas;

import arc.Core;
import arc.math.Mathf;
import arc.struct.EnumSet;
import arc.util.Strings;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.power.PowerGenerator;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class PowerGenerateBoiler extends Boiler{

    /** The amount of power produced per tick in case of an efficiency of 1.0, which represents 100%. */
    public float powerProduction;
    public Stat generationType = Stat.basePowerGeneration;

    public PowerGenerateBoiler(String name){
        super(name);
        sync = true;
        baseExplosiveness = 5f;
        flags = EnumSet.of(BlockFlag.generator);
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(generationType, powerProduction * 60.0f, StatUnit.powerSecond);
    }

    @Override
    public void setBars(){
        super.setBars();

        if(hasPower && outputsPower && !consumes.hasPower()){
            bars.add("power", (PowerGenerator.GeneratorBuild entity) -> new Bar(() ->
                    Core.bundle.format("bar.poweroutput",
                            Strings.fixed(entity.getPowerProduction() * 60 * entity.timeScale(), 1)),
                    () -> Pal.powerBar,
                    () -> entity.productionEfficiency));
        }
    }

    @Override
    public boolean outputsItems(){
        return false;
    }

    public class GeneratorBuild extends Building {
        public float generateTime;
        /**
         * The efficiency of the producer. An efficiency of 1.0 means 100%
         */
        public float productionEfficiency = 0.0f;

        @Override
        public float ambientVolume() {
            return Mathf.clamp(productionEfficiency);
        }

        @Override
        public float getPowerProduction() {
            return powerProduction * productionEfficiency;
        }

        @Override
        public byte version() {
            return 1;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(productionEfficiency);
            write.f(generateTime);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            productionEfficiency = read.f();
            if (revision >= 1) {
                generateTime = read.f();
            }
        }
    }
}
