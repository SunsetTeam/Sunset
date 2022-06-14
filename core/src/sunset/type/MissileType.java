package sunset.type;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.graphics.*;
import sunset.content.*;
import sunset.world.blocks.defense.turrets.*;

public class MissileType{
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
    public MissileType(String spriteName){
        this(spriteName, false);
    }

    private MissileType(String spriteName, boolean isFull){
        this.spriteName = isFull ? spriteName : ("sunset-" + spriteName);
        id = missileTypes.size;
        missileTypes.add(this);
    }

    public MissileType(MissileSiloTurret parent){
        this(parent.name + "-rocket", true);
    }

    public void load(){
        if(spriteName == null){
            Log.warn("cannot load missileRegion");
            return;
        }
        rocketRegion = Core.atlas.find(spriteName);
        rocketOutlineRegion = Core.atlas.find(spriteName + "-outline");
    }

    public void explode(Vec2 p){
        Damage.damage(p.x, p.y, splashDamageRadius, splashDamage);
        Damage.damage(p.x, p.y, 0.1f, damage);
        explodeEffect.at(p.x, p.y);
    }

    public Void launch(Vec2 from, Vec2 to){
//        Missile missile = Missile.create();
//        missile.set(from, to, this);
//        missile.add();
//        return missile;
        return null;
    }

    public void drawMissile(Void missile){
       /* Vec2 current = missile.currentPosition(Tmp.v1);
        effectContainer.set(id, Color.red, missile.time, missile.lifetime, splashDamageRadius, missile.to.x, missile.to.y, null);
        markEffect.renderer.get(effectContainer);
        float f = 2 - Mathf.pow(1 - missile.fslope(), 2);
        if(rocketOutlineRegion.found()){
            Draw.rect(rocketOutlineRegion, current.x, current.y,
            rocketOutlineRegion.width * f / 4f, rocketOutlineRegion.height * f / 4f);
        }
        Draw.rect(rocketRegion, current.x, current.y,
        rocketRegion.width * f / 4f, rocketRegion.height * f / 4f);*/
    }

    public void drawMissileAlpha(Void missile){
        /*Vec2 current = missile.currentPosition(Tmp.v1);
        effectContainer.set(id, Color.red, missile.time, missile.lifetime, splashDamageRadius, missile.to.x, missile.to.y, null);
        markEffect.renderer.get(effectContainer);
        float f = 2 - Mathf.pow(1 - missile.fslope(), 2);
        float alpha = Mathf.map(Interp.pow2.apply(1f - missile.fslope()), 0, 1, 0.5f, 1f);
        alpha = Mathf.map(missile.fslope(), 0, 1f, 1f, 5f);
        Draw.alpha(alpha);
        effectContainer.set(id, Color.red, missile.time, missile.lifetime, splashDamageRadius, missile.to.x, missile.to.y, null);

        if(rocketOutlineRegion.found()){
            Draw.rect(rocketOutlineRegion, current.x, current.y,
            rocketOutlineRegion.width * f / 4f, rocketOutlineRegion.height * f / 4f);
        }else{
            Draw.rect(rocketRegion, current.x, current.y,
            rocketRegion.width * f / 4f, rocketRegion.height * f / 4f);
        }*/
    }

    public void landed(Void self){
//        explode(self.to);
    }
}
