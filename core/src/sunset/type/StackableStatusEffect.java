package sunset.type;

import acontent.world.meta.AStats;
import arc.scene.ui.layout.Table;
import mindustry.*;
import mindustry.content.TechTree;
import arc.struct.BoolSeq;
import arc.struct.FloatSeq;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.entities.units.StatusEntry;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mma.*;
import sunset.entities.units.StackableStatusEntry;
import sunset.world.meta.values.StackableStatusEffectValue;

import static mindustry.Vars.state;

import java.lang.reflect.Field;

/**Superimposed status effect. Has varying strength on
 * each stage of overlap tends to increase.
 * @implNote If the length of the array defining this or that parameter
 * the effect status does not match the {@code maxStacks} parameter, then
 * parameters from regular {@link StatusEffect} are used.
 * Not intended for reactive effects (what's the point of stacking them if they instantly disappear?)
 * @apiNote Use the {@code apply (Unit, float)} method to set the status of an effect.
 * Instead of {@code update (Unit, float)}, override the {@code updateStack (Unit, float, int)} method.
 * Instead of {@code draw (Unit)}, override the {@code drawStack (Unit, int)} method.*/
public class StackableStatusEffect extends StatusEffect {
    /** The maximum number of overlays. */
    public int maxStacks = 1;
    /** Damage multipliers for each stack level. */
    public FloatSeq damageMultipliers = new FloatSeq();
    /** Health multipliers for each stack level. */
    public FloatSeq healthMultipliers = new FloatSeq();
    /** Movement speed multipliers for each stack level. */
    public FloatSeq speedMultipliers = new FloatSeq();
    /** Cooldown multipliers for each stack level. */
    public FloatSeq reloadMultipliers = new FloatSeq();
    /** Construction speed multipliers for each stack level. */
    public FloatSeq buildSpeedMultipliers = new FloatSeq();
    /** Acceleration multipliers for each stack level. */
    public FloatSeq dragMultipliers = new FloatSeq();
    /** The amount of damage when interacting with buffs for each level of the stack. */
    public FloatSeq transitionDamages = new FloatSeq();
    /** Failure to shoot for every stack level. */
    public BoolSeq disarmedArray = new BoolSeq();
    /** Damage per tick for each stack level. */
    public FloatSeq damageArray = new FloatSeq();
    /** List of individual overlay effect. */
    public Seq<StatusEffect> stacks;

    private StackableStatusEffectValue displayer;
    private final AStats aStats=new AStats(){
        @Override
        public void display(Table table) {
            if (displayer==null){
                setupDisplayer();
            }
            displayer.display(table);
        }
    };

    public void setupDisplayer() {
        displayer = new StackableStatusEffectValue(this);
    }

    public StackableStatusEffect(String name) {
        super(name);
        stats=aStats.copy(stats);
    }

    /** @implNote, when initialized, creates a status effect for each overlay level. */
    @Override
    public void init() {
        super.init();
        stacks = new Seq<>(maxStacks);
        StackableStatusEffect base = this;
        Vars.content.setCurrentMod(ModVars.modInfo);
        for(int i = 0; i < maxStacks; i++) {
            final int finalI = i;
            stacks.add(new StatusEffect(name + finalI){{
                //параметры эффекта
                this.damageMultiplier = (base.damageMultipliers.size == base.maxStacks) ?
                base.damageMultipliers.get(finalI) :
                base.damageMultiplier;
                this.healthMultiplier = (base.healthMultipliers.size == base.maxStacks) ?
                base.healthMultipliers.get(finalI) :
                base.healthMultiplier;
                this.speedMultiplier = (base.speedMultipliers.size == base.maxStacks) ?
                base.speedMultipliers.get(finalI) :
                base.speedMultiplier;
                this.reloadMultiplier = (base.reloadMultipliers.size == base.maxStacks) ?
                base.reloadMultipliers.get(finalI) :
                base.reloadMultiplier;
                this.buildSpeedMultiplier = (base.buildSpeedMultipliers.size == base.maxStacks) ?
                base.buildSpeedMultipliers.get(finalI) :
                base.buildSpeedMultiplier;
                this.dragMultiplier = (base.dragMultipliers.size == base.maxStacks) ?
                base.dragMultipliers.get(finalI) :
                base.dragMultiplier;
                this.transitionDamage = (base.transitionDamages.size == base.maxStacks) ?
                base.transitionDamages.get(finalI) :
                base.transitionDamage;
                this.disarm = (base.disarmedArray.size == base.maxStacks) ?
                base.disarmedArray.get(finalI) :
                base.disarm;
                this.damage = (base.damageArray.size == base.maxStacks) ?
                base.damageArray.get(finalI) :
                base.damage;
                base.opposites.each(this::opposite);
                base.affinities.each(e -> this.affinity(e, transitions.get(e)));
                // visual parameters
                this.localizedName = base.localizedName;
                this.description = base.description;
                this.details = base.details;
            }
                @Override
                public boolean isHidden() { return true; }
                // redirecting calls to various methods effect status
                // to method calls of the base (given) status effect
                @Override
                public void update(Unit unit, float time) {
                    super.update(unit, time);
                    updateStack(unit, time, finalI+1);
                }
                @Override
                public void draw(Unit unit) {
                    drawStack(unit, finalI+1);
                }
                @Override
                public boolean reactsWith(StatusEffect effect) {
                    return base.reactsWith(effect);
                }

                @Override
                public boolean applyTransition(Unit unit, StatusEffect to, StatusEntry entry, float time){
                    return base.applyTransition(unit, to, entry, time);
                }

                @Override
                public String emoji() { return base.emoji(); }
                @Override
                public TechTree.TechNode node() { return base.node(); }
                /*
                @Override
                public void display(Table table) { base.display(table); }*/
            });
        }
        Vars.content.setCurrentMod(null);
    }

    /** Called to handle affected units. Transfers
     * information about the remaining time and the degree of overlap. */
    public void updateStack(Unit unit, float time, int stackCount) { }
    /** Called to render the units on which the effect is applied. Transfers
     * information about the remaining time. */
    public void drawStack(Unit unit, int stackCount) { }

    /** Applies an effect to a unit if the effect has not been set yet, otherwise
     * increases the number of overlays of this effect by 1. */
    public void apply(Unit unit, float duration) {
        // We have to use reflection, since there are several classes that describe
        // units (at least - UnitEntity and UnitMech), and these classes have nothing in common
        // an interface that would have a statuses field (well, or I'm blind).
        Field fieldStatuses;
        Seq<StatusEntry> statuses = null;
        try {
            fieldStatuses = unit.getClass().getField("statuses");            
            fieldStatuses.setAccessible(true);
            statuses = (Seq<StatusEntry>)fieldStatuses.get(unit);
        } catch (NoSuchFieldException e) {
            // The field may not appear, then we just think that the unit does not support the effects
            return;
        } catch (Throwable e) {
            Log.err(e);
        }
        boolean foundStandard = statuses.remove(e -> e.effect == this);
        StackableStatusEntry prev = (StackableStatusEntry)statuses.find(e -> 
            (e instanceof StackableStatusEntry) && ((StackableStatusEntry)e).baseEffect == this);
        if(prev == null) {
            applyEffect(statuses, unit, duration, foundStandard ? 2 : 1);
        } else {
            prev.stack();
        }
    }
    protected void applyEffect(Seq<StatusEntry> statuses, Unit unit, float duration, int stackCount) {
        if (unit.isImmune(this)) return;
        if (state.isCampaign()) {
            unlock();
        }
        if (statuses.size > 0) {
            for (int i = 0; i < statuses.size; i++) {
                StatusEntry entry = statuses.get(i);
                if (entry.effect.reactsWith(this)) {
                    /*StatusEntry.tmp.effect = entry.effect;
                    entry.effect.applyTransition(unit, this,entry, entry.time);
                    entry.time = StatusEntry.tmp.time;
                    if (StatusEntry.tmp.effect != entry.effect) {
                        entry.effect = StatusEntry.tmp.effect;
                    }*/
                    return;
                }
            }
        }
        StackableStatusEntry entry = new StackableStatusEntry(this, stackCount, duration);
        statuses.add(entry);
    }
}
