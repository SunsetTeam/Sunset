package sunset.world.blocks.environment;

import arc.graphics.Color;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;
import sunset.content.SnFx;
import sunset.graphics.SnPal;

import static arc.math.Mathf.rand;
import static mindustry.Vars.tilesize;

public class ThermalFloor extends Floor {
    public Block parent = Blocks.air;
    public Effect effect = SnFx.thermalBubbles;
    public float effectSpacing = 15f;
    public Color effectColor = SnPal.thermalBuble;
    public ThermalFloor(String name, int variants) {
        super(name, variants);
    }
    @Override
    public boolean updateRender(Tile tile){
        return checkAdjacent(tile);
    }

    @Override
    public void renderUpdate(UpdateRenderState state){
        if(state.tile.block() == Blocks.air && (state.data += Time.delta) >= effectSpacing){
            effect.at(state.tile.x * tilesize + rand.random(-1, 1), state.tile.y * tilesize + rand.random(-1, 1), effectColor);
            state.data = 0f;
        }
    }

    public boolean checkAdjacent(Tile tile){
            Tile other = Vars.world.tile(tile.x, tile.y);
        return other != null && other.floor() == this;
    }
}
