package sunset.world.blocks.defense.turrets;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.annotations.Annotations;
import mindustry.entities.bullet.BulletType;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mma.ModVars;
import sunset.SnVars;
import sunset.utils.Utils;


public class MinigunTurret extends ItemTurret {
    public float inaccuracyUp = 0f;
    public float maxShootTime = 0f;
    @Annotations.Load("@-heatSensor")
    public TextureRegion heatSensor;

    public MinigunTurret(String name){
        super(name);
    }

    @Override
    public void setBars() {
        super.setBars();
        SnVars.settings.registerReloadBarBlock(this, (MinigunTurret.MinigunTurretBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.sunset-heat", Utils.stringsFixed(Mathf.clamp(entity.reload / maxShootTime) * 100f)),
                () -> entity.team.color,
                () -> Mathf.clamp(entity.reload / maxShootTime)
        ));
    }

    @Override
    public TextureRegion[] icons() {
        return !ModVars.packSprites ? new TextureRegion[]{region} : new TextureRegion[]{baseRegion, region};
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.inaccuracy, inaccuracyUp, StatUnit.degrees);
    }
    public class MinigunTurretBuild extends ItemTurretBuild{
        public float maxTime = Time.time * maxShootTime;

        @Override
        protected void bullet(BulletType type, float angle) {
            super.bullet(type, angle + Mathf.range(maxTime * inaccuracyUp));
        }

        @Override
        public void draw(){
            Draw.rect(heatSensor, x, y, maxTime);
        }
    }
}
