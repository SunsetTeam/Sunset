package sunset.world.blocks.defense.turrets;

import arc.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.bullet.*;
import mindustry.ui.*;
import mindustry.world.blocks.defense.turrets.*;
import sunset.*;
import sunset.entities.pattern.*;
import sunset.utils.*;

/**
 * Describes a multi-barreled turret.
 */
public class SpeedUpItemTurret extends ItemTurret{

    // reload acceleration multiplier
    public float maxReloadMultiplier = 0.3f;
    // number of detonations per 1 shot
    public float speedupPerShot = 0.01f;
    public float slowReloadTime = 100f;

    public SpeedUpItemTurret(String name){
        super(name);
        UtilsKt.TODO("WAITING FOR PULL REQUEST(LOOK AT " + ShootMultiBarrel.class.getName() + ")");
    }

    @Override
    public void setBars(){
        super.setBars();
        SnVars.settings.registerReloadBarBlock(this, (SpeedUpItemTurretBuild entity) -> new Bar(
        () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reloadCounter / reload) * 100f)),
        () -> entity.team.color,
        () -> Mathf.clamp(entity.reloadCounter / reload)
        ));
        addBar("sunset-speedup", (SpeedUpItemTurretBuild entity) -> new Bar(
        () -> Core.bundle.format("bar.sunset-speedup", Utils.stringsFixed(Mathf.clamp(entity.speedupScl / maxReloadMultiplier) * 100f)),
        () -> entity.team.color,
        () -> Mathf.clamp(entity.speedupScl / maxReloadMultiplier)
        ));
    }

    public class SpeedUpItemTurretBuild extends ItemTurretBuild{
        public float speedupScl = 0f;
        public float slowDownReload = 0f;

        @Override
        public void updateTile(){
            super.updateTile();
            if(slowDownReload >= 1f){
                slowDownReload -= Time.delta;
            }else speedupScl = Mathf.lerpDelta(speedupScl, 0f, 0.05f);
        }

        @Override
        protected void shoot(BulletType type){
            slowDownReload = slowReloadTime;
            speedupScl = Math.min(speedupScl + speedupPerShot, maxReloadMultiplier);
        }
    }
}