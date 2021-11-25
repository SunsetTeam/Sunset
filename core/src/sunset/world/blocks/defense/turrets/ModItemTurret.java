package sunset.world.blocks.defense.turrets;

import arc.Core;
import arc.math.Mathf;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import sunset.utils.Utils;

import static arc.Core.settings;

/** Item turret with useful things.
 * Features:
 * 1) reload bar
 * 2)
 * */
public class ModItemTurret extends ItemTurret {

    public ModItemTurret(String name) {
        super(name);
    }

    @Override
    public void setBars() {
        super.setBars();
        if (settings.getBool("sn-reloadbar")) {
            bars.add("sunset-reload", (ItemTurretBuild entity) -> new Bar(
                    () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reload / reloadTime) * 100f)),
                    () -> entity.team.color,
                    () -> Mathf.clamp(entity.reload / reloadTime)
            ));
        }
    }
}
