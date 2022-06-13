package sunset.world.draw;

import arc.graphics.g2d.*;
import arc.struct.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.defense.turrets.Turret.*;
import sunset.world.blocks.defense.turrets.*;
import sunset.world.blocks.defense.turrets.EMPFacility.*;

public class DrawEMPFacility extends SnDrawTurret{
    public DrawEMPFacility(String basePrefix){
        super(basePrefix);
    }

    public DrawEMPFacility(){
        super();
    }

    @Override
    public void getRegionsToOutline(Block block, Seq<TextureRegion> out){
        super.getRegionsToOutline(block, out);
    }

    @Override
    public void draw(Building build){
        EMPFacility turret = (EMPFacility)build.block;
        EMPFacilityBuild tb = build.as();
        Draw.rect(base, tb.x, tb.y);

        Draw.z(Layer.turret);

        TextureRegion r = preview;

        Draw.rect(r, tb.x, tb.y);

        Draw.rect(top, tb.x, tb.y);
    }

    @Override
    public void drawTurret(Turret block, TurretBuild build){
        super.drawTurret(block, build);
    }

    @Override
    public void drawHeat(Turret block, TurretBuild build){
        super.drawHeat(block, build);
    }

    @Override
    public void load(Block block){
        super.load(block);
    }

    @Override
    public TextureRegion[] icons(Block block){
        return super.icons(block);
    }
}
