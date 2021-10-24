package sunset.io;

import mindustry.annotations.Annotations;
import mindustry.gen.Itemsc;
import mindustry.input.InputHandler;
import mindustry.type.Item;

public class SnInput {

    @Annotations.Remote(called = Annotations.Loc.server, unreliable = true)
    public static void transferItemToUnit(Item item,int amount, float x, float y, Itemsc to){
        if(to == null) return;
        InputHandler. createItemTransfer(item, 1, x, y, to, () -> to.addItem(item,amount));
    }
}
