package sunset.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Interval;
import mindustry.annotations.Annotations;
import mindustry.entities.Units;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.Tile;

import static mindustry.Vars.tilesize;
import static mindustry.gen.Nulls.unit;


public class SpikeProp extends Block {
    public transient Interval timer;
    public final int timerMogus = timers++;
    public float touchRange;
    public float touchDamage;

    public float shadowOffset = -1.2f;
    public float layer = Layer.power + 1;
    public float rotationRand = 23f;
    public float shadowAlpha = 0.7f;

    public SpikeProp(String name) {
        super(name);
        solid = true;
        clipSize = 90;
        customShadow = true;
        breakable = false;
        teamPassable = true;
        update = true;
    }

    @Override
    public void init(){
        super.init();
        hasShadow = true;
        breakable = false;
        teamPassable = true;
        update = true;
    }

    @Override
    public void drawBase(Tile tile){
        float rot = Mathf.randomSeedRange(tile.pos() + 1, rotationRand);

        Draw.z(Layer.power - 1);
        Draw.color(0f, 0f, 0f, shadowAlpha);
        Draw.rect(variants > 0 ? variantShadowRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantShadowRegions.length - 1))] : customShadowRegion,
                tile.worldx() + shadowOffset, tile.worldy() + shadowOffset, rot);

        Draw.color();

        Draw.z(Layer.power + 1);
        Draw.rect(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region,
                tile.worldx(), tile.worldy(), rot);
    }

    @Override
    public void drawShadow(Tile tile){
    }

    @Override
    public TextureRegion[] icons(){
        return variants == 0 ? super.icons() : new TextureRegion[]{Core.atlas.find(name + "1")};
    }

    public class SpikePropBuilding extends Building {
        @Override
        public void updateTile(){
            Units.nearby(null, x, y, touchRange, u -> {
                if (u.moving() && !u.isFlying() && timer.get(timerMogus, 10)) {
                    u.damage(touchDamage);
                }
            });
        }
    }
}
