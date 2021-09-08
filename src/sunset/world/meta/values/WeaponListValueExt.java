package sunset.world.meta.values;

import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import arc.struct.OrderedMap;
import arc.struct.Seq;
import arc.util.Scaling;
import arc.util.Strings;
import mindustry.gen.Tex;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.ui.Cicon;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValue;
import mindustry.world.meta.values.AmmoListValue;

/** В отличии от стандартного WeaponListValueExt передаёт заполнение
 * информации об оружии самому оружию, если оно это поддерживает. */
public class WeaponListValueExt implements StatValue {
    private final Seq<Weapon> weapons;
    private final UnitType unit;

    public WeaponListValueExt(UnitType unit, Seq<Weapon> weapons){
        this.weapons = weapons;
        this.unit = unit;
    }

    @Override
    public void display(Table table){
        table.row();
        for(int i = 0; i < weapons.size;i ++){
            Weapon weapon = weapons.get(i);

            if(weapon.flipSprite){
                //flipped weapons are not given stats
                continue;
            }

            TextureRegion region = !weapon.name.equals("") && weapon.outlineRegion.found() ? weapon.outlineRegion : unit.icon(Cicon.full);

            table.image(region).size(60).scaling(Scaling.bounded).right().top();

            if(weapon instanceof StatValue) {
                table.table(Tex.underline, ((StatValue)weapon)::display).padTop(-9).left();
            } else {
                table.table(Tex.underline, w -> {
                    w.left().defaults().padRight(3).left();

                    if (weapon.inaccuracy > 0) {
                        sep(w, "[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int) weapon.inaccuracy + " " + StatUnit.degrees.localized());
                    }
                    sep(w, "[lightgray]" + Stat.reload.localized() + ": " + (weapon.mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / weapon.reload * weapon.shots, 2));

                    AmmoListValue<UnitType> bullet = new AmmoListValue<>(OrderedMap.of(unit, weapon.bullet));
                    bullet.display(w);
                }).padTop(-9).left();
            }
            table.row();
        }
    }

    void sep(Table table, String text){
        table.row();
        table.add(text);
    }
}
