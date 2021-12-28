package sunset.type.weapons;

import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.type.Weapon;
import mma.ModVars;

/** needs for remove modPrefix when you create a weapon*/
public class SnWeapon extends Weapon {
    public SnWeapon(String name) {
        super(ModVars.fullName(name));
    }

    public SnWeapon() {
        super();
    }

    /**Anuke bug fix*/
    @Override
    public void drawOutline(Unit unit, WeaponMount mount) {float
            rotation = unit.rotation - 90,
            weaponRotation  = rotation + (rotate ? mount.rotation : 0),
            wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -mount.recoil),
            wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -mount.recoil);

        if(outlineRegion.found()){
            Draw.rect(outlineRegion,
                    wx, wy,
                    outlineRegion.width * Draw.scl * -Mathf.sign(flipSprite),
                    outlineRegion.height * Draw.scl,
                    weaponRotation);
        }
    }
    /**Anuke bug fix*/
    @Override
    public void draw(Unit unit, WeaponMount mount){
        float
                rotation = unit.rotation - 90,
                weaponRotation  = rotation + (rotate ? mount.rotation : 0),
                wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -mount.recoil),
                wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -mount.recoil);

        if(shadow > 0){
            Drawf.shadow(wx, wy, shadow);
        }

        if (top) {
            drawOutline(unit, mount);
        }

        Draw.rect(region,
                wx, wy,
                region.width * Draw.scl * -Mathf.sign(flipSprite),
                region.height * Draw.scl,
                weaponRotation);

        if(heatRegion.found() && mount.heat > 0){
            Draw.color(heatColor, mount.heat);
            Draw.blend(Blending.additive);
            Draw.rect(heatRegion,
                    wx, wy,
                    heatRegion.width * Draw.scl * -Mathf.sign(flipSprite),
                    heatRegion.height * Draw.scl,
                    weaponRotation);
            Draw.blend();
            Draw.color();
        }
    }
}
