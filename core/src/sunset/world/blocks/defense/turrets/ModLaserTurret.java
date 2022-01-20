package sunset.world.blocks.defense.turrets;

import arc.Core;
import arc.math.Mathf;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.LaserTurret;
import sunset.SnVars;
import sunset.utils.Utils;

/** Laser turret with useful things.
 * Features:
 * 1) reload bar
 * 2)
 * */
public class ModLaserTurret extends LaserTurret {

    public ModLaserTurret(String name) {
        super(name);
    }

    @Override
    public void setBars() {
        super.setBars();
        SnVars.settings.registerReloadBarBlock(this,(LaserTurretBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reload / reloadTime) * 100f)),
                () -> entity.team.color,
                () -> Mathf.clamp(entity.reload / reloadTime)
        ));
    }
}
