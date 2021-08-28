package sunset.world.blocks;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.abilities.Ability;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.ui.Bar;
import mindustry.world.blocks.storage.StorageBlock;
import sunset.ai.DeliverAI;
import sunset.content.SnUnitTypes;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class Airport extends StorageBlock {
    public float unitBuildTime = 720f;
    public UnitType unitType;
    public ItemStack[] requirements;
    public int maxUnitCount;

    public Airport(String name) {
        super(name);
        update = true;
        configurable = true;
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

    public class AirportBuild extends StorageBuild {
        public int linkID = 0;
        public AirportBuild linked;
        public float construcionTime = 0f;
        Seq<Integer> units = new Seq<>(false);

        private void validateLink(){
            Building b = world.build(linkID);
            if(b instanceof AirportBuild) {
                linked = (AirportBuild)b;
                if(linked.dead) linked = null;
            } else linked = null;
        }
        @Override
        public void updateTile() {
            validateLink();
            for (int i = units.size-1; i > -1; i--) {
                Unit unit = Groups.unit.getByID(units.get(i));
                if (unit == null || unit.dead()) units.remove(i);
            }
            if (items.has(requirements) && units.size < maxUnitCount && linked != null) {
                construcionTime += Time.delta;
                if (construcionTime > unitBuildTime) {
                    items.remove(requirements);
                    construcionTime = 0;
                    Unit u = SnUnitTypes.deliveryUnit.spawn(team, x, y);
                    ((DeliverAI)u.controller()).setup(this);
                    units.add(u.id);
                }
            }
        }

        @Override
        public boolean onConfigureTileTapped(Building other) {
            if (this == other || linked == other) {
                linked = null;
                linkID = 0;
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
            write.i(linkID);
            write.f(construcionTime);
            write.i(units.size);
            for (int i = 0; i < units.size; i++) {
                write.i(units.get(i));
            }
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            linkID = read.i();
            validateLink();
            construcionTime = read.f();
            int unitsCount = read.i();
            for (int i = 0; i < unitsCount; i++) {
                int id = read.i();
                units.add(id);
            }
        }
    }
}
