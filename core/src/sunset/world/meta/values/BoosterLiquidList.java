package sunset.world.meta.values;

import arc.Core;
import arc.func.Boolf;
import arc.func.Func;
import arc.scene.ui.layout.Table;
import arc.util.Strings;
import mindustry.gen.Tex;
import mindustry.type.Liquid;
import mindustry.ui.Cicon;
import mindustry.world.meta.StatValue;

import static mindustry.Vars.content;

/** Представляет настраиваемый список ускоряющих
 * жидкостей в параметрах блока.
 */
public class BoosterLiquidList implements StatValue {
    private final Boolf<Liquid> filter;
    private final Func<Liquid, Float> factorGen;
    private final String bundle;

    public BoosterLiquidList(Boolf<Liquid> filter, Func<Liquid, Float> factorGen, String bundle) {
        this.filter = filter;
        this.bundle = bundle;
        this.factorGen = factorGen;
    }

    @Override
    public void display(Table table){
        table.row();
        table.table(c -> {
            for(Liquid liquid : content.liquids()){
                if(!filter.get(liquid)) continue;

                c.image(liquid.fullIcon).size(3 * 8).padRight(4).right().top();
                c.add(liquid.localizedName).padRight(10).left().top();
                c.table(Tex.underline, bt -> {
                    bt.left().defaults().padRight(3).left();
                    bt.add(Core.bundle.format(bundle, Strings.autoFixed(factorGen.get(liquid), 2)));
                }).left().padTop(-9);
                c.row();
            }
        }).colspan(table.getColumns());
        table.row();
    }
}
