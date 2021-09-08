package sunset.world.meta.values;

import arc.Core;
import arc.scene.Element;
import arc.scene.ui.Label;
import arc.scene.ui.TextButton;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import arc.util.Strings;
import mindustry.world.meta.StatValue;
import sunset.type.StackableStatusEffect;

/** Создаёт интерактивный интерфейс, предоставляющий информацию о {@link StackableStatusEffect}. */
public class StackableStatusEffectValue implements StatValue {
    final StackableStatusEffect effect;
    final TextButton prevStackButton, nextStackButton;
    final Label topDescriptionLabel, damageMultLabel, healthMultLabel, speedMultLabel,
        reloadMultLabel, disarmedLabel, damageLabel, buildSpeedMultLabel;
    private int currentStack = 0;
    public StackableStatusEffectValue(StackableStatusEffect effect) {
        this.effect = effect;
        topDescriptionLabel = new Label("");
        prevStackButton = new TextButton("<");
        prevStackButton.clicked(this::clickPrev);
        nextStackButton = new TextButton(">");
        nextStackButton.clicked(this::clickNext);
        damageMultLabel = new Label("");
        healthMultLabel = new Label("");
        speedMultLabel = new Label("");
        reloadMultLabel = new Label("");
        disarmedLabel = new Label("");
        damageLabel = new Label("");
        buildSpeedMultLabel = new Label("");
        updateTable();
    }
    private void updateTable() {
        topDescriptionLabel.setText(Core.bundle.format("stat.sse-description", currentStack+1, effect.maxStacks));
        topDescriptionLabel.setAlignment(Align.center);
        String val = (int)(effect.stacks.get(currentStack).damageMultiplier * 100) + "%";
        damageMultLabel.setText(format("stat.damagemultiplier", val));
        val = (int)(effect.stacks.get(currentStack).healthMultiplier * 100) + "%";
        healthMultLabel.setText(format("stat.healthmultiplier", val));
        val = (int)(effect.stacks.get(currentStack).speedMultiplier * 100) + "%";
        speedMultLabel.setText(format("stat.speedmultiplier", val));
        val = (int)(effect.stacks.get(currentStack).reloadMultiplier * 100) + "%";
        reloadMultLabel.setText(format("stat.reloadmultiplier", val));
        val = (int)(effect.stacks.get(currentStack).buildSpeedMultiplier * 100) + "%";
        buildSpeedMultLabel.setText(format("stat.buildspeedmultiplier", val));
        val = effect.stacks.get(currentStack).disarm ? Core.bundle.get("yes") : Core.bundle.get("no");
        disarmedLabel.setText(format("stat.disarm", val));
        val = Strings.autoFixed(effect.stacks.get(currentStack).damage * 60f, 2) + Core.bundle.get("unit.persecond");
        damageLabel.setText(format("stat.damage", val));
    }
    private String format(String key, String val) {
        return "[lightgray]" + Core.bundle.get(key) + ":[] " + val;
    }
    private void clickNext() {
        currentStack++;
        if(currentStack < effect.maxStacks) {
            updateTable();
        } else {
            currentStack = effect.maxStacks-1;
        }
    }
    private void clickPrev() {
        currentStack--;
        if(currentStack > -1) {
            updateTable();
        } else {
            currentStack = 0;
        }
    }
    @Override
    public void display(Table table) {
        displayStackMenu(table.table().align(Align.center).get());
        table.row();
    }
    private void displayStackMenu(Table table) {
        table.add(prevStackButton);
        table.add(topDescriptionLabel).align(Align.center).pad(3);
        table.add(nextStackButton);
        addRow(table, damageMultLabel);
        addRow(table, healthMultLabel);
        addRow(table, speedMultLabel);
        addRow(table, reloadMultLabel);
        addRow(table, disarmedLabel);
        addRow(table, damageLabel);
    }
    private static void addRow(Table t, Element e) {
        t.row();
        t.add(e).colspan(3);
    }
}