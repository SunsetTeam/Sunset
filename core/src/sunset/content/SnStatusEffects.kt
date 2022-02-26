package sunset.content

import arc.Core
import arc.graphics.Color
import arc.graphics.g2d.Draw
import mindustry.Vars
import mindustry.content.Fx
import mindustry.content.StatusEffects
import mindustry.ctype.ContentList
import mindustry.graphics.Layer
import mindustry.graphics.Pal
import mindustry.type.StatusEffect
import sunset.graphics.SnPal
import sunset.type.PublicStatusEffect
import sunset.type.StackableStatusEffect
import sunset.type.StackableStatusEffect.*
import sunset.utils.kotlin.color
import kotlin.Unit as KtUnit

class SnStatusEffects : ContentList {
    override fun load() {
        //region common
        frostbite = statusEffect("frostbite") {
            color = "6ecdec".color()
            damage = 0.17f
            speedMultiplier = 0.4f
            healthMultiplier = 0.7f
            effect = Fx.freezing
            transitionDamage = 18f
            init {
                opposite(StatusEffects.melting, StatusEffects.burning)
                affinity(StatusEffects.blasted) { unit, cur, time ->
                    unit.damagePierce(transitionDamage)
                    cur[frostbite] = time
                }
            }
        }
        stun = statusEffect("stun") {
            color = "392f17".color()
            speedMultiplier = 0f
            disarm = true
        } //no sprite
        starBuff = statusEffect("star-buff") {
            color = Pal.surge
            speedMultiplier = 1.3f
            healthMultiplier = 1.15f
            damageMultiplier = 1.1f
            reloadMultiplier = 1.1f
        }
        galaxyDebuff = statusEffect("galaxy-debuff") {
            speedMultiplier = 0.8f
            damageMultiplier = 0.9f
            reloadMultiplier = 0.8f
        } //no sprite
        universityLaserSlow = statusEffect("universityLaserSlow") {
            speedMultiplier = 0.1f
            dragMultiplier = 0.3f
        }
        radiation = statusEffect("radiation") {
            speedMultiplier = 0.5f
            damageMultiplier = 0.6f
            reloadMultiplier = 0.4f
            damageMultiplier = 10f;
        } //no sprite
        electricalShort = statusEffect("electric-short") {
            effectChance = 100f
            speedMultiplier = 0f
            disarm = true
            color = "0AFEFF".color()
            reloadMultiplier = 0.3f
            effect = Fx.freezing
            init {
                opposite(StatusEffects.shocked)
                affinity(StatusEffects.wet) { unit, result, time ->
                    unit.damagePierce(unit.health / 4)
                    result.set(reloading, 300f)
                }
                affinity(StatusEffects.freezing) { unit, result, time ->
                    unit.damagePierce(unit.health / 2)
                    result.set(reloading, 600f)
                }
            }
        }
        reloading = statusEffect("reboot") {
            speedMultiplier = 0f
            disarm = true
            color = "047070".color()
        }
        viscous = statusEffect("viscous") {
            color = Color.valueOf("721A1A")
            speedMultiplier = 0.94f
            effect = SnFx.viscous
            effectChance = 0.09f

        } //no sprite
        inferno = statusEffect("inferno") {
            color = SnPal.redfire1
            effect = SnFx.redFlame
            effectChance = 0.3f
            speedMultiplier = 1.1f
            reloadMultiplier = 0.6f
            healthMultiplier = 0.7f
            damageMultiplier = 0.7f
            init { opposite(frostbite) }
        } //no sprite
        //endregion common
        //region only reactive
        molecula = statusEffect("molecula") {
            color = "3DD957".color()
            permanent = true
            effect = Fx.heal
            init {
                opposite(StatusEffects.freezing, frostbite)
                affinity(StatusEffects.overclock) { unit, result, time ->
                    unit.damagePierce(600f)
                    result.set(molecula, 300f)
                }
                affinity(StatusEffects.overdrive) { unit, result, time ->
                    unit.damagePierce(600f)
                    result.set(molecula, 300f)
                }
                affinity(StatusEffects.shielded) { unit, result, time ->
                    unit.damagePierce(450f)
                    result.set(molecula, 300f)
                }
                affinity(StatusEffects.blasted) { unit, result, time ->
                    unit.damagePierce(30f)
                    result.set(molecula, 300f)
                }
                affinity(StatusEffects.shocked) { unit, result, time ->
                    unit.damagePierce(30f)
                    result.set(molecula, 300f)
                }
            }
        }
        //endregion only reactive
        //region stackable
        overheat = stackableStatusEffect("overheat") {
            color = "FF30000".color()
            stackEntries(
                stackEntry(health=0.99f,speed=0.99f),
                stackEntry(health=0.98f,speed=0.99f),
                stackEntry(health=0.97f,speed=0.99f),
                stackEntry(health=0.96f,speed=0.99f),
                stackEntry(health=0.95f,speed=0.98f),
                stackEntry(health=0.94f,speed=0.98f),
                stackEntry(health=0.92f,speed=0.98f),
                stackEntry(health=0.90f,speed=0.98f),
                stackEntry(health=0.86f,speed=0.97f),
                stackEntry(health=0.84f,speed=0.96f),
                stackEntry(health=0.82f,speed=0.96f),
                stackEntry(health=0.79f,speed=0.96f),
                stackEntry(health=0.76f,speed=0.95f),
                stackEntry(health=0.73f,speed=0.94f),
                stackEntry(health=0.70f,speed=0.93f),
                stackEntry(health=0.65f,speed=0.93f),
                stackEntry(health=0.60f,speed=0.93f),
                stackEntry(health=0.55f,speed=0.92f),
                stackEntry(health=0.47f,speed=0.91f),
                stackEntry(health=0.39f,speed=0.90f),
                null
            )
            healthMultiplier = 0.99f
            speedMultiplier = 0.99f
            color = Color.red


            var draw = false
            stacksDrawer = StacksDrawer { unit, stackIndex ->
                if (draw) return@StacksDrawer
                Vars.renderer.effectBuffer.begin()
                draw = true
                unit.draw()
                draw = false
                Vars.renderer.effectBuffer.end()
                Draw.z((if (unit.isFlying) Layer.flyingUnit else Layer.groundUnit) + 1)
                Draw.color(color, (stackIndex+1f) / maxStacks())
                val wrap = Draw.wrap(Vars.renderer.effectBuffer.texture)
                wrap.flip(false, true)
                Draw.rect(wrap, Core.camera.position.x, Core.camera.position.y, Core.camera.width, Core.camera.height)
                //Vars.renderer.effectBuffer.blit(Shaders.screenspace);
                Draw.color()
            }

        }
        incineration = stackableStatusEffect("incineration") {
            color = "BD4E17".color()
            stackEntries(
                stackEntry(health = 0.97f, speed = 0.97f, damage = 1f),
                stackEntry(health = 0.96f, speed = 0.97f, damage = 3f),
                stackEntry(health = 0.95f, speed = 0.97f, damage = 6f),
                stackEntry(health = 0.94f, speed = 0.97f, damage = 9f),
                stackEntry(health = 0.93f, speed = 0.98f, damage = 10f),
                stackEntry(health = 0.92f, speed = 0.95f, damage = 13f),
                stackEntry(health = 0.88f, speed = 0.95f, damage = 16f),
                stackEntry(health = 0.86f, speed = 0.95f, damage = 19f),
                stackEntry(health = 0.83f, speed = 0.974f, damage = 22f),
                stackEntry(health = 0.80f, speed = 0.93f, damage = 25f),
                stackEntry(health = 0.79f, speed = 0.93f, damage = 28f),
                stackEntry(health = 0.75f, speed = 0.93f, damage = 30f),
                stackEntry(health = 0.71f, speed = 0.93f, damage = 33f),
                stackEntry(health = 0.67f, speed = 0.92f, damage = 35f),
                stackEntry(health = 0.60f, speed = 0.92f, damage = 38f),
                stackEntry(health = 0.55f, speed = 0.91f, damage = 40f),
                stackEntry(health = 0.50f, speed = 0.91f, damage = 43f),
                stackEntry(health = 0.39f, speed = 0.91f, damage = 45f),
                stackEntry(health = 0.21f, speed = 0.90f, damage = 46f),
                stackEntry(health = 0.11f, speed = 0.89f, damage = 48f),
                stackEntry(health = 0.04f, speed = 0.85f, damage = 50f),
                null
            )
            healthMultiplier = 0.97f
            speedMultiplier = 0.97f
            damage = 5f
            color = Color.black

            var draw = true
            stacksDrawer = StacksDrawer { unit, stackIndex ->
                if (draw) return@StacksDrawer
                Vars.renderer.effectBuffer.begin()
                draw = true
                unit.draw()
                draw = false
                Vars.renderer.effectBuffer.end()
                Draw.z((if (unit.isFlying) Layer.flyingUnit else Layer.groundUnit) + 1)
                Draw.color(color, (stackIndex+1f) / maxStacks())
                val wrap = Draw.wrap(Vars.renderer.effectBuffer.texture)
                wrap.flip(false, true)
                Draw.rect(wrap, Core.camera.position.x, Core.camera.position.y, Core.camera.width, Core.camera.height)
                //Vars.renderer.effectBuffer.blit(Shaders.screenspace);
                Draw.color()
            }
        }
        //endregion stackable
    }

    companion object {
        //common
        lateinit var frostbite: StatusEffect
        lateinit var stun: StatusEffect
        lateinit var starBuff: StatusEffect
        lateinit var galaxyDebuff: StatusEffect
        lateinit var universityLaserSlow: StatusEffect
        lateinit var electricalShort: StatusEffect
        lateinit var reloading: StatusEffect
        lateinit var viscous: StatusEffect
        lateinit var inferno: StatusEffect
        lateinit var radiation: StatusEffect

        //only reactive
        lateinit var molecula: StatusEffect

        //stackable
        lateinit var overheat: StatusEffect
        lateinit var incineration: StatusEffect
    }
}

private fun stackableStatusEffect(name: String, block: StackableStatusEffect.() -> KtUnit): StackableStatusEffect {
    val effect = StackableStatusEffect(name)
    effect.block();
    return effect;
}

private fun statusEffect(name: String, block: PublicStatusEffect.() -> KtUnit): StatusEffect {
    val effect = PublicStatusEffect(name)
    effect.block();
    return effect;
}

private fun StackableStatusEffect.stackEntryFull(
    damageMultiplier: Float = unset,
    healthMultiplier: Float = unset,
    speedMultiplier: Float = unset,
    reloadMultiplier: Float = unset,
    buildSpeedMultiplier: Float = unset,
    dragMultiplier: Float = unset,
    transitionDamage: Float = unset
): StackEntry {
    return StackEntry(damageMultiplier, healthMultiplier, speedMultiplier, reloadMultiplier, buildSpeedMultiplier, dragMultiplier, transitionDamage)
}

private fun StackableStatusEffect.stackEntry(
    damage: Float = unset,
    health: Float = unset,
    speed: Float = unset,
    reload: Float = unset,
    buildSpeed: Float = unset,
    drag: Float = unset,
    transitionDamage: Float = unset
): StackEntry {
    return StackEntry(damage, health, speed, reload, buildSpeed, drag, transitionDamage)
}