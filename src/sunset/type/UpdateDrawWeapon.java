package sunset.type;

import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;

public interface UpdateDrawWeapon {
    void update(WeaponMount mount, Unit unit);
    void preDraw(WeaponMount mount, Unit unit);
    void postDraw(WeaponMount mount, Unit unit);
    boolean useDefaultDraw();
}
