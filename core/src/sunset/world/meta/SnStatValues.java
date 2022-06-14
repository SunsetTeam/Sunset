package sunset.world.meta;

import arc.Core;
import arc.func.Boolf;
import arc.func.Func;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.OrderedMap;
import arc.struct.Seq;
import arc.util.Scaling;
import arc.util.Strings;
import mindustry.content.StatusEffects;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Tex;
import mindustry.type.Liquid;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.ui.ItemDisplay;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValue;
import mindustry.world.meta.StatValues;
import sunset.content.affilitiation.SnBranches;
import sunset.content.affilitiation.SnGuilds;
import sunset.content.affilitiation.SnSubGuilds;
import sunset.type.blocks.DrillItem;

import static mindustry.Vars.content;
import static mindustry.Vars.tilesize;
import static mma.ModVars.fullName;

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
                        sep(w, "[lightgray]" + Stat.reload.localized() + ": " + (weapon.mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / weapon.reload * weapon.shoot.shots, 2));

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

    public static StatValue affil(SnGuilds guild) {
        return table -> {
            table.image(guild.region()).size(25);
            table.add("   " + guild.localizedName()).color(guild.color);
        };
     }
    public static StatValue affil(SnSubGuilds subGuild) {
        return table -> {
            table.image(subGuild.region()).size(25);
            table.add("   " + subGuild.localizedName()).color(subGuild.color);
        };
    }
    public static StatValue affil(SnBranches branch) {
        return table -> {
            table.image(branch.region()).size(25);
            table.add("   " + branch.localizedName()).color(branch.color);
        };
    }

    public static <T extends UnlockableContent> StatValue powerBullet(ObjectMap<T, BulletType> map, int indent, int shots){
        return table -> {

            table.row();

            var orderedKeys = map.keys().toSeq();
            orderedKeys.sort();

            for(T t : orderedKeys){
                boolean compact = t instanceof UnitType || indent > 0;

                BulletType type = map.get(t);

                //no point in displaying unit icon twice
                if(!compact && !(t instanceof PowerTurret)){
                    table.image(Core.atlas.find(fullName("lightning"))).padRight(4).right().top().size(25);
                    table.add(Core.bundle.format("stat.power-shot")).padRight(10).left().top();

                    //table.add().padRight(10).left();
                }

                table.table(bt -> {
                    bt.left().defaults().padRight(3).left();

                    if(type.damage > 0 && (type.collides || type.splashDamage <= 0)){
                        if(type.continuousDamage() > 0){
                            bt.add(Core.bundle.format("bullet.damage", type.continuousDamage()) + StatUnit.perSecond.localized());
                        }else{
                            bt.add(Core.bundle.format("bullet.damage", type.damage));
                        }
                    }

                    if(type.buildingDamageMultiplier != 1){
                        sep(bt, Core.bundle.format("bullet.buildingdamage", (int)(type.buildingDamageMultiplier * 100)));
                    }

                    if(type.splashDamage > 0){
                        sep(bt, Core.bundle.format("bullet.splashdamage", (int)type.splashDamage, Strings.fixed(type.splashDamageRadius / tilesize, 1)));
                    }

                    if(!compact && !Mathf.equal(type.ammoMultiplier, 1f) && type.displayAmmoMultiplier){
                        sep(bt, Core.bundle.format("bullet.multiplier", (int)type.ammoMultiplier));
                    }

                    if(!compact && !Mathf.equal(type.reloadMultiplier, 1f)){
                        sep(bt, Core.bundle.format("bullet.reload", Strings.autoFixed(type.reloadMultiplier, 2)));
                    }

                    if(type.knockback > 0){
                        sep(bt, Core.bundle.format("bullet.knockback", Strings.autoFixed(type.knockback, 2)));
                    }

                    if(type.healPercent > 0f){
                        sep(bt, Core.bundle.format("bullet.healpercent", Strings.autoFixed(type.healPercent, 2)));
                    }

                    if(type.pierce || type.pierceCap != -1){
                        sep(bt, type.pierceCap == -1 ? "@bullet.infinitepierce" : Core.bundle.format("bullet.pierce", type.pierceCap));
                    }

                    if(type.incendAmount > 0){
                        sep(bt, "@bullet.incendiary");
                    }

                    if(type.homingPower > 0.01f){
                        sep(bt, "@bullet.homing");
                    }

                    if(type.lightning > 0){
                        sep(bt, Core.bundle.format("bullet.lightning", type.lightning, type.lightningDamage < 0 ? type.damage : type.lightningDamage));
                    }

                    if(type.status != StatusEffects.none){
                        sep(bt, (type.minfo.mod == null ? type.status.emoji() : "") + "[stat]" + type.status.localizedName);
                    }

                    if(type.fragBullet != null){
                        sep(bt, Core.bundle.format("bullet.frags", type.fragBullets));
                        bt.row();

                        StatValues.ammo(ObjectMap.of(t, type.fragBullet), indent + 1).display(bt);
                    }

                    bt.row();
                    bt.add(Core.bundle.format("stat.shoots", shots)/* + StatUnit.perShot.localized()*/);
                }).padTop(compact ? 0 : -9).padLeft(indent * 8).left().get().background(compact ? null : Tex.underline);

                table.row();
            }
        };
    }
}
