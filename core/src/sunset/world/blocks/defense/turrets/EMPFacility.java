package sunset.world.blocks.defense.turrets;

import arc.*;
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
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.*;
import sunset.content.*;
import sunset.utils.*;

public class EMPFacility extends PowerTurret {
    public Seq<EMPPart> parts = new Seq<>();
    public boolean hasSpinners;
    public Color lightningColor;
    public float zapAngleRand, spinUp, spinDown, rangeExtention, lightningStroke = 3.5f;
    public int zaps;
    @Load("@-top")
    public TextureRegion top;
    @Load("@-bottom")
    public TextureRegion bottom;

    public EMPFacility(String name) {
        super(name);
        shootCone = 306f;
        lightningColor = Color.valueOf("7FFFD4");
        shootSound = Sounds.release;
        shootEffect = SnFx.empShootSmall;
        cooldown = 0.5f;
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

                t.add(Core.bundle.format("bullet.lightning", zaps, shootType.damage));
                t.row();

                if(shootType.status != StatusEffects.none) {
                    t.add((shootType.minfo.mod == null ? shootType.status.emoji() : "") + "[stat]" + shootType.status.localizedName);
                }
            }).padTop(-9).left().get().background(Tex.underline);
        });
    }

    @Override
    public void setBars() {
        super.setBars();
        bars.add("sunset-reload", (EMPBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reload / reloadTime) * 100f)),
                () -> entity.team.color,
                () -> Mathf.clamp(entity.reload / reloadTime)
        ));
    }

    public static class EMPPart {
        public float rotationMul = 12f;
        public float radius = 4.25f;
        public float xOffset = 0;
        public float yOffset = 0f;

        public EMPPart(float radius) {
            this.radius = radius;
        }
    }

    public class EMPBuild extends PowerTurretBuild {
        protected Seq<Teamc> targets = new Seq<>();
        protected float speedScl;


        @Override
        public void draw() {
            Draw.rect(baseRegion, x, y);

            Draw.z(Layer.turret);

            TextureRegion r = hasSpinners ? bottom : region;

            Drawf.shadow(r, x - elevation, y - elevation);

            Draw.rect(r, x, y);

            Draw.rect(top, x, y);

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

            targets = Utils.allNearbyEnemies(team, x, y, range + rangeExtention);

            if(targets.size > 0) {
                for(int i = 0; i < shots; i++) {
                    EMPPart part = parts.random();
                    Teamc target = targets.random();

                    Tmp.v1.trns(rotation * part.rotationMul, part.xOffset, part.yOffset);
                    Tmp.v2.setToRandomDirection().setLength(part.radius);
                    Tmp.v3.setToRandomDirection().setLength(Mathf.random(inaccuracy)); //inaccuracy

                    float shootX = x + Tmp.v1.x + Tmp.v2.x, shootY = y + Tmp.v1.y + Tmp.v2.y;
                    float sX = target.x() + Tmp.v3.x, sY = target.y() + Tmp.v3.y;

                    float shootAngle = Angles.angle(shootX, shootY, sX, sY);
                    float dist = Mathf.dst(shootX, shootY, sX, sY);

                    SnFx.fakeLightning.at(shootX, shootY, shootAngle, lightningColor, new Object[]{dist, lightningStroke, team});
                    shootSound.at(shootX, shootY, Mathf.random(0.9f, 1.1f));
                    shootEffect.at(shootX, shootY, shootAngle, lightningColor);
                    if(shootShake > 0f) {
                        Effect.shake(shootShake, shootShake, this);
                    }
                    final float spawnX = sX, spawnY = sY;
                    Time.run(3f, () -> {
                        for(int j = 0; j < zaps; j++) {
                            shootType.create(this, team, spawnX, spawnY, ((360f / zaps) * j) + Mathf.range(zapAngleRand));
                        }
                    });
                }
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