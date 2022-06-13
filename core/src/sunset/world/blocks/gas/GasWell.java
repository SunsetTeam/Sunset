package sunset.world.blocks.gas;

import arc.Core;
import arc.graphics.Color;
import arc.math.Mathf;
import gas.world.blocks.production.GasAttributeCrafter;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.graphics.Drawf;
import mindustry.world.Tile;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.Stat;
import sunset.content.SnAttribute;

public class GasWell extends GasCrafter {

    public Effect generateEffect = Fx.none;
    public float effectChance = 0.05f;
    public Attribute attribute = SnAttribute.gas;

    public GasWell(String name){
        super(name);
    }

    @Override
    public void init(){
        super.init();
        //proper light clipping
        clipSize = Math.max(clipSize, 45f * size * 2f * 2f);
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.tiles, attribute, floating, size * size, false);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        drawPlaceText(Core.bundle.formatFloat("bar.efficiency", sumAttribute(attribute, x, y) * 100, 1), x, y, valid);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        //make sure there's heat at this location
        return tile.getLinkedTilesAs(this, tempTiles).sumf(other -> other.floor().attributes.get(attribute)) > 0.01f;
    }

    public class GasWellBuild extends GasCrafterBuild {
        public float sum;

        @Override
        public void updateTile(){
            baseEfficiency = sum + attribute.env();

            if(baseEfficiency > 0.1f && Mathf.chanceDelta(effectChance)){
                generateEffect.at(x + Mathf.range(3f), y + Mathf.range(3f));
            }
        }

        @Override
        public void drawLight(){
            Drawf.light( x, y, (40f + Mathf.absin(10f, 5f)) * Math.min(baseEfficiency, 2f) * size, Color.scarlet, 0.4f);
        }

        @Override
        public void onProximityAdded(){
            super.onProximityAdded();

            sum = sumAttribute(attribute, tile.x, tile.y);
        }
    }
}
