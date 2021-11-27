package sunset.world.meta.values;

import arc.Core;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Strings;
import arc.util.Structs;
import mindustry.world.meta.StatValue;
import sunset.type.DrillItem;

import java.util.Arrays;

public class DrillItemsValue implements StatValue {
    protected final DrillItem[] items;
    protected final int count;
    public DrillItemsValue(DrillItem[] items, int count) {
        this.items = items;
        this.count = count;
        Arrays.sort(items,Structs.comparingFloat(di->di.sizeMultiplier));
    }
    @Override
    public void display(Table table) {
        table.table(t -> {
            t.left().defaults().padRight(3).left();
            for (DrillItem di : items) {
                t.row();
                t.add(Core.bundle.format("stat.drillitemdesc", count, di.item.emoji(), di.item.localizedName));
                t.add(Core.bundle.format("stat.drillsizedesc", Strings.autoFixed(di.sizeMultiplier, 2))).padLeft(6);
            }
        });
    }
}
