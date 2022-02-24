package sunset.world.blocks.defense.turrets;

import arc.Core;
import arc.math.Mathf;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import sunset.SnVars;
import sunset.utils.Utils;

import static mindustry.Vars.tilesize;

/** Power turret with useful things.
 * Features:
 * 1) reload bar
 * 2)
 * */
public class ModPowerTurret extends PowerTurret {
    public boolean reloadBar = true;

    public String faction;
    public String subFaction;
    public String branch;

    public ModPowerTurret(String name) {
        super(name);
        this.faction = "null";
        this.subFaction = "null";
        this.branch = "null";
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.placing);
        if (minRange > 0) Drawf.dashCircle(x, y, minRange, Pal.health);
    }

    @Override
    public void setBars() {
        super.setBars();
        if (reloadBar) {
            SnVars.settings.registerReloadBarBlock(this,(PowerTurretBuild entity) -> new Bar(
                    () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reload / reloadTime) * 100f)),
                    () -> entity.team.color,
                    () -> Mathf.clamp(entity.reload / reloadTime)
            ));
        }
    }
}
