package sunset.world.blocks.gas;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.util.Eachable;
import arc.util.Nullable;
import arc.util.Tmp;
import gas.world.blocks.distribution.GasItemBridge;
import gas.world.blocks.gas.GasBridge;
import mindustry.annotations.Annotations;
import mindustry.core.Renderer;
import mindustry.entities.units.BuildPlan;

import static mindustry.Vars.tilesize;

public class SnGasBridge extends GasBridge {
    private static BuildPlan otherReq;

    public @Annotations.Load("@-bridge") TextureRegion bridgeRegion;
    public @Annotations.Load("@-bridge-bottom") TextureRegion bridgeBotRegion;
    public @Annotations.Load("@-bridge-gas") TextureRegion bridgeGasRegion;
    public @Annotations.Load("@-arrow") TextureRegion arrowRegion;

    public SnGasBridge(String name){
        super(name);
    }

    @Override
    public void drawPlanConfigTop(BuildPlan plan, Eachable<BuildPlan> list){
        otherReq = null;
        list.each(other -> {
            if(other.block == this && plan != other && plan.config instanceof Point2 p && p.equals(other.x - plan.x, other.y - plan.y)){
                otherReq = other;
            }
        });

        if(otherReq != null){
            drawBridge(plan, otherReq.drawx(), otherReq.drawy(), 0, null);
        }
    }

    public void drawBridge(BuildPlan req, float ox, float oy, float flip, @Nullable Color gasColor){
        Draw.alpha(Renderer.bridgeOpacity);
        float
                angle = Angles.angle(ox, oy),
                cx = ox/2f,
                cy = oy/2f,
                len = Math.max(Math.abs(ox), Math.abs(oy)) - size * tilesize;

        Tmp.v1.set(ox, oy).sub(req.drawx(), req.drawy()).setLength(tilesize/2f);

        Draw.rect(bridgeRegion, cx, cy, len, tilesize, angle);
        if(gasColor != null){
            Draw.color(gasColor, gasColor.a * Renderer.bridgeOpacity);
            Draw.rect(bridgeGasRegion, cx, cy, len, tilesize, angle);
            Draw.color();
            Draw.alpha(Renderer.bridgeOpacity);
        }
        if(bridgeBotRegion.found()){
            Draw.color(0.4f, 0.4f, 0.4f, 0.4f * Renderer.bridgeOpacity);
            Draw.rect(bridgeBotRegion, cx, cy, len, tilesize, angle);
            Draw.reset();
        }
        Draw.alpha(Renderer.bridgeOpacity);

        Draw.rect(arrowRegion, (req.drawx() + ox) / 2f, (req.drawy() + oy) / 2f,
                Angles.angle(req.drawx(), req.drawy(), ox, oy) + flip);


        Draw.reset();
    }
}
