package sunset.type.weapons;

import arc.util.Time;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import sunset.ai.weapon.BaseWeaponAI;
import sunset.ai.weapon.WeaponAI;

/** Weapon with improvements: with its own AI, with reload processing, with the search for a mirror weapon. */
public class WeaponExt extends CustomWeapon {
    public WeaponAI ai = new BaseWeaponAI();

    public WeaponExt(String name) {
        super(name);
    }
    public WeaponExt() {
    }
    /** Returns the weapon that is mirrored to the given one. If mirror == false, then it will return null. */
    WeaponExt mirrorWeapon = this; //this - ещё не искалось, null - не было найдено
    public WeaponExt mirrorWeapon(Unit unit) {
        if(mirrorWeapon == this) {
            if(mirror) {
                for (WeaponMount mount : unit.mounts) {
                    if(mount.weapon == this) {
                        mirrorWeapon = (WeaponExt)unit.mounts[mount.weapon.otherSide].weapon;
                        return mirrorWeapon;
                    }
                }
            }
            mirrorWeapon = null;
        }
        return mirrorWeapon;
    }
    /** Is this weapon ready to fire? Considers the alternate variable. */
    protected long lastShootTime = 0;
    protected boolean readyToShoot(Unit unit) {
        return (Time.millis() - lastShootTime > (reload / 60 * 1000))
                && (!mirror || !alternate || Time.millis() - mirrorWeapon(unit).lastShootTime > reload / 60 * 500);
    }
    /** Indicates that the given weapon has fired. */
    protected void setShoot() {
        lastShootTime = Time.millis();
    }
}
