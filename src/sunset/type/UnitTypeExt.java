package sunset.type;

import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.world.meta.Stat;
import mindustry.world.meta.values.WeaponListValue;
import sunset.world.meta.values.WeaponListValueExt;

/** UnitType, который вызывает update() и draw() у орудий, если таковые есть. */
public class UnitTypeExt extends UnitType {
    public UnitTypeExt(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        if(weapons.any()) {
            stats.remove(Stat.weapons);
            stats.add(Stat.weapons, new WeaponListValueExt(this, weapons));
        }
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);
        for(WeaponMount mount : unit.mounts){
            Weapon weapon = mount.weapon;
            if(weapon instanceof UpdateDrawWeapon) {
                ((UpdateDrawWeapon)weapon).update(mount, unit);
            }
        }
    }

    @Override
    public void drawWeapons(Unit unit) {
        applyColor(unit);

        for(WeaponMount mount : unit.mounts) {
            Weapon weapon = mount.weapon;
            if (weapon instanceof UpdateDrawWeapon) {
                ((UpdateDrawWeapon) weapon).preDraw(mount, unit);
            }
            if(((UpdateDrawWeapon)weapon).useDefaultDraw) {
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
                    if (!weapon.top) Draw.z(z - outlineSpace);

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
            if(weapon instanceof UpdateDrawWeapon) {
                ((UpdateDrawWeapon)weapon).postDraw(mount, unit);
            }
        }
        Draw.reset();
    }
}
