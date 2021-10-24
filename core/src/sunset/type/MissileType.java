package sunset.type;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;
import sunset.content.SnFx;
import sunset.gen.Missile;
import sunset.world.blocks.defense.turrets.MissileSiloTurret;

import static mma.ModVars.fullName;

public class MissileType {
    private static final Effect.EffectContainer effectContainer = new Effect.EffectContainer();
    public static Seq<MissileType> missileTypes = new Seq<>();
    public final int id;
    public final String spriteName;
    public int outlineRadius = 3;
    public Color outlineColor = Pal.darkerMetal;
    public float damage;
    public float splashDamage;
    public float splashDamageRadius;
    public float speed;
    public Effect explodeEffect = Fx.none;
    public Effect markEffect = SnFx.missilePoint;

    public TextureRegion rocketRegion;
    public TextureRegion rocketOutlineRegion;

    /**
     * @param spriteName sprite name
     */
    public MissileType(String spriteName) {
        this(spriteName, false);
    }

    private MissileType(String spriteName, boolean isFull) {
        this.spriteName = isFull ? spriteName : fullName(spriteName);
        id = missileTypes.size;
        missileTypes.add(this);
    }

    public MissileType(MissileSiloTurret parent) {
        this(parent.name + "-rocket", true);
    }

    public void load() {
        if (spriteName == null) {
            Log.warn("cannot load missileRegion");
            return;
        }
        rocketRegion = Core.atlas.find(spriteName);
        rocketOutlineRegion = Core.atlas.find(spriteName + "-outline");
    }

    public void explode(Vec2 p) {
        Damage.damage(p.x, p.y, splashDamageRadius, splashDamage);
        Damage.damage(p.x, p.y, 0.1f, damage);
        explodeEffect.at(p.x, p.y);
    }

    public Missile launch(Vec2 from, Vec2 to) {
        Missile missile = Missile.create();
        missile.set(from, to, this);
        missile.add();
        return missile;
    }

    public void drawMissile(sunset.gen.Missile missile) {
        Vec2 current = missile.currentPosition(Tmp.v1);
        effectContainer.set(id, Color.red, missile.time, missile.lifetime, splashDamageRadius, missile.to.x, missile.to.y, null);
        markEffect.renderer.get(effectContainer);
        float f = 2 - Mathf.pow(1 - missile.fslope(), 2);
        if (rocketOutlineRegion.found()){
            Draw.rect(rocketRegion, current.x, current.y,
                    rocketRegion.width * f / 4f, rocketRegion.height * f / 4f);
        }
        Draw.rect(rocketRegion, current.x, current.y,
                rocketRegion.width * f / 4f, rocketRegion.height * f / 4f);
    }

    public void landed(Missile self) {
        explode(self.to);
    }
}
