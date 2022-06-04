package sunset.content.codegen

import mindustry.entities.Predict
import mindustry.entities.units.AIController
import mindustry.gen.Unit
import sunset.annotations.SnAnnotations.BothExtends
import sunset.type.weapons.WeaponExt
import sunset.utils.Utils

@BothExtends(
    classes = ["mindustry.ai.types.FlyingAI", "mindustry.ai.types.GroundAI"],
    classNames = ["FlyingWeaponAI_", "GroundWeaponAI_"],
    packageName = "sunset.ai"
)
private open class KotlinUnitWeaponAI : AIController() {
    override fun updateWeapons() {
        val rotation = unit.rotation - 90
        val retarget = retarget()

        if (retarget) {
            target = findTarget(unit.x, unit.y, unit.range(), unit.type.targetAir, unit.type.targetGround)
        }
        if (invalid(target)) {
            target = null
        }

        unit.isShooting(false)
        for (mount in unit.mounts) {
            val weapon = mount.weapon
            val mountX = Utils.mountX(unit, mount)
            val mountY = Utils.mountY(unit, mount)
            if (weapon is WeaponExt) {
                val ai = weapon.ai
                unit.isShooting(unit.isShooting or ai.update(unit, mount))
                continue
            }

            if (retarget) {
                mount.target = findTarget(mountX, mountY, weapon.bullet.range(), weapon.bullet.collidesAir, weapon.bullet.collidesGround)
            }
            var shoot = false
            if (mount.target != null) {
                shoot = mount.target.within(mountX, mountY, weapon.bullet.range()) && shouldShoot()
                val to = Predict.intercept(unit, mount.target, weapon.bullet.speed)
                mount.aimX = to.x
                mount.aimY = to.y
            }
            mount.shoot = shoot
            mount.rotate = shoot
            unit.isShooting(unit.isShooting or shoot)
            if (shoot) {
                unit.aimX = mount.aimX
                unit.aimY = mount.aimY
            }
        }
    }
}