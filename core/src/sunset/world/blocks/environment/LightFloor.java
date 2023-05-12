package sunset.world.blocks.environment;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.util.Tmp;
import mindustry.annotations.Annotations;
import mindustry.graphics.Drawf;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;

import static mindustry.Vars.tilesize;

public class LightFloor extends Floor {
    public float lRadius = 1;
    public Color lightColor = Color.white.cpy();
    public @Annotations.Load("@-top") TextureRegion topRegion;

    public LightFloor(String name){
        super(name);
        variants = 3;
    }

    @Override
    public void load(){
        super.load();

        //load variant regions for drawing
        if(variants > 0){
            variantRegions = new TextureRegion[variants];
            for(int i = 0; i < variants; i++){
                variantRegions[i] = Core.atlas.find(name + (i + 1));
            }
        }else{
            variantRegions = new TextureRegion[1];
            variantRegions[0] = Core.atlas.find(name);
        }
        int size = (int)(tilesize / Draw.scl);
        if(Core.atlas.has(name + "-edge")){
            edges = Core.atlas.find(name + "-edge").split(size, size);
        }
        region = variantRegions[0];
        edgeRegion = Core.atlas.find("edge");
    }

    @Override
    public void drawBase(Tile tile) {
        super.drawBase(tile);
        Mathf.rand.setSeed(tile.pos());
        Draw.rect(variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))], tile.worldx(), tile.worldy());

        Draw.alpha(1f);
        drawEdges(tile);
        drawFloorLight(tile);
        drawOverlay(tile);
    }

    public void drawFloorLight(Tile tile){
        Drawf.light((Position) topRegion, lightRadius, lightColor, 0.5f);
    }
}
