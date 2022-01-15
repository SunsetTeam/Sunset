package sunset.world.blocks.defense;

import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.logic.Ranged;
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
        public Unit target;
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
                float realRange = range + phaseHeat * phaseRangeBoost;
                charge = 0f;

                target = Units.closest(team, x, y, realRange, Unit::damaged);
                if (target.health() >= target.maxHealth()) {
                    target = null;
                }
                target.heal(repairHealth + phaseHeat * phaseBoost / 100f * efficiency());
                Fx.heal.at(target.x, target.y, target.hitSize, baseColor);

                indexer.eachBlock(this, realRange, Building::damaged, other -> {
                    other.heal(other.maxHealth() * (healPercent + phaseHeat * phaseBoost) / 100f * efficiency());
                    Fx.healBlockFull.at(other.x, other.y, other.block.size, baseColor);
                });
            }
        }
    }
}
