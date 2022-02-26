package sunset.entities.abilities

import arc.Core
import mindustry.type.StatusEffect
import mindustry.entities.abilities.Ability
import mindustry.world.meta.StatValue
import sunset.content.SnFx
import arc.func.Cons
import arc.scene.ui.layout.Table
import arc.util.Strings
import arc.util.Time
import mindustry.Vars
import mindustry.content.StatusEffects
import mindustry.entities.Units
import mindustry.gen.Unit

class StatusFieldAbility(private val allyEffect: StatusEffect,
                         private val enemyEffect: StatusEffect,
                         private val reload: Float,
                         private val range: Float) : Ability(), StatValue {
    private var timer = 0f

    override fun update(unit: Unit) {
        timer += Time.delta
        if (timer < reload) return
        SnFx.statusField.at(unit.x, unit.y, range)
        Units.nearby(null, unit.x, unit.y, range) {
            it.apply(if (it.team.isEnemy(unit.team)) enemyEffect else allyEffect, reload)
        }
        timer = 0f
    }

    override fun display(table: Table) {
        table.row()
        table.left().defaults().padLeft(6f).left()
        table.row()
        table.add(Core.bundle.format("ability.statusfield-summary",
                  Strings.autoFixed(range / Vars.tilesize, 1)))
        if (allyEffect !== StatusEffects.none) {
            table.row()
            table.add(Core.bundle.format("ability.statusfield-ally",
                      allyEffect.emoji(),
                      allyEffect.localizedName))
        }
        if (enemyEffect !== StatusEffects.none) {
            table.row()
            table.add(Core.bundle.format("ability.statusfield-enemy",
                      enemyEffect.emoji(),
                      enemyEffect.localizedName))
        }
    }
}