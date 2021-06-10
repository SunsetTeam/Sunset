package sunset.world.blocks.turrets;

import arc.func.Cons;
import arc.func.Func;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.entities.bullet.BulletType;
import mindustry.world.blocks.defense.turrets.ItemTurret;

/** Описывает многоствольную турель. */
public class MultiBarrelItemTurret extends ItemTurret {
    public Seq<Vec2> barrelPoints = new Seq<>();
    public Seq<Vec2> ejectPoints = new Seq<>();

    public MultiBarrelItemTurret(String name) {
        super(name);
    }

    public class MultiBarrelItemTurretBuild extends ItemTurretBuild {
        private int shotindex = 0; // для эффекта извлечения гильзы

        @Override
        protected void shoot(BulletType type) {
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
            for(shotindex = 0; shotindex < barrelPoints.size; shotindex++) {
                Time.run(burstSpacing * shotindex, shootFunc.get(shotindex));
            }
        }

        @Override
        protected void ejectEffects() {
            // эффекты в shoot()
        }
    }
}