package sunset.world.blocks.units;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.math.geom.Vec2;
import arc.scene.ui.layout.Table;
import arc.struct.IntSeq;
import arc.struct.Seq;
import arc.util.Pack;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.Units;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.io.TypeIO;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.ui.Bar;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;
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
        this.<Item, AirportBuild>config(Item.class, (tile, item) -> {
            tile.takeItem = item;
        });
        this.<Integer, AirportBuild>config(Integer.class, (tile, link) -> {
            tile.link = link;
        });
        this.<Long, AirportBuild>config(Long.class, (tile, link) -> {
            tile.link = Point2.unpack(Pack.leftInt(link)).add(tile.tile.x,tile.tile.y).pack();
            int id = Pack.rightInt(link);
            tile.takeItem = id==-1?null:content.item(id);
        });
    }

    @Override
    public void init() {
        super.init();

        consumes.add(new AdjustableConsumePower(powerUse, b -> {
            AirportBuild ab = (AirportBuild) b;
            return ab.shouldBuild ? ab.getBoost() : 0f;
        }));
    }

    @Override
    public void load() {
        super.load();
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
                        () -> (float) e.units.size / maxUnitCount
                ));
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.remove(Stat.powerUse);
        stats.add(Stat.powerUse, powerUse * 60f, StatUnit.powerSecond);
        stats.add(Stat.productionTime, unitBuildTime / 60f, StatUnit.seconds);
        stats.add(Stat.input, StatValues.items(requirements));
        stats.add(Stat.maxUnits, maxUnitCount);
    }

    public class AirportBuild extends StorageBuild {
        final IntSeq units = new IntSeq();
        final Seq<Vec2> positions = new Seq<>();
        public int link = 0;
        public float construcionTime = 0f;
        public boolean afterRead = false;
        public boolean shouldBuild;
        public Item takeItem;
        IntSeq states = new IntSeq();
        private long boostEndTime = 0;
        private float boost = 0f;

        @Override
        public void updateTile() {
            if (afterRead) firstUpdateAfterRead();
            if (link == -1) return;
            Building linkBuild = world.build(link);
            if (!(linkBuild instanceof AirportBuild linked) || !linked.isValid()) {
//                configure(-1);
                return;
            }
            for (int i = 0; i < units.size; i++) {
                Unit unit = Groups.unit.getByID(units.get(i));
                if (unit == null || unit.dead() || !unit.isValid()) {
                    units.removeIndex(i);
                    i--;
                }
            }
            shouldBuild = items.has(requirements) && units.size < maxUnitCount;
            if (shouldBuild) {
                construcionTime += Time.delta * getBoost() * efficiency();
                if (construcionTime > unitBuildTime) {
                    items.remove(requirements);
                    construcionTime = 0;
                    Unit u = SnUnitTypes.courier.spawn(team, x, y); //TODO починить тип юнита
                    ((DeliverAI) u.controller()).setup(this);
                    units.add(u.id);
                }
            }
        }

        public float getBoost() {
            return Time.millis() <= boostEndTime ? Math.max(boost, 1) : 1;
        }

        @Override
        public void applyBoost(float intensity, float duration) {
            boostEndTime = Time.millis() + (long) (duration * 1000f / 60f);
            boost = intensity;
        }

        @Override
        public void buildConfiguration(Table table) {
            ItemSelection.buildTable(table, content.items(), () -> takeItem, this::configure);
        }

        @Override
        public Long config() {
            return Pack.longInt(Point2.unpack(link).sub(tile.x,tile.y).pack(),takeItem==null?-1:takeItem.id);
        }

        @Override
        public boolean onConfigureTileTapped(Building other) {
            if (this == other || (other != null && link == other.pos())) {
                configure(-1);
                return false;
            } else if (other instanceof AirportBuild) {
                configure(other.pos());
                return false;
            }
            return true;
        }

        @Override
        public void drawConfigure() {
            float sin = Mathf.absin(Time.time, 6f, 1f);

            Draw.color(Pal.accent);
            Lines.stroke(1f);
            Drawf.circles(x, y, (tile.block().size / 2f + 1) * tilesize + sin - 2f, Pal.accent);

            if (link != -1 && world.build(link) instanceof AirportBuild linked && linked.isValid()) {
                Drawf.circles(linked.x, linked.y, (linked.block().size / 2f + 1) * tilesize + sin - 2f, Pal.place);
                Drawf.arrow(x, y, linked.x, linked.y, size * tilesize + sin, 4f + sin);
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.s(takeItem == null ? -1 : takeItem.id);
            write.i(link);
            write.f(construcionTime);
            write.i(units.size);
            for (int i = 0; i < units.size; i++) {
                Unit u = Groups.unit.getByID(units.get(i));
                write.f(u.x);
                write.f(u.y);
                write.i(((DeliverAI) u.controller()).state);
            }
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            takeItem = content.item(read.s());
            link = read.i();
            construcionTime = read.f();
            states.clear();
            int unitsCount = read.i();
            for (int i = 0; i < unitsCount; i++) {
                positions.add(TypeIO.readVec2(read));
                states.add(read.i());
            }
            afterRead = true;
        }

        public void firstUpdateAfterRead() {
            units.clear();
            for (int i = 0; i < states.size; i++) {
                Vec2 position = positions.get(i);
                Unit u = Units.closest(null, position.x, position.y, (_u) -> true);
                DeliverAI ai = (DeliverAI) u.controller();
                ai.setup(this);
                ai.state = states.get(i);
                units.add(u.id);
            }
            states.clear();
            positions.clear();
            afterRead = false;
        }
    }
}
