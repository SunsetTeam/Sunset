package sunset.type;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.Scaled;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.io.TypeIO;
import sunset.content.SnFx;
import sunset.gen.Missile;

public class MissileType {
    private static final Effect.EffectContainer effectContainer=new Effect.EffectContainer();
    public float damage;
    public float splashDamage;
    public float splashDamageRadius;
    public float speed;
    public final  int id;
    public static Seq<MissileType> missileTypes=new Seq<>();

    public Effect explodeEffect = Fx.none;
    public Effect markEffect = SnFx.missilePoint;

    public TextureRegion rocketRegion;

    public MissileType() {
        id=missileTypes.size;
        missileTypes.add(this);
    }

    public void explode(Vec2 p) {
        Damage.damage(p.x, p.y, splashDamageRadius, splashDamage);
        Damage.damage(p.x, p.y, 0.1f, damage);
        explodeEffect.at(p.x, p.y);
    }

    public Missile launch(Vec2 from, Vec2 to) {
        Missile missile = Missile.create();
        missile.set(from,to,this);
        missile.add();
        return  missile;
    }

    public void drawMissile(sunset.gen.Missile missile) {
        Vec2 current= missile.currentPosition(Tmp.v1);
        effectContainer.set(id,Color.red,missile.time,missile.lifetime,splashDamageRadius,missile.to.x,missile.to.y,null);
        markEffect.renderer.get(effectContainer);
        float f = 2 - Mathf.pow(1 - missile.fslope(), 2);
        Draw.rect(rocketRegion, current.x, current.y,
                rocketRegion.width * f / 4f, rocketRegion.height * f / 4f);
    }

    public void landed(Missile self) {
        explode(self.to);
    }
}
