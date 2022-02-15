package sunset.world.blocks.environment;

import arc.Core;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.StatusEffects;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.logic.Ranged;
import mindustry.type.StatusEffect;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.tilesize;

public class RadiationDebris extends Wall {
    public StatusEffect radiationStatus = StatusEffects.none;
    public float radiationDuration = 8f * Time.toSeconds;
    public float range = 10f * tilesize;
    public float lightRadius = 4f * tilesize;

    public RadiationDebris(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.range, range / tilesize, StatUnit.blocks);
        stats.add(Stat.abilities, table -> {
            table.image(radiationStatus.uiIcon).size(18f);
            table.add(" [accent]" + radiationStatus.localizedName + "[] " + (int)(radiationDuration / 60) + " " + Core.bundle.get("unit.seconds"));
        });
    }

    public class RadiationDebrisBuild extends Building implements Ranged {

        public void update(Unit unit){
            unit.apply(radiationStatus, radiationDuration);
            float angle = angleTo(unit);
            Tmp.v1.trns(angle, range).add(this);
        }

        public float range() {
            return range;
        }

        @Override
        public void drawLight(){
            super.drawLight();
            Drawf.light(team, x, y, lightRadius * size, radiationStatus.color, 0.2f);
        }
    }
}
