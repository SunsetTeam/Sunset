package sunset.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.*;
import mindustry.entities.Units;
import mindustry.graphics.Layer;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.TallBlock;
import sunset.world.type.*;


public class SpikeProp extends TallBlock implements UpdateEnvironment<Interval>{
    public float touchRange;
    public float touchDamage;

    public float shadowOffset = -1.2f;
    public float layer = Layer.power + 1;
    public float rotationRand = 23f;
    public float shadowAlpha = 0.7f;

    public SpikeProp(String name){
        super(name);
        solid = true;
        clipSize = 90;
        customShadow = true;
    }

    @Override
    public void init(){
        super.init();
        hasShadow = true;
        breakable = false;
    }

    @Override
    public void drawBase(Tile tile){
        float z = Draw.z();
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
    public TextureRegion[] icons(){
        return variants == 0 ? super.icons() : new TextureRegion[]{Core.atlas.find(name + "1")};
    }

    @Override
    public Interval getDefaultValue(){
        Interval interval = new Interval();
        interval.getTimes()[0] = Time.time - Mathf.random() * Time.toSeconds;
        return interval;
    }

    @Override
    public void updateTile(Tile tile, Interval data){
        if(!data.get(Time.toSeconds / 8)) return;
        Units.nearby(null, tile.worldx(), tile.worldy(), touchRange, u -> {
            if(u.moving() && !u.isFlying()){
                u.damage(touchDamage);
            }
        });
    }
}