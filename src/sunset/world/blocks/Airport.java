package sunset.world.blocks;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.struct.FloatSeq;
import arc.struct.IntSeq;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.ui.Bar;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.values.ItemListValue;
import sunset.utils.Utils;
import sunset.ai.DeliverAI;
import sunset.content.SnUnitTypes;
import sunset.world.consumers.AdjustableConsumePower;

import static mindustry.Vars.*;

public class Airport extends StorageBlock {
    public float unitBuildTime = 720f;
    public UnitType unitType;
    public ItemStack[] requirements;
    public float powerUse;
    public int maxUnitCount;

    public Airport(String name) {
        super(name);
        update = true;
        configurable = true;
        consumes.add(new AdjustableConsumePower(powerUse, b -> {
            AirportBuild ab = (AirportBuild)b;
            return ab.shouldBuild ? ab.getBoost() : 0f;
        }));
    }

    @Override
    public void setBars() {
        super.setBars();
        bars.add("progress", (AirportBuild e) ->
            new Bar("bar.progress", Pal.ammo, () -> e.construcionTime / unitBuildTime));

        bars.add("units", (AirportBuild e) ->
            new Bar(
                () -> Core.bundle.format("bar.unitcap",
                        "TODO: string",
                        e.units.size,
                        maxUnitCount
                    ),
                () -> Pal.power,
                () -> (float)e.units.size / maxUnitCount
            ));
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.remove(Stat.powerUse);
        stats.add(Stat.powerUse, powerUse * 60f, StatUnit.powerSecond);
        stats.add(Stat.productionTime, unitBuildTime / 60f, StatUnit.seconds);
        stats.add(Stat.input, new ItemListValue(requirements));
        stats.add(Stat.maxUnits, maxUnitCount);
    }

    public class AirportBuild extends StorageBuild {
        public int linkID = 0;
        public AirportBuild linked;
        public float construcionTime = 0f;
        public boolean readen = false;
        IntSeq units = new IntSeq();
        public boolean shouldBuild;
        public Item takeItem;

        private void validateLink(){
            AirportBuild prevLinked = linked;
            Building b = world.build(linkID);
            if(b instanceof AirportBuild) {
                linked = (AirportBuild)b;
                if(linked.dead) linked = null;
            } else linked = null;
            if(linked != prevLinked) Call.tileConfig(player, this, linked);
        }
        @Override
        public void updateTile() {
            if(readen) firstUpdateAfterRead();
            validateLink();
            for (int i = units.size-1; i > -1; i--) {
                Unit unit = Groups.unit.getByID(units.get(i));
                if (unit == null || unit.dead()) units.removeIndex(i);
            }
            shouldBuild = items.has(requirements) && units.size < maxUnitCount && linked != null;
            if (shouldBuild) {
                construcionTime += Time.delta * getBoost() * efficiency();
                if (construcionTime > unitBuildTime) {
                    items.remove(requirements);
                    construcionTime = 0;
                    Unit u = SnUnitTypes.courier.spawn(team, x, y);
                    ((DeliverAI)u.controller()).setup(this);
                    units.add(u.id);
                }
            }
        }

        private long boostEndTime = 0;
        private float boost = 0f;
        public float getBoost(){
            return Time.millis()<=boostEndTime ? Math.max(boost, 1) : 1;
        }
        @Override
        public void applyBoost(float intensity, float duration) {
            boostEndTime = Time.millis() + (long)(duration*1000f/60f);
            boost = intensity;
        }

        @Override
        public void buildConfiguration(Table table){
            ItemSelection.buildTable(table, content.items(), () -> takeItem, this::configure);
        }

        @Override
        public void configure(Object value) {
            takeItem = (Item)value;
            Call.tileConfig(player, this, value);
        }

        @Override
        public boolean onConfigureTileTapped(Building other) {
            if (this == other || linked == other) {
                linked = null;
                linkID = 0;
                Call.tileConfig(player, this, linked);
                return false;
            } else if (other instanceof AirportBuild) {
                linkID = other.pos();
                validateLink();
                return false;
            }
            return true;
        }

        @Override
        public void drawConfigure(){
            float sin = Mathf.absin(Time.time, 6f, 1f);

            Draw.color(Pal.accent);
            Lines.stroke(1f);
            Drawf.circles(x, y, (tile.block().size / 2f + 1) * tilesize + sin - 2f, Pal.accent);

            if(linked != null){
                Drawf.circles(linked.x, linked.y, (linked.block().size / 2f + 1) * tilesize + sin - 2f, Pal.place);
                Drawf.arrow(x, y, linked.x, linked.y, size * tilesize + sin, 4f + sin);
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.s(takeItem == null ? -1 : takeItem.id);
            write.i(linkID);
            write.f(construcionTime);
            write.i(units.size);
            for (int i = 0; i < units.size; i++) {
                Unit u = Groups.unit.getByID(units.get(i));
                write.f(u.x);
                write.f(u.y);
                write.i(((DeliverAI)u.controller()).state);
            }
        }

        IntSeq states = new IntSeq();
        FloatSeq xs = new FloatSeq();
        FloatSeq ys = new FloatSeq();
        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            takeItem = content.item(read.s());
            linkID = read.i();
            construcionTime = read.f();
            states.clear();
            xs.clear();
            ys.clear();
            int unitsCount = read.i();
            for (int i = 0; i < unitsCount; i++) {
                xs.add(read.f());
                ys.add(read.f());
                states.add(read.i());
            }
            readen = true;
        }
        public void firstUpdateAfterRead() {
            units.clear();
            for (int i = 0; i < states.size; i++) {
                Unit u = Utils.unitClosest(xs.get(i), ys.get(i), (_u) -> true);
                DeliverAI ai = (DeliverAI)u.controller();
                ai.setup(this);
                ai.state = states.get(i);
                units.add(u.id);
            }
            states.clear();
            xs.clear();
            ys.clear();
            readen = false;
        }
    }
}
