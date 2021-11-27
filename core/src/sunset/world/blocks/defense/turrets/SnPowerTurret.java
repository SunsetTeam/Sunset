package sunset.world.blocks.defense.turrets;

import arc.Core;
import arc.math.Mathf;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import sunset.SnVars;
import sunset.content.SnSettings;
import sunset.utils.Utils;

import static arc.Core.settings;
/** Power turret with useful things.
 * Features:
 * 1) reload bar
 * 2)
 * */
public class SnPowerTurret extends PowerTurret {

    public SnPowerTurret(String name) {
        super(name);
    }

    @Override
    public void setBars() {
        super.setBars();
        SnVars.settings.registerReloadBarBlock(this,(PowerTurretBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reload / reloadTime) * 100f)),
                () -> entity.team.color,
                () -> Mathf.clamp(entity.reload / reloadTime)
        ));
    }
}
