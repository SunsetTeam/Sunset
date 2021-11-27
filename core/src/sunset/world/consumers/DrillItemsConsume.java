package sunset.world.consumers;

import arc.scene.ui.layout.Table;
import arc.struct.Bits;
import arc.util.Structs;
import mindustry.gen.Building;
import mindustry.ui.ItemImage;
import mindustry.ui.MultiReqImage;
import mindustry.ui.ReqImage;
import mindustry.world.consumers.Consume;
import mindustry.world.consumers.ConsumeType;
import mindustry.world.meta.Stat;
import mindustry.world.meta.Stats;
import sunset.type.DrillItem;
import sunset.world.meta.values.DrillItemsValue;

public class DrillItemsConsume extends Consume {
    public final DrillItem[] drillItems;
    public final int itemAmount;

    public DrillItemsConsume(DrillItem[] drillItems, int itemAmount) {
        this.drillItems = drillItems;
        this.itemAmount = itemAmount;
    }

    @Override
    public void applyItemFilter(Bits filter) {
        for (DrillItem item : drillItems) {
            filter.set(item.item.id);
        }
    }

    @Override
    public void build(Building tile, Table table) {

        MultiReqImage image = new MultiReqImage();
        for (DrillItem item : drillItems) {
            if (!item.item.unlockedNow()) {
                continue;
            }
            image.add(new ReqImage(new ItemImage(item.item.uiIcon, itemAmount), () -> tile.items != null && tile.items.has(item.item)));
        }

        table.add(image).size(8 * 4);
    }

    @Override
    public String getIcon() {
        return "icon-item";
    }

    @Override
    public void update(Building entity) {

    }

    @Override
    public void trigger(Building entity) {
        entity.items.remove(Structs.find(drillItems, d -> entity.items.has(d.item, itemAmount)).item, itemAmount);
    }

    @Override
    public void display(Stats stats) {
        stats.add(Stat.input, new DrillItemsValue(drillItems, itemAmount));
    }

    @Override
    public ConsumeType type() {
        return ConsumeType.item;
    }

    @Override
    public boolean valid(Building entity) {
        for (DrillItem drillItem : drillItems) {
            if (entity.items.has(drillItem.item, itemAmount)) {
                return true;
            }
        }
        return false;
    }
}
