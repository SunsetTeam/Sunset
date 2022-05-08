package sunset.world.meta.values;

import arc.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.*;
import arc.scene.actions.*;
import arc.scene.ui.*;
import arc.scene.ui.TextField.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import sunset.type.*;

/**
 * Creates an interactive interface providing information about {@link StackableStatusEffect}.
 */
public class StackableStatusEffectValue implements StatValue{
    final StackableStatusEffect effect;
    private int currentStack = 0;
    private boolean updateField = true;

    public StackableStatusEffectValue(StackableStatusEffect effect){
        this.effect = effect;
    }

    private Prov<CharSequence> percent(boolean toInt, Floatp persent){
        if(toInt){
            return () -> (int)(persent.get() * 100) + "%";
        }else{
            return () -> persent.get() + "%";
        }
    }

    private void clickNext(){
        currentStack++;
        if(currentStack >= effect.maxStacks()){
            currentStack = effect.maxStacks() - 1;
        }else{
            updateField = false;
        }
    }

    private void clickPrev(){
        currentStack--;
        if(currentStack < 0){
            currentStack = 0;
        }else{
            updateField = false;
        }
    }

    @Override
    public void display(Table table){
        displayStackMenu(table.table().align(Align.center).get());
        table.row();
    }

    private Cell<Table> buildField(Table table, String name, Prov<CharSequence> value){
        return table.table(t -> {
            t.add(name + ": ").color(Color.lightGray);
            t.add(new ValueLabel(value));
        }).margin(0);
    }

    private Cell<Table> buildFieldKey(Table table, String bundleKey, Prov<CharSequence> value){
        return buildField(table, Core.bundle.get(bundleKey), value);
    }

    private void displayStackMenu(Table table){
        String yes = Core.bundle.get("yes");
        String no = Core.bundle.get("no");

        table.defaults().left();
        table.button("<", this::clickPrev).disabled(button -> currentStack == 0);
        table.table(this::setupTop).align(Align.center).pad(3);
        table.button(">", this::clickNext).disabled(button -> currentStack >= effect.maxStacks() - 1).row();
        table.defaults().colspan(3);
        buildFieldKey(table, "stat.damagemultiplier", percent(true, () -> stack().damageMultiplier)).row();
        buildFieldKey(table, "stat.healthmultiplier", percent(true, () -> stack().healthMultiplier)).row();
        buildFieldKey(table, "stat.speedmultiplier", percent(true, () -> stack().speedMultiplier)).row();
        buildFieldKey(table, "stat.reloadmultiplier", percent(true, () -> stack().reloadMultiplier)).row();
        buildFieldKey(table, "stat.buildspeedmultiplier", percent(true, () -> stack().buildSpeedMultiplier)).row();
        buildFieldKey(table, "stat.disarm", () -> stack().disarm ? yes : no).row();
        buildFieldKey(table, "stat.damage", () -> Strings.autoFixed(stack().damage * 60f, 2) + StatUnit.perSecond.localized()).row();
    }

    private StatusEffect stack(){
        return effect.stack(currentStack);
    }

    private void setupTop(Table table){

        String format = Core.bundle.format("stat.sse-description", "\n\n\n", effect.maxStacks());
        String[] strings = format.split("\n\n\n");
        table.table(t -> {

            t.add(strings[0]).align(Align.center).pad(3);
            t.field((currentStack + 1) + "", TextFieldFilter.digitsOnly, s -> {
                if(s.equals("")) return;
                int i = Strings.parseInt(s, -1);
                currentStack = Mathf.clamp(i - 1, 0, effect.maxStacks() - 1);
                if(currentStack != i - 1){
                    updateField = false;
                }
            }).update(f -> {
                if(!updateField){
                    f.setText((currentStack + 1) + "");
                    updateField = true;
                }
            }).self(cell -> {

                GlyphLayout obtain = GlyphLayout.obtain();
                obtain.setText(cell.get().getStyle().font, "0000");
                cell.width(obtain.width);
                obtain.free();
            });
//            t.add(new ValueLabel(() -> (currentStack + 1) + ""));
            t.add(strings[1]);

        });
    }

    private void colorAction(Element element){
        element.color.set(Pal.accent);
        for(Action action : element.getActions().select(a -> a instanceof ColorAction)){
            element.removeAction(action);
        }
        ColorAction color = Actions.color(Color.white, 1f);
//        color.setColor(Pal.accent);
//        color.setEndColor(Color.white);
        element.addAction(color);
    }

    private class ValueLabel extends Label{

        public ValueLabel(Prov<CharSequence> sup){
            super(sup.get());
            update(() -> {
                CharSequence newText = sup.get();
                if(!getText().toString().equals(newText + "")){
                    colorAction(this);
                    setText(sup.get());
                }
            });
        }
    }
}