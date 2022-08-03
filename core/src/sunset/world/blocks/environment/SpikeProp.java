package sunset.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.entities.Units;
import mindustry.gen.Building;
import mindustry.gen.Healthc;
import mindustry.gen.Unit;
import mindustry.world.Tile;
import mindustry.world.Block;

public class SpikeProp extends Block {
    public float range;
    public float damage;
    public boolean targetAir = false;
    public boolean targetGround = false;


    public SpikeProp(String name){
        super(name);
        breakable = false;
        alwaysReplace = false;
    }


    @Override
    public void drawBase(Tile tile){
        Draw.rect(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region, tile.worldx(), tile.worldy());
    }

    @Override
    public TextureRegion[] icons(){
        return variants == 0 ? super.icons() : new TextureRegion[]{Core.atlas.find(name + "1")};
    }

    public class SpikePropBuild extends Building{
        public Unit unit;

        @Override
        public void updateTile(){
            Units.nearby(null, x, y, range, u -> {
                if (u != unit | (targetAir) && (targetGround)) {
                    u.damage(damage);
                    }
            });
        }
    }
}
