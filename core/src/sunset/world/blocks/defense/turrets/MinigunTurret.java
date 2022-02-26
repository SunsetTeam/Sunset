package sunset.world.blocks.defense.turrets;

import acontent.world.meta.*;
import arc.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.bullet.*;
import mindustry.ui.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.*;
import mma.graphics.*;
import sunset.utils.*;
import sunset.world.meta.*;


public class MinigunTurret extends ItemTurret{
    public float inaccuracyUp = 0f;
    public float totalShootingTime = 20f;
    public AStats aStats = new AStats();

    public MinigunTurret(String name){
        super(name);
        stats = aStats.copy(stats);
    }

    @Override
    public void setBars(){
        super.setBars();
        bars.add("sunset-heat", (MinigunTurret.MinigunTurretBuild entity) -> new Bar(
        () -> Core.bundle.format("bar.sunset-heat", Utils.stringsFixed(Mathf.clamp(entity.reload / totalShootingTime) * 100f)),
        () -> entity.team.color,
        () -> Mathf.clamp(entity.reload / totalShootingTime)
        ));
    }

    @Override
    public void setStats(){
        super.setStats();
        aStats.add(SnStat.inaccuracy, inaccuracyUp, StatUnit.degrees);
    }

    public class MinigunTurretBuild extends ItemTurretBuild{
        boolean isShoot = false;
        @Override
        protected void shoot(BulletType type){
            isShoot = false;
            super.shoot(type);
        }
        @Override
        protected void updateShooting(){
            boolean canShoot = reload + delta() * peekAmmo().reloadMultiplier * baseReloadSpeed() >= reloadTime && !charging;
            this.isShoot = canShoot;
            super.updateShooting();
            if(!canShoot || isShoot) return;
            //if (totalShootingTime < 70) {
                totalShootingTime += Time.delta;
            //}

        }


        @Override
        public void bullet(BulletType type, float angle){
            super.bullet(type, angle + Mathf.range(inaccuracyUp * totalShootingTime));
        }

        @Override
        public void updateTile(){
            super.updateTile();
            if(!wasShooting){
                totalShootingTime = Math.max(0, totalShootingTime - Time.delta);
            }
        }

        @Override
        protected void updateCooling(){
            super.updateCooling();
        }

        @Override
        public void draw(){
            super.draw();
            ADrawf.drawText(this, "totalShootingTime: " + totalShootingTime);
        }

        @Override
        public BulletType useAmmo(){

            return super.useAmmo();
        }
    }
}
