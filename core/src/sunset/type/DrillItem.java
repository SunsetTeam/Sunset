package sunset.type;

import mindustry.type.Item;

public class DrillItem {
    public final Item item;
    public final float sizeMultiplier;
    public int amount;

    public DrillItem(Item item, float sizeMultiplier, int amount) {
        this.item = item;
        this.sizeMultiplier = sizeMultiplier;
        this.amount = amount;
    }

    public DrillItem(Item item, float sizeMultiplier) {
        this(item,sizeMultiplier,-1);
    }

    public DrillItem amount(int amount) {
        this.amount = amount;
        return this;
    }
}
