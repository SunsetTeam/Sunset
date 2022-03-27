package sunset.entities.bullet;

import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Time;
import mindustry.Vars;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.gen.Healthc;
import mindustry.gen.Posc;
import mindustry.graphics.Layer;
import sunset.content.SnFx;
import sunset.graphics.*;

public class EnergySphereBulletType extends BasicBulletType {
    // lightnings around settings
    public float lightningPeriod = 60f;
    public float lightningChance = 1f;
    // enemy follow settings
    public float followRadius = 517;
    public float followFactor = 0.025f;
    public float followMultiplier = 0.3f;
    public float distancePower = 3.25f;
    public float weightPower = 0.5f;
    public float minSpeedFactor = 0.6f;
    // draw settings
    public int count = 15;
    public float timeSclMin = 0.66f;
    public float timeSclMax = 1.66f;
    public float lenMin = 3f;
    public float lenMax = 5f;
    public float radMin = 2.5f;
    public float radMax = 4f;

    private float range;

    public EnergySphereBulletType(float speed, float damage) {
        super(speed, damage);
        collidesAir = collidesGround = false;
        range = Math.max(lightningLength, followRadius);
    }

    @Override
    public void draw(Bullet b) {
        Draw.mixcol(frontColor, 0.25f);
        Draw.z(Layer.bullet);
        Drawm.drawTrident(b.x, b.y, b.id, count, timeSclMin, timeSclMax, lenMin, lenMax, radMin, radMax, 1);
        Draw.mixcol();
    }

    Vec2 tmpVel = new Vec2(), tmpDst = new Vec2();

    @Override
    public void update(Bullet b) {
        super.update(b);
        tmpVel.set(0, 0);
        boolean lightning = b.timer.get(lightningPeriod);
        for (Team t:Team.all) {
            // units
            Units.nearby(t, b.x, b.y, range, e -> {
                if(lightning) lightningDamage(b, t, e);
                if(t.isEnemy(b.team)) {
                    adjustDirection(b, e, e.physicSize());
                    if(e.dst(b) <= hitSize) e.damageContinuous(continuousDamage());
                }
            });
            // blocks
            Vars.indexer.eachBlock(t, b.x, b.y, range, e -> true, e -> {
                if(lightning) lightningDamage(b, t, e);
                if(t.isEnemy(b.team)) {
                    if(e.dst(b) <= hitSize) e.damageContinuous(continuousDamage());
                    adjustDirection(b, e, Mathf.pow(e.hitSize(), 2));
                }
            });    
        }
        // move to enemies
        float f = Mathf.pow(followFactor, Time.delta);
        if(tmpVel.len() < speed * minSpeedFactor) tmpVel.setLength(speed * minSpeedFactor);
        tmpVel.add(b.vel).limit(speed);
        b.vel.scl(1-f).add(tmpVel.scl(f));
        if(b.vel.len() < speed * minSpeedFactor) b.vel.setLength(speed * minSpeedFactor);
    }

    private void adjustDirection(Bullet b, Posc to, float weight) {
        if(b.dst(to) > followRadius) return;
        float dst = tmpDst.set(to).sub(b).len() / followRadius;
        float mult = Mathf.pow(dst, distancePower) * Mathf.pow(weight, weightPower) * followMultiplier;
        tmpVel.add(tmpDst.scl(mult));
    }

    private <T extends Posc & Healthc> void lightningDamage(Bullet b, Team t, T e) {
        if(e.dst(b) > lightningLength || !Mathf.chance(lightningChance)) return;
        if(t.isEnemy(b.team)) {
            e.damagePierce(lightningDamage);
            SnFx.lightning(e.x(), e.y(), b.x, b.y, SnPal.lightningDamage, 3, 12f, SnFx.lightningFast);
        } else {
            if(e.damaged() && healPercent > 0) {
                e.heal(lightningDamage*healPercent/100f);
                SnFx.lightning(e.x(), e.y(), b.x, b.y, SnPal.lightningHeal, 3, 12f, SnFx.lightningFast);
            }
        }
    }

    @Override
    public float estimateDPS() {
        return damage + 2*splashDamage + lightningDamage*lifetime/lightningPeriod*lightning;
    }
}