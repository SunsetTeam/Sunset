package sunset.type;

import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;

public interface CustomWeapon {
    default void update(WeaponMount mount, Unit unit) { }

    default void preDraw(WeaponMount mount, Unit unit) { }

    default void postDraw(WeaponMount mount, Unit unit) { }

    default boolean useDefaultDraw() {
        return !(this instanceof CustomDrawWeapon);
    }

    interface CustomDrawWeapon { }
}
