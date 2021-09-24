package sunset.world.blocks.defense.turrets;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Sounds;
import mindustry.gen.Teamc;
import mindustry.gen.Tex;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import sunset.content.SnFx;
import sunset.utils.Utils;

public class EMPFacility extends PowerTurret{
    public Color lightningColor;
    public float shotAngleRand, spinUp, spinDown, rangeExtention, lightningStroke = 3.5f;
    public int shotss;

    public TextureRegion heatRegion, bottomRegion, topRegion;

    public EMPFacility(String name){
        super(name);
        shootCone = 306f;
        lightningColor = Pal.surge;
        shootSound = Sounds.spark;
        shootEffect = Fx.sparkShoot;
        cooldown = 0.04f;
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

                t.add(Core.bundle.format("bullet.lightning", shotss, shootType.damage));
                t.row();

                if(shootType.status != StatusEffects.none){
                    t.add((shootType.minfo.mod == null ? shootType.status.emoji() : "") + "[stat]" + shootType.status.localizedName);
                }
            }).padTop(-9).left().get().background(Tex.underline);
        });
    }

    @Override
    public void load(){
        super.load();
        heatRegion = Core.atlas.find(name + "-heat");
        bottomRegion = Core.atlas.find(name + "-bottom");
        topRegion = Core.atlas.find(name + "-top");
    }

    @Override
    public void init(){
        super.init();
        consumes.powerCond(powerUse, TurretBuild::isShooting);
        super.init();
    }

    public class EMPFacilityBuild extends PowerTurretBuild{
        protected Seq<Teamc> targets = new Seq<>();
        protected float speedScl;

        @Override
        public void draw(){
            Draw.rect(baseRegion, x, y);

            Draw.z(Layer.turret);
            if(heat > 0.00001f){
                        Draw.color(heatColor, heat);
                        Draw.blend(Blending.additive);
                        Draw.rect(heatRegion, x, y, rotation - 90f);
                        Draw.blend();
                        Draw.color();
                    }
            Draw.rect(topRegion, x, y);
        }

        @Override
        public void updateTile(){
                heat = Mathf.lerpDelta(heat, 0f, cooldown);

            if(!hasAmmo() || !isShooting() || !isActive() || !cons.valid()){
                speedScl = Mathf.lerpDelta(speedScl, 0, spinDown);
            }
            if(hasAmmo() && isShooting() && isActive() && cons.valid()){
                Liquid liquid = liquids.current();
                speedScl = Mathf.lerpDelta(speedScl, 1, spinUp * peekAmmo().reloadMultiplier * liquid.heatCapacity * coolantMultiplier * edelta());
            }

            rotation -= speedScl * Time.delta;

            super.updateTile();
        }

        @Override
        protected void shoot(BulletType type){
            targets.clear();

            targets = Utils.allNearbyEnemies(team, x, y, range + rangeExtention);

            if(targets.size > 0){
                for(int i = 0; i < shots; i++){
                    Teamc target = targets.random();

                    float shootX = x + Tmp.v1.x + Tmp.v2.x, shootY = y + Tmp.v1.y + Tmp.v2.y;
                    float sX = target.x() + Tmp.v3.x, sY = target.y() + Tmp.v3.y;

                    float shootAngle = Angles.angle(shootX, shootY, sX, sY);
                    float dist = Mathf.dst(shootX, shootY, sX, sY);

                    SnFx.fakeLightning.at(shootX, shootY, shootAngle, lightningColor, new Object[]{dist, lightningStroke, team});
                    shootSound.at(shootX, shootY, Mathf.random(0.9f, 1.1f));
                    shootEffect.at(shootX, shootY, shootAngle, lightningColor);
                    if(shootShake > 0f){
                        Effect.shake(shootShake, shootShake, this);
                    }
                    final float spawnX = sX, spawnY = sY;
                    Time.run(3f, () -> {
                        for(int j = 0; j < shotss; j++){
                            shootType.create(this, team, spawnX, spawnY, ((360f / shotss) * j) + Mathf.range(shotAngleRand));
                        }
                    });
                }
            }
        }

        @Override
        protected void turnToTarget(float targetRot){
        }

        @Override
        public boolean canControl(){
            return false;
        }

        public int size(){
            if(size < 3) return size = 3;
            return size;
        }
    }
}