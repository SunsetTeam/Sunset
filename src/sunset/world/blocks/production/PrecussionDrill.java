package sunset.world.blocks.production;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Strings;
import arc.util.Time;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.ItemSeq;
import mindustry.ui.Bar;
import mindustry.ui.Cicon;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.values.BlockFilterValue;
import sunset.world.consumers.AdjustableConsumePower;
import sunset.world.meta.values.DrillItemsValue;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class PrecussionDrill extends Block {
    /** Чем выше, тем быстрее бурит. Сильнее воздейсвует на мягкие руды, чем на твёрдые. */
    public float hardnessDrillMultiplier = 6f;
    /** Чем выше, тем быстрее бурит. Одинаково воздейсвует на все руды. */
    public float itemCountMultiplier = 1f;
    /** Во сколько раз ускоряется водой. */
    public float liquidBoostIntensity = 1.6f;
    /** Длительность добычи одной партии ресурсов. */
    public float drillTime = 540f;
    /** Список ресурсов, используемых при добыче. */
    public final Seq<DrillItem> drillItems = new Seq<>();
    /** Количество используемого ресурса за одну партию. */
    public int drillItemCount = 2;
    /** Вместимость используемого ресурса за одну партию. */
    public int drillItemCapacity = 10;
    public int tier = 5;
    public float powerUse = 1f;
    public TextureRegion rimRegion;
    public TextureRegion rotatorRegion;
    public TextureRegion topRegion;
    public PrecussionDrill(String name) {
        super(name);
        update = true;
        solid = true;
        group = BlockGroup.drills;
        hasLiquids = true;
        liquidCapacity = 5f;
        hasItems = true;
        ambientSound = Sounds.drill;
        ambientSoundVolume = 0.018f;
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region, rotatorRegion, topRegion};
    }

    @Override
    public void load() {
        super.load();
        rimRegion = Core.atlas.find(name+"-rim");
        rotatorRegion = Core.atlas.find(name+"-rotator");
        topRegion = Core.atlas.find(name+"-top");
        consumes.add(new AdjustableConsumePower(powerUse, e -> {
            PrecussionDrillBuild p = ((PrecussionDrillBuild)e);
            return p.working() ? p.getBoost() : 0;
        }));
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.remove(Stat.itemCapacity); //вместительность динаическая и равна размеру партии
        stats.add(Stat.productionTime, drillTime / 60f, StatUnit.seconds);
        stats.remove(Stat.powerUse);
        stats.add(Stat.powerUse, powerUse * 60f, StatUnit.powerSecond);
        stats.add(Stat.drillTier, new BlockFilterValue(b -> b instanceof Floor && ((Floor)b).itemDrop != null && ((Floor)b).itemDrop.hardness <= tier));
        stats.add(Stat.drillSpeed, (hardnessDrillMultiplier * size * size * itemCountMultiplier) / (drillTime / 60f), StatUnit.itemsSecond);
        if(liquidBoostIntensity != 1) {
            stats.add(Stat.boostEffect, liquidBoostIntensity, StatUnit.timesSpeed);
        }
        if(!drillItems.isEmpty()) {
            stats.add(Stat.input, new DrillItemsValue(drillItems, drillItemCount));
        }
    }

    @Override
    public void setBars(){
        super.setBars();
        bars.add("progress", (PrecussionDrillBuild e) ->
                new Bar(() -> Core.bundle.get("bar.drillprogress"),
                        () -> Pal.surge,
                        () -> e.progressTime / ((PrecussionDrill)e.block).drillTime));
        bars.add("drillspeed", (PrecussionDrillBuild e) ->
            new Bar(() -> Core.bundle.format("bar.drillspeed",
                Strings.fixed(e.displaySpeed, 2)),
                () -> Pal.ammo,
                () -> e.currentSpeed));
    }

    public boolean canPlaceOn(Tile tile, Team team) {
        return updateOre(tile, items, tier, this) == State.OK;
    }
    private final ItemSeq items = new ItemSeq();
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);
        Tile tile = world.tile(x, y);
        if(tile == null) return;

        State state = updateOre(tile, items, tier, this);

        if(state == State.OK) {
            final float[] sumSpeed = {0};
            items.each((item, amount) -> sumSpeed[0] += (hardnessDrillMultiplier / Mathf.pow(item.hardness+1, 0.5f) * amount) / (drillTime / 60f));
            float width = drawPlaceText(Core.bundle.formatFloat("bar.drillspeed", sumSpeed[0] * itemCountMultiplier, 2), x, y, valid);
            final float[] dx = {x * tilesize + offset - width / 2f - 4f};
            float dy = y * tilesize + offset + size * tilesize / 2f + 5;
            items.each((item, amount) -> {
                Draw.mixcol(Color.darkGray, 1f);
                Draw.rect(item.icon(Cicon.small), dx[0], dy - 1);
                Draw.reset();
                Draw.rect(item.icon(Cicon.small), dx[0], dy);
                dx[0] -= 6;
            });
        }else if(state == State.LowTier){
            Tile to = tile.getLinkedTilesAs(this, tempTiles).find(t -> t.drop() != null && t.drop().hardness > tier);
            Item item = to == null ? null : to.drop();
            if(item != null) drawPlaceText(Core.bundle.get("bar.drilltierreq"), x, y, valid);
        }
    }
    private static State updateOre(Tile tile, ItemSeq items, int tier, PrecussionDrill inst) {
        final State[] ret = {State.NoOre};
        items.clear();
        tile.getLinkedTilesAs(inst, tempTiles).each(t -> {
            if(t != null && t.drop() != null) {
                if(t.drop().hardness <= tier) {
                    items.add(t.drop());
                    ret[0] = State.OK;
                } else if (ret[0] != State.OK) ret[0] = State.LowTier;
            }
        });
        return ret[0];
    }
    public static class DrillItem {
        public final Item item;
        public final float sizeMultiplier;
        public DrillItem(Item item, float sizeMultiplier) {
            this.item = item;
            this.sizeMultiplier = sizeMultiplier;
        }
    }
    private enum State {
        NoOre, //нет руды под буром
        LowTier, //"Требуется бур получше"
        OK //всё гуд
    }

    public class PrecussionDrillBuild extends Building {
        public float displaySpeed, baseDisplaySpeed;
        public float currentSpeed, baseSpeed, totalSpeed;
        public float progressTime;
        public float totalBoost;
        public float warmupSpeed = 0.02f;
        private int offloadSize; //размер партии = количество item'ов, выдаваемых за раз
        private DrillItem currentDrillItem; //текущий предмет для бурения

        final ItemSeq items = new ItemSeq(); //список руды, находящейся под буром
        @Override
        public void created() {
            updateOre(tile, items, tier, (PrecussionDrill)block);
            items.each((item, amount) -> {
                baseDisplaySpeed += (hardnessDrillMultiplier / Mathf.pow(item.hardness + 1, 0.5f) * amount) / (drillTime / 60f);
                offloadSize += (int)(amount * (hardnessDrillMultiplier / Mathf.pow(item.hardness+1, 0.5f)));
            });
            baseDisplaySpeed *= itemCountMultiplier;
        }

        @Override
        public void updateTile() {
            currentDrillItem = drillItems.find(di -> items().has(di.item, drillItemCount));
            //speed count
            baseSpeed = (power == null) ? 1f : power.status;
            if (!working()) baseSpeed = 0;
            currentSpeed = Mathf.lerpDelta(currentSpeed, baseSpeed, warmupSpeed);
            totalBoost = getBoost();
            if (cons.optionalValid()) totalBoost *= liquidBoostIntensity;
            totalSpeed = currentSpeed * totalBoost;
            //update display value
            displaySpeed = baseDisplaySpeed * totalSpeed;
            if(currentDrillItem != null) displaySpeed *= currentDrillItem.sizeMultiplier;
            //updating
            progressTime += Time.delta * totalSpeed;
            if (progressTime >= drillTime && working()) {
                progressTime %= drillTime;
                items.each((item, amount) -> {
                    float multiplier = hardnessDrillMultiplier / Mathf.pow(item.hardness + 1, 0.5f) * currentDrillItem.sizeMultiplier;
                    for (int i = 0; i < (int) (amount * multiplier * itemCountMultiplier); i++) offload(item);
                });
                items().remove(currentDrillItem.item, drillItemCount);
            }
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            boolean accept = currentDrillItem == null || currentDrillItem.item == item;
            return accept && drillItems.contains(di -> di.item == item) && items().get(item) < drillItemCapacity;
        }

        @Override
        public boolean canDump(Building to, Item item) { return false; }
        // возможность мультидобычи ломает обычный вывод ресурсов,
        // для стабильной работы нужно использовать разгрузчик

        @Override
        public int getMaximumAccepted(Item item) {
            if(drillItems.contains(di -> di.item == item)) return drillItemCapacity;
            return 0;
        }

        private boolean working() {
            return enabled && (items().total() < offloadSize) && (currentDrillItem != null);
        }
        private long boostEndTime = 0;
        private float boost = 0f;
        public float getBoost() {
            return Time.millis() <= boostEndTime ? Math.max(boost, 1) : 1;
        }
        @Override
        public void applyBoost(float intensity, float duration) {
            boostEndTime = Time.millis() + (long)(duration*1000f/60f);
            boost = intensity;
        }

        @Override
        public void drawSelect(){
            final float[] dx = {x - size * tilesize / 2f};
            float dy = y + size * tilesize/2f;
            items.each((item, amount) -> {
                Draw.mixcol(Color.darkGray, 1f);
                Draw.rect(item.icon(Cicon.small), dx[0], dy - 1);
                Draw.reset();
                Draw.rect(item.icon(Cicon.small), dx[0], dy);
                dx[0] -= 6;
            });
        }
    }
}
