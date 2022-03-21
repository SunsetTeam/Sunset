package sunset.world.blocks.defense.turrets;

import acontent.world.meta.AStats;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.annotations.Annotations.Load;
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
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.consumers.ConsumeLiquidBase;
import mindustry.world.consumers.ConsumeType;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;
import sunset.SnVars;
import sunset.utils.Utils;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;

public class EMPFacility extends PowerTurret {
    public Seq<Core> cores = new Seq<>();
    public boolean hasSpinners;
    public Color lightningColor;
    public float zapAngleRand, spinUp, spinDown, rangeExtention, lightningStroke = 3.5f;
    public int zaps;
    @Load("@-top")
    public TextureRegion top;
    @Load("@-bottom")
    public TextureRegion bottom;
    public AStats aStats = new AStats();

    public EMPFacility(String name) {
        super(name);
        shootCone = 360f;
        lightningColor = Color.valueOf("7FFFD4");
        shootSound = Sounds.release;
        shootEffect = Fx.none;
        cooldown = 0.5f;
        stats = aStats.copy(stats);
    }

    @Override
    public void init() {
        consumes.powerCond(powerUse, TurretBuild::isShooting);
        super.init();
    }

    @Override
    public void setStats() {
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

                if(shootType.status != StatusEffects.none) {
                    t.add((shootType.minfo.mod == null ? shootType.status.emoji() : "") + "[stat]" + shootType.status.localizedName);
                }
            }).padTop(-9).left().get().background(Tex.underline);
        });

        stats.remove(Stat.booster);
        stats.add(Stat.input, StatValues.boosters(reloadTime, consumes.<ConsumeLiquidBase>get(ConsumeType.liquid).amount, coolantMultiplier, false, l -> consumes.liquidfilters.get(l.id)));
    }

    @Override
    public void setBars() {
        super.setBars();
        SnVars.settings.registerReloadBarBlock(this, (EMPFBuild entity) -> new Bar(
                () -> arc.Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reload / reloadTime) * 100f)),
                () -> entity.team.color,
                () -> Mathf.clamp(entity.reload / reloadTime)
        ));
    }

    public Effect ShootEffect = new Effect (22, e -> {
        color(Pal.lancerLaser);
        stroke(e.fout() * 2f);
        Lines.circle(e.x, e.y, this.range);
    });

    public static class Core {
        public float rotationMul = 12f;
        public float radius = 4.25f;
        public float xOffset = 0;
        public float yOffset = 0f;

        public Core(float radius) {
            this.radius = radius;
        }
    }

    public class EMPFBuild extends PowerTurretBuild {
        protected Seq<Teamc> targets = new Seq<>();
        protected float speedScl;


        @Override
        public void draw() {
            Draw.rect(baseRegion, x, y);

            Draw.z(Layer.turret);

            TextureRegion r = hasSpinners ? bottom : region;

            Draw.rect(r, x, y);

            Draw.rect(top, x, y);

            //this.drawTeamTop();
        }

        @Override
        public void updateTile() {
            if(!hasAmmo() || !isShooting() || !isActive() || !cons.valid()) {
                speedScl = Mathf.lerpDelta(speedScl, 0, spinDown);
            }
            if(hasAmmo() && isShooting() && isActive() && cons.valid()) {
                Liquid liquid = liquids.current();
                speedScl = Mathf.lerpDelta(speedScl, 1, spinUp * peekAmmo().reloadMultiplier * liquid.heatCapacity * coolantMultiplier * edelta());
            }

            rotation -= speedScl * Time.delta;

            super.updateTile();
        }

        @Override
        protected void shoot(BulletType type) {

            targets.clear();

            targets = Utils.allNearbyEnemiesOld(team, x, y, range + rangeExtention);

            if (targets.size > 0) {
                for (int i = 0; i < shots; i++) {

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

                    if (shootShake > 0f) {
                        Effect.shake(shootShake, shootShake, this);
                    }
                    final float spawnX = sX, spawnY = sY;
                    Time.run(3f, () -> {
                        for (int j = 0; j < zaps; j++) {
                            shootType.create(this, team, spawnX, spawnY, ((360f / zaps) * j) + Mathf.range(zapAngleRand));
                        }
                    });
                }
                ShootEffect.at(x, y, 0, lightningColor);
            }
        }

        @Override
        protected void turnToTarget(float targetRot) {
        }

        @Override
        public boolean canControl() {
            return false;
        }
    }
}