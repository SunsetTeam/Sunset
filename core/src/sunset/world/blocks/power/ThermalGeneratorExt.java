package sunset.world.blocks.power;

import arc.Core;
import arc.math.Mathf;
import mindustry.world.blocks.power.ThermalGenerator;
import mindustry.world.meta.Stat;

public class ThermalGeneratorExt extends ThermalGenerator {
    public float attributeScale = 1f;
    public ThermalGeneratorExt(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.remove(Stat.tiles);
        stats.add(Stat.tiles, attribute, floating, attributeScale, false);
    }
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        drawPotentialLinks(x, y);
        drawPlaceText(Core.bundle.format("bar.efficiency", Math.round(sumAttribute(attribute, x, y) * 100 * attributeScale)), x, y, valid);
    }
    public class ThermalGeneratorBuildExt extends ThermalGeneratorBuild {
        @Override
        public void updateTile() {
            if(efficiency>0) {
                productionEfficiency = sum * attributeScale + attribute.env();
            } else {
                productionEfficiency = 0;
            }
            if(productionEfficiency > 0.1f && Mathf.chanceDelta(0.05 * delta())) {
                generateEffect.at(x + Mathf.range(3f), y + Mathf.range(3f));
            }
        }
    }
}
