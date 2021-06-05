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
    private Boolf<Liquid> filter;
    private Func<Liquid, Float> factorGen;
    private String bundle;

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

                c.image(liquid.icon(Cicon.medium)).size(3 * 8).padRight(4).right().top();
                c.add(liquid.localizedName).padRight(10).left().top();
                c.table(Tex.underline, bt -> {
                    bt.left().defaults().padRight(3).left();
                    //TODO: это - костыль. Необходимо перенести в bundle файлы
                    bt.add(Core.bundle.get(bundle) + " x" + Strings.autoFixed(factorGen.get(liquid), 2));
                }).left().padTop(-9);
                c.row();
            }
        }).colspan(table.getColumns());
        table.row();
    }
}
