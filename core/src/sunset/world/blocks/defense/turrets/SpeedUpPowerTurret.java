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
public class SpeedUpPowerTurret extends PowerTurret{

    // reload acceleration multiplier
    public float maxReloadMultiplier = 0.3f;
    // number of detonations per 1 shot
    public float shotSpeedUp = 0.01f;
    public float slowReloadTime = 100f;

    public SpeedUpPowerTurret(String name){
        super(name);
    }

    @Override
    public void setBars(){
        super.setBars();
        SnVars.settings.registerReloadBarBlock(this, (SpeedUpPowerTurretBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reloadCounter / reload) * 100f)),
                () -> entity.team.color,
                () -> Mathf.clamp(entity.reloadCounter / reload)
        ));
        addBar("sunset-speedup", (SpeedUpPowerTurretBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.sunset-speedup", Utils.stringsFixed(Mathf.clamp(entity.speedUp / maxReloadMultiplier) * 100f)),
                () -> entity.team.color,
                () -> Mathf.clamp(entity.speedUp / maxReloadMultiplier)
        ));
    }

    public class SpeedUpPowerTurretBuild extends PowerTurretBuild{
        public float speedUp = 0f;
        public float sReload = 0f;

        @Override
        public void updateTile(){
            super.updateTile();
            if(sReload >= 1f){
                sReload -= Time.delta;
            }else speedUp = Mathf.lerpDelta(speedUp, 0f, 0.05f);
        }

        @Override
        protected void updateShooting() {
            if (reloadCounter >= reload) {
                BulletType type = peekAmmo();
                shoot(type);
                reloadCounter = 0f;
            } else {
                reloadCounter += (1 + speedUp) * delta() * peekAmmo().reloadMultiplier * baseReloadSpeed();
            }
        }

        @Override
        protected void shoot(BulletType type){
            super.shoot(type);
            sReload = slowReloadTime;
            if(speedUp < maxReloadMultiplier){
                speedUp += shotSpeedUp;
            }else speedUp = maxReloadMultiplier;
            //   speedUp = Math.min(speedUp + speedUpPerShot, maxReloadMultiplier);
        }
    }
}
