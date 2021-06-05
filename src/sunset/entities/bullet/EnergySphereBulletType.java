package sunset.entities.bullet;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.graphics.Pal;
import sunset.content.SnFx;
import sunset.graphics.SnPal;

public class EnergySphereBulletType extends BasicBulletType {
    public float lightningPeriod = 1f;
    public EnergySphereBulletType(String sprite, float speed, float damage) {
        super(speed, damage, sprite);
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        if(b.timer.get(lightningPeriod)){
            // damage enemy and heal ally
            for (Team t:Team.all) {
                // units
                Units.nearby(t, b.x, b.y, lightningLength, e -> {
                    if(t.isEnemy(b.team)) {
                        e.damagePierce(lightningDamage);
                        SnFx.lightning(e.x, e.y, b.x, b.y, SnPal.lightningDamage, 3, 12f, SnFx.lightningFast);
                    } else {
                        if(e.damaged() && healPercent > 0) {
                            e.heal(lightningDamage*healPercent/100f);
                            SnFx.lightning(e.x, e.y, b.x, b.y, SnPal.lightningHeal, 3, 12f, SnFx.lightningFast);
                        }
                    }
                });
                // blocks
                Vars.indexer.eachBlock(t, b.x, b.y, lightningLength, e -> true, e -> {
                    if(t.isEnemy(b.team)) {
                        e.damagePierce(lightningDamage);
                        SnFx.lightning(e.x, e.y, b.x, b.y, SnPal.lightningDamage, 3, 12f, SnFx.lightningFast);
                    } else {
                        if(e.damaged() && healPercent > 0) {
                            e.heal(lightningDamage*healPercent/100f);
                            SnFx.lightning(e.x, e.y, b.x, b.y, SnPal.lightningHeal, 3, 12f, SnFx.lightningFast);
                            Fx.healBlockFull.at(e.x, e.y, e.block.size, Pal.heal);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void draw(Bullet b) {
        Draw.color(Pal.surge);
        Fill.circle(b.x, b.y, hitSize);
    }

    @Override
    public float estimateDPS() {
        return damage + 2*splashDamage + lightningDamage*lifetime/lightningPeriod;
    }
}