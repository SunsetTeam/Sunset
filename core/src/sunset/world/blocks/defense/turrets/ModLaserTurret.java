package sunset.world.blocks.defense.turrets;

import acontent.world.meta.AStats;
import arc.Core;
import arc.math.Mathf;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.LaserTurret;
import sunset.SnVars;
import sunset.utils.Utils;

import static mindustry.Vars.tilesize;

/** Laser turret with useful things.
 * Features:<p>
 * 1) reload bar<p>
 * 2)
 * */
public class ModLaserTurret extends LaserTurret {
    public boolean reloadBar = true;

    public AStats aStats = new AStats();

    public ModLaserTurret(String name) {
        super(name);
        stats = aStats.copy(stats);
    }
    @Override
    public void setStats() {
        super.setStats();
    }
    @Override
    public void setBars() {
        super.setBars();
        if (reloadBar) {
            SnVars.settings.registerReloadBarBlock(this,(LaserTurretBuild entity) -> new Bar(
                    () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reloadCounter / reload) * 100f)),
                    () -> entity.team.color,
                    () -> Mathf.clamp(entity.reloadCounter / reload)
            ));
        }
    }
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.placing);
        if (minRange > 0) Drawf.dashCircle(x, y, minRange, Pal.health);
    }
}
