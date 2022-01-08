package sunset.world.meta;

import arc.Core;
import arc.func.Boolf;
import arc.func.Func;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import arc.struct.OrderedMap;
import arc.struct.Seq;
import arc.util.Scaling;
import arc.util.Strings;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Tex;
import mindustry.type.Liquid;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.ui.ItemDisplay;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValue;
import mindustry.world.meta.StatValues;
import sunset.type.blocks.DrillItem;

import static mindustry.Vars.content;
import static mindustry.Vars.tilesize;

public class SnStatValues {
    public static StatValue minMaxRange(float minRange, float maxRange) {
        return table -> {
            table.table(bt -> {
                bt.left().defaults().padRight(3).left();
                bt.add(Core.bundle.format("missile.rangerange",
                        Strings.fixed(minRange / tilesize, 1),
                        Strings.fixed(maxRange / tilesize, 1)));
            }).left();

            table.row();
        };
    }

    public static StatValue splashDamage(float damage, float splashDamage, float splashDamageRadius) {
        return table -> {
            table.table(bt -> {
                bt.left().defaults().padRight(3).left();

                if (damage > 0) {
                    bt.add(Core.bundle.format("bullet.damage", damage));
                }

                if (splashDamage > 0) {
                    bt.row();
                    bt.add(Core.bundle.format("bullet.splashdamage", Strings.fixed(splashDamage, 1), Strings.fixed(splashDamageRadius / tilesize, 1)));
                }

            }).left();

            table.row();
        };
    }

    public static StatValue boosterLiquidList(Boolf<Liquid> filter, Func<Liquid, Float> factorGen, String bundle) {
        return table -> {
            table.row();
            table.table(c -> {
                for (Liquid liquid : content.liquids()) {
                    if (!filter.get(liquid)) continue;

                    c.image(liquid.fullIcon).size(3 * 8).padRight(4).right().top();
                    c.add(liquid.localizedName).padRight(10).left().top();
                    c.table(Tex.underline, bt -> {
                        bt.left().defaults().padRight(3).left();
                        bt.add(Core.bundle.format(bundle, Strings.autoFixed(factorGen.get(liquid), 2)));
                    }).left().padTop(-9);
                    c.row();
                }
            }).colspan(table.getColumns());
            table.row();
        };
    }

    public static StatValue weaponListExt(UnitType unit, Seq<Weapon> weapons) {
        return table -> {
            table.row();
            for (int i = 0; i < weapons.size; i++) {
                Weapon weapon = weapons.get(i);

                if (weapon.flipSprite) {
                    //flipped weapons are not given stats
                    continue;
                }

                TextureRegion region = !weapon.name.equals("") && weapon.outlineRegion.found() ? weapon.outlineRegion : unit.fullIcon;

                table.image(region).size(60).scaling(Scaling.bounded).right().top();

                if (weapon instanceof StatValue) {
                    table.table(Tex.underline, ((StatValue) weapon)::display).padTop(-9).left();
                } else {
                    table.table(Tex.underline, w -> {
                        w.left().defaults().padRight(3).left();

                        if (weapon.inaccuracy > 0) {
                            sep(w, "[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int) weapon.inaccuracy + " " + StatUnit.degrees.localized());
                        }
                        sep(w, "[lightgray]" + Stat.reload.localized() + ": " + (weapon.mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / weapon.reload * weapon.shots, 2));

                        StatValues.ammo(OrderedMap.of(unit, weapon.bullet)).display(w);
                    }).padTop(-9).left();
                }
                table.row();
            }
        };
    }

    public static StatValue drillItems(DrillItem[] drillItems) {
        return drillItems(drillItems, 0);
    }

    public static StatValue drillItems(DrillItem[] drillItems, int indent) {
        return table -> {

            table.row();

            for (DrillItem t : drillItems) {
                boolean compact = indent > 0;


                //no point in displaying unit icon twice
                if (!compact) {
                    table.image(icon(t.item)).size(3 * 8).padRight(4).right().top();
                    table.add(t.item.localizedName).padRight(10).left().top();
                }

                table.table(bt -> {
                    bt.left().defaults().padRight(3).left();
                    bt.add(Core.bundle.format("stat.drillsizedesc", Strings.autoFixed(t.sizeMultiplier, 2))).padLeft(6);

                    sep(bt, t.amount + " " + StatUnit.items.localized());
                }).padTop(compact ? 0 : -9).padLeft(indent * 8).left().get().background(compact ? null : Tex.underline);

                table.row();
            }
        };
    }

    public static StatValue drillItems_old(DrillItem[] items) {
        return table -> {
            table.table(t -> {
                t.left().defaults().padRight(3).left();
                for (DrillItem di : items) {
                    t.add(new ItemDisplay(di.item, di.amount));
                    t.add(Core.bundle.format("stat.drillsizedesc", Strings.autoFixed(di.sizeMultiplier, 2))).padLeft(6);
//                t.add(Core.bundle.format("stat.drillitemdesc", count, di.item.emoji(), di.item.localizedName));
                    t.row();
                }
            });
        };
    }

    //for AmmoListValue
    private static void sep(Table table, String text) {
        table.row();
        table.add(text);
    }

    private static TextureRegion icon(UnlockableContent t) {
        return t.uiIcon;
    }
}
