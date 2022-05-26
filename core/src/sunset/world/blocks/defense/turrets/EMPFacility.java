package sunset.world.blocks.defense.turrets;

import acontent.world.meta.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.annotations.Annotations.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.*;
import sunset.*;
import sunset.utils.*;
import sunset.world.draw.*;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;

public class EMPFacility extends PowerTurret{
    public Seq<Core> cores = new Seq<>();
    //    public boolean hasSpinners;
    public Color lightningColor;
    public float zapAngleRand, spinUp, spinDown, rangeExtention, lightningStroke = 3.5f;
    public int zaps;
    @Load("@-top")
    public TextureRegion top;
    @Load("@-bottom")
    public TextureRegion bottom;
    public AStats aStats = new AStats();
    public Effect ShootEffect = new Effect(22, e -> {
        color(Pal.lancerLaser);
        stroke(e.fout() * 2f);
        Lines.circle(e.x, e.y, this.range);
    });

    public EMPFacility(String name){
        super(name);
        shootCone = 360f;
        lightningColor = Color.valueOf("7FFFD4");
        shootSound = Sounds.release;
        shootEffect = Fx.none;
        cooldownTime = 0.5f;
        stats = aStats.copy(stats);
        drawer = new DrawEMPFacility();
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.remove(Stat.inaccuracy);
        if(inaccuracy > 0f) stats.add(Stat.inaccuracy, inaccuracy / Vars.tilesize, StatUnit.blocks);

        stats.remove(Stat.ammo);
        stats.add(Stat.ammo, stat -> {
            stat.row();
            stat.table(t -> {
                t.left().defaults().padRight(3).left();

                t.add(arc.Core.bundle.format("bullet.lightning", zaps, shootType.damage));
                t.row();

                if(shootType.status != StatusEffects.none){
                    t.add((shootType.minfo.mod == null ? shootType.status.emoji() : "") + "[stat]" + shootType.status.localizedName);
                }
            }).padTop(-9).left().get().background(Tex.underline);
        });

        if(coolant != null) stats.remove(Stat.booster);
        stats.add(Stat.input, StatValues.boosters(reload, coolant.amount, coolantMultiplier, false, l -> liquidFilter[l.id]));
    }

    @Override
    public void setBars(){
        super.setBars();
        SnVars.settings.registerReloadBarBlock(this, (EMPFacilityBuild entity) -> new Bar(
        () -> arc.Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reloadCounter / reload) * 100f)),
        () -> entity.team.color,
        () -> Mathf.clamp(entity.reloadCounter / reload)
        ));
    }

    public static class Core{
        public float rotationMul = 12f;
        public float radius = 4.25f;
        public float xOffset = 0;
        public float yOffset = 0f;

        public Core(float radius){
            this.radius = radius;
        }
    }

    public class EMPFacilityBuild extends PowerTurretBuild{
        protected Seq<Teamc> targets = new Seq<>();
        protected float speedScl;


        @Override
        public void updateTile(){
            if(!hasAmmo() || !isShooting() || !isActive() || efficiency == 0){
                speedScl = Mathf.lerpDelta(speedScl, 0, spinDown);
            }
            if(hasAmmo() && isShooting() && isActive() && efficiency > 0 && hasLiquids){
                Liquid liquid = liquids.current();
                speedScl = Mathf.lerpDelta(speedScl, 1, spinUp * peekAmmo().reloadMultiplier * liquid.heatCapacity * coolantMultiplier * edelta());
            }

            rotation -= speedScl * Time.delta;

            super.updateTile();
        }

        @Override
        protected void shoot(BulletType type){

            targets.clear();

            Utils.allNearbyEnemies(team, x, y, range + rangeExtention, it -> targets.add(it.<Teamc>as()));

            if(targets.size > 0){
                for(int i = 0; i < shoot.shots; i++){

                    Core part = cores.random();
                    Teamc target = targets.random();

                    Tmp.v1.trns(rotation * part.rotationMul, part.xOffset, part.yOffset);
                    Tmp.v2.setToRandomDirection().setLength(part.radius);
                    Tmp.v3.setToRandomDirection().setLength(Mathf.random(inaccuracy)); //inaccuracy

                    float shootX = x + Tmp.v1.x + Tmp.v2.x, shootY = y + Tmp.v1.y + Tmp.v2.y;
                    float sX = target.x() + Tmp.v3.x, sY = target.y() + Tmp.v3.y;

                    /*float shootAngle = Angles.angle(shootX, shootY, sX, sY);

                    float dist = Mathf.dst(shootX, shootY, sX, sY);

                    SnFx.fakeLightning.at(shootX, shootY, shootAngle, lightningColor, new Object[] {dist, lightningStroke, team});*/

                    shootSound.at(shootX, shootY, Mathf.random(0.9f, 1.1f));

                    if(shake > 0f){
                        Effect.shake(shake, shake, this);
                    }
                    final float spawnX = sX, spawnY = sY;
                    Time.run(3f, () -> {
                        for(int j = 0; j < zaps; j++){
                            shootType.create(this, team, spawnX, spawnY, ((360f / zaps) * j) + Mathf.range(zapAngleRand));
                        }
                    });
                }
                ShootEffect.at(x, y, 0, lightningColor);
            }
        }

        @Override
        protected void turnToTarget(float targetRot){
        }

        @Override
        public boolean canControl(){
            return false;
        }
    }
}