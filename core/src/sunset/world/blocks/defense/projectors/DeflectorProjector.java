package sunset.world.blocks.defense.projectors;

import arc.Core;
import arc.audio.Sound;
import arc.func.Cons;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Intersector;
import arc.util.Time;
import mindustry.annotations.Annotations.Load;
import mindustry.content.Fx;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.logic.Ranged;
import mindustry.world.blocks.defense.ForceProjector;
import mindustry.world.consumers.ConsumeLiquidFilter;
import mindustry.world.meta.BlockStatus;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.player;
import static mindustry.Vars.tilesize;

public class DeflectorProjector extends ForceProjector {
    @Load("@-liquid")
    public TextureRegion liquid;
    public Color shieldColor = Color.valueOf("92a2dc");
    public static float chanceDeflect = 10;
    public static int deflectAngle = 60;
    public static Sound deflectSound = Sounds.none;
    public static DeflectorBuild paramEntity;
    static final Cons<Bullet> shieldConsumer = trait -> {
        if (trait.team != paramEntity.team && trait.type.absorbable && Intersector.isInsideHexagon(paramEntity.x, paramEntity.y, paramEntity.realRadius() * 2f, trait.x(), trait.y())) {
            if (!deflect(paramEntity, chanceDeflect, trait)) {
                trait.absorb();
                Fx.absorb.at(trait);
            }
            paramEntity.hit = 1f;
            paramEntity.buildup += trait.damage() * paramEntity.warmup;
        }
    };

    public DeflectorProjector(String name) {
        super(name);
    }

    public static boolean deflect(DeflectorBuild building, float chanceDeflect, Bullet bullet) {
        /** Deflect bullets if necessary. */
        if (chanceDeflect > 0f) {
            /** Slow bullets are not deflected. */
            if (bullet.vel().len() <= 0.1f || !bullet.type.reflectable) return false;
            /** Bullet reflection chance depends on bullet damage. */
            if (!Mathf.chance(chanceDeflect / bullet.damage())) return false;
            /** Make sound. */
            deflectSound.at(building.tile, Mathf.random(0.9f, 1.1f));
            /** Translate bullet back to where it was upon collision. */
            bullet.vel.x *= -1;
            bullet.vel.y *= -1;
            /** Add a random angle. */
            bullet.vel.setAngle(Mathf.random(deflectAngle) - deflectAngle / 2 + bullet.vel.angle());
            bullet.owner = building;
            bullet.team = building.team;
            bullet.time = (bullet.time + 1);
            return true;
        }
        return false;
    }

    @Override
    public void load() {
        super.load();
        deflectSound = Sounds.none;
    }

    @Override
    public void setStats() {
        super.setStats();
        if (chanceDeflect > 0) stats.add(Stat.baseDeflectChance, chanceDeflect, StatUnit.none);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        Draw.color(Pal.gray);
        Lines.stroke(3f);
        Lines.poly(x * tilesize + offset, y * tilesize + offset, 6, radius);
        Draw.color(player.team().color);
        Lines.stroke(1f);
        Lines.poly(x * tilesize + offset, y * tilesize + offset, 6, radius);
        Draw.color();
    }

    public class DeflectorBuild extends ForceBuild implements Ranged {

        @Override
        public void updateTile() {
            boolean phaseValid = itemConsumer != null && itemConsumer.efficiency(this) > 0;

            phaseHeat = Mathf.lerpDelta(phaseHeat, Mathf.num(phaseValid), 0.1f);

            if (phaseValid && !broken && timer(timerUse, phaseUseTime) && efficiency() > 0) {
                consume();
            }

            radscl = Mathf.lerpDelta(radscl, broken ? 0f : warmup, 0.05f);

            if (Mathf.chanceDelta(buildup / shieldHealth * 0.1f)) {
                Fx.reactorsmoke.at(x + Mathf.range(tilesize / 2f), y + Mathf.range(tilesize / 2f));
            }

            warmup = Mathf.lerpDelta(warmup, efficiency(), 0.1f);

            if (buildup > 0) {
                float scale = !broken ? cooldownNormal : cooldownBrokenBase;

                //TODO I hate this system
                if(coolantConsumer != null){
                    if(coolantConsumer.efficiency(this) > 0){
                        coolantConsumer.update(this);
                        scale *= (cooldownLiquid * (1f + (liquids.current().heatCapacity - 0.4f) * 0.9f));
                    }
                }

                buildup -= delta() * scale;
            }

            if (broken && buildup <= 0) {
                broken = false;
            }

            if (buildup >= shieldHealth + phaseShieldBoost * phaseHeat && !broken) {
                broken = true;
                buildup = shieldHealth;
                Fx.shieldBreak.at(x, y, realRadius(), shieldColor);
            }

            if (hit > 0f) {
                hit -= 1f / 5f * Time.delta;
            }

            float realRadius = realRadius();

            if (realRadius > 0 && !broken) {
                paramEntity = this;
                Groups.bullet.intersect(x - realRadius, y - realRadius, realRadius * 2f, realRadius * 2f, shieldConsumer);
            }
        }

        @Override
        public void drawShield() {
            if (!broken) {
                float radius = realRadius();

                Draw.z(Layer.shields);

                Draw.color(shieldColor, Color.white, Mathf.clamp(hit));

                if (Core.settings.getBool("animatedshields")) {
                    Fill.poly(x, y, 6, radius);
                } else {
                    Lines.stroke(1.5f);
                    Draw.alpha(0.09f + Mathf.clamp(0.08f * hit));
                    Fill.poly(x, y, 6, radius);
                    Draw.alpha(1f);
                    Lines.poly(x, y, 6, radius);
                    Draw.reset();
                }
            }

            Draw.reset();
        }

        @Override
        public void draw() {
            Draw.rect(region, x, y);

            //Drawf.liquid(liquid, x, y, liquids.total() / liquidCapacity, liquids.current().color);

            drawTeamTop();

            if(buildup > 0f) {
                Draw.alpha(buildup / shieldHealth * 0.75f);
                Draw.blend(Blending.additive);
                Draw.rect(topRegion, x, y);
                Draw.blend();
                Draw.reset();
            }

            drawShield();
        }
    }


}
