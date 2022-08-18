package sunset.world.blocks.environment;

import mindustry.content.*;
import mindustry.entities.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import sunset.*;

public class Geyser extends OverlayFloor{

    public Effect calmEffect = Fx.none;
    public Effect steamEffect = Fx.none;
    public Effect eruptionEffect = Fx.none;
    public float steamDamage = 0.5f;
    public float eruptionDamage = 1f;
    public int drawSize = 2;

    public Geyser(String name){
        super(name);
        Class<? extends Geyser> aClass = getClass();
        if(aClass.isAnonymousClass() && aClass.getSuperclass() == Geyser.class || aClass == Geyser.class){
//            return;
        }
//        throw new IllegalAccessException("You cannot extends");
    }

    @Override
    public void drawOverlay(Tile tile){
        super.drawOverlay(tile);
    }

    @Override
    public boolean updateRender(Tile tile){
        return super.updateRender(tile);
    }

    @Override
    public void renderUpdate(UpdateRenderState tile){

        super.renderUpdate(tile);
    }

    @Override
    public void drawBase(Tile tile){
        if(SnVars.logic.geyserLogic.shouldDrawTile(tile)) super.drawBase(tile);
    }
}
