package sunset.entities.abilities

import arc.util.Time
import mindustry.entities.Effect
import mindustry.entities.Units
import mindustry.entities.abilities.Ability
import mindustry.gen.Unit
import mindustry.type.StatusEffect
import kotlin.math.max

class EffectLowHPAbility(private val threshold: Float,
                         private val delay: Float,
                         private val range: Float,
                         private val duration: Float,
                         private val effect: StatusEffect,
                         private val fx: Effect) : Ability() {
    private var lastHealth = -1f
    private var lastActvation = 0f
    private var lower = false
    private fun checkUnit(unit: Unit) = lastHealth != -1f && // last health is set...
                                        unit.health() < lastHealth && // and it's smaller than current health (unit was hit)
                                        unit.healthf() < threshold && // and it's smaller than activation threshold
                                        !lower && // and effect wasn't activated
                                        delay < Time.time - lastActvation

    override fun update(unit: Unit) {
        if (checkUnit(unit)) {
            Units.nearbyEnemies(unit.team, unit.x, unit.y, range) { it.apply(effect, duration * max(0f, (1 - unit.dst(it) / range))) }
            fx.at(unit.x, unit.y, range)
            lastActvation = Time.time
            lower = true
        }
        if(unit.healthf() >= threshold) lower = false
        lastHealth = unit.health()
    }
}