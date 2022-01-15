package sunset.world.blocks.defense;

import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.*;
import arc.util.io.*;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.world.blocks.defense.MendProjector;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.indexer;
import static mindustry.Vars.tilesize;

public class RepairStation extends MendProjector {
    public final int timerUse = timers++;
    public float reload = 250f;
    public float range = 60f;
    public float healPercent = 12f;
    public float repairHealth = 50f;
    public float phaseBoost = 12f;
    public float phaseRangeBoost = 50f;
    public float useTime = 400f;

    public RepairStation(String name) {
        super(name);
    }

    @Override
    public void setStats(){
        stats.timePeriod = useTime;
        super.setStats();

        stats.add(Stat.repairTime, (int)(100f / healPercent * reload / 60f), StatUnit.seconds);
        stats.add(Stat.repairTime, (repairHealth * reload / 60f), StatUnit.seconds);
        stats.add(Stat.range, range / tilesize, StatUnit.blocks);

        stats.add(Stat.boostEffect, phaseRangeBoost / tilesize, StatUnit.blocks);
        stats.add(Stat.boostEffect, (phaseBoost + healPercent) / healPercent, StatUnit.timesSpeed);
        stats.add(Stat.boostEffect, (phaseBoost + repairHealth) / repairHealth, StatUnit.timesSpeed);
    }


    public class RepairStationBuild extends Building implements Ranged {
        float heat;
        float charge = Mathf.random(reload);
        float phaseHeat;
        float smoothEfficiency;

        @Override
        public float range(){
            return range;
        }

        @Override
        public void updateTile() {
            smoothEfficiency = Mathf.lerpDelta(smoothEfficiency, efficiency(), 0.08f);
            heat = Mathf.lerpDelta(heat, consValid() || cheating() ? 1f : 0f, 0.08f);
            charge += heat * delta();

            phaseHeat = Mathf.lerpDelta(phaseHeat, Mathf.num(cons.optionalValid()), 0.1f);

            if(cons.optionalValid() && timer(timerUse, useTime) && efficiency() > 0){
                consume();
            }

            if(charge >= reload){
                charge %= reload;

                runHealing();
            }

        }

        private void runHealing(){
            float realRange = range + phaseHeat * phaseRangeBoost;

            Units.nearby(team,x,y,realRange,u->{
                u.heal(repairHealth + phaseHeat * phaseBoost / 100f * efficiency());
                Fx.heal.at(u.x, u.y, u.hitSize, baseColor);
            });


            indexer.eachBlock(this, realRange, Building::damaged, other -> {
                other.heal(other.maxHealth() * (healPercent + phaseHeat * phaseBoost) / 100f * efficiency());
                Fx.healBlockFull.at(other.x, other.y, other.block.size, baseColor);
            });
        }
        @Override
        public double sense(LAccess sensor){
            if(sensor == LAccess.progress) return Mathf.clamp(charge / reload);
            return super.sense(sensor);
        }

        @Override
        public void drawSelect(){
            float realRange = range + phaseHeat * phaseRangeBoost;

            indexer.eachBlock(this, realRange, other -> true, other -> Drawf.selected(other, Tmp.c1.set(baseColor).a(Mathf.absin(4f, 1f))));

            Drawf.dashCircle(x, y, realRange, baseColor);
        }

        @Override
        public void draw(){
            super.draw();

            float f = 1f - (Time.time / 100f) % 1f;

            Draw.color(baseColor, phaseColor, phaseHeat);
            Draw.alpha(heat * Mathf.absin(Time.time, 50f / Mathf.PI2, 1f) * 0.5f);
            Draw.rect(topRegion, x, y);
            Draw.alpha(1f);
            Lines.stroke((2f * f + 0.2f) * heat);
            Lines.square(x, y, Math.min(1f + (1f - f) * size * tilesize / 2f, size * tilesize/2f));

            Draw.reset();
        }

        @Override
        public void drawLight(){
            Drawf.light(team, x, y, lightRadius * smoothEfficiency, baseColor, 0.7f * smoothEfficiency);
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(heat);
            write.f(phaseHeat);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            heat = read.f();
            phaseHeat = read.f();
        }
    }
}
