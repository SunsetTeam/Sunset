package sunset.entities.pattern;

import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.pattern.*;
import sunset.utils.*;
import sunset.utils.kotlin.*;

public class ShootMultiBarrel extends ShootPattern{
    public Vec2[] barrelPoints = {};
    public Vec2[] ejectPoints = {};
    public Effect ammoUseEffect = Fx.none;
    public int blockSize = 1;
    public boolean useRotation = false;
    {
        UtilsKt.TODO("WAITING FOR PULL REQUEST");
    }
    public ShootMultiBarrel barrelPoints(Vec2... barrelPoints){
        this.barrelPoints = barrelPoints;
        return this;
    }

    public ShootMultiBarrel ejectPoints(Vec2... ejectPoints){
        this.ejectPoints = ejectPoints;
        return this;
    }

    public ShootMultiBarrel ammoUseEffect(Effect ammoUseEffect){
        this.ammoUseEffect = ammoUseEffect;
        return this;
    }

    @Override
    public void shoot(int totalShots, BulletHandler handler){
        UtilsKt.TODO("WAITING FOR PULL REQUEST");
        /*for(int shoot = 0; shoot < totalShots; shoot++){
            for(int i = 0; i < barrelPoints.length; i++){
                final int index = i;
                Vec2 barrelPoint = barrelPoints[i];
                float rotation = Angles.angle(barrelPoint.x - 0.5f, barrelPoint.y - 0.5f) * Mathf.num(useRotation);
                handler.shoot((barrelPoint.x - 0.5f) * Vars.tilesize * blockSize, (barrelPoint.y - 0.5f) * Vars.tilesize * blockSize, rotation, shotDelay * i);
                Time.run(shotDelay * i, () -> {
                    Tmp.v1.set(ejectPoints[i]).sub(0.5f, 0.5f).scl(Vars.tilesize * blockSize);
                    ammoUseEffect.at(x + Tmp.v1.x, y + Tmp.v1.y, ejectPoints[i].x > 0.5f ? rotation : rotation + 180f);
                });
            }
        }*/
        super.shoot(totalShots, handler);
    }
}
