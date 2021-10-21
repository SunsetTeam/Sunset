package sunset.world.meta.values;

import arc.Core;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Strings;
import mindustry.world.meta.StatValue;
import sunset.world.blocks.production.raw.PrecussionDrill.DrillItem;

public class DrillItemsValue implements StatValue {
    protected final Seq<DrillItem> items;
    protected final int count;
    public DrillItemsValue(Seq<DrillItem> items, int count) {
        this.items = items;
        this.count = count;
        this.items.sort(di -> di.sizeMultiplier);
    }
    @Override
    public void display(Table table) {
        table.table(t -> {
            t.left().defaults().padRight(3).left();
            items.each(di -> {
                t.row();
                t.add(Core.bundle.format("stat.drillitemdesc", count, di.item.emoji(), di.item.localizedName));
                t.add(Core.bundle.format("stat.drillsizedesc", Strings.autoFixed(di.sizeMultiplier, 2))).padLeft(6);
            });
        });
    }
}
