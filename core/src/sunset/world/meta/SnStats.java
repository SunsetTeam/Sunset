package sunset.world.meta;

import arc.Core;
import arc.func.Cons;
import arc.scene.ui.layout.Table;
import arc.struct.OrderedMap;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.meta.*;

/** Характеристики с возможностью описания своих параметров.
 * @apiNote Методы без префикса предоставляют API по умолчанию. Методы с префиском L получают в параметры stat и
 * category заранее локализованыые строки. Методы с префиксом U получают в качестве аргументов stat и category
 * ключи для локализации. */
public class SnStats {
    private OrderedMap<String, OrderedMap<String, Seq<Cons<Table>>>> map = new OrderedMap<>();
    private boolean dirty;
    public boolean useCategories = true;

    public SnStats(Stats stats) {
        stats.toMap().each((category, catEntries) -> {
            catEntries.each((stat, statEntries) -> {
                statEntries.each(statValue -> {
                    addL(stat.localized(), category.localized(), statValue);
                });
            });
        });
        dirty = true;
    }
    //region stat as unlocalized string
    private String cbg(String key) { return Core.bundle.get(key); }
    public void addU(String stat, String category, float value, StatUnit unit) {
        addL(cbg("stat."+stat), cbg("category."+category), StatValues.number(value, unit));
    }
    public void addU(String stat, String category, float value) {
        addL(cbg("stat."+stat), cbg("category."+category), value, StatUnit.none);
    }
    public void addPercentU(String stat, String category, float value) {
        addL(cbg("stat."+stat), cbg("category."+category), StatValues.number((int)(value * 100), StatUnit.percent));
    }
    public void addU(String stat, String category, boolean value) {
        addL(cbg("stat."+stat), cbg("category."+category), StatValues.bool(value));
    }
    public void addU(String stat, String category, Item item) {
        addL(cbg("stat."+stat), cbg("category."+category), new ItemStack(item, 1));
    }
    public void addU(String stat, String category, ItemStack stack) {
        addL(cbg("stat."+stat), cbg("category."+category), StatValues.items(stack));
    }
    public void addU(String stat, String category, Liquid liquid, float amount, boolean perSecond) {
        addL(cbg("stat."+stat), cbg("category."+category), StatValues.liquid(liquid, amount, perSecond));
    }
    public void addU(String stat, String category, Attribute attr) {
        addL(cbg("stat."+stat), cbg("category."+category), attr, false, 1f, false);
    }
    public void addU(String stat, String category, Attribute attr, float scale) {
        addL(cbg("stat."+stat), cbg("category."+category), attr, false, scale, false);
    }
    public void addU(String stat, String category, Attribute attr, boolean floating) {
        addL(cbg("stat."+stat), cbg("category."+category), attr, floating, 1f, false);
    }
    public void addU(String stat, String category, Attribute attr, boolean floating, float scale, boolean startZero){
        addL(cbg("stat."+stat), cbg("category."+category), attr, floating, scale, startZero);
    }
    public void addU(String stat, String category, String format, Object... args) {
        addL(cbg("stat."+stat), cbg("category."+category), StatValues.string(format, args));
    }
    public void addU(String stat, String category, StatValue value) {
        addL(cbg("stat."+stat), cbg("category."+category), value);
    }
    public void removeU(String stat, String category) {
        removeL(cbg("stat."+stat), cbg("category."+category));
    }
    //endregion
    //region default API (Stat)
    public void add(Stat stat, float value, StatUnit unit) {
        addL(stat.localized(), stat.category.localized(), value, unit);
    }
    public void add(Stat stat, float value){
        addL(stat.localized(), stat.category.localized(), value);
    }
    public void addPercent(Stat stat, float value){
        addPercentL(stat.localized(), stat.category.localized(), value);
    }
    public void add(Stat stat, boolean value){
        addL(stat.localized(), stat.category.localized(), value);
    }
    public void add(Stat stat, Item item){
        addL(stat.localized(), stat.category.localized(), item);
    }
    public void add(Stat stat, ItemStack item){
        addL(stat.localized(), stat.category.localized(), item);
    }
    public void add(Stat stat, Liquid liquid, float amount, boolean perSecond) {
        addL(stat.localized(), stat.category.localized(), liquid, amount, perSecond);
    }
    public void add(Stat stat, Attribute attr){
        addL(stat.localized(), stat.category.localized(), attr);
    }
    public void add(Stat stat, Attribute attr, float scale){
        addL(stat.localized(), stat.category.localized(), attr, scale);
    }
    public void add(Stat stat, Attribute attr, boolean floating){
        addL(stat.localized(), stat.category.localized(), attr, floating);
    }
    public void add(Stat stat, String format, Object... args){
        addL(stat.localized(), stat.category.localized(), format, args);
    }
    public void remove(Stat stat){
        removeL(stat.localized(), stat.category.localized());
    }
    //endregion
    //region stat as localized string (realization here)
    public void addL(String stat, String category, float value, StatUnit unit) {
        addL(stat, category, StatValues.number(value, unit));
    }
    public void addL(String stat, String category, float value) {
        addL(stat, category, value, StatUnit.none);
    }
    public void addPercentL(String stat, String category, float value) {
        addL(stat, category, StatValues.number((int)(value * 100), StatUnit.percent));
    }
    public void addL(String stat, String category, boolean value) {
        addL(stat, category, StatValues.bool(value));
    }
    public void addL(String stat, String category, Item item) {
        addL(stat, category, new ItemStack(item, 1));
    }
    public void addL(String stat, String category, ItemStack stack) {
        addL(stat, category, StatValues.items(stack));
    }
    public void addL(String stat, String category, Liquid liquid, float amount, boolean perSecond) {
        addL(stat, category, StatValues.liquid(liquid, amount, perSecond));
    }
    public void addL(String stat, String category, Attribute attr) {
        addL(stat, category, attr, false, 1f, false);
    }
    public void addL(String stat, String category, Attribute attr, float scale) {
        addL(stat, category, attr, false, scale, false);
    }
    public void addL(String stat, String category, Attribute attr, boolean floating) {
        addL(stat, category, attr, floating, 1f, false);
    }
    public void addL(String stat, String category, Attribute attr, boolean floating, float scale, boolean startZero){
        for(Block block : Vars.content.blocks()
                .select(block ->
                    block instanceof Floor &&
                    block.asFloor().attributes.get(attr) != 0 &&
                    !(block.asFloor().isLiquid && !floating))
                .<Floor>as()
                .with(s -> s.sort(f -> f.attributes.get(attr)))
            ) addL(stat,
                category,
        StatValues.blockEfficiency(block.asFloor(),
                    block.asFloor().attributes.get(attr) * scale,
                    startZero));
    }
    public void addL(String stat, String category, String format, Object... args) {
        addL(stat, category, StatValues.string(format, args));
    }
    public void addL(String stat, String category, StatValue value) {
        map.get(category, OrderedMap::new).get(stat, Seq::new).add(value::display);
        dirty = true;
    }
    public void removeL(String stat, String category) {
        map.get(category, OrderedMap::new).remove(stat);
        dirty = true;
    }
    //endregion
    public OrderedMap<String, OrderedMap<String, Seq<Cons<Table>>>> toMap() {
        if(map == null) {
            map = new OrderedMap<>();
        } else if (dirty) {
            map.orderedKeys().sort();
            map.entries().forEach(e -> e.value.orderedKeys().sort());
            dirty = false;
        }
        return map;
    }
}
