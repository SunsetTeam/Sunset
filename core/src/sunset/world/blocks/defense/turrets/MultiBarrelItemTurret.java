package sunset.world.blocks.defense.turrets;

import arc.util.Time;
import arc.func.Func;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.entities.bullet.BulletType;
import mindustry.world.blocks.defense.turrets.ItemTurret;

/** Описывает многоствольную турель. */
public class MultiBarrelItemTurret extends ItemTurret {
    public Seq<Vec2> barrelPoints = new Seq<>();
    public Seq<Vec2> ejectPoints = new Seq<>();
    
    public float maxReloadMultiplier = 0.5f;
	public float speedupPerShot = 0.075f;
	public float slowReloadTime = 90f;

    public MultiBarrelItemTurret(String name) {
        super(name);
    }

    public class MultiBarrelItemTurretBuild extends ItemTurretBuild {
        public float speedupScl = 0f;
		public float slowDownReload = 0f;
		public float maxReloadTime = 0f;

        @Override
		public void updateTile(){
			super.updateTile();
			if(slowDownReload >= 1f){
				slowDownReload -= Time.delta;
			}else speedupScl = Mathf.lerpDelta(speedupScl, 0f, 0.05f);
		}
		
		@Override
		protected void updateShooting(){
            if(reload >= reloadTime){
                BulletType type = peekAmmo();
                shoot(type);
                reload = 0f;
            }else{
                reload += (1 + speedupScl) * delta() * peekAmmo().reloadMultiplier * baseReloadSpeed();
            }
        }

        @Override
        protected void shoot(BulletType type) {

			slowDownReload = slowReloadTime;
			if(speedupScl < maxReloadMultiplier){
				speedupScl += speedupPerShot;
			}else speedupScl = maxReloadMultiplier;

            // костыль с отдельной функцией нужен для сохранения индекса выстрела
            Func<Integer, Runnable> shootFunc = i -> () -> {
                if(!isValid() || !hasAmmo()) return;
                recoil = recoilAmount;
                Vec2 p = barrelPoints.get(i);
                tr.trns(rotation - 90, (p.x - 0.5f) * Vars.tilesize * size,
                                             (-p.y + 0.5f) * Vars.tilesize * size);
                bullet(type, rotation + Mathf.range(inaccuracy));
                p = new Vec2(ejectPoints.get(i));
                p.trns(rotation - 90, (p.x - 0.5f) * Vars.tilesize * size,
                                            (p.y - 0.5f) * Vars.tilesize * size);
                ammoUseEffect.at(x + p.x, y + p.y, ejectPoints.get(i).x > 0.5f ? rotation : rotation + 180f);
                effects();
                useAmmo();
                recoil = recoilAmount;
                heat = 1;
            };
            for(int i = 0; i < barrelPoints.size; i++) {
                Time.run(burstSpacing * i, shootFunc.get(i));
            }
        }

        @Override
        protected void ejectEffects() {
            // эффекты в shoot()
        }
    }
}