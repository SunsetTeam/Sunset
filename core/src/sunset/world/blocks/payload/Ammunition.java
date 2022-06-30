package sunset.world.blocks.payload;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.annotations.Annotations.Load;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import mma.ModVars;
import sunset.content.SnFx;

import static mindustry.Vars.tilesize;

public class Ammunition extends Block {
    public int explosionRadius = 11;
    public int explosionDamage = 70;
    public Effect explodeEffect = SnFx.sunriseMissileExplosion;

    @Load("@name")
    public TextureRegion ammunitionRegion;

    public Ammunition(String name) {
        super(name);
        health = 50;
        solid = true;
        update = true;
        category = Category.units;
        buildVisibility = BuildVisibility.sandboxOnly;
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{ammunitionRegion, region};
    }

    public class AmmunitionBuild extends Building {
        @Override
        public void onDestroyed(){
            super.onDestroyed();

            Sounds.explosionbig.at(this);

            Effect.shake(6f, 16f, x, y);
            // * ((float)fuel / itemCapacity) to scale based on fullness
            Damage.damage(x, y, explosionRadius * tilesize, explosionDamage * 4);

            explodeEffect.at(x, y);
        }
    }
}