package sunset.content.blocks;

import static mindustry.type.ItemStack.with;

import mindustry.content.Items;
import mindustry.content.Fx;
import mindustry.content.*;
import mindustry.world.*;
import mindustry.content.Liquids;
import mindustry.ctype.ContentList;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.power.NuclearReactor;
import sunset.content.SnItems;

public class SnPower implements ContentList{
    public static Block

//reactors
    planatriumReactor;

    @Override
    public void load() {

    planatriumReactor = new NuclearReactor("planatrium-reactor"){{
            requirements(Category.power, with(Items.lead, 400, Items.silicon, 270, Items.graphite, 220, SnItems.planatrium, 200, SnItems.fors, 180, SnItems.nobium, 120));
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.24f;
            size = 5;
            explosionRadius = 21;
            explosionDamage = 2100;
            health = 4100;
            itemCapacity = 50;
            liquidCapacity = 80;
            itemDuration = 280f;
            powerProduction = 38f;
//latter            explodeEffect = Fx.reactorExplosion;
            consumes.item(SnItems.planatrium);
            heating = 0.07f;
            consumes.liquid(Liquids.cryofluid, heating / coolantPower).update(false);
        }};
}
}
