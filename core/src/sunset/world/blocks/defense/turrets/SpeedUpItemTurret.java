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
    public float shotSpeedUp = 0.01f;
    public float slowReloadTime = 100f;

    public SpeedUpItemTurret(String name){
        super(name);
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
        () -> Core.bundle.format("bar.sunset-speedup", Utils.stringsFixed(Mathf.clamp(entity.speedUp / maxReloadMultiplier) * 100f)),
        () -> entity.team.color,
        () -> Mathf.clamp(entity.speedUp / maxReloadMultiplier)
        ));
    }

    public class SpeedUpItemTurretBuild extends ItemTurretBuild{
        public float speedUp = 1f;
        public float sReload = 1f;

        @Override
        public void updateTile(){
            super.updateTile();
            if(sReload >= 1f){
                sReload -= Time.delta;
            }else speedUp = Mathf.lerpDelta(speedUp, 0f, 0.05f);
        }

        @Override
        protected void updateShooting() {
            if (reload >= reloadCounter) {
                BulletType type = peekAmmo();
                shoot(type);
                reload = 0f;
            } else {
                reload += (1 + speedUp) * delta() * peekAmmo().reloadMultiplier * baseReloadSpeed();
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
