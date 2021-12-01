package sunset.world.blocks.defense.turrets;

import arc.Core;
import arc.func.Cons;
import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.annotations.Annotations.Load;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import sunset.SnVars;
import sunset.utils.Utils;

import static arc.Core.settings;

/** Item turret with useful things.
 * Features:
 * 1) reload bar
 * 2)
 * */
public class ModItemTurret extends ItemTurret {
    @Load("@-light")
    public TextureRegion light;
    public boolean drawLight;

    public ModItemTurret(String name) {
        super(name);
        drawLight = false;
    }

    @Override
    public void setBars() {
        super.setBars();
        SnVars.settings.registerReloadBarBlock(this, (ItemTurretBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reload / reloadTime) * 100f)),
                () -> entity.team.color,
                () -> Mathf.clamp(entity.reload / reloadTime)
        ));
    }

    public Cons<TurretBuild> lightDrawer = tile -> {
        if(tile.heat <= 0.00001f) return;

        Draw.color(heatColor, tile.heat);
        Draw.blend(Blending.additive);
        Draw.rect(light, tile.x + tr2.x, tile.y + tr2.y, tile.rotation - 90);
        Draw.blend();
        Draw.color();
    };

    public class ModItemTurretBuild extends ItemTurretBuild {
        @Override
        public void draw() {
            Draw.rect(baseRegion, x, y);
            Draw.color();

            Draw.z(Layer.turret);

            tr2.trns(rotation, -recoil);

            Drawf.shadow(region, x + tr2.x - elevation, y + tr2.y - elevation, rotation - 90);
            drawer.get(this);

            if(heatRegion != Core.atlas.find("error")) {
                heatDrawer.get(this);
            }

            if (drawLight && this.liquids.currentAmount() > 0) lightDrawer.get(this);
        }
    }
}
