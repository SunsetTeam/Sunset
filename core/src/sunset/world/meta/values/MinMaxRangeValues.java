package sunset.world.meta.values;

import arc.Core;
import arc.scene.ui.layout.Table;
import arc.util.Strings;
import mindustry.world.meta.StatValue;

import static mindustry.Vars.tilesize;

public class MinMaxRangeValues implements StatValue {
    public final float minRange;
    public final float maxRange;

    public MinMaxRangeValues(float minRange, float maxRange) {
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    @Override
    public void display(Table table) {
        table.table(bt -> {
            bt.left().defaults().padRight(3).left();
            bt.add(Core.bundle.format("missile.rangerange",
                    Strings.fixed(minRange / tilesize, 1),
                    Strings.fixed(maxRange / tilesize, 1)));
        }).left();

        table.row();
    }
}