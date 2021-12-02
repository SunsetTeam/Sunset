package sunset.type.weapons;

import arc.util.Time;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.type.Weapon;
import sunset.ai.weapon.BaseWeaponAI;
import sunset.ai.weapon.WeaponAI;

/** Орудие с улучшениями: с собственным AI, с обработкой перезарядки, с поиском зеркального орудия. */
public class WeaponExt extends SnWeapon {
    public WeaponAI ai = new BaseWeaponAI();
    public WeaponExt(String name) {
        super(name);
    }
    WeaponExt mirrorWeapon = this; //this - ещё не искалось, null - не было найдено
    /** Возвращает орудие, зеркальное данному. Если mirror==false, то вернёт null. */
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
    protected long lastShootTime = 0;
    /** Готово ли данное орудие стрелять. Учитывает alternate переменную. */
    protected boolean readyToShoot(Unit unit) {
        return (Time.millis() - lastShootTime > (reload / 60 * 1000))
                && (!mirror || !alternate || Time.millis() - mirrorWeapon(unit).lastShootTime > reload / 60 * 500);
    }
    /** Оповещает о том, что данное орудие выстрелило. */
    protected void setShoot() {
        lastShootTime = Time.millis();
    }
    public void draw(Weapon weapon) {
    }
}
