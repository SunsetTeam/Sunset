package sunset.type.unitTypes;

import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.ObjectSet;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.type.Weapon;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatValue;
import sunset.type.weapons.*;
import sunset.world.meta.SnStatValues;

/**
 * UnitType, which has some changes.
 */
public class UnitTypeExt extends SnUnitType {
    public UnitTypeExt(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        // We pass the filling of information about the abilities to the abilities themselves, if it supports it
        if (abilities.any()) {
            stats.remove(Stat.abilities);
            ObjectSet<String> unique = new ObjectSet<>();

            for (Ability a : abilities) {
                if (unique.add(a.localized())) {
                    if (a instanceof StatValue) {
                        stats.add(Stat.abilities, (StatValue) a);
                    } else {
                        stats.add(Stat.abilities, a.localized());
                    }
                }
            }
        }
        // We transfer the filling of information about the instrument to the instrument itself, if it supports
        if (weapons.any()) {
            stats.remove(Stat.weapons);
            stats.add(Stat.weapons, SnStatValues.weaponListExt(this, weapons));
        }
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);
        /*// Call update () on the implements that support it
        for (WeaponMount mount : unit.mounts) {
            Weapon weapon = mount.weapon;
            if (weapon instanceof CustomWeapon customWeapon) {
                customWeapon.update( unit,mount);
            }
        }*/
    }

    @Override
    public void drawWeapons(Unit unit) {
        applyColor(unit);
        // We transfer the rendering of the weapon to the weapon itself, if it supports it
        for (WeaponMount mount : unit.mounts) {
            Weapon weapon = mount.weapon;
            if (weapon instanceof CustomWeapon customWeapon) {
                customWeapon.preDraw(mount, unit);
            }
            if (!(weapon instanceof CustomWeapon customWeapon) || customWeapon.customDraw()) {
                float rotation = unit.rotation - 90;
                float weaponRotation = rotation + (weapon.rotate ? mount.rotation : 0);
                float recoil = -((mount.reload) / weapon.reload * weapon.recoil);
                float wx = unit.x + Angles.trnsx(rotation, weapon.x, weapon.y) + Angles.trnsx(weaponRotation, 0, recoil),
                        wy = unit.y + Angles.trnsy(rotation, weapon.x, weapon.y) + Angles.trnsy(weaponRotation, 0, recoil);

                if (weapon.shadow > 0) {
                    Drawf.shadow(wx, wy, weapon.shadow);
                }

                if (weapon.outlineRegion.found()) {
                    float z = Draw.z();
                    if (!weapon.top) Draw.z(z - outlineRadius);

                    Draw.rect(weapon.outlineRegion,
                            wx, wy,
                            weapon.outlineRegion.width * Draw.scl * -Mathf.sign(weapon.flipSprite),
                            weapon.region.height * Draw.scl,
                            weaponRotation);

                    Draw.z(z);
                }

                Draw.rect(weapon.region,
                        wx, wy,
                        weapon.region.width * Draw.scl * -Mathf.sign(weapon.flipSprite),
                        weapon.region.height * Draw.scl,
                        weaponRotation);

                if (weapon.heatRegion.found() && mount.heat > 0) {
                    Draw.color(weapon.heatColor, mount.heat);
                    Draw.blend(Blending.additive);
                    Draw.rect(weapon.heatRegion,
                            wx, wy,
                            weapon.heatRegion.width * Draw.scl * -Mathf.sign(weapon.flipSprite),
                            weapon.heatRegion.height * Draw.scl,
                            weaponRotation);
                    Draw.blend();
                    Draw.color();
                }
            }
            if (weapon instanceof CustomWeapon customWeapon) {
                customWeapon.postDraw(mount, unit);
            }
        }
        Draw.reset();
    }
}
