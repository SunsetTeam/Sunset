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

class OverdriveAbility(private val intensity: Float,
                       private val reload: Float,
                       private val range: Float) : Ability(), StatValue {
    private var timer = 0f

    override fun update(unit: Unit) {
        timer += Time.delta
        if (timer < reload) return
        SnFx.statusField.at(unit.x, unit.y, range)
        Units.nearbyBuildings(unit.x, unit.y, range) { it.applyBoost(1 + intensity, reload) }
        timer = 0f
    }

    override fun display(table: Table) {
        table.row()
        table.left().defaults().padLeft(6f).left()
        table.row()
        table.add(Core.bundle.format("ability.overdrivefield-summary",
                                     Strings.autoFixed(range / Vars.tilesize, 1),
                                     (intensity * 100).toInt()))
    }
}