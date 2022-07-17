package sunset.world.blocks.power;

import acontent.world.meta.AStats;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.EnumSet;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.world.blocks.power.Battery;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import sunset.content.SnFx;
import sunset.world.meta.SnStat;
import sunset.world.meta.SnStatUnit;

import static mindustry.Vars.tilesize;

public class LightningPowerNode extends Battery {
    // max lightning length
    public float range;

    // minimal energy delta per tile required for lightning
    public float thresholdPerTile;
    public Effect lightningFx = Fx.lightning;
    public Color lightningColor = Pal.surge;

    AStats aStats = new AStats();

    public LightningPowerNode(String name) {
        super(name);
        flags = EnumSet.of(); // remove "battery" flag
        update = true;
        stats = aStats.copy(stats);
    }

    @Override
    public void setStats() {
        super.setStats();
        aStats.add(Stat.range, range / Vars.tilesize, StatUnit.blocks);
        aStats.add(SnStat.lightningDiff, thresholdPerTile * Vars.tilesize, SnStatUnit.powerBlock);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.placing);
    }

    public class LightningPowerNodeBuild extends BatteryBuild {
        final Seq<LightningPowerNodeBuild> nodes = new Seq<>();
        final float[] energyToSet = new float[1];

        @Override
        public void draw() { Draw.rect(region, x, y); }

        @Override
        public void update() {
            super.update();
            float thisCap = powerCapacity(this), thisStored = powerStored(this);

            energyToSet[0] = thisStored; // include "this" node

            Vars.indexer.eachBlock(this, range, other -> {
                if(other == this || !(other instanceof LightningPowerNodeBuild)) return false;

                float powerDelta = thisStored - powerStored(other);
                float requiredDelta = thresholdPerTile * Mathf.len(other.x - x, other.y - y);
                return powerDelta > requiredDelta;
            }, b -> {
                nodes.add((LightningPowerNodeBuild)b);
                energyToSet[0] += powerStored(b);
            });

            if(!nodes.isEmpty()) {
                energyToSet[0] /= nodes.size + 1; // include "this" node
                nodes.forEach(node -> {
                    float capacity = powerCapacity(node), stored = powerStored(node);
                    float toSet = Math.min(capacity, energyToSet[0]); // don't "overcharge" other node
                    if(toSet <= stored) return; // don't decrease amount of energy in other node
                    float toGive = toSet - stored;

                    node.power.status += toGive / capacity;
                    power.status -= toGive / thisCap;

                    SnFx.lightning(x, y, node.x, node.y, lightningColor, 3, 12f, lightningFx);
                });
                nodes.clear();
            }
        }

        @Override
        public void drawSelect(){ Drawf.dashCircle(x, y, range, team.color); }
    }

    private static float powerStored(Building b) {
        if(!(b instanceof BatteryBuild)) return 0;
        return b.block.consPower.capacity * b.power.status;
    }

    private static float powerCapacity(Building b) {
        if(!(b instanceof BatteryBuild)) return 0;
        return b.block.consPower.capacity;
    }
}
