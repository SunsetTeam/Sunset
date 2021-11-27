package sunset.world.blocks.production.raw;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Strings;
import arc.util.Structs;
import arc.util.Time;
import mindustry.annotations.Annotations.Load;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.ItemSeq;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.power.DynamicConsumePower;
import mindustry.world.consumers.ConsumeItemFilter;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;
import mma.ModVars;
import sunset.type.DrillItem;
import sunset.world.consumers.DrillItemsConsume;
import sunset.world.meta.values.DrillItemsValue;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class PrecussionDrill extends Block {
    private static final ItemSeq tmpItems = new ItemSeq();
    /**
     * Чем выше, тем быстрее бурит. Сильнее воздейсвует на мягкие руды, чем на твёрдые.
     */
    public float hardnessDrillMultiplier = 6f;
    /**
     * Чем выше, тем быстрее бурит. Одинаково воздейсвует на все руды.
     */
    public float itemCountMultiplier = 1f;
    /**
     * Во сколько раз ускоряется водой.
     */
    public float liquidBoostIntensity = 1.6f;
    /**
     * Длительность добычи одной партии ресурсов.
     */
    public float drillTime = 540f;
    /**
     * Список ресурсов, используемых при добыче.
     */
    public DrillItem[] reqDrillItems = {};
    /**
     * Количество используемого ресурса за одну партию.
     */
    public int drillItemCount = 2;
    /**
     * Вместимость используемого ресурса за одну партию.
     */
    public int drillItemCapacity = 10;

    /**
     * automatic unloading on adjacent blocks
     * */
    public boolean canDump=false;

    public int tier = 5;
    public float powerUse = 1f;
    public Color heatColor = Color.valueOf("ff5512");
    @Load("@-rim")
    public TextureRegion rimRegion;
    @Load("@-rotator")
    public TextureRegion rotatorRegion;
    @Load("@-top")
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

    public  State updateOre(Tile tile, ItemSeq items, int tier) {
        return updateOre(tile,items,tier,this);
    }
    public static State updateOre(Tile tile, ItemSeq items, int tier, PrecussionDrill inst) {
        State ret = State.NoOre;
        items.clear();
        for (Tile t : tile.getLinkedTilesAs(inst, tempTiles)) {
            if (t != null && t.drop() != null) {
                if (t.drop().hardness <= tier) {
                    items.add(t.drop());
                    ret= State.OK;
                } else if (ret != State.OK) ret = State.LowTier;
            }
        }
        return ret;
    }

    public void drillItems(DrillItem... drillItems) {
        this.reqDrillItems = drillItems;
    }

    @Override
    public TextureRegion[] icons() {
        return !ModVars.packSprites ? new TextureRegion[]{region} : new TextureRegion[]{region, rotatorRegion, topRegion};
    }

    @Override
    public void init() {
        consumes.add(new DynamicConsumePower(b -> {
            PrecussionDrillBuild p = (PrecussionDrillBuild) b;
            return powerUse * (p.working() ? p.getBoost() : 0);
        }));
//        consumes.add(new ConsumeItemFilter(i -> Structs.contains(reqDrillItems, d -> d.item == i)));
        consumes.add(new DrillItemsConsume(reqDrillItems,drillItemCount));
        super.init();

    }

    @Override
    public void setStats() {
        super.setStats();
        stats.remove(Stat.itemCapacity); //вместительность динаическая и равна размеру партии
        stats.add(Stat.productionTime, drillTime / 60f, StatUnit.seconds);
        stats.remove(Stat.powerUse);
        stats.add(Stat.powerUse, powerUse * 60f, StatUnit.powerSecond);
        stats.add(Stat.drillTier, StatValues.blocks(b -> b instanceof Floor && b.itemDrop != null && b.itemDrop.hardness <= tier));
        stats.add(Stat.drillSpeed, (hardnessDrillMultiplier * size * size * itemCountMultiplier) / (drillTime / 60f), StatUnit.itemsSecond);
        if (liquidBoostIntensity != 1) {
            stats.add(Stat.boostEffect, liquidBoostIntensity, StatUnit.timesSpeed);
        }
    }

    @Override
    public void setBars() {
        super.setBars();
        bars.add("progress", (PrecussionDrillBuild e) ->
                new Bar(() -> Core.bundle.get("bar.drillprogress"),
                        () -> Pal.surge,
                        () -> e.progressTime / ((PrecussionDrill) e.block).drillTime));
        bars.add("drillspeed", (PrecussionDrillBuild e) ->
                new Bar(() -> Core.bundle.format("bar.drillspeed",
                        Strings.fixed(e.displaySpeed, 2)),
                        () -> Pal.ammo,
                        () -> e.currentSpeed));
    }

    public boolean canPlaceOn(Tile tile, Team team) {
        return updateOre(tile, tmpItems, tier) == State.OK;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);
        Tile tile = world.tile(x, y);
        if (tile == null) return;

        State state = updateOre(tile, tmpItems, tier);

        if (state == State.OK) {
            final float[] sumSpeed = {0};
            tmpItems.each((item, amount) -> sumSpeed[0] += (hardnessDrillMultiplier / Mathf.pow(item.hardness + 1, 0.5f) * amount) / (drillTime / 60f));
            float width = drawPlaceText(Core.bundle.formatFloat("bar.drillspeed", sumSpeed[0] * itemCountMultiplier, 2), x, y, valid);
            final float[] dx = {x * tilesize + offset - width / 2f - 4f};
            float dy = y * tilesize + offset + size * tilesize / 2f + 5;
            tmpItems.each((item, amount) -> {
                Draw.mixcol(Color.darkGray, 1f);
                Draw.rect(item.uiIcon, dx[0], dy - 1);
                Draw.reset();
                Draw.rect(item.uiIcon, dx[0], dy);
                dx[0] -= 6;
            });
        } else if (state == State.LowTier) {
            Tile to = tile.getLinkedTilesAs(this, tempTiles).find(t -> t.drop() != null && t.drop().hardness > tier);
            Item item = to == null ? null : to.drop();
            if (item != null) drawPlaceText(Core.bundle.get("bar.drilltierreq"), x, y, valid);
        }
    }

    private enum State {
        NoOre, //нет руды под буром
        LowTier, //"Требуется бур получше"
        OK //всё гуд
    }

    public class PrecussionDrillBuild extends Building {
        final ItemSeq drillItems = new ItemSeq(); //список руды, находящейся под буром
        public float displaySpeed, baseDisplaySpeed;
        public float currentSpeed, totalSpeed;
        public float progressTime;
        public float totalBoost;
        public float warmupSpeed = 0.02f;
        private int offloadSize; //размер партии = количество item'ов, выдаваемых за раз
        private DrillItem currentDrillItem; //текущий предмет для бурения
        private long boostEndTime = 0;
        private float boost = 0f;
public float baseSpeed(){
    return working()?(power == null) ? 1f : power.status:0f;
}
        @Override
        public void created() {
            updateOre(tile, drillItems, tier);
            drillItems.each((item, amount) -> {
                baseDisplaySpeed += (hardnessDrillMultiplier / Mathf.pow(item.hardness + 1, 0.5f) * amount) / (drillTime / 60f);
                offloadSize += (int) (amount * (hardnessDrillMultiplier / Mathf.pow(item.hardness + 1, 0.5f)));
            });
            baseDisplaySpeed *= itemCountMultiplier;
        }

        @Override
        public void updateTile() {
            currentDrillItem = Structs.find(reqDrillItems, di -> items.has(di.item, drillItemCount));

            currentSpeed = Mathf.lerpDelta(currentSpeed, baseSpeed(), warmupSpeed);
            if (!working()){
                return;
            }
            //speed count
            totalBoost = getBoost();
            if (cons.optionalValid()) totalBoost *= liquidBoostIntensity;
            totalSpeed = currentSpeed * totalBoost;
            //update display value
            displaySpeed = baseDisplaySpeed * totalSpeed;
            if (currentDrillItem != null) displaySpeed *= currentDrillItem.sizeMultiplier;
            //updating
            progressTime += Time.delta * totalSpeed;
            if (progressTime >= drillTime && working()) {
                cons.trigger();
                progressTime %= drillTime;
                drillItems.each((item, amount) -> {
                    float multiplier = hardnessDrillMultiplier / Mathf.sqrt(item.hardness + 1) * currentDrillItem.sizeMultiplier;
                    for (int i = 0; i < (int) (amount * multiplier * itemCountMultiplier); i++) {
                        offload(item);
                    }
                });
            }
            if (canDump){
                drillItems.each((i, a) -> dump(i));
            }
        }
        // возможность мультидобычи ломает обычный вывод ресурсов,
        // для стабильной работы нужно использовать разгрузчик

        @Override
        public boolean acceptItem(Building source, Item item) {
            boolean accept = currentDrillItem == null || currentDrillItem.item == item;
            return accept && Structs.contains(reqDrillItems, di -> di.item == item) && items().get(item) < drillItemCapacity;
        }

        @Override
        public boolean canDump(Building to, Item item) {
            return canDump&& drillItems.has(item);
        }

        @Override
        public int getMaximumAccepted(Item item) {
            if (Structs.contains(reqDrillItems, di -> di.item == item)) return drillItemCapacity;
            return 0;
        }

        private boolean working() {
            return enabled && (items().total() < offloadSize) && (currentDrillItem != null) && cons.valid();
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
        public void drawSelect() {
            final float[] dx = {x - size * tilesize / 2f};
            float dy = y + size * tilesize / 2f;
            drillItems.each((item, amount) -> {
                Draw.mixcol(Color.darkGray, 1f);
                Draw.rect(item.uiIcon, dx[0], dy - 1);
                Draw.reset();
                Draw.rect(item.uiIcon, dx[0], dy);
                dx[0] -= 6;
            });
        }

        @Override
        public void draw() {
            float s = 0.3f;
            float ts = 0.6f;

            Draw.rect(region, x, y);
            super.drawCracks();

            Draw.color(heatColor);
            //Draw.alpha(warmup * ts * (1f - s + Mathf.absin(Time.time, 3f, s)));
            Draw.blend(Blending.additive);
            Draw.rect(rimRegion, x, y);
            Draw.blend();
            Draw.color();

            Drawf.spinSprite(rotatorRegion, x, y, 2);

            Draw.rect(topRegion, x, y);
        }
    }
}
