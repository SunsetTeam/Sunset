package sunset.world.blocks.units;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.io.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.meta.*;
import sunset.content.*;
import sunset.gen.*;

import static mindustry.Vars.*;

public class Airport extends StorageBlock{
    private final Item[] canDump = null;
    public float unitBuildTime = 720f;
    public UnitType unitType;
    public ItemStack[] unitRequirements;
    public float powerUse;
    public int maxUnitCount;


    public Airport(String name){
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
            tile.link = decompressLink(link, tile.tile);
            tile.takeItem = decompressItem(link);
        });
    }

    public static long compress(Tile tile, int link, Item item){
        return Pack.longInt(Point2.unpack(link).sub(tile.x, tile.y).pack(), item == null ? -1 : item.id);
    }

    public static Item decompressItem(long compressed){
        int id = Pack.rightInt(compressed);
        return id == -1 ? null : content.item(id);
    }

    public static int decompressLink(long compressed, Tile tile){
        Point2 point2 = Point2.unpack(Pack.leftInt(compressed));
        if(tile != null){
            point2.add(tile.x, tile.y);
        }
        return point2.pack();
    }

    @Override
    public void init(){

        consumes.add(new DynamicConsumePower(build -> {
            AirportBuild tile = build.as();
            return powerUse * (tile.shouldBuild ? tile.getBoost() : 0);
        }));
        consumes.items(unitRequirements);
        super.init();
    }

    @Override
    public void load(){
        super.load();
    }

    @Override
    public void setBars(){
        super.setBars();
        bars.add("progress", (AirportBuild e) ->
        new Bar("bar.progress", Pal.ammo, () -> e.construcionTime / unitBuildTime));

        bars.add("units", (AirportBuild e) ->
        new Bar(
        //TODO: string
        () -> Core.bundle.format("bar.unitcap",
        e.units.size,
        maxUnitCount
        ),
        () -> Pal.power,
        () -> (float)e.units.size / maxUnitCount
        ));
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.remove(Stat.powerUse);
        stats.add(Stat.powerUse, powerUse * 60f, StatUnit.powerSecond);
        stats.add(Stat.productionTime, unitBuildTime / 60f, StatUnit.seconds);
        stats.add(Stat.input, StatValues.items(unitRequirements));
        stats.add(Stat.maxUnits, maxUnitCount);
    }

    public class AirportBuild extends StorageBuild{
        public final Seq<DeliverUnit> units = new Seq<>();
        public int link = 0;
        public float construcionTime = 0f;
        public boolean shouldBuild;
        public Item takeItem;
        public boolean afterRead = false;
        private long boostEndTime = 0;
        private float boost = 0f;

        @Override
        public void displayConsumption(Table table){
            super.displayConsumption(table);
        }

        @Override
        public void display(Table table){
            super.display(table);
        }

        @Override
        public void updateTile(){
            if(afterRead){
                afterRead = false;
                for(Deliverc deliver : SnGroups.delivers){
                    if(deliver.base() != this) continue;
                    if(needUnits()){
                        units.add(deliver.as());
                    }else{
                        deliver.base(null);
                    }
                }
            }
            units.removeAll(d -> !d.added || !d.isValid());
            if(needUnits()){
                for(Deliverc deliver : SnGroups.delivers){
                    if(deliver.base() == null){
                        units.add(deliver.as());
                        deliver.set(this);
                    }
                    if(!needUnits()){
                        break;
                    }
                }
            }
            if(link == -1) return;
            Building linkBuild = world.build(link);
            if(!(linkBuild instanceof AirportBuild linked) || !linked.isValid()){
//                configure(-1);
                return;
            }
            shouldBuild = items.has(unitRequirements) && needUnits();
            if(shouldBuild){
                construcionTime += Time.delta * getBoost() * efficiency();
                if(construcionTime > unitBuildTime){
                    items.remove(unitRequirements);
                    construcionTime %= unitBuildTime;
                    Unit u = SnUnitTypes.courier.spawn(team, x, y); //TODO починить тип юнита
                    DeliverUnit deliverUnit = u.as();
                    deliverUnit.set(this);
//                    ((DeliverAI) u.controller()).setup(this);
                    units.add(deliverUnit);
                }
            }
            /*if (canDump==null) {
                canDump=content.items().select(i -> !Structs.contains(requirements, r -> r.item == i)).toArray(Item.class);
            }
            for (Item item : canDump) {
                dump(item);
            }*/
        }

        public float getBoost(){
            return Time.millis() <= boostEndTime ? Math.max(boost, 1) : 1;
        }

        @Override
        public void applyBoost(float intensity, float duration){
            boostEndTime = Time.millis() + (long)(duration * 1000f / 60f);
            boost = intensity;
        }

        @Override
        public void buildConfiguration(Table table){
            ItemSelection.buildTable(table, content.items(), () -> takeItem, item -> {
                configure(compress(tile, link, item));
            });
        }

        @Override
        public Long config(){
            return compress(tile, link, takeItem);
        }

        @Override
        public boolean onConfigureTileTapped(Building other){
            if(this == other || (other != null && link == other.pos())){
                configure(-1);
                return false;
            }else if(other instanceof AirportBuild){
                configure(other.pos());
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

            if(link != -1 && world.build(link) instanceof AirportBuild linked && linked.isValid()){
                Drawf.circles(linked.x, linked.y, (linked.block().size / 2f + 1) * tilesize + sin - 2f, Pal.place);
                Drawf.arrow(x, y, linked.x, linked.y, size * tilesize + sin, 4f + sin);
            }
        }

        @Override
        public byte version(){
            return 1;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            TypeIO.writeItem(write, takeItem);
            write.i(link);
            write.f(construcionTime);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            takeItem = content.item(read.s());
            link = read.i();
            construcionTime = read.f();
            if(revision == 0){
//                states.clear();
                int unitsCount = read.i();
                for(int i = 0; i < unitsCount; i++){
                    TypeIO.readVec2(read);
                    read.i();
                }
            }
            afterRead = true;
        }

        public boolean needUnits(){
            return units.size < maxUnitCount;
        }
    }
}
