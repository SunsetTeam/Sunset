package sunset.type;

import acontent.world.meta.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;
import mma.*;
import sunset.world.meta.values.*;

import static mindustry.Vars.*;

/**
 * Superimposed status effect. Has varying strength on
 * each stage of overlap tends to increase.
 * @implNote If the length of the array defining this or that parameter
 * the effect status does not match the {@code maxStacks} parameter, then
 * parameters from regular {@link StatusEffect} are used.
 * Not intended for reactive effects (what's the point of stacking them if they instantly disappear?)
 * @apiNote Use the {@code apply (Unit, float)} method to set the status of an effect.
 * Instead of {@code update (Unit, float)}, override the {@code updateStack (Unit, float, int)} method.
 * Instead of {@code draw (Unit)}, override the {@code drawStack (Unit, int)} method.
 */
public class StackableStatusEffect extends PublicStatusEffect{

    public static final float unset = Float.NEGATIVE_INFINITY;
    public final Seq<StackEntry> stackEntries = new Seq<>();
    private final Seq<StackableEntryStatusEffect> stacks = new Seq<>();

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
    public StacksDrawer stacksDrawer = null;
    public StacksUpdater stacksUpdater = null;
    private StackableStatusEffectValue statsViewer;
    private final AStats aStats = new AStats(){
        @Override
        public void display(Table table){
            if(statsViewer == null){
                setupDisplayer();
            }
            statsViewer.display(table);
        }
    };

    public StackableStatusEffect(String name){
        super(name);
        stats = aStats.copy(stats);
    }

    public int maxStacks(){
        return stacks.size;
    }

    public void stackEntries(StackEntry... stackEntries){
        this.stackEntries.set(stackEntries);
        if(stackEntries[stackEntries.length - 1] == null){
            this.stackEntries.size--;
        }
    }

    public StackEntry stackEntry(float damageMultiplier, float healthMultiplier, float speedMultiplier, float reloadMultiplier, float buildSpeedMultiplier, float dragMultiplier, float transitionDamage){
        return new StackEntry(damageMultiplier, healthMultiplier, speedMultiplier, reloadMultiplier, buildSpeedMultiplier, dragMultiplier, transitionDamage);
    }

    public StackEntry stackEntry(){
        return new StackEntry();
    }

    public void setupDisplayer(){
        statsViewer = new StackableStatusEffectValue(this);
    }

    /** @implNote, when initialized, creates a status effect for each overlay level. */
    @Override
    public void init(){
        super.init();

        for(StackEntry entry : stackEntries){
            entry.checkDefs(this);
        }
        Vars.content.setCurrentMod(ModVars.modInfo);
        for(int i = 0; i < stackEntries.size; i++){
            stacks.add(new StackableEntryStatusEffect(this, i));
        }
        Vars.content.setCurrentMod(null);
    }

    /**
     * Called to handle affected units. Transfers
     * information about the remaining time and the degree of overlap.
     */
    public final void updateStack(Unit unit, float time, int stackIndex){
        if(stacksUpdater != null) stacksUpdater.update(unit, time, stackIndex);
    }

    /**
     * Called to render the units on which the effect is applied. Transfers
     * information about the remaining time.
     */
    public final void drawStack(Unit unit, int stackIndex){
        if(stacksDrawer != null) stacksDrawer.draw(unit, stackIndex);
    }

    /**
     * Applies an effect to a unit if the effect has not been set yet, otherwise
     * increases the number of overlays of this effect by 1.
     */
    public void apply(Unit unit, float duration){
        Seq<StatusEntry> statuses = getStatusEntries(unit);

        boolean foundStandard = statuses.remove(e -> e.effect == this);

       /* StackableStatusEntry prev = (StackableStatusEntry)statuses.find(e ->
        (e instanceof StackableStatusEntry statusEntry) && statusEntry.baseEffect == this);
        if(prev == null){
            applyEffect(statuses, unit, duration, foundStandard ? 2 : 1);
        }else{
            prev.stack();
        }*/
    }

    public Seq<StatusEntry> getStatusEntries(Unit unit){
        Seq<StatusEntry> statuses;
        try{
            statuses = Reflect.get(unit, unit.getClass().getField("statuses"));
        }catch(NoSuchFieldException e){
            throw new RuntimeException("Conflicts with mod \"" + unit.type.minfo.mod.name + "\"(unit: " + unit.type.name + ")", e);
        }catch(Throwable e){
            throw new RuntimeException(e);
        }
        return statuses;
    }

    protected void applyEffect(Seq<StatusEntry> statuses, Unit unit, float duration, int stackCount){
        if(unit.isImmune(this)) return;
        if(state.isCampaign()){
            unlock();
        }
        if(statuses.size > 0){
            for(int i = 0; i < statuses.size; i++){
                StatusEntry entry = statuses.get(i);
                if(entry.effect.reactsWith(this)){
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
        /*StackableStatusEntry entry = new StackableStatusEntry(this, stackCount, duration);
        statuses.add(entry);*/
    }

    public StackableEntryStatusEffect stack(int index){
        return stacks.get(index);
    }

    @Override
    public boolean applyTransition(Unit unit, StatusEffect to, StatusEntry entry, float time){
        if(to instanceof StackableEntryStatusEffect child && child.parent == this){
            entry.set(child.next(), entry.time / 2f + time / 2f);
            return true;
        }
        return super.applyTransition(unit, to, entry, time);
    }

    public interface StacksUpdater{
        void update(Unit unit, float time, int stackIndex);
    }

    public interface StacksDrawer{
        void draw(Unit unit, int stackIndex);
    }

    private static final class StackableEntryStatusEffect extends StatusEffect{


        private final StackableStatusEffect parent;
        private final int index;

        public StackableEntryStatusEffect(StackableStatusEffect parent, int index){
            super(parent.name + index);
            this.parent = parent;
            this.index = index;
            //параметры эффекта
            parent.stackEntries.get(index).applyOn(this);
            this.parent.opposites.each(this::opposite);
            this.parent.affinities.each(e -> this.affinity(e, transitions.get(e)));
            // visual parameters
            this.localizedName = this.parent.localizedName;
            this.description = this.parent.description;
            this.details = this.parent.details;
            techNode=parent.techNode;
        }

        @Override
        public boolean isHidden(){
            return true;
        }

        // redirecting calls to various methods effect status
        // to method calls of the parent (given) status effect
        @Override
        public void update(Unit unit, float time){
            super.update(unit, time);
            parent.updateStack(unit, time, index);
        }

        @Override
        public void draw(Unit unit){
            parent.drawStack(unit, index);
        }

        @Override
        public boolean reactsWith(StatusEffect effect){
            return parent.reactsWith(effect);
        }

        @Override
        public boolean applyTransition(Unit unit, StatusEffect to, StatusEntry entry, float time){
            if(to == parent){
                entry.set(next(), time / 2f + entry.time / 2f);
                return true;
            }
            return parent.applyTransition(unit, to, entry, time);
        }

        @Override
        public String emoji(){
            return parent.emoji();
        }

        public StackableEntryStatusEffect next(){
            return parent.stack(Math.min(parent.maxStacks() - 1, index + 1));
        }
    }

    public static class StackEntry{
        public float damageMultiplier = unset;
        public float healthMultiplier = unset;
        public float speedMultiplier = unset;
        public float reloadMultiplier = unset;
        public float buildSpeedMultiplier = unset;
        public float dragMultiplier = unset;
        public float transitionDamage = unset;
        public DisarmState disarm = DisarmState.enabled;

        public StackEntry(){
        }

        public StackEntry(float damageMultiplier, float healthMultiplier, float speedMultiplier, float reloadMultiplier, float buildSpeedMultiplier, float dragMultiplier, float transitionDamage){
            this.damageMultiplier = damageMultiplier;
            this.healthMultiplier = healthMultiplier;
            this.speedMultiplier = speedMultiplier;
            this.reloadMultiplier = reloadMultiplier;
            this.buildSpeedMultiplier = buildSpeedMultiplier;
            this.dragMultiplier = dragMultiplier;
            this.transitionDamage = transitionDamage;
        }

        public void checkDefs(StackableStatusEffect effect){
            if(damageMultiplier == unset) damageMultiplier = effect.damageMultiplier;
            if(healthMultiplier == unset) healthMultiplier = effect.healthMultiplier;
            if(speedMultiplier == unset) speedMultiplier = effect.speedMultiplier;
            if(reloadMultiplier == unset) reloadMultiplier = effect.reloadMultiplier;
            if(buildSpeedMultiplier == unset) buildSpeedMultiplier = effect.buildSpeedMultiplier;
            if(dragMultiplier == unset) dragMultiplier = effect.dragMultiplier;
            if(transitionDamage == unset) transitionDamage = effect.transitionDamage;
            if(disarm.unset()) disarm = effect.disarm ? DisarmState.enabled : DisarmState.disabled;
        }

        public void applyOn(StackableEntryStatusEffect effect){
            effect.damageMultiplier = damageMultiplier;
            effect.healthMultiplier = healthMultiplier;
            effect.speedMultiplier = speedMultiplier;
            effect.reloadMultiplier = reloadMultiplier;
            effect.buildSpeedMultiplier = buildSpeedMultiplier;
            effect.dragMultiplier = dragMultiplier;
            effect.transitionDamage = transitionDamage;
            effect.disarm = disarm.enabled();
        }

        public StackEntry damageMultiplier(float damageMultiplier){
            this.damageMultiplier = damageMultiplier;
            return this;
        }

        public StackEntry healthMultiplier(float healthMultiplier){
            this.healthMultiplier = healthMultiplier;
            return this;
        }

        public StackEntry speedMultiplier(float speedMultiplier){
            this.speedMultiplier = speedMultiplier;
            return this;
        }

        public StackEntry reloadMultiplier(float reloadMultiplier){
            this.reloadMultiplier = reloadMultiplier;
            return this;
        }

        public StackEntry buildSpeedMultiplier(float buildSpeedMultiplier){
            this.buildSpeedMultiplier = buildSpeedMultiplier;
            return this;
        }

        public StackEntry dragMultiplier(float dragMultiplier){
            this.dragMultiplier = dragMultiplier;
            return this;
        }

        public StackEntry transitionDamage(float transitionDamage){
            this.transitionDamage = transitionDamage;
            return this;
        }

        public StackEntry disarm(boolean disarm){
            this.disarm = disarm ? DisarmState.enabled : DisarmState.disabled;
            return this;
        }


        public StackEntry damage(float damageMultiplier){
            this.damageMultiplier = damageMultiplier;
            return this;
        }

        public StackEntry health(float healthMultiplier){
            this.healthMultiplier = healthMultiplier;
            return this;
        }

        public StackEntry speed(float speedMultiplier){
            this.speedMultiplier = speedMultiplier;
            return this;
        }

        public StackEntry reload(float reloadMultiplier){
            this.reloadMultiplier = reloadMultiplier;
            return this;
        }

        public StackEntry buildSpeed(float buildSpeedMultiplier){
            this.buildSpeedMultiplier = buildSpeedMultiplier;
            return this;
        }

        public StackEntry drag(float dragMultiplier){
            this.dragMultiplier = dragMultiplier;
            return this;
        }

        enum DisarmState{
            unset, enabled, disabled;

            public boolean enabled(){
                return this == enabled;
            }

            public boolean disabled(){
                return this == disabled;
            }

            public boolean unset(){
                return this == unset;
            }
        }
    }
}
