package sunset.world.meta.values;

import arc.Core;
import arc.scene.ui.layout.Table;
import arc.util.Strings;
import mindustry.world.meta.StatValue;

import static mindustry.Vars.tilesize;

public class SplashDamageValue implements StatValue {
    public final float damage;
    public final float splashDamage;
    public final float splashDamageRadius;

    public SplashDamageValue(float damage, float splashDamage, float splashDamageRadius) {
        this.damage = damage;
        this.splashDamage = splashDamage;
        this.splashDamageRadius = splashDamageRadius;
    }

    @Override
    public void display(Table table) {
        table.table(bt -> {
            bt.left().defaults().padRight(3).left();

            if(damage > 0) {
                bt.add(Core.bundle.format("bullet.damage", damage));
            }

            if(splashDamage > 0) {
                bt.row();
                bt.add(Core.bundle.format("bullet.splashdamage", Strings.fixed(splashDamage,1), Strings.fixed(splashDamageRadius / tilesize, 1)));
            }

        }).left();

        table.row();
    }
}