package sunset.entities.bullet;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;

public class AcceleratingLaserBulletType extends LaserBulletType{
    public Interp interp = Interp.pow5In;

    public AcceleratingLaserBulletType(float damage){
        this.damage = damage;
        this.speed = 0f;

        hitEffect = Fx.hitLaserBlast;
        hitColor = colors[2];
        despawnEffect = Fx.none;
        shootEffect = Fx.hitLancer;
        smokeEffect = Fx.none;
        hitSize = 4;
        lifetime = 16f;
        impact = true;
        keepVelocity = false;
        collides = false;
        pierce = true;
        hittable = false;
        absorbable = false;
    }

    @Override
    public void update(Bullet b){
        float
        x = b.x + Angles.trnsx(b.rotation(), length * interp.apply(b.fin())),
        y = b.y + Angles.trnsy(b.rotation(), length * interp.apply(b.fin()));

        if(b.timer.get(0, 6.5F)){
            despawnEffect.at(x, y, b.rotation() + 90);
            despawnEffect.at(x, y, b.rotation() - 90);
        }
        if(b.time < lifetime && b.fin() > 0.5){
            Damage.damage(b.team, x, y, 8, damage);
            if(healPercent > 0){
                var build = Vars.world.buildWorld(x, y);
                if(build != null && build.team == b.team){
                    if(build.damaged()){
                        build.heal(healPercent / 100 * build.maxHealth);
                        Fx.healBlockFull.at(build.getX(), build.getY(), build.block.size, colors[0]);
                    }
                }
            }
        }
    }

    @Override
    public void draw(Bullet b){
        float x = b.x + Angles.trnsx(b.rotation(), length * interp.apply(b.fin()));
        float y = b.y + Angles.trnsy(b.rotation(), length * interp.apply(b.fin()));
        float cwidth = width;
        float compound = 1f;

        for(Color color : colors){
            Draw.color(color);
            Lines.stroke((cwidth *= lengthFalloff) * b.fout());
            Lines.lineAngle(b.x, b.y, b.rotation(), length * interp.apply(b.fin()));
            Drawf.tri(x, y, Lines.getStroke() * 1.22f, cwidth * 2f + width / 2f, b.rotation());
        }

        Fill.circle(b.x, b.y, 1f * cwidth * b.fout());
        for(int i : Mathf.signs){
            Drawf.tri(b.x, b.y, sideWidth * b.fout() * cwidth, sideLength * compound, b.rotation() + sideAngle * i);
        }
        Drawf.light(b.team, b.x, b.y, x, y, width * 1.4f, this.colors[0], 0.6f);
        Draw.reset();
    }

    public void drawLight(Bullet b){
    }
}