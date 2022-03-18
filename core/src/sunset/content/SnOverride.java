package sunset.content;

import arc.struct.Seq;
import arc.util.Structs;
import mindustry.content.Blocks;
import mindustry.ctype.ContentList;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.world.Block;

import java.util.Arrays;

public class SnOverride implements ContentList {
    public void load() {
        Blocks.duct.buildVisibility.visible();
        Blocks.ductRouter.buildVisibility.visible();
        Blocks.ductBridge.buildVisibility.visible();
    }
    private static void addReq(Block target, ItemStack... items){
        ItemStack[] newReq = new ItemStack[items.length + target.requirements.length];

        System.arraycopy(target.requirements, 0, newReq, 0, target.requirements.length);
        System.arraycopy(items, 0, newReq, target.requirements.length, items.length);

        target.requirements = newReq;
        Arrays.sort(target.requirements, Structs.comparingInt((j) -> j.item.id));
    }

    private static void removeReq(Block target, Item... items){
        Seq<ItemStack> req = new Seq<>(ItemStack.class);
        req.addAll(target.requirements);

        for(Item item : items)req.each(itemReq -> itemReq.item == item, req::remove);

        target.requirements = req.shrink();
    }
}
