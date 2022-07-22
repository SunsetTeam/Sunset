package sunset.world.blocks.power;

import acontent.world.meta.AStats;
import arc.Core;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.core.UI;
import mindustry.entities.Effect;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import sunset.content.SnFx;
import sunset.world.meta.SnStat;
import sunset.world.meta.SnStatUnit;

import static mindustry.Vars.tilesize;

public class LightningPowerNode extends PowerNode {
    public final int debugUpdateTimer = timers++;
    final float debugUpdateTime = 40;
    // max lightning length
    public float lightningRange;

    // minimal energy delta per tile required for lightning
    public float thresholdPerTile;
    public Effect lightningFx = Fx.lightning;
    public Color lightningColor = Pal.surge;
    public Sound lightningSound = Sounds.spark;

    AStats aStats = new AStats();

    public LightningPowerNode(String name, int maxNodes) {
        super(name);
        this.maxNodes = maxNodes;
        if(maxNodes > 0) {
            consumesPower = true;
            outputsPower = true;
        }
        update = true;
        stats = aStats.copy(stats);
        if(maxNodes == 0) configurable = false;
    }

    @Override
    public void init() {
        super.init();
        if(maxNodes == 0) laserRange = lightningRange / tilesize;
    }

    @Override
    public void setStats() {
        super.setStats();
        if(maxNodes == 0) {
            aStats.remove(Stat.powerConnections);
            aStats.remove(Stat.powerRange);
        }
        aStats.add(Stat.powerCapacity, consPower.capacity);
        aStats.add(Stat.range, lightningRange / Vars.tilesize, StatUnit.blocks);
        aStats.add(SnStat.lightningDiff, thresholdPerTile * Vars.tilesize, SnStatUnit.powerBlock);
    }

    @Override
    public void setBars() {
        super.setBars();
        if(maxNodes == 0) {
            removeBar("batteries");
            removeBar("connections");
            addBar("power", entity -> new Bar(
                    () -> Core.bundle.format("bar.poweramount", UI.formatAmount((int)(entity.power.status * consPower.capacity))),
                    () -> Pal.powerBar,
                    () -> entity.power.status)
            );
        }
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        // draw connection range only if this block can be connected
        if(maxNodes > 0) super.drawPlace(x, y, rotation, valid);
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, lightningRange, Pal.placing);
    }

    public class LightningPowerNodeBuild extends PowerNodeBuild {
        final Seq<LightningPowerNodeBuild> nodes = new Seq<>();
        final float[] energyToSet = new float[1];


        @Override
        public void update() {
            super.update();
            if(!timer.get(debugUpdateTimer,debugUpdateTime)) return;

            float thisCap = powerCapacity(this), thisStored = powerStored(this);
            energyToSet[0] = thisStored; // include "this" node

            StringBuilder debugSb = new StringBuilder();
            debugSb.append(this.id + ": pos=" + x + "|" + y +
                                "; power=" + thisStored + "/" + thisCap +
                                System.lineSeparator());
            Vars.indexer.eachBlock(this, lightningRange, other -> {
                if (!other.isValid() || !other.isAdded() || other == this ||
                    !(other instanceof LightningPowerNodeBuild)) return false;

                float powerDelta = thisStored - powerStored(other);
                float requiredDelta = thresholdPerTile * Mathf.len(other.x - x, other.y - y);
                debugSb.append("other at=" + other.x + "|" + other.y +
                                "; power=" + powerStored(other) + "/" + powerCapacity(other) +
                                "; diff=" + powerDelta + "/" + requiredDelta +
                                System.lineSeparator());
                return powerDelta > requiredDelta;
            }, b -> {
                nodes.add((LightningPowerNodeBuild)b);
                energyToSet[0] += powerStored(b);
            });

            if(!nodes.isEmpty()) {
                energyToSet[0] /= nodes.size + 1; // include "this" node
                debugSb.append("total nodes=" + (nodes.size+1) +
                        "; target energy=" + energyToSet[0] +
                        System.lineSeparator());
                for(LightningPowerNodeBuild node : nodes) {
                    if(!node.isValid()) continue;
                    float capacity = powerCapacity(node), stored = powerStored(node);
                    float toSet = Math.min(capacity, energyToSet[0]); // don't "overcharge" other node
                    float toGive = toSet - stored;
                    debugSb.append("other at=" + node.x + "|" + node.y +
                            "; power=" + stored + "/" + capacity +
                            "; toSet=" + toSet +
                            "; toGive=" + toGive);
                    if(toSet <= stored) {
                        debugSb.append(System.lineSeparator());
                        return; // don't decrease amount of energy in other node
                    }
                    debugSb.append(" - give!" + System.lineSeparator());

                    node.power.status += toGive / capacity;
                    power.status -= toGive / thisCap;

                    SnFx.lightning(x, y, node.x, node.y, lightningColor, 3, 12f, lightningFx);
                    lightningSound.at(this);
                    lightningSound.at(node);
                }
                nodes.clear();
            }
            Log.warn(debugSb.toString());
        }

        @Override
        public void drawSelect() {
            // draw connection range only if this block can be connected
            if(maxNodes > 0) super.drawSelect();
            Drawf.dashCircle(x, y, lightningRange, team.color);
        }
    }

    private static float powerStored(Building b) {
        if(b.power == null || b.block.consPower == null) return 0;
        return b.block.consPower.capacity * b.power.status;
    }

    private static float powerCapacity(Building b) {
        if(b.block.consPower == null) return 0;
        return b.block.consPower.capacity;
    }
}
