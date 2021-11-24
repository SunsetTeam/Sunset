package sunset.world.blocks.defense.turrets;

import arc.Core;
import arc.math.Mathf;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import sunset.utils.Utils;

/** Power turret with useful things.
 * Features:
 * 1) reload bar
 * 2)
 * */
public class ModPowerTurret extends PowerTurret {

    public ModPowerTurret(String name) {
        super(name);
    }

    @Override
    public void setBars() {
        super.setBars();
        bars.add("sunset-reload", (PowerTurretBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reload / reloadTime) * 100f)),
                () -> entity.team.color,
                () -> Mathf.clamp(entity.reload / reloadTime)
        ));
    }
}
