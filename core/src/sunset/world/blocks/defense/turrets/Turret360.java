package sunset.world.blocks.defense.turrets;

import mindustry.world.blocks.defense.turrets.*;


//IMPORTANT
/**
 * Should be ShootPattern{@link mindustry.entities.pattern.ShootPattern}
 * @author Zelaux
 */
/**
 * Turret that shoots 360 degrees and rotates optionally.
 * Extends from {@link ItemTurret} for correct work of power shoot.
 * @author RePoweRED
 */
//IMPORTANT
/**
 * Should be ShootPattern{@link mindustry.entities.pattern.ShootPattern}
 * @author Zelaux
 */
class Turret360{}/* extends ItemTurret{
    public boolean rotateTurret;

    public BulletType powerBullet;
    public boolean debug = false;
    public int chargeShots;

    public AStats aStats = new AStats();

    public Turret360(String name){
        super(name);
        stats = aStats.copy(stats);
        rotateTurret = false;
        shootCone = 360;
        shoot = new ShootSpread(4, shootCone / 4f);
        chargeShots = 1;
    }

    @Override
    public void setStats(){
        super.setStats();
        aStats.add(Stat.ammo, SnStatValues.powerBullet(ObjectMap.of(this, powerBullet), 0, chargeShots));
    }

    @Override
    public void setBars(){
        super.setBars();
        SnVars.settings.registerReloadBarBlock(this, (ItemTurretBuild entity) -> new Bar(
        () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reloadCounter / reload) * 100f)),
        () -> entity.team.color,
        () -> Mathf.clamp(entity.reloadCounter / reload)
        ));
    }

    public class T360 extends ItemTurretBuild{
        boolean isShoot = false;
        int totalShoots = 1;

        @Override
        public void draw(){
            super.draw();


            if(debug) ADrawf.drawText(this, "Total Shoots: " + totalShoots);
        }

        @Override
        protected void updateShooting(){
            if(powerBullet != null){
                boolean canShoot = reload + delta() * peekAmmo().reloadMultiplier * baseReloadSpeed() >= reload && !charging();
                this.isShoot = canShoot;
                super.updateShooting();
                if(!canShoot || isShoot) return;
                totalShoots++;
                if(totalShoots == chargeShots){
                    totalShoots = 0;
                }
            }else{
                super.updateShooting();
            }
        }

        @Override
        protected void shoot(BulletType type){
            if(powerBullet != null) isShoot = false;
            super.shoot(type);
        }

        @Override
        public void bullet(BulletType type, float angle){
            tr.trns(this.rotation, 0);

            float lifeScl = type.scaleVelocity ? Mathf.clamp(Mathf.dst(x + tr.x, y + tr.y, targetPos.x, targetPos.y) / type.range(), minRange / type.range(), range / type.range()) : 1f;

            if(totalShoots == 0){
                powerBullet.create(this, team, x + tr.x, y + tr.y, angle, 1f + Mathf.range(velocityInaccuracy), lifeScl);
            }else{
                type.create(this, team, x + tr.x, y + tr.y, angle, 1f + Mathf.range(velocityInaccuracy), lifeScl);
            }
        }

        @Override
        protected void turnToTarget(float targetRot){
            if(rotateTurret){
                rotation += rotateSpeed;
            }else{
                rotateSpeed = 0;
            }
        }
    }
}
*/