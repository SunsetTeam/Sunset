package sunset.entities.abilities;

import arc.Core;
import arc.scene.ui.layout.Cell;
import arc.scene.ui.layout.Table;
import arc.util.Strings;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.StatusEffects;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mindustry.ui.Cicon;
import mindustry.world.meta.StatValue;
import sunset.Utils;

public class StatusFieldAbility extends Ability implements StatValue {
    public StatusEffect allyEffect, enemyEffect;
    public float reload, range;

    protected float timer;

    public StatusFieldAbility(StatusEffect allyEffect, StatusEffect enemyEffect, float reload, float range){
        this.reload = reload;
        this.range = range;
        this.allyEffect = allyEffect;
        this.enemyEffect = enemyEffect;
    }

    @Override
    public void update(Unit unit){
        timer += Time.delta;

        if(timer >= reload){
            //TODO: эффект активации
            Utils.unitsNearby(unit.x, unit.y, range, other -> {
                if (other.team.isEnemy(unit.team)) {
                    other.apply(enemyEffect, reload);
                } else {
                    other.apply(allyEffect, reload);
                }
            });
            timer = 0f;
        }
    }

    @Override
    public void display(Table table) {
        table.row();
        table.left().defaults().padLeft(6).left();

        table.row();
        table.add(Core.bundle.format("ability.statusfield-summary",
                Strings.autoFixed(range / Vars.tilesize, 1)));
        if(allyEffect != StatusEffects.none) {
            table.row();
            table.add(Core.bundle.format("ability.statusfield-ally",
                    allyEffect.emoji(),
                    allyEffect.localizedName));
        }
        if(enemyEffect != StatusEffects.none) {
            table.row();
            table.add(Core.bundle.format("ability.statusfield-enemy",
                    enemyEffect.emoji(),
                    enemyEffect.localizedName));
        }
    }
}
