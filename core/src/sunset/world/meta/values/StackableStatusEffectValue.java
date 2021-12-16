package sunset.world.meta.values;

import arc.Core;
import arc.func.Floatp;
import arc.func.Prov;
import arc.graphics.Color;
import arc.scene.Action;
import arc.scene.Element;
import arc.scene.actions.Actions;
import arc.scene.actions.ColorAction;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Cell;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import arc.util.Strings;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValue;
import sunset.type.StackableStatusEffect;

/**
 * Создаёт интерактивный интерфейс, предоставляющий информацию о {@link StackableStatusEffect}.
 */
public class StackableStatusEffectValue implements StatValue {
    final StackableStatusEffect effect;
    private int currentStack = 0;

    public StackableStatusEffectValue(StackableStatusEffect effect) {
        this.effect = effect;
    }
    private Prov<CharSequence> percent(boolean toInt, Floatp persent) {
        if (toInt) {
            return () -> (int) (persent.get() * 100) + "%";
        } else {
            return () -> persent.get() + "%";
        }
    }

    private String format(String key, String val) {
        return "[lightgray]" + Core.bundle.get(key) + ":[] " + val;
    }

    private void clickNext() {
        currentStack++;
        if (currentStack < effect.maxStacks) {
        } else {
            currentStack = effect.maxStacks - 1;
        }
    }

    private void clickPrev() {
        currentStack--;
        if (currentStack > -1) {
        } else {
            currentStack = 0;
        }
    }

    @Override
    public void display(Table table) {
        displayStackMenu(table.table().align(Align.center).get());
        table.row();
    }

    private Cell<Table> buildField(Table table, String name, Prov<CharSequence> value) {
        return table.table(t -> {
            t.add(name + ": ").color(Color.lightGray);
            t.add(new ValueLabel(value));
        }).margin(0);
    }

    private Cell<Table> buildFieldKey(Table table, String bundleKey, Prov<CharSequence> value) {
        return buildField(table, Core.bundle.get(bundleKey), value);
    }

    private void displayStackMenu(Table table) {
        String yes = Core.bundle.get("yes");
        String no = Core.bundle.get("no");


        table.button("<", this::clickPrev).disabled(button->currentStack==0);
        table.table(this::setupTop).align(Align.center).pad(3);
        table.button(">", this::clickNext).disabled(button -> currentStack >= effect.maxStacks-1).row();
        table.defaults().colspan(3);
        buildFieldKey(table, "stat.damagemultiplier", percent(true, () -> stack().damageMultiplier)).row();
        buildFieldKey(table, "stat.healthmultiplier", percent(true, () -> stack().healthMultiplier)).row();
        buildFieldKey(table, "stat.speedmultiplier", percent(true, () -> stack().speedMultiplier)).row();
        buildFieldKey(table, "stat.reloadmultiplier", percent(true, () -> stack().reloadMultiplier)).row();
        buildFieldKey(table, "stat.buildspeedmultiplier", percent(true, () -> stack().buildSpeedMultiplier)).row();
        buildFieldKey(table, "stat.disarm", () -> stack().disarm ? yes : no).row();
        buildFieldKey(table, "stat.damage", () -> Strings.autoFixed(stack().damage * 60f, 2) + StatUnit.perSecond.localized()).row();
    }

    private StatusEffect stack() {
        return effect.stacks.get(currentStack);
    }

    private void setupTop(Table table) {

        String format = Core.bundle.format("stat.sse-description", "\n\n\n", effect.maxStacks);
        String[] strings = format.split("\n\n\n");
        table.table(t -> {

            t.add(strings[0]).align(Align.center).pad(3);
            t.add(new ValueLabel(() -> (currentStack + 1) + ""));
            t.add(strings[1]);

        });
    }

    private void colorAction(Element element) {
        element.color.set(Pal.accent);
        for (Action action : element.getActions().select(a -> a instanceof ColorAction)) {
            element.removeAction(action);
        }
        ColorAction color = Actions.color(Color.white, 1f);
//        color.setColor(Pal.accent);
//        color.setEndColor(Color.white);
        element.addAction(color);
    }

    private class ValueLabel extends Label {

        public ValueLabel(Prov<CharSequence> sup) {
            super(sup.get());
            update(() -> {
                CharSequence newText = sup.get();
                if (!getText().toString().equals(newText + "")) {
                    colorAction(this);
                    setText(sup.get());
                }
            });
        }
    }
}