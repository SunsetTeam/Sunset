package sunset.ai.weapon;

import arc.math.geom.Position;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;

/**
 * AI, которое управляет одним орудием, а не всей боевой единицой.
 * @implNote реализация данного AI должна не только находить цель, но и
 * самостоятельно наводить орудие и проводить проверку на корректность цели!
 * */
public abstract class WeaponAI {
    protected Unit unit;
    protected WeaponMount mount;

    public boolean isShooting() { return mount.shoot; }
    public float aimX() { return mount.aimX; }
    public float aimY() { return mount.aimY; }

    protected float weaponX() { return mount.weapon.x + unit.x; }
    protected float weaponY() { return mount.weapon.y + unit.y; }
    protected float range() { return mount.weapon.bullet.range(); }

    public boolean isUnset() { return unit == null || mount == null; }
    public void set(Unit unit, WeaponMount mount) {
        this.mount = mount;
        this.unit = unit;
    }

    public abstract void update();
    protected abstract void aim(Position target);
}
